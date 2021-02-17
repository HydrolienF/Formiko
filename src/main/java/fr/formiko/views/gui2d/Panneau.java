package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panneau extends JPanel {//implements ActionListener{
  private static final long serialVersionUID = -3227223889149566494L;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public Panneau(){
    //setOpaque(false); //cause issues about Bouton.
  }
  // GET SET --------------------------------------------------------------------
  //public int getTailleDUneCase(){return tailleDUneCase;}
  //public void setTailleDUneCase(int x){tailleDUneCase = x;}
  //public int getEspaceRéservéBas(){return espaceRéservéBas;}
  //public void setEspaceRéservéBas(int x){espaceRéservéBas = x;}
  //public void setXCase(int x){xCase = x;}
  //public void setYCase(int y){yCase = y;}
  public BListener getBListener(){ return new BListener();}
  // Fonctions propre -----------------------------------------------------------
  //repaint() permet de réactualisé paintComponent()
  // L'instruction Thread.sleep(x miliseconde) permet d'effectuer une pause dans le programme.
  public void paintComponent(Graphics g){

  }
  public void doAction(int action){
    try {
      ((PanneauMenu)this).doAction(action);
    }catch (Exception e2) {
      debug.débogage("echec de PanneauMenu.doAction");
      try {
        ((PanneauJeu)this).doAction(action);
      }catch (Exception e3) {
        debug.débogage("echec de PanneauJeu.doAction. Lancement de Pj.doAction imposé.");
        Main.getPp().getPj().doAction(action);
      }
    }
  }

  class BListener implements ActionListener{
    private int compteur=0;
    //Redéfinition de la méthode actionPerformed()
    public void actionPerformed(ActionEvent arg0) {
      //Lorsque l'on clique sur le bouton, on met à jour le JLabel
      compteur++;
    }
  }
}
