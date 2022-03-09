package fr.formiko.views.gui2d;

import fr.formiko.usuel.fichier;
import fr.formiko.usuel.g;

import java.awt.Image;
import java.awt.event.MouseEvent;

/**
*{@summary Button able to launch an URL.}<br>
*@lastEditedVersion 2.21
*/
public class FButtonLink extends FButton {
  private String url;
  /**
  *{@summary Create a new button that can open a link.}<br>
  *@param name the name of the button
  *@param p panel that contain this
  *@param url the url to open
  *@param image image to represent the button
  *@lastEditedVersion 2.21
  */
  public FButtonLink(String name, FPanel p, String url, Image image){
    super(name, p, -2, image);
    this.url=url;
    addMouseMotionListener(new DescMouseMotionListener());
  }
  public String toString(){
    return super.toString()+" "+url;
  }
  /**
  *{@summary Open the corresponding URL.}<br>
  *@lastEditedVersion 2.21
  */
  @Override
  public void mouseReleased(MouseEvent event) {
    // super.mouseReleased(event);
    fichier.openWebLink(url);
  }
  /**
  *{@summary set the button selected or not.}<br>
  *Desc are print on the button (mouse located).
  *@param selected true if button is selected.
  *@lastEditedVersion 2.21
  */
  @Override
  public void setSelected(boolean selected){
    super.setSelected(selected, true);
  }
  @Override
  public String getDesc(){
    return g.getM(nom);
  }

}