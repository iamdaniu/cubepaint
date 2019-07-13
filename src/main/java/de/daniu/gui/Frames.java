package de.daniu.gui;

import de.daniu.SelectedColorService;
import de.daniu.domain.Cube;
import de.daniu.domain.CubeColor;
import de.daniu.domain.CubeFace;
import de.daniu.io.Codecs;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        commandContainer.setLayout(new BorderLayout());
        JButton displayButton = new JButton("show");
        JTextField display = new JTextField("");
        JPanel ioPanel = new JPanel();
        JButton saveButton = new JButton("save");
        JButton loadButton = new JButton("load");
        commandContainer.add(displayButton, BorderLayout.NORTH);
        commandContainer.add(display, BorderLayout.CENTER);
        ioPanel.add(saveButton);
        ioPanel.add(loadButton);
        commandContainer.add(ioPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(commandContainer, BorderLayout.SOUTH);
        Function<Cube, String> cubeEncoder = Codecs.CubeCodec.INSTANCE::encode;
        displayButton.addActionListener(e -> display.setText(cubeEncoder.apply(CUBE_MANAGER.getSelectedCube())));
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveButton.addActionListener(e -> {
            if (chooser.showSaveDialog(commandContainer) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(new FileWriter(selectedFile, true))) {
                    CUBE_MANAGER.getCubenames().stream()
                                .filter(s -> !"default".equals(s))
                                .map(n -> String.format("%s: %s", n, Codecs.CubeCodec.INSTANCE.encode(CUBE_MANAGER.getCube(n))))
                                .forEach(writer::println);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        loadButton.addActionListener(e -> {
            if (chooser.showOpenDialog(commandContainer) == JFileChooser.APPROVE_OPTION) {
                CUBE_MANAGER.clear();
                File selectedFile = chooser.getSelectedFile();
                try {
                    Files.lines(Paths.get(selectedFile.getPath()))
                            .forEach(s -> {
                                String[] split = s.split(":");
                                Cube cube = Codecs.CubeCodec.INSTANCE.decode(split[1].strip());
                                CUBE_MANAGER.addCube(split[0].strip(), cube);
                            });
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
