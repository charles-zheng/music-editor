package cs3500.music.view;

import cs3500.music.model.*;
import java.awt.Dimension;
/**
 * Created by Charles on 11/18/15.
 */
public interface ViewModel extends Model, View {

  void setCurrent(int x, int y);

  int getCurPitch();

  int getCurBeat();

  int getCurInstrument();

  void setCurPitch(int pitch);

  void setCurBeat(int beat);

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

  void setViewableRange(Dimension corner);

  Dimension getTopleft();

}
