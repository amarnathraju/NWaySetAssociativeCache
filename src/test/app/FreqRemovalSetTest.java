package test.app;

import main.app.NwayCacheApp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FreqRemovalSetTest {
    @Test
    public void testFR() throws ClassNotFoundException {
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(2, 2, "test.app.FreqRemovalSet");

        cache.put("k1", "v1");
        assertTrue(cache.get("k1").isPresent());
        assertEquals(cache.get("k1").get(), "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");

        cache.put("k4", "v4");
        cache.put("k5", "v5");
        assertTrue(!cache.get("k3").isPresent());
        assertTrue(cache.get("k1").isPresent());
        assertEquals(cache.get("k1").get(), "v1");
        assertTrue(cache.get("k5").isPresent());
        assertEquals(cache.get("k5").get(), "v5");

        cache.put("k5", "val5");
        assertTrue(cache.get("k5").isPresent());
        assertEquals(cache.get("k5").get(), "val5");

        cache.put("k3", "v3");
        assertTrue(!cache.get("k1").isPresent());





    }
}
