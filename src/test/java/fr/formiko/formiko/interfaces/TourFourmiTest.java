package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.exceptions.ClassTypeException;
import fr.formiko.tests.TestCaseMuet;

public class TourFourmiTest extends TestCaseMuet{
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
    f.tour = new TourFourmi();
    ((TourFourmi)(f.tour)).setF(f);
    return f;
  }
  private Fourmi ini2(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(5,5),0,0,1,false,false),1);
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
    f.setHealth(20);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(20,f.getHealth());
    f.setAction(1);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(20+f.getIndividu().getNétoyage(),f.getHealth());
    f.setAction(2);//coutNetoyer =3.
    f.setHealth(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(f.getIndividu().getNétoyage(),f.getHealth());
    f.setAction(3);//coutNetoyer =3.
    f.setHealth(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(f.getIndividu().getNétoyage(),f.getHealth());
    f.setAction(4);//coutNetoyer =3.
    f.setHealth(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(2*f.getIndividu().getNétoyage(),f.getHealth());
    f.setAction(9);//coutNetoyer =3.
    f.setHealth(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(2*f.getIndividu().getNétoyage(),f.getHealth());
    assertEquals(3,f.getAction());
    f.setAction(10);//coutNetoyer =3.
    f.setHealth(0);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(60,f.getHealth());
    assertEquals(4,f.getAction());
    assertTrue(f.getSeuilDeRisqueDInfection()<60);

    //with a different difficulty level
    //higth level player
    Main.getPartie().setDifficulté((byte)3);
    assertEquals(70,f.getSeuilDeRisqueDInfection());
    f.setHealth(0);
    f.setAction(15);//coutNetoyer =3.
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(90,f.getHealth());
    assertEquals(6,f.getAction());
    //higth level ai
    f.getFere().getJoueur().setIa(true);
    f.setHealth(0);
    f.setAction(9);//coutNetoyer =3.
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(30,f.getHealth());
    assertEquals(6,f.getAction());
    //low level player
    f.getFere().getJoueur().setIa(false);
    Main.getPartie().setDifficulté((byte)-4);
    assertEquals(10,f.getSeuilDeRisqueDInfection());
    f.setHealth(0);
    f.setAction(9);//coutNetoyer =3.
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(30,f.getHealth());
    assertEquals(6,f.getAction());
    f.getFere().getJoueur().setIa(true);
    //low level ai
    f.setHealth(0);
    f.setAction(9);//coutNetoyer =3.
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(90,f.getHealth());
    assertEquals(0,f.getAction());

    //with NetoyerNull
    f.netoyer = new NetoyerNull();
    f.setHealth(0);
    f.setAction(6);
    ((TourFourmi)(f.tour)).cleanItself();
    assertEquals(0,f.getHealth());
    assertEquals(6,f.getAction());


  }
  @Test
  public void testFeedOther(){
    //aNourrir is tested.
    Fourmi f = ini();
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f2.setMaxFood(10);
    f2.setFood(1);
    f.setMaxFood(100);
    f.setFood(100);
    f2.setCc(Main.getGc().getCCase(0,0));
    //ici ca coupe sans message d'erreur...
    assertTrue(f2.equals(((TourFourmi)(f.tour)).aNourrir()));
    assertTrue(!(f.trophallaxie instanceof TrophallaxieNull));
    ((TourFourmi)(f.tour)).feedOther(10);
    assertEquals(91,f.getFood());
    assertEquals(10,f2.getFood());

    f2.setFood(1);
    f.setFood(11);
    ((TourFourmi)(f.tour)).feedOther(10);
    assertEquals(10,f.getFood());
    assertEquals(2,f2.getFood());

    f2.setFood(1);
    f.setFood(11);
    ((TourFourmi)(f.tour)).feedOther(15);
    assertEquals(11,f.getFood());
    assertEquals(1,f2.getFood());

    f2.setFood(1);
    f.setFood(11);
    ((TourFourmi)(f.tour)).feedOther(5);
    assertEquals(5,f.getFood());
    assertEquals(7,f2.getFood());
  }
  @Test
  public void testANourrir(){
    Fourmi f2; Fourmi f3;
    Fourmi f = ini();
    //if not in the same Case :
    f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f2.setCc(Main.getGc().getCCase(0,1));
    f2.setFood(0);
    assertEquals(null,((TourFourmi)(f.tour)).aNourrir());
    //in the same case but not wantFood :
    f2.setCc(Main.getGc().getCCase(0,0));
    f2.setFood(f2.getMaxFood());
    assertEquals(null,((TourFourmi)(f.tour)).aNourrir());
    //in the same case & wantFood :
    f2.setCc(Main.getGc().getCCase(0,0));
    assertEquals(2,Main.getGc().getCCase(0,0).getContent().getGc().length());
    assertEquals(0,Main.getGc().getCCase(0,1).getContent().getGc().length());
    f2.setFood(0);
    assertTrue(f2.wantFood());
    assertEquals(f2,((TourFourmi)(f.tour)).aNourrir());


    //2 Ant want food :
    f3 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f3.setCc(Main.getGc().getCCase(0,0));
    f3.setFood(0);
    f2.setFood(0);
    f2.setCc(Main.getGc().getCCase(0,1));
    f2.setCc(Main.getGc().getCCase(0,0));
    assertEquals(3,Main.getGc().getCCase(0,0).getContent().getGc().length());
    assertEquals(0,Main.getGc().getCCase(0,1).getContent().getGc().length());
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(f3) || ((TourFourmi)(f.tour)).aNourrir().equals(f2));

    //f3.mourir(-1); ca fait bugué je sais pas pourquoi...
    //f3 = new Fourmi(f.getFere(),f.getEspece(), (byte) 0, (byte) 0);
    f3.setCc(Main.getGc().getCCase(0,1));
    f3.setCc(Main.getGc().getCCase(0,0));
    f3.setFood(0);
    f2.setFood(0);
    assertEquals(3,Main.getGc().getCCase(0,0).getContent().getGc().length());
    assertEquals(0,Main.getGc().getCCase(0,1).getContent().getGc().length());
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(f3) || ((TourFourmi)(f.tour)).aNourrir().equals(f2));

    //1 Creature + 1 Qeen wantFood :
    f3.setCc(Main.getGc().getCCase(0,1));
    f2.setCc(Main.getGc().getCCase(0,1));
    Fourmi f4 = new Fourmi(f.getFere(),f.getEspece(), (byte) 0, (byte) 0);
    f4.setCc(Main.getPartie().getGc().getCCase(0,0));
    Insecte i = new Insecte(Main.getPartie().getGc().getCCase(0,0),0,100,0);
    i.setFood(0);
    i.setPheromone(f4.getPheromone());
    f4.setFood(0);
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(f4));
    //1 Creature wantFood & 1 queen don't wantFood :
    f4.setFood(f4.getMaxFood());
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(i));

    //1 Creature wantFood & 1 Ant don't wantFood :
    f4.setCc(Main.getGc().getCCase(0,1));
    f2.setCc(Main.getGc().getCCase(0,0));
    f2.setFood(2);
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(i) || ((TourFourmi)(f.tour)).aNourrir().equals(f2));
    i.setFood(i.getMaxFood());
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(f2));
    f2.setCc(Main.getGc().getCCase(0,1));

    //3 creature wantFood & all of them can be feed :
    Insecte i2 = new Insecte(Main.getPartie().getGc().getCCase(0,0),0,100,0);
    i2.setPheromone(i.getPheromone());
    new Insecte(Main.getPartie().getGc().getCCase(0,0),0,100,0);
    assertTrue(((TourFourmi)(f.tour)).aNourrir().equals(i2));

    //not with the same Pheromone : test filterAlliés.
    //not hungry : test filterFaimMax.
    //wantFood : test filterWantFood
  }
  @Test
  public void testCleanOther(){
    Fourmi f = ini();
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f2.setHealth(0);
    f.setAction(1);
    ((TourFourmi)(f.tour)).cleanOther();
    assertTrue(f.getAction()<=0);
    f2.setHealth(70);
    f.setAction(1);
    ((TourFourmi)(f.tour)).cleanOther();
    assertEquals(1,f.getAction());
  }
  @Test
  public void testANetoyer(){
    Fourmi f = ini();
    assertEquals(null,((TourFourmi)(f.tour)).aNetoyer());
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    assertEquals(null,((TourFourmi)(f.tour)).aNetoyer());
    f2.setHealth(40);
    assertEquals(f2,((TourFourmi)(f.tour)).aNetoyer());
    Insecte i = new Insecte(Main.getPartie().getGc().getCCase(0,0),0,100,0);
    i.setFood(0);
    i.setPheromone(f.getPheromone());
    f2.setHealth(100);
    assertEquals(i,((TourFourmi)(f.tour)).aNetoyer());
  }
  @Test
  public void testBackHome(){
    Fourmi f = ini2();

    f.getFere().setCc(Main.getGc().getCCase(4,4));
    f.setCc(Main.getGc().getCCase(0,0));
    f.setAction(1);
    ((TourFourmi)(f.tour)).backHome();
    assertTrue(f.getCCase().equals(Main.getGc().getCCase(1,1)));
    assertTrue(f.getAction()<1);

    f.setCc(Main.getGc().getCCase(0,0));
    f.setAction(f.getIndividu().getCoutDéplacement()*2);
    ((TourFourmi)(f.tour)).backHome();
    assertTrue(f.getCCase().equals(Main.getGc().getCCase(2,2)));

    f.setCc(Main.getGc().getCCase(0,0));
    f.setAction(f.getIndividu().getCoutDéplacement()*5);
    ((TourFourmi)(f.tour)).backHome();
    assertTrue(f.getCCase().equals(Main.getGc().getCCase(4,4)));
  }
  @Test
  public void testEndTurn(){
    Fourmi f = ini();
    f.setTypeF((byte)3);
    f.setAction(f.getMaxAction());
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(0,f.getAction());
    f.setAction(2);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(0,f.getAction());
    f.setAction(-2);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(-2,f.getAction());

    f.setAge(0);
    f.setHealth(100);
    f.setFood(50);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(1,f.getAge());
    assertTrue(100>f.getHealth());
    assertEquals(49,f.getFood());
    //died by age.
    f.setAge(f.getMaxAge()-1);
    f.setHealth(100);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(1,Main.getGc().getCCase(0,0).getContent().getGc().length());
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(0,Main.getGc().getCCase(0,0).getContent().getGc().length());
    //died by health
    f = ini();
    f.setHealth(0);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(0,Main.getGc().getCCase(0,0).getContent().getGc().length());
    //died by food
    f = ini();
    f.setTypeF((byte)3);
    f.setFood(1);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(1,Main.getGc().getCCase(0,0).getContent().getGc().length());
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(0,Main.getGc().getCCase(0,0).getContent().getGc().length());
    f = ini();
    f.setTypeF((byte)0);
    f.setFood(1);
    assertEquals(1,Main.getGc().getCCase(0,0).getContent().getGc().length());
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(0,Main.getGc().getCCase(0,0).getContent().getGc().length());
    //egg don't need food.
    f = ini();
    f.setTypeF((byte)3);
    f.setStade(-3);
    f.setAge(0);
    f.setHealth(100);
    f.setFood(50);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(1,f.getAge());
    assertEquals(100,f.getHealth());
    assertEquals(50,f.getFood());

    f.setStade(-2);
    f.setAge(0);
    f.setHealth(100);
    f.setFood(50);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(1,f.getAge());
    assertTrue(f.getHealth()<100);
    assertEquals(49,f.getFood());

    f.setStade(-1);
    f.setAge(0);
    f.setHealth(100);
    f.setFood(50);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(1,f.getAge());
    assertEquals(100,f.getHealth());
    assertEquals(49,f.getFood());

    f = ini();
    f.setTypeF((byte)0);//queen
    f.setAge(0);
    f.setHealth(100);
    f.setFood(50);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(1,f.getAge());
    assertTrue(100>f.getHealth());
    assertEquals(3,f.getIndividu().getFoodConso(f.getStade()));
    assertEquals(47,f.getFood());

    f = ini();
    f.setStade(-1);
    f.setAge(f.getMaxAge()-1);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(-1,f.getStade());
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(0,f.getStade());
    f.setFood(100);
    f.setHealth(100);
    assertEquals(1,Main.getGc().getCCase(0,0).getContent().getGc().length());
    assertTrue(f.tour instanceof TourFourmi);
    //because it evoluer TourFourmi is a new TourFourmi !
    ((TourFourmi)(f.tour)).setF(f);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(1,Main.getGc().getCCase(0,0).getContent().getGc().length());
    assertEquals(0,f.getStade());
  }

  @Test
  public void testEndTurn2(){
    Fourmi f = ini();
    f.setTypeF((byte)3);
    f.setAction(f.getMaxAction());
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(0,f.getAction());
    assertEquals(Main.getPartie().getTour(),f.getLastTurnEnd());
    f.setAction(2);
    Main.getPartie().addTour();
    assertEquals(Main.getPartie().getTour()-1,f.getLastTurnEnd());
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(Main.getPartie().getTour(),f.getLastTurnEnd());
    assertEquals(0,f.getAction());
    f.setAction(-2);
    Main.getPartie().addTour();
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(-2,f.getAction());
    assertEquals(Main.getPartie().getTour(),f.getLastTurnEnd());
    ((TourFourmi)(f.tour)).endTurn();
    assertEquals(Main.getPartie().getTour(),f.getLastTurnEnd());
  }

  @Test
  public void testUnTour(){
    assertThrows(NullPointerException.class, () -> { this.unTour2();});
    assertThrows(ClassTypeException.class, () -> { this.unTour3();});
  }
  public void unTour2(){
    Creature c = null;
    Tour tour = new TourFourmi();
    tour.unTour(c);
  }
  public void unTour3(){
    Creature c = new Insecte();
    Tour tour = new TourFourmi();
    tour.unTour(c);
  }

  @Test
  public void testTour1(){
    Fourmi f = ini();
    f.setFood(2);
    f.setHealth(50);
    f.setAction(10);
    ((TourFourmi)(f.tour)).tour();
    ((TourFourmi)(f.tour)).endTurn();
    assertTrue(f.getAction()<=0);
    assertEquals(2-f.getFoodConso(),f.getFood());
    assertTrue(f.getHealth()<50);
  }
  @Test
  public void testTour2(){
    Fourmi f = ini();
    f.setFood(2);
    f.setHealth(50);
    f.setAction(10);
    Insecte i = new Insecte(Main.getPartie().getGc().getCCase(0,0),0,100,0);
    int givenFood = i.getGivenFood();
    ((TourFourmi)(f.tour)).tour();
    ((TourFourmi)(f.tour)).endTurn();
    assertTrue(f.getAction()<=0);
    assertEquals(2-f.getFoodConso()+givenFood,f.getFood());
    assertTrue(f.getHealth()<50);
    assertTrue(i.getIsDead());
  }
  //TODO #167 faire bcp d'autres tests jusqu'a ce que la fourmi arrive a endTurn avec encore des actions.
  //public void testTour3(){
  @Test
  public void testTourNull(){
    Fourmi f = ini();
    assertTrue(f!=null);
    f.setTypeF((byte)3);
    assertEquals(25,f.getIndividu().getNétoyage());
    //without action :
    f.setAction(0);
    f.setFood(19);
    assertEquals(0,f.getAge());
    assertEquals(0,f.getAction());
    assertEquals(19,f.getFood());
    assertEquals(100,f.getHealth());
    Main.getPartie().addTour();
    f.tour();
    f.endTurn();
    assertEquals(1,f.getAge());
    assertEquals(0,f.getAction());
    assertEquals(18,f.getFood());
    assertTrue(f.getHealthPerdu()!=0);
    assertEquals(100-f.getHealthPerdu(),f.getHealth());

    f.setAction(0);
    f.setTypeF((byte)0);
    Main.getPartie().addTour();
    f.tour();
    f.endTurn();
    assertEquals(2,f.getAge());
    assertEquals(0,f.getAction());
    assertEquals(15,f.getFood());
  }
}
