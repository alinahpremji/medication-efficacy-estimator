package main.model;

import main.persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// represents a Medication having a name and list of categories
public class Medication implements Writable {
    private String name;
    private List<Category> categories;

    // EFFECTS: creates a new medication with given name and 3 categories: sleep, mood, and overall effectiveness
    public Medication(String name, boolean initializeCategories) {
        this.name = name;
        categories = new ArrayList<>();
        if (initializeCategories) {
            Category c1 = new Category("sleep");
            Category c2 = new Category("mood");
            Category c3 = new Category("effectiveness");
            categories.add(c1);
            categories.add(c2);
            categories.add(c3);
        }
    }

    // getters
    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    // MODIFIES: this
    // EFFECTS: adds a category to categories
    public void addCategory(Category c) {
        categories.add(c);
    }

    // CITATION: the following method is based off toJson() in "JsonSerializationDemo - WorkRoomApp", as presented
    // in the "Phase 2. Data Persistence" module for the course "CPSC 210" (UBC, 2023)

    // EFFECTS: returns Medication as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonMedication = new JSONObject();
        jsonMedication.put("name", name);
        jsonMedication.put("categories", makeJsonCategories());
        return jsonMedication;
    }

    // EFFECTS: returns categories in Medication as a JSON array
    private JSONArray makeJsonCategories() {
        JSONArray jsonCategories = new JSONArray();
        for (Category c: categories) {
            jsonCategories.put(c.toJson());
        }
        return jsonCategories;
    }

    // EFFECTS: overrides equals() to compare medication and categories
    @Override
    public boolean equals(Object o) {
        if (o instanceof Medication) {
            Medication medication = (Medication) o;
            boolean sameName = (this.name.equals(medication.getName()));
            boolean sameCategories = categories.size() == medication.getCategories().size();
            for (int i = 0; i < categories.size() && i < medication.categories.size(); i++) {
                if (!categories.get(i).equals(medication.categories.get(i))) {
                    sameCategories = false;
                    break;
                }
            }
            return (sameName && sameCategories);
        } else {
            return false;
        }
    }

    // EFFECT: overrides hashcode() to produce a hash code for given name and categories
    @Override
    public int hashCode() {
        return Objects.hash(name, categories);
    }

}
