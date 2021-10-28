package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.GCreature;
import fr.formiko.formiko.Main;
import fr.formiko.tests.TestCaseMuet;

public class GCreatureTest extends TestCaseMuet{
  private Joueur j,j2,j3;
  private Fourmi f1,f2,f3,f4,f5,f6;
  // FUNCTIONS -----------------------------------------------------------------
  private void ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(2,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    j2 = new Joueur(new Fourmiliere(p.getGc().getCCase(0,1),null),"joueurTest2",false);
    j2.getFere().setJoueur(j2);
    j3 = new Joueur(new Fourmiliere(p.getGc().getCCase(1,1),null),"joueurTest3",false);
    j3.getFere().setJoueur(j3);
    p.getGj().add(j);
    p.getGj().add(j2);
    p.getGj().add(j3);
    f1 = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f1);
    f2 = new Fourmi(j2.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j2.getFere().getGc().add(f2);
    f3 = new Fourmi(j3.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j3.getFere().getGc().add(f3);
    f3.setPheromone(f1.getPheromone());
    f4 = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f4);
    f5 = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f5);
    f6 = new Fourmi(j3.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j3.getFere().getGc().add(f6);
  }
  @Test
  public void iniTest(){
    ini();
    assertEquals(1,f1.getFere().getId());
    assertEquals(1,f4.getFere().getId());
    assertEquals(1,f5.getFere().getId());
    assertEquals(2,f2.getFere().getId());
    assertEquals(3,f3.getFere().getId());
    assertEquals(3,f6.getFere().getId());
  }
  @Test
  public void getFourmiParFereTest1(){
    ini();
    GCreature gc = new GCreature();
    assertEquals(null, gc.getFourmiParFere(null));
    assertEquals(null, gc.getFourmiParFere(new Fourmiliere()));
    assertEquals(null, gc.getFourmiParFere(j.getFere()));
    Fourmi f = (Fourmi)j.getFere().getGc().getFirst();
    gc.add(f);
    assertEquals(f, gc.getFourmiParFere(j.getFere()));
    gc = new GCreature();
    gc.add(j2.getFere().getGc().getFirst());
    assertEquals(null, gc.getFourmiParFere(j.getFere()));
    gc = new GCreature();
    gc.add(j3.getFere().getGc().getFirst());
    assertEquals(null, gc.getFourmiParFere(j.getFere()));
  }
  @Test
  public void getFourmiParFereTest2(){
    ini();
    GCreature gc = new GCreature();
    Fourmi f = (Fourmi)j.getFere().getGc().getFirst();
    gc.add(f);
    assertEquals(f, gc.getFourmiParFere(j.getFere()));
    Insecte i1 = new Insecte(Main.getGc().getCCase(1,0),0,100,1);
    Insecte i2 = new Insecte(Main.getGc().getCCase(1,0),0,101,1);
    gc.add(i1);
    assertEquals(f, gc.getFourmiParFere(j.getFere()));
    gc.add(i2);
    assertEquals(f, gc.getFourmiParFere(j.getFere()));
    gc.remove(f);
    assertEquals(null, gc.getFourmiParFere(j.getFere()));
    gc.add(f);
    assertEquals(f, gc.getFourmiParFere(j.getFere()));
  }

  @Test
  public void getFourmiParFereTest3(){
    ini();
    GCreature gc = new GCreature();
    Insecte i1 = new Insecte(Main.getGc().getCCase(1,0),0,100,1);
    Insecte i2 = new Insecte(Main.getGc().getCCase(1,0),0,101,1);
    gc.add(i1);
    gc.add(f2);
    gc.add(f1);
    gc.add(f5);
    gc.add(i2);
    gc.add(f4);
    gc.add(f3);
    gc.add(f6);
    //it should swap between 1, 4 & 5
    Main.setPlayingAnt(null);
    Joueur.setPlayingJoueur(null);
    assertEquals(f1, gc.getFourmiParFere(j.getFere()));
    Joueur.setPlayingJoueur(j2);
    assertEquals(f2, gc.getFourmiParFere(j2.getFere()));
    Joueur.setPlayingJoueur(j);
    Main.setPlayingAnt(null);
    assertEquals(f1, gc.getFourmiParFere(j.getFere()));
    assertEquals(f1, gc.getFourmiParFere(j.getFere()));
    Main.setPlayingAnt(f1);
    assertEquals(f4, gc.getFourmiParFere(j.getFere()));
    Main.setPlayingAnt(f4);
    assertEquals(f5, gc.getFourmiParFere(j.getFere()));
    Main.setPlayingAnt(f5);
    assertEquals(f1, gc.getFourmiParFere(j.getFere()));
    gc.remove(f1);
    assertEquals(f4, gc.getFourmiParFere(j.getFere()));
    Main.setPlayingAnt(f4);
    assertEquals(f5, gc.getFourmiParFere(j.getFere()));
    Main.setPlayingAnt(f5);
    assertEquals(f4, gc.getFourmiParFere(j.getFere()));
    gc.add(f1);
    assertEquals(f1, gc.getFourmiParFere(j.getFere()));
  }
}
