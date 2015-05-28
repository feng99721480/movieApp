package com.wiseweb.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "movieapp.db"; // 数据库名称
	private static final int version = 1; // 数据库版本

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 应该是在这里创建数据库的时候就得把所有的表都建出来吧
		//电影的建一张表，查询到新信息的时候对它进行更新吧
		System.out.println("DatabaseHelper---onCreate()");
		// 城市信息
		String cityStr = "create table cityInfo("+" _id INTEGER PRIMARY KEY AUTOINCREMENT,cityId TEXT,cityName TEXT,first TEXT,tab TEXT, text TEXT)";
		db.execSQL(cityStr);
		// 正在上映电影的表
		String movieOnStr = "create table movieOnInfo(_id INTEGER PRIMARY KEY AUTOINCREMENT,movieId NUM,movieName TEXT,publishTime TEXT,movieLength INT,movieType TEXT,has2D Boolean,has3D Boolean, hasImax Boolean,movieProperty TEXT,movieScore TEXT,actor TEXT,country TEXT,director TEXT,movieImg BLOB,cityId TEXT)";
		db.execSQL(movieOnStr);
		// 即将上映
		String movieComingStr = "create table movieComingInfo(movieId NUM,movieName TEXT,actor TEXT,country TEXT,director TEXT,has2D Boolean,has3D Boolean,hasImax Boolean,movieProperty Text,hot INT,movieLength INT,movieType TEXT,movieImg BLOB,publishTime TEXT,score TEXT)";
		db.execSQL(movieComingStr);
		//城市内影院的表
		String cinemaStr = "create table cinemaInfo(cinemaId INT,cinemaName TEXT,cinemaAddress TEXT,cinemaTel TEXT,cityId INT, cityName TEXT,districtId INT,districtName TEXT,latitude TEXT,longtitude TEXT,platform INT,lowestPrice Double,distance TEXT)";
		db.execSQL(cinemaStr);
		// 电影详情的表
//		String movieDetailStr = "create table movieDetailInfo(movieId NUM,expired INT,intro,point)";
//		db.execSQL(movieDetailStr);
//		String movieStr = "create table movieInfo(movieId NUM,movieName TEXT,publishTime TEXT,movieLength INT,movieType TEXT,has2D INT,has3D INT, hasImax INT,hot INT,hot_planCount INT,hot_priority INT,minPrice TEXT,minVipPrice TEXT, score TEXT,actor TEXT,country TEXT,director TEXT,pathHorizonB TEXT,pathSquare TEXT,pathVerticalS TEXT,posterPath TEXT)";
//		db.execSQL(movieStr);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS cityInfo");
		db.execSQL("DROP TABLE IF EXISTS movieOnInfo");
		db.execSQL("DROP TABLE IF EXISTS movieComingInfo");
		db.execSQL("DROP TABLE IF EXISTS cinemaInfo");
//		db.execSQL("DROP TABLE IF EXISTS movieInfo");
//		db.execSQL("DROP TRIGGER IF EXISTS fk_empdept_deptid");
//		db.execSQL("DROP VIEW IF EXISTS  " );
		onCreate(db);
	}
}
