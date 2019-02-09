package test.app;

import main.app.NwayCacheApp;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class NwayCacheAppTest {

    @Test
    public void testLRU() throws ClassNotFoundException{
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(2, 3, "LRU");
        assertEquals("main.app.LRUSet", cache.getAlgoClassPath());
    }


    @Test
    public void testMRU() throws ClassNotFoundException{
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(1, 10, "MRU");
        assertEquals("main.app.MRUSet", cache.getAlgoClassPath());
    }


    @Test
    public void testCustom() throws ClassNotFoundException{
        // Using a custom path to the algo
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(1, 1, "main.app.LRUSet");
        assertEquals("main.app.LRUSet", cache.getAlgoClassPath());
    }


    @Test(expected = ClassNotFoundException.class)
    public void testWrongAlgo() throws ClassNotFoundException{
        // Using a custom path to the algo
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(1, 1, "WrongPath");
        assertTrue(false); // Test should fail because we are giving a wrong path/algo

    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetNullKey() throws ClassNotFoundException{
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(1, 1, "LRU");
        cache.get(null);
        assertTrue(false); // Test should fail because we are trying to get a null key

        cache.put("Test","t1");
        assertTrue(cache.get("Test").isPresent());
        assertEquals(cache.get("Test"), "t1");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutNullKey() throws ClassNotFoundException{
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(1, 1, "LRU");
        cache.put(null,"testOne");
        assertTrue(false); // Test should fail because we are trying to put a null key


        cache.put("Test","t1");
        assertTrue(cache.get("Test").isPresent());
        assertEquals(cache.get("Test"), "t1");

    }


    @Test(expected = IllegalArgumentException.class)
    public void testPutNullValue() throws ClassNotFoundException{
        NwayCacheApp<String, String> cache = new NwayCacheApp<>(1, 1, "LRU");
        cache.put("test1",null);
        assertTrue(false); // Test should fail because we are trying to put a null value

    }

    @Test
    public void testBasicReadWrite() throws ClassNotFoundException {
        NwayCacheApp cache = new NwayCacheApp(2, 8, "LRU");
        cache.put("Leeroy", "Jenkins");
        cache.put("Amar", "Raju");
        assertEquals(cache.get("Leeroy").get(), "Jenkins");
        assertEquals(cache.get("Amar").get(), "Raju");
    }

    @Test
    public void testUpdateSameKey() throws ClassNotFoundException {
        NwayCacheApp cache = new NwayCacheApp(2, 8, "LRU");
        cache.put(16, "Raj");
        cache.put(16, "Amar");
        assertEquals(cache.get(16).get(), "Amar");

    }

    @Test
    public void testCUSTOM() throws ClassNotFoundException, InterruptedException {
        NwayCacheApp cache = new NwayCacheApp(8, 4, "MRU");
        cache.put(16, "Christiano");
        Thread.sleep(50);
        cache.put(32, "Ronaldo");
        Thread.sleep(50);
        cache.put(48, "Lionel");
        Thread.sleep(50);
        cache.put(64, "Messi");
        Thread.sleep(50);
        cache.put(80, "Kaka");
        assertEquals(cache.get(16).get(), "Christiano");
        assertEquals(cache.get(32).get(), "Ronaldo");
        assertEquals(cache.get(48).get(), "Lionel");
        assertTrue(!cache.get(64).isPresent());
        assertEquals(cache.get(80).get(), "Kaka");
    }


    @Test
    public void testMultiType() throws ClassNotFoundException, InterruptedException {
        // Permits any type of Key or Value
        NwayCacheApp cache = new NwayCacheApp(8, 4, "MRU");
        cache.put("Amar", "raju");
        cache.put("Rain", 0);
        cache.put(1, "River");

        assertEquals(cache.get(1).get(), "River");

        //Permits only string keys and values
        NwayCacheApp<String, String> cache2 = new NwayCacheApp(8, 4, "MRU");
        cache2.put("Amar", "raju");
        cache2.put("Rain", "0");
        cache2.put("1", "River");
        assertEquals(cache2.get("1").get(), "River");

        //Permits only string keys but any type of Objects
        NwayCacheApp<String, Object > cache3 = new NwayCacheApp(8, 4, "LRU");
        cache3.put("Amar", "raju");
        cache3.put("Rain", Boolean.TRUE);
        cache3.put("Rain", 1);
        cache3.put("1", "River");
        assertEquals(cache3.get("Rain").get(), 1);

        //Permits only string keys but any type of Objects
        NwayCacheApp< Object, String > cache4 = new NwayCacheApp(8, 4, "LRU");
        cache4.put("Amar", "raju");
        cache4.put(Boolean.TRUE, "Rain");
        cache4.put(1, "one" );
        //cache4.put(null, "River");
        assertEquals(cache4.get(!false).get(), "Rain");


    }

    @Test
    public void testMapTypes() throws ClassNotFoundException, InterruptedException {
        //Permits only string keys but any type of Objects
        NwayCacheApp cache = new NwayCacheApp(8, 4, "LRU");
        Map supermap = new HashMap();

        Map hashMap = new HashMap();
        hashMap.put(1,2);
        hashMap.put(2,3);

        Map treeMap = new TreeMap();
        treeMap.put(1,2);
        treeMap.put(2,3);

        cache.put(hashMap, "hashmap" );
        cache.put(treeMap, "treemap" );

        supermap.put(hashMap, "hashmap");
        supermap.put(treeMap, "treemap");


        // Note that Java returns "treemap" in an oridinary Hashmap if we get for the key hashMap
        // Therefore the cache also does the same
        assertEquals(supermap.get(hashMap), "treemap");
        assertEquals(cache.get(hashMap).get(), "treemap");

        assertEquals(cache.get(treeMap).get(), "treemap");
        assertEquals(cache.get(treeMap).get(), "treemap");
    }

    @Test
    public void testListTypes() throws ClassNotFoundException, InterruptedException {
        //Permits only string keys but any type of Objects
        NwayCacheApp cache = new NwayCacheApp(8, 4, "LRU");
        Map map  = new HashMap();
        List<Integer> arraylist = new ArrayList<>();
        arraylist.add(1);
        List<Integer> linkedlist = new LinkedList<>();
        linkedlist.add(1);

        cache.put(arraylist, "arraylist" );
        cache.put(linkedlist, "linkedlist" );

        map.put(arraylist, "arraylist" );
        map.put(linkedlist, "linkedlist" );


        // Note that Java returns "linkedlist" in a Hashmap if we get for the key arraylist
        // Therefore the cache also does the same
        assertEquals(map.get(arraylist), "linkedlist");
        assertEquals(cache.get(arraylist).get(), "linkedlist");

        assertEquals(map.get(linkedlist), "linkedlist");
        assertEquals(cache.get(linkedlist).get(), "linkedlist");

    }


    @Test
    public void testSameListType() throws ClassNotFoundException, InterruptedException {
        //Permits only string keys but any type of Objects
        NwayCacheApp cache = new NwayCacheApp(8, 4, "LRU");
        Map map  = new HashMap();
        List<Integer> arraylist1 = new ArrayList<>();
        arraylist1.add(1);
        List<Integer> arraylist2 = new ArrayList<>();
        arraylist2.add(1);


        cache.put(arraylist1, "arraylist1" );
        cache.put(arraylist2, "arraylist2" );

        map.put(arraylist1, "arraylist1" );
        map.put(arraylist2, "arraylist2" );

        // Note that Java returns "arraylist2" in a Hashmap if we get for the key arraylist1.
        // Therefore the cache also does the same
        assertEquals(map.get(arraylist1), "arraylist2");
        assertEquals(cache.get(arraylist1).get(), "arraylist2");

        assertEquals(map.get(arraylist2), "arraylist2");
        assertEquals(cache.get(arraylist2).get(), "arraylist2");

    }

    @Test
    public void testSubTypes() throws ClassNotFoundException, InterruptedException {
        //Permits only string keys but any type of Objects
        NwayCacheApp<Parent, String> cache = new NwayCacheApp(8, 4, "LRU");
        Parent o1 = new Child1(1);
        Parent o2 = new Child2(1);
        cache.put(o1, "child1" );
        cache.put(o2, "child2" );

        assertEquals(cache.get(o1).get(), "child1");
    }


    @Test
    public void testSameClass() throws ClassNotFoundException, InterruptedException {
        //Permits only string keys but any type of Objects
        NwayCacheApp<Parent, String> cache = new NwayCacheApp(8, 4, "LRU");
        Child1 o1 = new Child1(1);
        Child1 o2 = new Child1(1);
        cache.put(o1, "child1" );
        cache.put(o2, "child2" );

        assertEquals(cache.get(o1).get(), "child1");
    }

    @Test
    public void testSameString() throws ClassNotFoundException, InterruptedException {
        //Permits only string keys but any type of Objects
        NwayCacheApp<String, String> cache = new NwayCacheApp(8, 4, "LRU");
        String str1 = "sample";
        String str2 = "sample";
        cache.put(str1, "Str1" );
        cache.put(str2, "Str2" );

        // Immutable Strings
        assertEquals(cache.get(str1).get(), "Str2");


        NwayCacheApp cache2 = new NwayCacheApp(8, 4, "LRU");
        StringBuilder stringBuilder1 = new StringBuilder("sb1");
        StringBuilder stringBuilder2 = new StringBuilder("sb2");
        cache2.put(stringBuilder1, "sb1" );
        cache2.put(stringBuilder2, "sb2" );


        assertEquals(cache2.get(stringBuilder1).get(), "sb1");
        assertEquals(cache2.get(stringBuilder2).get(), "sb2");


    }

    @Test
    public void testHashZero() throws ClassNotFoundException, InterruptedException {

        NwayCacheApp cache = new NwayCacheApp(8, 4, "LRU");
        cache.put(0, "zero" );
        cache.put(new HashMap<>(), "hashmap" );
        cache.put(new Object(), "object" );
        assertEquals(cache.get(0).get(), "zero");
    }

    @Test
    public void testHashNeg() throws ClassNotFoundException, InterruptedException {

        NwayCacheApp cache = new NwayCacheApp(8, 4, "LRU");
        cache.put(-10, -10);
        cache.put(-1, -1);
        cache.put(-2, -2);
        cache.put(-3, -3);
        assertEquals(cache.get(-10).get(), -10);
        assertEquals(cache.get(-1).get(), -1);
        assertEquals(cache.get(-2).get(), -2);
        assertEquals(cache.get(-3).get(), -3);



        NwayCacheApp cache1 = new NwayCacheApp(8, 4, "LRU");
        cache1.put(-1, "neg one" );
        cache1.put(1, "pos one" );
        assertEquals(cache1.get(-1).get(), "neg one");
        assertEquals(cache1.get(1).get(), "pos one");


    }

    }