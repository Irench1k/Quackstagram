package quackstagram.views;

import java.awt.Color;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

// Singleton Design Pattern for theme change
public class Theme {
    private static Theme theme = null;
    private ThemeName currentTheme = ThemeName.DARK;
    private final EnumMap<ThemeName, EnumMap<ColorID, Color>> themeDefinitions = new EnumMap<>(Map.ofEntries(
        Map.entry(ThemeName.LIGHT, new EnumMap<>(Map.ofEntries(
            Map.entry(ColorID.BACKGROUND_HEADER, new Color(51, 51, 51)),
            Map.entry(ColorID.BACKGROUND_HEADER_SECONDARY, Color.GRAY),
            Map.entry(ColorID.MAIN_BACKGROUND, new Color(249, 249, 249)),
            Map.entry(ColorID.LIKE_BUTTON, new Color(255, 90, 95)),
            Map.entry(ColorID.PRIMARY_lOGIN_BUTTON, new Color(255, 90, 95)),
            Map.entry(ColorID.BACKGROUND_SPACING, new Color(230, 230, 230)),
            Map.entry(ColorID.FOLLOW_BUTTON, new Color(225, 228, 232)),
            Map.entry(ColorID.TEXT_PRIMARY, Color.BLACK),
            Map.entry(ColorID.TEXT_SECONDARY, Color.GRAY),
            Map.entry(ColorID.OPPOSITE_TEXT, Color.WHITE)
        ))),
        Map.entry(ThemeName.DARK, new EnumMap<>(Map.ofEntries(
            Map.entry(ColorID.BACKGROUND_HEADER, new Color(204, 204, 204)),
            Map.entry(ColorID.BACKGROUND_HEADER_SECONDARY, Color.GRAY),
            Map.entry(ColorID.MAIN_BACKGROUND, new Color(40, 44, 52)),
            Map.entry(ColorID.LIKE_BUTTON, new Color(0, 165, 160)),
            Map.entry(ColorID.PRIMARY_lOGIN_BUTTON, new Color(0, 165, 160)),
            Map.entry(ColorID.BACKGROUND_SPACING, new Color(35, 35, 35)),
            Map.entry(ColorID.FOLLOW_BUTTON, new Color(30, 27, 23)),
            Map.entry(ColorID.TEXT_PRIMARY, Color.WHITE),
            Map.entry(ColorID.TEXT_SECONDARY, new Color(131,138,155)),
            Map.entry(ColorID.OPPOSITE_TEXT, Color.BLACK)
        )))
    ));

    private final EnumMap<ThemeName, EnumMap<IconID, String>> iconDefinitions = new EnumMap<>(Map.ofEntries(
        Map.entry(ThemeName.LIGHT, new EnumMap<>(Map.ofEntries(
            Map.entry(IconID.ADD, "img/icons/light/add.png"),
            Map.entry(IconID.HEART, "img/icons/light/heart.png"),
            Map.entry(IconID.HOME, "img/icons/light/home.png"),
            Map.entry(IconID.PROFILE, "img/icons/light/profile.png"),
            Map.entry(IconID.SEARCH, "img/icons/light/search.png")
        ))),
        Map.entry(ThemeName.DARK, new EnumMap<>(Map.ofEntries(
            Map.entry(IconID.ADD, "img/icons/dark/add.png"),
            Map.entry(IconID.HEART, "img/icons/dark/heart.png"),
            Map.entry(IconID.HOME, "img/icons/dark/home.png"),
            Map.entry(IconID.PROFILE, "img/icons/dark/profile.png"),
            Map.entry(IconID.SEARCH, "img/icons/dark/search.png")
        )))
    ));

    private Theme() {}

    enum ThemeName {
        DARK,
        LIGHT
    }

    // Theme theme = Theme.getInstance();
    public static Theme getInstance() {
        if (theme == null) {
            theme = new Theme();
        }
        System.out.println("Theme.getInstance returning: " + theme);
        return theme;
    }

    public void changeTheme(ThemeName name) {
        currentTheme = name;
    }

    // Color likeColor = theme.getColor(Theme.ColorComponent.LIKE_BUTTON);
    public Color getColor(ColorID id) {
        System.out.println("getColor: " + id.toString());
        return themeDefinitions.get(currentTheme).get(id);
    }

    public String getIconPath(IconID id) {
        return iconDefinitions.get(currentTheme).get(id);
    }

}
