package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.ReadFile;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.structures.listes.Liste;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

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
  public IEspece getIEspeceParId(int id){
    for (IEspece e : this) {
      if (e.getId()==id){ return e;}
    }
    return null;
  }
  /**
  *{@summary Return a random insect species id.}<br>
  *@param typeOfCase id of the Case
  *@lastEditedVersion 2.23
  */
  public byte getRandomTypeInsectOnTheCase(int typeOfCase){
    int a = allea.getAlléa(getTotal(typeOfCase));
    byte r = (byte) getIEspeceParAllea(a,typeOfCase);
    return r;
  }
  /**
  *{@summary get total score for a typeOfCase.}<br>
  *@param typeOfCase id of the Case
  *@lastEditedVersion 2.23
  */
  private int getTotal(int typeOfCase){
    int total=0;
    for (IEspece e : this) {
      total+=e.getCt(typeOfCase);
    }
    return total;
  }
  /**
  *{@summary Return a random insect species id from a random number.}<br>
  *@param a random that have been chossen
  *@param typeOfCase id of the Case
  *@lastEditedVersion 2.23
  */
  private int getIEspeceParAllea(int a, int typeOfCase){
    for (IEspece e : this) {
      a-=e.getCt(typeOfCase);
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
    String td [] = new String [0];
    try {
      td = ReadFile.readFileArray(Main.getFolder().getFolderStable()+Main.getFolder().getFolderBin()+"IEspece.csv");
    }catch (Exception e) {
      erreur.erreur("Species file fail to be found");
    }
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
  *@lastEditedVersion 2.23
  */
  private IEspece createIEspece(String s, String [] tdefault){
    String t [] = decoderUnFichier.getTableauString(s,',');
    tableau.boucherLesCasesVide(t,tdefault);
    int tInt[];
    try {
      tInt = str.sToI(t);
    }catch (Exception e) {
      tInt = new int[3];
      tInt[0]=0;tInt[1]=0;tInt[2]=0;
    }
    return new IEspece(tInt);
  }
}
