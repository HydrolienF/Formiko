package fr.formiko.usuel.conversiondetype;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.tableau;

public class ent{

  //Conversion de type.
	public static int ent(double x){
		return (int) x;
	}
  public static int ent(float x){
		return (int) x;
	}
  public static int ent(String s){
		try {
			return Integer.parseInt(s);
		} catch (Exception e){ // je sais pas comment trow une Exeption autre que celle de base sur un truc comme ca.
			e.printStackTrace();
		}
		return -1;
	}
	public static int ent(boolean b){
		if (b) return 1;
		return 0;
	}
	public static int [] ent(String t []){
		int tr [] = new int [t.length]; int i=0;
		for (String s : t) {
			tr [i] = ent(s); i++;
		}
		return tr;
	}
	public static int [][] ent(String t [][]){
		int tr [][] = new int [t.length][]; int i=0;
		for (String s [] : t) {
			tr[i] = ent(s); i++;
		}
		return tr;
	}
	public static int stringToInt(String s){
		try{
			return Integer.parseInt(s);
		}catch (NumberFormatException e) {
			erreur.erreur(g.get("ent",1,"La conversion String To int a échoué"),"ent.stringToInt");
			return -1;
		}
	}public static int sToI(String s){ return stringToInt(s);}
}
