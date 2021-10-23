package fr.formiko.usuel;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class gTest extends TestCaseMuet{
  //just for test coverage.
  @Test
  public void testG(){
    g c = new g();
  }

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  @SuppressWarnings("unchecked")
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
    HashMap<String,String> map = new HashMap<String, String>();
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
    HashMap<String,String> map = new HashMap<String, String>();
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
    HashMap<String,String> map = new HashMap<String, String>();
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
    HashMap<String,String> map = new HashMap<String, String>();
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
    HashMap<String,String> map = new HashMap<String, String>();
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
  public void testGetOr(){
    HashMap<String,String> map = new HashMap<String, String>();
    map.put("la","la");
    map.put("le","le");
    g.setMap(map);
    assertEquals("la/le",g.getOr("la","le"));

    map = new HashMap<String, String>();
    map.put("la","the");
    map.put("le","the");
    g.setMap(map);
    assertEquals("the",g.getOr("la","le"));

    map = new HashMap<String, String>();
    map.put("la","the");
    map.put("le","ø");
    g.setMap(map);
    assertEquals("the",g.getOr("la","le"));

    map = new HashMap<String, String>();
    map.put("la","the");
    g.setMap(map);
    assertEquals("the",g.getOr("la","le"));

  }
  @Test
  public void testExist(){
    HashMap<String,String> map = new HashMap<String, String>();
    map.put("key","tv");
    g.setMap(map);
    assertTrue(g.exist("key"));
    assertTrue(!g.exist("keY"));
    assertTrue(!g.exist("clé"));
  }
  @Test
  public void testExist2(){
    HashMap<String,String> map = new HashMap<String, String>();
    map.put("key","tv");
    map.put("keY","");
    map.put("clé","ø");
    g.setMap(map);
    assertTrue(g.exist("key"));
    assertTrue(!g.exist("keY"));
    assertTrue(g.exist("clé"));
  }

  @Test
  public void testGet4(){
    assertEquals("",g.get(null, null));
    assertEquals("",g.get("", null));
    assertEquals("default",g.get(null, "default"));
    assertEquals("default",g.get("", "default"));
  }
  @Test
  public void testGet5(){
    HashMap<String,String> map = new HashMap<String, String>();
    map.put("key","");
    g.setMap(map);
    assertEquals("default",g.get("key", "default"));
    map.put("key","t");
    assertEquals("t",g.get("key", "default"));
    map.put("key",null);
    assertEquals("default",g.get("key", "default"));
    assertEquals("",g.get("key",null));
    assertEquals("key",g.get("key"));
  }
  @Test
  public void testGetOr2(){
    HashMap<String,String> map = new HashMap<String, String>();
    map.put("k2","str");
    g.setMap(map);
    assertEquals("str", g.getOr("k1","k2"));
    assertEquals("str", g.getOr("k2","k1"));
    assertEquals("k1/k3", g.getOr("k1","k3"));
  }
}
