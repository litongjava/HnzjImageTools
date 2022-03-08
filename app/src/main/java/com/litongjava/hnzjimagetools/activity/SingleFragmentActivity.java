package com.litongjava.hnzjimagetools.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.litongjava.hnzjimagetools.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SingleFragmentActivity extends FragmentActivity {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  protected abstract Fragment createFragment();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);

    //创建一个新的fragment事务，执行一个fragment添加操作，然后提交该事务
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragment_container);
    /**
     * 为什么要获取的fragment可能有了呢？设备旋转或回收内存时，Android系统会销
     毁CrimeActivity，而后重建时，会调用CrimeActivity.onCreate(Bundle)方法。activity被
     销毁时，它的FragmentManager会将fragment队列保存下来。这样，activity重建时，新的
     FragmentManager会首先获取保存的队列，然后重建fragment队列，从而恢复到原来的状态。
     */
    if (fragment == null) {
      fragment = createFragment();
      log.info("add fragment:{}", fragment);
      fm.beginTransaction()
        //参数一：容器视图资源ID 参数二：创建的fragment
        .add(R.id.fragment_container, fragment)
        .commit();
    }
  }
}