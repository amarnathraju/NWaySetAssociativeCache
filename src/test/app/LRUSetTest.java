package test.app;

import main.app.NwayCacheApp;
import org.junit.Test;
import static org.junit.Assert.*;

public class LRUSetTest {

    @Test
    public void testLRU() throws ClassNotFoundException {
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(2, 2, "LRU");

        // Note:
        // (hashcode("test1") % 2) = (hashcode("test3") % 2) = (hashcode("test5") % 2)
        // (hashcode("test2") % 2) = (hashcode("test4") % 2) = (hashcode("test6") % 2)
        cache.put("test1", "Test1");
        cache.put("test2", "Test2");
        cache.put("test3", "Test3");
        cache.put("test4", "Test4");
        cache.put("test5", "Test5"); // Removes test1
        cache.put("test6", "Test6"); // Removes test2

        assertTrue(!cache.get("test1").isPresent());
        assertTrue(!cache.get("test2").isPresent());

        assertEquals("Test3", cache.get("test3").get()); // Note Access to Test3

        cache.put("test1", "Test1"); // Should remove test5 and not test3 because test3 was just accessed
        cache.put("test2", "Test2"); // Should remove test4

        assertTrue(cache.get("test1").isPresent());
        assertTrue(cache.get("test3").isPresent()); // "test3" is the most recently used in that set
        assertTrue(!cache.get("test5").isPresent());

        assertTrue(!cache.get("test4").isPresent()); // test4 should have been removed

        cache.put("test1", "TestOne"); // replacing the existing value and test1 becomes the most recently used.
        assertEquals("TestOne", cache.get("test1").get());

        cache.put("test5", "TestFive"); // Should remove test3 and not test1

        assertTrue(!cache.get("test3").isPresent());
    }

    @Test
    public void testLRU2() throws ClassNotFoundException {
        NwayCacheApp<Comparable, String> cache = new NwayCacheApp<>(2, 2, "LRU");

        // Note:
        // (hashcode("test1") % 2) = (hashcode("test3") % 2) = (hashcode("test5") % 2)
        // (hashcode("test2") % 2) = (hashcode("test4") % 2) = (hashcode("test6") % 2)
        cache.put(1, "Test1");
        cache.put("test2", "Test2");
    }
}