package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;

public class PanneauDialogueInf extends Panneau {
  private static Image fond;
  private Desc b;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauDialogueInf(){
    super();
    // this.setLayout(null);
    // this.setBackground(new Color(0,0,0,0));
    // setOpaque(false);
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
    g.drawImage(fond,0,0,this);
    super.paintComponent(g);
  }
  public static void chargerFond(){
    fond = image.getImage("hautDeLaFenetre");
    fond = fond.getScaledInstance(Main.getDimX(), Main.getTailleElementGraphiqueY(210),Image.SCALE_SMOOTH);
  }
  public void addBSuivant(){
    // TODO fixer la Desc Suivant qui s'affiche mal.
    //b.setVisible(true);
    // b=new JLabel(g.get("suivant"));
    // b.setVisible(true);
    // b.updateUI();
    b = new Desc(Main.getTailleElementGraphiqueX(180),Desc.getDimY());
    b.setSize(b.getPreferredSize());
    b.setFondTransparent();
    b.setTexte(g.get("suivant"));
    b.setBounds(getView().getPd().getWidth()-b.getWidth()-Main.getTailleElementGraphiqueX(20),getHeight()-b.getHeight()-Main.getTailleElementGraphiqueY(40),b.getWidth());
    b.setHorizontalAlignment(b.RIGHT);
    add(b);
    repaint();
  }
  public void removeBSuivant(){
    //b.setVisible(false);
    remove(b);
    repaint();
  }
}
