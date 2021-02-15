package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import fr.formiko.formiko.Touches;

public class Fenetre extends JFrame {
  private PanneauPrincipal pp;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public Fenetre(String titre, int xMax, int yMax){
    if(Main.getPleinEcran()){
      //@OS
      if(Main.getOs().getId()==1){
        setExtendedState(JFrame.MAXIMIZED_BOTH); //n'as pas l'effet de plein écran sur linux.
        setUndecorated(true);
      }else{
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
      }
    }
    setTitle(titre);
    setSize(xMax,yMax);// (x,y) en pixel
    setLocationRelativeTo(null); // fenetre centrée
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // bouton fermer par défaut.
    //notre bouton fermé si dessous.
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    Runnable r = new Runnable() {
      @Override // indique au compilateur qu'on écrit sur la méthode run.
      public void run() {
        setBoutonFermer(); //Méthode qui demande validation si le joueur clic sur le bouton fermé la fenetre.
      }
    };
    SwingUtilities.invokeLater(r);
    pp = new PanneauPrincipal();
    this.setContentPane(pp);
    addKeyListener(new Touches());
    this.setResizable(true); //On permet ou pas le redimensionnement
    this.setVisible(true); // visible
    debug.g("Fenetre",xMax,yMax);
  }
  public Fenetre(){
    this("Formiko",getDimEcranX(),getDimEcranY());
  }
  // GET SET --------------------------------------------------------------------
  public int getXMax(){return getWidth();}
  public int getYMax(){return getHeight();}
  public PanneauPrincipal getPp(){ return pp;}
  public static int getDimEcranX(){ return Toolkit.getDefaultToolkit().getScreenSize().width;}
  public static int getDimEcranY(){ return Toolkit.getDefaultToolkit().getScreenSize().height;}

  // Fonctions propre -----------------------------------------------------------
  public void setBoutonFermer(){
    this.addWindowListener(new WindowAdapter() {
        @Override // indique au compilateur qu'on écrit sur la méthode windowClosing déjà défini et il est sencé vérifier qu'on a pas fait de bêtise d'écriture.
        public void windowClosing(WindowEvent e) {
          //si la fermeture de la fenetre ne doit pas etre immédiate.
          quit();
        }
    });
  }
  public void quit(){
    try {
      boolean choix=true;
      if (!Main.getForcerQuitter()){
        Question q = new Question("validerQuitter","quitterJeu");
        choix = q.getChoix();
      }
      //si validation.
      if(choix){
        // On dit a la fenetre de ne pas ce fermer lors du clic
        Main.getF().setVisible(false);
        Main.getF().dispose();
        // On provoque une fermeture normale du jeu.
        Main.quitter();
      }//sinon rien.
    }catch (Exception e2) {
      erreur.erreur("La fermeture normale n'as pas fonctionné","Fenetre.setBoutonFermer");
      Main.quitter();
    }
  }

}
