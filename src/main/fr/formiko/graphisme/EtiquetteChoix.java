package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import javax.swing.JComboBox;
import fr.formiko.usuel.liste.GString;
import fr.formiko.formiko.Main;
import java.awt.Graphics;
import java.awt.Font;

public class EtiquetteChoix extends Panneau{
  private JComboBox<String> choixX;
  private Desc choixXDesc;
  private int k;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public EtiquetteChoix(int x, String clé,GString gs){
    debug.débogage("Lancement de la création d'une EtiquetteChoix avec "+gs.length()+" éléments");
    this.setLayout(null);
    setOpaque(false);
    choixXDesc = new Desc();
    choixXDesc.setTexte(g.getM(clé)+" : ");
    choixXDesc.setFondTransparent();

    choixX = gs.getComboBox(x);
    choixX.setFont(Main.getFont1(0.9));
    add(choixX);add(choixXDesc);
    int wi2 = Main.getTailleElementGraphiqueX(960);
    int taille = Desc.getDimY();
    choixXDesc.setBounds(0,taille*k,wi2/2,Desc.getDimY());
    choixX.setBounds(wi2/2,taille*k,wi2/3,Desc.getDimY());
  }
  public EtiquetteChoix(String clé, GString gs){ this(0,clé,gs);}
  // GET SET --------------------------------------------------------------------
  public String getSelectedItem(){ return choixX.getSelectedItem()+"";}
  public JComboBox getChoixX(){ return choixX;}
  public Desc getChoixXDesc(){ return choixXDesc;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    //debug.débogage("Lancement de eDif.paintComponent");
  }
  public void setPos(int k){
    this.k = k;
    //repaint();
  }
  public void setTaille(int x, int y){
    choixX.setSize(x/2,y);
    choixXDesc.setSize(x/2,y);
  }
  public void setPolice(Font font){
    choixX.setFont(font);
    choixXDesc.setFont(font);
  }
}
