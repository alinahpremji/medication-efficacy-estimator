package test.model;

import main.model.Medication;
import main.model.MedicationList;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// test class for MedicationList
public class MedicationListTest {
    MedicationList medicationList;
    Medication m1;
    Medication m2;
    Medication m3;

    @BeforeEach
    public void setUp() {
        medicationList = new MedicationList();
        m1 = new Medication("name1", true);
        m2 = new Medication("name2", true);
        m3 = new Medication("name3", true);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, medicationList.getMedications().size());
        assertEquals(0, medicationList.getNames().size());
    }

    @Test
    public void testGetMedicationFirstIteration() {
        medicationList.addMedication(m1);
        medicationList.addMedication(m2);
        medicationList.addMedication(m3);
        assertEquals(m1, medicationList.getMedication("name1"));
    }

    @Test
    public void testGetMedicationSecondIteration() {
        medicationList.addMedication(m1);
        medicationList.addMedication(m2);
        medicationList.addMedication(m3);
        assertEquals(m2, medicationList.getMedication("name2"));
    }

    @Test
    public void testAddMedication() {
        medicationList.addMedication(m1);
        medicationList.addMedication(m2);
        medicationList.addMedication(m3);
        assertEquals(3, medicationList.getMedications().size());
        assertEquals(3, medicationList.getNames().size());
    }

    @Test
    public void testDeleteMedicationToEmpty() {
        medicationList.addMedication(m1);
        assertEquals(1, medicationList.getMedications().size());
        assertEquals(1, medicationList.getNames().size());
        medicationList.deleteMedication(m1);
        assertEquals(0, medicationList.getMedications().size());
        assertEquals(0, medicationList.getNames().size());
    }

    @Test
    public void testDeleteMedicationMultiple() {
        medicationList.addMedication(m1);
        medicationList.addMedication(m2);
        medicationList.addMedication(m3);
        assertEquals(m2, medicationList.getMedications().get(1));
        medicationList.deleteMedication(m2);
        assertEquals(m3, medicationList.getMedications().get(1));
        medicationList.deleteMedication(m3);
        assertEquals(1, medicationList.getMedications().size());
        assertEquals(1, medicationList.getNames().size());
    }

    // JSON Testing

    @Test
    public void testToJsonEmpty() {
        JSONObject test = medicationList.toJson();
        assertEquals(0, test.getJSONArray("medications").length());
        assertEquals(0, test.getJSONArray("names").length());
    }

    @Test
    public void testToJsonAddOne() {
        JSONObject test = medicationList.toJson();
        assertEquals(0, test.getJSONArray("medications").length());
        assertEquals(0, test.getJSONArray("names").length());
        medicationList.addMedication(m1);
        test = medicationList.toJson();
        assertEquals(1, test.getJSONArray("medications").length());
        assertEquals(1, test.getJSONArray("names").length());
    }

    @Test
    public void testToJsonAddMultiple() {
        JSONObject test = medicationList.toJson();
        assertEquals(0, test.getJSONArray("medications").length());
        assertEquals(0, test.getJSONArray("names").length());
        medicationList.addMedication(m1);
        medicationList.addMedication(m2);
        medicationList.addMedication(m3);
        test = medicationList.toJson();
        assertEquals(3, test.getJSONArray("medications").length());
        assertEquals(3, test.getJSONArray("names").length());
    }

}


