package quackstagram.controllers.moderator;

import quackstagram.FileHandler;
import quackstagram.models.ModeratorModel;
import quackstagram.models.Picture;
import quackstagram.models.User;
import quackstagram.views.moderator.ModeratorView;

import java.util.List;
import java.util.stream.Collectors;

public class ModeratorController {
    private ModeratorModel model;
    private ModeratorView view;

    public ModeratorController(ModeratorModel model, ModeratorView view) {
        this.model = model;
        this.view = view;
        view.setOnUserClickListener(this::onUserClicked);
        view.setOnBioDeleteListener(username -> {
            try {
                FileHandler.deleteUserBio(username);
                onUserClicked(username); // Refresh user details display
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        updateView();
    }

    private void onUserClicked(String username) {
        User user = model.getUserByUsername(username);
        if (user != null) {
            List<Picture> pictures = model.getPicturesForUser(username);
            String userDetails = "Username: " + user.getUsername() +
                    "\nBio: " + user.getBio() +
                    "\n\nPictures:\n" + pictures.stream()
                    .map(pic -> pic.getPictureID() + " - " + pic.getCaption())
                    .collect(Collectors.joining("\n"));
            view.setUserDetails(userDetails);
        }
    }

    private void updateView() {
        List<String> usernames = model.getUserList().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        view.displayUserList(usernames);
    }

    public void showView() {
        view.setVisible(true);
    }
}
