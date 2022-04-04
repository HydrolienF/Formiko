package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.interfaces.TourInsecte;
import fr.formiko.tests.TestCaseMuet;

public class TourInsecteTest extends TestCaseMuet{
  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testUnTour(){ //to check that an Insect will eat if he is safe and alone.
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    //if it was a dead insect
    Insecte i = new Insecte(p.getGc().getCCase(0,0),0,100,0);
    assertTrue(!(i.tour instanceof TourInsecte));
    assertTrue(i.tour instanceof TourCreatureMorte);
    i.supprimerDeLaCarte();
    //with a alive insect
    i = new Insecte(p.getGc().getCCase(0,0),0,100,1);
    i.setFood(10);
    i.setMaxFood(100);
    i.setfoodEatable(2);
    p.getGc().getCCase(0,0).getContent().setFoodInsecteMax((byte)50);
    p.getGc().getCCase(0,0).getContent().setFoodInsecte((byte)10);
    p.getGc().getCCase(0,0).getContent().setFoodInsecteParTour((byte)1);
    p.getGc().getCCase(0,1).getContent().setFoodInsecteMax((byte)50);
    p.getGc().getCCase(0,1).getContent().setFoodInsecte((byte)4);
    p.getGc().getCCase(0,1).getContent().setFoodInsecteParTour((byte)1);
    //everything is ok in initialisation :
    int x=10;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    assertTrue(p.getGc().getCCase(0,0).getContent().getGc().getHead().getContent().getId()==i.getId());
    assertTrue(i.tour instanceof TourInsecte);

    //if insect hungry.
    i.preTurn();i.tour();
    x+=-2;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    p.getGc().getCCase(0,0).getContent().updateFoodInsecte();
    x+=1;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    i.preTurn();i.tour();
    x+=-2;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    i.preTurn();i.tour();
    x+=-2;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    i.preTurn();i.tour();
    x+=-2;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    i.preTurn();i.tour();
    x+=-2;
    //x=1 1<2 so he move to the other Case.
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    i.preTurn();i.tour();
    assertTrue(p.getGc().getCCase(0,1).equals(i.getCCase()));
    assertEquals(4,p.getGc().getCCase(0,1).getContent().getFoodInsecte());
    p.getGc().getCCase(0,0).getContent().updateFoodInsecte();//+1 case 0,0
    i.setAction(0);
    i.preTurn();i.tour();
    assertEquals(2,p.getGc().getCCase(0,1).getContent().getFoodInsecte());
    p.getGc().getCCase(0,0).getContent().updateFoodInsecte();//+1 case 0,0
    i.preTurn();i.tour();
    assertEquals(0,p.getGc().getCCase(0,1).getContent().getFoodInsecte());
    //no more food so he re-move.
    i.preTurn();i.tour();
    assertTrue(p.getGc().getCCase(0,0).equals(i.getCCase()));




    //if insect not hungry anymore.
    x=10;
    p.getGc().getCCase(0,0).getContent().setFoodInsecte((byte)x);
    p.getGc().getCCase(0,0).getContent().updateFoodInsecte();
    x+=1;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    i.setFood(i.getMaxFood());
    i.preTurn();i.tour();
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
  }
  @Test
  public void testUnTour2(){ //to check that an Insect will eat if he is safe and alone.
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    //with a alive insect 4 actions/turn
    Insecte i = new Insecte(p.getGc().getCCase(0,0),0,100,4);
    i.setFood(10);
    i.setMaxFood(100);
    i.setfoodEatable(2);
    p.getGc().getCCase(0,0).getContent().setFoodInsecteMax((byte)50);
    p.getGc().getCCase(0,0).getContent().setFoodInsecte((byte)50);
    p.getGc().getCCase(0,0).getContent().setFoodInsecteParTour((byte)1);
    int x=50;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());
    i.preTurn();i.tour();
    x-=8;
    assertEquals(x,p.getGc().getCCase(0,0).getContent().getFoodInsecte());

  }
  //TODO test that if i.getFood() * 2 > i.getMaxFood() insecte try to fined a partner. so that i.getFemelle()!=partner.getFemelle();
}
