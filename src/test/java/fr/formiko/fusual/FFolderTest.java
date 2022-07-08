package fr.formiko.fusual;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.views.ViewNull;

public class FFolderTest extends TestCaseMuet {
  @Test
  public void testGetCurentVersion(){
    FFolder f = new FFolder(new ViewNull());
    assertNotNull(f.getCurentVersion());
    assertNotEquals(f.getCurentVersion(), "");
    assertNotEquals(f.getCurentVersion(), FFolder.DEFAULT_NULL_VERSION);
  }
}
