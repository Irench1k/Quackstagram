package quackstagram.controllers.prelogin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import quackstagram.FileHandler;
import quackstagram.models.User;
import quackstagram.views.prelogin.SignInUI;
import quackstagram.views.prelogin.SignUpUI;

public class SignUpControllerDecorator extends SignUpController {
    SignUpController signUpController;

    public SignUpControllerDecorator(SignUpController signUpController) {
        super(signUpController.view);
        this.signUpController = signUpController;
    }
}
