package cs3500.music;

import cs3500.music.model.*;
import cs3500.music.view.*;
import cs3500.music.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents an abstract test class
 */
public class AbstractViewTest {

  /**
   * The "Mary Had a Little Lamb" model
   */
  protected Model mary;

  /**
   * The Super Mario Bros. model
   */
  protected Model smb;

  /**
   * The Tetris song model
   */
  protected Model tetris;

  /**
   * A self-made test model
   */
  protected Model test1;

  /**
   * A C-major scale model
   */
  protected Model scale;

  /**
   * Initializes all the models to their appropriate format
   * @throws IOException
   */
  protected void initialize() throws IOException {
    BufferedReader maryFile = new BufferedReader(new FileReader("mary-little-lamb.txt"));
    this.mary = MusicReader.parseFile(maryFile, new MusicModelBuilder());
    BufferedReader smbFile = new BufferedReader(new FileReader("mystery-1.txt"));
    this.smb = MusicReader.parseFile(smbFile, new MusicModelBuilder());
    BufferedReader tetrisFile = new BufferedReader(new FileReader("mystery-2.txt"));
    this.tetris = MusicReader.parseFile(tetrisFile, new MusicModelBuilder());
    BufferedReader testFile = new BufferedReader(new FileReader("test-1.txt"));
    this.test1 = MusicReader.parseFile(testFile, new MusicModelBuilder());

    this.scale = new ModelImpl();
    scale.setTempo(100000);
    scale.addNote(new PitchImpl(60), 0, 2, 1, 50);
    scale.addNote(new PitchImpl(62), 2, 4, 1, 58);
    scale.addNote(new PitchImpl(64), 4, 6, 1, 66);
    scale.addNote(new PitchImpl(65), 6, 8, 1, 74);
    scale.addNote(new PitchImpl(67), 8, 10, 1, 82);
    scale.addNote(new PitchImpl(69), 10, 12, 1, 90);
    scale.addNote(new PitchImpl(71), 12, 14, 1, 98);
    scale.addNote(new PitchImpl(72), 14, 16, 1, 106);

  }
}
