package api.model.scroll;

import model.autogenerator.GenerationException;

public interface IScrollerFactory {
    Scroller buildScroller(String[] args, String generatorPath);

    Scroller buildScrollerFromArgs(String identifier, String[] constructorArgs)
            throws GenerationException;

    String[] buildConstructorArgs(String[] args, String generatorFilePath);

    Scroller buildManualScroller(String[] args);

    Scroller buildAutoScroller(String[] args);

    Scroller buildDoodleGenerationScroller(String[] args) throws GenerationException;

    Scroller buildAutoGenerationScroller(String[] args) throws GenerationException;
}
