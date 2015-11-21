package cs3500.music.controller;

import cs3500.music.view.*;
import cs3500.music.model.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controls the interactions between Model and the Composite and Gui views
 */
public class ControllerImpl implements Controller {

  /**
   * The composite view, a combination of midi and gui views
   */
  private CompositeView view;

  private ViewModel model;

  private Timer timer;

  /**
   * The Keyboard handler, deals with key events
   */
  private KeyboardHandler kh;

  /**
   * Makes a new Controller with the given view
   *
   * @param m The composite view that this controller will control
   */
  public ControllerImpl(Model m) throws InvalidMidiDataException {
    this.model = new GuiViewModel(m);
    this.view = new CompositeView(new MidiViewImpl(m), new GuiViewFrame(model));
    this.kh = new KeyboardHandler();
    this.timer = new Timer();

    int t = model.getTempo() / 1000;
    timer.schedule(new AdvanceTime(), 100, t);
    timer.schedule(new Record(), 0, t);

    //TODO

    this.kh.addTypedEvent(65, new AddNewNote()); //       'a'
    this.kh.addTypedEvent(69, new ExtendNote()); //    'e'
    this.kh.addTypedEvent(45, new LowerNote()); //     '-'
    this.kh.addTypedEvent(521, new RaiseNote()); //    '+'
    this.kh.addTypedEvent(83, new ShortenNote()); //   's'
    this.kh.addTypedEvent(46, new Play()); //      '.'
    this.kh.addTypedEvent(47, new Pause()); //     '/'
    this.kh.addTypedEvent(44, new Rewind()); //    ','
    this.view.addListener(this.kh);
  }

  /**
   * Initializes this controller by initializing the view
   *
   * @throws InvalidMidiDataException
   */
  public void initialize() throws InvalidMidiDataException {
    this.view.initialize();
  }
  //TODO
  public class AddNewNote implements Runnable {

    public void run() {
      int pitch = model.getCurPitch();
      int beat = model.getCurBeat();
      if (beat != -1 && pitch != -1) {
        model.addNote(new PitchImpl(pitch), beat, beat + 2, 1, 80);
        model.setCurPitch(-1);
        model.setCurBeat(-1);
      }
      view.paintAgain();
    }
  }

  public class ExtendNote implements Runnable {

    public void run() {
      int pitch = model.getCurPitch();
      int beat = model.getCurBeat();
      try {
        Note n = model.getNoteIn(new PitchImpl(pitch), beat);
        model.editNoteEndTime(new PitchImpl(pitch), n.getStartTime(),
            n.getEndTime() + 1, n.getInstrument());
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
      }
      view.paintAgain();
    }
  }

  public class ShortenNote implements Runnable {

    public void run() {
      int pitch = model.getCurPitch();
      int beat = model.getCurBeat();
      try {
        Note n = model.getNoteIn(new PitchImpl(pitch), beat);
        model.setCurBeat(n.getStartTime());
        if (n.getStartTime() == n.getEndTime() - 1) {
          // Do nothing
        }
        else {
          model.editNoteEndTime(new PitchImpl(pitch), n.getStartTime(), n.getEndTime() - 1,
              n.getInstrument());
        }
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
      }
      view.paintAgain();
    }
  }

  public class LowerNote implements Runnable {

    public void run() {
      int pitch = model.getCurPitch();
      int beat = model.getCurBeat();
      try {
        Note n = model.getNoteIn(new PitchImpl(pitch), beat);
        if (pitch != 0) {
          model.addNote(new PitchImpl(n.getPitch().getValue() - 1), n.getStartTime(),
              n.getEndTime(), n.getInstrument(), n.getVelocity());
          model.deleteNote(n.getPitch(), n.getStartTime(), n.getInstrument());
          model.setCurPitch(pitch - 1);
        }
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
      }
      view.paintAgain();
    }
  }

  public class RaiseNote implements Runnable {

    public void run() {
      int pitch = model.getCurPitch();
      int beat = model.getCurBeat();
      try {
        Note n = model.getNoteIn(new PitchImpl(pitch), beat);
        if (pitch != 127) {
          model.addNote(new PitchImpl(n.getPitch().getValue() + 1), n.getStartTime(),
              n.getEndTime(), n.getInstrument(), n.getVelocity());
          model.deleteNote(n.getPitch(), n.getStartTime(), n.getInstrument());
          model.setCurPitch(pitch + 1);
        }
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
      }
      view.paintAgain();
    }
  }

  public class Record extends TimerTask {

    public void run() {
      try {
        view.recordNotes(model.getTimeStamp());
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
    }
  }

  public class AdvanceTime extends TimerTask {

    public void run() {
      model.advanceTimestamp();
      System.out.println(model.getTimeStamp());
    }
  }

  public class Play implements Runnable {

    public void run() {
      try {
        view.play();
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
    }
  }

  public class Pause implements Runnable {
    public void run() {
      view.pause();
    }
  }

  public class Rewind implements Runnable {
    public void run() {
      view.rewind();
    }
  }

  public class PlayNext extends TimerTask {

    /**
     * The action to be performed by this timer task.
     */
    @Override public void run() {
      model.advanceTimestamp();
      view.paintAgain();
      System.out.println(model.getTimeStamp());
    }
  }
}
