package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;

/**
*{@summary Panel with buttons able to launch an URL.}<br>
*Every button have a desciption, an image &#38; an URL to launch.
*@lastEditedVersion 2.21
*/
public class FPanelLinks extends FPanel {
  private int buttonSize;
  private int spaceBetweenButton;
  // private List<FButtonLink>; //maybe we don't need it.

  /**
  *{@summary Create a new link panel that will resize himself.}<br>
  *@param buttonSize the size of the button
  *@param spaceBetweenButton the free space between 2 button
  *@lastEditedVersion 2.21
  */
  public FPanelLinks(int buttonSize, int spaceBetweenButton){
    super();
    this.buttonSize=buttonSize;
    this.spaceBetweenButton=spaceBetweenButton;
  }

  /**
  *{@summary Add a button with a desciption, an image &#38; an URL to launch.}<br>
  *@lastEditedVersion 2.21
  */
  public void addButton(String buttonCodeName, String url){
    Main.getData().iniOtherImages();
    FButtonLink bl = new FButtonLink(g.get(buttonCodeName), this, url, fr.formiko.usuel.images.image.resize(Main.getData().getImage(buttonCodeName), buttonSize, buttonSize));
    bl.setBounds(getWidth(), 0, buttonSize, buttonSize);
    add(bl);
    int addWidth = spaceBetweenButton+buttonSize;
    setSize(getWidth()+addWidth, buttonSize); //add space for the button.
    setLocation(getX()-addWidth/2, getY()); //center
  }

}
