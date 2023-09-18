package main.persistence;

import org.json.JSONObject;

// CITATION: the Writable interface is *directly* from "JsonSerializationDemo - WorkRoomApp", found here:
//           https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// interface for creating JSON objects
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
