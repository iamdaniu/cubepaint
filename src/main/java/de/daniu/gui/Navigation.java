package de.daniu.gui;

import de.daniu.domain.Cube;

import javax.swing.*;
import java.awt.*;

import static de.daniu.CubeManager.CUBE_MANAGER;

public class Navigation {
    public static JFrame navigationFrame() {
        JFrame frame = new JFrame("navigation");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        Container nameContainer = new JPanel();
        JTextField textField = new JTextField(CUBE_MANAGER.getSelectedCubename(), 20);
        CUBE_MANAGER.addListener(textField::setText);
        nameContainer.add(textField);
        JButton addButton = new JButton("store");
        addButton.addActionListener(e -> CUBE_MANAGER.addCube(textField.getText(), Cube.copy(CUBE_MANAGER.getSelectedCube())));
        nameContainer.add(addButton);
        container.add(nameContainer, BorderLayout.NORTH);

        return frame;
    }
}
