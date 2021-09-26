import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ProgressExemple extends JFrame
 {

  JProgressBar barre_progression;
  static final int MINIMUM=0;
  static final int MAXIMUM=100;

  public ProgressExemple( )
 {
 	 // Créer un objet de la Barre de progression
     barre_progression = new JProgressBar( );

     // Définir la valeur initiale de la barre de progression
     barre_progression.setMinimum(MINIMUM);
     // Définir la valeur maximale de la barre de progression
     barre_progression.setMaximum(MAXIMUM);

     // Créer un JPanel et ajouter la barre de progression dans le JPanel
     JPanel pnl=new JPanel();
     pnl.add(barre_progression);

     setTitle("Barre de progression en Java");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setContentPane(pnl);
     pack( );
     setVisible(true);

     for (int i = MINIMUM; i <= MAXIMUM; i++)
    {
       final int percent=i;
      try
      {
         SwingUtilities.invokeLater(new Runnable( ) {
             public void run( ) {
               updateBar(percent);
             }
         });
         java.lang.Thread.sleep(100);
       } catch (InterruptedException e) {;}
     }

  }

  public void updateBar(int newValue)
  {
    barre_progression.setValue(newValue);
  }

  public static void main(String args[])
  	{
     new ProgressExemple();

   }
}
