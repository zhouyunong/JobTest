package com.zjgsu.recognizedemo;

import com.zjgsu.ocr.OCRUtils;
import com.zjgsu.utils.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private TextView tv_result;
	private ImageView iv_image;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		tv_result = (TextView)findViewById(R.id.textView1);
		Button btn_ocr = (Button)findViewById(R.id.button1);
		btn_ocr.setOnClickListener(Recognize);
		iv_image = (ImageView)findViewById(R.id.imageView1);
	}
	
	private OnClickListener Recognize = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			OCRUtils ocr = new OCRUtils();
			BitmapFactory.Options opts =  new  BitmapFactory.Options();
	        opts.inSampleSize = 2;
	        Bitmap bitmap = BitmapFactory.decodeFile(Constants.IMAGE_PATH, opts);
			if (bitmap == null){
				System.out.println("获取图片失败");
				return;
			}
			String result = ocr.doOcr(bitmap);
			tv_result.setText(result);
		}
	};
}
