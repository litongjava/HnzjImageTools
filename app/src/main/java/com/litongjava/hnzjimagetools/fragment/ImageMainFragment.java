package com.litongjava.hnzjimagetools.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.litongjava.android.utils.dialog.AlertDialogUtils;
import com.litongjava.android.view.inject.annotation.FindViewById;
import com.litongjava.android.view.inject.annotation.OnClick;
import com.litongjava.android.view.inject.utils.ViewInjectUtils;
import com.litongjava.hnzjimagetools.R;
import com.litongjava.hnzjimagetools.adapter.RecyclerViewPhotosAdapter;
import com.litongjava.hnzjimagetools.callback.MyItemTouchHelperCallback;
import com.litongjava.hnzjimagetools.constats.RequestCodes;
import com.litongjava.hnzjimagetools.services.ImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

public class ImageMainFragment extends Fragment {
  private Logger log = LoggerFactory.getLogger(this.getClass());
  //最大选择几张照片
  public static final int maxPhoto = 9;

  @FindViewById(R.id.imageDst)
  private ImageView imageDst;

  @FindViewById(R.id.textText)
  private EditText textText;

  @FindViewById(R.id.btnCompound)
  private Button btnCompound;

  @FindViewById(R.id.btnSave)
  public Button btnSave;
  /**
   * 图片区域
   */
  @FindViewById(R.id.recyclerViewPhotos)
  private RecyclerView recyclerViewPhotos;
  private RecyclerViewPhotosAdapter mPhotoAdapter;

  private ImageService imageService = new ImageService();

  public static ImageMainFragment newInstance() {
    return new ImageMainFragment();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_image_main, container, false);
    ViewInjectUtils.injectView(view, this);
    ViewInjectUtils.injectOnClick(view, this);
    initView();
    initData();
    initListener();
    return view;
  }

  private void initView() {
    //设置布局,最大显示三个,水平显示
    recyclerViewPhotos.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    //设置识别器
    mPhotoAdapter = new RecyclerViewPhotosAdapter(getActivity(), this);
    recyclerViewPhotos.setAdapter(mPhotoAdapter);

    //attachToRecyclerView
    MyItemTouchHelperCallback callBack = new MyItemTouchHelperCallback(mPhotoAdapter);
    ItemTouchHelper touchHelper = new ItemTouchHelper(callBack);
    touchHelper.attachToRecyclerView(recyclerViewPhotos);
  }

  private void initData() {
  }

  private void initListener() {
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    log.info("onActivityResult");
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == PhotoPicker.REQUEST_CODE) {
        //用户新选择了图片
        if (data != null) {
          List<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
          //修改Datas的值
          ArrayList<String> datas = mPhotoAdapter.getDatas();
          datas.addAll(datas.size() - 1, photos);
          log.info("notifyDataSetChanged");
          mPhotoAdapter.notifyDataSetChanged();
        }
      }
      //预览返回
      else if (requestCode == PhotoPicker.REQUEST_CODE) {

      }
    }
  }

  //回调函数，无论用户是否允许都会调用执行此方法
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == RequestCodes.REQUEST_CODE_ASK_PERMISSIONS) {
      imageService.onRequestPermissionsResult(requestCode, permissions, grantResults);
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }


  @OnClick(R.id.btnCompound)
  public void btnCompoundOnClick(View view) {
    log.info("btnCompound");
    Editable editable = textText.getText();
    String text = editable.toString();
    //获取图片列表,最后一个元素的值是1
    ArrayList<String> datas = mPhotoAdapter.getDatas();
    //因为最后一个元素是"1",表示添加图片选择框
    if(datas.size()>1){
      imageService.merge(this, imageDst, text, datas);
    }else{
      AlertDialogUtils.showWaringDialog(getActivity(),"请添加图片");
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @OnClick(R.id.btnSave)
  public void btnSaveOnClick(View view) {
    imageService.save(this, imageDst);
  }

  @OnClick(R.id.btnShare)
  public void btnShare_OnClick(View view){
    imageService.share(this,imageDst);
  }
  @OnClick(R.id.btnSaveAndShare)
  public void btnSaveAndShareOnClick(View view){
    Editable editable = textText.getText();
    String text = editable.toString();
    ArrayList<String> datas = mPhotoAdapter.getDatas();
    imageService.saveAndShare(this, imageDst,text,datas);
  }
}