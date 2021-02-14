package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class Carte implements Serializable{
  private GCase gc;
  private byte abondanceInsecte,abondanceGraine,abondanceHerbe;
  private boolean casesNuageuses,casesSombres;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Carte(GCase gc, byte abondanceInsecte, byte abondanceGraine, byte abondanceHerbe, boolean caseNuageuse, boolean caseSombre){
    if (gc==null){erreur.erreur("impossible d'avoir un GCase null ici","Carte.Carte");}
    this.gc = gc;
    debug.débogage("Création de carte a "+this.gc.length()+" cases");
    this.abondanceInsecte = abondanceInsecte; this.abondanceGraine = abondanceGraine; this.abondanceHerbe = abondanceHerbe;
    casesSombres=caseSombre; casesNuageuses=caseNuageuse;
  }public Carte(GCase gc, int a, int b, int c, boolean d, boolean e){ this(gc,(byte) a,(byte) b,(byte) c, d, e);}
  public Carte(GCase gc){
    this(gc,10,10,10,true,true);
  }
  // GET SET --------------------------------------------------------------------
  public GCase getGc(){ return gc;}
  public byte getAbondanceGraine(){ return abondanceGraine;}
  public byte getAbondanceInsecte(){ return abondanceInsecte;}
  public byte getAbondanceHerbe(){ return abondanceHerbe;}
  public int getNbrDInsecteAuDébut(){ return (gc.length()*abondanceInsecte)/100;} //si abondance = 100 (le max) on a 1 insecte par case, sinon 10 fois moins par défaut. sinon 100 fois moins si l'abondance est minimale (1).
  public boolean getCasesNuageuses(){return casesNuageuses;}
  public void setCasesNuageuses(boolean b){casesNuageuses=b;}
  public boolean getCasesSombres(){return casesSombres;}
  public void setCasesSombres(boolean b){casesSombres=b;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String sr = "";
    sr+= g.getM("abondanceInsecte")+" : "+abondanceInsecte+", ";
    sr+= g.getM("abondanceGraine")+" : "+abondanceGraine+", ";
    sr+= g.getM("abondanceHerbe")+" : "+abondanceHerbe+", ";
    sr+= g.getM("casesNuageuses")+" : "+casesNuageuses+", ";
    sr+= g.getM("casesSombres")+" : "+casesSombres+", ";
    sr+= g.getM("dimention")+" : "+gc.getDim();
    return sr;
  }
  public void afficheToi(){
    System.out.println(this);
  }
  public int length(){return gc.length();}
}
