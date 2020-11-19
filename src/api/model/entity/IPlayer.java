package api.model.entity;

import model.entity.Modifier;

import java.util.Map;

public interface IPlayer {

    double getMovementSpeedModifierValue();

    double getJumpSpeedModifierValue();

    double getAntiGravityModifierValue();

    Map<Modifier.ModifierType, Modifier> getModifiers();

    default void updateModifiers(){
        for(Modifier.ModifierType modifierType : this.getModifiers().keySet()){
            Modifier modifier = this.getModifiers().get(modifierType);
            if(modifier != null){
                modifier.updateDuration();
                if(modifier.getDuration() <= 0){
                    this.removeModifier(modifierType);
                }
            }
        }
    }

    default void applyModifier(Modifier modifier){
        this.getModifiers().put(modifier.getModifierType(), modifier);
    }

    default void removeModifier(Modifier.ModifierType modifierType){
        this.getModifiers().remove(modifierType);
    }
}
