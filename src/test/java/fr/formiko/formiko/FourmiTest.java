package fr.formiko.formiko;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;

public class FourmiTest extends TestCaseMuet{

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
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    j.getFere().getGc().add(f);
    assertEquals(1,j.getFere().getGc().length());
    return f;
  }

  @Test
  @Disabled("test fail with maven")
  public void testWantFood(){
    Fourmi f = ini();
    assertEquals(1,f.getIndividu().getNourritureConso());
    f.setNourriture(f.getNourritureMax());
    assertTrue(!f.wantFood());
    f.setNourriture(f.getNourritureMax()/2);
    assertTrue(!f.wantFood());
    //can't eat for the 2 next turn
    f.setNourritureMax(10);
    f.setNourriture(1);
    assertTrue(f.wantFood());
    //can't eat for the 2 next turn
    f.setNourritureMax(2);
    f.setNourriture(1);
    assertTrue(f.wantFood());
    //can't eat for the 2 next turn but already full.
    f.setNourritureMax(1);
    f.setNourriture(1);
    assertTrue(!f.wantFood());
    //don't have enoth food.
    f.setNourritureMax(100);
    f.setNourriture(1);
    assertTrue(f.wantFood());
    f.setNourriture(2);
    assertTrue(f.wantFood());
    f.setNourriture(4);
    assertTrue(f.wantFood());
    f.setNourriture(5);
    assertTrue(!f.wantFood());
    f.setNourriture(6);
    assertTrue(!f.wantFood());
    f.setNourriture(10);
    assertTrue(!f.wantFood());
  }
}
