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
  private Synthesizer synth;

  /**
   * Receiver object that receives playback messages
   */
  private Receiver receiver;

  /**
   * Allows for the playback of multiple notes in multiple channels
   */
  private Sequencer seq;

  /**
   * The model that this view is playing
   */
  private final Model m;

  private boolean paused;

  /**
   * Whether or not to create a mock MidiDevice
   */
  private boolean mock;

  /**
   * The output produced by a mock receiver
   */
  private String output;

  /**
   * Creates a new Midi view with playback
   *
   * @param m
   */
  public MidiViewImpl(Model m) throws InvalidMidiDataException {
    this(m, false);
  }

  /**
   * Creates a new Midi view with either playback or testing
   *
   * @param m the model that this view will play back
   * @param mock whether or not to create a mock MidiDevice
   */
  public MidiViewImpl(Model m, boolean mock) throws InvalidMidiDataException {
    this.m = Objects.requireNonNull(m);
    Synthesizer s = null;
    Sequencer sq = null;
    Receiver r = null;
    this.paused = true;
    this.mock = mock;
    this.output = "";
    if (!mock) {
      try {
        s = MidiSystem.getSynthesizer();
        sq = MidiSystem.getSequencer();
        r = s.getReceiver();
        s.open();
        sq.open();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
      synth = s;
      receiver = r;
      seq = sq;
    } else {
      try {
        s = new MockMidiDevice();
        r = s.getReceiver();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
      synth = s;
      receiver = r;
      seq = null;
    }

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
    // sets each channel to its respective instrument
    if (!this.mock) {
      MidiChannel[] chan = synth.getChannels();
      for (int i = 1; i < 16; i++) {
        MidiChannel mc = chan[i];
        mc.programChange(i);
      }
    }

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

  public void recordNotes(int time) throws InvalidMidiDataException, MidiUnavailableException {
    receiver = synth.getReceiver();
    MidiChannel[] chan = synth.getChannels();
    int t = m.getTempo();
    List<Note> offs = m.getEndNotesAtTime(time);
    for (Note n : offs) {
      chan[n.getInstrument() - 1].noteOff(n.getPitch().getValue(), n.getVelocity());
    }

    List<Note> ons = m.getNotesAtTime(time);
    for (Note n : ons) {
      chan[n.getInstrument() - 1].noteOn(n.getPitch().getValue(), n.getVelocity());
    }

    receiver.close();
  }

  @Override public void pause() {
    MidiChannel[] chan = synth.getChannels();
    for (MidiChannel mc : chan) {
      mc.allNotesOff();
    }
    paused = true;
  }

  public boolean isPaused() {
    return this.paused;
  }
}

