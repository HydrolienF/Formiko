package fr.formiko.views.gui2d;

import fr.formiko.formiko.Pheromone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FPanelColorChooser extends FPanel implements ChangeListener {
  private static int lenJccb = 20;
  private JColorChooser jcc;
  private JComponent comp;
  private boolean deployMode;
  private FButton deployButton;
  private FButton randomColorButton;
  private FPanel pannelToPrintJcc;
  public FPanelColorChooser(int w, int h, JComponent comp, FPanel pannelToPrintJcc){
    super();
    this.comp=comp;
    this.pannelToPrintJcc=pannelToPrintJcc;
    if(lenJccb>0){
      //update UIManager for ColorChooser
      UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(lenJccb, lenJccb));
      UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(lenJccb, lenJccb));
      lenJccb=0;
    }
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

    jcc = new JColorChooser(comp.getForeground());
    jcc.getSelectionModel().addChangeListener(this);
    jcc.setVisible(false);
    jcc.setSize(1,1);
    jcc.setSize(getView().getPm().getWidth()*2/3,getView().getPm().getHeight()*2/3);
    jcc.setPreviewPanel(null);
    jcc.setPreviewPanel(new JPanel());
    // System.out.println(jcc);
    // jcc.pack();
    // System.out.println(jcc);
    // System.out.println();
    // System.out.println();
    // jcc.setLocation(0,h);
    pannelToPrintJcc.add(jcc);
  }

  public void deploy(boolean b){
    if(b){
      // JColorChooser.createDialog(getView().getF(), "ChooseColor", true, jcc, null, null);
    }
    // int w = deployButton.getWidth();
    // int h = deployButton.getHeight();
    // if(b){
    //   w+=jcc.getWidth();
    //   h+=jcc.getHeight();
    // }
    // setSize(w,h);
    if(jcc!=null){
      jcc.setVisible(b);
    }
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
