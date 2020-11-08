package model.scroll;

import java.util.List;
import model.Level;
import model.entity.Entity;
import model.entity.PlayerEntity;

public abstract class Scroller {

    public abstract void scroll(List<Entity> entityList, PlayerEntity player);
}
