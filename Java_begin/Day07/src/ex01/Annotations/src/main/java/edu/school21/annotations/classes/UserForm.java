package edu.school21.annotations;

import edu.school21.annotations.HtmlInput;
import edu.school21.annotations.HtmlForm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@HtmlForm(fileName = "user_form.html", action = "/users", method = "post")
public class UserForm {
    @HtmlInput(type = "text", name = "first_name", placeholder = "Enter First Name")
    private String firstName;

    @HtmlInput(type = "text", name = "last_name", placeholder = "Enter Last Name")
    private String lastName;

    @HtmlInput(type = "password", name = "password", placeholder = "Enter Password")
    private String password;
}