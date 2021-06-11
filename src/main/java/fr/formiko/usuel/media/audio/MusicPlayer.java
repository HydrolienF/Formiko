package fr.formiko.usuel.media.audio;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.maths.allea;

import java.io.File;
import java.io.IOException;

public class MusicPlayer implements AudioInterface {
  private AudioPlayer audioPlayer;
  private boolean musicPaused;
  private GString nextMusics;
  private GString availableMusics;
  // CONSTRUCTORS --------------------------------------------------------------
  public MusicPlayer(){
    nextMusics = new GString();
  }
  // GET SET -------------------------------------------------------------------
  public String getPath(){
    return Main.getFolder().getFolderStable()+Main.getFolder().getFolderMusiques();
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary play next music.}<br>
  *@version 1.52
  */
  public void play(){
    if(audioPlayer!=null){audioPlayer.stop();}
    String music = getNextMusique();
    if(music==null || music.equals("")){
      erreur.alerte("Can't play music because music is null or empty");
      return;
    }
    audioPlayer = new AudioPlayer(music);
    audioPlayer.play();
    musicPaused=false;
  }
  /**
  *{@summary pause curent music.}<br>
  *@see MusicPlayer#resume()
  *@version 1.52
  */
  public void pause(){
    if(audioPlayer!=null && !musicPaused){
      audioPlayer.pause();
      musicPaused=true;
    }
  }
  /**
  *{@summary resume curent music.}<br>
  *@see MusicPlayer#pause()
  *@version 1.52
  */
  public void resume(){
    if(audioPlayer!=null && musicPaused){
      audioPlayer.resume();
      musicPaused=false;
    }
  }
  /**
  *{@summary stop curent music.}<br>
  *We need play() to start music again.<br>
  *@version 1.52
  */
  public void stop(){
    if(audioPlayer!=null){
      audioPlayer.stop();
      audioPlayer=null;
      musicPaused=true;
    }
  }
  //private --------------------------------------------------------------------
  /**
  *{@summary return a music.}<br>
  *If there is a next music, it return next musique.
  *If next music list is empty, it return a random music.<br>
  *@version 1.52
  */
  private String getNextMusique(){
    if(nextMusics.isEmpty()){
      return getRandomMusic();
    }else{
      String music = nextMusics.getItem(0);
      nextMusics.removeItem(0);
      return music;
    }
  }
  /**
  *{@summary return a random music.}<br>
  *If the list of availableMusics is null, it create it.<br>
  *@version 1.52
  */
  private String getRandomMusic(){
    if(availableMusics==null){iniAvailableMusics();}
    if(availableMusics==null){return null;}
    int len = availableMusics.length();
    if(len<1){return "";}
    int i = allea.getAllea(len);
    String music = availableMusics.getItem(i);
    erreur.info("music "+i+" :"+music);//@a
    return getPath()+music;
  }
  /**
  *{@summary Initialize availables musics list.}<br>
  *@version 1.52
  */
  private void iniAvailableMusics(){
    File dir = new File(getPath());
    if(dir.isDirectory()){
      String t [] = dir.list();
      if(t.length>0){
        availableMusics = new GString();
        for (String s : t) {
          availableMusics.add(s);
        }
      }else{
        String path = "null";
        try {
          path=dir.getCanonicalPath();
        }catch (IOException e) {}
        erreur.info("no availableMusics in "+path);
      }
    }else{
      erreur.info("no music file");
    }
  }
}
