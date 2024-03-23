package quackstagram.controllers.moderator;

import quackstagram.FileHandler;
import quackstagram.models.ModeratorModel;
import quackstagram.models.Picture;
import quackstagram.models.User;
import quackstagram.views.moderator.ModeratorView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModeratorController {
    private ModeratorModel model;
    private ModeratorView view;

    public ModeratorController(ModeratorModel model, ModeratorView view) {
        this.model = model;
        this.view = view;
        view.setOnUserClickListener(this::onUserClicked);
        view.setOnAttributeClickListener(this::onAttributeClicked);
        updateView();
    }

    private void onUserClicked(String username) {
        User user = model.getUserByUsername(username);
        if (user != null) {
            List<String> userDetails = new ArrayList<>();
            userDetails.add("Username: " + user.getUsername());
            userDetails.add("Bio: " + user.getBio());
            // Add other details as needed

            List<Picture> pictures = model.getPicturesForUser(username);
            for (int i = 0; i < pictures.size(); i++) {
                Picture pic = pictures.get(i);
                String pictureText = "Picture " + (i + 1) + ": " + pic.getPictureID() + " - " + pic.getCaption();
                userDetails.add(pictureText);
            }

            view.setUserDetails(userDetails);
        }
    }

    private void onAttributeClicked(String attributeName) {
        // Implement the delete logic here
        System.out.println("Logic to delete " + attributeName + " to be implemented.");

        // If needed, you can call additional methods to handle the deletion from the model.
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
