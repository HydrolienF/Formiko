package fr.formiko.views.gui2d;

import fr.formiko.usuel.maths.math;

import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;

public class FIntField extends JFormattedTextField {
  private FIntField(NumberFormatter formatter){
    super(formatter);
  }

  public static FIntField newFIntField(int min, int max, int value){
    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format){
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
}
