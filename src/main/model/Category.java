package main.model;

import main.persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// represents a Category having a name and list of rankings
public class Category implements Writable {
    private String name;
    private List<Integer> rankings;

    // EFFECTS: creates a new category with given name and no rankings
    public Category(String name) {
        this.name = name;
        rankings = new ArrayList<>();
    }

    // getters
    public String getName() {
        return name;
    }

    public List<Integer> getRankings() {
        return rankings;
    }

    // REQUIRES: ranking is an integer between 0 and 10 (inclusive)
    // MODIFIES: this
    // EFFECTS: adds a ranking to rankings
    public void addRanking(Integer i) {
        rankings.add(i);
    }

    // EFFECTS: returns average ranking
    public double getAverageRanking() {
        double average;
        if (rankings.size() >= 1) {
            Integer sum = 0;
            for (Integer i: rankings) {
                sum = sum + i;
            }
            average =  (double) sum / rankings.size();
        } else {
            average = 0;
        }
        return average;
    }

    // EFFECTS: returns average rate of change among rankings
    public double getAverageChange() {
        double average;
        if (rankings.size() >= 2) {
            Integer numerator = rankings.get(rankings.size() - 1) - rankings.get(0);
            Integer denominator = rankings.size() - 1;
            average = numerator / denominator;
        } else {
            average = 0;
        }
        return average;
    }


    // CITATION: the following method is based off toJson() in "JsonSerializationDemo - WorkRoomApp", as presented
    // in the "Phase 2. Data Persistence" module for the course "CPSC 210" (UBC, 2023)

    // EFFECTS: returns Category as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonCategory = new JSONObject();
        jsonCategory.put("name", name);
        jsonCategory.put("rankings", makeJsonRankings());
        return jsonCategory;
    }

    // EFFECTS: returns rankings in Category as a JSON array
    private JSONArray makeJsonRankings() {
        JSONArray jsonRankings = new JSONArray();
        for (Integer i: rankings) {
            jsonRankings.put(i);
        }
        return jsonRankings;
    }

    // EFFECTS: overrides equals() to compare name and rankings
    @Override
    public boolean equals(Object o) {
        if (o instanceof Category) {
            Category category = (Category) o;
            boolean sameName = (this.name.equals(category.getName()));
            boolean sameRankings = rankings.size() == category.getRankings().size();
            for (int i = 0; i < rankings.size() && i < category.getRankings().size(); i++) {
                if (!rankings.get(i).equals(category.getRankings().get(i))) {
                    sameRankings = false;
                    break;
                }
            }
            return (sameName && sameRankings);
        } else {
            return false;
        }
    }

    // EFFECT: overrides hashcode() to produce a hash code for given name and categories
    @Override
    public int hashCode() {
        return Objects.hash(name, rankings);
    }

}
