package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Graphics;
import fr.formiko.usuel.Temps;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.type.str;
import fr.formiko.formiko.Touches;

public class PanneauTBoolean extends PanneauTX{
  private int choix;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauTBoolean(String desc){super();
    if(desc==null){ return;}
    addKeyListener(new Touches());
    tailleBouton = Main.getTailleBoutonTX();
    this.descTI = desc;
    debug.débogage("Création d'un PanneauTBoolean");
    this.setLayout(new GridBagLayout());
    int nbrDeCase = 2;
    if (nbrDeCase > 0){Main.getPb().setDescTI(descTI);}
    else{((PanneauBouton) pb).setDescTI("");}
    x = 2;
    y = 1;
    this.setSize(x*tailleBouton,y*tailleBouton);
    Bouton tB [] = new Bouton [nbrDeCase];
    Dimension dim = new Dimension(tailleBouton,tailleBouton);
    //tB[0]=new Bouton(g.get("OUI"), Main.getPp().getPj(),40);
    //tB[1]=new Bouton(g.get("NON"), Main.getPp().getPj(),41);
    tB[0]=new Bouton("O", Main.getPp().getPj(),40);
    tB[1]=new Bouton("N", Main.getPp().getPj(),41);
    for (Bouton b :tB){b.setPreferredSize(dim);}
    GridBagConstraints gbc = new GridBagConstraints();
    int k=0;
    gbc.gridx = 0;
    for (int j=0;k < nbrDeCase;j++ ) {
      gbc.gridy = j;
      this.add(tB[k],gbc);k++;
    }
    debug.débogage(k+" boutons ont été ajoutés");
  }
  // GET SET --------------------------------------------------------------------
  // Fonctions propre -----------------------------------------------------------
  public static boolean getChoixId(){
    int id2 = -1;
    debug.débogage("lancement d'une boucle de choix.");
    //Main.getPj().repaint();
    while(id2==-1){
      id2 = Main.getPp().getPj().getPb().getChoixId();
      Temps.pause(10);
    } // on a reçu 0 ou 1.
    boolean b = str.iToB(id2);
    Main.getPp().getPj().getPb().setChoixId(-1);
    Main.getPp().getPj().setDescTI("");
    Main.getPp().getPj().remove(Main.getPp().getPj().getPti());
    return b;
  }
}
