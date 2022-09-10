package fr.formiko.views;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.GSquare;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usual.Os;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.views.ViewCLI;

public class ViewCLITest extends TestCaseMuet{
  private void ini(GSquare gc){
    Os.setOs(new Os());
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(gc,0,0,1,false,false),1);
    Main.setPartie(p);
  }
  @Test
  public void testGetCSquareFromString(){
    GSquare gc = new GSquare(1,1);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("a0"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("0a"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("a    0"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("a,0"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("0A"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("a 0 0"));
  }
  @Test
  public void testGetCSquareFromString2(){
    GSquare gc = new GSquare(2,2);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("a0"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("0a"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("a    0"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("a,0"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("0A"));
    assertEquals(gc.getCSquare(0,0),view.getCSquareFromString("a 0 0"));
  }
  @Test
  public void testGetCSquareFromString3(){
    GSquare gc = new GSquare(2,2);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(gc.getCSquare(1,0),view.getCSquareFromString("a1"));
    assertEquals(gc.getCSquare(1,0),view.getCSquareFromString("1a"));
    assertEquals(gc.getCSquare(1,0),view.getCSquareFromString("a    1"));
    assertEquals(gc.getCSquare(0,1),view.getCSquareFromString("b,0"));
    assertEquals(gc.getCSquare(0,1),view.getCSquareFromString("0B"));
    assertEquals(gc.getCSquare(1,1),view.getCSquareFromString("B01"));
  }
  @Test
  public void testGetCSquareFromString4(){
    GSquare gc = new GSquare(2,2);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(null,view.getCSquareFromString("c1"));
    assertEquals(null,view.getCSquareFromString("1"));
    assertEquals(null,view.getCSquareFromString("A"));
    assertEquals(null,view.getCSquareFromString("A un"));
  }
  @Test
  public void testGetCSquareFromString5(){
    GSquare gc = new GSquare(100,100);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(gc.getCSquare(1,25).getPoint(),view.getCSquareFromString("z1").getPoint());
    assertEquals(gc.getCSquare(99,0).getPoint(),view.getCSquareFromString("99a").getPoint());
    assertEquals(null,view.getCSquareFromString("100a"));
    assertEquals(gc.getCSquare(1,5).getPoint(),view.getCSquareFromString("f    1").getPoint());
    // assertEquals(null,view.getCSquareFromString("aa4")); //an update may resolve that.
  }
  @Test
  public void testGetCSquareFromString6(){
    GSquare gc = new GSquare(100,100);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(null,view.getCSquareFromString("ZM01"));
    assertEquals(gc.getCSquare(1,5).getPoint(),view.getCSquareFromString("f    1").getPoint());
    assertEquals(gc.getCSquare(4,26).getPoint(),view.getCSquareFromString("aa4").getPoint());
    assertEquals(gc.getCSquare(4,28).getPoint(),view.getCSquareFromString("a4C").getPoint());
    assertEquals(gc.getCSquare(0,64).getPoint(),view.getCSquareFromString("BM0").getPoint());
    assertEquals(gc.getCSquare(0,63).getPoint(),view.getCSquareFromString("BL0").getPoint());
  }
}
