package cs3500.music;

import cs3500.music.controller.KeyboardHandler;
import org.junit.Test;

import javax.swing.plaf.basic.BasicTableUI;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.*;
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

  @Test
  public void testKey4() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addReleasedEvent(69, kh.new TestKeyHandler());
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, 'e');
    kh.keyReleased(e);
    assertEquals(kh.getOutput(), "Ran\nKey released: e key code = 69");
  }

  @Test
  public void testKey5() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addReleasedEvent(45, kh.new TestKeyHandler());
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, '-');
    kh.keyReleased(e);
    assertEquals(kh.getOutput(), "Ran\nKey released: - key code = 45");
  }

  @Test
  public void testKey6() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addReleasedEvent(83, kh.new TestKeyHandler());
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, 's');
    kh.keyReleased(e);
    assertEquals(kh.getOutput(), "Ran\nKey released: s key code = 83");
  }

  @Test
  public void testKey7() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addReleasedEvent(46, kh.new TestKeyHandler());
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, '.');
    kh.keyReleased(e);
    assertEquals(kh.getOutput(), "Ran\nKey released: . key code = 46");
  }

  @Test
  public void testKey8() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addReleasedEvent(47, kh.new TestKeyHandler());
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, '/');
    kh.keyReleased(e);
    assertEquals(kh.getOutput(), "Ran\nKey released: / key code = 47");
  }

  @Test
  public void testKey9() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addReleasedEvent(77, kh.new TestKeyHandler());
    KeyEvent e = new KeyEvent(new Button(), 0, 0, 0, 0, 'm');
    kh.keyReleased(e);
    assertEquals(kh.getOutput(), "Ran\nKey released: m key code = 77");
  }
}
