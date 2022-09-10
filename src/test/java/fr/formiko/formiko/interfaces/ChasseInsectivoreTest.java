package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.tests.TestCaseMuet;

public class ChasseInsectivoreTest extends TestCaseMuet{
  // FUNCTIONS -----------------------------------------------------------------
  private Fourmi ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GSquare(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCSquare(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    assertTrue(p.getGc().getCSquare(0,0).getContent().getFere().equals(j.getFere()));
    assertTrue(p.getGc().getCSquare(0,1).getContent().getFere()==null);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f);
    assertEquals(1,j.getFere().getGc().length());
    f.chasse = new ChasseInsectivore();
    ((ChasseInsectivore)(f.chasse)).setC(f);
    return f;
  }
  @Test
  public void testCanHuntMore(){
    Fourmi f = ini();
    assertTrue(((ChasseInsectivore)(f.chasse)).canHuntMore());
    f.setAction(f.getMaxAction()-1);
    assertTrue(((ChasseInsectivore)(f.chasse)).canHuntMore());
    f.setAction(1);
    assertTrue(((ChasseInsectivore)(f.chasse)).canHuntMore());
    f.setAction(0);
    assertTrue(!((ChasseInsectivore)(f.chasse)).canHuntMore());
    f.setAction(-3);
    assertTrue(!((ChasseInsectivore)(f.chasse)).canHuntMore());
    f.setAction(f.getMaxAction()-1);
    f.setFood(f.getMaxFood()-1);
    assertTrue(((ChasseInsectivore)(f.chasse)).canHuntMore());
    f.setFood(f.getMaxFood());
    assertTrue(!((ChasseInsectivore)(f.chasse)).canHuntMore());
  }
  /*@Test
  public void testChasser(){
    //TODO
  }*/
}
