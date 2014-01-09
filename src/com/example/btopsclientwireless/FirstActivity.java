package com.example.btopsclientwireless;

import com.example.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

public class FirstActivity extends Activity {

/*	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
	}
*/
	  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_first);
        //this.setContentView(R.layout.letao_myletao_code_shoes);
        Handler handler = new Handler(){
        	     @Override
        	    public void handleMessage(Message msg) {
        	    	 switch (msg.what) {
					case 2:
						Intent intent = new Intent(FirstActivity.this , LoginActivity.class);
						overridePendingTransition(R.anim.zoomin,R.anim.zoomout);//在跳转的语句后加上这条语句
						FirstActivity.this.startActivityForResult(intent, 0);
						FirstActivity.this.finish();
						int version =  Integer.valueOf(android.os.Build.VERSION.SDK);
						if(version > 5 ){
							overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
						}
						FirstActivity.this.finish();
					    default:
						break;
					}
        	    	super.handleMessage(msg);
        	    }
        };
        handler.sendEmptyMessageDelayed(2, 900L);
    }
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

}
