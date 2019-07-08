package de.daniu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class CubeFace {
    private List<CubeColor> facelets = new ArrayList<>();

    private CubeFace() {}

    Stream<CubeColor> colors() {
        return facelets.stream();
    }

    void setColor(int i, CubeColor c) {
        facelets.set(i, c);
    }
    CubeColor getColor(int i) {
        return facelets.get(i);
    }

    static CubeFace filled(CubeColor c) {
        CubeFace f = new CubeFace();
        for (int i = 0; i < 9; i++) {
            f.facelets.add(c);
        }
        return f;
    }
}
