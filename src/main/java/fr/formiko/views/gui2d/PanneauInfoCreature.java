package fr.formiko.views.gui2d;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
*{@summary Pannel that containt infos about a Creature as a List of Pannel.}<br>
*@author Hydrolien
*@version 2.7
*/
public class PanneauInfoCreature extends PanneauInfo {
  // FUNCTIONS -----------------------------------------------------------------
  // @Override
  public static PanneauInfoCreatureBuilder builder(){return new PanneauInfoCreatureBuilder();}
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    System.out.println("PanneauInfoCreature infos :");//@a
    erreur.info(toString());//@a
  }
  /**
  *{@summary Standard Builder for this.}<br>
  *@author Hydrolien
  *@version 2.7
  */
  // SUB-CLASS -----------------------------------------------------------------
  static class PanneauInfoCreatureBuilder extends PanneauInfoBuilder {
    private Creature c;
    // CONSTRUCTORS --------------------------------------------------------------

    // GET SET -------------------------------------------------------------------

    // FUNCTIONS -----------------------------------------------------------------
    //public
    /**
    *{@summary Standard Builder for this.}<br>
    *@version 2.7
    */
    public PanneauInfoBuilder addCreature(Creature c){
      this.c=c;
      return this;
    }
    /**
    *{@summary Build including the tranfomation of Creature to Pannel.}<br>
    *Creature are represent as icon & FProgressBar.
    *@version 2.7
    */
    @Override
    public PanneauInfo build(){
      if(c!=null){
        addCreatureInfo();
      }else{
        erreur.alerte("PanneauInfoCreature don't have a Creature and will be build as a PanneauInfo");
      }
      return super.build();
    }
    //private
    /**
    *{@summary Add the icons then the progress bar with there icon.}<br>
    *@version 2.7
    */
    private void addCreatureInfo(){
      addCreatureIcons();
      addCreatureProgressBars();
    }
    /**
    *{@summary Add the icons.}<br>
    *@version 2.7
    */
    private void addCreatureIcons(){
      //TODO
    }
    /**
    *{@summary Add the progress bars.}<br>
    *@version 2.7
    */
    private void addCreatureProgressBars(){
      addCreatureProgressBar(1, c.getAge(), c.getAgeMax());
      addCreatureProgressBar(1, c.getNourriture(), c.getNourritureMax());
      addCreatureProgressBar(1, c.getAction(), c.getActionMax());
      addCreatureProgressBar(1, c.getProprete(), c.getProprete());
    }
    /**
    *{@summary Add a progress bar.}<br>
    *@param state state that define color of the bar
    *@param value curent value of the bar
    *@param maxValue max value of the bar
    *@version 2.7
    */
    private void addCreatureProgressBar(int state, int value, int maxValue){
      if (value<0) {
        value=0;
        erreur.alerte("Value is <0");
      } else if(value>maxValue) {
        value=maxValue;
        erreur.alerte("Value is >maxValue");
      }
      FProgressBar pb = new FProgressBar();
      pb.setState(state);
      pb.setMaximum(maxValue);
      pb.setValue(value);
      pb.setSize(x, yByElement);
      PanneauProgressBar p = new PanneauProgressBar(pb);
      p.setSize(x, yByElement);
      add(p);
    }
  }
}
/**
*{@summary Pannel that containt a FProgressBar &#39; an icon.}<br>
*@author Hydrolien
*@version 2.7
*/
class PanneauProgressBar extends Panneau {
  private FProgressBar pb;
  public PanneauProgressBar(FProgressBar pb){
    super();
    this.pb=pb;
    add(pb);
  }
  @Override
  public void setSize(int x, int y){
    super.setSize(x,y);
    pb.setSize((int)(x*0.9),y);
    pb.setLocation((int)(x*0.1),0);
    //set size for the Icon
    erreur.info("size set to "+getSize(),5);//@a
  }
  // @Override
  // public void revalidate(){
  //   System.out.println(getSize());
  //   System.out.println("revalidate");//@a
  //   super.revalidate();
  //   System.out.println(getSize());
  // }
}
