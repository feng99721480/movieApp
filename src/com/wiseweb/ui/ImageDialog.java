package com.wiseweb.ui;

import com.wiseweb.activity.CinemaDetailActivity;
import com.wiseweb.movie.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ImageDialog extends Dialog {
	private Bitmap bm;
	private ImageView img;
	public ImageDialog(Context context,Bitmap bm) {
		super(context, R.style.ImageloadingDialogStyle);
		this.bm = bm;
		//setOwnerActivity((CinemaDetailActivity) context);// 设置dialog全屏显示
		
	}

//	private ImageDialog(Context context, int theme) {
//		super(context, theme);
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_img);
		img = (ImageView) findViewById(R.id.image);
		img.setImageBitmap(bm);
	
	}
	
}
