package api.model.autogenerator;

public interface IConstantGeneration {
    void buildInstruction(String[] args);

    void buildXSpecification(String xArgs);

    void buildYSpecification(String yArgs);

    void buildStartRow(String rowArg);

    void buildEndRow(String rowArg);

    void buildStartCol(String colArg);

    void buildEndCol(String colArg);
}
