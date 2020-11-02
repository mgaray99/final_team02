package model.entity;

public class EmptyEntity extends Entity{

    public static final Entity INSTANCE = new EmptyEntity();

    protected EmptyEntity(){
        super();
    }
}
