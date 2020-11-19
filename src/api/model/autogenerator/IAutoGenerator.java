package api.model.autogenerator;

import model.autogenerator.GenerationInstruction;
import model.autogenerator.XMLHelper;

public interface IAutoGenerator {
    void buildSpecification(XMLHelper helper);

    void setDimensions(XMLHelper helper);

    void setDefault(XMLHelper helper);

    String[][] generateNextBlock();

    void fillInDefaultValues();

    void executeAllSpecifications();

    void executeSpecification(GenerationInstruction spec);
}
