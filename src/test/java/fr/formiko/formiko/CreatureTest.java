package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.GSquare;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.interfaces.ChasseGranivoreTest;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.exceptions.NotNullLocationException;

public class CreatureTest extends TestCaseMuet {

  // FUNCTIONS -----------------------------------------------------------------
  private void ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GSquare(1,5),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
  }
  private Fourmi iniF(){
    Partie p;
    Main.initialisation();
    p = new Partie(0,100,new Carte(new GSquare(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCSquare(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    assertTrue(p.getGc().getCSquare(0,0).getContent().getFere().equals(j.getFere()));
    assertTrue(p.getGc().getCSquare(0,1).getContent().getFere()==null);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    j.getFere().getGc().add(f);
    assertEquals(1,j.getFere().getGc().length());
    return f;
  }
  private Fourmi iniGranivoreAnt(){
    return ChasseGranivoreTest.ini();
  }
  @Test
  public void testSetCSquare(){
    ini();
    Creature g1 = new Insecte(Main.getGc().getCSquare(0,0));
    assertEquals(Main.getCSquare(0,0), g1.getCSquare());
    Creature g2 = new Fourmi();
    assertEquals(null, g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,0));
    assertEquals(Main.getCSquare(0,0), g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,3));
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
    g2.setCSquare(Main.getCSquare(100,10));
    assertEquals(null, g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,3));
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
    g1.setCSquare(Main.getCSquare(0,3));
    assertEquals(Main.getCSquare(0,3), g1.getCSquare());
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
  }
  @Test
  public void testSetTransported(){
    ini();
    Creature c = new Insecte(Main.getGc().getCSquare(0,0));
    Graine g1 = new Graine(Main.getGc().getCSquare(0,0),100,(byte)10);
    Graine g2 = new Graine(Main.getGc().getCSquare(0,0),100,(byte)11);
    assertEquals(null,c.getTransported());
    c.setTransported(g1);
    assertEquals(g1,c.getTransported());
    assertThrows(NotNullLocationException.class, () -> {
        c.setTransported(g2);
    });
    assertEquals(g1,c.getTransported());
    c.setTransported(null);
    assertEquals(null,c.getTransported());
    c.setTransported(g2);
    assertEquals(g2,c.getTransported());

  }
  @Test
  public void testIsHungry(){
    Main.initialisation();
    GSquare gc = new GSquare(1,1);
    Insecte i = new Insecte(gc.getCSquareAlléa());
    i.setMaxFood(10);
    i.setFood(5);
    assertTrue(!i.isHungry(0));
    assertTrue(!i.isHungry(20));
    assertTrue(!i.isHungry(40));
    assertTrue(!i.isHungry(49));
    assertTrue(!i.isHungry(50));
    assertTrue(i.isHungry(51));
    assertTrue(i.isHungry(80));
    assertTrue(i.isHungry(100));
    //exception
    assertTrue(!i.isHungry(-20));
    assertTrue(i.isHungry(200));
    //more than full (should be correct by setFood(1) when setMaxFood(1)).
    i.setFood(5);
    i.setMaxFood(1);
    assertTrue(!i.isHungry(0));
    assertTrue(!i.isHungry(100));
    i.setMaxFood(10);
    i.setFood(9);
    assertTrue(!i.isHungry(89));
    assertTrue(i.isHungry(100));
    i.setMaxFood(100);
    i.setFood(2);
    assertTrue(!i.isHungry(2));
    assertTrue(i.isHungry(3));
    i.setMaxFood(101);
    assertTrue(!i.isHungry(1));
    assertTrue(i.isHungry(2));
    assertTrue(i.isHungry(3));
  }
  @Test
  public void testFriendlyLevel(){
    Fourmi f = new Fourmi();
    f.setPheromone(new Pheromone(0,0,0));
    f.setPheromoneTolerence((byte)5);
    Fourmi f2 = new Fourmi();
    f2.setPheromone(new Pheromone(100,0,0));
    f2.setPheromoneTolerence((byte)5);
    //enemy
    assertEquals(-1,f.friendlyLevel(f2));
    assertEquals(-1,f2.friendlyLevel(f));
    //ally
    f.setPheromone(new Pheromone(99,0,0));
    assertEquals(1,f.friendlyLevel(f2));
    assertEquals(1,f2.friendlyLevel(f));
    //only 1 ally
    f2.setPheromoneTolerence((byte)0);
    assertEquals(1,f.friendlyLevel(f2));
    assertEquals(-1,f2.friendlyLevel(f));
  }
  @Test
  public void testFriendlyLevel2(){
    Fourmi f = new Fourmi();
    f.setPheromone(new Pheromone(0,0,0));
    f.setPheromoneTolerence((byte)5);
    Insecte i = new Insecte();
    i.setType(2); //puceron
    i.setPheromone(new Pheromone(50,0,100));
    i.setPheromoneTolerence((byte)5);
    //neutral ant / neutral insect
    assertEquals(-1,f.friendlyLevel(i)); //TODO ant should be friendly with this type of insect
    // assertEquals(0,f.friendlyLevel(i));
    assertEquals(-1,i.friendlyLevel(f));
    //ally
    i.setPheromone(new Pheromone(0,0,0));
    assertEquals(1,f.friendlyLevel(i));
    assertEquals(1,i.friendlyLevel(f));
    //ant friendly with an insect but insect not frienly with ant.
    i.setPheromoneTolerence((byte)0);
    i.setPheromone(new Pheromone(2,0,1));
    assertEquals(1,f.friendlyLevel(i));
    assertEquals(-1,i.friendlyLevel(f));
  }

  @Test
  public void testEat(){
    Fourmi f = iniGranivoreAnt();
    int gfood=55;
    Graine g=new Graine(Main.getGc().getCSquare(0,0),55,(byte)10);
    int food = f.getFood();
    f.eat(80);
    assertEquals(food+gfood, f.getFood());
    assertTrue(f.getTransported()==null);
  }
  @Test
  public void testEat2(){
    Fourmi f = iniGranivoreAnt();
    int gfood=55;
    Graine g=new Graine(Main.getGc().getCSquare(0,0),55,(byte)10);
    int food = f.getFood();
    f.eat(10);
    assertEquals(food, f.getFood());
    assertTrue(f.getTransported()==null);
  }
  @Test
  public void testEat3(){
    Fourmi f = iniGranivoreAnt();
    f.setFood(f.getMaxFood()-3);
    int gfood=55;
    Graine g=new Graine(Main.getGc().getCSquare(0,0),55,(byte)10);
    int food = f.getFood();
    f.eat(80);
    assertEquals(food, f.getFood());
    assertTrue(f.getTransported()==null);
  }
}
