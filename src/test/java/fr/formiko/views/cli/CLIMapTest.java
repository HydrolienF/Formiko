package fr.formiko.views.cli;

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
import fr.formiko.usuel.color;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.tests.TestCaseMuet;

public class CLIMapTest extends TestCaseMuet{
  @Test
  public void testObjetSurCarteAIdToStringW(){
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    assertEquals("1",cLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals("G2",cLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals("I3",cLIMap.objetSurCarteAIdToString(new Insecte()));
    assertEquals("G4",cLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals("5",cLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals("C6",cLIMap.objetSurCarteAIdToString(new ObjetSurCarteAId()));
  }
  @Test
  public void testObjetSurCarteAIdToStringM(){
    Main.initialisation();
    Main.getOs().setId((byte)2);
    Main.setPartie(Partie.getDefautlPartie());
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    assertEquals("1",cLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals("G2",cLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals("I3",cLIMap.objetSurCarteAIdToString(new Insecte()));
    assertEquals("G4",cLIMap.objetSurCarteAIdToString(new Graine()));
    Main.getOs().setId((byte)1);
    assertEquals("5",cLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals("C6",cLIMap.objetSurCarteAIdToString(new ObjetSurCarteAId()));
  }
  @Test
  @Disabled
  public void testObjetSurCarteAIdToStringL(){
    Main.initialisation();
    Main.getOs().setId((byte)0);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getPartie().setElément(1,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getDébut().getContenu();
    Main.getPartie().setPlayingAnt(playingAnt);
    assertEquals(color.GREEN+"1"+color.NEUTRAL,cLIMap.objetSurCarteAIdToString(playingAnt));
    assertEquals(color.BROWN+"G2"+color.NEUTRAL,cLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals(color.RED+"I3"+color.NEUTRAL,cLIMap.objetSurCarteAIdToString(new Insecte()));
    assertEquals(color.BROWN+"G4"+color.NEUTRAL,cLIMap.objetSurCarteAIdToString(new Graine()));
    assertEquals(color.GREEN+"5"+color.NEUTRAL,cLIMap.objetSurCarteAIdToString(new Fourmi()));
    assertEquals(color.RED+"C6"+color.NEUTRAL,cLIMap.objetSurCarteAIdToString(new ObjetSurCarteAId()));
  }
  @Test
  public void testCaseToStringSombreNuageuse(){
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(false);
    Main.getMap().setCasesNuageuses(false);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //without any player on the map
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),false,false));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),false,true));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),true,false));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),true,true));
  }
  @Test
  public void testCaseToStringSombreNuageuse2(){
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(true);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //without any player on the map but we input value for the 2 boolean
    assertEquals(0,Main.getGj().length());
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),false,false));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),false,true));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),true,false));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),true,true));
  }
  @Test
  public void testCaseToStringSombreNuageuse3(){
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(false);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //without any player on the map but we input value for the 2 boolean
    assertEquals(0,Main.getGj().length());
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),false,false));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),false,true));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),true,false));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getDébut().getContenu(),true,true));
  }
  @Test
  public void testCaseToStringSombreNuageuse4(){
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(true);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //with a player
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getDébut().getContenu();
    Main.getPartie().setPlayingAnt(playingAnt);
    playingAnt.setCCase(Main.getGc().getCCase(2,0));
    playingAnt.getFere().setCc(Main.getGc().getCCase(2,0));
    j.initialisationCaseNS();
    j.actualiserCaseSN();
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(0,0));
    j.actualiserCaseSN();
    assertEquals("1   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,0),j));
    assertEquals("F1□□",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,2),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,3),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(1,2),j));

    playingAnt.setCCase(Main.getGc().getCCase(1,1));
    j.actualiserCaseSN();
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("F1  ",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(2,2));
    j.actualiserCaseSN();
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
  }
  public void testCaseToStringNuageuse(){
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(false);
    Main.getMap().setCasesNuageuses(true);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //with a player
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getDébut().getContenu();
    Main.getPartie().setPlayingAnt(playingAnt);
    playingAnt.setCCase(Main.getGc().getCCase(2,0));
    playingAnt.getFere().setCc(Main.getGc().getCCase(2,0));
    j.initialisationCaseNS();
    j.actualiserCaseSN();
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(0,0));
    j.actualiserCaseSN();
    assertEquals("1   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,0),j));
    assertEquals("F1  ",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,2),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(0,3),j));
    assertEquals("■■■■",cLIMap.caseToString(Main.getGc().getCCase(1,2),j));

    playingAnt.setCCase(Main.getGc().getCCase(1,1));
    j.actualiserCaseSN();
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("F1  ",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(2,2));
    j.actualiserCaseSN();
    assertEquals("    ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
  }
  public void testCaseToStringSombre(){
    Main.initialisation();
    Main.getOs().setId((byte)1);
    Main.setPartie(Partie.getDefautlPartie());
    Main.getMap().setCasesSombres(true);
    Main.getMap().setCasesNuageuses(false);
    Main.getPartie().setElément(0,0,0);
    CLIMap cLIMap = new CLIMap(Main.getPartie().getGc());
    //with a player
    Joueur j = new Joueur(1,true,Main.getCarte());
    Fourmi playingAnt = (Fourmi) j.getFere().getGc().getDébut().getContenu();
    Main.getPartie().setPlayingAnt(playingAnt);
    playingAnt.setCCase(Main.getGc().getCCase(2,0));
    playingAnt.getFere().setCc(Main.getGc().getCCase(2,0));
    j.initialisationCaseNS();
    j.actualiserCaseSN();
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(0,0));
    j.actualiserCaseSN();
    assertEquals("1   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,1),j));
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(1,0),j));
    assertEquals("F1□□",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,2),j));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,3),j));
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(1,2),j));

    playingAnt.setCCase(Main.getGc().getCCase(1,1));
    j.actualiserCaseSN();
    assertEquals("-   ",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
    assertEquals("F1  ",cLIMap.caseToString(Main.getGc().getCCase(2,0),j));
    playingAnt.setCCase(Main.getGc().getCCase(2,2));
    j.actualiserCaseSN();
    assertEquals("□□□□",cLIMap.caseToString(Main.getGc().getCCase(0,0),j));
  }
  /*@Test
  public void testMapToString(){

  }*/
}
