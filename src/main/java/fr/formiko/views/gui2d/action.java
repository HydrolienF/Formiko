package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.usuel.types.str;


import javax.swing.JOptionPane;

/**
*{@summary All the gui action are launch here.}
*@author Hydrolien
*@version 1.41
*/
public class action{
  private static Partie pa;
  // GET SET -------------------------------------------------------------------
  public static Partie getPartie(){ return pa;}
  public static void setPartie(Partie p){pa=p;}
  // public static Partie getPartie(){return Main.getPartie();}
  // public static void setPartie(Partie p){Main.setPartie(p);}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Launch an action on gui mode.}
  *@version 1.41
  */
  public static void doAction(int action){
    if(Panneau.getView().getPe() != null && Panneau.getView().getPe().getVisible()){
      doActionPe(action);
    }else if(Main.getView().getActionGameOn()){
      doActionPj(action);
    }else{
      doActionPm(action);
    }
  }
  /**
  *{@summary Launch an action on menu.}
  *@version 1.41
  */
  public static void doActionPm(int ac){//TODO passer dans une autre class Controleur ?
    PanneauMenu pm = Panneau.getView().getPm();
    if(ac==-1){
      Panneau.getView().close();
    }if(ac==0){
      System.out.println("back to main menu");
      Panneau.getView().menuMain();
    }else if(ac==1){
      Panneau.getView().menuNewGame();
    }else if(ac==2){
      Panneau.getView().menuLoadAGame();
    }else if(ac==3){
      Panneau.getView().menuOptions();
    }else if(ac==4){
      debug.débogage("lancementNouvellePartie");
      pm.setLancer(true); //TODO to remove
    }else if(ac==5){
      Panneau.getView().menuPersonaliseAGame();
    }else if(ac==6){
      Partie.setScript("tuto");
      pm.setLancer(true); //TODO to remove
    }else if(ac==7){
      pm.validatelanguageChoice();
    }else if(ac==100){
      // Main.getView().menuNewGame();
      setPartie(Panneau.getView().getPnp().getPartie());
      pm.setLancer(true); //TODO to remove
    }else if(ac==101){
      setPartie(pm.getPcp().getPartie());
      pm.setLancer(true); //TODO to remove
    }
  }
  /**
  *{@summary Launch an action on action game.}
  *@version 1.41
  */
  public static void doActionPj(int ac){
    debug.débogage("action pj : "+ac);
    try {
      if(Panneau.getView().getPe()==null || !Panneau.getView().getPe().getVisible()){
        if(ac < 9 && ac > -1){
          Panneau.getView().getPj().actionZoom((byte)ac);
        }else if(ac>=20 && ac<=31){
          if(Main.getPlayingAnt()==null){
            erreur.erreur("aucune fourmi n'est selectionné pour réaliser l'action voulue.");
          }else{
            debug.débogage("clic qui lance "+(ac-20));
            Panneau.getView().getPb().setActionF(ac-20);
          }
          Main.repaint();
        }else if(ac==111){
          ((ViewGUI2d)(Panneau.getView())).closePanneauChargement();
        }else if(ac==112){//retour au menu
          // Main.setRetournerAuMenu(true);
          retournerAuMenu();
        }else if(ac==113){//retour au jeu
          Panneau.getView().getPj().removePfp();
          Main.getPartie().setContinuerLeJeu(true);
          Main.repaint();
        }else if(ac>=40){
          PanneauBouton pb = Panneau.getView().getPb();
          pb.setChoixId(pb.getPti().getBoutonX(ac-40));
          pb.remove(pb.getPti());
          pb.setPti(new PanneauTInt(null,pb));
          Main.repaint();
        }
      }
    }catch (Exception e) {
      erreur.erreur("L'action "+ac+" n'as pas fonctionnée");
    }
  }
  /**
  *{@summary Launch an escape panel action.}
  *@version 1.41
  */
  private static void doActionPe(int ac){
    if(ac==-9){
      Main.getScript().setCmdSuivante(true);
    }else if(ac==-10){
      String s = getSaveName();
      sauvegarderUnePartie.sauvegarder(Main.getPartie(),s+".save");
      Panneau.getView().getPe().setVisible(false);
    }else if(ac==-11){

    }else if(ac==-12){
      retournerAuMenu();
    }else if(ac==-13){
      Main.getF().quit();
    }else if(ac==-14){
      Panneau.getView().getPe().setVisible(false);
    }
  }
  /**
  *{@summary Ask save name in gui.}
  *@version 1.41
  */
  private static String getSaveName(){
    String s = "null";
    JOptionPane d = new JOptionPane(g.get("sauvegarder"));
    d.setMessageType(JOptionPane.QUESTION_MESSAGE);
    //d.setInitialSelectionValue(Temps.getDatePourSauvegarde());
    Object[] options = {g.get("ok")};
    //d.title = g.get("sauvegarder");
    String saveName = g.getM("sauvegarde")+" "+sauvegarderUnePartie.getSave().getIdS();//donne un identifiant unique au fichier.
    try {
      //saveName+="  "+Main.getGj().gethead().getContent().getPseudo();
      saveName+="  "+Temps.getDatePourSauvegarde();
    }catch (Exception e) {
      erreur.alerte("Un nom de sauvegarde n'a pas pu être choisi.");
    }
    saveName = str.sToFileName(saveName);//le pseudo pourrai contenir des char interdits sur des fichiers.
    s = d.showInputDialog(Main.getF(),g.get("save.message"),saveName);
    s = str.sToFileName(s);
    //s = d.showInputDialog(Main.getF(),g.get("save.message"),g.get("sauvegarder"),JOptionPane.QUESTION_MESSAGE);
    Object o = g.get("save.message");
    Object oNull = null;
    //TODO s'arranger pour conserver ce qu'on a mais avoir 1 seul bouton g.get("ok") & on veut le titre et la valeur préremplie.
    //javadoc showInputDialog(Component parentComponent, Object message, String title, int messageType, Icon icon, Object[] selectionValues, Object initialSelectionValue)
    //s = d.showInputDialog(Main.getF(),o,g.get("sauvegarder"),JOptionPane.QUESTION_MESSAGE,new ImageIcon(),options,oNull);
    return s;
  }
  /**
  *{@summary go back to main menu.}
  *@version 1.41
  */
  public static void retournerAuMenu(){
    Main.setRetournerAuMenu(true);//ne prend effet dans la void main que lorsque le tour est fini.
    try {
      Main.getGj().setAction0AndEndTurn();//empèche une autre fourmi de jouer
      Panneau.getView().getPb().setActionF(9);//empèche la fourmi actuel de jouer.
    }catch (Exception e) {}
    // erreur.info("retournerAuMenu 2");
    // Panneau.getView().getPp().removePj();
    // Panneau.getView().getPp().addPm();
  }
}
