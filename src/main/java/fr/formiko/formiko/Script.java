package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.types.str;
import fr.formiko.usuel.lireUnFichier;

import java.io.Serializable;

/**
*{@summary Script class used to do levels.}<br>
*All script use cheat code of triche.java.
*@lastEditedVersion 1.38
*@author Hydrolien
*/
public class Script implements Serializable{
  private boolean cmdSuivante;
  private boolean ecouteClic;
  private String name;
  // CONSTRUCTORS --------------------------------------------------------------
  public Script(String name){
    this.name=name;
  }
  // GET SET -------------------------------------------------------------------
  public boolean getCmdSuivante(){return cmdSuivante;}
  public void setCmdSuivante(boolean b){cmdSuivante=b;}
  public boolean getEcouteClic(){return ecouteClic;}
  public void setEcouteClic(boolean b){ecouteClic=b;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Load the script then launch it.}
  *It will test 3 folders : stable then ressourcesPacks then temporary.
  *All default script will be in stable.
  *A player can add levels in ressourcesPacks.
  *A player can create new levels in temporary.
  *@lastEditedVersion 1.38
  */
  public boolean script(){
    String t [] = loadScript(Main.getFolder().getFolderStable());
    if(t==null || t.length==0){
      t = loadScript(Main.getFolder().getFolderResourcesPacks());
    }
    if(t==null || t.length==0){
      t = loadScript(Main.getFolder().getFolderTemporary());
    }
    if(t==null || t.length==0){
      return false;
    }
    executeScript(t);
    return true;
  }
  /**
  *{@summary Load the script if it is in rep/levels/.}
  *@param rep The folder where to search.
  *@lastEditedVersion 1.38
  */
  private String [] loadScript(String rep){
    rep+=Main.getFolder().getFolderLevels();
    name = str.addALaFinSiNecessaire(name,".formiko");
    String ts [] = lireUnFichier.readFileArray(rep+name);
    return ts;
  }
  /**
  *{@summary Execute the script line by line.}
  *@param t the [] with all script lines.
  *@lastEditedVersion 1.38
  */
  private void executeScript(String t[]){
    debug.débogage("Lancement d'1 script a "+t.length+" commandes");
    setCmdSuivante(true);
    setEcouteClic(true);
    for (String s : t ) {
      while(!getCmdSuivante()){
        if(Main.getThScript().isInterrupted()){
          return;
        }
        try {
          wait();
        }catch (Exception e) {}
      }
      setEcouteClic(true);//au cas ou la commande juste avant ai désactivé l'écoute clic.
      debug.débogage("Commande : "+s);
      try {
        triche.commande(s);
      }catch (Exception e) {
        erreur.alerte("la commande suivante n'as pas été reconnue : "+s);
      }
    }
  }
}
