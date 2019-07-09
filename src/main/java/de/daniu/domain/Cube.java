package de.daniu.domain;

import java.util.stream.Stream;

public class Cube {
    private CubeFace up = CubeFace.filled(CubeColor.YELLOW);
    private CubeFace left = CubeFace.filled(CubeColor.GREEN);
    private CubeFace right = CubeFace.filled(CubeColor.BLUE);
    private CubeFace back = CubeFace.filled(CubeColor.RED);
    private CubeFace front = CubeFace.filled(CubeColor.ORANGE);
    private CubeFace down = CubeFace.filled(CubeColor.WHITE);

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
}
