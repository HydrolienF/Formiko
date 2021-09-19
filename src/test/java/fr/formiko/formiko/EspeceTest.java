package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;

public class EspeceTest extends TestCaseMuet{
  // FUNCTIONS -----------------------------------------------------------------
  private Fourmi ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    assertTrue(p.getGc().getCCase(0,0).getContent().getFere().equals(j.getFere()));
    assertTrue(p.getGc().getCCase(0,1).getContent().getFere()==null);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f);
    assertEquals(1,j.getFere().getGc().length());
    return f;
  }
  @Test
  public void testgetIndividuByType(){
    Fourmi f = ini();
    assertEquals(0,f.getTypeF());
    assertEquals(200,(int)(f.getIndividu().getTaille()));
    assertEquals(50,f.getIndividu().getActionMax());
    assertEquals(3,f.getIndividu().getNourritureConso(f.getStade()));

    f.setTypeF((byte)3);
    assertEquals(3,(int)(f.getIndividu().getTaille()));
    assertEquals(35,f.getIndividu().getActionMax());
    assertEquals(1,f.getIndividu().getNourritureConso(f.getStade()));
  }
}
