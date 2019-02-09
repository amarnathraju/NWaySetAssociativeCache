package test.app;

import main.app.NwayCacheApp;
import org.junit.Test;
import static org.junit.Assert.*;

public class MRUSetTest {

    @Test
    public void testMRU() throws ClassNotFoundException{
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(2, 2, "MRU");

        // Note:
        // (hashcode("test1") % 2) = (hashcode("test3") % 2) = (hashcode("test5") % 2)
        // (hashcode("test2") % 2) = (hashcode("test4") % 2) = (hashcode("test6") % 2)
        cache.put("test1", "Test1");
        cache.put("test2", "Test2");
        cache.put("test3", "Test3");
        cache.put("test4", "Test4");
        cache.put("test5", "Test5"); // Removes test3
        cache.put("test6", "Test6"); // Removes test4

        assertTrue(!cache.get("test4").isPresent() );
        assertTrue(!cache.get("test3").isPresent() );

        assertEquals("Test1", cache.get("test1").get()); // Note Access to Test3

        cache.put("test3", "Test3"); // Should remove test1
        cache.put("test4", "Test4"); // Should remove test6

        assertTrue(cache.get("test3").isPresent() );
        assertTrue(cache.get("test4").isPresent() ); // "test3" is the most recently used
        assertTrue(!cache.get("test1").isPresent());

        assertTrue(!cache.get("test6").isPresent()); // test6 should have been removed

        cache.put("test3", "TestThree"); // replacing the existing value and test3 becomes the most recently used.
        assertEquals("TestThree", cache.get("test3").get() );

        cache.put("test1", "TestOne"); // removes test3

        assertTrue(!cache.get("test3").isPresent());
    }


}