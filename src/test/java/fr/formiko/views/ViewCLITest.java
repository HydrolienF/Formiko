package fr.formiko.views;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Graine;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.Os;
import fr.formiko.usuel.color;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.tests.TestCaseMuet;
import fr.formiko.views.ViewCLI;

public class ViewCLITest extends TestCaseMuet{
  private void ini(GCase gc){
    Main.setOs(new Os());
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(gc,0,0,1,false,false),1);
    Main.setPartie(p);
  }
  @Test
  public void testGetCCaseFromString(){
    GCase gc = new GCase(1,1);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("a0"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("0a"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("a    0"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("a,0"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("0A"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("a 0 0"));
  }
  @Test
  public void testGetCCaseFromString2(){
    GCase gc = new GCase(2,2);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("a0"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("0a"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("a    0"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("a,0"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("0A"));
    assertEquals(gc.getCCase(0,0),view.getCCaseFromString("a 0 0"));
  }
  @Test
  public void testGetCCaseFromString3(){
    GCase gc = new GCase(2,2);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(gc.getCCase(1,0),view.getCCaseFromString("a1"));
    assertEquals(gc.getCCase(1,0),view.getCCaseFromString("1a"));
    assertEquals(gc.getCCase(1,0),view.getCCaseFromString("a    1"));
    assertEquals(gc.getCCase(0,1),view.getCCaseFromString("b,0"));
    assertEquals(gc.getCCase(0,1),view.getCCaseFromString("0B"));
    assertEquals(gc.getCCase(1,1),view.getCCaseFromString("B01"));
  }
  @Test
  public void testGetCCaseFromString4(){
    GCase gc = new GCase(2,2);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(null,view.getCCaseFromString("c1"));
    assertEquals(null,view.getCCaseFromString("1"));
    assertEquals(null,view.getCCaseFromString("A"));
    assertEquals(null,view.getCCaseFromString("A un"));
  }
  @Test
  public void testGetCCaseFromString5(){
    GCase gc = new GCase(100,100);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(gc.getCCase(1,25).getPoint(),view.getCCaseFromString("z1").getPoint());
    assertEquals(gc.getCCase(99,0).getPoint(),view.getCCaseFromString("99a").getPoint());
    assertEquals(null,view.getCCaseFromString("100a"));
    assertEquals(gc.getCCase(1,5).getPoint(),view.getCCaseFromString("f    1").getPoint());
    // assertEquals(null,view.getCCaseFromString("aa4")); //an update may resolve that.
  }
  @Test
  public void testGetCCaseFromString6(){
    GCase gc = new GCase(100,100);
    ini(gc);
    ViewCLI view = new ViewCLI();
    //Main.setView(view);
    assertEquals(null,view.getCCaseFromString("ZM01"));
    assertEquals(gc.getCCase(1,5).getPoint(),view.getCCaseFromString("f    1").getPoint());
    assertEquals(gc.getCCase(4,26).getPoint(),view.getCCaseFromString("aa4").getPoint());
    assertEquals(gc.getCCase(4,28).getPoint(),view.getCCaseFromString("a4C").getPoint());
    assertEquals(gc.getCCase(0,64).getPoint(),view.getCCaseFromString("BM0").getPoint());
    assertEquals(gc.getCCase(0,63).getPoint(),view.getCCaseFromString("BL0").getPoint());
  }
}
