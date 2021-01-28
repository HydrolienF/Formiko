package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.awt.BorderLayout;

public class ListeFontes extends JFrame {
  public ListeFontes() {
    GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] liste = gE.getAvailableFontFamilyNames();
    JLabel label;
    JPanel panneau = new JPanel();

    panneau.setLayout(new GridLayout(20, 0, 10, 0));
    for (String nom : liste) {
    	label = new JLabel();
    	label.setFont(new Font(nom, Font.PLAIN, 18));
    	label.setText(nom);
    	panneau.add(label);
    }
    add(new JScrollPane(panneau), BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 500);
    setLocation(100, 100);
    setVisible(true);
  }
}

class EssaiListeFontes {
  public static void main(String[] arg) {
      new ListeFontes();
    }
}
