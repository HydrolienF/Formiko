package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.tests.TestCaseMuet;

public class GraineTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  private void ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,5),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
  }
  @Test
  public void testSetCCase(){
    ini();
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(Main.getCCase(0,0), g1.getCCase());
    Graine g2 = new Graine(null,10,(byte)20);
    assertEquals(null, g2.getCCase());
    g2.setCCase(Main.getCCase(0,0));
    assertEquals(Main.getCCase(0,0), g2.getCCase());
    g2.setCCase(Main.getCCase(0,3));
    assertEquals(Main.getCCase(0,3), g2.getCCase());
    g2.setCCase(Main.getCCase(100,10));
    assertEquals(null, g2.getCCase());
    g2.setCCase(Main.getCCase(0,3));
    assertEquals(Main.getCCase(0,3), g2.getCCase());
    g1.setCCase(Main.getCCase(0,3));
    assertEquals(Main.getCCase(0,3), g1.getCCase());
    assertEquals(Main.getCCase(0,3), g2.getCCase());
  }
  @Test
  public void testGraine(){
    ini();
    assertEquals(0,Main.getGc().getCCase(0,0).getContent().getGg().length());
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(1,Main.getGc().getCCase(0,0).getContent().getGg().length());
    assertEquals(g1,Main.getGc().getCCase(0,0).getContent().getGg().getHead().getContent());
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),10,(byte)100);
    assertEquals(2,Main.getGc().getCCase(0,0).getContent().getGg().length());
    assertEquals(g2,Main.getGc().getCCase(0,0).getContent().getGg().getHead().getContent());
    assertEquals(g1,Main.getGc().getCCase(0,0).getContent().getGg().getHead().getSuivant().getContent());
  }
}
