package de.daniu;

public enum CubeManager {
    INSTANCE;

    private Cube cube = new Cube();

    public Cube getCube() {
        return cube;
    }
}
