package cs3500.music.view;


import cs3500.music.model.*;
import java.awt.Dimension;
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
   * if no pitch is selected
   */
  private int curPitch;

  /**
   * The current beat that is selected
   *
   * INVARIANT: always within 0 and m.getFinalEndBeat(), or -1 if no beat is selected
   */
  private int curBeat;

  /**
   * The current instrument of the note that was selected
   *
   * INVARIANT: always within 1 and 16, or -1 if no note was selected
   */
  private int curInstrument;

  /**
   * Represents the timestamp that the piece is currently at
   *
   * INVARIANT: Must be within 0 and m.getFinalEndBeat();
   */
  private int timeStamp;

  /**
   * Represents the top-left corner of the viewable screen
   */
  private Dimension topleft;

  /**
   * Constructs a new GuiViewModel that wraps around the given model
   *
   * @param m the model to which we are adding new functionality
   */
  public GuiViewModel(Model m) {
    this.m = m;
    this.curPitch = -1;
    this.curBeat = -1;
    this.curInstrument = -1;
    this.timeStamp = 0;
    this.topleft = new Dimension(0, 0);
  }

  /**
   * Gets the lowest pitch of the piece.
   *
   * @return the lowest pitch
   */
  @Override public Pitch getLowestPitch() {
    return m.getLowestPitch();
  }

  /**
   * Gets the highest pitch of the piece.
   *
   * @return the highest pitch
   */
  @Override public Pitch getHighestPitch() {
    return m.getHighestPitch();
  }

  /**
   * Gets the final beat of the piece.
   *
   * @return the final beat
   */
  @Override public int getFinalStartBeat() {
    return m.getFinalStartBeat();
  }

  /**
   * Gets the beat after the last note of the piece ends.
   *
   * @return the final end beat
   */
  @Override public int getFinalEndBeat() {
    return m.getFinalEndBeat();
  }

  /**
   * Gets the measure for the piece.
   *
   * @return the measure of the piece
   */
  @Override public int getMeasure() {
    return m.getMeasure();
  }

  /**
   * Gets the tempo of this piece.
   *
   * @return the tempo
   */
  @Override public int getTempo() {
    return m.getTempo();
  }

  /**
   * Sets the tempo of this piece to the given tempo.
   *
   * @param tempo the new tempo of the piece
   */
  @Override public void setTempo(int tempo) {
    m.setTempo(tempo);
  }

  /**
   * Gets a list of notes that should be played at the given time.
   *
   * @return the list of notes that should be played at the given timestamp
   */
  @Override public List<Note> getNotesAtTime(int time) {
    return m.getNotesAtTime(time);
  }

  /**
   * Gets a list of notes that should be turned off at the given time.
   *
   * @param time the time at which to get the notes that end
   * @return the list of notes that should be turned off at the given time
   */
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

  public void setViewableRange(Dimension corner) {
    this.topleft = corner;
  }

  public Dimension getTopleft() {
    return this.topleft;
  }

}
