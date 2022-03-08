package com.litongjava.hnzjimagetools;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.litongjava.hnzjimagetools.activity.SingleFragmentActivity;
import com.litongjava.hnzjimagetools.fragment.ImageMainFragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageActivity extends SingleFragmentActivity {

  private Logger log= LoggerFactory.getLogger(this.getClass());
  @Override
  protected Fragment createFragment() {
    return ImageMainFragment.newInstance();
  }
}
