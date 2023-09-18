package main.persistence;

import main.model.Category;
import main.model.Medication;
import main.model.MedicationList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// CITATION: this code is modelled off the structure in "JsonSerializationDemo - WorkRoomApp",
// as presented in the "Phase 2. Data Persistence" module for the course "CPSC 210" (UBC, 2023).

// Represents a reader that reads medicationList from stored data in file
public class JsonReader {
    private String file;

    // EFFECTS: constructs a reader to read from the game's file
    public JsonReader(String file) {
        this.file = file;
    }

    // EFFECTS: reads medicationList from file, then returns the list
    // if unable to read file, throws IOException
    public MedicationList read() throws IOException {
        String data = readFile(file);
        JSONObject jsonData = new JSONObject(data);
        return makeMedicationList(jsonData);
    }

    // CITATION: the readFile method is *directly* from "JsonSerializationDemo - WorkRoomApp", found here:
    //           https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: medicationList
    // EFFECTS: converts JSON dataObject into corresponding MedicationList and returns this value
    private MedicationList makeMedicationList(JSONObject jsonData) {
        MedicationList medicationList = new MedicationList();
        addMedications(medicationList, jsonData);
        return medicationList;
    }

    // MODIFIES: medicationList
    // EFFECTS: converts medications from jsonData and adds them to the given MedicationList
    private void addMedications(MedicationList medicationList, JSONObject jsonData) {
        JSONArray jsonMedications = jsonData.getJSONArray("medications");
        for (Object obj : jsonMedications) {
            JSONObject jsonMedication = (JSONObject) obj;
            addMedication(medicationList, jsonMedication);
        }
    }

    // MODIFIES: medicationList
    // EFFECTS: converts a medication from a JSONObject and adds it to medicationList
    private void addMedication(MedicationList medicationList, JSONObject jsonMedication) {
        String name = jsonMedication.getString("name");
        Medication medication = new Medication(name, false);
        JSONArray jsonCategories = jsonMedication.getJSONArray("categories");
        addCategories(medication, jsonCategories);
        medicationList.addMedication(medication);
    }

    // MODIFIES: medication
    // EFFECTS: converts categories from jsonData and adds them to the medication
    private void addCategories(Medication medication, JSONArray jsonCategories) {
        for (Object obj : jsonCategories) {
            JSONObject jsonCategory = (JSONObject) obj;
            String name = jsonCategory.getString("name");
            Category category = new Category(name);
            JSONArray jsonRankings = jsonCategory.getJSONArray("rankings");
            addRankings(category, jsonRankings);
            medication.addCategory(category);
        }
    }

    // MODIFIES: category
    // EFFECTS: converts rankings from jsonData and adds them to the category
    private void addRankings(Category category, JSONArray jsonRankings) {
        for (Object obj: jsonRankings) {
            int ranking = (int) obj;
            category.addRanking(ranking);
        }
    }

}
