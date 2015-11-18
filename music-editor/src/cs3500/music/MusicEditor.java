package cs3500.music;


import cs3500.music.model.*;
import cs3500.music.view.*;
import cs3500.music.util.*;
import cs3500.music.controller.*;


import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import javax.sound.midi.InvalidMidiDataException;

/**
 * Represents the music editor view
 */
public class MusicEditor {

  /**
   * Runs the program automatically
   *
   * @param args
   * @throws IOException
   * @throws InvalidMidiDataException
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {

    //Saves the first string to be typed as the file to be played
    String file = args[0];

    //Saves the next string to be typed as the type of view to be used
    String viewType = args[1];

    // Reads and parses the file and saves the info to the model
    BufferedReader f = new BufferedReader(new FileReader(file));
    Model m = MusicReader.parseFile(f, new MusicModelBuilder());

    // Creates a builder to set the model and view type
    ViewBuilder builder = new ViewBuilderImpl();
    builder.setModel(m).setView(viewType);

    // Creates new view according to the user input
    View view = builder.build();

    ControllerImpl controller = new ControllerImpl((GuiView)view);

    // runs the view
    controller.initialize();
  }
}
