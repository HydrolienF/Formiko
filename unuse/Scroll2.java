import javax.swing.*;
import java.awt.*;

public class Scroll2 {
  public static void main(String... ar) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new A();
        }
    });
  }//Closing the main method
}//Closing the class Combo


class A { //implements ActionListener {
  Object [] index;
  JFrame jf;
  JPanel jp;
  JLabel label1, label2;

  A() {

    jf = new JFrame("JScrollBar");
    jp = new JPanel();

    label1 = new JLabel("Displaying a picture ",JLabel.CENTER);

    ImageIcon image = new ImageIcon("C:/Users/lili5/Downloads/Capture.PNG");
    JLabel label = new JLabel(image, JLabel.CENTER);
    jp = new JPanel(new BorderLayout());
    jp.add( label, BorderLayout.CENTER );

    JScrollBar scrollB1 = new JScrollBar(JScrollBar.HORIZONTAL, 10, 40, 0, 100);
    JScrollBar scrollB2 = new JScrollBar(JScrollBar.VERTICAL, 10, 60, 0, 100);

    jf.add(label1,BorderLayout.NORTH);
    jf.add(jp,BorderLayout.CENTER);
    jf.add(scrollB2,BorderLayout.EAST);
    jf.add(scrollB1,BorderLayout.SOUTH);

    jf.setSize(350,270);
    jf.setVisible(true);
  }

}
