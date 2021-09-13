package fr.formiko.usuel.types;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Os;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.io.File;

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
    Main.ini();
    assertEquals("windows", os.toString());
  }

  class OsExtends extends Os{
    public OsExtends(String s){
      OS=s;
      iniOs();
    }
  }
}
