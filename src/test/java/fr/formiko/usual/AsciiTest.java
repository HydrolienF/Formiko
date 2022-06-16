package fr.formiko.usual;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;

public class AsciiTest extends TestCaseMuet {
  //just for test coverage.
  @Test
  public void testAscii(){
    new Ascii();
  }
  @Test
  public void testTo(){
    assertEquals('A', Ascii.asciiToA(65));
    assertEquals(65, Ascii.aToAscii('A'));
  }
  @Test
  public void testStringToAscii(){
    assertEquals("65 67/n", Ascii.stringToAscii("AC"));
    assertEquals("65 32 80/n", Ascii.stringToAscii("A P"));
    assertEquals("/n", Ascii.stringToAscii(""));
    assertEquals("/n", Ascii.stringToAscii((String)null));
  }
  @Test
  public void testIntToLetterCode(){
    assertEquals("A", Ascii.intToLetterCode(1));
    assertEquals("C", Ascii.intToLetterCode(3));
    assertEquals("Z", Ascii.intToLetterCode(26));
    assertEquals("AA", Ascii.intToLetterCode(27));
    assertEquals("AB", Ascii.intToLetterCode(28));
    assertEquals("CB", Ascii.intToLetterCode(28+2*26));
    assertEquals("", Ascii.intToLetterCode(27*26+1));
  }
}
