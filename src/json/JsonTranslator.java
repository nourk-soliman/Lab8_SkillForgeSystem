package Json;

import com.google.gson.JsonElement;
import java.util.List;

public interface JsonTranslator<T> {
    void readFromFile(String filename);
    void saveToFile(String filename);
}

