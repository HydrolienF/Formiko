package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.ReadFile;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

/**
*{@summary List of species.}<br>
*@author Hydrolien
*@lastEditedVersion 2.23
*/
public class GEspece extends Liste<Espece> implements Serializable {
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Create a new list of species from game data.}<br>
  *@lastEditedVersion 2.23
  */
  public GEspece(){
    super();
    loadEspeces();
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Return a species.}<br>
  *@param id id of the species
  *@lastEditedVersion 2.23
  */
  public Espece getEspeceById(int id){
    for (Espece e : this) {
      if (e.getId()==id){ return e;}
    }
    return null;
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Load all species from game data.}<br>
  *@lastEditedVersion 2.23
  */
  public void loadEspeces(){
    String td [] = new String [0];
    try {
      td = ReadFile.readFileArray(Main.getFolder().getFolderStable()+Main.getFolder().getFolderBin()+"Espece.csv");
    }catch (Exception e) {
      erreur.erreur("Species file fail to be found",true);
    } int lentd = td.length;
    if (lentd < 3){ erreur.erreur("Species file should have at least 4 line",true);}
    String tdefault [] = decoderUnFichier.getTableauString(td[2],',');
    for (int i=3;i<lentd; i++) {
      this.add(createEspece(td[i],tdefault));
    }
  }
  /**
  *{@summary Load a species from game data.}<br>
  *Empty cells will be fullfill by default value.
  *@param s line to use to get sepcies data.
  *@param tdefault default sepcies data.
  *@return the created species
  *@lastEditedVersion 2.23
  */
  public Espece createEspece(String s, String [] tdefault){
    debug.débogage("Création d'une nouvelle Espece");
    String t [] = decoderUnFichier.getTableauString(s,',');
    tableau.boucherLesCasesVide(t,tdefault);
    int k=0;
    int id = str.sToI(t[k++]); String nom = t[k++]; int nbr = str.sToI(t[k++]); boolean polycalique=str.sToB(t[k++]); boolean monogyne=str.sToB(t[k++]); boolean insectivore=str.sToB(t[k++]); boolean granivore=str.sToB(t[k++]); boolean fongivore=str.sToB(t[k++]); boolean herbivore=str.sToB(t[k++]);boolean mv=str.sToB(t[k++]);
    byte tmin=str.sToBy(t[k++]); byte tmini=str.sToBy(t[k++]); byte tmaxi=str.sToBy(t[k++]); byte tmax=str.sToBy(t[k++]); byte tnidmin=str.sToBy(t[k++]);byte tnidmini=str.sToBy(t[k++]); byte tnidmaxi=str.sToBy(t[k++]); byte tnidmax=str.sToBy(t[k++]);
    byte humin=str.sToBy(t[k++]); byte humax=str.sToBy(t[k++]);boolean ha0=str.sToB(t[k++]); boolean ha1=str.sToB(t[k++]); boolean ha2=str.sToB(t[k++]);
    byte po [] = {str.sToBy(t[k++]),str.sToBy(t[k++]),str.sToBy(t[k++]),str.sToBy(t[k++])};
    int nf [] = {str.sToI(t[k++]),str.sToI(t[k++]),str.sToI(t[k++]),str.sToI(t[k++])};
    int taille [] = {str.sToI(t[k++]),str.sToI(t[k++]),str.sToI(t[k++]),str.sToI(t[k++])};
    boolean vole = str.sToB(t[k++]);
    String note=t[k];
    Espece e = new Espece(id, nom, nbr, polycalique, monogyne,insectivore,granivore,fongivore,herbivore,mv,tmin, tmini, tmaxi, tmax,tnidmin, tnidmini, tnidmaxi, tnidmax, humin, humax, ha0, ha1, ha2, po,nf,taille,vole,note);
    return e;
  }
}
