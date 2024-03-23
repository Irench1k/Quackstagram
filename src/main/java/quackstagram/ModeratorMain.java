package quackstagram;

import quackstagram.controllers.moderator.ModeratorController;
import quackstagram.models.ModeratorModel;
import quackstagram.views.moderator.ModeratorView;

public class ModeratorMain {
    public static void main(String[] args) {
        ModeratorModel model = new ModeratorModel();
        ModeratorView view = new ModeratorView();
        ModeratorController controller = new ModeratorController(model, view);
        controller.showView();
    }
}