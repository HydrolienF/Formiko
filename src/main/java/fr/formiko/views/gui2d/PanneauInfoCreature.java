package fr.formiko.views.gui2d;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

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
      int col=1;
      if(c.getAgeMax()<Main.getPartie().getNbrDeTour()){
        if(c.getAge()>=c.getAgeMax()*0.9){col=2;}
        addCreatureProgressBar(col, c.getAge(), c.getAgeMax(),"age");
      }
      if(c.getNourriture()<0.1*c.getNourritureMax()){col=3;}
      else if(c.getNourriture()<0.2*c.getNourritureMax()){col=2;}
      else if(c.getNourriture()<0.4*c.getNourritureMax()){col=1;}
      else {col=0;}
      addCreatureProgressBar(col, c.getNourriture(), c.getNourritureMax(),"food");
      if(c.getAction()==c.getActionMax()){col=0;}
      else if(c.getAction()<=0){col=3;}
      else{col=1;}
      addCreatureProgressBar(col, c.getAction(), c.getActionMax(),"move");
      col=0;
      if(c instanceof Fourmi){
        Fourmi f = (Fourmi)c;
        if(f.wantClean()){
          if(f.getProprete() < f.getSeuilDeRisqueDInfection()){
            col=3;
          }else{
            col=1;
          }
        }
      }
      addCreatureProgressBar(col, math.min(c.getProprete(),c.getPropreteMax()), c.getPropreteMax(),"heal");
    }
    /**
    *{@summary Add a progress bar.}<br>
    *@param state state that define color of the bar
    *@param value curent value of the bar
    *@param maxValue max value of the bar
    *@version 2.7
    */
    private void addCreatureProgressBar(int state, int value, int maxValue, String iconName){
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
      pb.setString(value+"/"+maxValue);
      pb.setStringPainted(true);
      pb.setSize(x, yByElement);
      PanneauProgressBar p = new PanneauProgressBar(pb, Main.getData().getIconImage(iconName));
      p.setSize(x, yByElement);
      String message = g.getM(iconName)+" : "+value+" / "+maxValue +" ("+g.getM("colorState."+state)+")";
      p.setMessageDesc(message);
      add(p);
    }
  }
}
// SUB-CLASS -----------------------------------------------------------------
/**
*{@summary Pannel that containt a FProgressBar &#39; an icon.}<br>
*@author Hydrolien
*@version 2.7
*/
class PanneauProgressBar extends Panneau {
  private FProgressBar pb;
  private BufferedImage iconImage;
  private String messageDesc;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@version 2.7
  */
  public PanneauProgressBar(FProgressBar pb, BufferedImage bi){
    super();
    this.pb=pb;
    iconImage=bi;
    if(this.pb!=null){
      add(this.pb);
    }
    addMouseListener(new PanneauProgressBarMouseListener());
  }

  // GET SET -------------------------------------------------------------------
  public void setMessageDesc(String s){messageDesc=s;}
  /**
  *{@summary Override of set size to also set size of the FProgressBar.}<br>
  *@version 2.7
  */
  @Override
  public void setSize(int x, int y){
    super.setSize(x,y);
    pb.setSize((int)(x*0.9), (int)(y*0.8));
    pb.setLocation((int)(x*0.1), (int)(y*0.1));
  }

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Paint component: FProgressBar &#39; icon image.}<br>
  *@version 2.7
  */
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    if(iconImage!=null){
      g.drawImage(iconImage,0,(getHeight()-iconImage.getHeight()+1)/2,this);
    }
  }
  // SUB-CLASS -----------------------------------------------------------------
  public class PanneauProgressBarMouseListener implements MouseListener{
    @Override
    public void mouseEntered(MouseEvent event) {
      getView().setMessageDesc(messageDesc, true);
    }
    @Override
    public void mouseExited(MouseEvent event) {
      getView().setMessageDesc("", true);
    }
    @Override
    public void mouseClicked(MouseEvent event) {}
    @Override
    public void mousePressed(MouseEvent event) {}
    @Override
    public void mouseReleased(MouseEvent event) {}

  }
}
