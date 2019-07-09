package de.daniu;

import de.daniu.domain.Cube;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum CubeManager {
    CUBE_MANAGER;

    private Map<String, Cube> cubes = new LinkedHashMap<>();
    private List<SelectionListener> listeners = new ArrayList<>();
    private String selectedCubename;

    public void selectCube(String name) {
        selectedCubename = name;
        listeners.forEach(l -> l.cubeSelected(selectedCubename));
    }
    public String getSelectedCubename() {
        return selectedCubename;
    }
    public Cube getSelectedCube() {
        return cubes.get(selectedCubename);
    }

    public void addCube(String cubename, Cube cube) {
        cubes.put(cubename, cube);
        if (selectedCubename == null) {
            selectCube(cubename);
        }
    }
    public List<String> getCubenames() {
        return new ArrayList<>(cubes.keySet());
    }
    public Cube getCube(String name) {
        return cubes.get(name);
    }

    public void addListener(SelectionListener listener) {
        listeners.add(listener);
    }

    public interface SelectionListener {
        void cubeSelected(String name);
    }
}
