package cs3500.music;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Charles on 11/24/15.
 */
public class ViewModelTest extends AbstractViewTest {

  @Test
  public void testTimeStamp1() {
    try {
      initialize();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(this.mary.getTimeStamp(), 0);
    this.mary.advanceTimestamp();
    this.mary.advanceTimestamp();
    this.mary.advanceTimestamp();
    assertEquals(this.mary.getTimeStamp(), 3);
    this.mary.setTimeStamp(5);
    assertEquals(this.mary.getTimeStamp(), 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTimeStamp2() {
    try {
      initialize();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.mary.setTimeStamp(65);
  }

  @Test
  public void testSetCurrent1() {
    try {
      initialize();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.mary.setCurrent(450, 130);
    assertEquals(mary.getCurBeat(), 16);
    assertEquals(mary.getCurPitch(), 63);
  }

  @Test
  public void testSetCurrent2() {
    try {
      initialize();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.mary.setCurrent(10, 175);
    assertEquals(mary.getCurBeat(), -1);
    assertEquals(mary.getCurPitch(), 61);
  }

  @Test
  public void testSetCurrent3() {
    try {
      initialize();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.mary.setCurrent(1038, 3);
    assertEquals(mary.getCurBeat(), 39);
    assertEquals(mary.getCurPitch(), -1);
  }
}
