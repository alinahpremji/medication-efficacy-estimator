package test.model;

import main.model.Category;
import main.model.Medication;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test class for Medication
public class MedicationTest {
    Medication m;

    @BeforeEach
    public void setUp() {
        m = new Medication("name", true);
    }

    @Test
    public void testConstructor() {
        assertEquals("name", m.getName());
        assertEquals(3, m.getCategories().size());
    }

    @Test
    public void testAddCategory() {
        Category c1 = new Category("c1");
        Category c2 = new Category("c2");
        Category c3 = new Category("c3");
        m.addCategory(c1);
        m.addCategory(c2);
        m.addCategory(c3);
        assertEquals(6, m.getCategories().size());
    }

    @Test
    public void testToJson() {
        JSONObject test = m.toJson();
        assertEquals("name", test.get("name"));
        assertEquals(3, test.getJSONArray("categories").length());
        m.addCategory(new Category("c1"));
        test = m.toJson();
        assertEquals("name", test.get("name"));
        assertEquals(4, test.getJSONArray("categories").length());
    }

    @Test
    public void testEqualsTrue() {
        Medication m1 = new Medication("name", true);
        m1.addCategory(new Category("c1"));
        Medication m2 = new Medication("name", true);
        m2.addCategory(new Category("c1"));
        assertTrue(m1.equals(m2));
        assertTrue(m1.equals(m1));
    }

    @Test
    public void testEqualsFalseMedication() {
        // different names
        Medication m1 = new Medication("name1", true);
        Medication m2 = new Medication("name2", true);
        assertFalse(m1.equals(m2));
        // different categories
        Medication m3 = new Medication("name", true);
        Medication m4 = new Medication("name", false);
        assertFalse(m3.equals(m4));
        // different names and categories
        Medication m5 = new Medication("name5", true);
        Medication m6 = new Medication("name6", true);
        m5.addCategory(new Category("c1"));
        m6.addCategory(new Category("c2"));
        assertFalse(m5.equals(m6));
    }

    @Test
    public void testEqualsFalseNonMedication() {
        assertFalse(m.equals("name"));
        assertFalse(m.equals(null));
    }

    @Test
    public void testHashCode() {
        Medication m1 = new Medication("name", true);
        Medication m2 = new Medication("name", true);
        assertTrue(m1.equals(m2));
        assertTrue(m1.hashCode() == m2.hashCode());
        m2.addCategory(new Category("c1"));
        assertFalse(m1.equals(m2));
        assertFalse(m1.hashCode() == m2.hashCode());
    }




}
