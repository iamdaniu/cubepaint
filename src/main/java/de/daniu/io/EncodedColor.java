package de.daniu.io;

import java.util.stream.Stream;

public enum EncodedColor {
    YELLOW("y"),
    WHITE("w"),
    GREEN("g"),
    BLUE("b"),
    RED("r"),
    ORANGE("o"),

    BLACK("n"),
    TRANSPARENT("t");

    private final String encoding;

    EncodedColor(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    public static EncodedColor fromEncoding(String encoding) {
        return Stream.of(EncodedColor.values())
                .filter(c -> encoding.equals(c.encoding))
                .findAny()
                .orElseThrow();
    }
}
