package de.daniu.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cube {
    private final Map<Faces, CubeFace> faces = new EnumMap<>(Faces.class);

    {
        faces.put(Faces.UP, CubeFace.filled(CubeColor.YELLOW));
        faces.put(Faces.LEFT, CubeFace.filled(CubeColor.GREEN));
        faces.put(Faces.RIGHT, CubeFace.filled(CubeColor.BLUE));
        faces.put(Faces.BACK, CubeFace.filled(CubeColor.RED));
        faces.put(Faces.FRONT, CubeFace.filled(CubeColor.ORANGE));
        faces.put(Faces.DOWN, CubeFace.filled(CubeColor.WHITE));
    }

    private static final List<Function<Cube, CubeFace>> GET_FACES =
        Stream.of(Faces.values())
              .map(f -> (Function<Cube, CubeFace>) c -> c.getFace(f))
              .collect(Collectors.toList());

    public void copyFrom(Cube c) {
        GET_FACES.forEach(f -> {
            CubeFace source = f.apply(c);
            CubeFace target = f.apply(this);
            for (int i = 0; i < 9; i++) {
                target.setColor(i, source.getColor(i));
            }
        });
    }

    public Stream<CubeFace> getFaces() {
        return Stream.of(Faces.values()).map(faces::get);
    }

    public CubeFace getFace(Faces face) {
        return faces.get(face);
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Cube) {
            Cube other = (Cube) obj;
            result = GET_FACES.stream()
                              .allMatch(f -> f.apply(this).equals(f.apply(other)));
        }
        return result;
    }

    @Override
    public int hashCode() {
        return faces.hashCode();
    }
}
