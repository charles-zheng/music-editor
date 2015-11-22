package cs3500.music.view;

import cs3500.music.model.*;
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

}
