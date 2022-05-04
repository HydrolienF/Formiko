package fr.formiko.usuel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import fr.formiko.usuel.Os;
import fr.formiko.tests.TestCaseMuet;

public class OsTest extends TestCaseMuet {
  @Test
  public void testOs(){
    Os os = new OsExtends("win");
    assertEquals(1,os.getId());
    assertTrue(os.isWindows());
    assertTrue(!os.isLinux());
    assertTrue(!os.isMac());
  }
  @Test
  public void testOs2(){
    Os os = new OsExtends("batOS");
    assertEquals(-1,os.getId());
    assertTrue(!os.isWindows());
    assertTrue(!os.isLinux());
    assertTrue(!os.isMac());
  }
  @Test
  public void testOs3(){
    Os os = new OsExtends("macOS");
    assertEquals(2,os.getId());
    assertTrue(os.isMac());
    assertTrue(!os.isLinux());
    assertTrue(!os.isWindows());
  }
  @Test
  public void testOs4(){
    Os os = new OsExtends("linux64");
    assertEquals(0,os.getId());
  }
  @Test
  public void testOs5(){
    Os os = new OsExtends("nix32");
    assertEquals(0,os.getId());
  }
  @Test
  public void testOs6(){
    Os os = new OsExtends("aix2021");
    assertEquals(0,os.getId());
    assertTrue(os.isLinux());
  }

  @Test
  public void testToString(){
    Os os = new OsExtends("win");
    assertTrue(os.toString().equals("os.1") || os.toString().equals("windows"));
  }
  @Test
  public void testGetARCH(){
    Os os = new Os();
    assertTrue(os.getARCH()!=null);
  }
  @AfterAll
  public static void resetOs(){
    erreur.println("clean");
    new OsExtends(System.getProperty("os.name").toLowerCase());
  }
}
class OsExtends extends Os{
  public OsExtends(String s){
    OS=s;
    iniOs();
  }
}
