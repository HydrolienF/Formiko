package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.interfaces.TourInsecte;
import fr.formiko.usuel.tests.TestCaseMuet;
import fr.formiko.usuel.exceptions.ClassTypeException;

public class ChasseGranivoreTest extends TestCaseMuet{
  // Fonctions propre -----------------------------------------------------------
  private Fourmi ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",true);
    j.getFere().setJoueur(j);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
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
    f.setAction(f.getActionMax()-1);
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(1);
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(0);
    assertTrue(!((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(-3);
    assertTrue(!((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setAction(f.getActionMax()-1);
    f.setNourriture(f.getNourritureMax()-1);
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setNourriture(f.getNourritureMax());
    assertTrue(!((ChasseGranivore)(f.chasse)).canHuntMore());

    f.setNourriture(f.getNourritureMax()-1);
    f.setTransported(new Graine());
    assertTrue(!((ChasseGranivore)(f.chasse)).canHuntMore());
    f.setTransported(null);
    assertTrue(((ChasseGranivore)(f.chasse)).canHuntMore());
  }
  @Test
  public void testChasse(){
    Fourmi f = ini();
    f.setAction(f.getActionMax());
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    Graine g2 = new Graine(null,10,(byte)20);
    f.setTransported(g2);
    assertTrue(!f.chasse());

    f = ini();
    f.setAction(f.getActionMax());
    g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(1,f.getCCase().getContenu().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getActionMax());
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
    assertEquals(1,f.getCCase().getContenu().getGg().length());
    f.chasse();
    assertEquals(-6,f.getAction());
    assertEquals(null,f.getTransported());
    assertEquals(Main.getGc().getCCase(0,0),g1.getCCase());
  }
  @Test
  public void testChasse2(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getActionMax());
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    assertEquals(2,f.getCCase().getContenu().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getActionMax());
    assertEquals(g2,f.getTransported());
  }
  @Test
  public void testChasse3(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(false);
    f.setAction(f.getActionMax());
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),10,(byte)10);
    assertEquals(2,f.getCCase().getContenu().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getActionMax());
    assertEquals(g1,f.getTransported());
  }
  @Test
  public void testChasse4(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(0);
    f.setAction(f.getActionMax());
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(2,f.getCCase().getContenu().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getActionMax());
    assertEquals(g2,f.getTransported());
  }
  @Test
  public void testChasse5(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(-1); //with difficulty < 0 it take the 1a graine and not the better 1.
    f.setAction(f.getActionMax());
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    assertEquals(2,f.getCCase().getContenu().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getActionMax());
    assertEquals(g1,f.getTransported());
  }
  @Test
  public void testChasse6(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(true);
    Main.setDifficulté(-1);
    f.setAction(f.getActionMax());
    Graine g1 = new Graine(Main.getGc().getCCase(0,0),100,(byte)10);
    Graine g2 = new Graine(Main.getGc().getCCase(0,0),105,(byte)10);
    assertEquals(2,f.getCCase().getContenu().getGg().length());
    f.chasse();
    assertTrue(f.getAction()<f.getActionMax());
    assertEquals(g2,f.getTransported());
  }
  @Test
  public void testChasser(){
    //TODO
    //doit faire comme chasse si il y a une graine.
    //sinon doit ce déplacer vers une graine. (la meilleure graine si la difficulté est suffisante.)
  }
}
