package quackstagram.views.postlogin.commands;

import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.NavigationCommand;

public class OpenHomeUICommand implements NavigationCommand {
    private AbstractPostLogin ui;

    public OpenHomeUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.openHomeUI();
    }
}