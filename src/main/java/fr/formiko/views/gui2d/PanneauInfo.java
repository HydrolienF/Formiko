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
*@version 2.7
*/
public class PanneauInfo extends Panneau {
  protected Liste<Panneau> lp;
  // CONSTRUCTORS --------------------------------------------------------------
  /** Main constructor. */
  protected PanneauInfo(){super();}

  /**
  *{@summary Return a new builder for this.}<br>
  *@version 2.7
  */
  public static PanneauInfoBuilder builder(){
    return new PanneauInfoBuilder();
  }
  // GET SET -------------------------------------------------------------------
  /** Return number of sub Panel. */
  public int length(){ return lp.length();}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Standard paintComponent.}<br>
  *@version 2.7
  */
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  /**
  *{@summary Strandard to string function that also print child.}<br>
  *@version 2.7
  */
  public String toString(){
    String r = super.toString()+"\n";//+" number of desc "+nbrDeDesc+" ";
    if(lp!=null){
      for (Panneau p : lp ) {
        r+=" "+p.toString();
      }
    }
    return r;
  }
  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Standard Builder for this.}<br>
  *@author Hydrolien
  *@version 2.7
  */
  static class PanneauInfoBuilder {
    protected Liste<Panneau> lp;
    protected int x=-1;
    protected int yByElement=-1;
    // CONSTRUCTORS --------------------------------------------------------------
    /**
    *{@summary Main constructor.}<br>
    $It only initialize List.
    *@version 2.7
    */
    protected PanneauInfoBuilder(){
      lp = new Liste<Panneau>();
    }

    // GET SET -------------------------------------------------------------------
    public PanneauInfoBuilder setX(int x){this.x = x; return this;}
    public PanneauInfoBuilder setYByElement(int y){this.yByElement = y; return this;}
    public PanneauInfoBuilder add(Panneau p){lp.addTail(p); return this;}

    // FUNCTIONS -----------------------------------------------------------------
    /**
    *{@summary Main function that build PanneauInfo &#38; return it.}<br>
    *@version 2.7
    */
    public PanneauInfo build(){
      PanneauInfo pi = new PanneauInfo();
      if(x<1 || yByElement<1){throw new IllegalArgumentException("x or y is <1");}
      int lenLp = lp.length();
      if(lenLp==0){throw new IllegalArgumentException("lp is empty");}
      int y = yByElement*lenLp;
      pi.setSize(x,y);
      // pi.setLayout(new GridBagLayout());

      // GridBagConstraints gbc = new GridBagConstraints();
      // gbc.gridx = 0;
      int k = 0;
      for (Panneau p : lp ) {
        p.setSize(x,yByElement);
        p.setLocation(0,yByElement*k);
        // if(withAlpha){p.setBackground(Main.getData().getButtonColor());}
        pi.add(p);
        // pi.add(p,gbc);
        // gbc.gridy++;
        // gbc.gridy = k;
        k++;
      }
      return pi;
    }
  }
}
