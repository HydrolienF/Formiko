package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.fusual.FFolder;
import fr.formiko.formiko.Main;

import java.awt.Font;

public class OptionsTest extends TestCaseMuet {
  @Test
  public void testGetFontTitle(){
    Main.setFolder(new FFolder(Main.getView()));
    Main.getFolder().ini();
    Options op = Options.newDefaultOptions();
    op.setFontText("Default");
    op.setFontTitle("Insektofobiya");
    op.updateFont();
    Font font1 = op.getFont1();
    Font font2 = op.getFont2();
    assertTrue(!font1.equals(font2));
    assertEquals(font2.getName(), op.getFontTitle(null).getName());
    assertEquals(font2.getName(), op.getFontTitle("").getName());
    assertEquals(font2.getName(), op.getFontTitle("A").getName());
    assertEquals(font2.getName(), op.getFontTitle("a test with only standard char").getName());
    // assertEquals(font1.getName(), op.getFontTitle("|").getName());
    // assertEquals(font2.getName(), op.getFontTitle("a string with an un suported café.").getName());
  }
}
