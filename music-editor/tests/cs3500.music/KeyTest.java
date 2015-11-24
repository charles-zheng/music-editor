package cs3500.music;

import cs3500.music.controller.KeyboardHandler;
import org.junit.Test;

import javax.swing.plaf.basic.BasicTableUI;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests our key handler, making sure that everything is correctly wired
 */
public class KeyTest extends AbstractViewTest {

  @Test
  public void testKey1() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addTypedEvent(84, kh.new TestKeyHandler());// 't'
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, 't');
    kh.keyTyped(e);
    assertEquals(kh.getOutput(), "Ran\nKey typed: t key code = 84");
  }

  @Test
  public void testKey2() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addPressedEvent(521, kh.new TestKeyHandler());
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, '+');
    kh.keyPressed(e);
    assertEquals(kh.getOutput(), "Ran\nKey pressed: + key code = 521");
  }

  @Test
  public void testKey3() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addReleasedEvent(65, kh.new TestKeyHandler());
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, 'a');
    kh.keyReleased(e);
    assertEquals(kh.getOutput(), "Ran\nKey released: a key code = 65");
  }

}
