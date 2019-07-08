package de.daniu;

import javax.swing.*;
import java.awt.*;

class CubeButtonLayouter {

    private static final int SIDE_BUTTON_COUNT = 3;

    static void toComponent(Cube cube, Container component) {
        component.setLayout(new BorderLayout());
        component.add(leftFaceButtons(cube), BorderLayout.WEST);
        component.add(topFaceButtons(cube), BorderLayout.CENTER);
        component.add(rightFaceButtons(cube), BorderLayout.EAST);
        component.add(frontFaceButtons(cube), BorderLayout.SOUTH);
        component.add(backFaceButtons(cube), BorderLayout.NORTH);
    }

    private static JComponent topFaceButtons(Cube cube) {
        return buttons(cube.getUp(), new Dimension(40, 40), new GridLayout(3, 3), 9);
    }
    private static JComponent leftFaceButtons(Cube cube) {
        return buttons(cube.getLeft(), leftRightSize(), leftRightLayout(), SIDE_BUTTON_COUNT);
    }
    private static JComponent rightFaceButtons(Cube cube) {
        return buttons(cube.getRight(), leftRightSize(), leftRightLayout(), SIDE_BUTTON_COUNT);
    }
    private static JComponent frontFaceButtons(Cube cube) {
        return buttons(cube.getFront(), frontBackSize(), frontBackLayout(), SIDE_BUTTON_COUNT);
    }
    private static JComponent backFaceButtons(Cube cube) {
        return buttons(cube.getBack(), frontBackSize(), frontBackLayout(), SIDE_BUTTON_COUNT);
    }

    private static JComponent buttons(CubeFace face, Dimension buttonSize, LayoutManager manager, int buttonCount) {
        JComponent component = new JPanel(manager);
        for (int i = 0; i < buttonCount; i++) {
            component.add(colorButton(face, i, buttonSize));
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

    private static JButton colorButton(CubeFace face, int index, Dimension dimension) {
        JButton button = new JButton();
        button.setPreferredSize(dimension);
        button.addActionListener(e -> {
            CubeColor color = SelectedColorService.INSTANCE.getSelected();
            face.setColor(index, color);
            button.setBackground(color.getColor());
        });
        button.setBackground(face.getColor(index).getColor());
        return button;
    }

}
