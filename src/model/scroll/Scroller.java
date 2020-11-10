package model.scroll;

import java.util.List;
import model.entity.IEntity;
import model.entity.Player;

public interface Scroller {
    int NUM_BLOCKS = 15;

    void scroll(List<IEntity> entityList, Player player);
}
