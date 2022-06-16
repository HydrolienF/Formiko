package fr.formiko.views.gui2d;

import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Main;
import fr.formiko.views.gui2d.FPanelInfoText;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;

import java.awt.Graphics;

public class FPanelFinPartie extends FPanel {
  private FLabel message;
  private GJoueur gj;
  private FPanelInfoText pi;
  private FButton mp;
  private FButton c;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelFinPartie(){
    setVisible(false);
  }
  public void ini(String s, GJoueur gj, boolean withButton, boolean canResumeGame){
    this.setLayout(null);
    addMessage(s);
    addFPanelInfo(gj);
    if(withButton){
      addBoutonMenuPrincipal();
      if(canResumeGame){
        addBoutonContinuer();
      }
    }
    message.setBounds(0,0,this.getWidth(),FLabel.getDimY());
    pi.setBounds(0,message.getHeight(),pi.getWidth(),pi.getHeight());
    setBackground(Main.getData().getButtonColor());
    setOpaque(true);
    setVisible(true);
  }
  // GET SET -------------------------------------------------------------------
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    // try {
      // message.setBounds(0,0,this.getWidth(),Main.getFontSizeTitle()*2);
      // pi.setBounds(0,message.getHeight(),pi.getWidth(),pi.getHeight());
      //mp.setBounds()
    // }catch (Exception e) {
    //   erreur.erreur("certain élément graphique de FPanelFinPartie ne sont pas affichable.");
    // }
    super.paintComponent(g);
  }
  public void addMessage(String s){
    message = new FLabel(this.getWidth(),(int)(Main.getFontSizeTitle()*1.5));
    message.setText(s);
    add(message);
  }
  public void addFPanelInfo(GJoueur gj){
    this.gj=gj;
    pi = new FPanelInfoText(gj.scoreToGString());
    add(pi);
    //pi.setBounds(0,message.getHeight(),pi.getWidth(),pi.getHeight());
  }
  public void addBoutonMenuPrincipal(){
    mp= new FButton(g.getM("quitterToMp"),FPanel.getView().getPj(),112);
    mp.setSize(getWidth()/2, FLabel.getDimY());
    mp.setLocation(0,getHeight()-mp.getHeight());
    add(mp);
  }
  public void addBoutonContinuer(){
    c = new FButton(g.getM("continuerJeu"),FPanel.getView().getPj(),113);
    c.setSize(getWidth()/2, FLabel.getDimY());
    c.setLocation(getWidth()/2,getHeight()-mp.getHeight());
    add(c);
  }
}
