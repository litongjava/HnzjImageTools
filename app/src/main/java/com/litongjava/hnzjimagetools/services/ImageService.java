package com.litongjava.hnzjimagetools.services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.blankj.utilcode.util.ThreadUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.litongjava.android.utils.bitmap.BitmapSaveUtils;
import com.litongjava.android.utils.bitmap.BitmapUtils;
import com.litongjava.android.utils.bitmap.ImageViewUtils;
import com.litongjava.android.utils.dialog.AlertDialogUtils;
import com.litongjava.android.utils.intent.IntentUtils;
import com.litongjava.android.utils.toast.ToastUtils;
import com.litongjava.hnzjimagetools.constats.RequestCodes;
import com.litongjava.hnzjimagetools.fragment.ImageMainFragment;
import com.litongjava.hnzjimagetools.utils.ShareUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ImageService {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  //图片地址
  private Uri uri;
  //activity
  private FragmentActivity activity;
  //ImageView
  private ImageView imageView;
  /**
   * 合成之后保存的文件路径
   */
  private File savedFile;


  private void updateImage() {
    imageView.setImageURI(uri);
  }

  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      // Permission Granted 用户允许权限 继续执行
      updateImage();
    } else {
      // Permission Denied 拒绝
      ToastUtils.defaultToast(activity, "获取权限失败");
    }
  }

  /**
   * 合成文件
   * @param fragment
   * @param imageDst
   * @param text
   * @param datas
   */
  public void merge(final Fragment fragment, final ImageView imageDst, final String text, final ArrayList<String> datas) {
    savedFile=null;
    final int size = datas.size();
    //使用线程池执行
    ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
      List<Bitmap> list = new ArrayList<>();

      @Override
      public Void doInBackground() throws Throwable {
        for (int i = 0; i < size - 1; i++) {
          String url = datas.get(i);
          log.info("合并图片:{}=>{}", i, url);
          try {
            Bitmap bitmap = Glide.with(fragment.getActivity())
              .load(url)
              .asBitmap()
              .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
              .get();
            list.add(bitmap);
          } catch (ExecutionException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        return null;
      }

      @Override
      public void onSuccess(Void result) {
        Bitmap dstBitmap = BitmapUtils.merge(text, list);
        imageDst.setImageBitmap(dstBitmap);
      }
    });
  }

  /**
   * 保存图片
   *
   * @param fragment
   * @param imageDst
   */
  public File save(Fragment fragment, ImageView imageDst) {
    ToastUtils.defaultToast(fragment.getContext(),"开始保存,请稍后");
//    //判断imageDst中是否有图片,如果有图片drawableState有具体的值,如果没有图片getConstantState()返回空指针异常
//    try {
    //测试耗时太长,放弃
//      Drawable.ConstantState drawableState = imageDst.getDrawable().getCurrent().getConstantState();
//      //log.info("drawableState:{}",drawableState);
//    } catch (Exception e) {
//      AlertDialogUtils.showWaringDialog(fragment.getActivity(), "请先合成图片");
//      return null;
//    }

    // 计算文件名
    //通过UUID生成字符串文件名
    String fileBaseName = UUID.randomUUID().toString();
    // 保存图片
    Bitmap bitmap=null;
    try{
      bitmap = ImageViewUtils.getBitmap(imageDst);
    }catch (Exception e){
      ToastUtils.defaultToast(fragment.getActivity(), "保存成功,请到相册查看");
      return null;
    }

    File file = BitmapSaveUtils.saveToLocal(bitmap, fileBaseName);
    ToastUtils.defaultToast(fragment.getActivity(), "保存成功,请到相册查看");
    // 发送广播,扫描更新
    IntentUtils.scanFile(fragment.getActivity(), file);
    return file;

  }

  /**
   * 分享文件到QQ
   * @param fragment
   * @param imageDst
   */
  public void share(Fragment fragment, ImageView imageDst) {
    if(savedFile==null){
      //先合成文件
      savedFile=this.save(fragment,imageDst);
    }
    ShareUtils.sharePictureToQQFriend(fragment.getContext(),savedFile);
  }

  /**
   * 合成,保存并分享文件
   * @param fragment
   * @param imageDst
   */
  public void saveAndShare(final Fragment fragment, final ImageView imageDst, final String text, final ArrayList<String> datas) {
    //判断imageDst中是否有图片,如果有图片drawableState有具体的值,如果没有图片getConstantState()返回空指针异常
//    try {
//      Drawable.ConstantState drawableState = imageDst.getDrawable().getCurrent().getConstantState();
//      //log.info("drawableState:{}",drawableState);
//    } catch (Exception e) {
//
//
//    }
    //合成文件
//    this.merge(fragment,imageDst,text,datas);
    //设置为null,防止合成出现一些问题
    savedFile=null;
    final int size = datas.size();
    //使用线程池执行
    ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
      List<Bitmap> list = new ArrayList<>();

      @Override
      public Void doInBackground() throws Throwable {
        for (int i = 0; i < size - 1; i++) {
          String url = datas.get(i);
          log.info("合并图片:{}=>{}", i, url);
          try {
            Bitmap bitmap = Glide.with(fragment.getActivity())
              .load(url)
              .asBitmap()
              .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
              .get();
            list.add(bitmap);
          } catch (ExecutionException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        return null;
      }

      @Override
      public void onSuccess(Void result) {
        Bitmap dstBitmap = BitmapUtils.merge(text, list);
        imageDst.setImageBitmap(dstBitmap);
        //保存并分享文件
        share(fragment,imageDst);
      }
    });
  }
}
