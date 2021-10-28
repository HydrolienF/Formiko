package fr.formiko.usuel.images;

import fr.formiko.formiko.GCase;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.image;

public class ThImage extends Thread{
  private GCase gc;
  private Img ie;
  private Img ti[];
  private int i;
  private int lenj;private int leni;
  // CONSTRUCTORS --------------------------------------------------------------
  public ThImage(GCase gc,Img ie,int i,Img ti[],int lenj,int leni){
    this.gc=gc;this.ie=ie;this.i=i;this.ti=ti;this.lenj=lenj;this.leni=leni;
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  @Override
  public void run(){
    for (int j=0;j<lenj;j++ ) {
      System.out.println("éléments n°"+(i*lenj+j)+"/"+(leni*lenj));
      ie.add(i*500,j*500,ti[gc.getCCase(i,j).getContent().getType()]);
    }
  }
}
