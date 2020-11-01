package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import javax.swing.JTextField;
import java.awt.Font;
import fr.formiko.formiko.Main;
import java.awt.Color;

public class Champ extends JTextField{

  // CONSTRUCTEUR ---------------------------------------------------------------
  public Champ(String s){
    super(s);
    setPolice();
  }
  public Champ(){ this("");}
  // GET SET --------------------------------------------------------------------
  //setText et getText
  public void setBounds(int a, int b, int c){setBounds(a,b,c,Desc.getDimY());}
  public void setPolice(Font fon){ this.setFont(fon);}
  public void setPolice(){ setPolice(Main.getFont1());}
  public void setFondColoré(Color col){this.setBackground(col);}
  public void setFondTransparent(){setFondColoré(new Color(0,0,0,0));}
  // Fonctions propre -----------------------------------------------------------

}
