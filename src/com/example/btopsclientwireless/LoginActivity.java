package com.example.btopsclientwireless;






import com.example.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends Activity {
	private String userName;
	private String password;
	private String work;

	/** 以下是UI */
	private EditText view_userName;
	private EditText view_password;
	private EditText view_work;
	private CheckBox view_rememberMe;
	private Button view_loginSubmit;
	private Button view_loginConfig;
	private static final int MENU_EXIT = Menu.FIRST - 1;
	private static final int MENU_ABOUT = Menu.FIRST;
	
	/** 用来操作SharePreferences的标识 */
	private final String SHARE_LOGIN_TAG = "MAP_SHARE_LOGIN_TAG";

	/** 如果登录成功后,用于保存用户名到SharedPreferences,以便下次不再输入 */
	private String SHARE_LOGIN_USERNAME = "MAP_LOGIN_USERNAME";

	/** 如果登录成功后,用于保存PASSWORD到SharedPreferences,以便下次不再输入 */
	private String SHARE_LOGIN_PASSWORD = "MAP_LOGIN_PASSWORD";
	
	/** 如果登录成功后,用于保存工班到SharedPreferences,以便下次不再输入 */
	private String SHARE_LOGIN_WORK = "MAP_LOGIN_WORK";
	
	/** 如果登陆失败,这个可以给用户确切的消息显示,true是网络连接失败,false是用户名和密码错误 */
	private boolean isNetError;

	/** 登录loading提示框 */
	private ProgressDialog proDialog;

	/** 登录后台通知更新UI线程,主要用于登录失败,通知UI线程更新界面 */
	Handler loginHandler = new Handler() {
		public void handleMessage(Message msg) {
			isNetError = msg.getData().getBoolean("isNetError");
			if (proDialog != null) {
				proDialog.dismiss();
			}
			if (isNetError) {
				Toast.makeText(LoginActivity.this, "登陆失败:\n1.请检查您网络连接.\n2.请联系我们.!",
						Toast.LENGTH_SHORT).show();
			}
			// 用户名和密码错误
			else {
				Toast.makeText(LoginActivity.this, "登陆失败,请输入正确的用户名和密码!",
						Toast.LENGTH_SHORT).show();
				// 清除以前的SharePreferences密码
				clearSharePassword();
			}
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        findViewsById();
        initView(false);
        setListener();
    }
    
	/** 初始化注册View组件 */
	private void findViewsById() {
		view_userName = (EditText) findViewById(R.id.username_edit);
		view_password = (EditText) findViewById(R.id.password_edit);
		view_work = (EditText) findViewById(R.id.work_edit);
		view_rememberMe = (CheckBox) findViewById(R.id.loginRememberMeCheckBox);
		view_loginSubmit = (Button) findViewById(R.id.signin_button);
		view_loginConfig = (Button) findViewById(R.id.config_button);
	}
	
	/**
	 * 初始化界面
	 * 
	 * @param isRememberMe
	 *            如果当时点击了RememberMe,并且登陆成功过一次,则saveSharePreferences(true,ture)后,则直接进入
	 * */
	private void initView(boolean isRememberMe) {
		SharedPreferences share = getSharedPreferences(SHARE_LOGIN_TAG, 0);
		String work = share.getString(SHARE_LOGIN_WORK, "");
		String userName = share.getString(SHARE_LOGIN_USERNAME, "");
		String password = share.getString(SHARE_LOGIN_PASSWORD, "");
		Log
				.d(this.toString(), "userName=" + userName + " password="
						+ password + "work=" +work);
		if (!"".equals(userName)) {
			view_userName.setText(userName);
		}
		if (!"".equals(work)) {
			view_work.setText(work);
		}
		if (!"".equals(password)) {
			view_password.setText(password);
			view_rememberMe.setChecked(true);
		}
		// 如果密码也保存了,则直接让登陆按钮获取焦点
		if (view_password.getText().toString().length() > 0) {
			view_loginSubmit.requestFocus();
			// view_password.requestFocus();
		}
		share = null;
	}

	/** 设置监听器 */
	private void setListener() {
		view_loginSubmit.setOnClickListener(submitListener);
		view_loginConfig.setOnClickListener(registerLstener);
		view_rememberMe.setOnCheckedChangeListener(rememberMeListener);
	}
	
	/** 登录Button Listener */
	private OnClickListener submitListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			proDialog = ProgressDialog.show(LoginActivity.this, "连接中..",
					"连接中..请稍后....", true, true);
			Intent intent = new Intent(LoginActivity.this , NineBox.class);
			overridePendingTransition(R.anim.zoomin,R.anim.zoomout);//在跳转的语句后加上这条语句
			LoginActivity.this.startActivityForResult(intent, 0);
			proDialog.dismiss();
			// 开一个线程进行登录验证,主要是用于失败,成功可以直接通过startAcitivity(Intent)转向
			//Thread loginThread = new Thread(new LoginFailureHandler());
			//loginThread.start();
		}
	};

	// .start();
	// }
	// };

	/** 记住我checkBoxListener */
	private OnCheckedChangeListener rememberMeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (view_rememberMe.isChecked()) {
				Toast.makeText(LoginActivity.this, "如果登录成功,以后账号和密码会自动输入!",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	/** 注册Listener */
	private OnClickListener registerLstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			//intent.setClass(LoginActivity.this, Register.class);
			// 转向注册页面
			//startActivity(intent);
		}
	};
	
	/** 清除密码 */
	private void clearSharePassword() {
		SharedPreferences share = getSharedPreferences(SHARE_LOGIN_TAG, 0);
		share.edit().putString(SHARE_LOGIN_PASSWORD, "").commit();
		share = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_EXIT, 0, getResources().getText(R.string.MENU_EXIT));
		menu.add(0, MENU_ABOUT, 0, getResources().getText(R.string.MENU_ABOUT));
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
		case MENU_EXIT:
			finish();
			break;
		case MENU_ABOUT:
			alertAbout();
			break;
		}
		return true;
	}

	/** 弹出关于对话框 */
	private void alertAbout() {
		new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.MENU_ABOUT)
				.setMessage(R.string.aboutInfo).setPositiveButton(
						R.string.ok_label,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						}).show();
	}


	

}