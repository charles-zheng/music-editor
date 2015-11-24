package cs3500.music;

import cs3500.music.controller.KeyboardHandler;
import org.junit.Test;

import javax.swing.plaf.basic.BasicTableUI;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.*;
import static org.junit.Assert.assertEquals;
/**
 * Represents the tests for Key Events
 */
public class KeyTest extends AbstractViewTest {

  @Test
  public void testKey1() {
    KeyboardHandler kh = new KeyboardHandler();
    kh.addTypedEvent(84, kh.new TestKeyHandler());// 't'
    kh.keyTyped(new KeyEvent(new Button(), KeyEvent.VK_T, KeyEvent.VK_T,
        KeyEvent.VK_T, KeyEvent.VK_T, 't'));
    assertEquals(kh.getOutput(), "Key typed: ");
  }
}
