package fr.formiko.views.gui2d;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
  /**
  *{@summary Standard paintComponent.}<br>
  *@version 2.7
  */
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
    *Icon are : the creature itself,
    *the icon draw on the map if creature is not the playing ant,
    *seed if there is any.<br>
    *Specie name is also print.<br>
    *@version 2.7
    */
    private void addCreatureIcons(){
      PanneauCreatureIcons pci = new PanneauCreatureIcons(yByElement);
      pci.addIcon(Main.getData().getCreatureImage(c));
      pci.setText(c.getSex()+" "+c.getEspece().getNom());
      if(c!=null && !c.equals(Main.getPlayingAnt())){
        pci.addIcon(PanneauCarte.getIconImage(c,Main.getPlayingAnt()));
      }
      //TODO add carriing seed if there is one.
      add(pci);
    }
    /**
    *{@summary Add the progress bars.}<br>
    *@version 2.7
    */
    private void addCreatureProgressBars(){
      if(c.getAgeMax()<Main.getPartie().getNbrDeTour()){
        addCreatureProgressBar(c.getStateAge(), c.getAge(), c.getAgeMax(),"age");
      }
      addCreatureProgressBar(c.getStateFood(), c.getNourriture(), c.getNourritureMax(),"food");
      addCreatureProgressBar(c.getStateAction(), math.max(c.getAction(),0), c.getActionMax(),"action");
      int col=0;
      if(c instanceof Fourmi){
        Fourmi f = (Fourmi)c;
        col = f.getStateHealth();
      }
      addCreatureProgressBar(col, math.min(c.getProprete(),c.getPropreteMax()), c.getPropreteMax(),"health");
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
      String message = g.getM("iconName."+iconName)+" : "+value+" / "+maxValue +" ("+g.getM("colorState."+state)+")";
      p.setMessageDesc(message);
      add(p);
    }
  }
}
// SUB-CLASS -----------------------------------------------------------------
/**
*{@summary Pannel that containt a FProgressBar &#38; an icon.}<br>
*@author Hydrolien
*@version 2.7
*/
class PanneauProgressBar extends Panneau {
  private FProgressBar pb;
  private BufferedImage iconImage;
  private String messageDesc;
  private PanneauProgressBarMouseListener PPBML = new PanneauProgressBarMouseListener();
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
    addMouseListener(PPBML);
    pb.addMouseListener(PPBML);
    addMouseMotionListener(PPBML);
    pb.addMouseMotionListener(PPBML);
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
  *{@summary Paint component: FProgressBar &#38; icon image.}<br>
  *@version 2.7
  */
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    if(iconImage!=null){
      g.drawImage(iconImage,0,(getHeight()-iconImage.getHeight()+1)/2,this);
    }
  }
  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Listener that update description when it need.}<br>
  *@version 2.7
  */
  public class PanneauProgressBarMouseListener implements MouseListener, MouseMotionListener {
    /**
    *{@summary Update message description.}<br>
    *@version 2.7
    */
    @Override
    public void mouseEntered(MouseEvent event) {
      getView().setMessageDesc(messageDesc, true);
    }
    /**
    *{@summary Remove message description.}<br>
    *@version 2.7
    */
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
    // addMouseMotionListener(new MouseMotionAdapter() {
    /**
    *{@summary Update mouse location to hide the message.}<br>
    *@version 2.7
    */
    @Override
    public void mouseMoved(MouseEvent event){
      getView().getPj().updateTimeFromLastMove();
    }
    @Override
    public void mouseDragged​(MouseEvent e){}
  }
}
/**
*{@summary Pannel that containt the creature icons.}<br>
*@author Hydrolien
*@version 2.7
*/
class PanneauCreatureIcons extends Panneau {
  private Liste<BufferedImage> iconsList;
  private FLabel label;
  private int xy;
  /**
  *{@summary Main constructor.}<br>
  *@version 2.7
  */
  public PanneauCreatureIcons(int xy){
    iconsList = new Liste<BufferedImage>();
    this.xy=xy;
  }
  /**
  *{@summary Add an icon at the liste of icon to print.}<br>
  *@version 2.7
  */
  public void addIcon(BufferedImage bi){
    iconsList.add(image.resize(bi,xy));
  }
  /**
  *{@summary Aet the text of the FLabel.}<br>
  *@version 2.7
  */
  public void setText(String s){
    if(label==null){
      label =new FLabel();
      label.setFondTransparent();
      add(label);
    }
    label.setText(s);
    label.updateSize();
  }
  /**
  *{@summary Paint every icon & the FLabel.}<br>
  *@version 2.7
  */
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    int k=1;
    int labelSize=0;
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(new Color(255,255,255,120));
    g2d.fillRect(xy,0,getWidth(),xy); //xy*iconsList.length()+label.getWidth()+Main.getTailleElementGraphiqueX(5)
    for (BufferedImage bi : iconsList) {
      g.drawImage(bi,k*xy+labelSize,0,this);
      k++;
      if(k==2){
        label.setLocation(k*xy,0);
        labelSize=label.getWidth();
      }
    }
  }
  //TODO add desc for icons
}
