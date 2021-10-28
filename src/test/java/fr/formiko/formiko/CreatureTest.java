package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.exceptions.NotNullLocationException;
import fr.formiko.tests.TestCaseMuet;

public class CreatureTest extends TestCaseMuet{

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
    Creature g1 = new Insecte(Main.getGc().getCCase(0,0));
    assertEquals(Main.getCCase(0,0), g1.getCCase());
    Creature g2 = new Fourmi();
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
  public void testSetTransported(){
    ini();
    Creature c = new Insecte(Main.getGc().getCCase(0,0));
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),100,(byte)11);
    assertEquals(null,c.getTransported());
    c.setTransported(g1);
    assertEquals(g1,c.getTransported());
    assertThrows(NotNullLocationException.class, () -> {
        c.setTransported(g2);
    });
    assertEquals(g1,c.getTransported());
    c.setTransported(null);
    assertEquals(null,c.getTransported());
    c.setTransported(g2);
    assertEquals(g2,c.getTransported());

  }
  @Test
  public void testIsHungry(){
    Main.initialisation();
    GCase gc = new GCase(1,1);
    Insecte i = new Insecte(gc.getCCaseAlléa());
    i.setNourritureMax(10);
    i.setNourriture(5);
    assertTrue(!i.isHungry(0));
    assertTrue(!i.isHungry(20));
    assertTrue(!i.isHungry(40));
    assertTrue(!i.isHungry(49));
    assertTrue(!i.isHungry(50));
    assertTrue(i.isHungry(51));
    assertTrue(i.isHungry(80));
    assertTrue(i.isHungry(100));
    //exception
    assertTrue(!i.isHungry(-20));
    assertTrue(i.isHungry(200));
    //more than full (should be correct by setNourriture(1) when setNourritureMax(1)).
    i.setNourriture(5);
    i.setNourritureMax(1);
    assertTrue(!i.isHungry(0));
    assertTrue(!i.isHungry(100));
    i.setNourritureMax(10);
    i.setNourriture(9);
    assertTrue(!i.isHungry(89));
    assertTrue(i.isHungry(100));
    i.setNourritureMax(100);
    i.setNourriture(2);
    assertTrue(!i.isHungry(2));
    assertTrue(i.isHungry(3));
    i.setNourritureMax(101);
    assertTrue(!i.isHungry(1));
    assertTrue(i.isHungry(2));
    assertTrue(i.isHungry(3));
  }
}
