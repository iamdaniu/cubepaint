package de.daniu.gui;

import de.daniu.SelectedColorService;
import de.daniu.domain.Cube;
import de.daniu.domain.CubeColor;
import de.daniu.domain.CubeFace;
import de.daniu.domain.Faces;

import javax.swing.*;
import java.awt.*;

import static de.daniu.CubeManager.CUBE_MANAGER;
import static de.daniu.gui.ColorMapper.COLOR_MAPPER;

class CubeButtonLayouter {

    private static final int SIDE_BUTTON_COUNT = 3;
    private static final int FULL = 60;
    private static final int HALF = FULL/2;

    static void toComponent(Container component) {
        component.setLayout(new BorderLayout());
        component.add(leftFaceButtons(), BorderLayout.WEST);
        component.add(topFaceButtons(), BorderLayout.CENTER);
        component.add(rightFaceButtons(), BorderLayout.EAST);
        component.add(frontFaceButtons(), BorderLayout.SOUTH);
        component.add(backFaceButtons(), BorderLayout.NORTH);
    }

    private static JComponent topFaceButtons() {
        return buttons(Faces.UP, new Dimension(FULL, FULL), new GridLayout(3, 3), 9);
    }

    private static JComponent leftFaceButtons() {
        return buttons(Faces.LEFT, leftRightSize(), leftRightLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent rightFaceButtons() {
        return inverseButtons(Faces.RIGHT, leftRightSize(), leftRightLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent frontFaceButtons() {
        return buttons(Faces.FRONT, frontBackSize(), frontBackLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent backFaceButtons() {
        return inverseButtons(Faces.BACK, frontBackSize(), frontBackLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent buttons(Faces face, Dimension buttonSize, LayoutManager manager, int buttonCount) {
        JComponent component = new JPanel(manager);
        for (int i = 0; i < buttonCount; i++) {
            component.add(colorButton(face, i, buttonSize));
        }
        return component;
    }

    private static JComponent inverseButtons(Faces face, Dimension buttonSize, LayoutManager manager, int buttonCount) {
        JComponent component = new JPanel(manager);
        // will only work for side button panel...
        for (int i = 2; i >= 0; i--) {
            component.add(colorButton(face, i, buttonSize));
        }
        return component;
    }

    private static Dimension leftRightSize() {
        return new Dimension(HALF, FULL);
    }

    private static Dimension frontBackSize() {
        return new Dimension(FULL, HALF);
    }

    private static LayoutManager leftRightLayout() {
        return new GridLayout(3, 1);
    }

    private static LayoutManager frontBackLayout() {
        return new GridLayout(1, 3);
    }

    private static JButton colorButton(Faces faces, int index, Dimension dimension) {
        JButton button = new JButton();
        button.setPreferredSize(dimension);
        button.addActionListener(e -> {
            CubeColor color = SelectedColorService.SELECTED_COLORS.getSelected();
            CubeFace face = CUBE_MANAGER.getSelectedCube().getFace(faces);
            face.setColor(index, color);
            button.setBackground(getColor(faces, index));
        });
        button.setBackground(getColor(faces, index));
        CUBE_MANAGER.addSelectionListener(n -> {
            Cube c = CUBE_MANAGER.getSelectedCube();
            button.setBackground(getColor(faces, index));
        });
        return button;
    }

    private static Color getColor(Faces face, int index) {
        return COLOR_MAPPER.getColor(CUBE_MANAGER.getSelectedCube().getFace(face).getColor(index));
    }
}
