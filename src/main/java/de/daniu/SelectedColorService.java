package de.daniu;

public enum SelectedColorService {
    INSTANCE;

    private CubeColor selected = CubeColor.YELLOW;

    public CubeColor getSelected() {
        return selected;
    }

    public void setSelected(CubeColor selected) {
        this.selected = selected;
    }
}
