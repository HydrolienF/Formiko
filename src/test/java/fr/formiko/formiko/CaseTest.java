package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usuel.Folder;
import fr.formiko.usuel.Os;
import fr.formiko.usuel.g;
import fr.formiko.views.ViewNull;

public class CaseTest extends TestCaseMuet{
  private Partie p;
  // FUNCTIONS -----------------------------------------------------------------
  private Fourmi ini(){
    Main.setOs(new Os());
    Main.setFolder(new Folder());
    Main.getFolder().ini();
    Main.iniOp();
    Main.initialisation();
    Main.setLanguage(0);
    p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    j.getFere().getGc().add(f);
    p.getCarte().setCasesSombres(true);
    p.getCarte().setCasesNuageuses(true);
    return f;
  }
  @Test
  public void testToString(){
    Fourmi f = ini();
    p.setPlayingAnt(f);
    Joueur j = Main.getGj().getHead().getContent();
    j.initialisationCaseNS();
    j.updateCaseSN();
    assertEquals(0,Main.getOp().getLanguage());
    //show everything
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
    //do not show anything
    j.setCaseNuageuse(0,0,true);
    // assertTrue("".equals(Main.getGc().getCCase(0,0).getContent().toString()));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
    //show only stable information (no insecte & no ant.)
    j.setCaseNuageuse(0,0,false);
    j.setCaseSombre(0,0,true);
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
  }
  //I can't get why this test exist.
  // @Test
  // public void testToString2(){
  //   Fourmi f = ini();
  //   Joueur j = Main.getGj().getHead().getContent();
  //   j.initialisationCaseNS();
  //   j.updateCaseSN();
  //   //show everything
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
  //   j.setCaseNuageuse(0,0,true);
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
  //   j.setCaseNuageuse(0,0,false);
  //   j.setCaseSombre(0,0,true);
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
  // }
  @Test
  public void testToString3(){
    Fourmi f = ini();
    Joueur j = Main.getGj().getHead().getContent();
    p.getCarte().setCasesNuageuses(false);
    p.setPlayingAnt(f);
    j.initialisationCaseNS();
    j.updateCaseSN();
    //show everything
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
    j.setCaseNuageuse(0,0,true);
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
    j.setCaseNuageuse(0,0,true);
    j.setCaseSombre(0,0,true);
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
    //show only stable information (no insecte & no ant.)
    j.setCaseNuageuse(0,0,false);
    j.setCaseSombre(0,0,true);
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmilière")));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains(g.get("fourmi")));
  }
  @Test
  public void testSetFoodInsecte(){
    Fourmi f = ini();
    Case c = f.getCase();
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
    Case c = f.getCase();
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
    Case c = f.getCase();
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
    Case c = f.getCase();
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
}
