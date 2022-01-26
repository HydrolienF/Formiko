import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;

public class IntField {
  public static void main(String[] args) {
    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format){
      @Override
      public Object stringToValue(String text) throws ParseException {
        if(text.equals("")){return null;}
        else{return super.stringToValue(text);}
      }
    };
    formatter.setValueClass(Integer.class);
    formatter.setMinimum(0);//can't set negative value
    // formatter.setMaximum(Integer.MAX_VALUE);
    formatter.setMaximum(30);
    formatter.setAllowsInvalid(false);
    // If you want the value to be committed on each keystroke instead of focus lost
    formatter.setCommitsOnValidEdit(true);
    JFormattedTextField field = new JFormattedTextField(formatter);

    JOptionPane.showMessageDialog(null, field);

    // getValue() always returns something valid
    System.out.println(field.getValue());
  }
}
