package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.math.math;
import fr.formiko.formiko.interfaces.*;
import java.io.Serializable;

public class Insecte extends Creature implements Serializable{
  protected byte nourritureMangeable;
  protected byte type;
  protected static GIEspece gie;
  // CONSTRUCTEUR -----------------------------------------------------------------
  // Principal
  public Insecte (CCase p, int age, int ageMax, int actionMax){
    // Soit l'insecte est terrestre et vien de naitre, soit il est volant et il est mort.
    super(p,age,ageMax, actionMax);
    if (action == 0){ estMort = true; age = ageMax; }
    this.nourritureMangeable =(byte) (allea.getAlléa(3)+1);// de 1 a 4.
    this.déplacement = new DeplacementFourmi();
    p.getContenu().getGc().ajouter(this);
    type = getTypeInsecte();//p.getContenu().getTypeInsecte();
    stade = (byte)0; // doit apparaitre en -3 pour etre un oeuf.
    mourir = new MourirInsecte();
    e = Main.getGEspece().getEspeceParId(100+getType());//les Espece d'insectes sont décaler de 100 par rapport au espece de Fourmi.
    if(e==null){erreur.erreur("Une espece d'insecte n'as pas pu etre chargé : "+(100+getType()),"Insecte.Insecte");}
    setNourritureFournie(e.getNourritureFournie(getStade()));
    debug.débogage("L'insecte "+ this.id + " a été  créée");
  }
  //Auxiliaire
  public Insecte (CCase p){
    this(p, 0,10 + allea.getAlléa(101), allea.getAlléa(21));//action entre 0 et 20.
  }
  public Insecte (){ //on place l'insecte alléatoirement. //  en théorie soit il nait et il a la case de ca mere, soit il vient d'autre par et dans ce cas il apparait sur une case en bordure de carte.
    this(Main.getGc().getCCaseAlléa());
    int cptBoucle=0;
  }

  // GET SET -----------------------------------------------------------------------
  public byte getNourritureMangeable(){ return nourritureMangeable;}
  public void setNourritureMangeable(byte x){ nourritureMangeable=x;} public void setNourritureMangeable(int x){ setNourritureMangeable((byte)x);}
  @Override
  public byte getType(){ return type;}
  public void setType(byte x){type=x;}public void setType(int x){setType((byte)x);}
  public byte getTypeInsecte(){return gie.getTypeInsecte(getCCase().getContenu().getType());}
  @Override
  public boolean getVole(){if(getStade()!=0){return false;}return e.getVole();}//si c'est un imago ca dépend de l'espece.
  public static void setGie(){ gie=new GIEspece();}//initialise le fichier/
  @Override
  public String getNom(){return g.get("I"+getType());}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String m = "";
    if (estMort){
      m = " (Mort"+(ageMax-age)+") ";
    }else {
      m = " ("+(ageMax-age)+" tour avant Mort)";
    }
    String sr = g.getOu("le","la")+" "+getNom();
    sr+= " "+id+ m +" "+g.get("de")+" "+g.get("type")+" "+type+" "+p.desc()+" "+g.get("nourritureFournie")+" : " + getNourritureFournie() +" "+g.get("nourriture")+" "+nourriture+"/"+nourritureMax;
    return sr;
  }
  public void afficheToi(){
    System.out.println(this);
  }
  public void tourInsecte(){
    if(!estMort){
      int i=0;
      while( this.getAction()>i){
        if (this.getNourriture()<this.getNourritureMax()/2 && this.getCCase().getContenu().getNourritureInsecte() > nourritureMangeable){
          manger();
        }else{
          this.ceDeplacer(true); // ce déplacer de façon alléatoire.
        }
        i++;
      }
      // Un tour ça coute en age et en nourriture.
      this.setNourritureMoins1();
    }this.setAgePlus1(); //une fois morte une créature a un délai avant disparition total dans la variable age
  }
  public void manger(){
    byte nourritureSurCase = this.getCCase().getContenu().getNourritureInsecte();
    if (nourritureSurCase > 0){
      byte nourritureMangé = (byte) math.min(nourritureSurCase,nourritureMangeable);
      this.getCCase().getContenu().setNourritureInsecte((byte)(nourritureSurCase-nourritureMangé));
      this.setNourriture(this.getNourriture() + nourritureMangé);
    }
  }

}
