package cs3500.music.view;

import cs3500.music.model.*;

import java.util.List;


/**
 * An adapter that adds additional functionality to our model.
 */
public interface ViewModel extends Model, View {

  /**
   * Sends in the location of the mouse click to set the current Note that is being
   * selected.
   *
   * @param x The x coordinate of the mouse click.
   * @param y The y coordinate of the mouse click.
   */
  void setCurrent(int x, int y);

  /**
   * Gets the pitch of the note that is currently selected.
   *
   * @return The value of the pitch of the note that is currently selected.
   */
  int getCurPitch();

  /**
   * Gets the beat of the note that is currently selected.
   *
   * @return The beat of the note that is currently selected.
   */
  int getCurBeat();

  /**
   * Gets the instrument of the note that is currently selected.
   *
   * @return The instrument of the note that is currently selected.
   */
  int getCurInstrument();

  /**
   * Saves the pitch of the current note.
   *
   * @param pitch The pitch of the note that is currently selected.
   */
  void setCurPitch(int pitch);

  /**
   * Saves the beat of the current note.
   *
   * @param beat The beat of the note that is currently selected.
   */
  void setCurBeat(int beat);

  /**
   * Saves the instrument of the current note.
   *
   * @param instrument The instrument of the note that is currently selected.
   */
  void setCurInstrument(int instrument);

  /**
   * Gets the current timestamp of the piece
   *
   * @return the current timestamp
   */
  int getTimeStamp();

  /**
   * Advance the timestamp of this piece by 1;
   */
  void advanceTimestamp();

  /**
   * Sets the timestamp of this piece of this piece to the given timestamp
   *
   * @param t the new timestamp
   */
  void setTimeStamp(int t);

  /**
   * Resets the timestamp of this piece to 0
   */
  void resetTimestamp();

  /**
   * Returns the beat at the current timestamp
   *
   * @return the beat at the current timestamp
   */
  int getBeatStamp();

  /**
   * Gets the beat that was previously clicked on
   *
   * @return the beat that was previously clicked on
   */
  int getPrevBeat();

  /**
   *
   */
  void initBeats();

  /**
   * Sets complex notes to true or false, depending on the param init.
   *
   * @param init True of complex notes are being collected, false if the collection is done.
   */
  void setComplexNotes(boolean init);

  /**
   * Gets whether or not complex notes are being collected.
   *
   * @return true is complex notes for the repeat are being collected, false if not.
   */
  boolean getComplexNotes();

  /**
   * Returns the list of complex notes.
   *
   * @return the list of complex notes.
   */
  List<Integer> getComplexEnds();

  /**
   * Adds the current beat to the list of complex notes.
   */
  void addToComplex();

  /**
   * Resets the complex notes list to empty.
   */
  void initComplexNotes();

}
