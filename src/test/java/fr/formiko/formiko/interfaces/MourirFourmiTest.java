package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;

public class MourirFourmiTest extends TestCaseMuet{
  private Fourmi ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f);
    f.tour = new TourFourmi();
    ((TourFourmi)(f.tour)).setF(f);
    return f;
  }
  @Test
  public void testMourir(){
    Fourmi f = ini();
    Fourmiliere fere = f.getFere();
    assertEquals(1,Main.getCCase(0,0).getContent().getGc().length());
    assertEquals(1,fere.getGc().length());
    Main.setPlayingAnt(f);
    f.mourir(0);
    assertTrue(f.getEstMort());
    assertEquals(0,Main.getCCase(0,0).getContent().getGc().length());
    assertEquals(0,fere.getGc().length());
    assertEquals(null,Main.getPlayingAnt());
  }
}
