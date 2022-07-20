package fr.formiko.views.gui2d;

import java.awt.Color;
import javax.swing.JCheckBox;

/**
*{@summary A simple checkBox using Formiko UI style.}<br>
*@author Hydrolien
*@lastEditedVersion 2.26
*/
public class FCheckBox extends JCheckBox {
  /**
  *{@summary Main constructor using Formiko UI style.}<br>
  *@lastEditedVersion 2.26
  */
  public FCheckBox(){
    super();
    setForeground(Color.BLACK);
    setBackground(new Color(0,0,0,0));
    // toggleButton.setPreferredSize(new Dimension(128, 128));
  }
}
