package de.daniu.domain;

public enum CubeColor {
    YELLOW("y"),
    WHITE("w"),
    GREEN("g"),
    BLUE("b"),
    RED("r"),
    ORANGE("o"),

    BLACK("n"),
    TRANSPARENT("t");

    private final String query;

    CubeColor(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
