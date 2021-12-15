package fr.formiko.views.gui2d;

import fr.formiko.formiko.Pheromone;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FPanelColorChooser extends FPanel implements ChangeListener {
  private JColorChooser jcc;
  private JComponent comp;
  private boolean deployMode;
  private FButton deployButton;
  private FButton randomColorButton;
  public FPanelColorChooser(int w, int h, JComponent comp){
    super();
    this.comp=comp;
    setSize(w*2, h);
    jcc = new JColorChooser(comp.getForeground());
    jcc.getSelectionModel().addChangeListener(this);
    jcc.setVisible(false);
    deployButton = new FButton("X", getView().getPm(), -2);
    deployButton.setSize(w,h);
    deployButton.addMouseListener(new MouseListenerEmpty(){
      @Override
      public void mouseReleased(MouseEvent event) {
        deploy(!deployMode);
      }
    });
    jcc.setSize(getView().getPm().getWidth()/2,getView().getPm().getHeight()/2);
    jcc.setLocation(0,h);
    randomColorButton = new FButton("R", getView().getPm(), -2);
    randomColorButton.addMouseListener(new MouseListenerEmpty(){
      @Override
      public void mouseReleased(MouseEvent event) {
        setRandomColor();
      }
    });
    randomColorButton.setBounds(deployButton.getWidth(), 0, deployButton.getWidth(), deployButton.getHeight());
    add(jcc);
    add(deployButton);
    add(randomColorButton);
  }

  public void deploy(boolean b){
    int w = deployButton.getWidth();
    int h = deployButton.getHeight();
    if(b){
      w+=jcc.getWidth();
      h+=jcc.getHeight();
    }
    setSize(w,h);
    jcc.setVisible(b);
    deployMode=b;
  }
  public Color getColor(){return jcc.getColor();}
  public void setColor(Color col){jcc.setColor(col);}
  public void setRandomColor(){
    setColor(new Pheromone().phToColor());
  }

  public void stateChanged(ChangeEvent e) {
    comp.setForeground(jcc.getColor());
  }
}
