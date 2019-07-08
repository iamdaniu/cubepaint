package de.daniu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Supplier;

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
        JButton okButton = new JButton("show");
        JTextField display = new JTextField("");
        JButton saveButton = new JButton("save");
        commandContainer.add(okButton, BorderLayout.NORTH);
        commandContainer.add(display, BorderLayout.CENTER);
        commandContainer.add(saveButton, BorderLayout.SOUTH);
        frame.getContentPane().add(commandContainer, BorderLayout.SOUTH);
        Supplier<String> cubeEncoder = () -> cube.getFaces().flatMap(CubeFace::colors).map(CubeColor::getQuery).collect(joining(""));
        okButton.addActionListener(e -> display.setText(cubeEncoder.get()));
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveButton.addActionListener(e -> {
            if (chooser.showSaveDialog(commandContainer) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(new FileWriter(selectedFile, true))) {
                    writer.println(cubeEncoder.get());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
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
