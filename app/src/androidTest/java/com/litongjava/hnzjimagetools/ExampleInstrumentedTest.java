package com.litongjava.hnzjimagetools;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Test
  public void useAppContext() {

    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();
    log.info("appContext:{}", appContext);
    Assert.assertEquals("com.litongjava.hnzjimagetools", appContext.getPackageName());
  }
}
