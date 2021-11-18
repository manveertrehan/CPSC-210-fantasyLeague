package persistence;

import org.json.JSONObject;

// interface taken from JsonSerializationDemo

public interface Writeable {
    // EFFECTS: return this as a JSON object
    JSONObject toJson();
}
