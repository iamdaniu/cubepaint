package de.daniu.gui;

import de.daniu.domain.Cube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.Vector;

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
        JButton addButton = new JButton("add");
        ActionListener addListener = e -> {
            CUBE_MANAGER.addCube(textField.getText());
            CUBE_MANAGER.selectCube(textField.getText());
        };
        addButton.addActionListener(addListener);
        nameContainer.add(addButton);
        JButton copyButton = new JButton("copy");
        copyButton.addActionListener(e -> {
            Cube current = CUBE_MANAGER.getSelectedCube();
            CUBE_MANAGER.addCube(textField.getText());
            CUBE_MANAGER.getCube(textField.getText()).copyFrom(current);
            CUBE_MANAGER.selectCube(textField.getText());
        });
        nameContainer.add(copyButton);
        container.add(nameContainer, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel();
        Vector<String> cubeNameList = new Vector<>(CUBE_MANAGER.getCubenames());

        DefaultComboBoxModel<String> nameModel = new DefaultComboBoxModel<>(cubeNameList);
        JComboBox<String> cubeList = new JComboBox<>(nameModel);
        CUBE_MANAGER.addAddListener(e -> {
            if (!cubeNameList.contains(e)) {
                nameModel.addElement(e);
            }
        });
        CUBE_MANAGER.addClearListener(nameModel::removeAllElements);
        CUBE_MANAGER.addSelectionListener(nameModel::setSelectedItem);
        actionPanel.add(cubeList);
        cubeList.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                CUBE_MANAGER.selectCube(l.getItem().toString());
            }
        });
        JButton resetButton = new JButton("reset");
        actionPanel.add(resetButton);
        resetButton.addActionListener(e -> CUBE_MANAGER.addCube(CUBE_MANAGER.getSelectedCubename()));
        container.add(actionPanel, BorderLayout.CENTER);

        return frame;
    }
}
