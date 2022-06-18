package fr.formiko.usual.media.audio;

import fr.formiko.usual.Folder;
import fr.formiko.usual.erreur;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.structures.listes.GString;

import java.io.File;
import java.io.IOException;

/**
*{@summary to listen music.}<br>
*It use audioPlayer &#38; make sur than only 1 is running at the same time.
*@author Hydrolien
*@lastEditedVersion 2.25
*/
public class MusicPlayer implements AudioInterface {
  private AudioPlayer audioPlayer;
  private boolean musicPaused;
  private GString nextMusics;
  private GString availableMusics;
  private String curentMusic;
  private Folder folder;
  private boolean bMusic;
  private int volMusic;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@lastEditedVersion 2.25
  */
  public MusicPlayer(Folder folder, boolean bMusic, int volMusic){
    nextMusics = new GString();
    this.folder=folder;
    this.bMusic=bMusic;
    this.volMusic=volMusic;
  }
  // GET SET -------------------------------------------------------------------
  public String getPath(){return folder.getFolderStable()+folder.getFolderMusiques();}
  public boolean isBMusic() {return bMusic;}
	public void setBMusic(boolean bMusic) {this.bMusic=bMusic;}
	public int getVolMusic() {return volMusic;}
	public void setVolMusic(int volMusic) {this.volMusic=volMusic;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Play next music.}<br>
  *@lastEditedVersion 2.25
  */
  public synchronized void play(){
    // erreur.info("Play",4);
    if(!bMusic){return;}
    if(audioPlayer!=null){audioPlayer.stop();}
    curentMusic = getNextMusic();
    if(curentMusic==null || curentMusic.equals("")){
      erreur.alerte("Can't play music because music is null or empty");
      return;
    }
    audioPlayer = new AudioPlayer(true, curentMusic);
    audioPlayer.setMp(this);
    audioPlayer.setVolume(volMusic);
    audioPlayer.play();
    musicPaused=false;
  }
  /**
  *{@summary Pause curent music.}<br>
  *@see MusicPlayer#resume()
  *@lastEditedVersion 1.52
  */
  public synchronized void pause(){
    if(audioPlayer!=null && !musicPaused){
      audioPlayer.pause();
      musicPaused=true;
    }
  }
  /**
  *{@summary Resume curent music.}<br>
  *@see MusicPlayer#pause()
  *@lastEditedVersion 1.52
  */
  public synchronized void resume(){
    if(audioPlayer!=null && musicPaused){
      audioPlayer.resume();
      musicPaused=false;
    }
  }
  /**
  *{@summary Stop curent music.}<br>
  *We need play() to start music again.<br>
  *@lastEditedVersion 1.52
  */
  public synchronized void stop(){
    if(audioPlayer!=null){
      audioPlayer.stop();
      audioPlayer=null;
      musicPaused=true;
    }
  }
  /**
  *{@summary Add next music to list of music to play.}<br>
  *@param music name of the music.
  *@param first If true add music at the head of the list. Else add at the end.
  *@lastEditedVersion 1.52
  */
  public synchronized void addNextMusic(String music, boolean first){
    if(first){
      nextMusics.addHead(music);
    }else{
      nextMusics.addTail(music);
    }
  }
  /**
  *{@summary Play next music.}<br>
  *@lastEditedVersion 1.52
  */
  public void next(){
    play();
  }
  /**
  *{@summary Initialize availables musics list.}<br>
  *@lastEditedVersion 1.52
  */
  public void iniAvailableMusics(){
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
  //private --------------------------------------------------------------------
  /**
  *{@summary return a music.}<br>
  *If there is a next music, it return next Music.
  *If next music list is empty, it return a random music.<br>
  *@lastEditedVersion 1.52
  */
  private String getNextMusic(){
    if(nextMusics.isEmpty()){
      curentMusic = getRandomMusic();
    }else{
      curentMusic = nextMusics.getItem(0);
      nextMusics.removeItem(0);
    }
    return getPath()+curentMusic;
  }
  /**
  *{@summary return a random music.}<br>
  *If the list of availableMusics is null, it create it.<br>
  *It should avoid to play same music than curent one by gettin a new random 1 (10 try).
  *@lastEditedVersion 1.52
  */
  private String getRandomMusic(){
    if(availableMusics==null){iniAvailableMusics();}
    if(availableMusics==null){return null;}
    int len = availableMusics.length();
    if(len<1){return "";}
    String music = null;
    int k=0;
    int i=0;
    do {
      i = allea.getAllea(len);
      music = availableMusics.getItem(allea.getAllea(len));
      k++;
    } while (curentMusic.equals(music) && len>1 && k<10);
    erreur.info("music "+i+" :"+music);
    return music;
  }
}
