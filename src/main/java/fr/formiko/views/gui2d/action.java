package fr.formiko.views.gui2d;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.sauvegarderUnePartie;
import fr.formiko.formiko.triche;
import fr.formiko.usual.Time;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.types.str;

import java.awt.MouseInfo;
import javax.swing.SwingUtilities;
import java.awt.Point;

/**
*{@summary All the gui action are launch here.}
*@author Hydrolien
*@lastEditedVersion 1.41
*/
public class action {
  private static Partie pa;
  private static boolean needToSetPaNullWhenActionDone=false;
  // GET SET -------------------------------------------------------------------
  public static Partie getPartie(){ return pa;}
  public static void setPartie(Partie p){pa=p;}
  public static void setNeedToSetPaNullWhenActionDone(boolean b){needToSetPaNullWhenActionDone=b;}
  // public static Partie getPartie(){return Main.getPartie();}
  // public static void setPartie(Partie p){Main.setPartie(p);}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Launch an action on gui mode.}
  *@lastEditedVersion 1.41
  */
  public static void doAction(int action){
    if(FPanel.getView().getFl()!=null){
      doActionPl(action);
    }else if(FPanel.getView().getPe() != null && FPanel.getView().getPe().getVisible()){
      doActionPe(action);
    }else if(Main.getView().getActionGameOn()){
      doActionPj(action);
    }else{
      doActionPm(action);
    }
  }
  /**
  *{@summary Launch an action on gui mode on the Panel launcher.}
  *@lastEditedVersion 2.x
  */
  public static void doActionPl(int ac){
    if(ac==1000){//retry to download data from FFrameLauncher
      Main.getFolder().setLaunchDownload(true);
      FPanel.getView().setButtonRetryVisible(false);
    }
  }
  /**
  *{@summary Launch an action on menu.}
  *@lastEditedVersion 1.41
  */
  public static void doActionPm(int ac){//TODO passer dans une autre class Controleur ?
    FPanelMenu pm = FPanel.getView().getPm();
    if(ac==-2){
      return; //don't do anything
    }else if(ac==-1){
      FPanel.getView().close();
    }else if(ac==0){
      FPanel.getView().menuMain();
    }else if(ac==1){
      FPanel.getView().menuNewGame();
    }else if(ac==2){
      FPanel.getView().menuLoadAGame();
    }else if(ac==3){
      FPanel.getView().menuOptions();
    }else if(ac==4){
      debug.débogage("lancementNouvellePartie");
      FPanel.getView().setLaunchFromPm(true);
    }else if(ac==5){
      FPanel.getView().menuPersonaliseAGame();
    }else if(ac==6){
      Partie.setScript("tuto");
      FPanel.getView().setLaunchFromPm(true);
    }else if(ac==7){
      pm.validatelanguageChoice();
    }else if(ac==100){
      setPartie(FPanel.getView().getPnp().getPartie());
      FPanel.getView().setLaunchFromPm(true);
    }else if(ac==101){
      setPartie(pm.getPcp().getPartie());
      FPanel.getView().setLaunchFromPm(true);
    }
  }
  /**
  *{@summary Launch an action on action game.}
  *@lastEditedVersion 1.41
  */
  public static void doActionPj(int ac){
    debug.débogage("action pj : "+ac);
    try {
      if(FPanel.getView().getPe()==null || !FPanel.getView().getPe().getVisible()){
        if(ac < 9 && ac > -1){
          FPanel.getView().getPj().actionZoom((byte)ac);
        }else if(ac>=20 && ac<=31){
          if(Main.getPlayingAnt()==null){
            erreur.erreur("aucune fourmi n'est selectionné pour réaliser l'action voulue.");
          }else{
            debug.débogage("clic qui lance "+(ac-20));
            if(needToSetPaNullWhenActionDone){
              triche.commande("setPa 20");
              setNeedToSetPaNullWhenActionDone(false);
            }
            FPanel.getView().getPb().setActionF(ac-20);
          }
          Main.repaint();
        }else if(ac==111){
          ((ViewGUI2d)(FPanel.getView())).closeFPanelChargement();
        }else if(ac==112){//retour au menu
          // Main.setRetournerAuMenu(true);
          retournerAuMenu();
        }else if(ac==113){//retour au jeu
          FPanel.getView().getPj().removePfp();
          Main.getPartie().setContinuerLeJeu(true);
          Main.repaint();
        }else if(ac==200){//endTurnButton
          try {
            Main.getPlayingJoueur().setIsTurnEnded(true);
          }catch (Exception e) {
            erreur.alerte("fail to end turn");
          }
        }else if(ac>=350 && ac<400){
          doGraphicsAction(ac);
        }else if(ac>=40){
          FPanelBouton pb = FPanel.getView().getPb();
          pb.setChoixId(pb.getPti().getBoutonX(ac-40));
          pb.remove(pb.getPti());
          pb.setPti(new FPanelTInt(null,pb));
          Main.repaint();
        }
      }
    }catch (Exception e) {
      erreur.erreur("Action "+ac+" fail by "+e,10);
    }
  }
  /**
  *{@summary Launch an escape panel action.}
  *@lastEditedVersion 1.41
  */
  private static void doActionPe(int ac){
    if(ac==-9){
      Main.getScript().setCmdSuivante(true);
    }else if(ac==-10){
      String s = getSaveName();
      sauvegarderUnePartie.sauvegarder(Main.getPartie(),s+".save");
      // FPanel.getView().getPe().setVisible(false); //done in getSaveName()
    }else if(ac==-11){
      System.out.println("abandonner");//@a
      if(Main.getPlayingJoueur()==null){erreur.erreur("Can't abandon because current player is null.");}
      else{
        Main.getPlayingJoueur().abandon();
        Main.getPlayingJoueur().setIsTurnEnded(true);
      }
      FPanel.getView().getPe().setVisible(false);
    }else if(ac==-12){
      System.out.println("options");
    }else if(ac==-13){
      retournerAuMenu();
    }else if(ac==-14){
      Main.getF().quit();
    }else if(ac==-15){
      FPanel.getView().getPe().setVisible(false);
    }
  }
  /**
  *{@summary Ask save name in gui.}
  *@lastEditedVersion 2.17
  */
  private static String getSaveName(){
    String saveName = g.getM("sauvegarde")+" "+sauvegarderUnePartie.getSave().getIdS();//donne un identifiant unique au fichier.
    try {
      //saveName+="  "+Main.getGj().getFirst().getPseudo();
      saveName+="  "+Time.getDatePourSauvegarde();
    }catch (Exception e) {
      erreur.alerte("Un nom de sauvegarde n'a pas pu être choisi.");
    }
    // saveName = str.sToFileName(saveName);//le pseudo pourrait contenir des char interdits sur des fichiers.
    if(FPanel.getView().getPe()!=null){
      return FPanel.getView().getPe().getSaveName(saveName);
    }else{
      return saveName;
    }
  }
  /**
  *{@summary go back to main menu.}
  *@lastEditedVersion 1.41
  */
  public static void retournerAuMenu(){
    Main.setRetournerAuMenu(true);//ne prend effet dans la void main que lorsque le tour est fini.
    try {
      Main.getGj().setAction0AndEndTurn();//empèche une autre fourmi de jouer
      FPanel.getView().getPb().setActionF(9);//empèche la fourmi actuel de jouer.
    }catch (Exception e) {}
    // erreur.info("retournerAuMenu 2");
    // FPanel.getView().getPp().removePj();
    // FPanel.getView().getPp().addPm();
  }
  /**
  *{@summary Do as if mouse have been update.}
  *@lastEditedVersion 2.17
  */
  public static void updateMouseLocation(){
    if(FPanel.getView().getPs()==null){return;}
    Point mouse = MouseInfo.getPointerInfo().getLocation();
    SwingUtilities.convertPointFromScreen(mouse, FPanel.getView().getPp());
    CCase cc = FPanel.getView().getPs().getCCase((int)mouse.getX(), (int)mouse.getY());
    if(cc!=null){
      FPanel.getView().getPs().mouseMovedUpdate(cc, true);
    }
  }
  /**
  *{@summary do a graphic action concerning map aspect.}
  *@lastEditedVersion 2.10
  */
  public static void doGraphicsAction(int ac){
    switch(ac){
      case 350:
      Main.getOp().setDrawGrid(!Main.getOp().getDrawGrid());
      break;
      case 351:
      Main.getOp().setDrawRelationsIcons(!Main.getOp().getDrawRelationsIcons());
      break;
      case 352:
      Main.getOp().setDrawStatesIconsLevel((byte)((Main.getOp().getDrawStatesIconsLevel()+1)%5));
      break;
      case 353:
      Main.getOp().setDrawAllAnthillColor(!Main.getOp().getDrawAllAnthillColor());
      break;
      case 354:
      Main.getOp().setDrawPlayerMessagePanel(!Main.getOp().getDrawPlayerMessagePanel());
      if (FPanel.getView().getPb()!=null) {
        FPanel.getView().getPb().addPIJ();
      }
      break;
      case 355:
      Main.getOp().setDrawOnlyEatable(!Main.getOp().getDrawOnlyEatable());
      Main.getView().setPlayingAnt(Main.getPartie().getPlayingAnt());//update list of seed/creature on the case of playingAnt.
      break;
      case 356:
      Main.getOp().setAntColorLevel((byte)((Main.getOp().getAntColorLevel()+1)%3));
      Main.getView().setPlayingAnt(Main.getPartie().getPlayingAnt());//update list of seed/creature on the case of playingAnt.
      break;
      case 357:
      Main.getOp().setDrawDrawBlades(!Main.getOp().getDrawBlades());
      FPanel.getView().setBladeChanged(true);
      break;
      case 395:
      Main.getMp().last();
      FPanel.getView().getPmu().updateColorButtonPause();
      break;
      case 396:
      if(Main.getMp().isRunning()){
        Main.getMp().pause();
      }else{
        Main.getMp().resume();
      }
      break;
      case 397:
      Main.getMp().next();
      FPanel.getView().getPmu().updateColorButtonPause();
      break;
      case 398:
      FPanel.getView().getPmu().setVisible(!FPanel.getView().getPmu().isVisible());
      break;
      case 399:
      FPanel.getView().getPe().setVisible(!FPanel.getView().getPe().isVisible());
      break;
      default:
      break;
    }
  }
}
