package model.entity;

import java.util.Random;

public interface IEmpowering {

     Modifier getModifier();

     void setModifier(Modifier modifier);

     boolean hasAppliedModifier();

     void setHasAppliedModifier(boolean hasAppliedModifier);

     default void setRandomModifier(double value, int duration){
          Random random = new Random();
          Modifier.ModifierType[] modifierTypes = Modifier.ModifierType.values();
          Modifier.ModifierType randomType = modifierTypes[random.nextInt(modifierTypes.length)];
          Modifier randomModifier = new Modifier(randomType, value, duration);
          this.setModifier(randomModifier);
     }
}
