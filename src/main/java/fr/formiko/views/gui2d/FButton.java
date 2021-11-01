package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.Pixel;
import fr.formiko.usuel.images.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
*{@summary Extends of Button with some added functions.}<br>
*There is a default color for normal button, focus button &#38; disable button.<br>
*Defaults colors are in Data.<br>
*@author Hydrolien
*@version 1.54
*/
public class FButton extends JButton implements MouseListener{
  protected final int id; protected static int cpt=1;
  protected Image img;
  protected String nom;
  protected Panneau p;
  protected int action;
  protected boolean bordure=true;
  protected boolean cFondUseAlpha;
  protected boolean withBackground;
  protected int color=0;
  protected boolean isYellow=false;
  // protected Color cFond;
  // protected Color buttonColor;
  // CONSTRUCTORS --------------------------------------------------------------
  public FButton(String str, Panneau p, int action, Image imag){
    super();id=cpt; cpt++;
    // setBorderPainted(false);
    setOpaque(false);
    img = imag;
    // String s = "non null"; if(imag==null){ s="null";}
    // debug.débogage("Création du bouton "+str+" avec une image "+s);
    this.nom = str; this.p = p; this.action = action;
    this.addMouseListener(this); //Grâce à cette instruction, notre objet va s'écouter  Dès qu'un événement de la souris sera intercepté, il en sera averti
    // this.addActionListener(p.getBListener()); // permet a p d'écouter le bouton.
    setContentAreaFilled(false);
    setFocusPainted(false); //Diasble paint swap to next button when do tab.
    setCFondUseAlpha(false);
    withBackground=false;
    setBorder(new FBorder());
  }
  public FButton(String str, Panneau p, int action){
    super(str);id=cpt; cpt++;
    // setBorderPainted(false);
    setOpaque(false);
    debug.débogage("Création du bouton "+str);
    this.nom = str; this.p = p; this.action = action;
    this.addMouseListener(this);
    // if (p!=null) {
    //   this.addActionListener(p.getBListener()); // permet a p d'écouter le bouton.
    // }
    // this.setFont(Main.getFont1());
    setForeground(Color.BLACK);
    setContentAreaFilled(false);
    setFocusPainted(false); //paint swap to next button when do tab disable.
    setCFondUseAlpha(true);
    setText(nom);
    withBackground=true;
    setBorder(new FBorder());
  }
  // GET SET -------------------------------------------------------------------
  public String getNom(){ return nom;}
  public void setNom(String s){nom=s;debug.débogage("le nom a été changé pour "+s);}
  public int getActionB(){ return action;}
  public void setActionB(int x){action =x; }
  public void setBordure(boolean b){bordure=b;}
  // public void setCFond(Color c){cFond=c;}
  // public Color getCFont(){
  //   if(cFont!=null){return cFont;}
  //   else if(buttonColor!=null){return buttonColor;}
  //   else{return Main.getData().getButtonColor();}
  // }
  public static int getDimY(){ return (int)(Main.getOp().getTaillePolice1()*1.4);}
  public void setCFondUseAlpha(boolean b){cFondUseAlpha=b;}
  /**
  *{@summary return background color with or without alpha.}
  *With alpha if cFondUseAlpha==True;
  *@version 1.54
  */
  public Color getBackgroundColor(){
    Color c = Main.getData().getButtonColor(color);
    if(c==null){return null;}
    if(cFondUseAlpha){
      return new Color(c.getRed(), c.getGreen(), c.getBlue(), 100);
    }else{
      return c;
    }
  }
  public Color getCFont(){return getBackgroundColor();}
  public boolean getWithBackground(){return withBackground;}
  public void setWithBackground(boolean b){withBackground=b;}
  public FBorder getFBorder(){
    return (FBorder)getBorder();
  }
  public void setSize(int size){setSize(size,size);}
  // public void setButtonColor(Color c){buttonColor=c;}
  public void setColor(int x){color=x;}
  /**
  *{@summary Swap color beween green &#38; yellow.}<br>
  *@version 2.5
  */
  public void setDefaultColor(){
    if(isYellow){
      setColor(1);
    }else{
      setColor(0);
    }
  }
  public void setIsYellow(boolean b){isYellow=b;}
  // Fonctions propre ----------------------------------------------------------
  /**
  *{@summary To draw component.}<br>
  *It draw a fill rectangle as background color.
  *Draw it there alow to have alpha color.
  *@version 1.54
  */
  @Override
  public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    if(withBackground){
      //FontMetrics fm = new FontMetrics(Main.getFont1());//new FontRenderContext(null, false, false);
      //Rectangle2D rect = fm.getStringBounds(nom,g);
      //new FontRenderContext(null, false, false)
      //le fond
      if(color>-1 && !isOpaque()){
        g2d.setColor(getBackgroundColor());
        g2d.fillRect(0,0,getWidth(),getHeight());
      }
    }
    if(img==null){setText(nom);}
    else{g2d.drawImage(this.img,0,0, null);}
    if(bordure){
      try {
        ((FBorder)getBorder()).setColor(new Color(getCFont().getRed(), getCFont().getGreen(), getCFont().getBlue()));
      }catch (Exception e) {
        erreur.alerte("can't set border color");
      }
      // paintBorder(g2d);
    }
    super.paintComponent(g);
  }
  /**
  *{@summary Draw border.}<br>
  *@author Hydrolien
  *@version 1.54
  */
  public void paintBorder(Graphics2D g){
    //TODO #398 move to FBorder.
    g.setColor(new Color(getCFont().getRed(),getCFont().getGreen(),getCFont().getBlue()));
    byte x = Main.getBordureBouton();
    if(x<1){return;}
    BasicStroke ligne = new BasicStroke(x);
    g.setStroke(ligne);
    g.drawRect( 0, 0, getWidth(), getHeight());
  }
  @Override
  public void setEnabled(boolean b){
    if(b){
      setDefaultColor();
    }else{
      setColor(5);
    }
    super.setEnabled(b);
  }

  //Méthode appelée lors du clic de souris
  @Override
  public void mouseClicked(MouseEvent event) {}

  //Méthode appelée lors du survol de la souris
  @Override
  public void mouseEntered(MouseEvent event) {
    setSelected(true);
  }

  //Méthode appelée lorsque la souris sort de la zone du bouton
  @Override
  public void mouseExited(MouseEvent event) {
    setSelected(false);
  }

  //Méthode appelée lorsque l'on presse le bouton gauche de la souris
  @Override
  public void mousePressed(MouseEvent event) {}

  //Méthode appelée lorsque l'on relâche le clic de souris
  @Override
  public void mouseReleased(MouseEvent event) {
    if(!isEnabled()){return;}
    debug.débogage("Un bouton a été cliqué, l'action "+action+" vas être effectué.");
    Main.doAction(action);
  }
  /**
  *{@summary set the button selected or not depending of mouse x,y.}<br>
  *@version 2.2
  */
  public void updateSelected(){
    int mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
    int mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();
    setSelected(mouseX>=getX() && mouseX<=(getX()+getWidth()) && mouseY>=getY() && mouseY<=(getY()+getHeight()));
  }
  /**
  *{@summary set the button selected or not.}<br>
  *@param selected true if button is selected.
  *@version 2.2
  */
  public void setSelected(boolean selected){
    if(!isEnabled()){Panneau.getView().setMessageDesc(""); return;}
    if(selected){
      Panneau.getView().setMessageDesc(g.get("bouton.desc."+action)+getKeyboardKey());
      // setCFond(Main.getData().getButtonFocusColor());
      setColor(2);
    }else{
      Panneau.getView().setMessageDesc("");
      setDefaultColor();
    }
    repaint();
  }
  /**
  *{@summary return the shortcut key as a String.}<br>
  *If key don't exist it will return an empty String.
  *@version 2.2
  */
  private String getKeyboardKey(){
    String key = "";
    try { //on essaie de récupéré le raccourci clavier associé.
      key = ""+(char)Main.getKey(action+"");
    }catch (Exception e) {}
    if(!key.equals("")){key="("+g.get("raccourci")+" \""+key+"\")";}
    return key;
  }
}
