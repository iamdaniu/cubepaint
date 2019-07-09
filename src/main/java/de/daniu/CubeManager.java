package de.daniu;

import de.daniu.domain.Cube;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum CubeManager {
    CUBE_MANAGER;

    private Map<String, Cube> cubes = new LinkedHashMap<>();

    public List<String> getCubenames() {
        return new ArrayList<>(cubes.keySet());
    }

    public Cube getCube(String name) {
        return cubes.get(name);
    }

    public void addCube(String cubename, Cube cube) {
        cubes.put(cubename, cube);
    }
}
