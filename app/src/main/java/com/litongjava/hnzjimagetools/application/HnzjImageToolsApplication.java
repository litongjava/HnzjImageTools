package com.litongjava.hnzjimagetools.application;

import android.app.Application;

import com.hss01248.glidepicker.GlideIniter;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.iwf.photopicker.PhotoPickUtils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022/2/26
 */
public class HnzjImageToolsApplication extends TinkerApplication {

  private Logger log= LoggerFactory.getLogger(this.getClass());

  public HnzjImageToolsApplication(){
    /**
     * 初始化bugly
     * <pre>
     * 参数解析：
     * 参数1：int tinkerFlags 表示Tinker支持的类型 dex only、library only or all suuport，default: TINKER_ENABLE_ALL
     * 参数2：String delegateClassName Application代理类 这里填写你自定义的ApplicationLike
     * 参数3：String loaderClassName  Tinker的加载器，使用默认即可
     * 参数4：boolean tinkerLoadVerifyFlag  加载dex或者lib是否验证md5，默认为false
     * </pre>
     */
    super(ShareConstants.TINKER_ENABLE_ALL,
      "com.litongjava.hnzjimagetools.application.SampleApplicationLike",
      "com.tencent.tinker.loader.TinkerLoader",
      false, true);
  }
  @Override
  public void onCreate() {
    //初始化PhotoPicker
    PhotoPickUtils.init(getApplicationContext(), new GlideIniter());
    super.onCreate();
  }
}