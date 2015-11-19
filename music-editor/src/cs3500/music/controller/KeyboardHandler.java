package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Listens to and handles key events
 */
public class KeyboardHandler implements KeyListener {

  /**
   * Represents all the events that should happen when a key is types
   * mapped to the key's int representation
   */
  private Map<Integer, Runnable> typed;

  /**
   * Represents all the events that should happen when a key is pressed
   * mapped to the key's int representation
   */
  private Map<Integer, Runnable> pressed;

  /**
   * Represents all the events that should happen when a key is released
   * mapped to the key's int representation
   */
  private Map<Integer, Runnable> released;

  /**
   * Makes a new KeyBoard handler. Initializes all the fields to empty
   */
  public KeyboardHandler() {
    this.typed = new HashMap<Integer, Runnable>();
    this.pressed = new HashMap<Integer, Runnable>();
    this.released = new HashMap<Integer, Runnable>();
  }

  /**
   *
   *
   * @param e
   */
  @Override public void keyTyped(KeyEvent e) {
    if (this.typed.containsKey(e.getExtendedKeyCode())) {
      this.typed.get(e.getExtendedKeyCode()).run();
    }
    displayInfo(e, "KEY TYPED: ");
  }

  @Override public void keyPressed(KeyEvent e) {
    if (this.pressed.containsKey(e.getExtendedKeyCode())) {
      this.pressed.get(e.getExtendedKeyCode()).run();
    }
  }

  @Override public void keyReleased(KeyEvent e) {
    if (this.released.containsKey(e.getExtendedKeyCode())) {
      this.released.get(e.getExtendedKeyCode()).run();
    }
  }

  public void addTypedEvent(int e, Runnable r) {
    this.typed.put(e, r);
  }

  public void addPressedEvent(int e, Runnable r) {
    this.pressed.put(e, r);
  }

  public void addReleasedEvent(int e, Runnable r) {
    this.released.put(e, r);
  }

  private void displayInfo(KeyEvent e, String keyStatus){

    //You should only rely on the key char if the event
    //is a key typed event.
    int id = e.getID();
    String keyString;
    if (id == KeyEvent.KEY_TYPED) {
      char c = e.getKeyChar();
      keyString = "key character = '" + c + "'";
    } else {
      int keyCode = e.getKeyCode();
      keyString = "key code = " + keyCode
          + " ("
          + KeyEvent.getKeyText(keyCode)
          + ")";
    }


    System.out.println(keyString);
    System.out.println(e.getExtendedKeyCode());
  }
}
