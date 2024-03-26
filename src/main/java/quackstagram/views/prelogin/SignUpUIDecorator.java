package quackstagram.views.prelogin;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignUpUIDecorator extends SignUpUI {
    SignUpUI signUpUI;

    public SignUpUIDecorator(SignUpUI signUpUI) {
        super();
        this.signUpUI = signUpUI;
    }

    @Override
    protected JPanel createMainContentPanel() {
        JPanel panel = signUpUI.createMainContentPanel();
        JTextField twoFAField = new JTextField("Passcode");
        panel.add(twoFAField);
        return panel;
    }
}
