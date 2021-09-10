package fr.formiko.views.gui2d;

import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class PanneauChamp extends Panneau {
  private Champ c;
  private BoutonV b;
  private int tailleBouton=20;
  private boolean validé=false;
  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauChamp(String défaut){
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    Dimension dimC = new Dimension(500,tailleBouton);
    Dimension dimB = new Dimension(tailleBouton*2,tailleBouton);
    c = new Champ(défaut);
    b = new BoutonV();
    c.setPreferredSize(dimC); b.setPreferredSize(dimB);
    gbc.gridy = 0; gbc.gridx = 0;
    this.add(c,gbc);
    gbc.gridx = 1;
    this.add(b,gbc);
  }
  public PanneauChamp(){ this("");}
  // GET SET -------------------------------------------------------------------
  public boolean getValidé(){ return validé;}
  public String getTexte(){ return c.getText();}
  // FUNCTIONS -----------------------------------------------------------------


  class BoutonV extends JButton implements MouseListener{
    public BoutonV(){
      super("OK");
      this.addMouseListener(this);
    }
    //Méthode appelée lors du clic de souris
    public void mouseClicked(MouseEvent event) {
      debug.débogage("Un bouton a été cliqué, l'action \"validé\" vas être effectué.");
      validé=true;
    }
    //Méthode appelée lors du survol de la souris
    public void mouseEntered(MouseEvent event) { }

    //Méthode appelée lorsque la souris sort de la zone du bouton
    public void mouseExited(MouseEvent event) { }

    //Méthode appelée lorsque l'on presse le bouton gauche de la souris
    public void mousePressed(MouseEvent event) { }

    //Méthode appelée lorsque l'on relâche le clic de souris
    public void mouseReleased(MouseEvent event) { }
  }

}
