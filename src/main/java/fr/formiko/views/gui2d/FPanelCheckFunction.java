package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.CheckFunction;

import java.awt.FlowLayout;

/**
*{@summary A checkBox panel with a message.}<br>
*@author Hydrolien
*@lastEditedVersion 2.28
*/
public class FPanelCheckFunction extends FPanel {
  private CheckFunction cf;
  private FCheckBox cb;
  /**
  *{@summary Main constructor with check box &#38; text field.}<br>
  *@param cf CheckFunction to use to get text, default checked &#38; launch function
  *@lastEditedVersion 2.28
  */
  public FPanelCheckFunction(CheckFunction cf){
    super();
    this.cf=cf;
    setLayout(new FlowLayout());
    cb = new FCheckBox();
    cb.setText(cf.getText());
    cb.setSelected(cf.isChecked());
    // cb.setBackground(Main.getData().getButtonColorWithoutAlpha());
    add(cb);
  }
  /**
  *{@summary Update checked boolean &#38; run function.}<br>
  *@lastEditedVersion 2.28
  */
  public void run(){
    cf.setChecked(cb.isSelected());
    cf.run();
  }
}
