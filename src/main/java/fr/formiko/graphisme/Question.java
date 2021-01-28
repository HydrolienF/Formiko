package fr.formiko.graphisme;
//import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
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
