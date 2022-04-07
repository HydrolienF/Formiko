package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.tests.TestCaseMuet;

public class ChasseGranivoreTest extends TestCaseMuet{
  // FUNCTIONS -----------------------------------------------------------------
  private Fourmi ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(2,5),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",true);
    j.getFere().setJoueur(j);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f);
    f.chasse = new ChasseGranivore();
    ((ChasseGranivore)(f.chasse)).setC(f);
    f.setCCase(0,0);
    f.getFere().setCCase(Main.getCCase(0,1));
    return f;
  }
  @Test
  public void testCanHuntMore(){
    Fourmi f = ini();
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(f.getMaxAction()-1);
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(1);
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(0);
    assertTrue(!((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(-3);
    assertTrue(!((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(f.getMaxAction()-1);
    f.setFood(f.getMaxFood()-1);
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setFood(f.getMaxFood());
    assertTrue(!((ChasseGranivore)(f.chasse)).canHuntMore());

    f.setFood(f.getMaxFood()-1);
    f.setTransported(new Graine());
    assertTrue(!((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setTransported(null);
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
  }
  @Test
  public void testChasse(){
    Fourmi f = ini();
    f.setAction(f.getMaxAction());
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    Graine g2 = new Graine(null,10,(byte)20);
    f.setTransported(g2);
    assertTrue(!f.chasse());

    f = ini();
    f.setAction(f.getMaxAction());
    g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(1,f.getCCase().getContent().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(g1,f.getTransported());
    assertEquals(null,g1.getCCase());

    f = ini();
    f.setAction(0);
    g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertTrue(!f.chasse());
    assertEquals(null,f.getTransported());
    assertEquals(Main.getGc().getCCase(0,0),g1.getCCase());

    f = ini();
    f.setAction(-6);
    g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(1,f.getCCase().getContent().getGg().length());
    f.chasse();
    assertEquals(-6,f.getAction());
    assertEquals(null,f.getTransported());
    assertEquals(Main.getGc().getCCase(0,0),g1.getCCase());
  }
  @Test
  public void testChasse2(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    assertEquals(2,f.getCCase().getContent().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(g2,f.getTransported());
  }
  @Test
  public void testChasse3(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getMaxAction());
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    new Graine(Main.getGc().getCCase(0,0),10,(byte)10);
    assertEquals(2,f.getCCase().getContent().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(g1,f.getTransported());
  }
  @Test
  public void testChasse4(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(0);
    f.setAction(f.getMaxAction());
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(2,f.getCCase().getContent().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(g2,f.getTransported());
  }
  @Test
  public void testChasse5(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(-1); //with difficulty < 0 it take the 1a graine and not the better 1.
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(2,f.getCCase().getContent().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(g1,f.getTransported());
  }
  @Test
  public void testChasse6(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(-1);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    assertEquals(2,f.getCCase().getContent().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(g2,f.getTransported());
  }

  // 1 seed to get on the same Case.
  @Test
  public void testChasser(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getMaxAction());
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(g2,f.getTransported());
  }
  // 1 seed to get on an other Case.
  @Test
  public void testChasser2(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(0,0),f.getCCase());
    assertEquals(null,f.getTransported());
  }
  // 2 seed
  @Test
  public void testChasser3(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(0,0),f.getCCase());
    assertEquals(null,f.getTransported());
  }
  // 2 seed 1 on the same case.
  @Test
  public void testChasser3b(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getMaxAction());
    Graine g1 = new Graine(Main.getGc().getCCase(0,1),100,(byte)10);
    new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(0,1),f.getCCase());
    assertEquals(g1,f.getTransported());
  }
  // 2 seed on different Case.
  @Test
  public void testChasser4(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    new Graine(Main.getGc().getCCase(0,2),105,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(0,2),f.getCCase());
    assertEquals(null,f.getTransported());
  }
  //if it choose default seed.
  @Test
  public void testChasser5(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(0);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    new Graine(Main.getGc().getCCase(0,2),105,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(0,0),f.getCCase());
    assertEquals(null,f.getTransported());
  }
  @Test
  public void testChasser6(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(0);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,2),105,(byte)10);
    new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(0,0),f.getCCase());
    assertEquals(null,f.getTransported());
  }
  @Test
  public void testChasser6b(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(0);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(1,1),100,(byte)10);
    new Graine(Main.getGc().getCCase(0,2),105,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(1,1),f.getCCase());
    assertEquals(null,f.getTransported());
  }
  //if it choose best seed.
  @Test
  public void testChasser7(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(1);
    f.setAction(f.getMaxAction());
    new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    new Graine(Main.getGc().getCCase(0,2),105,(byte)10);
    f.chasser(8);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(0,2),f.getCCase());
    assertEquals(null,f.getTransported());
  }
  //if there is no seed move to give direction.
  @Test
  public void testChasser8(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(1);
    f.setAction(f.getMaxAction());
    f.chasser(6);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(1,1),f.getCCase());
    assertEquals(null,f.getTransported());
  }
  @Test
  public void testChasser9(){
    Fourmi f = ini();
    f.setCCase(0,1);
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(1);
    f.setAction(f.getMaxAction());
    f.chasser(9);
    assertTrue(f.getAction()<f.getMaxAction());
    assertEquals(Main.getGc().getCCase(1,2),f.getCCase());
    assertEquals(null,f.getTransported());
  }
}
