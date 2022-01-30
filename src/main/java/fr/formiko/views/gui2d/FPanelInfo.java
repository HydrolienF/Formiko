package fr.formiko.views.gui2d;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
*{@summary Panel that containt infos as a List of Panel.}<br>
*@author Hydrolien
*@lastEditedVersion 2.7
*/
public class FPanelInfo extends FPanel {
  protected Liste<FPanel> lp;
  // CONSTRUCTORS --------------------------------------------------------------
  /** Main constructor. */
  protected FPanelInfo(){super();}

  /**
  *{@summary Return a new builder for this.}<br>
  *@lastEditedVersion 2.7
  */
  public static FPanelInfoBuilder builder(){
    return new FPanelInfoBuilder();
  }
  // GET SET -------------------------------------------------------------------
  /** Return number of sub Panel. */
  public int length(){ return lp.length();}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Standard paintComponent.}<br>
  *@lastEditedVersion 2.7
  */
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  /**
  *{@summary Strandard to string function that also print child.}<br>
  *@lastEditedVersion 2.7
  */
  public String toString(){
    String r = super.toString()+"\n";//+" number of desc "+nbrDeDesc+" ";
    if(lp!=null){
      for (FPanel p : lp ) {
        r+=" "+p.toString();
      }
    }
    return r;
  }
  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Standard Builder for this.}<br>
  *@author Hydrolien
  *@lastEditedVersion 2.7
  */
  static class FPanelInfoBuilder {
    protected Liste<FPanel> lp;
    protected int x=-1;
    protected int yByElement=-1;
    protected boolean allowPanelsOnSameLine;
    // CONSTRUCTORS --------------------------------------------------------------
    /**
    *{@summary Main constructor.}<br>
    $It only initialize List.
    *@lastEditedVersion 2.7
    */
    protected FPanelInfoBuilder(){
      lp = new Liste<FPanel>();
      allowPanelsOnSameLine=false;
    }

    // GET SET -------------------------------------------------------------------
    public FPanelInfoBuilder setX(int x){this.x = x; return this;}
    public FPanelInfoBuilder setYByElement(int y){this.yByElement = y; return this;}
    public FPanelInfoBuilder add(FPanel p){lp.addTail(p); return this;}
    public FPanelInfoBuilder setAllowPanelsOnSameLine(boolean b){allowPanelsOnSameLine=b; return this;}

    // FUNCTIONS -----------------------------------------------------------------
    /**
    *{@summary Main function that build FPanelInfo &#38; return it.}<br>
    *@lastEditedVersion 2.7
    */
    public FPanelInfo build(){
      FPanelInfo pi = new FPanelInfo();
      if(x<1 || yByElement<1){throw new IllegalArgumentException("x or y is <1");}
      int lenLp = lp.length();
      if(lenLp==0){throw new IllegalArgumentException("lp is empty");}
      int y = yByElement*lenLp;
      pi.setSize(x,y);
      // pi.setLayout(new GridBagLayout());

      //TODO other methode if(allowPanelsOnSameLine==true)
      int k = 0;
      for (FPanel p : lp ) {
        p.setSize(x,yByElement);
        p.setLocation(0,yByElement*k);
        // if(withAlpha){p.setBackground(Main.getData().getButtonColor());}
        pi.add(p);
        k++;
      }
      return pi;
    }
  }
}
