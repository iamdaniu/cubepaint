package de.daniu.gui;

import de.daniu.SelectedColorService;
import de.daniu.domain.CubeColor;
import de.daniu.io.FileIo;

import javax.swing.*;
import java.awt.*;

import static de.daniu.CubeManager.CUBE_MANAGER;
import static de.daniu.gui.ColorMapper.COLOR_MAPPER;
import static de.daniu.io.CubeEncoder.ENCODER;

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
        JPanel buttonContainer = new JPanel(new FlowLayout());
        commandContainer.add(displayButton, BorderLayout.NORTH);
        commandContainer.add(display, BorderLayout.CENTER);
        commandContainer.add(buttonContainer, BorderLayout.SOUTH);
        buttonContainer.add(loadButton);
        buttonContainer.add(saveButton);
        frame.getContentPane().add(commandContainer, BorderLayout.SOUTH);
        displayButton.addActionListener(e -> display.setText(ENCODER.encode(CUBE_MANAGER.getSelectedCube())));
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveButton.addActionListener(e -> {
            if (chooser.showSaveDialog(commandContainer) == JFileChooser.APPROVE_OPTION) {
                FileIo.INSTANCE.saveToFile(chooser.getSelectedFile());
            }
        });
        loadButton.addActionListener(e -> {
            if (chooser.showOpenDialog(commandContainer) == JFileChooser.APPROVE_OPTION) {
                FileIo.INSTANCE.loadFromFile(chooser.getSelectedFile());
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
