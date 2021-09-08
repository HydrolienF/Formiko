import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;

class Part {
   double value;
   Color color;

   public Part(double value, Color color) {
      this.value = value;
      this.color = color;
   }
}
class MyComponent extends JComponent {
   Part[] slices = {
      new Part(15, Color.yellow), new Part(30, Color.white), new Part(25, Color.blue), new Part(30, Color.red)
   };
   MyComponent() {
   }
   public void paint(Graphics g) {
      drawPie((Graphics2D) g, getBounds(), slices);
   }
   void drawPie(Graphics2D g, Rectangle area, Part[] slices) {
      double total = 0.0D;
      for (int i = 0; i < slices.length; i++) {
         total += slices[i].value;
      }
      double curValue = 0.0D;
      int startAngle = 0;
      for (int i = 0; i < slices.length; i++) {
         startAngle = (int) (curValue * 360 / total);
         int arcAngle = (int) (slices[i].value * 360 / total);

         g.setColor(slices[i].color);
         g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
         curValue += slices[i].value;
      }
   }
}
public class Panel {
   public static void main(String[] argv) {
      JFrame frame = new JFrame();
      frame.getContentPane().add(new MyComponent());
      frame.setSize(300, 300);
      frame.setVisible(true);
   }
}
