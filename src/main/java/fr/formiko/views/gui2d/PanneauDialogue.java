package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;

import java.awt.Graphics;

public class PanneauDialogue extends Panneau {
  private PanneauInfo pi;
  private boolean needToStayMaxSize;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauDialogue(){
    super();
    // pi = new PanneauInfo(new GString(),0);
    // pi.setBounds(0,0,0,0);
    // setSize(pi.getWidth(),pi.getHeight());
  }
  public void initialiser(String s, boolean needToStayMaxSize){
    this.needToStayMaxSize=needToStayMaxSize;
    if(pi!=null){remove(pi);}
    try {
      if(s==null || s.equals("")){
        setVisible(false);
        return;
      }
      getView().getPj().getPdi().setVisible(true);
      if(getView().getPch() != null && getView().getPch().isVisible()){
        getView().getPj().getPdi().setVisible(false);
      }
    }catch (Exception e) {
      erreur.alerte("Le PanneauDialogueInf n'as pas pu être mis visible.");
    }
    try {
      GString gs = new GString();
      gs.addParMorceaux(s,80,true);
      int tailleX = Main.getDimX();
      try {
        tailleX = Main.getDimXCarte();
        tailleX=tailleX-Main.getTailleElementGraphiqueX(210);
      }catch (Exception e) {}
      pi = new PanneauInfo(gs,tailleX+Main.getTailleElementGraphiqueX(200),false);
      pi.setLocation(Main.getTailleElementGraphiqueX(210),Main.getTailleElementGraphiqueY(15));
      setSize(pi.getWidth(),pi.getHeight()+Main.getTailleElementGraphiqueX(30));
      add(pi);
    }catch (Exception e) {
      erreur.alerte("fail to ini PanneauDialogue");
    }
    setVisible(true);
  }
  // GET SET --------------------------------------------------------------------
  public boolean getNeedToStayMaxSize(){return needToStayMaxSize;}
  // public void setVisible(boolean b){
  //   super.setVisible(b);
  //   erreur.info("setVisible "+b,4);
  // }
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  public synchronized boolean clicEn(int x, int y){
    //on écoute toute la fenetre si un panneau de dialogue est afficher et qu'on peu le passer.
    //if(x<Main.getPdi().getWidth() && y<Main.getPdi().getHeight()){
      if (Main.getScript().getEcouteClic()) {//si on écoute la fenetre.
        Main.getScript().setCmdSuivante(true);// le joueur a cliqué pour passer a la commande suivante.
      }
      //return true;
      return false; //si on écoute pas la fenetre.
    //}
    //return false;
  }
  /**
  *{@summary Override setVisible to also setVisible dependent Panneau.}
  *Dependent Panneau are pdi &#38; pij.
  *@param visible true if need to be visible.
  *@version 2.0
  */
  @Override
  public void setVisible(boolean visible){
    super.setVisible(visible);
    getView().getPj().getPdi().setVisible(visible);
    try {
      getView().getPij().setVisible(!visible);
      if(!visible){
        Panneau.getView().getPb().addPIJ();
      }
    }catch (Exception e) {}
  }
}
