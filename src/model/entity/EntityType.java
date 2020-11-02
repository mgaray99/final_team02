package model.entity;

public enum EntityType implements IEntityType{
    PLAYER("player"),
    ENEMY("enemy"),
    BARRIER_BLOCK("barrier_block"),
    BREAKABLE_BLOCK("breakable_block"),
    DAMAGING_BLOCK("damaging_block"),
    POWER_UP_BLOCK("power_up_block"),
    POWER_UP("power_up"),
    GOAL("goal"),
    EMPTY("empty");

    private final String typeID;

    EntityType(String typeID) {
        this.typeID = typeID;
    }

    @Override
    public String getTypeID() {
        return typeID;
    }
}
