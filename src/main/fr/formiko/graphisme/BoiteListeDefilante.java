package fr.formiko.graphisme;
//import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.Temps;
//imspiré de http://remy-manu.no-ip.biz/Java/Tutoriels/IHM/dialogue.html

public class BoiteListeDefilante {
  private JComboBox<String> jcb;
  //private JOptionPane jop;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public BoiteListeDefilante(){}
  public void initialiser(){}
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public int getChoixId(int ti [], String message){
    String ts []= str.iToS(ti);
    return getChoixId(ts,message);
  }
  public int getChoixId(String ts [], String message){
    jcb = new JComboBox<String>(ts);
    int x=-1;
    while (x==-1){
      JOptionPane.showMessageDialog(Main.getF(), jcb, message, JOptionPane.PLAIN_MESSAGE);//,new ImageIcon("montre.gif"));
      Object o = jcb.getSelectedItem();
      String s = o.toString();
      try {
        //on ne prend en compte que le premier mot qui dervait être l'id de la Fourmi.
        String tsSplit [] = s.split(" ");
        if(tsSplit.length > 1){
          s = tsSplit[0];
        }
        x=str.sToI(s);
      }catch (Exception e) {erreur.erreur("L'Objet de JOptionPane n'est pas reconnu comme int : "+s,"BoiteListeDefilante");}
      Temps.pause(10);
    }
    return x;
  }
}
