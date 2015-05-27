package com.wiseweb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserSQLiteOpenHelper extends SQLiteOpenHelper {

	public UserSQLiteOpenHelper(Context context) {
		super(context, "user.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 初始化数据库表结构
		System.out.println("创建数据库表结构。。。。。。。。。。。。。。。。。。。。。。。。");
		db.execSQL("create table user(id integer primary key autoincrement,"
				+ "username varchar(80),password varchar(80),nickname varchar(80),header BLOB,gender varchar(20))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
