package ru.travelmatch.exception;

/**
 * @author Ostrovskiy Dmitriy
 * @created 20.08.2020
 * RestExceptionHandler
 *
 * Возможно создать пользовательское исключение
 * с наследованием либо от Exception либо от RunTimeException
 * Чтобы принудительно вызвать пользовательское исключение
 * нужно прописать: throw new YouException() в блоке catch
 *
 * @version v1.0
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    public RestExceptionHandler() {
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> handleInvalidAuth(AuthenticationException ex) {
        logger.error("Неправильное имя пользователя или пароль. AuthenticationException = {}", ex.toString());
        return new ResponseEntity<>(new SimpleException("Неправильное имя пользователя или пароль"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<?> handleInvalidAuth2(BadCredentialsException ex) {
        logger.error("Неправильное имя пользователя или пароль. BadCredentialsException = {}", ex.toString());
        return new ResponseEntity<>(new SimpleException("Неправильное имя пользователя или пароль"),
                HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class SimpleException {
       private String message;
    }

    /**
     * Обработка исключения некорректного чтения json
     *
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        DataErrorResponse response = new DataErrorResponse(status, "Incorrect JSON request", ex);
        logger.error("Incorrect JSON request. HttpMessageNotReadableException = {}, {}", ex.toString(), response.toString());
        return new ResponseEntity(response, status);
    }

    /**
     * Обработка исключения если не найдено обработчика на данный запрос
     *
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        logger.error("Incorrect JSON request. NoHandlerFoundException = {}", ex.toString());
        return new ResponseEntity<>(new DataErrorResponse(status, "No handler found", ex), HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка исключений на валидные поля
     *
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        DataErrorResponse response = new DataErrorResponse(status, "Method arg not valid", ex);
        response.addValidationErrors(ex.getBindingResult().getFieldErrors());
        logger.error("Incorrect JSON request. MethodArgumentNotValidException = {}, {}", ex.toString(), response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработка исключение на неверный тип аргумента
     *
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        DataErrorResponse response = new DataErrorResponse(BAD_REQUEST);
        response.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        response.setDebugMessage(ex.getMessage());
        logger.error("Incorrect JSON request. MethodArgumentTypeMismatchException = {}, {}", ex.toString(), response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработка пользовательского исключения
     *
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> handleEntityNotFoundEx(EntityNotFoundException ex, WebRequest request) {
        DataErrorResponse response = new DataErrorResponse(HttpStatus.NOT_FOUND, "entity not found ex", ex);
        logger.error("Incorrect JSON request. EntityNotFoundException = {}, {}", ex.toString(), response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Общий обработчик по умолчанию Exception
     * Если не отработали остальные, то этот ловит почти всё остальное
     *
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        DataErrorResponse response = new DataErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Exception", ex);
        logger.error("Exception = {}, , {}", ex.toString(), response.toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Общий обработчик по умолчанию RuntimeException
     * Если не отработали остальные, то этот ловит почти всё остальное
     *
     */
    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<?> handleDataNotFoundException(DataNotFoundException ex) {
        DataErrorResponse response = new DataErrorResponse(HttpStatus.NOT_FOUND, "RunTime Exception", ex);
        logger.error("RunTime Exception = {}, {}", ex.toString(), response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка исключения файл не найден
     *
     */
    @ExceptionHandler(FileNotFoundException.class)
    protected ResponseEntity<DataErrorResponse> handleFileNotFoundException(FileNotFoundException ex) {
        DataErrorResponse response = new DataErrorResponse(HttpStatus.NOT_FOUND, "File not found exception", ex);
        logger.error("File not found exception = {}, {}", ex.toString(), response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка исключения загрузчика файла
     *
     */
    @ExceptionHandler(FileUploadException.class)
    protected ResponseEntity<?> handleFileUploadException(FileUploadException ex) {
        DataErrorResponse response = new DataErrorResponse();
        response.setStatus(HttpStatus.NOT_ACCEPTABLE);
        response.setMessage(ex.getMessage());
        logger.error("FileUploadException = {}, {}", ex.toString(), response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Обработка пользовательских ошибок BadRequest
     *
     */
    @ExceptionHandler(DataBadRequest.class)
    protected ResponseEntity<DataErrorResponse> handleDataBadRequest() {
        logger.error("DataBadRequest!");
        return new ResponseEntity<>(new DataErrorResponse(), HttpStatus.BAD_REQUEST);
    }
}
