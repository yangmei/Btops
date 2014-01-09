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

	/** ������UI */
	private EditText view_userName;
	private EditText view_password;
	private EditText view_work;
	private CheckBox view_rememberMe;
	private Button view_loginSubmit;
	private Button view_loginConfig;
	private static final int MENU_EXIT = Menu.FIRST - 1;
	private static final int MENU_ABOUT = Menu.FIRST;
	
	/** ��������SharePreferences�ı�ʶ */
	private final String SHARE_LOGIN_TAG = "MAP_SHARE_LOGIN_TAG";

	/** �����¼�ɹ���,���ڱ����û�����SharedPreferences,�Ա��´β������� */
	private String SHARE_LOGIN_USERNAME = "MAP_LOGIN_USERNAME";

	/** �����¼�ɹ���,���ڱ���PASSWORD��SharedPreferences,�Ա��´β������� */
	private String SHARE_LOGIN_PASSWORD = "MAP_LOGIN_PASSWORD";
	
	/** �����¼�ɹ���,���ڱ��湤�ൽSharedPreferences,�Ա��´β������� */
	private String SHARE_LOGIN_WORK = "MAP_LOGIN_WORK";
	
	/** �����½ʧ��,������Ը��û�ȷ�е���Ϣ��ʾ,true����������ʧ��,false���û������������ */
	private boolean isNetError;

	/** ��¼loading��ʾ�� */
	private ProgressDialog proDialog;

	/** ��¼��̨֪ͨ����UI�߳�,��Ҫ���ڵ�¼ʧ��,֪ͨUI�̸߳��½��� */
	Handler loginHandler = new Handler() {
		public void handleMessage(Message msg) {
			isNetError = msg.getData().getBoolean("isNetError");
			if (proDialog != null) {
				proDialog.dismiss();
			}
			if (isNetError) {
				Toast.makeText(LoginActivity.this, "��½ʧ��:\n1.��������������.\n2.����ϵ����.!",
						Toast.LENGTH_SHORT).show();
			}
			// �û������������
			else {
				Toast.makeText(LoginActivity.this, "��½ʧ��,��������ȷ���û���������!",
						Toast.LENGTH_SHORT).show();
				// �����ǰ��SharePreferences����
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
    
	/** ��ʼ��ע��View��� */
	private void findViewsById() {
		view_userName = (EditText) findViewById(R.id.username_edit);
		view_password = (EditText) findViewById(R.id.password_edit);
		view_work = (EditText) findViewById(R.id.work_edit);
		view_rememberMe = (CheckBox) findViewById(R.id.loginRememberMeCheckBox);
		view_loginSubmit = (Button) findViewById(R.id.signin_button);
		view_loginConfig = (Button) findViewById(R.id.config_button);
	}
	
	/**
	 * ��ʼ������
	 * 
	 * @param isRememberMe
	 *            �����ʱ�����RememberMe,���ҵ�½�ɹ���һ��,��saveSharePreferences(true,ture)��,��ֱ�ӽ���
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
		// �������Ҳ������,��ֱ���õ�½��ť��ȡ����
		if (view_password.getText().toString().length() > 0) {
			view_loginSubmit.requestFocus();
			// view_password.requestFocus();
		}
		share = null;
	}

	/** ���ü����� */
	private void setListener() {
		view_loginSubmit.setOnClickListener(submitListener);
		view_loginConfig.setOnClickListener(registerLstener);
		view_rememberMe.setOnCheckedChangeListener(rememberMeListener);
	}
	
	/** ��¼Button Listener */
	private OnClickListener submitListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			proDialog = ProgressDialog.show(LoginActivity.this, "������..",
					"������..���Ժ�....", true, true);
			Intent intent = new Intent(LoginActivity.this , NineBox.class);
			overridePendingTransition(R.anim.zoomin,R.anim.zoomout);//����ת����������������
			LoginActivity.this.startActivityForResult(intent, 0);
			proDialog.dismiss();
			// ��һ���߳̽��е�¼��֤,��Ҫ������ʧ��,�ɹ�����ֱ��ͨ��startAcitivity(Intent)ת��
			//Thread loginThread = new Thread(new LoginFailureHandler());
			//loginThread.start();
		}
	};

	// .start();
	// }
	// };

	/** ��ס��checkBoxListener */
	private OnCheckedChangeListener rememberMeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (view_rememberMe.isChecked()) {
				Toast.makeText(LoginActivity.this, "�����¼�ɹ�,�Ժ��˺ź�������Զ�����!",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	/** ע��Listener */
	private OnClickListener registerLstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			//intent.setClass(LoginActivity.this, Register.class);
			// ת��ע��ҳ��
			//startActivity(intent);
		}
	};
	
	/** ������� */
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

	/** �������ڶԻ��� */
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