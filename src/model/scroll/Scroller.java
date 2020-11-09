package model.scroll;

import java.util.List;
import model.entity.IEntity;
import model.entity.Player;

public abstract class Scroller {

    public abstract void scroll(List<IEntity> entityList, Player player);
}
