package com.website.spring.service.forms;

import com.website.spring.service.validators.UniqueEmail;
import com.website.spring.service.validators.UniqueLogin;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserForm {
    private static final String ERROR_MESSAGE_FIRSTNAME = "Can have only English and Russian letters";
    private static final String ERROR_MESSAGE_LASTNAME = "Can have only English and Russian letters";
    private static final String ERROR_MESSAGE_LOGIN = "Can have English letters and numbers only";
    private static final String ERROR_MESSAGE_LOGIN_UNIQUE = "This login already exist";
    private static final String ERROR_MESSAGE_PASSWORD = "Must have A-Za-z0-9@#$%^&+= at least 8 symbols without whitespace";
    private static final String ERROR_MESSAGE_EMAIL = "Incorrect email";
    private static final String ERROR_MESSAGE_EMAIL_UNIQUE = "This email already exist";

    @NotNull
    @Size(min = 3, max = 35)
    @Pattern(message = ERROR_MESSAGE_FIRSTNAME, regexp = "(?iu)[a-zа-яэёъы]+")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 35)
    @Pattern(message = ERROR_MESSAGE_LASTNAME, regexp = "(?iu)[a-zа-яэёъы]+")
    private String lastName;

    @NotNull
    @Size(min = 3, max = 35)
    @Pattern(message = ERROR_MESSAGE_LOGIN, regexp = "(?i)[a-z0-9]+")
    @UniqueLogin(message = ERROR_MESSAGE_LOGIN_UNIQUE)
    private String login;

    @Size(min = 8, max = 35)
    @Pattern(message = ERROR_MESSAGE_PASSWORD,
            regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")
    private String password;

    @Size(min = 1, max = 50)
    @Email(message = ERROR_MESSAGE_EMAIL)
    @UniqueEmail(message = ERROR_MESSAGE_EMAIL_UNIQUE)
    private String email;
}
