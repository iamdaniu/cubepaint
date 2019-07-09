package de.daniu;

import de.daniu.domain.Cube;
import de.daniu.gui.Frames;
import de.daniu.gui.Navigation;

import java.util.stream.Stream;

/**
 *
 */
public class App {
    public static void main(String[] args) {
        Cube cube = new Cube();
        CubeManager.CUBE_MANAGER.addCube("default", cube);

        Stream.of(
            Frames.mainFrame(),
            Frames.paletteFrame(),
            Navigation.navigationFrame()
        ).forEach(f -> {
             f.pack();
             f.setVisible(true);
         });
    }
}
