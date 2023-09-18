package main.ui;

import main.model.Category;
import main.model.Medication;
import main.model.MedicationList;
import main.persistence.JsonReader;
import main.persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Estimator: allows user to add a medication, log an entry, view history, get stats, save/load, or quit
public class Estimator {
    private static final String FILE = "./data/medicationlist.json";

    private MedicationList medicationList;
    private Scanner scanner;
    private String command;
    private JsonWriter writer;
    private JsonReader reader;

    // MODIFIES: this
    // EFFECTS: begins estimator with a new scanner, MedicationList, writer and reader, then opens the main menu
    public Estimator() {
        scanner = new Scanner(System.in);
        medicationList = new MedicationList();
        writer = new JsonWriter(FILE);
        reader = new JsonReader(FILE);
        mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: displays main menu
    public void mainMenu() {
        System.out.println("Main Menu:");
        System.out.println("Enter 'add' to add a medication.");
        System.out.println("Enter 'log' to log an entry.");
        System.out.println("Enter 'history' to view history.");
        System.out.println("Enter 'stats' to get statistics.");
        System.out.println("Enter 'save' to save your history");
        System.out.println("Enter 'load' to reload from file.");
        System.out.println("Enter 'quit' to quit.");
        command = scanner.next();
        if (command.equals("add")) {
            newMedication();
        } else if (command.equals("log")) {
            logMedication();
        } else if (command.equals("history")) {
            viewHistory();
        } else if (command.equals("stats")) {
            viewStatistics();
        } else if (command.equals("save")) {
            saveApplication();
        } else if (command.equals("load")) {
            loadApplication();
        } else if (command.equals("quit")) {
            System.out.println("Thanks for Using the Estimator!");
        } else {
            System.out.println("This is not a valid input, try again.");
            mainMenu();
        }
    }

    // REQUIRES: medication name consists of one word
    // MODIFIES: this
    // EFFECTS: allows user to create a new medication and add it to medication list
    //          Medication names cannot be repeated
    private void newMedication() {
        System.out.println("To add a new medication, type the name, then press 'enter':");
        Medication med = new Medication(scanner.next(), true);
        String name = med.getName();
        List<String> names = medicationList.getNames();
        if (names.contains(name)) {
            System.out.println("Medication names cannot be repeated, try again.");
            newMedication();
        } else {
            medicationList.addMedication(med);
            mainMenu();
        }
    }

    // EFFECTS: allows user to enter a medication to be logged
    private void logMedication() {
        System.out.println("To log an entry, type the name of the medication you wish to log for:");
        String name = scanner.next();
        List<String> names = medicationList.getNames();
        if (names.contains(name)) {
            Medication med = medicationList.getMedication(name);
            entryMenu(med);
        } else {
            System.out.println("This medication does not exist, try again.");
            logMedication();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays "entry menu", where users can choose which category to rank or return to main menu
    private void entryMenu(Medication m) {
        System.out.println("Enter one of:");
        System.out.println("'1': to log a sleep ranking.");
        System.out.println("'2': to log a mood ranking.");
        System.out.println("'3': to log an effectiveness ranking.");
        System.out.println("'4': to return to the menu.");
        command = scanner.next();
        if (command.equals("1")) {
            Integer categoryIndex = 0;
            rank(m, categoryIndex);
            System.out.println("Ranking successfully added!");
            entryMenu(m);
        } else if (command.equals("2")) {
            Integer categoryIndex = 1;
            rank(m, categoryIndex);
            System.out.println("Ranking successfully added!");
            entryMenu(m);
        } else if (command.equals("3")) {
            Integer categoryIndex = 2;
            rank(m, categoryIndex);
            System.out.println("Ranking successfully added!");
            entryMenu(m);
        } else if (command.equals("4")) {
            mainMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds ranking for given medication and category, rankings must be an integer between 0 and 10
    private void rank(Medication m, Integer categoryIndex) {
        Category c = m.getCategories().get(categoryIndex);
        System.out.println("Enter an integer ranking between 0 and 10 (inclusive).");
        command = scanner.next();
        try{
            int i = Integer.parseInt(command);
            if (i <= 10 && i >= 0) {
                c.addRanking(i);
            } else {
                System.out.println("Input is invalid, try again.");
                rank(m, categoryIndex);
            }
        }
        catch (NumberFormatException ex){
            System.out.println("Input is invalid, try again.");
            rank(m, categoryIndex);
        }
    }

    // EFFECTS: allows user to view all rankings for a given medication from oldest to newest
    private void viewHistory() {
        System.out.println("To view your history, type the name of the medication you wish to view:");
        String name = scanner.next();
        List<String> names = medicationList.getNames();
        if (names.contains(name)) {
            Medication med = medicationList.getMedication(name);
            List<Category> categories = med.getCategories();

            System.out.println("Sleep logs:");
            Category c1 = categories.get(0);
            printRankings(c1);
            System.out.println();

            System.out.println("Mood logs:");
            Category c2 = categories.get(1);
            printRankings(c2);
            System.out.println();

            System.out.println("Effectiveness logs:");
            Category c3 = categories.get(2);
            printRankings(c3);
            System.out.println();
            mainMenu();
        } else {
            System.out.println("This medication does not exist, try again.");
            viewHistory();
        }
    }

    // EFFECTS: prints rankings for a given category
    private void printRankings(Category c) {
        for (Integer i: c.getRankings()) {
            System.out.println(i);
        }
    }

    // EFFECTS: allows user to enter name of medication they wish to view stats on
    private void viewStatistics() {
        System.out.println("To view statistics, type the name of the medication you wish to view:");
        String name = scanner.next();
        List<String> names = medicationList.getNames();
        if (names.contains(name)) {
            Medication med = medicationList.getMedication(name);
            analyze(med);
            mainMenu();
        } else {
            System.out.println("This medication does not exist, try again.");
            viewStatistics();
        }
    }

    // EFFECTS: analyzes and prints stats for given medication
    private void analyze(Medication med) {
        List<Category> categories = med.getCategories();
        List<Double> rankings = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            Category c = categories.get(i);
            double averageRanking = c.getAverageRanking();
            rankings.add(averageRanking);
            System.out.println("Average ranking for " + c.getName() + " = " + averageRanking);
            System.out.println(c.getName() + " has been changing at an average rate of " + c.getAverageChange() + " units each entry.");
            System.out.println();
        }

        double overallAverage;
        if (rankings.size() >= 1) {
            Double sum = 0.0;
            for (Double d: rankings) {
                sum = sum + d;
            }
            overallAverage = sum / rankings.size();
        } else {
            overallAverage = 0;
        }
        System.out.println("Overall Average of " + med.getName() + " = " + overallAverage);
        System.out.println();
    }

    // EFFECTS: saves entire application (i.e. MedicationList with medications and rankings)
    private void saveApplication() {
        try {
            writer.open();
            writer.save(medicationList);
            writer.close();
            System.out.println("File successfully saved to " + FILE);
            mainMenu();
        } catch (IOException e) {
            System.out.println("File cannot be saved to  " + FILE);
            mainMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: reloads application from file (i.e. MedicationList with medications and rankings)
    private void loadApplication() {
        try {
            medicationList = reader.read();
            System.out.println("Data successfully loaded from " + FILE);
            mainMenu();
        } catch (IOException e) {
            System.out.println("Data cannot be loaded from  " + FILE);
            mainMenu();
        }
    }

}
