package api.model.configuration;

import java.io.IOException;
import java.util.Map;

public interface ILevelDecoder {
    void buildKeyToEntityTypeMap() throws IOException, NullPointerException;

    Map<String, String> getIdToEntityMap();
}
