package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.g;

import javax.swing.JOptionPane;

/**
*{@summary Question class to get GUI answer.}<br>
*@author Hydrolien
*@version 2.13
*/
public class Question {
  private int choix;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary constructor to get a boolean answer.}
  *@param popUpName name of the popUp
  *@param popUpMessage message of the popUp
  *@version 2.13
  */
  public Question(String popUpName, String popUpMessage){
    String[] options = {g.get("oui"),g.get("non")};
    choix = JOptionPane.showOptionDialog(Main.getF(), g.get(popUpName), g.get(popUpMessage)+" ?",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
  }
  // GET SET -------------------------------------------------------------------
  public boolean getChoix(){return choix==JOptionPane.OK_OPTION;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Static methode to get a String answer.}
  *@param popUpName name of the popUp
  *@param popUpMessage message of the popUp
  *@version 2.13
  */
  public static QuestionString(String popUpName, String popUpMessage){
    return JOptionPane.showInputDialog (Main.getF(), g.getM(s), s2, JOptionPane.QUESTION_MESSAGE);
  }

}
