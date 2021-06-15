package fr.formiko.views.gui2d;

import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Main;
import fr.formiko.views.gui2d.PanneauInfo;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Graphics;

public class PanneauFinPartie extends Panneau {
  private Desc message;
  private GJoueur gj;
  private PanneauInfo pi;
  private Bouton mp;
  private Bouton c;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauFinPartie(){
    setVisible(false);
  }
  public void ini(String s, GJoueur gj, boolean withButton, boolean canResumeGame){
    this.setLayout(null);
    addMessage(s);
    addPanneauInfo(gj);
    if(withButton){
      addBoutonMenuPrincipal();
      if(canResumeGame){
        addBoutonContinuer();
      }
    }
    message.setBounds(0,0,this.getWidth(),Desc.getDimY());
    pi.setBounds(0,message.getHeight(),pi.getWidth(),pi.getHeight());
    setBackground(Main.getData().getButtonColor());
    setOpaque(true);
    setVisible(true);
  }
  // GET SET --------------------------------------------------------------------
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    // try {
      // message.setBounds(0,0,this.getWidth(),Main.getTaillePolice2()*2);
      // pi.setBounds(0,message.getHeight(),pi.getWidth(),pi.getHeight());
      //mp.setBounds()
    // }catch (Exception e) {
    //   erreur.erreur("certain élément graphique de PanneauFinPartie ne sont pas affichable.");
    // }
    super.paintComponent(g);
  }
  public void addMessage(String s){
    message = new Desc(this.getWidth(),(int)(Main.getTaillePolice2()*1.5));
    message.setText(s);
    add(message);
  }
  public void addPanneauInfo(GJoueur gj){
    this.gj=gj;
    pi = new PanneauInfo(gj.scoreToGString());
    add(pi);
    //pi.setBounds(0,message.getHeight(),pi.getWidth(),pi.getHeight());
  }
  public void addBoutonMenuPrincipal(){
    mp= new Bouton(g.getM("quitterToMp"),Panneau.getView().getPj(),112);
    mp.setSize(getWidth()/2, Desc.getDimY());
    mp.setLocation(0,getHeight()-mp.getHeight());
    add(mp);
  }
  public void addBoutonContinuer(){
    c = new Bouton(g.getM("continuerJeu"),Panneau.getView().getPj(),113);
    c.setSize(getWidth()/2, Desc.getDimY());
    c.setLocation(getWidth()/2,getHeight()-mp.getHeight());
    add(c);
  }
}
