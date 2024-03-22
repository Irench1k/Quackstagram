package quackstagram.views.postlogin.commands;

import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.NavigationCommand;

public class OpenNotificationUICommand implements NavigationCommand {
    private AbstractPostLogin ui;

    public OpenNotificationUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.notificationsUI();
    }
}
