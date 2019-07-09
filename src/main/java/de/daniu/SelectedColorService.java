package de.daniu;

import de.daniu.domain.CubeColor;

public enum SelectedColorService {
    SELECTED_COLORS;

    private CubeColor selected = CubeColor.YELLOW;

    public CubeColor getSelected() {
        return selected;
    }

    public void setSelected(CubeColor selected) {
        this.selected = selected;
    }
}
