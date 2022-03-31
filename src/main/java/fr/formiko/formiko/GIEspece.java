package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

public class GIEspece implements Serializable{
  protected CIEspece début;
  protected CIEspece fin;
  // CONSTRUCTORS --------------------------------------------------------------
  public GIEspece(){
    début = null;
    chargerLesIEspeces();
  }
  // GET SET -------------------------------------------------------------------
  public CIEspece getHead(){ return début;}
  public void setDébut(CIEspece ce){début = ce; }
  public IEspece getIEspeceParId(int id){
    if (début == null){
      return null;
    }else {
      return début.getIEspeceParId(id);
    }
  }
  public byte getRandomTypeInsectOnTheCase(int typeDeCase){
    //return (byte) 0; //a continuer
    int total = getTotal(typeDeCase);
    int a = allea.getAlléa(total);
    byte r = (byte) getIEspeceParAllea(a,typeDeCase);
    return r;
  }
  public int getTotal(int x){
    if(début==null){erreur.erreurGXVide("GIEspece");return 0;}
    return début.getTotal(x);
  }
  public int getIEspeceParAllea(int a, int x){
    if(début==null){erreur.erreurGXVide("GIEspece");return 0;}
    return début.getIEspeceParAllea(a,x);
  }
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    if(début == null){ erreur.erreurGXVide("GIEspece");return "";}
    return début.toString();
  }
  public void chargerLesIEspeces(){
    String td [] = new String [0];
    try {
      td = lireUnFichier.readFileArray(Main.getFolder().getFolderStable()+Main.getFolder().getFolderBin()+"IEspece.csv");
    }catch (Exception e) {
      erreur.erreur("Le fichier des IEspece n'as pas pu être localisé. il devrais y avoir un docier data et celui ci devrait contenir un fichier IEspece.txt",true);
    } int lentd = td.length;
    //if (lentd < 3){ erreur.erreur("Le fichier des IEspece devrais contenir au moins 4 lignes dont 1 IEspece","GIEspece.chargerLesIEspeces",true);}
    String tDéfaut [] = {"5","5","5"};
    for (int i=1;i<lentd; i++) {
      this.add(créerUneIEspece(td[i],tDéfaut));
    }
  }
  public IEspece créerUneIEspece(String s, String [] tDéfaut){
    debug.débogage("Création d'une nouvelle IEspece");
    // test ici
    String t [] = decoderUnFichier.getTableauString(s,',');
    tableau.boucherLesCasesVide(t,tDéfaut); // on remplace d'éventuelle case vide par les paramètres par défaut.
    int tInt[];
    try {
      tInt = str.sToI(t);
    }catch (Exception e) {
      tInt = new int[3];
      tInt[0]=0;tInt[1]=0;tInt[2]=0;
    }
    return new IEspece(tInt);
  }

  public void add(IEspece e){
    if(début == null){
      début = new CIEspece(e);
      fin =  début;
    }else{
      CIEspece temp = new CIEspece(e);
      fin.setSuivant(temp);
      fin = temp;
    }
  }
}
