package api.model.autogenerator;

public interface IGenerationInstruction {
    int getStartRow();

    int getStartCol();

    int getEndRow();

    int getEndCol();

    String getEntityTypeToInsert();

    void validate();

    void throwGenerationException();
}
