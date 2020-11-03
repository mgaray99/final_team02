package model.entity;

public final class EmptyEntity extends Entity{

    public static final Entity INSTANCE = new EmptyEntity();

    protected EmptyEntity(){
        super();
    }
}
