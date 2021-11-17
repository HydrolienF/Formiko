package fr.formiko.z;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usuel.fichier;

public class ZLastTest extends TestCaseMuet {
  @Test
  public void testClean(){
    fichier.deleteDirectory("null");
    assertTrue(true);
  }
}
