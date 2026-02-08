package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemFactoryImplTest {
    private ItemFactoryImpl itemFactoryImpl;

    @BeforeEach
    void init(){
        itemFactoryImpl = new ItemFactoryImpl();
    }

    @Test
    void testCorrectItem(){
        assertTrue(itemFactoryImpl.generateItem("M") instanceof Chart);
        assertTrue(itemFactoryImpl.generateItem("D") instanceof Dynamite);
        assertTrue(itemFactoryImpl.generateItem("G") instanceof Gold);
        assertTrue(itemFactoryImpl.generateItem("L") instanceof Lifes);
        assertTrue(itemFactoryImpl.generateItem("C") instanceof LuckyClover);
        assertTrue(itemFactoryImpl.generateItem("P") instanceof Pickaxe);
        assertTrue(itemFactoryImpl.generateItem("S") instanceof Shield);
        assertTrue(itemFactoryImpl.generateItem("X") instanceof GoldX3);

    }

    @Test
    void generateItems(){
        String key[] = {"M", "D", "G", "L", "C", "P", "S", "X"};
        for (String str : key) {
            Item item = itemFactoryImpl.generateItem(str);
            assertNotNull(item, "should return an item given the key");
        }
    }

    @Test
    void generateWrongItem(){
        assertThrows(IllegalArgumentException.class, () -> itemFactoryImpl.generateItem("Q"));
    }

    @Test
    void testObjectsAreDiff(){
        Item firsItem = itemFactoryImpl.generateItem("M");
        Item secondItem = itemFactoryImpl.generateItem("M");
        assertNotSame(firsItem, secondItem, "every call should return a different object");
    }













}
