package fr.formiko.views.gui2d;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;

public class FPanelTest extends TestCaseMuet {
  @Test
  public void testCenterInParent(){
    FPanel parent = new FPanel();
    parent.setSize(100,100);
    FPanel child = new FPanel();
    child.setSize(50,10);
    parent.add(child);
    assertEquals(0, child.getX());
    child.centerInParent();
    assertEquals(25, child.getX());
  }
}
