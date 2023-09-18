package test.persistence;

import main.model.Medication;
import main.model.MedicationList;
import main.persistence.JsonReader;
import main.persistence.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// test class for JsonWriter
public class JsonWriterTest {
    JsonWriter writer;
    JsonReader reader;
    MedicationList medicationList;

    @Test
    void testFileNotFoundExceptionThrown() {
        writer = new JsonWriter("./data/my\0illegal:fileName.json");
        try {
            writer.open();
            fail("Exception not thrown");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    void testEmptyMedicationList() {
        writer = new JsonWriter("./data/emptyList.json");
        reader = new JsonReader("./data/emptyList.json");
        medicationList = new MedicationList();
        try {
            // write
            writer.open();
            writer.save(medicationList);
            writer.close();
            // read
            medicationList = reader.read();
            // check
            assertEquals(0, medicationList.getMedications().size());
            assertEquals(0, medicationList.getNames().size());
        } catch (IOException e) {
            fail("Exception Thrown");
        }
    }

    @Test
    void testNormalMedicationList() {
        writer = new JsonWriter("./data/writerList.json");
        reader = new JsonReader("./data/writerList.json");
        medicationList = new MedicationList();
        try {
            // read
            medicationList = reader.read();
            // check
            assertEquals(2, medicationList.getMedications().size());
            assertEquals(2, medicationList.getNames().size());

            // edit
            Medication m = new Medication("test", true);
            medicationList.addMedication(m);
            // write
            writer.open();
            writer.save(medicationList);
            writer.close();
            // read
            medicationList = reader.read();
            // check
            assertEquals(3, medicationList.getMedications().size());
            assertEquals(3, medicationList.getNames().size());

            // re-initialize
            medicationList.deleteMedication(m);
            writer.open();
            writer.save(medicationList);
            writer.close();
        } catch (IOException e) {
            fail("Exception Thrown");
        }
    }

}
