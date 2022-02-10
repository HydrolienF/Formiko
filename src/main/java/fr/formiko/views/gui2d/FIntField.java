package fr.formiko.views.gui2d;

import fr.formiko.usuel.maths.math;

import java.awt.Component;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

/**
*{@summary Standard int field for Formiko.}
*@author Hydrolien
*@lastEditedVersion 2.17
*/
public class FIntField extends JFormattedTextField {
  /**
  *{@summary Main constructor.}
  *@lastEditedVersion 2.17
  */
  private FIntField(NumberFormatter formatter){
    super(formatter);
  }
  /**
  *{@summary Create an editable int field.}<br>
  *Value will always be in [min, max].
  * Unautorised char can't be add to the field.
  *@param min the min value (should be > -1)
  *@param max the max value (should be > -1)
  *@param value default value
  *@lastEditedVersion 2.17
  */
  public static FIntField newFIntField(int min, int max, int value){
    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format){
      /**
      *{@summary Allow to have 0 char on field.}<br>
      *@lastEditedVersion 2.17
      */
      @Override
      public Object stringToValue(String text) throws ParseException {
        if(text.equals("")){return null;}
        else{return super.stringToValue(text);}
      }
    };
    formatter.setValueClass(Integer.class);
    formatter.setMinimum(math.max(0, min));//can't set negative value
    formatter.setMaximum(max);
    formatter.setAllowsInvalid(false);
    formatter.setCommitsOnValidEdit(true);
    FIntField field = new FIntField(formatter);
    field.setValue(value);
    return field;
  }
  /**
  *{@summary Add a listener to update this &#38; parent size so that this can still fit in.}<br>
  *@lastEditedVersion 2.19
  */
  public void addSizeUpdater(){
    addPropertyChangeListener(new PropertyChangeListener() {
      /**
      *{@summary Update size.}<br>
      *@lastEditedVersion 2.19
      */
      @Override
      public void propertyChange(PropertyChangeEvent event) {
        updateParentSize();
      }
    });
  }
  /**
  *{@summary Update parent size so that this can still fit in.}<br>
  *@lastEditedVersion 2.19
  */
  private void updateParentSize(){
    Component parent = getParent();
    while(parent.getParent()!=null && !(parent instanceof Window)){
      parent=parent.getParent();
    }
    if(parent instanceof Window && !(parent.equals(FPanel.getView().getF()))){
      ((Window)parent).pack();
    }
  }
}
