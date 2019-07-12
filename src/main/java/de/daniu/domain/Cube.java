package de.daniu.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class Cube {
    private final CubeFace up = CubeFace.filled(CubeColor.YELLOW);
    private final CubeFace left = CubeFace.filled(CubeColor.GREEN);
    private final CubeFace right = CubeFace.filled(CubeColor.BLUE);
    private final CubeFace back = CubeFace.filled(CubeColor.RED);
    private final CubeFace front = CubeFace.filled(CubeColor.ORANGE);
    private final CubeFace down = CubeFace.filled(CubeColor.WHITE);

    private static final List<Function<Cube, CubeFace>> GET_FACES =
        Arrays.asList(Cube::getUp, Cube::getLeft, Cube::getRight, Cube::getFront, Cube::getBack);

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
        return Stream.of(up, right, front, down, left, back);
    }

    public CubeFace getUp() {
        return up;
    }

    public CubeFace getLeft() {
        return left;
    }

    public CubeFace getRight() {
        return right;
    }

    public CubeFace getBack() {
        return back;
    }

    public CubeFace getFront() {
        return front;
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
        return Objects.hash(up, left, right, back, front, down);
    }
}
