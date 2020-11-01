package fr.formiko.usuel.son;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

//https://forums.commentcamarche.net/forum/affich-27006051-aide-java-comment-faire-lire-un-fichier-musique

public class Sound {
    private AudioFormat format;
    private byte[] samples;
    private SourceDataLine line;
    /**
     *
     * @param filename le lien vers le fichier song (URL ou absolute path)
     */
    public Sound(String filename){
     try{
      AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
      format = stream.getFormat();
      samples = getSamples(stream);
     }
     catch (UnsupportedAudioFileException e){
      e.printStackTrace();
     }
     catch (IOException e){
      e.printStackTrace();
     }
    }
    public byte[] getSamples(){
     return samples;
    }
    public byte[] getSamples(AudioInputStream stream){
     int length = (int)(stream.getFrameLength() * format.getFrameSize());
     byte[] samples = new byte[length];
     DataInputStream in = new DataInputStream(stream);
     try{
      in.readFully(samples);
     }
     catch (IOException e){
      e.printStackTrace();
     }
     return samples;
    }

    public void play(InputStream source) {
      int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
      byte[] buffer = new byte[bufferSize];
      try{
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        line = (SourceDataLine)AudioSystem.getLine(info);
        line.open(format, bufferSize);
      }
      catch (LineUnavailableException e){
        e.printStackTrace();
      return;
    }
    line.start();
    try{
      int numBytesRead = 0;
      while (numBytesRead != -1){
        numBytesRead = source.read(buffer, 0, buffer.length);
      if (numBytesRead != -1)
        line.write(buffer, 0, numBytesRead);
      }
    }
    catch (IOException e){
      e.printStackTrace();
    }
    stop();
  }
  public void stop(){
    line.close();//permet un arrêt rapide.
    try {
      line.drain();
      line.close();
    }catch (Exception e) {
      erreur.erreur("Impossible d'arreter la musique.");
    }
  }
}
