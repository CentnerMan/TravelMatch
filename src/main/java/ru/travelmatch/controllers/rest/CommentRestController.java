package ru.travelmatch.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.dto.CommentDTO;
import ru.travelmatch.services.CommentService;

/**
 * @Author Farida Gareeva
 * Created 25.08.2020
 * v1.0
 */
@RestController
@RequestMapping(value = "api/v1/articles/comments")
@AllArgsConstructor
@Api("Set of endpoints for crud operation over article comments")
public class CommentRestController {

    private CommentService commentService;

    @ApiOperation("GET all article comments")
    @GetMapping(produces = "application/json")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAll());
    }

    @ApiOperation("GET article comment by id")
    @GetMapping(value = "/id={id}",produces = "application/json")
    public ResponseEntity findById(@PathVariable Long id){
        return commentService.findById(id);
    }

    @ApiOperation("DELETE article comment by id")
    @DeleteMapping(value = "/id={id}",produces = "application/json")
    public ResponseEntity deleteById(@PathVariable Long id){
        return commentService.delete(id);
    }

    //Всегда вставляет новые объекты (insert)
    @ApiOperation("INSERT article comment (always new element)")
    @PostMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity createComment(@RequestBody CommentDTO commentDTO){
        return commentService.create(commentDTO);
    }

    //Обновляет существующие или вставляет новые, если не найдены (update or insert)
    @ApiOperation("UPDATE article comment by id or INSERT new, if comment with selected id not found")
    @PutMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity updateComment(@RequestBody CommentDTO commentDTO){
        return commentService.update(commentDTO);
    }
}
