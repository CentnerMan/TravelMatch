package ru.travelmatch.utils;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.travelmatch.utils.validation.FieldMatch;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
public class SystemUser {
    @NotEmpty(message = "Not Empty!")
    @Size(min = 3, message = "Too short <4!")
    private String username;

    @NotNull(message = "Not Null!")
    @Size(min = 3, message = "Too short <4!")
//    @Pattern(regexp = "(?=^.{4,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z])", message = "must be a-Z,0-9,_'^&/+! and <4")
    private String password;

    @NotNull(message = "Not Null!")
    @Size(min = 3, message = "Too short <4!")
//    @Pattern(regexp = "(?=^.{4,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z])", message = "Must be a-Z,0-9,_'^&/+! and <4")
    private String matchingPassword;

    @NotEmpty(message = "Not Empty!")
    private String firstName;

    @NotEmpty(message = "Not Empty!")
    private String lastName;

    @NotNull(message = "Not Null!")
    @Size(min = 3, message = "Too short <3!")
    @Email(message = "Must be Email@xxx.xxx!")
    private String email;

}
