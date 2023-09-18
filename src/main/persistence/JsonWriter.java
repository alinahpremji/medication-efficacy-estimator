package main.persistence;

import main.model.MedicationList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// CITATION: this code is modelled off the structure in "JsonSerializationDemo - WorkRoomApp",
// as presented in the "Phase 2. Data Persistence" module for the course "CPSC 210" (UBC, 2023)

// Represents a writer that writes JSON medicationList to file
public class JsonWriter {
    private String file;
    private PrintWriter writer;

    // EFFECTS: constructs a writer to write medicationList into file
    public JsonWriter(String file) {
        this.file = file;
    }

    // CITATION: the open() method is *directly* from "JsonSerializationDemo - WorkRoomApp", found here:
    //           https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: opens writer,
    // if there is an error with opening the file, throws FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(file));
    }

    // MODIFIES: this
    // EFFECTS: converts medicationList to a JSONObject, then writes string to file
    public void save(MedicationList medicationList) {
        JSONObject jsonMedicationList = medicationList.toJson();
        String text = jsonMedicationList.toString();
        writer.print(text);
    }

    // CITATION: the close() method is *directly* from "JsonSerializationDemo - WorkRoomApp", found here:
    //           https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

}