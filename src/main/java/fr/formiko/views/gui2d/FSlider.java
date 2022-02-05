package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;

import javax.swing.JSlider;

/**
*{@summary Personalised JSlider.}<br>
*@author Hydrolien
*@lastEditedVersion 2.17
*/
public class FSlider extends JSlider{
  /**
  *{@summary Main constructor.}<br>
  *@param min the min value
  *@param max the max value
  *@param value the curent value
  *@lastEditedVersion 2.17
  */
  public FSlider(int min, int max, int value){
    super(min, max, value);
    setBackground(Main.getData().getButtonColorWithoutAlpha());
    //TODO #515 gui change
  }
}
