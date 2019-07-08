package de.daniu;

import java.util.stream.Stream;

class Cube {
    private CubeFace up = CubeFace.filled(CubeColor.YELLOW);
    private CubeFace left = CubeFace.filled(CubeColor.GREEN);
    private CubeFace right = CubeFace.filled(CubeColor.BLUE);
    private CubeFace back = CubeFace.filled(CubeColor.RED);
    private CubeFace front = CubeFace.filled(CubeColor.ORANGE);
    private CubeFace down = CubeFace.filled(CubeColor.WHITE);

    Stream<CubeFace> getFaces() {
        return Stream.of(up, right, front, down, left, back);
    }

    CubeFace getUp() {
        return up;
    }

    CubeFace getLeft() {
        return left;
    }

    CubeFace getRight() {
        return right;
    }

    CubeFace getBack() {
        return back;
    }

    CubeFace getFront() {
        return front;
    }
}
