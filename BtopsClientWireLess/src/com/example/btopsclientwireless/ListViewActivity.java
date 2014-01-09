﻿package com.example.btopsclientwireless;

import java.util.List;
import com.example.hlistview.*;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewActivity extends Activity implements OnScrollListener {
	private static final String TAG = "ListViewActivity";
	ListView mListView1;
	RelativeLayout mHead;
	LinearLayout main;
	HolderAdapter holderAdapter;
	private int last_item_position;// 最后item的位置
	private boolean isLoading = false;// 是否加载过,控制加载次数
	private int currentPage = 1;// 当前页,默认为1
	private int pageSize = 20;// 每页显示十条信息
	private View loadingView;// 加载视图的布局

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listview);

		mHead = (RelativeLayout) findViewById(R.id.head);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		mHead.setBackgroundColor(Color.BLACK);
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

		// 加载视图布局
		loadingView = LayoutInflater.from(this).inflate(
				R.layout.list_page_load, null);

		mListView1 = (ListView) findViewById(R.id.listView1);
		mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mListView1.setCacheColorHint(0);
		// 添加底部加载视图
		mListView1.addFooterView(loadingView);
		// 滚动条监听
		mListView1.setOnScrollListener(this);

		// 创建当前用于显示视图的数据
		List<Data> currentData = RemoteDataUtil.createUpdateData(currentPage,
				pageSize);
		currentPage = currentPage + 1;

		/*
		 * List<Data> datas = new ArrayList<Data>();
		 * 
		 * for (int i = 0; i < 10; i++) { Data data = new Data(); data.setStr1(i
		 * + "行"); data.setStr2(i + ""); data.setStr3(i + ""); data.setStr4(i +
		 * ""); data.setStr5(i + ""); data.setStr6(i + ""); data.setStr7(i +
		 * ""); data.setStr8(i + ""); datas.add(data); }
		 */
		holderAdapter = new HolderAdapter(this, R.layout.item, currentData,
				mHead);
		mListView1.setAdapter(holderAdapter);
		// OnClick监听
		mListView1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("MainActivity ListView", "onItemClick Event");
				Toast.makeText(ListViewActivity.this, "点了第" + arg2 + "个",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		public boolean onTouch(View arg0, MotionEvent arg1) {
			// 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
			HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			HorizontalScrollView headSrcrollView2 = (HorizontalScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView.onTouchEvent(arg1);
			headSrcrollView2.onTouchEvent(arg1);
			return false;
		}
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		last_item_position = firstVisibleItem + visibleItemCount - 1;

		if (last_item_position == totalItemCount - 2) {
			// 这里控制当焦点落在某一个位置时,开始加载.
			// 当前是在第9个位置开始加载,改为totalItemCount-1
			// 则会在第10个位置开始加载

			/**
			 * Loading 标记当前视图是否处于加载中,如果正在加载(isLoading=true)就不执行更新操作
			 * 加载完成后isLoading=false,在 loadingHandler 中改变状态
			 */
			if (!isLoading) {

				// 开启一个线程加载数据
				isLoading = true;
				RemoteDataUtil.setRemoteDataByPage(currentPage, pageSize,
						new LoadStateInterface() {
							public void onLoadComplete(List<Data> remotDate) {
								holderAdapter.addItem(remotDate);
								handler.sendEmptyMessage(0);
							}
						});
			}
			;
		}
		;

		// 当ListView没有FooterView时,添加FooterView(---loadingView---)
		if (mListView1.getFooterViewsCount() == 0) {
			handler.sendEmptyMessage(1);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				// 更新
				holderAdapter.notifyDataSetChanged();
				// 删除FooterView
				mListView1.removeFooterView(loadingView);
				// 进入下一页，此时视图未加载.
				isLoading = false;
				// 当前页自加
				currentPage = currentPage + 1;
				break;
			}
			case 1: {
				mListView1.addFooterView(loadingView);
				break;
			}
			default: {
				Log.w(TAG, "未知的Handler Message:" + msg.obj.toString());
			}
			}

		};
	};

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	/**
	 * 监听ListView的OnItemClick事件
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.i("MainActivity ListView", "onItemClick Event");
		Toast.makeText(ListViewActivity.this, "点了第" + arg2 + "个",
				Toast.LENGTH_SHORT).show();
	}
}
