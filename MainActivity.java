package com.example.tempaturedisp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chinasofti.service.ServiceCenter;
import com.example.tempaturedisp.R.string;




public class MainActivity extends Activity
{
private static MainActivity mainActivity;
	
	public static MainActivity getMainActivity()
	{
		return mainActivity;
	}
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainActivity = this;
		if (savedInstanceState == null)
		{
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	public void onConnectServer(String serverIP)
	{
		PlaceholderFragment.getMainFragment().onConnectServer(serverIP);
	}
	
	public void onDisconnectServer(String serverIP)
	{
		PlaceholderFragment.getMainFragment().onDisconnectServer(serverIP);
	}
	
	public void receiveClientMessage(String message)
	{
		PlaceholderFragment.getMainFragment().receiveClientMessage(message);
	}

	public void receiveSensorMessage(String message)
	{
		PlaceholderFragment.getMainFragment().receiveSensorMessage(message);
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	public static class PlaceholderFragment extends Fragment
	{
		
		//View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		
		private static PlaceholderFragment mainFragment;
		private Handler handlerMessage = null;
		
		public static PlaceholderFragment getMainFragment()
		{
			return mainFragment;
		}
		
		
		
		public PlaceholderFragment()
		{
			mainFragment = this;
		}
		
		public void onConnectServer(String serverIP)
		{
			Message msg = new Message();
			msg.what = R.id.buttonConnect;
			msg.obj = "已连接至服务器！";
			handlerMessage.sendMessage(msg);
		}
		
		public void onDisconnectServer(String serverIP)
		{
			Message msg = new Message();
			msg.what = R.id.buttonStop;
			msg.obj = "已断开服务器！";
			handlerMessage.sendMessage(msg);
		}
		
		public void receiveClientMessage(String message)
		{
			Message msg = new Message();
			msg.what = R.id.tvReceivedMessage;
			msg.obj = message;
			handlerMessage.sendMessage(msg);
		}
		
		public void receiveSensorMessage(String message)
		{
//			Message msg = new Message();
//			msg.what = R.id.tvReceivedMessage;
//			msg.obj = message;
//			handlerMessage.sendMessage(msg);
//			
			Message msg = new Message();
			msg.what = R.id.action_settings;
			msg.obj = message;
			handlerMessage.sendMessage(msg);
			_arr = message.toCharArray();

		}
		
		
		@SuppressLint("HandlerLeak") @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			
			
			final MainView mainview = (MainView)rootView.findViewById(R.id.mainView1);
			
			
			
			
			
			
			
			
			final TextView tvClientStatus = (TextView)rootView.findViewById(R.id.tvClientStatus);
			final TextView tvReceivedMessage = (TextView)rootView.findViewById(R.id.tvReceivedMessage);
			final Button buttonConnect = (Button)rootView.findViewById(R.id.buttonConnect);
			final Button buttonStop = (Button)rootView.findViewById(R.id.buttonStop);
			final EditText etServerIP = (EditText)rootView.findViewById(R.id.etServerIP);
			final EditText etServerPort = (EditText)rootView.findViewById(R.id.etServerPort);
			
			
			
			
			
			buttonConnect.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String ip = etServerIP.getText().toString();
					String strPort = etServerPort.getText().toString();
					
					if(ip == null || ip.isEmpty() || strPort == null || strPort.isEmpty())
						return;
					
					int port = Integer.parseInt(strPort);
					
					if(port <= 0 || port >= 65535)
						return;
					
					ServiceCenter.getServiceCenter().connectServer(ip, port);
				}
			});
			buttonStop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ServiceCenter.getServiceCenter().safeDisconnectServer();
				}
			});
			
			handlerMessage = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					switch(msg.what)
					{
					case R.id.tvReceivedMessage:
						tvReceivedMessage.setText(msg.obj.toString());
						break;
					case R.id.buttonConnect:
						buttonConnect.setEnabled(false);
						buttonStop.setEnabled(true);
						tvClientStatus.setText(msg.obj.toString());
						break;
					case R.id.buttonStop:
						buttonConnect.setEnabled(true);
						buttonStop.setEnabled(false);
						tvClientStatus.setText(msg.obj.toString());
						break;
					case R.id.action_settings:
						int sz = _arr.length;   
						int flag = 1;
						int valX = 0;
						int valY = 0;
						int valZ = 0;
						if(sz == 3)
						{
							valX = _arr[0] - '0';
							valY = _arr[1] - '0';
							valZ = _arr[2] - '0';
						}
						//-123
						//1-23
						//12-3
						if(sz == 4)
						{
							if(_arr[0] == '-')
							{
								valX = (-1) * (_arr[1] - '0');
								valY = _arr[2] - '0';
								valZ = _arr[3] - '0';
							}
							if(_arr[1] == '-')
							{
								valX = _arr[0] - '0';
								valY = (-1) * (_arr[2] - '0');
								valZ = _arr[3] - '0';
							}
							if(_arr[2] == '-')
							{
								valX = _arr[0] - '0';
								valY = _arr[1] - '0';
								valZ = (-1) * (_arr[3] - '0');
							}
						}
						//1-2-3               1 3
						//-12-3				  0 3
						//-1-23				  0 2
						if(sz == 5)
						{
							if(_arr[0] == '-')
							{
								if(_arr[2] == '-')
								{
									valX = (-1) * (_arr[1] - '0');
									valY = (-1) * (_arr[3] - '0');
									valZ = _arr[4] - '0';
								}
								else
								{
									valX = (-1) * (_arr[1] - '0');
									valY = _arr[2] - '0';
									valZ = (-1) * (_arr[4] - 0);
								}
							}
							else
							{
								valX = _arr[0] - '0';
								valY = (-1) * (_arr[2] - '0');
								valZ = (-1) * (_arr[4] - '0');
							}
						}
						
						if(sz == 6)
						{
							valX = (-1) * (_arr[1] - '0');
							valY = (-1) * (_arr[3] - '0');
							valZ = (-1) * (_arr[5] - '0');
						}
						mainview.get_tempature_normally(valX / 2, valY / 2, valZ / 2);
					}
						
				}
			};
			
			
			//按键
			//Button button_connect = (Button)rootView.findViewById(R.id.button_connect);
			//Button button_get_tempature = (Button)rootView.findViewById(R.id.button_get_tempature);

//			
//			button_connect.setOnClickListener(new OnClickListener()
//			{
//				public void onClick(View arg0)
//				{
//					
//				}
//			});
			
			
//			
//			button_get_tempature.setOnClickListener(new OnClickListener()
//			{
//				public void onClick(View arg0)
//				{
//					mainview.get_tempature();
//				}
//			});
			
			return rootView;
		}
		public char[] _arr;
	}
}
