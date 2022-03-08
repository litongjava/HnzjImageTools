package com.litongjava.hnzjimagetools.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;


import com.blankj.utilcode.util.ToastUtils;
import com.litongjava.android.utils.android.AndroidUtils;
import com.litongjava.android.utils.share.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ShareUtils {
  private static String mesage_01 = "请先安装微信客户端";
  private static String mesage_02 = "请先安装QQ客户端";

  /**
   * 分享文本到微信好友
   *
   * @param context context
   * @param content 需要分享的文本
   */
  public static void shareTextToWechatFriend(Context context, String content) {
    if (AndroidUtils.hasAPP(context, Constants.PACKAGE_WECHAT)) {
      Intent intent = new Intent();
      ComponentName cop = new ComponentName(Constants.PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareImgUI");
      intent.setComponent(cop);
      intent.setAction(Intent.ACTION_SEND);
      intent.putExtra(Intent.EXTRA_TEXT, content);
      intent.putExtra("Kdescription", "shareTextToWechatFriend");
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } else {
      ToastUtils.showLong(mesage_01);
    }
  }

  /**
   * 分享单张图片到微信好友
   *
   * @param context context
   * @param picFile 要分享的图片文件
   */
  public static void sharePictureToWechatFriend(Context context, File picFile) {
    if (AndroidUtils.hasAPP(context, Constants.PACKAGE_WECHAT)) {
      Intent intent = new Intent();
      ComponentName cop = new ComponentName(Constants.PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareImgUI");
      intent.setComponent(cop);
      intent.setAction(Intent.ACTION_SEND);
      intent.setType("image/*");
      if (picFile != null) {
        if (picFile.isFile() && picFile.exists()) {
          Uri uri = Uri.fromFile(picFile);
          intent.putExtra(Intent.EXTRA_STREAM, uri);
        }
      }
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(Intent.createChooser(intent, "sharePictureToWechatFriend"));
    } else {
      ToastUtils.showLong(mesage_01);
    }
  }


  /**
   * 分享单张图片到QQ好友
   *
   * @param context conrtext
   * @param picFile 要分享的图片文件
   */
  public static void sharePictureToQQFriend(Context context, File picFile) {
    if (AndroidUtils.hasAPP(context, Constants.PACKAGE_MOBILE_QQ)) {
      Intent shareIntent = new Intent();
      ComponentName componentName = new ComponentName(Constants.PACKAGE_MOBILE_QQ,
        "com.tencent.mobileqq.activity.JumpActivity");
      shareIntent.setComponent(componentName);
      shareIntent.setAction(Intent.ACTION_SEND);
      shareIntent.setType("image/*");
      String packageName = context.getPackageName();

      //将file协议的文件转为content协议的文件
      Uri uri = FileProvider.getUriForFile(context,  packageName+".fileprovider", picFile);

      shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
      shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

      // 遍历所有支持发送图片的应用。找到需要的应用
      context.startActivity(Intent.createChooser(shareIntent, "shareImageToQQFriend"));
    } else {
      ToastUtils.showLong(mesage_02);
    }
  }

  /**
   * 分享文本到QQ好友
   *
   * @param context context
   * @param content 文本
   */
  public static void shareTextToQQFriend(Context context, String content) {
    if (AndroidUtils.hasAPP(context, Constants.PACKAGE_MOBILE_QQ)) {
      Intent intent = new Intent("android.intent.action.SEND");
      intent.setComponent(new ComponentName(Constants.PACKAGE_MOBILE_QQ, "com.tencent.mobileqq.activity.JumpActivity"));
      intent.setType("text/plain");
      intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
      intent.putExtra(Intent.EXTRA_TEXT, content);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } else {

      ToastUtils.showLong(mesage_02);
    }
  }

  /**
   * 分享单张图片到朋友圈
   *
   * @param context context
   * @param picFile 图片文件
   */
  public static void sharePictureToTimeLine(Context context, File picFile) {
    if (AndroidUtils.hasAPP(context, Constants.PACKAGE_WECHAT)) {
      Intent intent = new Intent();
      ComponentName comp = new ComponentName(Constants.PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareToTimeLineUI");
      intent.setComponent(comp);
      intent.setAction(Intent.ACTION_SEND);
      intent.setType("image/*");
      Uri uri = Uri.fromFile(picFile);
      intent.putExtra(Intent.EXTRA_STREAM, uri);
      intent.putExtra("Kdescription", "sharePictureToTimeLine");
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } else {
      ToastUtils.showLong(mesage_01);
    }
  }

  /**
   * 分享多张图片到朋友圈
   *
   * @param context context
   * @param files   图片集合
   */
  public static void shareMultiplePictureToTimeLine(Context context, List<File> files) {
    if (AndroidUtils.hasAPP(context, Constants.PACKAGE_WECHAT)) {
      Intent intent = new Intent();
      ComponentName comp = new ComponentName(Constants.PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareToTimeLineUI");
      intent.setComponent(comp);
      intent.setAction(Intent.ACTION_SEND_MULTIPLE);
      intent.setType("image/*");

      ArrayList<Uri> imageUris = new ArrayList<>();
      for (File f : files) {
        imageUris.add(Uri.fromFile(f));
      }
      intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
      intent.putExtra("Kdescription", "shareMultiplePictureToTimeLine");
      context.startActivity(intent);
    } else {
      ToastUtils.showLong(mesage_02);
    }
  }
}