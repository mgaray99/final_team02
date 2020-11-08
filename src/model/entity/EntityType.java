package model.entity;

public enum EntityType implements IEntityType{
    PLAYER("PLAYER"){
        @Override
        public boolean isAffectedByGravity() {
            return true;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return true;
        }
    },

    ENEMY("ENEMY"){
        @Override
        public boolean isAffectedByGravity() {
            return true;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return true;
        }
    },
    BARRIER_BLOCK("BARRIER_BLOCK"){
        @Override
        public boolean isAffectedByGravity() {
            return false;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return false;
        }
    },
    BREAKABLE_BLOCK("BREAKABLE_BLOCK"){
        @Override
        public boolean isAffectedByGravity() {
            return false;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return false;
        }
    },
    DAMAGING_BLOCK("DAMAGING_BLOCK"){
        @Override
        public boolean isAffectedByGravity() {
            return false;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return false;
        }
    },
    POWER_UP_BLOCK("POWER_UP_BLOCK"){
        @Override
        public boolean isAffectedByGravity() {
            return false;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return false;
        }
    },
    POWER_UP("POWER_UP"){
        @Override
        public boolean isAffectedByGravity() {
            return false;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return false;
        }
    },
    GOAL("GOAL"){
        @Override
        public boolean isAffectedByGravity() {
            return false;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return false;
        }
    },
    EMPTY("EMPTY"){
        @Override
        public boolean isAffectedByGravity() {
            return false;
        }

        @Override
        public boolean shouldCheckCollisions() {
            return false;
        }
    };

    private final String typeID;

    EntityType(String typeID) {
        this.typeID = typeID;
    }

    @Override
    public String getTypeID() {
        return typeID;
    }

    @Override
    public abstract boolean isAffectedByGravity();

    @Override
    public abstract boolean shouldCheckCollisions();

}
