package model.entity;

public class Modifier {
    private ModifierType modifierType;
    private double value;
    private double duration;

    public Modifier(ModifierType modifierType, double value, double duration){
        this.modifierType = modifierType;
        this.value = value;
        this.duration = duration;
    }

    public void updateDuration(){
        this.duration--;
    }

    public ModifierType getModifierType() {
        return this.modifierType;
    }

    public double getValue() {
        return this.value;
    }

    public double getDuration() {
        return this.duration;
    }

    public enum ModifierType{
        MOVEMENT_SPEED,
        JUMP_SPEED,
        //COLLISION_DAMAGE,
        //HEALTH,
        GRAVITY
    }
}
