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

  private boolean playing;

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
    this.playing = false;

    int t = model.getTempo() / 1000;
    timer.schedule(new Record(), 0, t);

    //TODO

    this.kh.addTypedEvent(65, new AddNewNote()); //       'a'
    this.kh.addTypedEvent(8, new DeleteNote()); //     'delete'
    this.kh.addTypedEvent(69, new ExtendNote()); //    'e'
    this.kh.addTypedEvent(45, new LowerNote()); //     '-'
    this.kh.addTypedEvent(521, new RaiseNote()); //    '+'
    this.kh.addTypedEvent(83, new ShortenNote()); //   's'
    this.kh.addTypedEvent(46, new Play()); //      '.'
    this.kh.addTypedEvent(47, new Pause()); //     '/'
    this.kh.addTypedEvent(44, new Rewind()); //    ','
    this.kh.addTypedEvent(77, new MoveNoteRight()); // 'm'
    this.kh.addTypedEvent(78, new MoveNoteLeft()); //  'n'
    this.kh.addPressedEvent(37, new MoveScreenLeft()); //  'left'
    this.kh.addPressedEvent(38, new MoveScreenUp()); // 'up'
    this.kh.addPressedEvent(39, new MoveScreenRight()); // 'right'
    this.kh.addPressedEvent(40, new MoveScreenDown()); // 'down
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

  public class DeleteNote implements Runnable {

    public void run() {
      int pitch = model.getCurPitch();
      int beat = model.getCurBeat();
      if (beat != -1 && pitch != -1) {
        try {
          Note n = model.getNoteIn(new PitchImpl(pitch), beat);
          model.deleteNote(n.getPitch(), n.getStartTime(), n.getInstrument());
          model.setCurBeat(-1);
        } catch (Model.IllegalAccessNoteException e) {
          //do nothing
        }
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
        model.editNoteEndTime(new PitchImpl(pitch), n.getStartTime(), n.getEndTime() + 1,
            n.getInstrument());
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
        } else {
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
      int instrument = model.getCurInstrument();
      Note n;
      try {
        n = model.getNoteIn(new PitchImpl(pitch), beat, instrument);
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
        return;
      }
      try {
        try {
          model.getNoteAt(new PitchImpl(n.getPitch().getValue() - 1),
              n.getStartTime(), instrument);
        }
        catch(Model.IllegalAccessNoteException ex) {
          if (pitch != 0) {
            model.addNote(new PitchImpl(n.getPitch().getValue() - 1), n.getStartTime(),
                n.getEndTime(), n.getInstrument(), n.getVelocity());
            model.deleteNote(n.getPitch(), n.getStartTime(), n.getInstrument());
            model.setCurPitch(pitch - 1);
          }
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
      int instrument = model.getCurInstrument();
      Note n;
      try {
        n = model.getNoteIn(new PitchImpl(pitch), beat, instrument);
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
        return;
      }
      try {
        try {
          model.getNoteAt(new PitchImpl(n.getPitch().getValue() + 1),
              n.getStartTime(), instrument);
        }
        catch(Model.IllegalAccessNoteException ex) {
          if (pitch != 127) {
            model.addNote(new PitchImpl(n.getPitch().getValue() + 1), n.getStartTime(),
                n.getEndTime(), n.getInstrument(), n.getVelocity());
            model.deleteNote(n.getPitch(), n.getStartTime(), n.getInstrument());
            model.setCurPitch(pitch + 1);
          }
        }
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
      }
      view.paintAgain();
    }
  }


  public class Record extends TimerTask {

    public void run() {
      if (playing && model.getTimeStamp() < model.getFinalEndBeat()) {
        view.paintAgain();
        try {
          view.recordNotes(model.getTimeStamp());
        } catch (InvalidMidiDataException e) {
          e.printStackTrace();
        } catch (MidiUnavailableException e) {
          e.printStackTrace();
        }

        model.advanceTimestamp();
        System.out.println(model.getTimeStamp());
      }
    }
  }


  public class Play implements Runnable {

    public void run() {
      playing = true;
      try {
        view.play();
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }

    }
  }

  public class Pause implements Runnable {
    public void run() {
      playing = false;
      view.pause();

    }
  }

  public class Rewind implements Runnable {
    public void run() {
      model.resetTimestamp();
      view.rewind();
      view.paintAgain();
    }
  }


  public class MoveNoteLeft implements Runnable {
    public void run() {
      int pitch = model.getCurPitch();
      int beat = model.getCurBeat();
      int instrument = model.getCurInstrument();
      Note n;
      try {
        n = model.getNoteIn(new PitchImpl(pitch), beat, instrument);
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
        return;
      }

      try {
        if (n.getStartTime() != 0) {
          try {
            model.getNoteAt(new PitchImpl(pitch), n.getStartTime() - 1, instrument);
          }
          catch (Model.IllegalAccessNoteException ex) {
            model.deleteNote(new PitchImpl(pitch), n.getStartTime(), n.getInstrument());
            model.addNote(new PitchImpl(pitch), n.getStartTime() - 1, n.getEndTime() - 1, n.getInstrument(), n.getVelocity());
            model.setCurBeat(n.getEndTime() - 2);
            view.paintAgain();
          }
        }
      }
      catch (Model.IllegalAddException ex) {
        //do nothing
        return;
      }
      catch (IllegalArgumentException ex) {
        //do nothing
        return;
      }
    }
  }


  public class MoveNoteRight implements Runnable {
    public void run() {
      int pitch = model.getCurPitch();
      int beat = model.getCurBeat();
      int instrument = model.getCurInstrument();
      Note n;
      try {
        n = model.getNoteIn(new PitchImpl(pitch), beat, instrument);
      } catch (Model.IllegalAccessNoteException ex) {
        //do nothing
        return;
      }
      try {
        try {
          model.getNoteAt(new PitchImpl(pitch), n.getStartTime() + 1, instrument);
        }
        catch(Model.IllegalAccessNoteException ex) {
          model.deleteNote(new PitchImpl(pitch), n.getStartTime(), n.getInstrument());
          model.addNote(new PitchImpl(pitch), n.getStartTime() + 1, n.getEndTime() + 1,
              n.getInstrument(), n.getVelocity());
          model.setCurBeat(n.getEndTime());
          view.paintAgain();
        }
      }
      catch (Model.IllegalAddException ex) {
        //do nothing
        return;
      }
      catch (IllegalArgumentException ex) {
        //do nothing
        return;
      }
    }
  }

  public class MoveScreenLeft implements Runnable {

    public void run() {
      view.shiftLeft();
    }
  }

  public class MoveScreenRight implements Runnable {

    public void run() {
      view.shiftRight();
    }
  }

  public class MoveScreenUp implements Runnable {

    public void run() {
      view.shiftUp();
    }
  }

  public class MoveScreenDown implements Runnable {

    public void run() {
      view.shiftDown();
    }
  }


}
