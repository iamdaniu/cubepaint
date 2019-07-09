package de.daniu.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CubeFace {
    private List<CubeColor> facelets = new ArrayList<>();

    private CubeFace() {}

    public Stream<CubeColor> colors() {
        return facelets.stream();
    }

    public void setColor(int i, CubeColor c) {
        facelets.set(i, c);
    }
    public CubeColor getColor(int i) {
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
