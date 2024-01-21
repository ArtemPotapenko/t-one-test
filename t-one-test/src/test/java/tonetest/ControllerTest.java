package tonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tonetest.comtrollers.MainController;

import java.util.*;

@SpringBootTest
public class ControllerTest {
    @Autowired
    MainController controller;

    private void assertSortedMap(Map<Character, Integer> first, Map<Character, Integer> second) {
        Iterator<Map.Entry<Character, Integer>> firstIterator = first.entrySet().iterator();
        Iterator<Map.Entry<Character, Integer>> secondIterator = second.entrySet().iterator();
        Map<Integer, HashSet<Character>> firstHashSetMap = new LinkedHashMap<>();
        Map<Integer, HashSet<Character>> secondHashSetMap = new LinkedHashMap<>();

        while (firstIterator.hasNext() && secondIterator.hasNext()) {
            Map.Entry<Character, Integer> firstEntry = firstIterator.next();
            Map.Entry<Character, Integer> secondEntry = secondIterator.next();
            Assertions.assertEquals(firstEntry.getValue(), secondEntry.getValue());

            firstHashSetMap.compute(firstEntry.getValue(), (k, v) -> v == null ? new HashSet<>() : v);
            firstHashSetMap.get(firstEntry.getValue()).add(firstEntry.getKey());
            secondHashSetMap.compute(secondEntry.getValue(), (k, v) -> v == null ? new HashSet<>() : v);
            secondHashSetMap.get(secondEntry.getValue()).add(secondEntry.getKey());
        }

        Assertions.assertFalse(firstIterator.hasNext());
        Assertions.assertFalse(secondIterator.hasNext());

        for (Integer key : firstHashSetMap.keySet()) {
            HashSet<Character> firstSet = firstHashSetMap.get(key);
            HashSet<Character> secondSet = secondHashSetMap.get(key);
            for (Character character : firstSet) {
                Assertions.assertTrue(secondSet.contains(character));
            }
        }
    }

    @Test
    void oneLetterTest() {
        Map<Character, Integer> map = new LinkedHashMap<>();
        map.put('a', 5);
        Map<Character, Integer> ansMap = controller.count("aaaaa");
        assertSortedMap(ansMap, map);
    }

    @Test
    void twoLettersTest() {
        Map<Character, Integer> map = new LinkedHashMap<>();
        map.put('a', 5);
        map.put('b', 4);
        Map<Character, Integer> ansMap = controller.count("aabbabaab");
        assertSortedMap(map, ansMap);
    }

    @Test
    void emptyMapTest() {
        assertSortedMap(controller.count(""), new HashMap<>());
    }

    @Test
    void equalAmountTest() {
        Map<Character, Integer> map = new LinkedHashMap<>();
        map.put('b', 5);
        map.put('a', 5);
        map.put('c', 3);
        assertSortedMap(map, controller.count("aabbcccbbaaba"));
    }
    @Test
    void qwertyTest(){
        Map<Character,Integer> map = new LinkedHashMap<>();
        map.put('q',1);
        map.put('w',1);
        map.put('e',1);
        map.put('r',1);
        map.put('t',1);
        map.put('y',1);
        assertSortedMap(map,controller.count("qwerty"));
    }
}
