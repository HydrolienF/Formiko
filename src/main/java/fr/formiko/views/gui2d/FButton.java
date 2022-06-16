package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Pixel;
import fr.formiko.usual.images.image;

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
*@lastEditedVersion 1.54
*/
public class FButton extends JButton implements MouseListener {
  protected final int id; protected static int cpt=1;
  protected Image img;
  protected String nom;
  protected FPanel p;
  protected int action; // -2 = do nothing.
  protected boolean bordure=true;
  protected boolean cFondUseAlpha;
  protected boolean withBackground;
  protected int color=0;
  protected boolean isYellow=false;
  protected boolean drawOnlySelectedBorder;
  // protected Color cFond;
  // protected Color buttonColor;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Create a new FButton.}
  *@param name the name of the button
  *@param p panel that contain this
  *@param action action to do. -2 = no action to do.
  *@param imag image to represent the button
  *@lastEditedVersion 2.21
  */
  public FButton(String name, FPanel p, int action, Image imag){
    super();id=cpt; cpt++;
    // setBorderPainted(false);
    setOpaque(false);
    img = imag;
    // String s = "non null"; if(imag==null){ s="null";}
    // debug.débogage("Création du bouton "+str+" avec une image "+s);
    this.nom = name; this.p = p; this.action = action;
    this.addMouseListener(this); //Grâce à cette instruction, notre objet va s'écouter  Dès qu'un événement de la souris sera intercepté, il en sera averti
    // this.addActionListener(p.getBListener()); // permet a p d'écouter le bouton.
    setContentAreaFilled(false);
    setFocusPainted(false); //Diasble paint swap to next button when do tab.
    setCFondUseAlpha(false);
    withBackground=false;
    setBorder(new FBorder());
  }
  /**
  *{@summary Create a new FButton without image.}
  *@param name the name of the button
  *@param p panel that contain this
  *@param action action to do. -2 = no action to do.
  *@lastEditedVersion 2.21
  */
  public FButton(String str, FPanel p, int action){
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
  public String getName(){ return nom;}
  public void setNom(String s){nom=s;}
  public int getActionB(){ return action;}
  public void setActionB(int x){action =x; }
  public void setBordure(boolean b){bordure=b;}
  // public FPanel getP(){return p;}
  // public void setCFond(Color c){cFond=c;}
  // public Color getCFont(){
  //   if(cFont!=null){return cFont;}
  //   else if(buttonColor!=null){return buttonColor;}
  //   else{return Main.getData().getButtonColor();}
  // }
  public static int getDimY(){ return (int)(Main.getOp().getFontSizeText()*1.4);}
  public void setCFondUseAlpha(boolean b){cFondUseAlpha=b;}
  /**
  *{@summary return background color with or without alpha.}
  *With alpha if cFondUseAlpha==True;
  *@lastEditedVersion 1.54
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
  *{@summary Swap color between green &#38; yellow.}<br>
  *@lastEditedVersion 2.5
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
  *@lastEditedVersion 1.54
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
      if(getBorder()!=null && getBorder() instanceof FBorder){
        updateBorderColor();
      }
    }
    super.paintComponent(g);
  }
  /**
  *{@summary Update the border color.}<br>
  *@lastEditedVersion 2.21
  */
  protected void updateBorderColor(){
    if (drawOnlySelectedBorder && color!=2) {
      ((FBorder)getBorder()).setColor(new Color(0,0,0,0));
    }else{
      ((FBorder)getBorder()).setColor(new Color(getCFont().getRed(), getCFont().getGreen(), getCFont().getBlue()));
    }
  }
  /**
  *{@summary Draw border.}<br>
  *@lastEditedVersion 1.54
  */
  public void paintBorder(Graphics2D g){
    //TODO #398 move to FBorder.
    g.setColor(new Color(getCFont().getRed(),getCFont().getGreen(),getCFont().getBlue()));
    byte x = Main.getBorderButtonSize();
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
  *@lastEditedVersion 2.2
  */
  public void updateSelected(){
    int mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
    int mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();
    setSelected(mouseX>=getX() && mouseX<=(getX()+getWidth()) && mouseY>=getY() && mouseY<=(getY()+getHeight()));
  }
  /**
  *{@summary set the button selected or not.}<br>
  *@param selected true if button is selected
  *@param mouseLocated true if desc is mouse located
  *@lastEditedVersion 2.10
  */
  public void setSelected(boolean selected, boolean mouseLocated){
    if(!isEnabled()){FPanel.getView().setMessageDesc("", mouseLocated); return;}
    if(selected){
      FPanel.getView().setMessageDesc(getDesc(), mouseLocated);
      setColor(2);
    }else{
      FPanel.getView().setMessageDesc("", mouseLocated);
      setDefaultColor();
    }
  }
  /**
  *{@summary Set the button selected or not.}<br>
  *By default desc is not mouse located.
  *@param selected true if button is selected
  *@lastEditedVersion 2.10
  */
  public void setSelected(boolean selected){
    setSelected(selected, false);
  }
  /**
  *{@summary Return the shortcut key as a String.}<br>
  *If key don't exist it will return an empty String.
  *@lastEditedVersion 2.2
  */
  private String getKeyboardKey(){
    String key = "";
    try { //on essaie de récupéré le raccourci clavier associé.
      key = ""+(char)Main.getKey(action+"");
    }catch (Exception e) {}
    if(!key.equals("")){key="("+g.get("raccourci")+" \""+key+"\")";}
    return key;
  }
  /**
  *{@summary Return the description of the button.}<br>
  *@lastEditedVersion 2.10
  */
  protected String getDesc(){
    String desc = g.get("bouton.desc."+action);
    if(desc.equals("bouton.desc."+action)){
      return "";
    }
    return desc+getKeyboardKey();
  }
}
