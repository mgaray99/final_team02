package model.entity;

public class BreakableBlockEntity extends BlockEntity{

    public BreakableBlockEntity(int xUpperLeft, int yUpperLeft) {
        super(EntityType.BREAKABLE_BLOCK, xUpperLeft, yUpperLeft);
    }
}
