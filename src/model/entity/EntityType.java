package model.entity;

public enum EntityType implements IEntityType{
    PLAYER("PLAYER"),
    ENEMY("ENEMY"),
    BARRIER_BLOCK("BARRIER_BLOCK"),
    BREAKABLE_BLOCK("BREAKABLE_BLOCK"),
    DAMAGING_BLOCK("DAMAGING_BLOCK"),
    POWER_UP_BLOCK("POWER_UP_BLOCK"),
    POWER_UP("POWER_UP"),
    GOAL("GOAL"),
    EMPTY("EMPTY");

    private final String typeID;

    EntityType(String typeID) {
        this.typeID = typeID;
    }

    @Override
    public String getTypeID() {
        return typeID;
    }

}
