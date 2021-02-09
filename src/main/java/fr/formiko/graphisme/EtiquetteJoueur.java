package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import java.awt.Graphics;
import fr.formiko.formiko.Main;
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import fr.formiko.formiko.Joueur;
import javax.swing.JTextField;
import fr.formiko.formiko.Pheromone;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.*;

public class EtiquetteJoueur extends Panneau{
  private static int idCpt; private final int id;
  private Champ dsc;
  private boolean ia;
  private boolean ouvert;
  private ComboBox<String> combo;
  private Champ couleur;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public EtiquetteJoueur(String s, Boolean b){
    id =idCpt; idCpt++;
    setOpaque(false);
    dsc = new Champ();
    if(b!=null){ dsc.setText(s);}
    else{dsc.setText("");}
    dsc.setFondTransparent();
    dsc.setBorder(null);
    ItemState2 is2 = new ItemState2();
    is2.setEj(this);
    //addKeyListener(is2);
    dsc.addKeyListener(is2);
    add(dsc);
    String[] tab = {g.getM("joueur"), g.getM("ia"), g.getM("fermé")};
    combo = new ComboBox<String>(tab);
    couleur=new Champ();
    couleur.setFondTransparent();
    couleur.setBorder(null);
    if(b==null){ ouvert=false; combo.setSelectedIndex(2);}
    else{ouvert=true;ia=b;iniCouleur();}
    if(ia){combo.setSelectedIndex(1);}
    ItemState is = new ItemState();
    combo.addItemListener(is);
    combo.setFont(Main.getFont1(0.9));
    is.setEj(this);
    add(combo);
    couleur.addKeyListener(is2);
    add(couleur);

  }
  public EtiquetteJoueur(boolean b){
    this(Joueur.get1Pseudo(),b);
  }
  public EtiquetteJoueur(){
    this(Joueur.get1Pseudo(),null);
  }

  // GET SET --------------------------------------------------------------------
  public int getId(){return id;}
  public String getPseudo(){return dsc.getText();}
  public void setPseudo(String s){dsc.setText(s);}
  public boolean getIa(){return ia;}
  public void setIa(boolean b){ia=b;}
  public boolean getOuvert(){return ouvert;}
  public void setOuvert(Boolean b){ouvert=b;}
  public Pheromone getCouleur(){return Pheromone.sToPh(couleur.getText());}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    return getPseudo() +" id:"+ id + " ia:"+getIa()+" ouvert:"+getOuvert();
  }
  /**
  *{@summary Standard equals function with id.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof EtiquetteJoueur)){return false;}
    return getId()==((EtiquetteJoueur)(o)).getId();
  }
  public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    int taille = Main.getF().getWidth()/2;
    int arrondi = Desc.getDimY();
    Color col;
    if(ouvert){
      Pheromone ph = getCouleur();
      col = ph.phToColor();
    }else{
      col = new Color(200,200,200);
    }
    if(col.getRed()+col.getGreen()+col.getBlue() < 400){
      dsc.setForeground(Color.WHITE);
      couleur.setForeground(Color.WHITE);
    }else{
      dsc.setForeground(Color.BLACK);
      couleur.setForeground(Color.BLACK);
    }
    //g2d.setColor(new Color(col.getRed(),col.getGreen(),col.getBlue(),152));
    g2d.setColor(col); // une couleur sans transparence pour évité d'avoir a redessiner toute la fenetre.
    g2d.fillRoundRect(0,0,taille*7/10+taille/7,Desc.getDimY()*2,arrondi,arrondi);
    dsc.setBounds(Desc.getDimY()/2,0,taille*5/10-Desc.getDimY()/4);
    combo.setBounds(taille*5/10,0,taille/7,Desc.getDimY());
    couleur.setBounds(taille*5/10+taille/7,0,taille/7,Desc.getDimY());
    //ajouter un bouton changer la couleur alléatoirement
    g2d.setColor(new Color(0,0,0));
    peintBordure(g2d,taille,arrondi);
  }
  public void peintBordure(Graphics2D g, int taille, int arrondi){
    byte x = 3;//Main.getBordureBouton();
    if(x<1){return;}
    BasicStroke ligne = new BasicStroke(x);
    g.setStroke(ligne);
    g.drawRoundRect(0,0,taille*7/10+taille/7,Desc.getDimY()*2,arrondi,arrondi);
  }
  public void afficheToi(){
    System.out.println(this);
  }
  public void iniCouleur(){
    couleur.setText(new Pheromone().phToS());
  }

  //Classe interne implémentant l'interface ItemListener
  class ItemState implements ItemListener{
    public EtiquetteJoueur ej;
    public void itemStateChanged(ItemEvent e) {
      debug.débogage("événement déclenché sur : " + e.getItem());
      PanneauNouvellePartie pnp = Main.getPnp();
      if(e.getStateChange()==1){ // si l'action est bien sélectionner.
        if(combo.getItemAt(0).equals(e.getItem())){
          ouvert=true; ia=false; setPseudo(g.getM("joueur")+" "+(id+1));
          //if ((combo.getSelectedItemReminder()).equals(combo.getItemAt(2))){
            iniCouleur();
          //}
        }else if(combo.getItemAt(1).equals(e.getItem())){
          ouvert=true; ia=true; setPseudo(Joueur.get1Pseudo());
          //if ((combo.getSelectedItemReminder()).equals(combo.getItemAt(2))){
            iniCouleur();
          //}
        }else if(combo.getItemAt(2).equals(e.getItem())){
          //if(ej.equals(pnp.getGej().getFin().getContenu())){}
          ouvert=false;
          pnp.getGej().retirer(ej);
          pnp.rafraichirPgej();
          //on retire l'EtiquetteJoueur.
          //Main.getPnp().get...
        }
      }
      if(pnp.getGej().getFin().getContenu().getOuvert()){ // si la dernière n'est pas fermé.
        pnp.getGej().ajouter(new EtiquetteJoueur());
        pnp.rafraichirPgej();
      }
      pnp.repaint();
    }
    public void setEj(EtiquetteJoueur ejTemp){ ej=ejTemp;}
  }
  class ItemState2 implements KeyListener{
    public EtiquetteJoueur ej;
    public void keyReleased(KeyEvent e) {
      Main.getPnp().getPGej().repaint();
    }
    public void keyPressed(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void setEj(EtiquetteJoueur ejTemp){ ej=ejTemp;}
  }
}
