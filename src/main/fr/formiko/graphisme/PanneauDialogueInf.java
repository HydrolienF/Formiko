package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.image.image;
import java.awt.Image;
import java.awt.Graphics;

public class PanneauDialogueInf extends Panneau {
  private static Image fond;
  private Desc b;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauDialogueInf(){
    super();
    this.setLayout(null);
  }
  public void initialiser(){
    setSize(Main.getDimX(),Main.getTailleElementGraphiqueY(210));
    //b.setSize(b.getPreferredSize());
    //b.setSize(Main.getTailleElementGraphiqueX(20),((int)(b.getPreferredSize().getHeight())));
    //add(b);
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(fond,0,0,this);
  }
  public static void chargerFond(){
    fond = image.getImage("hautDeLaFenetre");
    fond = fond.getScaledInstance(Main.getDimX(), Main.getTailleElementGraphiqueY(210),Image.SCALE_SMOOTH);
  }
  public void addBSuivant(){
    // TODO fixer la Desc Suivant qui s'affiche mal.
    //b.setVisible(true);
    b = new Desc(Main.getTailleElementGraphiqueX(80),Desc.getDimY());
    b.setSize(b.getPreferredSize());
    b.setFondTransparent();
    b.setTexte(g.get("suivant"));
    //b.setSize(Main.getTailleElementGraphiqueX(80),((int)(b.getPreferredSize().getHeight())));
    b.setBounds(Main.getPd().getWidth()-b.getWidth()-Main.getTailleElementGraphiqueX(100),getHeight()-b.getHeight()-Main.getTailleElementGraphiqueY(40),b.getWidth());
    add(b);
    repaint();
  }
  public void removeBSuivant(){
    //b.setVisible(false);
    remove(b);
    repaint();
  }
}
