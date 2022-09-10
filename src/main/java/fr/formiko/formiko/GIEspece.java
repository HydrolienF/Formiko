package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.ReadFile;
import fr.formiko.usual.decoderUnFichier;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.structures.listes.Liste;
import fr.formiko.usual.tableau;
import fr.formiko.usual.types.str;

import java.io.Serializable;

/**
*{@summary List of IEspece.}<br>
*@author Hydrolien
*@lastEditedVersion 2.23
*/
public class GIEspece extends Liste<IEspece> implements Serializable {
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Create a new list of species from game data.}<br>
  *@lastEditedVersion 2.23
  */
  public GIEspece(){
    super();
    loadIEspeces();
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Return a species.}<br>
  *@param id id of the species
  *@lastEditedVersion 2.23
  */
  public IEspece getIEspeceById(int id){
    for (IEspece e : this) {
      if (e.getId()==id){ return e;}
    }
    return null;
  }
  /**
  *{@summary Return a random insect species id.}<br>
  *@param typeOfSquare id of the Square
  *@lastEditedVersion 2.23
  */
  public byte getRandomTypeInsectOnTheSquare(int typeOfSquare){
    int a = allea.getAlléa(getTotal(typeOfSquare));
    byte r = (byte) getIEspeceParAllea(a,typeOfSquare);
    return r;
  }
  /**
  *{@summary get total score for a typeOfSquare.}<br>
  *@param typeOfSquare id of the Square
  *@lastEditedVersion 2.23
  */
  private int getTotal(int typeOfSquare){
    int total=0;
    for (IEspece e : this) {
      total+=e.getCt(typeOfSquare);
    }
    return total;
  }
  /**
  *{@summary Return a random insect species id from a random number.}<br>
  *@param a random that have been chossen
  *@param typeOfSquare id of the Square
  *@lastEditedVersion 2.23
  */
  private int getIEspeceParAllea(int a, int typeOfSquare){
    for (IEspece e : this) {
      a-=e.getCt(typeOfSquare);
      if(a<0){
        return e.getId();
      }
    }
    erreur.erreur("Wrong way to choose IEspece","Chosen insect will be last 1");
    return getLast().getId();
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Load all IEspece from game data.}<br>
  *@lastEditedVersion 2.23
  */
  private void loadIEspeces(){
    String td [] = ReadFile.readFileArray(Main.getFolder().getFolderStable()+Main.getFolder().getFolderBin()+"IEspece.csv");
    int lentd = td.length;
    String tdefault [] = {"5","5","5"};
    for (int i=1;i<lentd; i++) {
      this.add(createIEspece(td[i],tdefault));
    }
  }
  /**
  *{@summary Load a IEspece from game data.}<br>
  *Empty cells will be fullfill by default value.
  *@param s line to use to get sepcies data.
  *@param tdefault default sepcies data.
  *@return the created IEspece
  *@lastEditedVersion 2.29
  */
  private IEspece createIEspece(String s, String [] tdefault){
    String t [] = decoderUnFichier.getTableauString(s,',');
    tableau.boucherLesCasesVide(t,tdefault);
    int tInt[]=str.sToI(t);
    if(tInt==null || tInt.length<3 || tInt[0]==-1){
      tInt = new int[3];
      tInt[0]=0;tInt[1]=0;tInt[2]=0;
    }
    return new IEspece(tInt);
  }
}
