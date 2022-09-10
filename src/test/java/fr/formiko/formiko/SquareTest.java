package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GSquare;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.fusual.FFolder;
import fr.formiko.usual.Os;
import fr.formiko.usual.g;
import fr.formiko.views.ViewNull;

public class SquareTest extends TestCaseMuet {
  private Partie p;
  // FUNCTIONS -----------------------------------------------------------------
  private Fourmi ini(){
    Os.setOs(new Os());
    Main.setFolder(new FFolder(Main.getView()));
    try {
      FFolder.getFolder().ini();
    }catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    }
    Main.iniOp();
    Main.initialisation();
    Main.setLanguage(0);
    p = new Partie(0,100,new Carte(new GSquare(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCSquare(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    j.getFere().getGc().add(f);
    p.getCarte().setSquaresSombres(true);
    p.getCarte().setSquaresNuageuses(true);
    return f;
  }
  @Test
  public void testToString(){
    try{
      Fourmi f = ini();
      p.setPlayingAnt(f);
      Joueur j = Main.getGj().getFirst();
      j.initialisationSquareNS();
      j.updateSquareSN();
      assertEquals(0,Main.getOp().getLanguage());
      //show everything
      assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
      assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmi")));
      //do not show anything
      j.setSquareNuageuse(0,0,true);
      // assertTrue("".equals(Main.getGc().getCSquare(0,0).getContent().toString()));
      assertTrue(!Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
      assertTrue(!Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("creatures")+" : "));
      //show only stable information (no insecte & no ant.)
      j.setSquareNuageuse(0,0,false);
      j.setSquareSombre(0,0,true);
      assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
      assertTrue(!Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("creatures")+" : "));

    }catch (StackOverflowError e) {
      e.printStackTrace();
    }
  }
  //I can't get why this test exist.
  // @Test
  // public void testToString2(){
  //   Fourmi f = ini();
  //   Joueur j = Main.getGj().getFirst();
  //   j.initialisationSquareNS();
  //   j.updateSquareSN();
  //   //show everything
  //   assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
  //   assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmi")));
  //   j.setSquareNuageuse(0,0,true);
  //   assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
  //   assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmi")));
  //   j.setSquareNuageuse(0,0,false);
  //   j.setSquareSombre(0,0,true);
  //   assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
  //   assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmi")));
  // }
  @Test
  public void testToString3(){
    Fourmi f = ini();
    Joueur j = Main.getGj().getFirst();
    p.getCarte().setSquaresNuageuses(false);
    p.setPlayingAnt(f);
    j.initialisationSquareNS();
    j.updateSquareSN();
    //show everything
    assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmi")));
    j.setSquareNuageuse(0,0,true);
    assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmi")));
    j.setSquareNuageuse(0,0,true);
    j.setSquareSombre(0,0,true);
    assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(!Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("creatures")+" : "));
    //show only stable information (no insecte & no ant.)
    j.setSquareNuageuse(0,0,false);
    j.setSquareSombre(0,0,true);
    assertTrue(Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(!Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("creatures")+" : "));
    assertTrue(!Main.getGc().getCSquare(0,0).getContent().toString().contains(g.get("graines")+" : "));
  }
  @Test
  public void testSetFoodInsecte(){
    Fourmi f = ini();
    Square c = f.getSquare();
    int x = c.getFoodInsecte();
    c.setFoodInsecteMax((byte)50);
    c.setFoodInsecte(0);
    assertEquals(0, c.getFoodInsecte());
    c.setFoodInsecte(10);
    assertEquals(10, c.getFoodInsecte());
    c.setFoodInsecte(8);
    assertEquals(8, c.getFoodInsecte());
    c.setFoodInsecte(13);
    assertEquals(13, c.getFoodInsecte());
    c.setFoodInsecte(50);
    assertEquals(50, c.getFoodInsecte());
    c.setFoodInsecte(51);
    assertEquals(50, c.getFoodInsecte());
    c.setFoodInsecte(-1);
    assertEquals(0, c.getFoodInsecte());
  }
  @Test
  public void testAddFoodInsecte(){
    Fourmi f = ini();
    Square c = f.getSquare();
    int x = c.getFoodInsecte();
    c.setFoodInsecteMax((byte)50);
    c.setFoodInsecte(0);
    c.addFoodInsecte(10);
    assertEquals(10, c.getFoodInsecte());
    c.addFoodInsecte(10);
    assertEquals(20, c.getFoodInsecte());
    c.addFoodInsecte(1);
    assertEquals(21, c.getFoodInsecte());
    c.addFoodInsecte(-2);
    assertEquals(21, c.getFoodInsecte());
  }
  @Test
  public void testRemoveFoodInsecte(){
    Fourmi f = ini();
    Square c = f.getSquare();
    int x = c.getFoodInsecte();
    c.setFoodInsecteMax((byte)50);
    c.setFoodInsecte(50);
    assertEquals(50, c.getFoodInsecte());
    c.removeFoodInsecte(10);
    assertEquals(40, c.getFoodInsecte());
    c.removeFoodInsecte(10);
    assertEquals(30, c.getFoodInsecte());
    c.removeFoodInsecte(1);
    assertEquals(29, c.getFoodInsecte());
    c.removeFoodInsecte(-1);
    assertEquals(29, c.getFoodInsecte());
  }
  @Test
  public void testFoodInsecteWithView(){
    Fourmi f = ini();
    Square c = f.getSquare();
    int x = c.getFoodInsecte();
    c.setFoodInsecteMax((byte)50);
    Main.setView(new ViewNull(){
      @Override
      public boolean isBladesEnable(){return true;}
    });
    c.setFoodInsecteMax((byte)100);
    c.setFoodInsecte(0);
    assertEquals(0,c.getGb().length());
    c.setFoodInsecte(23);
    assertEquals(23,c.getGb().length());
    c.removeFoodInsecte(3);
    assertEquals(20,c.getGb().length());
    c.addFoodInsecte(10);
    assertEquals(30,c.getGb().length());
    c.removeFoodInsecte(3);
    assertEquals(27,c.getGb().length());
    c.removeFoodInsecte(3);
    assertEquals(24,c.getGb().length());
  }
  @Test
  public void testGetSortedGc(){
    Fourmi f = ini();
    f.setPheromone(10,10,10);
    Fourmi f2 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f3 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    f3.setPheromone(0,0,100);
    f2.setCSquare(f.getCSquare());
    f3.setCSquare(f.getCSquare());
    GCreature gc = f.getCSquare().getContent().getSortedGc(f);
    Creature tc [] = new Creature[gc.length()];
    tc[0]=f;
    tc[1]=f2;
    tc[2]=f3;
    assertArrayEquals(tc,gc.toArray());
  }
  @Test
  public void testGetSortedGc2(){
    Fourmi f = ini();
    f.setPheromone(10,10,10);
    Fourmi f2 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f3 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    f2.setPheromone(0,0,100);
    f2.setCSquare(f.getCSquare());
    f3.setCSquare(f.getCSquare());
    GCreature gc = f.getCSquare().getContent().getSortedGc(f);
    Creature tc [] = new Creature[gc.length()];
    tc[0]=f;
    tc[1]=f3;
    tc[2]=f2;
    assertArrayEquals(tc,gc.toArray());
  }
  @Test
  public void testGetSortedGcB(){
    Fourmi f = ini();
    f.setPheromone(10,10,10);
    Fourmi f3 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f2 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    f2.setPheromone(0,0,100);
    f3.setCSquare(f.getCSquare());
    f2.setCSquare(f.getCSquare());
    GCreature gc = f.getCSquare().getContent().getSortedGc(f);
    Creature tc [] = new Creature[gc.length()];
    tc[0]=f;
    tc[1]=f3;
    tc[2]=f2;
    assertArrayEquals(tc,gc.toArray());
  }
  @Test
  public void testGetSortedGc2B(){
    Fourmi f = ini();
    f.setPheromone(10,10,10);
    Fourmi f3 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f2 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    // f3.setPheromone(0,0,100);
    f3.setCSquare(f.getCSquare());
    f2.setCSquare(f.getCSquare());
    GCreature gc = f.getCSquare().getContent().getSortedGc(f);
    Creature tc [] = new Creature[gc.length()];
    tc[0]=f;
    tc[1]=f3;
    tc[2]=f2;
    assertArrayEquals(tc,gc.toArray());
  }
  @Test
  public void testGetSortedGc3(){
    Fourmi f = ini();
    f.setPheromone(10,10,10);
    Fourmi f2 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f3 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f4 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    f3.setPheromone(0,0,100);
    f2.setCSquare(f.getCSquare());
    f3.setCSquare(f.getCSquare());
    f4.setCSquare(f.getCSquare());
    GCreature gc = f.getCSquare().getContent().getSortedGc(f);
    Creature tc [] = new Creature[gc.length()];
    tc[0]=f;
    tc[1]=f2;
    tc[2]=f4;
    tc[3]=f3;
    assertArrayEquals(tc,gc.toArray());
  }
  @Test
  public void testGetSortedGc4(){
    Fourmi f = ini();
    f.setPheromone(10,10,10);
    Fourmi f2 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f3 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f4 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Insecte i = new Insecte();
    i.setType(2);
    i.setPheromone(100,100,67);
    f3.setPheromone(0,0,100);
    f2.setCSquare(f.getCSquare());
    f3.setCSquare(f.getCSquare());
    f4.setCSquare(f.getCSquare());
    i.setCSquare(f.getCSquare());
    GCreature gc = f.getCSquare().getContent().getSortedGc(f);
    Creature tc [] = new Creature[gc.length()];
    tc[0]=f;
    tc[1]=f2;
    tc[2]=f4;
    tc[3]=f3;
    tc[4]=i;
    assertArrayEquals(tc,gc.toArray());
  }
  @Test
  public void testGetSortedGc5(){
    Fourmi f = ini();
    f.setPheromone(10,10,10);
    Fourmi f2 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f3 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Fourmi f4 = new Fourmi(f.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    Insecte i = new Insecte();
    i.setType(2);
    i.setPheromone(100,100,67);
    f3.setPheromone(0,0,100);
    f4.setCSquare(f.getCSquare());
    f2.setCSquare(f.getCSquare());
    i.setCSquare(f.getCSquare());
    f3.setCSquare(f.getCSquare());
    GCreature gc = f.getCSquare().getContent().getSortedGc(f);
    Creature tc [] = new Creature[gc.length()];
    tc[0]=f;
    tc[1]=f4;
    tc[2]=f2;
    tc[3]=i;
    tc[4]=f3;
    assertArrayEquals(tc,gc.toArray());
  }
}