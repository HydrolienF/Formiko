package fr.formiko.usual;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;


public class colorTest extends TestCaseMuet{
  //just for test coverage.
  @Test
  public void testColor(){
    new color();
  }
  @Test
  public void testIniColor(){
    color col = new color();
    Os os = Os.getOs();
    Os.setOs(new Os());
    Os.getOs().setId((byte)0);
    col.iniColor();
    assertTrue(col.RED.charAt(0)==(char)27);
    Os.getOs().setId((byte)1);
    col.iniColor();
    assertTrue(col.RED.charAt(0)=='\033');
    Os.getOs().setId((byte)2);
    col.iniColor();
    assertTrue(col.RED.charAt(0)==(char)27);
    Os.getOs().setId((byte)3);
    col.iniColor();
    assertTrue(col.RED.charAt(0)==(char)27);
    Os.setOs(os);
  }
}
