package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.types.str;
import fr.formiko.views.gui2d.FComboBox;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

//imspiré de http://remy-manu.no-ip.biz/Java/Tutoriels/IHM/dialogue.html

public class BoiteListeDefilante {
  private FComboBox<String> jcb;
  //private JOptionPane jop;
  // CONSTRUCTORS --------------------------------------------------------------
  public BoiteListeDefilante(){}
  public void ini(){}
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public int getChoixId(int ti [], String message){
    String ts []= str.iToS(ti);
    return getChoixId(ts,message);
  }
  public int getChoixId(String ts [], String message){
    jcb = new FComboBox<String>(ts);
    int x=-1;
    while (x==-1){
      JOptionPane.showMessageDialog(Main.getF(), jcb, message, JOptionPane.PLAIN_MESSAGE);//,new ImageIcon("montre.gif"));
      Object o = jcb.getSelectedItem();
      if(o==null){erreur.erreur("L'élément sélectioné est null"); return x;}
      String s = o.toString();
      try {
        //on ne prend en compte que le premier mot qui dervait être l'id de la Créature.
        String tsSplit [] = s.split(" ");
        if(tsSplit.length > 1){
          s = tsSplit[0];
        }
        x=str.sToI(s);
      }catch (Exception e) {erreur.erreur("L'Objet de JOptionPane n'est pas reconnu comme int : "+s);}
      Temps.pause(10);
    }
    return x;
  }
}
