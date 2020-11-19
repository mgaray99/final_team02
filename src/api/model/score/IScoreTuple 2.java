package api.model.score;

import org.jetbrains.annotations.NotNull;

public interface IScoreTuple extends Comparable<IScoreTuple> {
    @Override
    String toString();

    String getName();

    int getScore();

    @Override
    int compareTo(@NotNull IScoreTuple other);
}
