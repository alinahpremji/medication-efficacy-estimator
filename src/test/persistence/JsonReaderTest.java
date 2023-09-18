package test.persistence;

import main.model.MedicationList;
import main.persistence.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// test class for JsonReader
public class JsonReaderTest {
    JsonReader reader;
    MedicationList medicationList;

    @Test
    public void testIOExceptionThrown() {
        reader = new JsonReader("./data/noSuchFile.json");
        try {
            medicationList = reader.read();
            fail("Exception not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testEmptyMedicationList() {
        reader = new JsonReader("./data/emptyList.json");
        try {
            medicationList = reader.read();
        } catch (IOException e) {
            fail("IOException Thrown");
        }
        assertEquals(0, medicationList.getMedications().size());
        assertEquals(0, medicationList.getNames().size());
    }

    @Test
    public void testNormalMedicationList() {
        reader = new JsonReader("./data/readerList.json");
        try {
            medicationList = reader.read();
            assertEquals(2, medicationList.getMedications().size());
            assertEquals(2, medicationList.getNames().size());
            // test medication attributes
            assertEquals("name3", medicationList.getMedications().get(1).getName());
            assertEquals(3, medicationList.getMedications().get(1).getCategories().size());
            assertEquals(2, medicationList.getMedications().get(1).getCategories().get(1).getAverageRanking());
        } catch (IOException e) {
            fail("IOException Thrown");
        }
    }
}
