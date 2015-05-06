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
		// 城市信息
		String cityStr = "create table cityInfo(cityId TEXT,cityName TEXT,first TEXT,tab TEXT)";
		db.execSQL(cityStr);
		// 正在上映电影的表
//		String sql = "create table movieActionInfo(movieId NUM,movieName TEXT,publishTime TEXT,movieLength INT,movieType TEXT,has2D INT,has3D INT, hasImax INT,hot INT,hot_planCount INT,hot_priority INT,minPrice TEXT,minVipPrice TEXT, score TEXT,actor TEXT,country TEXT,director TEXT,pathHorizonB TEXT,pathSquare TEXT,pathVerticalS TEXT,posterPath TEXT)";
//		db.execSQL(sql);
//		// 即将上映
//		String movieComingStr = "create tablle movieComingInfo(movieId NUM,movieName TEXT,actor TEXT,country TEXT,director TEXT,hot,movieLength,movieType,pathSquare,pathVerticalS,publishTime,score)";
//		db.execSQL(movieComingStr);
//		// 电影详情的表
		String movieDetailStr = "create table movieDetailInfo(movieId NUM,expired INT,intro,point)";
		db.execSQL(movieDetailStr);
		String movieStr = "create table movieInfo(movieId NUM,movieName TEXT,publishTime TEXT,movieLength INT,movieType TEXT,has2D INT,has3D INT, hasImax INT,hot INT,hot_planCount INT,hot_priority INT,minPrice TEXT,minVipPrice TEXT, score TEXT,actor TEXT,country TEXT,director TEXT,pathHorizonB TEXT,pathSquare TEXT,pathVerticalS TEXT,posterPath TEXT)";
		db.execSQL(movieStr);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS cityInfo");
		db.execSQL("DROP TABLE IF EXISTS movieActionInfo");
		db.execSQL("DROP TABLE IF EXISTS movieComingInfo");
		db.execSQL("DROP TABLE IF EXISTS movieDetailInfo");
		db.execSQL("DROP TABLE IF EXISTS movieInfo");
//		db.execSQL("DROP TRIGGER IF EXISTS fk_empdept_deptid");
//		db.execSQL("DROP VIEW IF EXISTS  " );
		onCreate(db);
	}
}
