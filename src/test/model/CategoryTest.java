package test.model;

import main.model.Category;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test class for Category
public class CategoryTest {
    Category c;

    @BeforeEach
    public void setUp() {
        c = new Category("name");
    }

    @Test
    public void testConstructor() {
        assertEquals("name", c.getName());
        assertEquals(0, c.getRankings().size());
    }

    @Test
    public void testAddRanking() {
        c.addRanking(0);
        c.addRanking(5);
        c.addRanking(10);
        assertEquals(3, c.getRankings().size());
    }

    @Test
    public void testGetAverageRanking() {
        assertEquals(0, c.getAverageRanking());
        c.addRanking(1);
        assertEquals(1, c.getAverageRanking());
        c.addRanking(4);
        c.addRanking(9);
        assertEquals(4.666666666666667, c.getAverageRanking());
    }

    @Test
    public void testGetAverageChange() {
        assertEquals(0, c.getAverageChange());
        c.addRanking(1);
        assertEquals(0, c.getAverageChange());
        c.addRanking(4);
        c.addRanking(9);
        assertEquals(4.0, c.getAverageChange());
    }

    @Test
    public void testToJson() {
        JSONObject test = c.toJson();
        assertEquals("name", test.get("name"));
        assertEquals(0, test.getJSONArray("rankings").length());
        c.addRanking(3);
        test = c.toJson();
        assertEquals("name", test.get("name"));
        assertEquals(1, test.getJSONArray("rankings").length());
    }

    @Test
    public void testEqualsTrue() {
        Category c1 = new Category("name");
        c1.addRanking(10);
        Category c2 = new Category("name");
        c2.addRanking(10);
        assertTrue(c1.equals(c2));
        assertTrue(c1.equals(c1));
    }

    @Test
    public void testEqualsFalseCategory() {
        // different names
        Category c1 = new Category("name1");
        Category c2 = new Category("name2");
        assertFalse(c1.equals(c2));
        // different rankings
        Category c3 = new Category("name");
        c3.addRanking(3);
        Category c4 = new Category("name");
        c4.addRanking(4);
        assertFalse(c3.equals(c4));
        // different names and categories
        Category c5 = new Category("name5");
        Category c6 = new Category("name6");
        c5.addRanking(5);
        assertFalse(c5.equals(c6));
    }

    @Test
    public void testEqualsFalseNonCategory() {
        assertFalse(c.equals("name"));
        assertFalse(c.equals(null));
    }

    @Test
    public void testHashCode() {
        Category c1 = new Category("name");
        Category c2 = new Category("name");
        assertTrue(c1.equals(c2));
        assertTrue(c1.hashCode() == c2.hashCode());
        c2.addRanking(10);
        assertFalse(c1.equals(c2));
        assertFalse(c1.hashCode() == c2.hashCode());
    }

}
