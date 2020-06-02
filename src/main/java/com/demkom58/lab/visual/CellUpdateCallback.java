package com.demkom58.lab.visual;

import javafx.scene.input.KeyEvent;

public interface CellUpdateCallback {
    void onCellUpdate(KeyEvent event, int x, int y);
}
