package de.daniu.io;

import de.daniu.domain.CubeColor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static de.daniu.domain.CubeColor.*;

public enum ColorEncoder {
    COLOR_ENCODER;

    private final Map<CubeColor, Character> encodeMap = new EnumMap<>(CubeColor.class);
    private final Map<Character, CubeColor> decodeMap = new HashMap<>();

    ColorEncoder() {
        register(YELLOW, 'y');
        register(WHITE, 'w');
        register(GREEN, 'g');
        register(BLUE, 'b');
        register(RED, 'r');
        register(ORANGE, 'o');

        register(BLACK, 'n');
        register(TRANSPARENT, 't');
    }

    private void register(CubeColor color, Character encoded) {
        encodeMap.put(color, encoded);
        decodeMap.put(encoded, color);
    }

    public Character encode(CubeColor cubeColor) {
        return encodeMap.get(cubeColor);
    }

    public CubeColor decode(Character encoded) { return decodeMap.get(encoded); }
}
