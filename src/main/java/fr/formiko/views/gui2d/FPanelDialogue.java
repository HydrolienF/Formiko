package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;

import java.awt.Graphics;

public class FPanelDialogue extends FPanel {
  private FTextArea fta;
  private boolean needToStayMaxSize;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelDialogue(){
    super();
  }
  public void ini(String s, boolean needToStayMaxSize){
    this.needToStayMaxSize=needToStayMaxSize;
    if(fta!=null){remove(fta);}
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
      erreur.alerte("Le FPanelDialogueInf n'as pas pu être mis visible.");
    }
    try {
      int tailleX = Main.getDimX()-Main.getTailleElementGraphiqueX(550);
      fta = new FTextArea(s,tailleX);
      setLocation(Main.getTailleElementGraphiqueX(210),Main.getTailleElementGraphiqueY(15));
      setSize(fta.getWidth(),fta.getHeight()+Main.getTailleElementGraphiqueX(30));
      // setLocation(Main.getTailleElementGraphiqueX(210),Main.getTailleElementGraphiqueY(15));
      add(fta);
    }catch (Exception e) {
      erreur.alerte("fail to ini FPanelDialogue");
    }
    setVisible(true);
  }
  // GET SET -------------------------------------------------------------------
  public boolean getNeedToStayMaxSize(){return needToStayMaxSize;}
  // FUNCTIONS -----------------------------------------------------------------
  // public void paintComponent(Graphics g){
  //   super.paintComponent(g);
  // }
  /**
  *{@summary If we are waiting for going to next commande, launch it.}
  *@return true if we have launch next commande.
  *@lastEditedVersion 2.19
  */
  public synchronized boolean clicEn(int x, int y){
    if (Main.getScript().getEcouteClic()) {//si on écoute la fenetre.
      Main.getScript().setCmdSuivante(true);// le joueur a cliqué pour passer a la commande suivante.
      return true;
    }
    return false;
  }
  /**
  *{@summary Override setVisible to also setVisible dependent FPanel.}
  *Dependent FPanel are pdi &#38; pij.
  *@param visible true if need to be visible.
  *@lastEditedVersion 2.0
  */
  @Override
  public void setVisible(boolean visible){
    super.setVisible(visible);
    getView().getPj().getPdi().setVisible(visible);
    try {
      getView().getPij().setVisible(!visible);
      if(!visible){
        FPanel.getView().getPb().addPIJ();
      }
    }catch (Exception e) {}
  }
}
