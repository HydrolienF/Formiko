package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

public class GEspece implements Serializable{
  protected CEspece début;
  protected CEspece fin;
  // CONSTRUCTORS --------------------------------------------------------------
  public GEspece(CEspece d){
    début = d;
  }
  public GEspece(Espece e){
    this(new CEspece(e));
  }
  public GEspece(){
    début = null;
    chargerLesEspeces();
  }
  // GET SET -------------------------------------------------------------------
  public CEspece getHead(){ return début;}
  public void setDébut(CEspece ce){début = ce; }
  public Espece getEspeceParId(int id){
    if (début == null){
      return null;
    }else {
      return début.getEspeceParId(id);
    }
  }
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    if(début == null){ return "GEspece vide";}
    return début.toString();
  }
  public void chargerLesEspeces(){
    String td [] = new String [0];
    try {
      td = lireUnFichier.readFileArray(Main.getFolder().getFolderStable()+Main.getFolder().getFolderBin()+"Espece.csv");
    }catch (Exception e) {
      erreur.erreur("Le fichier des Espece n'as pas pu être localisé. il devrais y avoir un docier data et celui ci devrait contenir un fichier Espece.txt",true);
    } int lentd = td.length;
    if (lentd < 3){ erreur.erreur("Le fichier des espece devrais contenir au moins 4 lignes dont 1 Espece",true);}
    String tDéfaut [] = decoderUnFichier.getTableauString(td[2],',');
    for (int i=3;i<lentd; i++) {
      this.add(créerUneEspece(td[i],tDéfaut));
    }
  }
  public Espece créerUneEspece(String s, String [] tDéfaut){
    debug.débogage("Création d'une nouvelle Espece");
    // test ici
    String t [] = decoderUnFichier.getTableauString(s,',');
    tableau.boucherLesCasesVide(t,tDéfaut); // on remplace d'éventuelle case vide par les paramètres par défaut.
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

  public void add(Espece e){
    if(début == null){
      début = new CEspece(e);
      fin =  début;
    }else{
      CEspece temp = new CEspece(e);
      fin.setSuivant(temp);
      fin = temp;
    }
  }
}
