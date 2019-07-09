package de.daniu.gui;

import de.daniu.SelectedColorService;
import de.daniu.domain.Cube;
import de.daniu.domain.CubeColor;
import de.daniu.domain.CubeFace;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

import static de.daniu.CubeManager.CUBE_MANAGER;
import static de.daniu.gui.ColorMapper.COLOR_MAPPER;
import static java.util.stream.Collectors.joining;

public class Frames {
    public static JFrame mainFrame() {
        JFrame frame = new JFrame("default");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CUBE_MANAGER.addSelectionListener(frame::setTitle);

        frame.getContentPane().setLayout(new BorderLayout());
        JComponent cubeContainer = new JPanel();
        frame.getContentPane().add(cubeContainer, BorderLayout.CENTER);
        JComponent commandContainer = new JPanel(new BorderLayout());
        layoutCommandContainer(frame, commandContainer);

        CubeButtonLayouter.toComponent(cubeContainer);
        return frame;
    }

    public static JFrame paletteFrame() {
        JFrame frame = new JFrame("palette");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JComponent paletteContainer = new JPanel();
        frame.getContentPane().add(paletteContainer, BorderLayout.CENTER);
        layoutPalette(paletteContainer);
        return frame;
    }

    private static void layoutCommandContainer(JFrame frame, JComponent commandContainer) {
        JButton displayButton = new JButton("show");
        JTextField display = new JTextField("");
        JButton saveButton = new JButton("save");
        commandContainer.add(displayButton, BorderLayout.NORTH);
        commandContainer.add(display, BorderLayout.CENTER);
        commandContainer.add(saveButton, BorderLayout.SOUTH);
        frame.getContentPane().add(commandContainer, BorderLayout.SOUTH);
        Function<Cube, String> cubeEncoder = c -> c.getFaces().flatMap(CubeFace::colors).map(CubeColor::getQuery).collect(joining(""));
        displayButton.addActionListener(e -> display.setText(cubeEncoder.apply(CUBE_MANAGER.getSelectedCube())));
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveButton.addActionListener(e -> {
            if (chooser.showSaveDialog(commandContainer) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(new FileWriter(selectedFile, true))) {
                    CUBE_MANAGER.getCubenames().stream()
                                .filter(s -> !"default".equals(s))
                                .map(n -> String.format("%s: %s", n, cubeEncoder.apply(CUBE_MANAGER.getCube(n))))
                                .forEach(writer::println);
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
            selectButton.setBackground(COLOR_MAPPER.getColor(cubeColor));
            selectButton.setPreferredSize(new Dimension(30, 30));
            selectButton.addActionListener(e -> SelectedColorService.SELECTED_COLORS.setSelected(cubeColor));
            paletteContainer.add(selectButton);
        }
    }
}
