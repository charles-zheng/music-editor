package cs3500.music.view;

import javax.sound.midi.*;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Objects;
import java.io.*;

import cs3500.music.model.Model;
import cs3500.music.model.Note;

/**
 * Represents the MIDI playback view
 */
public final class MidiViewImpl implements MidiView {

  /**
   * Synthesizer object that creates sounds
   */
 // private final Synthesizer synth;

  /**
   * Receiver object that receives playback messages
   */
  private final Receiver receiver;

  /**
   * Allows for the playback of multiple notes in multiple channels
   */
  private final Sequencer seq;

  /**
   * The model that this view is playing
   */
  private final Model m;

  /**
   * Whether or not to create a mock MidiDevice
   */
  private final boolean mock;

  /**
   * The output produced by a mock receiver
   */
  private String output;

  /**
   * Creates a new Midi view with playback
   *
   * @param m
   */
  public MidiViewImpl(Model m) {
    this(m, false);
  }

  /**
   * Creates a new Midi view with either playback or testing
   *
   * @param m the model that this view will play back
   * @param mock whether or not to create a mock MidiDevice
   */
  public MidiViewImpl(Model m, boolean mock) {
    this.m = Objects.requireNonNull(m);
    Synthesizer s = null;
    Sequencer sq = null;
    Receiver r = null;
    this.mock = mock;
    this.output = "";
    if (!mock) {
      try {
        s = MidiSystem.getSynthesizer();
        sq = MidiSystem.getSequencer();
        r = sq.getReceiver();
        s.open();
        sq.open();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
     // synth = s;
      receiver = r;
      seq = sq;
    } else {
      try {
        s = new MockMidiDevice();
        r = s.getReceiver();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
      //synth = s;
      receiver = r;
      seq = null;
    }
  }


  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  /**
   * Plays the midi view
   *
   * @throws InvalidMidiDataException if the given data is not valid in Midi
   */
  public void initialize() throws InvalidMidiDataException {

    Sequence mySeq = null;
    try{
      mySeq = new Sequence(Sequence.PPQ, 20, 16);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    seq.setSequence(mySeq);
    for (int i = 0; i < mySeq.getTracks().length; i++) {
      seq.recordEnable(mySeq.getTracks()[i], i);
    }
    seq.startRecording();

    int t = m.getTempo();
    for (int i = 0; i <= m.getFinalStartBeat(); i++) {
      List<Note> ns = m.getNotesAtTime(i);
      for (Note n : ns) {
        MidiMessage start =
            new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1, n.getPitch().getValue(),
                n.getVelocity());
        MidiMessage stop =
            new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1, n.getPitch().getValue(),
                n.getVelocity());
        receiver.send(start, t * n.getStartTime());
        receiver.send(stop, t * (n.getStartTime() + (n.getEndTime() - n.getStartTime())));
      }
    }
    receiver.close();

    seq.stopRecording();
    seq.setTickPosition(0);

    // for testing purposes, store the output stream in a string field
    if (this.mock) {
      System.out.print(((MockReceiver) receiver).output());
      // Create a stream to hold the output
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(out);
      // IMPORTANT: Save the old System.out!
      PrintStream old = System.out;
      System.setOut(ps);
      System.out.print(((MockReceiver) receiver).output());
      // Put things back
      System.out.flush();
      System.setOut(old);

      this.output = out.toString();
      //System.out.println("Here: " + baos.toString());
    }
  }

  public String getOutput() {
    return this.output;
  }

  public void pauseSong() {
    seq.stop();
  }

  public void playSong() {
    seq.start();
  }

  public void rewindSong() {
    seq.stop();
    seq.setTickPosition(0);
    seq.start();
  }

  public Runnable play() {
    return new Play();
  }

  public Runnable pause() {
    return new Pause();
  }

  public Runnable rewind() {
    return new Rewind();
  }

  public class Play implements Runnable {

    public void run() {
      playSong();
    }
  }

  public class Pause implements Runnable {
    public void run() {
      pauseSong();
    }
  }

  public class Rewind implements Runnable {
    public void run() {
      rewindSong();
    }
  }
}

