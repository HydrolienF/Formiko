package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import fr.formiko.usuel.liste.GString;import fr.formiko.usuel.liste.CString;
import fr.formiko.usuel.lireUnFichier;

public class impor {
  public static void getImports() {
    String t [] = new String [1]; t[0]="contenu.txt";
    GString gs = new GString();
    for (String nom : t ) {
      gs.ajouter(getImport(nom));
    }
    gs.filtreDoublon();
    transformer(gs);
    System.out.println("module test.exemple {");
    gs.afficheToi();
    System.out.println("}");
  }
  // Fonctions propre -----------------------------------------------------------

  public static GString getImport(String nom){
    String t [] = lireUnFichier.lireUnFichier(nom);
    GString gs = new GString();
    for (String ligne : t ) {//import
      if (ligne.length() > 10 && contientImport(ligne)){
        gs.ajouter(ligne.substring(7));// la ligne moins les 7 premier char
      }
    }
    return gs;
  }
  public static boolean contientImport(String se){
    String s[] = se.split(" ");
    if (s.length < 2){ return false;}
    //if(s.charAt(0)=='i' && s.charAt(1)=='m' && s.charAt(2)=='p' && s.charAt(3)=='o' && s.charAt(4)=='r' && s.charAt(5)=='t'){
      //if(s.charAt(7)=='u' && s.charAt(8)=='s' && s.charAt(9)=='u' && s.charAt(10)=='e' && s.charAt(11)=='l'){ return false;}
    if(s[0].equals("import")){
      String s1 = s[1];
      //System.out.println(s1);
      //String s2 = s1.substring(0,7);
      //if (s2.length==0){ return false;}
      if(commencePar("formiko",s1) || commencePar("graphisme",s1) || commencePar("usuel",s1)){ return false;}
      return true;
    }
    return false;
  }
  public static boolean commencePar(String mot, String phrase){
    if (phrase.length() < mot.length()){ return false;}
    return phrase.substring(0,mot.length()).equals(mot);
  }
  public static void transformer(GString gs){
    CString cs = gs.getDébut();
    while(cs!=null){
      cs.setContenu("   requires "+cs.getContenu());
      cs=cs.getSuivant();
    }
  }
}
