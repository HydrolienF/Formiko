package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.types.str;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
*{@summary Escap panel.}<br>
*Used to pause game.
*@author Hydrolien
*@lastEditedVersion 2.30
*/
public class FPanelEchap extends FPanel{
  private FButton tb[];
  private boolean visible;
  private FPanelOptions pop;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor that set not visible.}
  *@lastEditedVersion 2.23
  */
  public FPanelEchap(){
    setVisible(false);
  }
  /**
  *{@summary standard build methode.}
  *Disable all other panel #38; print this one with the 5 button.
  *@lastEditedVersion 2.30
  */
  public void build(){
    if(estContruit()){return;}
    pop=new FPanelOptions();
    pop.setBounds(0,0,getWidth(),getHeight());
    add(pop);
    //setBackground(new Color(50,50,50,100));
    getView().getPs().setSize(0,0);
    setSize(Main.getDimX(),Main.getDimY());
    int lentb = 6;
    tb = new FButton[lentb];
    for (int i=0;i<lentb ;i++ ) {
      String s ="";
      tb[i]=new FButton(g.getM("bouton.nom."+(-10-i))+s,getView().getPj(),-10-i);
      if(i==1 && Main.getPartie().getPartieFinie()){tb[i].setEnabled(false);}
      //tb[i].setBounds(0,FLabel.getDimY()*i*2,Main.getDimX()/4,FLabel.getDimY());
      // tb[i].setCFond(new Color(55, 255, 0));
      tb[i].setCFondUseAlpha(false); //TODO #40
      add(tb[i]);
    }
    revalidate();
    getView().paint();
  }
  // GET SET -------------------------------------------------------------------
  public void setTb(FButton tbTemp[]){tb=tbTemp;}
  public boolean getVisible(){return visible;}
  public boolean isOn(){return getVisible();}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary standard paint methode.}
  *@lastEditedVersion 2.30
  */
  @Override
  public void paintComponent(Graphics g){
    // TODO draw blue area also when pop is draw. (with the good size).
    Graphics2D g2d = (Graphics2D)g;
    if(pop.isVisible()){
      super.paintComponent(g);
    }else{
      try {
        int lentb = tb.length;
        if(lentb==0){return;}
        int xCentré = (int)(Main.getDimX()*1.5/4);
        int tailleY =(int)(FButton.getDimY()*lentb*1.5 - FButton.getDimY()*0.5);
        int yCentré = ((Main.getDimY()-tailleY)/2);
        g2d.setColor(new Color(0,250,255));
        int bordure = Main.getTailleElementGraphique(10);
        g2d.fillRect(-bordure+xCentré,-bordure+yCentré,2*bordure+(Main.getDimX()/4),2*bordure+tailleY);
        g2d.setColor(new Color(20,20,255));
        g2d.setStroke(new BasicStroke(Main.getTailleElementGraphique(3)));
        g2d.drawRect(-bordure+xCentré,-bordure+yCentré,2*bordure+(Main.getDimX()/4),2*bordure+tailleY);
        for (int i=0;i<lentb ;i++ ) {
          tb[i].setBounds(xCentré,yCentré+(int)(FButton.getDimY()*i*1.5),Main.getDimX()/4,FButton.getDimY());
        }
      }catch (Exception e) {
        erreur.alerte("something when wrong when drawing component");
      }
    }
  }
  /**
  *{@summary Set FPanelEchap visible.}<br>
  *It Override Component.setVisible() but also build panel if needed &#38; update FPanelSup size.<br>
  *@lastEditedVersion 2.30
  */
  @Override
  public void setVisible(boolean b){
    // erreur.info("Set Pe visibility to "+b);
    if(b){build();getView().getPs().setSize(0,0);}
    else{
      if(pop!=null){
        pop.setVisible(b);
      }
      try {
        getView().getPs().updateSize();
      }catch (Exception e) {
        if(estContruit()){
          erreur.alerte("Can't update size of Ps.");
        }
      }
    }
    visible=b;
    if(getView().getPz()!=null){
      getView().getPz().setEnabled(!visible);
    }
    if(getView().getPc()!=null){
      getView().getPc().setEnabled(!visible);
    }
    // try{
    //   getView().getPs().setEnabled(!visible);
    // }catch (Exception e) {}
    if(getView().getPa()!=null){
      getView().getPa().setEnabled(!visible);
    }
    // try{
    //   getView().getPmmc().setEnabled(!visible);
    // }catch (Exception e) {}
    super.setVisible(b);
    if(!b){
      action.updateMouseLocation();
    }
    if(getView().getPigm()!=null){
      getView().getPigm().updateColorButtonEscape();
    }
  }
  /**
  *{@summary Return true if only FPanelEchap button sould be enable.}
  *@lastEditedVersion 1.41
  */
  public boolean estContruit(){
    return tb!=null;
  }
  /**
  *{@summary Ask save name in gui.}
  *@param defaultName the default save name
  *@lastEditedVersion 2.17
  */
  public String getSaveName(String defaultName){
    setVisible(false);
    FOptionPane opane = new FOptionPane(); //, g.get("sauvegarder")
    opane.addField(defaultName);
    opane.build();
    String s=opane.getContent();
    return str.sToFileName(s);
  }

  public void showPop(){
    pop.ini();
    pop.setCurrentTabId(0);
    pop.setVisible(true);
  }
}
