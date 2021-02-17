package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.g;

import javax.swing.JOptionPane;

public class Question {
  int choix;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Question(String s, String s2){
    String[] options = {g.get("oui"),g.get("non")};
    choix = JOptionPane.showOptionDialog(Main.getF(), g.get(s), g.get(s2)+" ?",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
  }
  // GET SET --------------------------------------------------------------------
  public boolean getChoix(){return choix==JOptionPane.OK_OPTION;}
  // Fonctions propre -----------------------------------------------------------

}
