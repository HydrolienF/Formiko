package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.conversiondetype.str;

public class GEspece {
  protected CEspece début;
  protected CEspece fin;
  // CONSTRUCTEUR ---------------------------------------------------------------
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
  // GET SET --------------------------------------------------------------------
  public CEspece getDébut(){ return début;}
  public void setDébut(CEspece ce){début = ce; }
  public Espece getEspeceParId(int id){
    if (début == null){
      return null;
    }else {
      return début.getEspeceParId(id);
    }
  }
  // Fonctions propre -----------------------------------------------------------
  public void afficheToi(){
    if(début == null){ erreur.erreur("Impossible d'afficher une liste vide","GEspece.afficheToi");return;}
    début.afficheToi();
  }
  public void chargerLesEspeces(){
    String td [] = new String [0];
    try {
      td = lireUnFichier.lireUnFichier("data/Espece.csv");
    }catch (Exception e) {
      erreur.erreur("Le fichier des Espece n'as pas pu être localisé. il devrais y avoir un docier data et celui ci devrait contenir un fichier Espece.txt","GEspece.chargerLesEspeces",true);
    } int lentd = td.length;
    if (lentd < 3){ erreur.erreur("Le fichier des espece devrais contenir au moins 4 lignes dont 1 Espece","GEspece.chargerLesEspeces",true);}
    String tDéfaut [] = decoderUnFichier.getTableauString(td[2],',');
    for (int i=3;i<lentd; i++) {
      this.ajouter(créerUneEspece(td[i],tDéfaut));
    }
  }
  public Espece créerUneEspece(String s, String [] tDéfaut){
    debug.débogage("Création d'une nouvelle Espece");
    // test ici
    String t [] = decoderUnFichier.getTableauString(s,',');
    tableau.boucherLesCasesVide(t,tDéfaut); // on remplace d'éventuelle case vide par les paramètres par défaut.
    int id = str.sToI(t[0]); String nom = t[1]; int nbr = str.sToI(t[2]); boolean polycalique=str.sToB(t[3]); boolean monogyne=str.sToB(t[4]); boolean insectivore=str.sToB(t[5]); boolean granivore=str.sToB(t[6]); boolean fongivore=str.sToB(t[7]);
    byte tmin=str.sToBy(t[8]); byte tmini=str.sToBy(t[9]); byte tmaxi=str.sToBy(t[10]); byte tmax=str.sToBy(t[11]); byte tnidmin=str.sToBy(t[12]);byte tnidmini=str.sToBy(t[13]); byte tnidmaxi=str.sToBy(t[14]); byte tnidmax=str.sToBy(t[15]);
    byte humin=str.sToBy(t[16]); byte humax=str.sToBy(t[17]);boolean ha0=str.sToB(t[18]); boolean ha1=str.sToB(t[19]); boolean ha2=str.sToB(t[20]); byte po0=str.sToBy(t[21]); byte po1=str.sToBy(t[22]); byte po2=str.sToBy(t[23]); byte po3=str.sToBy(t[24]); String note=t[25];
    Espece e = new Espece(id, nom, nbr, polycalique, monogyne,insectivore,granivore,fongivore,tmin, tmini, tmaxi, tmax,tnidmin, tnidmini, tnidmaxi, tnidmax, humin, humax, ha0, ha1, ha2, po0,po1,po2,po3,note);
    return e;
  }

  public void ajouter(Espece e){
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