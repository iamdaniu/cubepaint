package de.daniu.gui;

import de.daniu.CubeManager;
import de.daniu.SelectedColorService;
import de.daniu.domain.Cube;
import de.daniu.domain.CubeColor;
import de.daniu.domain.CubeFace;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

import static de.daniu.gui.ColorMapper.COLOR_MAPPER;

class CubeButtonLayouter {

    private static final int SIDE_BUTTON_COUNT = 3;

    static void toComponent(Container component) {
        component.setLayout(new BorderLayout());
        component.add(leftFaceButtons(), BorderLayout.WEST);
        component.add(topFaceButtons(), BorderLayout.CENTER);
        component.add(rightFaceButtons(), BorderLayout.EAST);
        component.add(frontFaceButtons(), BorderLayout.SOUTH);
        component.add(backFaceButtons(), BorderLayout.NORTH);
    }

    private static JComponent topFaceButtons() {
        return buttons(Cube::getUp, new Dimension(40, 40), new GridLayout(3, 3), 9);
    }

    private static JComponent leftFaceButtons() {
        return buttons(Cube::getLeft, leftRightSize(), leftRightLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent rightFaceButtons() {
        return inverseButtons(Cube::getRight, leftRightSize(), leftRightLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent frontFaceButtons() {
        return buttons(Cube::getFront, frontBackSize(), frontBackLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent backFaceButtons() {
        return inverseButtons(Cube::getBack, frontBackSize(), frontBackLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent buttons(Function<Cube, CubeFace> getFace, Dimension buttonSize, LayoutManager manager, int buttonCount) {
        JComponent component = new JPanel(manager);
        for (int i = 0; i < buttonCount; i++) {
            component.add(colorButton(getFace, i, buttonSize));
        }
        return component;
    }

    private static JComponent inverseButtons(Function<Cube, CubeFace> getFace, Dimension buttonSize, LayoutManager manager, int buttonCount) {
        JComponent component = new JPanel(manager);
        // will only work for side button panel...
        for (int i = 2; i >= 0; i--) {
            component.add(colorButton(getFace, i, buttonSize));
        }
        return component;
    }

    private static Dimension leftRightSize() {
        return new Dimension(20, 40);
    }

    private static Dimension frontBackSize() {
        return new Dimension(40, 20);
    }

    private static LayoutManager leftRightLayout() {
        return new GridLayout(3, 1);
    }

    private static LayoutManager frontBackLayout() {
        return new GridLayout(1, 3);
    }

    private static JButton colorButton(Function<Cube, CubeFace> getFace, int index, Dimension dimension) {
        JButton button = new JButton();
        button.setPreferredSize(dimension);
        button.addActionListener(e -> {
            CubeColor color = SelectedColorService.SELECTED_COLORS.getSelected();
            CubeFace face = getFace.apply(CubeManager.CUBE_MANAGER.getSelectedCube());
            face.setColor(index, color);
            button.setBackground(COLOR_MAPPER.getColor(face.getColor(index)));
        });
        button.setBackground(COLOR_MAPPER.getColor(getFace.apply(CubeManager.CUBE_MANAGER.getSelectedCube()).getColor(index)));
        return button;
    }
}
