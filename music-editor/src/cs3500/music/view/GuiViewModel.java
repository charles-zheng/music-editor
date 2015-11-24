package cs3500.music.view;


import cs3500.music.model.*;

import javax.sound.midi.InvalidMidiDataException;
import java.util.List;

/**
 * Represents a ViewModel that adds additional functionality to our model.
 * This functionality includes keeping track of selected notes, and keeping
 * a timestamp.
 */
public class GuiViewModel implements ViewModel {

  /**
   * The model to add functionality to
   */
  private Model m;

  /**
   * The current pitch that is selected
   *
   * INVARIANT: always within m.getHighestPitch() and m.getLowestPitch(), or -1
   */
  private int curPitch;

  /**
   * The current
   */
  private int curBeat;

  /**
   *
   */
  private int curInstrument;

  /**
   * Represents the timestamp that the piece is currently at
   */
  private int timeStamp;
  //INVARIANT: Must not be negative

  public GuiViewModel(Model m) {
    this.m = m;
    this.curPitch = -1;
    this.curBeat = -1;
    this.curInstrument = -1;
    this.timeStamp = 0;
  }

  @Override public Pitch getLowestPitch() {
    return m.getLowestPitch();
  }

  @Override public Pitch getHighestPitch() {
    return m.getHighestPitch();
  }

  @Override public int getFinalStartBeat() {
    return m.getFinalStartBeat();
  }

  @Override public int getFinalEndBeat() {
    return m.getFinalEndBeat();
  }

  @Override public int getMeasure() {
    return m.getMeasure();
  }

  @Override public int getTempo() {
    return m.getTempo();
  }

  @Override public void setTempo(int tempo) {
    m.setTempo(tempo);
  }

  @Override public List<Note> getNotesAtTime(int time) {
    return m.getNotesAtTime(time);
  }

  @Override public List<Note> getEndNotesAtTime(int time) { return m.getEndNotesAtTime(time); }

  @Override
  public void addNote(Pitch pitch, int startTime, int endTime, int instrument, int velocity) {
    try {
      m.addNote(pitch, startTime, endTime, instrument, velocity);
    }
    catch (IllegalAddException ex) {
      //do nothing
    }
  }

  @Override public Note getNoteAt(Pitch pitch, int time, int instrument) {
    return m.getNoteAt(pitch, time, instrument);
  }

  @Override public Note getNoteIn(Pitch pitch, int time, int instrument) {
    return m.getNoteIn(pitch, time, instrument);
  }

  @Override public Note getNoteIn(Pitch pitch, int time) {
    return m.getNoteIn(pitch, time);
  }

  @Override public void deleteNote(Pitch pitch, int time, int instrument) {
    m.deleteNote(pitch, time, instrument);
  }

  @Override
  public void editNoteStartTime(Pitch pitch, int currentStart, int newStart, int instrument) {
    m.editNoteStartTime(pitch, currentStart, newStart, instrument);
  }

  @Override
  public void editNoteEndTime(Pitch pitch, int currentStart, int newEnd, int instrument) {
    m.editNoteEndTime(pitch, currentStart, newEnd, instrument);
  }

  @Override public void addAllNotes(List<Note> allNotes, boolean atEnd) {
    m.addAllNotes(allNotes, atEnd);
  }

  /**
   * Gets the current timestamp of the piece
   *
   * @return the current timestamp
   */
  public int getTimeStamp() {
    return this.timeStamp;
  }

  /**
   * Advance the timestamp of this piece by 1;
   */
  public void advanceTimestamp() {
    this.timeStamp++;
  }

  /**
   * Sets the timestamp of this piece of this piece to the given timestamp
   *
   * @param t the new timestamp
   */
  public void setTimeStamp(int t) {
    this.timeStamp = t;
  }

  /**
   * Resets the timestamp of this piece to 0
   */
  public void resetTimestamp() {
    this.timeStamp = 0;
  }

  @Override public void initialize() throws InvalidMidiDataException {

  }

  public void setCurrent(int x, int y) {
    this.curBeat = (x >= 50 && x <= (m.getFinalEndBeat() + 2) * ConcreteGuiViewPanel.BOX_SIZE)
        ? (x - 50) / ConcreteGuiViewPanel.BOX_SIZE : -1;
    this.curPitch = (y >= 25 && y <= ((m.getHighestPitch().getValue() -
        m.getLowestPitch().getValue() + 2) * ConcreteGuiViewPanel.BOX_SIZE)) ?
        m.getHighestPitch().getValue() -
        ((y - ConcreteGuiViewPanel.BOX_SIZE) / ConcreteGuiViewPanel.BOX_SIZE) : -1;
    try {
      this.curInstrument = m.getNoteIn(new PitchImpl(curPitch), curBeat).getInstrument();
    }
    catch(IllegalAccessNoteException ex) {
      this.curInstrument = -1;
    }
  }

  public int getCurPitch() {
    return this.curPitch;
}

  public int getCurBeat() {
    return this.curBeat;
  }

  public int getCurInstrument() {
    return this.curInstrument;
  }

  public void setCurPitch(int pitch) {
    this.curPitch = pitch;
  }

  public void setCurBeat(int beat) {
    this.curBeat = beat;
  }

  public void setCurInstrument(int instrument) {
    this.curInstrument = instrument;
  }

}
