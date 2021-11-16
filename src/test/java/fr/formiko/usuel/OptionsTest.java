package fr.formiko.usuel;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usuel.Options;
import fr.formiko.formiko.Main;

import java.awt.Font;

public class OptionsTest extends TestCaseMuet {
  @Test
  public void testGetFontTitle(){
    Main.setFolder(new Folder());
    Main.getFolder().ini();
    Options op = Options.newDefaultOptions();
    op.setFontText("Default");
    op.setFontTitle("Insektofobiya");
    op.updateFont();
    Font font1 = op.getFont1();
    Font font2 = op.getFont2();
    assertTrue(!font1.equals(font2));
    assertEquals(font2, op.getFontTitle(null));
    assertEquals(font2, op.getFontTitle(""));
    assertEquals(font2, op.getFontTitle("A"));
    assertEquals(font2, op.getFontTitle("a test with only standard char"));
    assertEquals(font1, op.getFontTitle("|"));
    assertEquals(font1, op.getFontTitle("a string with an un suported café."));
  }
}
