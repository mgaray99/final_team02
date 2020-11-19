package api.model.score;

import java.io.IOException;

public interface IGameLeaderboard {
    void buildTitle();

    void buildScoresList();

    void insertIntoScoresList(String line);

    void sortScoresList();

    String getScoreTupleAtPlace(int index);

    String getTitle();

    void addScoreTuple(IScoreTuple toBeAdded) throws IOException;

    void writeTupleToFile(IScoreTuple toBeWritten) throws IOException;
}
