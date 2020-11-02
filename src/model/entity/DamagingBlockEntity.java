package model.entity;

public class DamagingBlockEntity extends BlockEntity{
    public DamagingBlockEntity(int xUpperLeft, int yUpperLeft) {
        super(EntityType.DAMAGING_BLOCK, xUpperLeft, yUpperLeft);
    }
}
