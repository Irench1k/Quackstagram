package quackstagram.views.postlogin.commands;

import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.NavigationCommand;

public class OpenInstagramProfileUICommand implements NavigationCommand {
    private AbstractPostLogin ui;

    public OpenInstagramProfileUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.openProfileUI();
    }
}
