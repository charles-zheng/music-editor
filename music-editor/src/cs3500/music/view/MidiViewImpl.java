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
        r = sq.getReceiver();
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

    seq.startRecording();
    /*for (int i = 20; i < 100; i++) {
      try {
        recordNotes(i);
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
    }*/

    /*
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
    seq.stopRecording();*/

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

  public void recordNotes(int time) throws InvalidMidiDataException, MidiUnavailableException{
    receiver = seq.getReceiver();
    int t = m.getTempo();
    List<Note> ns = m.getNotesAtTime(time);
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
    receiver.close();
  }

  @Override public void pause() {
    paused = true;
    seq.stop();
  }

  @Override public void play() throws InvalidMidiDataException {
    if (paused) {
      long time = seq.getTickPosition();
     // initialize();
      seq.setTickPosition(time);
      seq.start();
    }
    paused = false;
  }

  @Override public void rewind() {
    paused = true;
    seq.stop();
    seq.setTickPosition(0);
  }

  public boolean isPaused() {
    return this.paused;
  }
}
