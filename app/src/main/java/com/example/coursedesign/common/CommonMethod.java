package com.example.coursedesign.common;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


public class CommonMethod {
	
	
	static Map<String,Integer> map ;
	private static final int QR_WIDTH=200,QR_HEIGHT=200;
	public static Bitmap createQRImage(String url) {
		try {
			// 判断URL合法性
			if (url == null || "".equals(url) || url.length() < 1) {
				return null;
			}
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(url,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			// 下面这里按照二维码的算法，逐个生成二维码的图片，
			// 两个for循环是图片横列扫描的结果
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}
				}
			}
			// 生成二维码图片的格式，使用ARGB_8888
			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			// 显示到一个ImageView上面
			//sweepIV.setImageBitmap(bitmap);
			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public static String limitLineCharNumber(String originString){
		
		if(originString.length()>15){
			String oneLine=originString.substring(0, 15);
			String twoLine = originString.substring(15);
			return oneLine+"\n"+twoLine;
		}else {
			return originString;
		}
	}


	public static int getLevelFromRadim(int radiu){//根据半径的大小设置地图的图层
		if (radiu>2000000) {//大于2000公里
			return 3;
		}else if (radiu>1000000) {
			return 4;
		}else if (radiu>500000) {
			return 5;
		}else if (radiu>200000) {
			return 6;
		}else if (radiu>100000) {
			return 7;
		}else if (radiu>50000) {
			return 8;
		}else if (radiu>25000) {
			return 9;
		}else if (radiu>20000) {
			return 10;
		}else if (radiu>10000) {
			return 11;
		}else if (radiu>5000) {
			return 12;
		}else if (radiu>2000) {
			return 13;
		}else if (radiu>1000) {
			return 14;
		}else if (radiu>500) {
			return 15;
		}else if (radiu>200) {
			return 16;
		}else if (radiu>100) {
			return 17;
		}else if (radiu>50) {
			return 18;
		}else if (radiu>20) {
			return 19;
		}else if (radiu>10) {
			return 20;
		}else {
			return 0;
		}
		
		
	}
	
	public static Boolean saveBitmap(Bitmap bitmap,String dir){
		File dirFile = new File(dir);
		if (!dirFile.exists()) {  
			dirFile.mkdirs();  
        }  
		String filepath=dir+ System.currentTimeMillis() + ".jpg";
		File bitmapFile = new File(filepath);
		try {
			FileOutputStream outputStream = new FileOutputStream(bitmapFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
			outputStream.flush();  
			outputStream.close();  
		} catch (Exception e) {
			return false;
		}	
		return true;
	}
	
	public static Bitmap scaleBitmap(Bitmap bitmap,int width,int height){
		int originWidth=bitmap.getWidth();
		int originHeight = bitmap.getHeight();
        //计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) width) / originWidth;
        float scaleHeight = ((float) height) / originHeight;

        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				width, height, matrix, true);

		return resizedBitmap;
	}
	
	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 256)
					&& (options.outHeight >> i <= 256)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	public static void saveHistory(String field, AutoCompleteTextView auto,Activity activity) {
		String text = auto.getText().toString().trim();
		SharedPreferences sp = activity.getSharedPreferences("logInSharePreferences", 0);
		String longhistory = sp.getString(field, "nothing");
		if (longhistory.trim()=="nothing") {
			sp.edit().putString("history", (text+",").trim()).commit();
		}else {
			if (!longhistory.contains(text + ",")) {//该记录不存在则保存
				StringBuilder sb = new StringBuilder(longhistory);
				sb.insert(0, text + ",");
				sp.edit().putString("history", sb.toString()).commit();
			}
		}
		
	}
	
	public static void initAutoCompleteTextView(String field,AutoCompleteTextView auto,Activity activity){
		SharedPreferences sharedPreferences = activity.getSharedPreferences("logInSharePreferences",activity.CONTEXT_IGNORE_SECURITY);
		String longhistoryString = sharedPreferences.getString("history", "nothing");
		if (longhistoryString.trim()!="nothing") {//有历史记录
			String[] hisArrays = longhistoryString.split(",");
			 ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
		                android.R.layout.simple_dropdown_item_1line, hisArrays);  
		        //只保留最近的50条的记录  
		        if(hisArrays.length > 5){  
		            String[] newArrays = new String[5];
		            System.arraycopy(hisArrays, 0, newArrays, 0, 5);
		            adapter = new ArrayAdapter<String>(activity,
		                    android.R.layout.simple_dropdown_item_1line, newArrays);  
		        }  
		        auto.setAdapter(adapter);  
		       // auto.setDropDownHeight(350);  
		        auto.setThreshold(1);  
		       // auto.setCompletionHint("最近的5条记录");  
		        
		}
		
	}
}
