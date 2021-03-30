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
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    assertTrue(p.getGc().getCCase(0,0).getContenu().getFere().equals(j.getFere()));
    assertTrue(p.getGc().getCCase(0,1).getContenu().getFere()==null);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f);
    assertEquals(1,j.getFere().getGc().length());
    f.chasse = new ChasseGranivore();
    ((ChasseGranivore)(f.chasse)).setC(f);
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
    //TODO
    //doit collecter 1 graine si il y en a une sur la case et qu'elle n'en pas pas déja et quelle a des actions.
  }
  @Test
  public void testChasser(){
    //TODO
    //doit faire comme chasse si il y a une graine.
    //sinon doit ce déplacer vers une graine. (la meilleure graine si la difficulté est suffisante.)
  }
}
