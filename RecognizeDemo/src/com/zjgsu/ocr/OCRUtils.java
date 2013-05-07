package com.zjgsu.ocr;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.zjgsu.utils.Constants;

public class OCRUtils{
	private static final String TAG = "OCR..."; 
	private static final String DEFAULT_LANGUAGE = "eng";
	
	public String doOcr(Bitmap bitmap){
		try{
			String recognizedText = ocr(bitmap);
			return recognizedText;
		}catch(Exception ex){
			return ex.getMessage();
		}
		
	}
	
	protected String ocr(Bitmap bitmap) {       
         
        try {   
            ExifInterface exif = new ExifInterface(Constants.getImagePath());   
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);    
         
            Log.v(TAG, "Orient: " + exifOrientation);    
         
            int rotate = 0;   
            switch (exifOrientation) {   
                case ExifInterface.ORIENTATION_ROTATE_90:   
                    rotate = 90;   
                    break;   
                case ExifInterface.ORIENTATION_ROTATE_180:   
                    rotate = 180;   
                    break;   
                case ExifInterface.ORIENTATION_ROTATE_270:   
                    rotate = 270;   
                    break;   
            }    
         
            Log.v(TAG, "Rotation: " + rotate);    
         
            if (rotate != 0) {    
         
                // Getting width & height of the given image.   
                int w = bitmap.getWidth();   
                int h = bitmap.getHeight();    
         
                // Setting pre rotate   
                Matrix mtx = new Matrix();   
                mtx.preRotate(rotate);    
         
                // Rotating Bitmap   
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);   
                
            }  
         // HACK:以上try部分可以不做
         // tesseract req. ARGB_8888  
       
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        } catch (IOException e) {   
            Log.e(TAG, "Rotate or coversion failed: " + e.toString());   
        }     
         
        Log.v(TAG, "Before baseApi");    
         
        TessBaseAPI baseApi = new TessBaseAPI();   
        baseApi.setDebug(true);   
        baseApi.init(Constants.getTessPath(), DEFAULT_LANGUAGE);   
        baseApi.setImage(bitmap);   
        String resultText = baseApi.getUTF8Text();   
        baseApi.end();    
         
        Log.v(TAG, "OCR Result: " + resultText);    
         
        // clean up and show   
//        if (DEFAULT_LANGUAGE.equalsIgnoreCase("eng")) {   
//            recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");   
//        }    
        return resultText;
    }    
}