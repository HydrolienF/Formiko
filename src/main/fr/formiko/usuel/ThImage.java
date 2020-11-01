package fr.formiko.usuel.image;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.GCase;
import fr.formiko.usuel.image.Img;
import fr.formiko.usuel.image.image;

public class ThImage extends Thread{
  private GCase gc;
  private Img ie;
  private Img ti[];
  private int i;
  private int lenj;private int leni;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public ThImage(GCase gc,Img ie,int i,Img ti[],int lenj,int leni){
    this.gc=gc;this.ie=ie;this.i=i;this.ti=ti;this.lenj=lenj;this.leni=leni;
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  @Override
  public void run(){
    for (int j=0;j<lenj;j++ ) {
      System.out.println("éléments n°"+(i*lenj+j)+"/"+(leni*lenj));
      ie.add(i*500,j*500,ti[gc.getCCase(i,j).getContenu().getType()]);
    }
  }
}
