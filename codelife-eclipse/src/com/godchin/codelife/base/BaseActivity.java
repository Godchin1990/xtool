package com.godchin.codelife.base;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.godchin.codelife.R;


public class BaseActivity extends Activity {

	private RelativeLayout container;
	private TextView mTitle;
	private ImageView mBack;
	private ImageView mSetting;
	private Context con;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.activity_base);
        con = this;
        init_View();
    }
    
    private void init_View(){

    	container = (RelativeLayout) findViewById(R.id.root_layout);
    	mTitle = (TextView) findViewById(R.id.title);
    	mBack = (ImageView) findViewById(R.id.back_btn);
 
    	mBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
    		
    	});
    	mSetting = (ImageView) findViewById(R.id.setting_btn);
    }
    
    
   @Override
   public void setContentView(int layoutResID) {
	   
       View child = (View) getLayoutInflater().inflate(layoutResID, null);
       RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
    		   RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
       lp.addRule(RelativeLayout.BELOW, R.id.mytitle);
       container.addView(child,lp);
   }
   
   /**
    * 设置title内容
    * */
   public void setMyTitle(String title_text){
	   mTitle.setText(title_text);
   }
   
   /**
    * 设置back按钮的跳转动作
    * */
   public void setBackBtn(Intent backTo){
	   
	   final Intent back_intent = backTo;
	   mBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Toast.makeText(con, "返回按钮", Toast.LENGTH_SHORT).show();
				
//				con.startActivity(back_intent);
//				finish();
			}
   		
	   });
   }
   
   /**
    * 设置setting按钮的跳转动作
    * */
   public void setSettingBtn(Intent Setting){
	   
	   final Intent setting_intent = Setting;
	   mSetting.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Toast.makeText(con, "设置菜单", Toast.LENGTH_SHORT).show();
//				con.startActivity(setting_intent);
//				finish();
			}
   		
	   });
   }
   
   public void setSettingBtnINVISIBLE(){
	   mSetting.setVisibility(View.INVISIBLE);
   }
}
