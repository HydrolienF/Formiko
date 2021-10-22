package fr.formiko.views.cli;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.CCase;
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
import fr.formiko.usuel.Os;
import fr.formiko.usuel.color;
import fr.formiko.usuel.tests.TestCaseMuet;

public class CLIMapTest extends TestCaseMuet{
  @Test
  public void testObjetSurCarteAIdToStringW(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    // CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    assertEquals("1",CLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals("G2",CLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals("I3",CLIMap.objetSurCarteAIdToString(new Insecte()));
    assertEquals("G4",CLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals("5",CLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals("C6",CLIMap.objetSurCarteAIdToString(new ObjetSurCarteAId()));
  }
  @Test
  public void testObjetSurCarteAIdToStringM(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)2);
    Main.setPartie(Partie.getDefautlPartie());
    // CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    assertEquals("1",CLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals("G2",CLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals("I3",CLIMap.objetSurCarteAIdToString(new Insecte()));
    assertEquals("G4",CLIMap.objetSurCarteAIdToString(new Graine()));
    Main.getOs().setId((byte)1);
    assertEquals("5",CLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals("C6",CLIMap.objetSurCarteAIdToString(new ObjetSurCarteAId()));
  }
  @Test
  public void testObjetSurCarteAIdToStringL(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)0);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getPartie().setElément(1,0,0);
    // CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getHead().getContent();
    Main.getPartie().setPlayingAnt(playingAnt);
    assertEquals(color.GREEN_FLASH+"1"+color.NEUTRAL,CLIMap.objetSurCarteAIdToString(playingAnt));
    assertEquals(color.BROWN+"G2"+color.NEUTRAL,CLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals(color.RED+"I3"+color.NEUTRAL,CLIMap.objetSurCarteAIdToString(new Insecte()));
    assertEquals(color.BROWN+"G4"+color.NEUTRAL,CLIMap.objetSurCarteAIdToString(new Graine()));
    Fourmi ally = new Fourmi();
    ally.setPheromone(playingAnt.getPheromone());
    assertEquals(color.GREEN+"5"+color.NEUTRAL,CLIMap.objetSurCarteAIdToString(ally));
    assertEquals(color.BLACK+"C6"+color.NEUTRAL,CLIMap.objetSurCarteAIdToString(new ObjetSurCarteAId()));
  }
  @Test
  public void testCaseToStringSombreNuageuse(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(false);
    Main.getMap().setCasesNuageuses(false);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //without any player on the map
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getHead().getContent(),false,false));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getHead().getContent(),false,true));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getHead().getContent(),true,false));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getHead().getContent(),true,true));
  }
  @Test
  public void testCaseToStringSombreNuageuse2(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(true);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //without any player on the map but we input value for the 2 boolean
    assertEquals(0,Main.getGj().length());
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getHead().getContent(),false,false));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getHead().getContent(),false,true));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getHead().getContent(),true,false));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getHead().getContent(),true,true));
  }
  @Test
  public void testCaseToStringSombreNuageuse3(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(false);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //without any player on the map but we input value for the 2 boolean
    assertEquals(0,Main.getGj().length());
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getHead().getContent(),false,false));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getHead().getContent(),false,true));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getHead().getContent(),true,false));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getHead().getContent(),true,true));
  }
  @Test
  public void testCaseToStringSombreNuageuse4(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(true);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //with a player
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getHead().getContent();
    Main.getPartie().setPlayingAnt(playingAnt);
    playingAnt.setCCase(Main.getGc().getCCase(2,0));
    playingAnt.getFere().setCc(Main.getGc().getCCase(2,0));
    j.initialisationCaseNS();
    j.updateCaseSN();
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(0,0));
    j.updateCaseSN();
    assertEquals("1   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,0),j));
    assertEquals("F1□□",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,2),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,3),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(1,2),j));

    playingAnt.setCCase(Main.getGc().getCCase(1,1));
    j.updateCaseSN();
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("F1  ",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(2,2));
    j.updateCaseSN();
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
  }
  public void testCaseToStringNuageuse(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(false);
    Main.getMap().setCasesNuageuses(true);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //with a player
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getHead().getContent();
    Main.getPartie().setPlayingAnt(playingAnt);
    playingAnt.setCCase(Main.getGc().getCCase(2,0));
    playingAnt.getFere().setCc(Main.getGc().getCCase(2,0));
    j.initialisationCaseNS();
    j.updateCaseSN();
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(0,0));
    j.updateCaseSN();
    assertEquals("1   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,0),j));
    assertEquals("F1  ",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,2),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,3),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(1,2),j));

    playingAnt.setCCase(Main.getGc().getCCase(1,1));
    j.updateCaseSN();
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("F1  ",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(2,2));
    j.updateCaseSN();
    assertEquals("    ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
  }
  public void testCaseToStringSombre(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(false);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //with a player
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getHead().getContent();
    Main.getPartie().setPlayingAnt(playingAnt);
    playingAnt.setCCase(Main.getGc().getCCase(2,0));
    playingAnt.getFere().setCc(Main.getGc().getCCase(2,0));
    j.initialisationCaseNS();
    j.updateCaseSN();
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(0,0));
    j.updateCaseSN();
    assertEquals("1   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,0),j));
    assertEquals("F1□□",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,2),j));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,3),j));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(1,2),j));

    playingAnt.setCCase(Main.getGc().getCCase(1,1));
    j.updateCaseSN();
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("F1  ",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(2,2));
    j.updateCaseSN();
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
  }
  @Test
  public void testMapToString(){
    Main.setOs(new Os());
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(true);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //with a player
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getHead().getContent();
    //Main.getView().setActionGameOn(true);
    Main.getPartie().setPlayingAnt(playingAnt);
    playingAnt.setCCase(Main.getGc().getCCase(2,0));
    playingAnt.getFere().setCc(Main.getGc().getCCase(2,0));
    j.initialisationCaseNS();
    j.updateCaseSN();
    playingAnt.setCCase(Main.getGc().getCCase(0,0));
    j.updateCaseSN();
    assertEquals("1   -   F1□□□□□□"+repeat(12,"■■■■"),cLIMap.mapLineToString(Main.getGc().getCCase(0,0),j));
    assertTrue(Main.getPartie().setPlayingAnt(playingAnt));
    // Joueur.setPlayingJoueur(null);
    Joueur.setPlayingJoueur(playingAnt.getFere().getJoueur());
    assertTrue(Main.getPlayingJoueur()!=null);
    Graine g = new Graine();
    Insecte in = new Insecte();
    in.setCCase(1,0);
    g.setCCase(1,1);
    String sMap = "1   I3  F1□□□□□□■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n";
    sMap+="-   G2  □□□□□□□□■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n";
    for (int i=0;i<7 ;i++ ) {
      sMap+="■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n";
    }
    assertEquals(sMap,cLIMap.mapToString());
  }
  private static String repeat(int x, String s){
    String sr = "";
    for (int i=0;i<x ;i++ ) {
      sr+=s;
    }
    return sr;
  }
  @BeforeAll
  public static void clean(){
    Main.setOs(new Os());
  }
}
