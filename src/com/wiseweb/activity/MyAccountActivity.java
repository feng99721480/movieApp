package com.wiseweb.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyAccountActivity extends Activity implements OnClickListener {
	private static final int NONE = 0;
	private static final int PHOTO_GRAPH = 1;// 拍照
	private static final int PHOTO_ZOOM = 2; // 缩放
	private static final int PHOTO_RESOULT = 3;// 结果
	private static final int MODIFY_NICKNAME = 4;// 改变nickname
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private Button logout;
	private RelativeLayout titleBack;
	private RelativeLayout headLayout;
	private RelativeLayout usernameLayout;
	private RelativeLayout genderLayout;
	private RelativeLayout PwdLayout;
	private RelativeLayout bindLayout;
	private TextView genderTv;
	private TextView nicknameTv;
	private SharedPreferences sp;
	private String nickname;
	private ImageView headImg;
	private File file;
	private byte[] header;
	private UserSQLiteOpenHelper helper;
	private Cursor cursor;
	private Bitmap headerBmp ;
	private String gender;
	private String username;
	private TextView bindTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_account);
		sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		nickname = sp.getString("nickname", "");
		gender = sp.getString("gender", "未知");
		username = sp.getString("username", "");
		
		init();
		
		if(getHeader(nickname) == null){
			headImg.setImageResource(R.drawable.ic_userpic);
			System.out.println("数据库里没有头像数据。。。。。。。。。。。。。");
		}else{
			headerBmp = getHeader(nickname);
			headImg.setImageBitmap(headerBmp);
			System.out.println("有头像数据啦！！！！！！！！！！！！！！！！");
		}
		
	}

	private void init() {
		
		bindLayout = (RelativeLayout) findViewById(R.id.my_account_bind_layout);
		bindTv = (TextView) findViewById(R.id.my_account_bind_tv);
		bindTv.setText(username);
		PwdLayout = (RelativeLayout) findViewById(R.id.my_account_pwd_layout);
		helper = new UserSQLiteOpenHelper(MyAccountActivity.this);
		genderTv = (TextView) findViewById(R.id.my_account_gender_tv);
		genderTv.setText(gender);
		genderLayout = (RelativeLayout) findViewById(R.id.my_account_gender_layout);
		nicknameTv = (TextView) findViewById(R.id.my_account_username_tv);
		nicknameTv.setText(nickname);
		usernameLayout = (RelativeLayout) findViewById(R.id.my_account_username_layout);
		headImg = (ImageView) findViewById(R.id.my_account_head_img);
		
		headLayout = (RelativeLayout) findViewById(R.id.my_account_head_layout);
		titleBack = (RelativeLayout) findViewById(R.id.my_account_title_back);
		logout = (Button) findViewById(R.id.logout);
		headLayout.setOnClickListener(this);
		titleBack.setOnClickListener(this);
		logout.setOnClickListener(this);
		usernameLayout.setOnClickListener(this);
		genderLayout.setOnClickListener(this);
		PwdLayout.setOnClickListener(this);
		bindLayout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_account_title_back://点击返回
			Intent intent = new Intent();
			intent.putExtra("nickname", nickname);
			setResult(0, intent);
			finish();
			break;
		case R.id.logout://点击退出登录
			System.out.println("__________");
			setResult(1, null);
			finish();
			break;
		case R.id.my_account_head_layout://修改头像
			AlertDialog.Builder headBuilder = new Builder(
					MyAccountActivity.this);
			headBuilder.setTitle("设置头像");
			final String[] items = { "选择本地照片", "拍照" };
			headBuilder.setSingleChoiceItems(items, -1,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 0) {
								Intent intent = new Intent(Intent.ACTION_PICK,
										null);
								intent.setDataAndType(
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										IMAGE_UNSPECIFIED);
								startActivityForResult(intent, PHOTO_ZOOM);

							} else if (which == 1) {

								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								String path = Environment
										.getExternalStorageDirectory()
										.toString();
								System.out.println(path);
								File f = new File(path);
								if (null != f)
									for (File fileDel : f.listFiles()) {// 删除遗留的相片保证用户sd卡只有一张temp图
										if (null != fileDel)
											if (fileDel.toString().endsWith(
													"_temp.jpg")) {
												//"_temp.jpg"
												fileDel.delete();
											}
									}
								file = new File(Environment
										.getExternalStorageDirectory()
										+ "/"
										+ System.currentTimeMillis()
										+ "_temp.jpg");
								//"_temp.jpg"
								intent.putExtra(MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(file));
								// intent.putExtra(MediaStore.EXTRA_OUTPUT,
								// Uri.fromFile(new File(Environment
								// .getExternalStorageDirectory(),"temp.jpg")));
								startActivityForResult(intent, PHOTO_GRAPH);
							}
							dialog.dismiss();
						}
					});
			headBuilder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			headBuilder.show();// 一定要show出来
			break;
		case R.id.my_account_username_layout://跳转修改昵称界面
			Intent intent1 = new Intent();
			intent1.setClass(MyAccountActivity.this,
					ModifyUsernameActivity.class);
			startActivityForResult(intent1, 0);
			break;
		case R.id.my_account_gender_layout://修改性别
			AlertDialog.Builder genderBuilder = new Builder(
					MyAccountActivity.this);
			genderBuilder.setTitle("设置头像");
			final String[] genderItems = { "男", "女" };
			genderBuilder.setSingleChoiceItems(genderItems, -1,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							genderTv.setText(genderItems[which]);
							//将性别保存到shared 
							Editor editor = sp.edit();
							editor.putString("gender", genderItems[which]);
							editor.commit();
							//将性别保存到数据库
							updateGender(genderItems[which]);
							dialog.dismiss();
						}
					});
			genderBuilder.show();
			break;
		case R.id.my_account_pwd_layout://跳转修改密码界面
			Intent intent2 = new Intent();
			intent2.setClass(MyAccountActivity.this, ModifyPasswordActivity.class);
			startActivity(intent2);
			break;
		case R.id.my_account_bind_layout://跳转修改手机号界面
			Intent intent3 = new Intent();
			intent3.setClass(MyAccountActivity.this, BindPhoneNumberActivity.class);
			startActivityForResult(intent3, 0);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTO_GRAPH) {// 拍照
			// 设置文件保存路径
			// System.out.println(Environment.getExternalStorageDirectory());
			// File picture = new File(Environment.getExternalStorageDirectory()
			// + "/temp.jpg");

			startPhotoZoom(Uri.fromFile(file));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTO_ZOOM) {
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTO_RESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
				// 此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
				header = stream.toByteArray();
				//将头像以字节存入数据库
				updateHeader(header);
				headImg.setImageBitmap(photo); // 把图片显示在ImageView控件上
			}

		}
		// 改变nickname
		if (resultCode == MODIFY_NICKNAME) {
			System.out.println("改变nickname了吗？？？？？？？？？？？？？？？？？？？");
			nickname = data.getStringExtra("nickname");
			nicknameTv.setText(nickname);
		}
		// 改变username
				if (resultCode == 5) {
					System.out.println("改变username了吗？？？？？？？？？？？？？？？？？？？");
					username = data.getStringExtra("username");
					bindTv.setText(username);
				}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 收缩图片
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		System.out.println("执行这个方法了吗");
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 64);
		intent.putExtra("outputY", 64);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_RESOULT);
		
	}
	//将图片以字节形式存入数据库
	public void updateHeader(byte[] head){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("header", head);
		db.update("user", values, "nickname=?", new String[]{nickname});
		db.close();
	}
	//获取存入数据库的图片
	public Bitmap getHeader(String nicknameStr){
		System.out.println("111111111"+nicknameStr);
		SQLiteDatabase db = helper.getReadableDatabase();
		cursor = db.query("user", new String[]{"header"}, "nickname=?", new String[]{nicknameStr}, null, null, null);
		Bitmap bitmap = null;
		while(cursor.moveToNext()){
			if(cursor.getBlob(cursor.getColumnIndex("header"))!=null){
				byte[] img = cursor.getBlob(cursor.getColumnIndex("header"));
				bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
				return bitmap;
			}
		}
		cursor.close();
		db.close();
		return null;
	}
	//将性别存入数据库
	public void updateGender(String genderStr){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("gender", genderStr);
		db.update("user", values, "nickname=?", new String[]{nickname});
		db.close();
	}
}
