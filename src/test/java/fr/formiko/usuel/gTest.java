package fr.formiko.usuel.types;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class gTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testSetMap(){
    HashMap map = new HashMap<String, String>();
    g.setMap(map);
    map = new HashMap<String, Integer>();
    g.setMap(map); //we can't stop that ;(
    /*Map map2 = new TreeMap<String, String>(); //do not compile.
    assertTrue(!g.setMap(map2));*/
  }
  @Test
  public void testGet1(){
    HashMap map = new HashMap<String, String>();
    map.put("menu.2","translated value !");
    g.setMap(map);
    assertEquals("translated value !",g.get("menu",2,"ifItFail"));
    assertEquals("ifItFail",g.get("menu",1,"ifItFail"));
    assertEquals("ifItFail",g.get("menu",3,"ifItFail"));
    assertEquals("ifItFail",g.get("menu",-2,"ifItFail"));
    assertEquals("ifItFail",g.get("dfgh",2,"ifItFail"));
  }
  @Test
  public void testGetWithAMaj(){
    HashMap map = new HashMap<String, String>();
    map.put("menu.2","translated value !");
    map.put("key","translated value 2 !");
    g.setMap(map);
    assertEquals("translated value !",g.get("menu",2,"ifItFail"));
    assertEquals("ifItFail",g.get("menu",3,"ifItFail"));
    assertEquals("Translated value !",g.get("Menu",2,"ifItFail")); //it work with a uppercase & return translation with a maj.
    assertEquals("Translated value 2 !",g.get("Key","ifItFail2"));
  }
  @Test
  public void testGet2(){
    HashMap map = new HashMap<String, String>();
    map.put("key","translated value 1");
    map.put("key in several words","translated value 2");
    map.put("keyInSeveralWordsWithoutSpace","translated value 3");
    g.setMap(map);
    assertEquals("translated value 1",g.get("key","ifItFailGet 2"));
    assertEquals("translated value 2",g.get("key in several words","ifItFailGet 2"));
    assertEquals("translated value 3",g.get("keyInSeveralWordsWithoutSpace","ifItFailGet 2"));
    assertEquals("ifItFailGet 2",g.get("keY","ifItFailGet 2"));
    assertEquals("ifItFailGet 2",g.get("kéy","ifItFailGet 2"));
    g.setMap(new HashMap<String, String>());
    assertEquals("ifItFailGet 2",g.get("key","ifItFailGet 2"));

  }
  @Test
  public void testGet3(){
    HashMap map = new HashMap<String, String>();
    map.put("key","translated value 1");
    map.put("key in several words","translated value 2");
    map.put("keyInSeveralWordsWithoutSpace","translated value 3");
    map.put("k4","ø");
    g.setMap(map);
    assertEquals("keY",g.get("keY"));
    assertEquals("kéy",g.get("kéy"));
    assertEquals("",g.get("k4"));

  }
  @Test
  public void testGetM(){
    HashMap map = new HashMap<String, String>();
    map.put("key","tv");
    map.put("key2","translated value 2");
    map.put("key3","OP");
    map.put("key4","é oui !");
    g.setMap(map);
    assertEquals("KeY",g.getM("KeY"));
    assertEquals("Kéy",g.getM("kéy"));

    assertEquals("Tv",g.getM("key"));
    assertEquals("Translated value 2",g.getM("key2"));
    assertEquals("OP",g.getM("key3"));
    assertEquals("É oui !",g.getM("key4"));


  }
  @Test
  public void testGetOu(){
    HashMap map = new HashMap<String, String>();
    map.put("la","la");
    map.put("le","le");
    g.setMap(map);
    assertEquals("la/le",g.getOu("la","le"));

    map = new HashMap<String, String>();
    map.put("la","the");
    map.put("le","the");
    g.setMap(map);
    assertEquals("the",g.getOu("la","le"));

    map = new HashMap<String, String>();
    map.put("la","the");
    map.put("le","ø");
    g.setMap(map);
    assertEquals("the",g.getOu("la","le"));

    map = new HashMap<String, String>();
    map.put("la","the");
    g.setMap(map);
    assertEquals("the",g.getOu("la","le"));

  }
  @Test
  public void testExist(){
    HashMap map = new HashMap<String, String>();
    map.put("key","tv");
    g.setMap(map);
    assertTrue(g.exist("key"));
    assertTrue(!g.exist("keY"));
    assertTrue(!g.exist("clé"));
  }
  @Test
  public void testExist2(){
    HashMap map = new HashMap<String, String>();
    map.put("key","tv");
    map.put("keY","");
    map.put("clé","ø");
    g.setMap(map);
    assertTrue(g.exist("key"));
    assertTrue(!g.exist("keY"));
    assertTrue(g.exist("clé"));
  }
}
