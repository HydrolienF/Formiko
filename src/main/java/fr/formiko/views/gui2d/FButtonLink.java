package fr.formiko.views.gui2d;

import fr.formiko.usuel.fichier;

import java.awt.Image;
import java.awt.event.MouseEvent;

public class FButtonLink extends FButton {
  private String url;
  public FButtonLink(String str, FPanel p, String url, Image image){
    super(str, p, -2, image);
    this.url=url;
  }
  /**
  *{@summary Update color at every clics.}<br>
  *@lastEditedVersion 2.10
  */
  @Override
  public void mouseReleased(MouseEvent event) {
    // super.mouseReleased(event);
    fichier.openWebLink(url);
  }

}
