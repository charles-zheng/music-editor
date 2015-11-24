package cs3500.music;


import cs3500.music.model.*;
import cs3500.music.view.*;
import cs3500.music.util.*;
import cs3500.music.controller.*;
import jdk.internal.util.xml.impl.Pair;


import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import javax.sound.midi.InvalidMidiDataException;

/**
 * Represents the music editor view
 */
public class MusicEditor {

  /**
   * Runs the program automatically.
   *
   * @param args the command line arguments.
   * @throws IOException if the input data is invalid
   * @throws InvalidMidiDataException if the midi data is invalid
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {

    // The file that will be played.
    String file;

    // The type of view to be displayed.
    String viewType;

    //Saves the first string to be typed as the file to be played
    try {
      file = args[0];
    }
    catch (IndexOutOfBoundsException ex) {
      System.out.println("You need to input a file and a view.");
      return;
    }

    //Saves the next string to be typed as the type of view to be used
    try {
      viewType = args[1];
    }
    catch (IndexOutOfBoundsException ex) {
      System.out.println("You need to input a file and a view.");
      return;
    }

    // Reads and parses the file and saves the info to the model
    BufferedReader f = new BufferedReader(new FileReader(file));
    Model m = MusicReader.parseFile(f, new MusicModelBuilder());

    // Creates a builder to set the model and view type
    ViewBuilder builder = new ViewBuilderImpl();

    try {
      builder.setModel(m).setView(viewType);
    }
    catch (IllegalArgumentException ex) {
      System.out.println("Please input a valid view: visual, console, midi, or composite");
    }

    // Creates new view according to the user input
    View view = builder.build();

    Controller controller = new ControllerImpl(m);

    // runs the view
    controller.initialize();
  }
}
