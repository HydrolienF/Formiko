package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
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
