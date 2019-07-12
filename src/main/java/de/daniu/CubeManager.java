package de.daniu;

import de.daniu.domain.Cube;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public enum CubeManager {
    CUBE_MANAGER;

    private Map<String, Cube> cubes = new LinkedHashMap<>();
    private List<CubeListener> selectionListeners = new ArrayList<>();
    private List<CubeListener> addListeners = new ArrayList<>();
    private String selectedCubename;

    public void selectCube(String name) {
        selectedCubename = name;
        selectionListeners.forEach(l -> l.accept(selectedCubename));
    }
    public String getSelectedCubename() {
        return selectedCubename;
    }
    public Cube getSelectedCube() {
        return cubes.get(selectedCubename);
    }

    public void addCube(String cubename) {
        cubes.put(cubename, new Cube());
        if (selectedCubename == null) {
            selectCube(cubename);
        }
        addListeners.forEach(l -> l.accept(cubename));
    }
    public List<String> getCubenames() {
        return new ArrayList<>(cubes.keySet());
    }
    public Cube getCube(String name) {
        return cubes.get(name);
    }

    public void addSelectionListener(CubeListener listener) {
        selectionListeners.add(listener);
    }
    public void addAddListener(CubeListener listener) {
        addListeners.add(listener);
    }

    public interface CubeListener extends Consumer<String> {
    }
}
