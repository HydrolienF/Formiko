package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.liste.GString;
import java.awt.Graphics;

public class PanneauDialogue extends Panneau {
  private PanneauInfo pi;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauDialogue(){
    this.setLayout(null);
    pi = new PanneauInfo(new GString(),0);
    pi.setBounds(0,0,0,0);
    setSize(pi.getWidth(),pi.getHeight());
  }
  public void initialiser(String s){
    if(pi!=null){remove(pi);}
    if(s.equals("")){
      Main.getPj().getPdi().setVisible(false);
      return;
    }
    try {
      Main.getPj().getPdi().setVisible(true);
    }catch (Exception e) {
      erreur.alerte("Le PanneauDialogueInf n'as pas pu être mis visible.","PanneauDialogue.initialiser");
    }
    GString gs = new GString();
    gs.addParMorceaux(s,80,true);
    int tailleX = Main.getDimX();
    try {
      tailleX = Main.getDimXCarte();
      tailleX=tailleX-Main.getTailleElementGraphiqueX(210);
    }catch (Exception e) {}
    pi = new PanneauInfo(gs,tailleX+Main.getTailleElementGraphiqueX(200));
    pi.setBounds(Main.getTailleElementGraphiqueX(210),Main.getTailleElementGraphiqueY(15),pi.getWidth(),pi.getHeight());
    setSize(pi.getWidth(),pi.getHeight()+Main.getTailleElementGraphiqueX(30));
    add(pi);
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){

  }
  public boolean clicEn(int x, int y){
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
}
