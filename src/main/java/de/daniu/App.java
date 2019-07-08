package de.daniu;

import javax.swing.*;
import java.awt.*;

import static java.util.stream.Collectors.joining;

/**
 *
 */
public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("painter");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Cube cube = CubeManager.INSTANCE.getCube();

        frame.getContentPane().setLayout(new BorderLayout());
        JComponent buttonContainer = new JPanel();
        frame.getContentPane().add(buttonContainer, BorderLayout.CENTER);
        JComponent paletteContainer = new JPanel();
        frame.getContentPane().add(paletteContainer, BorderLayout.NORTH);
        JComponent commandContainer = new JPanel(new BorderLayout());
        layoutCommandContainer(frame, cube, commandContainer);

        CubeButtonLayouter.toComponent(cube, buttonContainer);
        layoutPalette(paletteContainer);

        frame.pack();
        frame.setVisible(true);
    }

    private static void layoutCommandContainer(JFrame frame, Cube cube, JComponent commandContainer) {
        JButton okButton = new JButton("ok");
        JTextField display = new JTextField("");
        commandContainer.add(okButton, BorderLayout.NORTH);
        commandContainer.add(display, BorderLayout.SOUTH);
        frame.getContentPane().add(commandContainer, BorderLayout.SOUTH);
        okButton.addActionListener(e -> {
            String query = cube.getFaces().flatMap(CubeFace::colors).map(CubeColor::getQuery).collect(joining(""));
            display.setText(query);
        });
    }

    private static void layoutPalette(JComponent paletteContainer) {
        paletteContainer.setLayout(new FlowLayout());
        for (CubeColor cubeColor : CubeColor.values()) {
            JButton selectButton = new JButton();
            selectButton.setBackground(cubeColor.getColor());
            selectButton.setPreferredSize(new Dimension(30, 30));
            selectButton.addActionListener(e -> SelectedColorService.INSTANCE.setSelected(cubeColor));
            paletteContainer.add(selectButton);
        }
    }
}
