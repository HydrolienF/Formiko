package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.views.gui2d.Panneau;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.*;
import fr.formiko.usuel.types.str;

import java.awt.Image;
import java.io.File;

public class ini {

  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  //arboressence des Panneaux
  public static void initialiserToutLesPaneauxVide(){
    PanneauPrincipal pp = Panneau.getView().getPp();
    pp.build();
    pp.addPm();
    pp.getPm().build();
    //le Menu est fonctionnel ici.
  }
  public static void initialiserPanneauJeuEtDépendance(){
    if(Panneau.getView().getPj()!=null){
      erreur.alerte("This function should be call only 1 time by fenetre.");
    }
    Panneau.getView().getPp().addPj();
    Panneau.getView().getPj().setBounds(0,0,Main.getDimX(),Main.getDimY());
    Panneau.getView().getPj().addPs();
    //le panneau pp a ses 2 sous panneaux
    Panneau.getView().getPj().addPe();//ajoute le panneau complètement vide juste pour qu'il soit au 1a plan
    Panneau.getView().getPj().addPch();
    Panneau.getView().getPj().addPfp();//add empty panel.
    Panneau.getView().getPj().addPd();
    Panneau.getView().getPj().addPb();
    Panneau.getView().getPj().addPc();
    //pj a ses 4 sous panneau
    Panneau.getView().getPb().build();//plein d'élément non visible sont initialiser ici.
    Panneau.getView().getPc().build();
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
      créerDBG("F0&",i);
    }
  }
  public static void créerDBG(String nom, int i){
    String is = "";
    // if(i!=-1){
      is=i+"";
    // }
    Img imgTemp = new Img(nom+is);
    debug.débogage("load "+nom+is);
    //String rep = Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages();//""; if(str.nbrDeX(nom,'/')==0){rep="temporaire/";}
    //String s = rep+nom+is;
    imgTemp.sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom+is+"h"+".png");
    // if (Main.getElementSurCarteOrientéAprèsDéplacement()){
    //   imgTemp.tourner();
    //   imgTemp.sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom+is+"d"+".png");
    //   imgTemp.tourner();
    //   imgTemp.sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom+is+"b"+".png");
    //   imgTemp.tourner();
    //   imgTemp.sauvegarder(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages(),nom+is+"g"+".png");
    // }
  }
  public static synchronized void initialiserAutreELémentTournés(){
    //de façon a ce que l'initialisation ce fasse qu'une seule fois.
    if(Main.getAvancementChargement()==0){
      File f = new File(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderImages());
      if(!Main.getGarderLesGraphismesTourné() || f.list().length<10){
        //les jeunes fourmis
        for (int i=-3;i<0 ;i++ ) {
          créerDBG("F",i);
        }
        // les graines
        int len2 = image.getNbrImages("seed");
        for (int i=0;i<len2 ;i++ ) {
          créerDBG("seed",i);
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
