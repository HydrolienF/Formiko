package fr.formiko.formiko.interfaces;


import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.interfaces.TourInsecte;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;

public class TourFourmiTest extends TestCaseMuet{
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
    f.tour = new TourFourmi();
    ((TourFourmi)(f.tour)).setF(f);
    return f;
  }
  @Test
  public void testCleanItself(){
    Fourmi f = ini();
    assertTrue(f!=null);
    assertEquals(30,f.getIndividu().getNétoyage());
    //without action :
    f.setAction(0);
    f.setProprete(20);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(20,f.getProprete());
    f.setAction(1);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(20+f.getIndividu().getNétoyage(),f.getProprete());
    f.setAction(2);//coutNetoyer =3.
    f.setProprete(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(f.getIndividu().getNétoyage(),f.getProprete());
    f.setAction(3);//coutNetoyer =3.
    f.setProprete(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(f.getIndividu().getNétoyage(),f.getProprete());
    f.setAction(4);//coutNetoyer =3.
    f.setProprete(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(2*f.getIndividu().getNétoyage(),f.getProprete());
    f.setAction(9);//coutNetoyer =3.
    f.setProprete(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(2*f.getIndividu().getNétoyage(),f.getProprete());
    assertEquals(3,f.getAction());
    f.setAction(10);//coutNetoyer =3.
    f.setProprete(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(60,f.getProprete());
    assertEquals(4,f.getAction());
    assertTrue(f.getSeuilDeRisqueDInfection()<60);

    //with a different difficulty level
    //higth level player
    Main.getPartie().setDifficulté((byte)3);
    assertEquals(70,f.getSeuilDeRisqueDInfection());
    f.setProprete(0);
    f.setAction(15);//coutNetoyer =3.
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(90,f.getProprete());
    assertEquals(6,f.getAction());
    //higth level ai
    f.getFere().getJoueur().setIa(true);
    f.setProprete(0);
    f.setAction(9);//coutNetoyer =3.
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(30,f.getProprete());
    assertEquals(6,f.getAction());
    //low level player
    f.getFere().getJoueur().setIa(false);
    Main.getPartie().setDifficulté((byte)-4);
    assertEquals(10,f.getSeuilDeRisqueDInfection());
    f.setProprete(0);
    f.setAction(9);//coutNetoyer =3.
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(30,f.getProprete());
    assertEquals(6,f.getAction());
    f.getFere().getJoueur().setIa(true);
    //low level ai
    f.setProprete(0);
    f.setAction(9);//coutNetoyer =3.
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(90,f.getProprete());
    assertEquals(0,f.getAction());

    //with NetoyerNull
    f.netoyer = new NetoyerNull();
    f.setProprete(0);
    f.setAction(6);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(0,f.getProprete());
    assertEquals(6,f.getAction());


  }
  @Test
  public void testFeedOther(){
    //aNourrir is tested.
    Fourmi f = ini();
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f2.setNourritureMax(10);
    f2.setNourriture(1);
    f.setNourritureMax(100);
    f.setNourriture(100);
    f2.setCc(Main.getGc().getCCase(0,0));
    //ici ca coupe sans message d'erreur...
    assertTrue(f2.equals(((TourFourmi)(f.tour)).aNourrir()));
    assertTrue(!(f.trophallaxie instanceof TrophallaxieNull));
    ((TourFourmi)(f.tour)).feedOther(10);
    assertEquals(91,f.getNourriture());
    assertEquals(10,f2.getNourriture());

    f2.setNourriture(1);
    f.setNourriture(11);
    ((TourFourmi)(f.tour)).feedOther(10);
    assertEquals(10,f.getNourriture());
    assertEquals(2,f2.getNourriture());

    f2.setNourriture(1);
    f.setNourriture(11);
    ((TourFourmi)(f.tour)).feedOther(15);
    assertEquals(11,f.getNourriture());
    assertEquals(1,f2.getNourriture());

    f2.setNourriture(1);
    f.setNourriture(11);
    ((TourFourmi)(f.tour)).feedOther(5);
    assertEquals(5,f.getNourriture());
    assertEquals(7,f2.getNourriture());
  }
  @Test
  public void testANourrir(){
    Fourmi f2; Fourmi f3;
    Fourmi f = ini();
    //if not in the same Case :
    f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f2.setCc(Main.getGc().getCCase(0,1));
    f2.setNourriture(0);
    assertEquals(null,((TourFourmi)(f.tour)).aNourrir());
    //in the same case but not wantFood :
    f2.setCc(Main.getGc().getCCase(0,0));
    f2.setNourriture(f2.getNourritureMax());
    assertEquals(null,((TourFourmi)(f.tour)).aNourrir());
    //in the same case & wantFood :
    f2.setCc(Main.getGc().getCCase(0,0));
    assertEquals(2,Main.getGc().getCCase(0,0).getContenu().getGc().length());
    assertEquals(0,Main.getGc().getCCase(0,1).getContenu().getGc().length());
    f2.setNourriture(0);
    assertTrue(f2.wantFood());
    assertEquals(f2,((TourFourmi)(f.tour)).aNourrir());


    //2 Ant want food :
    f3 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f3.setCc(Main.getGc().getCCase(0,0));
    f3.setNourriture(0);
    f2.setNourriture(0);
    f2.setCc(Main.getGc().getCCase(0,1));
    f2.setCc(Main.getGc().getCCase(0,0));
    assertEquals(3,Main.getGc().getCCase(0,0).getContenu().getGc().length());
    assertEquals(0,Main.getGc().getCCase(0,1).getContenu().getGc().length());
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(f3) || ((TourFourmi)(f.tour)).aNourrir().equals(f2));

    //f3.mourir(-1); ca fait bugué je sais pas pourquoi...
    //f3 = new Fourmi(f.getFere(),f.getEspece(), (byte) 0, (byte) 0);
    f3.setCc(Main.getGc().getCCase(0,1));
    f3.setCc(Main.getGc().getCCase(0,0));
    f3.setNourriture(0);
    f2.setNourriture(0);
    assertEquals(3,Main.getGc().getCCase(0,0).getContenu().getGc().length());
    assertEquals(0,Main.getGc().getCCase(0,1).getContenu().getGc().length());
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(f3) || ((TourFourmi)(f.tour)).aNourrir().equals(f2));

    //1 Creature + 1 Qeen wantFood :
    f3.setCc(Main.getGc().getCCase(0,1));
    f2.setCc(Main.getGc().getCCase(0,1));
    Fourmi f4 = new Fourmi(f.getFere(),f.getEspece(), (byte) 0, (byte) 0);
    f4.setCc(Main.getPartie().getGc().getCCase(0,0));
    Insecte i = new Insecte(Main.getPartie().getGc().getCCase(0,0),0,100,0);
    i.setNourriture(0);
    i.setPheromone(f4.getPheromone());
    f4.setNourriture(0);
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(f4));
    //1 Creature wantFood & 1 queen don't wantFood :
    f4.setNourriture(f4.getNourritureMax());
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(i));

    //1 Creature wantFood & 1 Ant don't wantFood :
    f4.setCc(Main.getGc().getCCase(0,1));
    f2.setCc(Main.getGc().getCCase(0,0));
    f2.setNourriture(2);
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(i) || ((TourFourmi)(f.tour)).aNourrir().equals(f2));
    i.setNourriture(i.getNourritureMax());
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(f2));
    f2.setCc(Main.getGc().getCCase(0,1));

    //3 creature wantFood & all of them can be feed :
    Insecte i2 = new Insecte(Main.getPartie().getGc().getCCase(0,0),0,100,0);
    i2.setPheromone(i.getPheromone());
    Insecte i3 = new Insecte(Main.getPartie().getGc().getCCase(0,0),0,100,0);
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(i2));

    //not with the same Pheromone : test filtreAlliés.
    //not hungry : test filtreFaimMax.
    //wantFood : test filtreWantFood
  }
  @Test
  public void testCleanOther(){

  }
  @Test
  public void testANetoyer(){

  }
  @Test
  public void testBackHomeAndShareFood(){

  }
  @Test
  public void testBackHome(){

  }
  @Test
  public void testFinTour(){

  }

  @Test
  public void testUnTour(){

  }
  @Test
  public void testTour(){

  }
  @Test
  public void testTourNull(){
    Fourmi f = ini();
    assertTrue(f!=null);
    assertEquals(30,f.getIndividu().getNétoyage());
    //without action :
    f.setAction(0);
    f.setNourriture(19);
    assertEquals(0,f.getAge());
    assertEquals(0,f.getAction());
    assertEquals(19,f.getNourriture());
    assertEquals(100,f.getProprete());
    f.tour();
    assertEquals(1,f.getAge());
    assertEquals(0,f.getAction());
    assertEquals(18,f.getNourriture());
    assertTrue(f.getPropretéPerdu()!=0);
    assertEquals(100-f.getPropretéPerdu(),f.getProprete());

  }
}
