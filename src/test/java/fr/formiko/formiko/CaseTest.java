package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;

public class CaseTest extends TestCaseMuet{
  private Partie p;
  // FUNCTIONS -----------------------------------------------------------------
  private Fourmi ini(){
    Main.initialisation();
    Main.setLangue(0);
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
    //show everything
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
    //do not show anything
    j.setCaseNuageuse(0,0,true);
    // assertTrue("".equals(Main.getGc().getCCase(0,0).getContent().toString()));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
    //show only stable information (no insecte & no ant.)
    j.setCaseNuageuse(0,0,false);
    j.setCaseSombre(0,0,true);
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
  }
  //I can't get why this test exist.
  // @Test
  // public void testToString2(){
  //   Fourmi f = ini();
  //   Joueur j = Main.getGj().getHead().getContent();
  //   j.initialisationCaseNS();
  //   j.updateCaseSN();
  //   //show everything
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
  //   j.setCaseNuageuse(0,0,true);
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
  //   j.setCaseNuageuse(0,0,false);
  //   j.setCaseSombre(0,0,true);
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
  //   assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
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
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
    j.setCaseNuageuse(0,0,true);
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
    j.setCaseNuageuse(0,0,true);
    j.setCaseSombre(0,0,true);
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
    //show only stable information (no insecte & no ant.)
    j.setCaseNuageuse(0,0,false);
    j.setCaseSombre(0,0,true);
    assertTrue(Main.getGc().getCCase(0,0).getContent().toString().contains("formikejo"));
    assertTrue(!Main.getGc().getCCase(0,0).getContent().toString().contains("formiko"));
  }
}
