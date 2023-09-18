package main.model;

import main.persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// represents a MedicationList having a list of medications that the user has added,
// and a list of their corresponding names
public class MedicationList implements Writable {
    private List<Medication> medications;
    private List<String> names;

    // REQUIRES: meds and names have the same length
    // EFFECTS: creates an empty medication list, with no meds and no names
    public MedicationList() {
        medications = new ArrayList<>();
        names = new ArrayList<>();
    }

    // getters
    public List<Medication> getMedications() {
        return medications;
    }

    public List<String> getNames() {
        return names;
    }

    // REQUIRES: a medication with the given name exists in MedicationList
    // EFFECTS: returns medication with given name
    public Medication getMedication(String n) {
        Medication answer = null;
        for (Medication m: medications) {
            String name = m.getName();
            if (name.equals(n)) {
                answer = m;
                break;
            }
        }
        return answer;
    }

    // REQUIRES: no medications can share the same name
    // MODIFIES: this
    // EFFECTS: adds a medication to meds, and its name to names
    public void addMedication(Medication m) {
        String name = m.getName();
        medications.add(m);
        names.add(name);
    }


    // REQUIRES: given medication exists in MedicationList
    // MODIFIES: this
    // EFFECTS: removes a medication from medications, and its name from names
    public void deleteMedication(Medication m) {
        String name = m.getName();
        medications.remove(m);
        names.remove(name);
    }

    // CITATION: the following 3 methods are based off toJson() and thingiesToJson() in
    // "JsonSerializationDemo - WorkRoomApp", as presented in the "Phase 2. Data Persistence" module for the course
    // "CPSC 210" (UBC, 2023)

    // EFFECTS: returns MedicationList as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonMedicationList = new JSONObject();
        jsonMedicationList.put("medications", makeJsonMedications());
        jsonMedicationList.put("names", makeJsonNames());
        return jsonMedicationList;
    }

    // EFFECTS: returns medications in MedicationList as a JSON array
    private JSONArray makeJsonMedications() {
        JSONArray jsonMedications = new JSONArray();
        for (Medication m: medications) {
            jsonMedications.put(m.toJson());
        }
        return jsonMedications;
    }

    // EFFECTS: returns names in MedicationList as a JSON array
    private JSONArray makeJsonNames() {
        JSONArray jsonNames = new JSONArray(names);
        return jsonNames;
    }

}
