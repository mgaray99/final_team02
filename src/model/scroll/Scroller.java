package model.scroll;

import model.Level;
import model.entity.Player;

public interface Scroller {
    int NUM_BLOCKS = 15;

    void scroll(Level level, Player player);
}
