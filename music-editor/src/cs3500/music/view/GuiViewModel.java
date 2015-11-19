package cs3500.music.view;


import cs3500.music.model.*;

import javax.sound.midi.InvalidMidiDataException;
import java.util.List;

/**
 * Created by Charles on 11/18/15.
 */

//TODO
public class GuiViewModel implements ViewModel {

  private Model m;
  private int curPitch;
  private int curBeat;

  public GuiViewModel(Model m) {
    this.m = m;
    this.curPitch = -1;
    this.curBeat = -1;
  }

  @Override public int getTimeStamp() {
    return m.getTimeStamp();
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

  @Override
  public void addNote(Pitch pitch, int startTime, int endTime, int instrument, int velocity) {
    m.addNote(pitch, startTime, endTime, instrument, velocity);
  }

  @Override public Note getNoteAt(Pitch pitch, int time, int instrument) {
    return m.getNoteAt(pitch, time, instrument);
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

  @Override public void advanceTimestamp() {
    m.advanceTimestamp();
  }

  @Override public void setTimeStamp(int t) {m.setTimeStamp(t);}

  @Override public void resetTimestamp() {
    m.resetTimestamp();
  }

  @Override public void initialize() throws InvalidMidiDataException {

  }

  public void setCurrent(int x, int y) {
    this.curBeat = (x - 50) / ConcreteGuiViewPanel.BOX_SIZE;
    this.curPitch = m.getHighestPitch().getValue() -
        ((y - ConcreteGuiViewPanel.BOX_SIZE) / ConcreteGuiViewPanel.BOX_SIZE);
  }

  public int getCurPitch() {
    return this.curPitch;
  }

  public int getCurBeat() {
    return this.curBeat;
  }

  public void setCurPitch(int pitch) {
    this.curPitch = pitch;
  }

  public void setCurBeat(int beat) {
    this.curBeat = beat;
  }

}
