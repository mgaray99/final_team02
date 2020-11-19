package api.model.autogenerator;

public interface IRandomizer {
    void initializeRandomizer(String randomizerString);

    void checkArrayLengthsAlign();

    String stripParentheses(String path);

    int[] fillIntArrayWithStrings(String[] values);

    double[] fillDoubleArrayWithStrings(String[] values);

    int getRandomValue();
}
