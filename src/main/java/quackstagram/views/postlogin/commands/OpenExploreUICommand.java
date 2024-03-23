package quackstagram.views.postlogin.commands;

import quackstagram.views.postlogin.AbstractPostLogin;
import quackstagram.views.postlogin.NavigationCommand;
import quackstagram.views.postlogin.QuakstagramHomeUI;

public class OpenExploreUICommand implements NavigationCommand {
    private AbstractPostLogin ui;

    public OpenExploreUICommand(AbstractPostLogin ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.exploreUI();
    }
}