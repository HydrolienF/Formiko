package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import fr.formiko.formiko.Main;
import fr.formiko.usuel.types.str;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.images.*;
import java.awt.Image;
import fr.formiko.formiko.Touches;

public class ini {

  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  //arboressence des Panneaux
  public static void initialiserToutLesPaneauxVide(){
    PanneauPrincipal pp = Main.getPp();
    pp.construire();
    pp.addPm();
    pp.getPm().construire();
    pp.getPm().setBounds(0,0,Main.getDimX(),Main.getDimY());
    //le Menu est fonctionnel ici.
  }
  public static void initialiserPanneauJeuEtDépendance(){
    Main.getPp().addPj();
    Main.getPj().setBounds(0,0,Main.getDimX(),Main.getDimY());
    Main.getPj().addPs();
    //le panneau pp a ses 2 sous panneaux
    Main.getPj().addPe();//ajoute le panneau complètement vide juste pour qu'il soit au 1a plan
    Main.getPj().addPd();
    Main.getPj().addPfp();//ajoute le panneau complètement vide
    Main.getPj().addPb();
    Main.getPj().addPc();
    //pj a ses 4 sous panneau
    Main.getPb().construire();//plein d'élément non visible sont initialiser ici.
    Main.getPc().construire();
    initialiserTouches();
  }

  public static void initialiserTouches(){
    Touches tu = new Touches();
    Main.getPp().addKeyListener(tu);
    Main.getPm().addKeyListener(tu);
    Main.getPj().addKeyListener(tu);
    Main.getPb().addKeyListener(tu);
    Main.getPc().addKeyListener(tu);
    Main.getPi().addKeyListener(tu);
    Main.getPac().addKeyListener(tu);
  }

  //initialisation des éléments graphiques
  public static void initialiserElémentTournés(){
    initialiserFourmiTournées();
    initialiserAutreELémentTournés();
  }

  /*public static void initialiserFourmiTournées(){
    //les fourmis.
    int len = Main.getGj().length()+1;//tF.length;
    for (int i=1;i<len ;i++ ) {
      créerDBG("F",i);
    }
  }*/
  public static void initialiserFourmiTournées(){
    for (int i=1;i<Main.getGj().length()+1 ;i++ ) {
      créerDBG("F",i);
    }
  }
  public static void créerDBG(String nom, int i){
    String is = ""; if(i!=-1){is=i+"";}
    Img imgTemp = new Img(nom+is+".png");
    //String rep = image.REP2;//""; if(str.nbrDeX(nom,'/')==0){rep="temporaire/";}
    //String s = rep+nom+is;
    imgTemp.sauvegarder(image.REP2,nom+is+"h"+".png");
    if (Main.getElementSurCarteOrientéAprèsDéplacement()){
      imgTemp.tourner();
      imgTemp.sauvegarder(image.REP2,nom+is+"d"+".png");
      imgTemp.tourner();
      imgTemp.sauvegarder(image.REP2,nom+is+"b"+".png");
      imgTemp.tourner();
      imgTemp.sauvegarder(image.REP2,nom+is+"g"+".png");
    }
  }
  public static synchronized void initialiserAutreELémentTournés(){
    //de façon a ce que l'initialisation ce fasse qu'une seule fois.
    if(Main.getAvancementChargement()==0){
      if(!Main.getGarderLesGraphismesTourné()){
        //les jeunes fourmis
        for (int i=0;i<3 ;i++ ) {
          créerDBG("fourmi",i);
        }
        // les graines
        int len2 = image.getNbrImages("graine");
        for (int i=0;i<len2 ;i++ ) {
          créerDBG("graine",i);
        }
        //les insectes
        int len3 = image.getNbrImages("I");
        for (int i=0;i<len3 ;i++ ) {
          créerDBG("I",i);
          //créerDBG(ts[i],-1); // -1 = sans numérotation.
        }
        //les flèches
        Main.getData().tournerLesFleches();
      }
      Main.setAvancementChargement(1);
    }
  }
}
