package com.wiseweb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CinemaSQLiteOpenHelper extends SQLiteOpenHelper {
	// 构造方法
	public CinemaSQLiteOpenHelper(Context context) {
		super(context, "cinema.db", null, 1);
	}

	// 数据库第一次被创建的时候调用的方法
	@Override
	public void onCreate(SQLiteDatabase db) {
		//初始化数据库表结构
		db.execSQL("create table cinema(id integer primary key autoincrement" +
				",_id varchar(80),cinemaId integer,ticketType integer" +
				",platform integer,openTime varchar(80),machineType integer" +
				",longitude varchar(80),logo varchar(80),latitude varchar(80)" +
				",hot varchar(10),foreignCinemaId varchar(80),flag varchar(10)" +
				",drivePath varchar(200),districtName varchar(20),districtId integer" +
				",cityName varchar(20),cityId integer,cinemaTel varchar(80)" +
				",cinemaName varchar(80),cinemaIntro varchar(200)" +
				",cinemaAddress varchar(200),banner varchar(200)" +
				",score integer,updateAt varchar(80),creatAt varchar(80)" +
				",galleries varchar(200))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
}
