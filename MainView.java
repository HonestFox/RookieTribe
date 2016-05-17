package com.example.tempaturedisp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MainView extends View 
{
	public static MainView mainview;
	public static MainView getMainView()
	{
		return mainview;
	}
	
	
	public static Tempature TArr_1 = new Tempature();
	public static Tempature TArr_2 = new Tempature();
	public static Tempature TArr_3 = new Tempature();
	public static Tempature TArr_4 = new Tempature();
	public static  float size = 50;
	Paint paint = new Paint();

	
//	public void get_tempature()
//	{
//		TArr_1.ChangeData();
//		TArr_2.ChangeData();
//		TArr_3.ChangeData();
//		TArr_4.ChangeData();
//		this.invalidate();
//	}

	public void get_tempature_normally(int val1, int val2, int val3)
	{
		int val4 = -val1;
		TArr_1.ChangeDataNormally(val1);
		TArr_2.ChangeDataNormally(val2);
		TArr_3.ChangeDataNormally(val3);
		TArr_4.ChangeDataNormally(val1 - val2);
		this.invalidate();
	}
	

	public MainView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	protected void onDraw(Canvas canvas) 
	{
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(2);
		paint.setTextSize(size);
	//	drawList(canvas);
		//坐标原点为  50, 950
		
		//！写完封装成函数！
		//设置横坐标横坐标
		
		canvas.drawLine(50, 1500, 1050, 1500, paint);
		canvas.drawCircle(250,1500,4,paint);
		canvas.drawCircle(450,1500,4,paint);
		canvas.drawCircle(650,1500,4,paint);
		canvas.drawCircle(850,1500,4,paint);
		canvas.drawLine(1050, 1500, 1025, 1475, paint);
		canvas.drawLine(1050, 1500, 1025, 1525, paint);
		

		//
		//纵坐标
		canvas.drawLine(50, 1500, 50, 690, paint);
		canvas.drawLine(50, 690, 25, 715, paint);
		canvas.drawLine(50, 690, 75, 715, paint);
		canvas.drawLine(50, 1100, 90, 1100, paint);
		canvas.drawLine(50, 1220, 70, 1220, paint);
		canvas.drawText("-30", 0, 1220, paint);
		canvas.drawLine(50, 980, 70, 980, paint);
		canvas.drawText("30", 0, 980, paint);
		paint.setColor(Color.BLACK);
		for(int i = 700; i < 1500; i += 40)
		{
			canvas.drawLine(50, i, 59, i, paint);
		}
		paint.setColor(Color.RED);
		canvas.drawLine(50, 1100, 90, 1100, paint);
		canvas.drawText("0", 30, 1100, paint);
		
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		//paint.setColor(Color.BLUE);
		
	
		
		
		//显示温度
		int val_1 = Converse(TArr_1._data);
		int val_2 = Converse(TArr_2._data);
		int val_3 = Converse(TArr_3._data);
		int val_4 = Converse(TArr_4._data);
		String str1 = Integer.toString(TArr_1._data);
		String str2 = Integer.toString(TArr_2._data);
		String str3 = Integer.toString(TArr_3._data);
		String str4 = Integer.toString(TArr_4._data);
		paint.setTextSize(size);
		//test
		paint.setARGB(200, 100-TArr_1._data,100-TArr_1._data, 100-TArr_1._data);
		canvas.drawRect(200, val_1, 300, 1500, paint);
		canvas.drawText(str1, 220, val_1, paint);
		paint.setARGB(200, 100-TArr_2._data, 100-TArr_2._data,150);
		canvas.drawRect(400, val_2, 500, 1500, paint);
		canvas.drawText(str2, 420, val_2, paint);
		paint.setARGB(200, 150, 100-TArr_3._data,100-TArr_3._data);
		canvas.drawRect(600, val_3, 700, 1500, paint);
		canvas.drawText(str3, 620, val_3, paint);
		paint.setARGB(200, 100-TArr_4._data, 150, 100-TArr_4._data);
		canvas.drawRect(800, val_4, 900, 1500, paint);
		canvas.drawText(str4, 820, val_4, paint);
		
//		CheckTempature(TArr_1);
//		CheckTempature(TArr_2);
//		CheckTempature(TArr_3);
//		CheckTempature(TArr_4);
		
//		if(TArr_1._data > 95)
//		{
//			TArr_1.SetDec();
//		}
//		if(TArr_1._data < -95)
//		{
//			TArr_1.SetInc();
//		}
//		
//		if(TArr_2._data > 95)
//		{
//			TArr_2.SetDec();
//		}
//		if(TArr_2._data < -95)
//		{
//			TArr_2.SetInc();
//		}
//		
//		if(TArr_3._data > 95)
//		{
//			TArr_3.SetDec();
//		}
//		if(TArr_3._data < -95)
//		{
//			TArr_3.SetInc();
//		}
//		
//		if(TArr_4._data > 95)
//		{
//			TArr_4.SetDec();
//		}
//		if(TArr_4._data < -95)
//		{
//			TArr_4.SetInc();
//		}
	}
	
	//温度转换为纵坐标
	public int Converse(int tempature)
	{
		return (1100 - 4 * tempature);
	}
	
	public void CheckTempature(Tempature tmp)
	{
		if(Converse(tmp._data) >= 95)
		{
			tmp.SetDec();
		}
		else if(Converse(tmp._data) <= -95)
		{
			tmp.SetInc();
		}
	}
}


class Tempature
{
	public void SetInc()
	{
		_IsInc = true;
	}
	public void SetDec()
	{
		_IsInc = false;
	}
	public void ChangeDataNormally(int val)
	{
		if(val > 0)
		{
			_IsInc = true;
		}
		if(val < 0)
		{
			_IsInc = false;
		}
		_data += val;
		if(_data > 100)
		{
			_data = 100;
		}
		if(_data < -100)
		{
			_data = -100;
		}
	}
	public void ChangeData()
	{
		if(_IsInc)
		{
			int inc = (int)(Math.random() *10);  
			int dec = (int)(Math.random() *10);
			int tmp = (int)(Math.random() *10);
			if(dec > inc)
			{
				int tmp_1 = inc;
				inc = dec;
				dec = tmp_1;
			}
			if(tmp <= 7)
			{
				_data += inc;
			}
			else
			{
				_data -= (dec / 2);
			}
		}
		else if(!_IsInc)
		{
			int inc = (int)(Math.random() *10);
			int dec = (int)(Math.random() *10);
			if(dec < inc)
			{
				int tmp = inc;
				inc = dec;
				dec = tmp;
			}	
			if(inc <= 7)
			{
				_data -= dec;
			}
			else
			{
				_data += (inc / 2);
			}
		}
	}
	public int _data = 0;
	public boolean _IsInc = true;
}

