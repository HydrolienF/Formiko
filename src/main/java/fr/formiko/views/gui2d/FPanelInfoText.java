package fr.formiko.views.gui2d;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Font;
import java.util.List;

/**
*{@summary A simple text Pannel.}<br>
*@lastEditedVersion 2.23
*@author Hydrolien
*/
public class FPanelInfoText extends FPanel {
  private int nbrDeDesc;
  private FLabel descs [];
  private int yPi;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *It create a text panel from a list of String.
  *@param gs list of String to paint
  *@param width with of this &#38; max width of small text item
  *@param withAlpha if true set small text item color with alpha
  *@param font Font to get font size to use &#38; use it on the text item
  *@lastEditedVersion 2.23
  */
  public FPanelInfoText(List<String> gs, int width, boolean withAlpha, Font font){
    setLayout(null);
    nbrDeDesc=gs.size();
    int yD = FLabel.getDimY();
    if(font!=null){
      yD = (int)(font.getSize()*1.2);
    }
    yPi=(yD+1)*nbrDeDesc;
    this.setSize(width,yPi);
    int k=0;
    descs = new FLabel[nbrDeDesc];
    for (String s : gs ) {
      FLabel desc = new FLabel(s);
      if(withAlpha){
        desc.setBackground(Main.getData().getButtonColor());
      }
      if(font!=null){
        desc.setFont(font);
      }
      desc.updateSize();
      desc.setLocation((getWidth()-desc.getWidth())/2,k*yD);
      add(desc);
      descs[k]=desc;
      k++;
    }
  }
  /**
  *{@summary Secondary constructor.}<br>
  *It create a text panel from a list of String.<br>
  *Font will be default 1.<br>
  *@param gs list of String to paint
  *@param width with of this &#38; max width of small text item
  *@param withAlpha if true set small text item color with alpha
  *@lastEditedVersion 2.23
  */
  public FPanelInfoText(List<String> gs, int width, boolean withAlpha){
    this(gs,width,withAlpha,null);
  }
  /**
  *{@summary Secondary constructor.}<br>
  *It create a text panel from a list of String.
  *@param gs list of String to paint
  *@lastEditedVersion 2.23
  */
  public FPanelInfoText(List<String> gs){
    this(gs, getView().getPz().getbuttonSize()*5,false);
  }
  /**
  *{@summary Secondary constructor.}<br>
  *It create a text panel from an ant description.
  *@param f the ant to describe
  *@param width with of this &#38; max width of small text item
  *@lastEditedVersion 2.23
  */
  public FPanelInfoText(Fourmi f,int width){
    this(f.descriptionGString(),width,false);
  }
  /**
  *{@summary Secondary constructor.}<br>
  *It create a text panel from an ant description.
  *@param f the ant to describe
  *@lastEditedVersion 2.23
  */
  public FPanelInfoText(Fourmi f){
    this(f.descriptionGString());
  }
  // GET SET -------- ------------------------------------------------------------
  public int length(){ return nbrDeDesc;}
  public int getXPi(){ return getWidth();}
  public int getYPi(){ return yPi;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Standard toString(), return a String that describe this.}<br>
  *@lastEditedVersion 2.23
  */
  public String toString(){
    String r = super.toString()+" number of desc "+nbrDeDesc+" ";
    if(descs!=null){
      for (FLabel d : descs ) {
        try {
          r+=" "+d.getText();
        }catch (Exception e) {
          erreur.alerte("can't read desc text.");
        }
      }
    }
    return r;
  }
}
