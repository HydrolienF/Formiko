package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;

public class GJoueurTest extends TestCaseMuet {
  private Joueur j,j2,j3,j4;
  private Fourmi f1,f2,f3,f4,f5,f6;
  private void ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GSquare(2,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    j = new Joueur(new Fourmiliere(p.getGc().getCSquare(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    j2 = new Joueur(new Fourmiliere(p.getGc().getCSquare(0,1),null),"joueurTest2",false);
    j2.getFere().setJoueur(j2);
    j3 = new Joueur(new Fourmiliere(p.getGc().getCSquare(1,1),null),"joueurTest3",false);
    j3.getFere().setJoueur(j3);
    j4 = new Joueur(new Fourmiliere(p.getGc().getCSquare(1,1),null),"joueurTest4",false);
    j4.getFere().setJoueur(j4);
    p.getGj().add(j);
    p.getGj().add(j2);
    p.getGj().add(j3);
    p.getGj().add(j4);
    f1 = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f1);
    f2 = new Fourmi(j2.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j2.getFere().getGc().add(f2);
    f3 = new Fourmi(j3.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j3.getFere().getGc().add(f3);
    f3.setPheromone(f1.getPheromone());
    f4 = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f4);
    f5 = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f5);
    f6 = new Fourmi(j3.getFere(),Main.getEspeceById(0), (byte) 0, (byte) 0);
    j3.getFere().getGc().add(f6);
  }
  @Test
  public void testGetNbrDeJoueurVivant(){
    ini();
    assertEquals(3,Main.getPartie().getGj().getNbrDeJoueurVivant());
    f2.mourir();
    assertEquals(2,Main.getPartie().getGj().getNbrDeJoueurVivant());
    f1.mourir();
    assertEquals(2,Main.getPartie().getGj().getNbrDeJoueurVivant());
    f5.mourir();
    assertEquals(2,Main.getPartie().getGj().getNbrDeJoueurVivant());
    f4.mourir();
    assertEquals(1,Main.getPartie().getGj().getNbrDeJoueurVivant());
  }
}
