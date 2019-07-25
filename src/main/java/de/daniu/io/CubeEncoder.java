package de.daniu.io;

import de.daniu.domain.Cube;
import de.daniu.domain.CubeColor;
import de.daniu.domain.CubeFace;
import de.daniu.domain.Faces;

import java.util.List;

import static de.daniu.io.ColorEncoder.COLOR_ENCODER;
import static java.util.stream.Collectors.joining;

public enum CubeEncoder {
    ENCODER;

    private static final List<Faces> FACE_ORDER = List.of(Faces.UP, Faces.RIGHT, Faces.FRONT, Faces.DOWN, Faces.LEFT, Faces.BACK);

    public String encode(Cube c) {
        return FACE_ORDER.stream()
                         .map(c::getFace)
                         .flatMap(CubeFace::colors)
                         .map(COLOR_ENCODER::encode)
                         .map(Object::toString)
                         .collect(joining(""));
    }

    public Cube decode(String encoded) {
        Cube cube = new Cube();
        for (int j = 0; j < 6; j++) {
            CubeFace face = cube.getFace(FACE_ORDER.get(j));
            String encodedFace = encoded.substring(j * 9, j * 9 + 9);
            for (int i = 0; i < encodedFace.length(); i++) {
                CubeColor color = COLOR_ENCODER.decode(encodedFace.charAt(i));
                face.setColor(i, color);
            }
        }
        return cube;
    }
}
