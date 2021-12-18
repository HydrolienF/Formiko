package fr.formiko.views.gui2d;

import fr.formiko.formiko.Pheromone;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FPanelColorChooser extends FPanel implements ChangeListener {
  private JComponent comp;
  private boolean deployMode;
  private FButton deployButton;
  private FButton randomColorButton;
  public FPanelColorChooser(int w, int h, JComponent comp){
    super();
    this.comp=comp;
    setSize(w*2, h);
    deployButton = new FButton("X", getView().getPm(), -2);
    deployButton.setSize(w,h);
    deployButton.addMouseListener(new MouseListenerEmpty(){
      @Override
      public void mouseReleased(MouseEvent event) {
        deploy(!deployMode);
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
  public JColorChooser getJcc(){return getView().getJcc();}

  public void deploy(boolean b){
    if(getJcc()!=null){
      getJcc().setVisible(b);
      if(b){
        getJcc().setSelectionModel(new DefaultColorSelectionModel(getColor()));
        getJcc().getSelectionModel().addChangeListener(this);
      }
    }
    deployMode=b;
  }
  public Color getColor(){return comp.getForeground();}
  public void setColor(Color col){
    comp.setForeground(col);
    getJcc().setColor(col);
  }
  public void setRandomColor(){
    setColor(new Pheromone().phToColor());
  }

  public void stateChanged(ChangeEvent e) {
    comp.setForeground(getJcc().getColor());
  }
}
