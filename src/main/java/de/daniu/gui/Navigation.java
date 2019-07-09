package de.daniu.gui;

import de.daniu.domain.Cube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

import static de.daniu.CubeManager.CUBE_MANAGER;

public class Navigation {
    public static JFrame navigationFrame() {
        JFrame frame = new JFrame("navigation");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        Container nameContainer = new JPanel();
        JTextField textField = new JTextField(CUBE_MANAGER.getSelectedCubename(), 20);
        CUBE_MANAGER.addSelectionListener(textField::setText);
        nameContainer.add(textField);
        JButton addButton = new JButton("store");
        addButton.addActionListener(e -> {
            CUBE_MANAGER.addCube(textField.getText(), Cube.copy(CUBE_MANAGER.getSelectedCube()));
            CUBE_MANAGER.selectCube(textField.getText());
        });
        nameContainer.add(addButton);
        container.add(nameContainer, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel();
        DefaultComboBoxModel<String> nameModel = new DefaultComboBoxModel<>(CUBE_MANAGER.getCubenames().toArray(String[]::new));
        JComboBox<String> cubeList = new JComboBox<>(nameModel);
        CUBE_MANAGER.addAddListener(nameModel::addElement);
        CUBE_MANAGER.addSelectionListener(nameModel::setSelectedItem);
        actionPanel.add(cubeList);
        cubeList.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                CUBE_MANAGER.selectCube(l.getItem().toString());
            }
        });
        JButton resetButton = new JButton("reset");
        actionPanel.add(resetButton);
        resetButton.addActionListener(e -> CUBE_MANAGER.addCube(CUBE_MANAGER.getSelectedCubename(), new Cube()));
        container.add(actionPanel, BorderLayout.CENTER);

        return frame;
    }
}
