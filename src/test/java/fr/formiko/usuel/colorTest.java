package fr.formiko.usuel;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
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
    Os os = Main.getOs();
    Main.setOs(new Os());
    Main.getOs().setId((byte)0);
    col.iniColor();
    assertTrue(col.RED.charAt(0)==(char)27);
    Main.getOs().setId((byte)1);
    col.iniColor();
    assertTrue(col.RED.charAt(0)=='\033');
    Main.getOs().setId((byte)2);
    col.iniColor();
    assertTrue(col.RED.charAt(0)==(char)27);
    Main.getOs().setId((byte)3);
    col.iniColor();
    assertTrue(col.RED.charAt(0)==(char)27);
    Main.setOs(os);
  }
}
