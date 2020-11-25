package fr.formiko.graphisme;
//import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class PanneauEchap extends Panneau{
  private Bouton tb[];
  private boolean aff;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public void PanneauEchap(){
    //super();
    this.setLayout(null);
    tb = new Bouton[0];
    setOpaque(true);
  }
  public void construire(){
    if(estContruit()){return;}
    //this.setBackground(new Color(50,50,50,100));
    Main.getPs().setSize(0,0);
    setSize(Main.getDimX(),Main.getDimY());
    int lentb = 6;
    tb = new Bouton[lentb];
    for (int i=0;i<lentb ;i++ ) {
      String s ="";
      if(i> 0 && i<3){s=" ("+g.get("bientôt")+")";}//TODO s'assurer que ce n'est plus utile puis le retirer.
      tb[i]=new Bouton(g.getM("bouton.nom."+(-10-i))+s,Main.getPj(),-10-i);
      //tb[i].setBounds(0,Desc.getDimY()*i*2,Main.getDimX()/4,Desc.getDimY());
      tb[i].setOpaque(true);
      tb[i].setCFond(new Color(55, 255, 0));
      add(tb[i]);
    }
    //miseALaMemeTaille(lentb);
    revalidate();
    repaint();
  }
  @Override
  public void setVisible(boolean b){
    if(b){construire();Main.getPs().setSize(0,0);}
    else{Main.getPs().actualiserTaille();}
    setAff(b);
    super.setVisible(b);
  }
  // GET SET --------------------------------------------------------------------
  public boolean getAff(){return aff;}
  public void setAff(boolean b){aff=b;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    try {
      int lentb = tb.length;
      if(lentb==0){return;}
      int xCentré = (int)(Main.getDimX()*1.5/4);
      int tailleY =(int)(Bouton.getDimY()*lentb*1.5 - Bouton.getDimY()*0.5);
      int yCentré = ((Main.getDimY()-tailleY)/2);
      g2d.setColor(new Color(0,250,255));
      int bordure = Main.getTailleElementGraphique(10);
      g2d.fillRect(-bordure+xCentré,-bordure+yCentré,2*bordure+(Main.getDimX()/4),2*bordure+tailleY);
      g2d.setColor(new Color(20,20,255));
      g2d.setStroke(new BasicStroke(Main.getTailleElementGraphique(3)));
      g2d.drawRect(-bordure+xCentré,-bordure+yCentré,2*bordure+(Main.getDimX()/4),2*bordure+tailleY);
      for (int i=0;i<lentb ;i++ ) {
        tb[i].setBounds(xCentré,yCentré+(int)(Bouton.getDimY()*i*1.5),Main.getDimX()/4,Bouton.getDimY());
      }
    }catch (Exception e) {}
  }
  public boolean estContruit(){
    if(tb==null){return false;}
    return !(tb.length==0);}//il est contruit si le tableau n'est pas vide.
  public void miseALaMemeTaille(int lentb){
    int xMax = 0;
    for (int i=0;i<lentb ;i++ ) {
      if(tb[i].getWidth()>xMax){xMax = tb[i].getWidth();}
    }
    for (int i=0;i<lentb ;i++ ) {
      tb[i].setSize(xMax,getHeight());
    }
  }
}
