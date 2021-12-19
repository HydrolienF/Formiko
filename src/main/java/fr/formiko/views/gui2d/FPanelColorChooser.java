package fr.formiko.views.gui2d;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import javax.swing.JTabbedPane;
import java.util.Random;

/**
*{@summary A color chooser Panel that can let user pick a specify color or a random one.}<br>
*@author Hydrolien
*@version 2.15
*/
public class FPanelColorChooser extends FPanel implements ChangeListener {
  private static Random rand;
  private static int idDeployed;
  private JComponent comp;
  private FButton deployButton;
  private FButton randomColorButton;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Standard constructor with width, heigth &#38; the component to color.}<br>
  *@param w width
  *@param h heigth
  *@param comp JComponent to paint background
  *@version 2.15
  */
  public FPanelColorChooser(int w, int h, JComponent comp){
    super();
    this.comp=comp;
    setSize(w*2, h);
    deployButton = new FButton("X", getView().getPm(), -2);
    deployButton.setSize(w,h);
    deployButton.addMouseListener(new MouseListenerEmpty(){
      @Override
      public void mouseReleased(MouseEvent event) {
        deploy();
      }
    });
    randomColorButton = new FButton("R", getView().getPm(), -2);
    randomColorButton.addMouseListener(new MouseListenerEmpty(){
      @Override
      public void mouseReleased(MouseEvent event) {
        setRandomColor();
      }
    });
    randomColorButton.setBounds(deployButton.getWidth(), 0, deployButton.getWidth(), deployButton.getHeight());
    add(deployButton);
    add(randomColorButton);
  }
  // GET SET -------------------------------------------------------------------
  public JColorChooser getJcc(){return getView().getJcc();}
  /**
  *{@summary Deploy the ColorChooser to pick a color.}<br>
  *@version 2.15
  */
  public void deploy(){
    boolean b = idDeployed!=id;
    if(getJcc()!=null){
      getJcc().setVisible(b);
      if(b){
        getJcc().setSelectionModel(new DefaultColorSelectionModel(getColor()));
        getJcc().getSelectionModel().addChangeListener(this);
        stateChanged(null);
        idDeployed=id;
      }else{
        idDeployed=-1;
      }
    }
  }
  public Color getColor(){return comp.getForeground();}
  /**
  *{@summary Set color &#38; update color dependent component.}<br>
  *@version 2.15
  */
  public void setColor(Color col){
    getJcc().setColor(col);
    stateChanged(null);
  }
  /**
  *{@summary Set color to a random color.}<br>
  *@version 2.15
  */
  public void setRandomColor(){
    if(rand==null){rand = new Random();}
    setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Update dependent component &#38; color chooser panel.}<br>
  *@version 2.15
  */
  public void stateChanged(ChangeEvent e) {
    JColorChooser jcc = getJcc();
    Color col = jcc.getColor();
    comp.setForeground(col);
    int lenJcc = jcc.getComponentCount();
    for (int i = 0; i < lenJcc; i++) {
      Component c = jcc.getComponent(i);
      if(c instanceof JTabbedPane){
        int len = ((JTabbedPane)(c)).getTabCount();
        for (int m=0; m<len; m++) {
          if(((JTabbedPane)(c)).getComponentAt(m) instanceof JComponent){
            JComponent c2 = ((JComponent)((JTabbedPane)(c)).getComponentAt(m));
            c2.setBackground(col);
          }
        }
      }
    }
  }
}
