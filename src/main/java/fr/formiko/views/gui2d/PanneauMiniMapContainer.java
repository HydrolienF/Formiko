package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanneauMiniMapContainer extends Panneau {
  private static int BUTTON_RADIUS=18;
  private FButtonEndTurn endTurn;
  private PanneauMiniMap pmm;
  public PanneauMiniMapContainer() {
    super();
    endTurn = new FButtonEndTurn();
    add(endTurn);
    pmm = new PanneauMiniMap();
    pmm.setLocation(Main.getTailleElementGraphiqueX(BUTTON_RADIUS),Main.getTailleElementGraphiqueY(BUTTON_RADIUS));
    add(pmm);
    setSize(pmm.getWidth()+Main.getTailleElementGraphiqueX(BUTTON_RADIUS), pmm.getHeight()+Main.getTailleElementGraphiqueY(BUTTON_RADIUS));
    setLocation(Panneau.getView().getWidth()-getWidth(), Panneau.getView().getHeight()-getHeight());
  }

  class FButtonEndTurn extends FButton {
    private int lineSize;
    public FButtonEndTurn() {
      super("", Panneau.getView().getPj(), 200);
      setSize(Main.getTailleElementGraphique(BUTTON_RADIUS*2));
      setLocation(0,0);
      lineSize = Main.getTailleElementGraphique(3);
      setBorderPainted(false);
    }
    public void paintComponent(Graphics gTemp){
      Graphics2D g = (Graphics2D)gTemp;
      BasicStroke line = new BasicStroke(lineSize);
      g.setStroke(line);
      g.setColor(Color.RED);
      g.fillOval(lineSize/2,lineSize/2,getWidth()-lineSize,getHeight()-lineSize);
      g.setColor(Color.BLACK);
      g.drawOval(lineSize/2,lineSize/2,getWidth()-lineSize,getHeight()-lineSize);
    }
  }
}
