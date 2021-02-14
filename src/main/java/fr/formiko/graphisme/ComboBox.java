package fr.formiko.graphisme;

import fr.formiko.usuel.debug;

import javax.swing.JComboBox;

public class ComboBox<String> extends JComboBox<String>{

  // CONSTRUCTEUR ---------------------------------------------------------------
  public ComboBox(String tab[]){
    super(tab);
  }
  // GET SET --------------------------------------------------------------------
  public Object getSelectedItemReminder(){return selectedItemReminder;}
  // Fonctions propre -----------------------------------------------------------

}
