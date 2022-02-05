package fr.formiko.usuel.media.audio;

import fr.formiko.usuel.erreur;

/**
*{@summary audio interface with basic commands.}<br>
*@lastEditedVersion 1.52
*/
public interface AudioInterface {
  /***
  *{@summary play audio}<br>
  *@lastEditedVersion 1.52
  */
  void play();
  /***
  *{@summary pause audio}<br>
  *@lastEditedVersion 1.52
  */
  void pause();
  /***
  *{@summary resume audio}<br>
  *@lastEditedVersion 1.52
  */
  void resume();
  /***
  *{@summary stop audio}<br>
  *@lastEditedVersion 1.52
  */
  void stop();
}
