package com.wiseweb.util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GetEnc {
	
	public static String getEnc(HashMap<String, Object> map, String key) {
		//排序
		List<Map.Entry<String, Object>> mHashMapEntryList;
		mHashMapEntryList = new ArrayList<Map.Entry<String,Object>>(map.entrySet());
		Collections.sort(mHashMapEntryList,new Comparator<Map.Entry<String,Object>>(){

			@Override
			public int compare(Entry<String, Object> firstMapEntry,
					Entry<String, Object> secondMapEntry) {
				return firstMapEntry.getKey().compareTo(secondMapEntry.getKey());
			}
			
		});
		//System.out.println(mHashMapEntryList);
		//组合
		String param = "";
		for(int i = 0; i<mHashMapEntryList.size();i++){
			param +=mHashMapEntryList.get(i).getValue();
		}
		//System.out.println(param);
		param +=key;
		//System.out.println(param);
		//URLencode
		try {
			param = URLEncoder.encode(param, "utf-8");
			//System.out.println(param);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//MD5处理
		param = getMD5String(param);
		//System.out.println(param);
		//将param小写
		param = param.toLowerCase();
		System.out.println(param);
		return param;

	}
	//MD5实现
	public static String getMD5String(String s){
		char hexDigits[] = { '0', '1', '2', '3', '4',  
                '5', '6', '7', '8', '9',  
                'A', 'B', 'C', 'D', 'E', 'F' };  
        try {  
            byte[] btInput = s.getBytes();  
            //获得MD5摘要算法的 MessageDigest 对象  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
            //使用指定的字节更新摘要  
            mdInst.update(btInput);  
            //获得密文  
            byte[] md = mdInst.digest();  
            //把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
	}
	
//	public static void main(String args[]){
//		HashMap<String,String>  mHashMap= null;
//		mHashMap=new HashMap<String, String>();
//		mHashMap.put("a", "aaa");
//		mHashMap.put("z", "bbbb");
//		mHashMap.put("c", "ccc");
//		mHashMap.put("x", "ddd");
//		//System.out.println(mHashMap);
//		//System.out.println(getEnc(mHashMap,"key"));
//	}
}
