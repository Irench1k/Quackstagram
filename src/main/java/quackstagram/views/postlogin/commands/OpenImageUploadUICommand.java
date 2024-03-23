package quackstagram.views.postlogin.commands;

import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.NavigationCommand;
import quackstagram.views.postlogin.QuakstagramHomeUI;

public class OpenImageUploadUICommand implements NavigationCommand {
    private AbstractPostLogin ui;

    public OpenImageUploadUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.imageUploadUI();
    }
}