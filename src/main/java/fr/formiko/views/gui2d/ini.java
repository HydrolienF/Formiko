package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.*;
import fr.formiko.usuel.types.str;

import java.awt.Image;
import java.io.File;

public class ini {

  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  //arboressence des Panneaux
  public static void initialiserToutLesPaneauxVide(){
    PanneauPrincipal pp = Main.getPp();
    pp.build();
    pp.addPm();
    pp.getPm().build();
    // pp.getPm().setBounds(0,0,Main.getDimX(),Main.getDimY());
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
    Main.getPb().build();//plein d'élément non visible sont initialiser ici.
    Main.getPc().build();
    keys.addBindings();
  }


  //initialisation des éléments graphiques
  public static void initialiserElémentTournés(){
    initialiserFourmiTournées();
    initialiserAutreELémentTournés();
  }

  public static void initialiserFourmiTournées(){
    int len = Main.getGj().length()+1;
    for (int i=1;i<len ;i++ ) {
      créerDBG("F",i);
    }
  }
  public static void créerDBG(String nom, int i){
    String is = ""; if(i!=-1){is=i+"";}
    Img imgTemp = new Img(nom+is+".png");
    //String rep = Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages();//""; if(str.nbrDeX(nom,'/')==0){rep="temporaire/";}
    //String s = rep+nom+is;
    imgTemp.sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom+is+"h"+".png");
    if (Main.getElementSurCarteOrientéAprèsDéplacement()){
      imgTemp.tourner();
      imgTemp.sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom+is+"d"+".png");
      imgTemp.tourner();
      imgTemp.sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom+is+"b"+".png");
      imgTemp.tourner();
      imgTemp.sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom+is+"g"+".png");
    }
  }
  public static synchronized void initialiserAutreELémentTournés(){
    //de façon a ce que l'initialisation ce fasse qu'une seule fois.
    if(Main.getAvancementChargement()==0){
      File f = new File(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages());
      if(!Main.getGarderLesGraphismesTourné() || f.list().length<10){
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
