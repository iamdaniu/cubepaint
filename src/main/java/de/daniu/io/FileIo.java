package de.daniu.io;

import de.daniu.domain.Cube;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.Stream;

import static de.daniu.CubeManager.CUBE_MANAGER;
import static de.daniu.io.CubeEncoder.ENCODER;

public enum FileIo {
    INSTANCE;

    public void loadFromFile(File file) {
        try (Stream<String> lines = Files.lines(file.toPath())) {
            lines.map(s -> s.split(": "))
                 .forEach(sa -> {
                     Cube decoded = ENCODER.decode(sa[1]);
                     CUBE_MANAGER.addCube(sa[0])
                                 .copyFrom(decoded);
                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            CUBE_MANAGER.getCubenames().stream()
                        .filter(s -> !"default".equals(s))
                        .map(n -> String.format("%s: %s", n, ENCODER.encode(CUBE_MANAGER.getCube(n))))
                        .forEach(writer::println);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
