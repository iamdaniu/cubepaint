package de.daniu;

import java.awt.*;

enum CubeColor {
    YELLOW(Color.YELLOW, "y"),
    WHITE(Color.WHITE, "w"),
    GREEN(Color.GREEN, "g"),
    BLUE(Color.BLUE, "b"),
    RED(Color.RED, "r"),
    ORANGE(Color.ORANGE, "o"),

    BLACK(Color.BLACK, "n"),
    TRANSPARENT(Color.GRAY, "t");

    private final Color color;
    private final String query;

    CubeColor(Color color, String query) {
        this.color = color;
        this.query = query;
    }

    public Color getColor() {
        return color;
    }

    public String getQuery() {
        return query;
    }
}
