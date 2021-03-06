package io.github.emergentorganization.cellrpg.systems.CASystems.CAs.CACell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseCell {
    private final Logger logger = LogManager.getLogger(getClass());

    public int state;  // cell state value
    public boolean lock; // TODO: true locks the cell, means "don't compute rule on this cell"

    public BaseCell(int _state) {
        state = _state;
    }

    public int getState() {
        return state;
    }

    public void setState(final int new_state) {
        state = new_state;
    }
}
