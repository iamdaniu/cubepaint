package de.daniu.gui;

import de.daniu.domain.CubeColor;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

enum ColorMapper {
    COLOR_MAPPER;

    private final Map<CubeColor, Color> colorMap = new EnumMap<>(CubeColor.class);

    ColorMapper() {
        colorMap.put(CubeColor.YELLOW, Color.YELLOW);
        colorMap.put(CubeColor.WHITE, Color.WHITE);
        colorMap.put(CubeColor.GREEN, Color.GREEN);
        colorMap.put(CubeColor.BLUE, Color.BLUE);
        colorMap.put(CubeColor.RED, Color.RED);
        colorMap.put(CubeColor.ORANGE, Color.ORANGE);

        colorMap.put(CubeColor.BLACK, Color.BLACK);
        colorMap.put(CubeColor.TRANSPARENT, Color.GRAY);
    }

    public Color getColor(CubeColor cubeColor) {
        return colorMap.get(cubeColor);
    }
}
