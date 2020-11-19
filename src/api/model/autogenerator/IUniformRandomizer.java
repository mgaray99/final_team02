package api.model.autogenerator;

public interface IUniformRandomizer {
    void initialize(String randomizerPath);

    String stripParentheses(String path);

    int getUniformValue();
}
