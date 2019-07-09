package de.daniu;

import de.daniu.domain.Cube;
import de.daniu.gui.Frames;

import javax.swing.*;

/**
 *
 */
public class App {
    public static void main(String[] args) {
        Cube cube = new Cube();
        CubeManager.CUBE_MANAGER.addCube("default", cube);

        JFrame frame = Frames.mainFrame(cube);
        frame.pack();
        frame.setVisible(true);

        JFrame paletteFrame = Frames.paletteFrame();
        paletteFrame.pack();
        paletteFrame.setVisible(true);
    }
}
