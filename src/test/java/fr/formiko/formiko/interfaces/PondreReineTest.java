package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.tests.TestCaseMuet;

public class PondreReineTest extends TestCaseMuet{
  private Fourmi ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GSquare(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCSquare(0,0),null),"joueurTest",true);
    j.getFere().setJoueur(j);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f);
    f.tour = new TourReine();
    return f;
  }
  @Test
  public void testCanLay(){
    Fourmi f = ini();
    f.setFood(14);
    f.setAction(1);
    assertTrue(f.canLay());
    f.setFood(13);
    assertTrue(!f.canLay());
    f.setFood(14);
    f.setAction(0);
    assertTrue(!f.canLay());
    f.setAction(-2);
    assertTrue(!f.canLay());
  }
  @Test
  public void testCanLay2(){
    Fourmi f = ini();
    f.pondre = new PondreNull();
    f.setFood(14);
    f.setAction(1);
    assertTrue(!f.canLay());
  }
  @Test
  public void testCanLay3(){
    Fourmi f = ini();
    f.setFood(f.getMaxFood());
    f.setAction(1);
    assertTrue(f.canLay());
    f.setFood(14);
    f.setAction(f.getMaxAction());
    assertTrue(f.canLay());
  }
  @Test
  public void testCanLay4(){
    Fourmi f = ini();
    f.getFere().setCSquare(Main.getCSquare(0,0));
    f.setCSquare(Main.getCSquare(0,1));
    f.setFood(f.getMaxFood());
    f.setAction(f.getMaxAction());
    assertTrue(!f.canLay());
  }
  //It should work always but sometimes it fail.
  // @Test
  // public void testCanLay5(){
  //   ini();
  //   Creature f = new Insecte();
  //   ((Insecte)(f)).setType((byte)(0));
  //   f.setMaxFood(100);
  //   assertTrue(!f.canLay());
  //   f.setFood(f.getMaxFood());
  //   f.setAction(f.getMaxAction());
  //   assertTrue(!f.canLay());
  //   f.pondre = new PondreReine();
  //   assertTrue(f.canLay());
  // }
}
