package com.example.btopsclientwireless;

import com.example.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NineBox extends Activity {
    /** Called when the activity is first created. */
	private static final int MENU_EXIT = Menu.FIRST - 1;
	private static final int MENU_ABOUT = Menu.FIRST;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ����� 
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
  //      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
  //              WindowManager.LayoutParams.FLAG_FULLSCREEN); 
      
        setContentView(R.layout.main_activity);
        GridView gridview=(GridView)findViewById(R.id.gridview);
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();   
        for(int i=1;i<10;i++)   
        {   
          HashMap<String, Object> map = new HashMap<String, Object>(); 
          if(i==1){
                map.put("ItemImage", R.drawable.g11);
                map.put("ItemText", getResources().getString(R.string.gridview1));
          }
          if(i==2){  
              map.put("ItemImage", R.drawable.g12);
              map.put("ItemText", getResources().getString(R.string.gridview2));
        }
          if(i==3){  
              map.put("ItemImage", R.drawable.g13);
              map.put("ItemText", getResources().getString(R.string.gridview3));
        }
          if(i==4){  
              map.put("ItemImage", R.drawable.g14);
              map.put("ItemText", getResources().getString(R.string.gridview4));   
        }
          if(i==5){  
              map.put("ItemImage", R.drawable.g15);
              map.put("ItemText", getResources().getString(R.string.gridview5));
        }
          if(i==6){  
              map.put("ItemImage", R.drawable.g16);
              map.put("ItemText", getResources().getString(R.string.gridview6));
        }
          if(i==7){  
              map.put("ItemImage", R.drawable.g17);
              map.put("ItemText", getResources().getString(R.string.gridview7));
        }
          if(i==8){  
              map.put("ItemImage", R.drawable.g18);
              map.put("ItemText", getResources().getString(R.string.gridview8));
        }
          if(i==9){  
              map.put("ItemImage", R.drawable.g19); 
              map.put("ItemText", getResources().getString(R.string.gridview9));
        }
          lstImageItem.add(map); 
          
        }   

        SimpleAdapter saImageItems = new SimpleAdapter(this, 
                                                  lstImageItem, 
                                                  R.drawable.grid_item,      
                                                  new String[] {"ItemImage","ItemText"},    
                                                  new int[] {R.id.ItemImage,R.id.ItemText});   
  
        gridview.setAdapter(saImageItems);  
        //���þŹ���ļ�����˾����
        gridview.setOnItemClickListener(new ItemClickListener());   
    }   
       
  
	class  ItemClickListener implements OnItemClickListener   
    {   

       @SuppressWarnings("unchecked")
	public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened    
                                      View arg1,//The view within the AdapterView that was clicked   
                                      int arg2,//The position of the view in the adapter   
                                      long arg3//The row id of the item that was clicked   
                                    ) {   
      
      HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);   
    
      if(item.get("ItemText").equals(getResources().getString(R.string.gridview1))){
    	  Toast.makeText(NineBox.this, R.string.gridview1, Toast.LENGTH_LONG).show();
      }
      if(item.get("ItemText").equals(getResources().getString(R.string.gridview2))){
    	  Toast.makeText(NineBox.this, R.string.gridview2, Toast.LENGTH_LONG).show();
      }
      if(item.get("ItemText").equals(getResources().getString(R.string.gridview3))){
    	  Toast.makeText(NineBox.this, R.string.gridview3, Toast.LENGTH_LONG).show();
      }
      if(item.get("ItemText").equals(getResources().getString(R.string.gridview4))){
    	  //Toast.makeText(NineBox.this, R.string.gridview4, Toast.LENGTH_LONG).show();
    	  Intent intentview = new Intent(NineBox.this , ListViewActivity.class);
    	  overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
    	  NineBox.this.startActivityForResult(intentview, 0);
      }
      if(item.get("ItemText").equals(getResources().getString(R.string.gridview5))){
    	  Toast.makeText(NineBox.this, R.string.gridview5, Toast.LENGTH_LONG).show();
      }
      if(item.get("ItemText").equals(getResources().getString(R.string.gridview6))){
    	  Toast.makeText(NineBox.this, R.string.gridview6, Toast.LENGTH_LONG).show();
      }

      if(item.get("ItemText").equals(getResources().getString(R.string.gridview7))){ 
    	  Toast.makeText(NineBox.this, R.string.gridview7, Toast.LENGTH_LONG).show();
      }
      if(item.get("ItemText").equals(getResources().getString(R.string.gridview8))){
    	  Toast.makeText(NineBox.this, R.string.gridview8, Toast.LENGTH_LONG).show();
      }
      if(item.get("ItemText").equals(getResources().getString(R.string.gridview9))){
    	  Toast.makeText(NineBox.this, R.string.gridview9, Toast.LENGTH_LONG).show();
      }
     }   
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
		new AlertDialog.Builder(NineBox.this).setTitle(R.string.MENU_ABOUT)
				.setMessage(R.string.aboutInfo).setPositiveButton(
						R.string.ok_label,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						}).show();
	}

}