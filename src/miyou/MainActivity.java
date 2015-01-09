package miyou;

import miyou.createtiezi.CreateTieziActivity;
import miyou.extra.ShowToast;
import miyou.fragment.FragmentAllMiBo;
import miyou.fragment.FragmentChat;
import miyou.fragment.FragmentContact;
import miyou.fragment.FragmentFriendMiBo;
import miyou.fragment.FragmentHotMiBo;
import miyou.fragment.FragmentI;
import miyou.login.LoginActivity;
import miyou.user.User;
import statics.BroadcastString;
import statics.ChannelCodes;
import utilclass.NotificationIcon;

import com.capricorn.ArcMenu;
import com.capricorn.RayMenu;
import com.luluandroid.miyou.R;

import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SpinnerAdapter;
import application.control.ManagerApplication;

public class MainActivity extends ActionBarActivity {
	private LocalBroadcastManager lbm;
	private LoginReceiver loginReceiver;
	private FragmentChangeReceiver mFragmentChangeReceiver;
	private Fragment mShowFragment;
	private RadioGroup dockbuttons;
	private ActionBar actionBarSpinner;
	private NotificationIcon mNotificationIcon;//提示小红泡
	private ImageView refreshView;
	private Animation roteAnim;
	private MenuItem refreshItem;
	
	//底部提示栏
	private RayMenu arcDockButtons;
	
	private float currentX;
	private float currentY;
	
	private final int[] ITEM_DockButtons= {R.drawable.ic_action_edit,R.drawable.ic_action_email,R.drawable.ic_action_search};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			mShowFragment = FragmentAllMiBo.getInstance(this);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, mShowFragment).commit();
		}
		initBroadcastReceiver();
		IsLogin();
		initView();
		//initRadioGroup();
		initSpinner();
		ManagerApplication.getInstance().addActivity(this);
	}

	private void initView() {
		//dockbuttons = (RadioGroup) this.findViewById(R.id.dockbuttons_group);
		refreshView = (ImageView)getLayoutInflater().inflate(R.layout.action_view, null);
		initArcDockButtons();
		loadAnim();//加载动画
	}
	
	private void initArcDockButtons(){
		 arcDockButtons = (RayMenu)findViewById(R.id.arc_dock_buttons);
		 initRayMenu(arcDockButtons,ITEM_DockButtons);
		 arcDockButtons.setOnTouchListener(new OnTouchListener() {
			private float downY;

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				 currentY=event.getY();
				// TODO Auto-generated method stub
//				switch(event.getAction() & MotionEvent.ACTION_MASK){
				 switch(event.getAction() & MotionEvent.ACTION_MASK){
				case MotionEvent.ACTION_DOWN:
					downY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					break;
				case MotionEvent.ACTION_MOVE:
					FrameLayout.LayoutParams FLP = (FrameLayout.LayoutParams)arcDockButtons.getLayoutParams();
					FLP.topMargin += currentY - downY;
					arcDockButtons.setLayoutParams(FLP);
					arcDockButtons.invalidate();
					break;
				default:
					break;
				}
				return true;
			}
		});
		
	}
	
	private void initRayMenu(RayMenu menu,int[] itemDrawables){
		//发帖按钮
		ImageView itemCreateTiezi = new ImageView(this);
		itemCreateTiezi.setImageResource(itemDrawables[0]);
		menu.addItem(itemCreateTiezi, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentToCreateTizi();
			}
		});
		
		//消息
		ImageView itemMessage = new ImageView(this);
		itemMessage.setImageResource(itemDrawables[1]);
		menu.addItem(itemMessage, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowToast.showShortToast(MainActivity.this, "消息");
			}
		});
		
		//搜索
				ImageView itemSearch = new ImageView(this);
				itemSearch.setImageResource(itemDrawables[2]);
				menu.addItem(itemSearch, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ShowToast.showShortToast(MainActivity.this, "搜索");
					}
				});
		
	}
	
	private void initSpinner(){
		actionBarSpinner = getSupportActionBar();
		actionBarSpinner.setNavigationMode(actionBarSpinner.NAVIGATION_MODE_LIST);
		actionBarSpinner.setDisplayShowTitleEnabled(false);//设置actionbar标题不可显现
		actionBarSpinner.setDisplayShowHomeEnabled(true);//设置actionbar的home图标不可显现
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_array, android.R.layout.simple_spinner_dropdown_item);
		
		actionBarSpinner.setListNavigationCallbacks(mSpinnerAdapter, new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				// TODO Auto-generated method stub\
				lbm = LocalBroadcastManager.getInstance(MainActivity.this);
				switch(itemPosition){
				case 0:
					lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_ALLMIBO));
					break;
				case 1:
					lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_HOTMIBO));
					break;
				case 2:
					lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_FRIENDMIBO));
					break;
					default:
						break;
				}
				return true;
			}
		});
		
		//添加actionbar customview 方便点击actionbar相应scrollview滚到顶部
		View customView = LayoutInflater.from(this).inflate(R.layout.activity_main_logo_customview,new LinearLayout(this), false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(customView);
		customView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_ALLMIBO_LISTVIEW_TOP));
				Toast.makeText(MainActivity.this, "点击了customview", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void initBroadcastReceiver() {
		lbm = LocalBroadcastManager.getInstance(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadcastString.ACTION_LOGIN_SUCCESS_RETURN_TO_MAIN);
		loginReceiver = new LoginReceiver();
		lbm.registerReceiver(loginReceiver, filter);
		mFragmentChangeReceiver = new FragmentChangeReceiver();
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction(BroadcastString.ACTION_FRAGMENT_ALLMIBO);
		filter2.addAction(BroadcastString.ACTION_FRAGMENT_HOTMIBO);
		filter2.addAction(BroadcastString.ACTION_FRAGMENT_FRIENDMIBO);
		lbm.registerReceiver(mFragmentChangeReceiver, filter2);
	}

	private void loadAnim(){
		roteAnim = AnimationUtils.loadAnimation(this, R.anim.rote_center);
		LinearInterpolator lin = new LinearInterpolator();
		roteAnim.setInterpolator(lin);
	}
	
	/**
	 *为MenuItem播放旋转动画
	 * @param item
	 */
	private void PlayRsAnim(MenuItem item){
		StopRsAnim(item);
		refreshItem  = item;
		
		refreshView.setImageResource(R.drawable.ic_action_refresh);
		refreshItem.setActionView(refreshView);
		refreshView.startAnimation(roteAnim);
	}
	

	/**
	 * 停止MenuItem旋转动画
	 * @param refreshItem
	 */
	private void StopRsAnim(MenuItem refreshItem){
		if(refreshItem != null){
			ShowToast.showLongToast(this, "停止播放刷新动画");
			View view = refreshItem.getActionView();
//			refreshItem.setActionView(null);
			if(view != null){
				view.clearAnimation();
				refreshItem.setActionView(null);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		lbm.unregisterReceiver(loginReceiver);
		lbm.unregisterReceiver(mFragmentChangeReceiver);
		super.onDestroy();
	}

	private void IntentToCreateTizi(){
		Intent intent = new Intent(MainActivity.this,CreateTieziActivity.class);
		startActivityForResult(intent, ChannelCodes.CREATE_TIEZI);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		case R.id.actions_menu_create_tiezi:
			ShowToast.showShortToast(this, "发帖");
			Intent intent = new Intent(MainActivity.this,CreateTieziActivity.class);
			startActivityForResult(intent, ChannelCodes.CREATE_TIEZI);
			break;
		case R.id.actions_menu_message:
			ShowToast.showShortToast(this, "消息");
			break;
		case R.id.actions_menu_refresh:
			ShowToast.showShortToast(this, "刷新");
			PlayRsAnim(item);
			break;
		case R.id.actions_menu_search:
			ShowToast.showShortToast(this, "搜贴");
			//测试刷新动画，停止 
			StopRsAnim(refreshItem);
			break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void IsLogin() {
		if (User.getUserIsntance().isLogin(this)) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	private void initRadioGroup() {
		/*mNotificationIcon = NotificationIcon.getInstance(MainActivity.this);
		mNotificationIcon.Marknewtips(findViewById(R.id.dockbarbuttons_mibo), "5", "TAG_marknewtips_mibo");
		mNotificationIcon.Marknewtips(findViewById(R.id.dockbarbuttons_chat), "3", "TAG_marknewtips_chat");*/
		dockbuttons.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				lbm = LocalBroadcastManager.getInstance(MainActivity.this);
				switch (checkedId) {
				case R.id.dockbarbuttons_mibo:
					/*mNotificationIcon.dismissBadgeViewWithTag("TAG_marknewtips_mibo");*/
					lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_ALLMIBO));
					
					break;
				case R.id.dockbarbuttons_chat:
					/*mNotificationIcon.dismissBadgeViewWithTag("TAG_marknewtips_chat");*/
					if (mShowFragment != FragmentChat
							.getInstance(MainActivity.this)) {
						mShowFragment = FragmentChat
								.getInstance(MainActivity.this);
					}
					
					break;
				case R.id.dockbarbuttons_contact:
					if (mShowFragment != FragmentContact
							.getInstance(MainActivity.this)) {
						mShowFragment = FragmentContact
								.getInstance(MainActivity.this);
					}
					break;
				case R.id.dockbarbuttons_i:
					if (mShowFragment != FragmentI
							.getInstance(MainActivity.this)) {
						mShowFragment = FragmentI
								.getInstance(MainActivity.this);
					}
					break;
				default:
					break;
				}
				if(mShowFragment!=null){
					switchFragment(mShowFragment);
				}else
				Log.e("fragment_error", "mShowFragment----->为空");
				
			}
		});
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
		case ChannelCodes.CREATE_TIEZI_SUCCESS:
			ShowToast.showShortToast(this, "发帖成功");
			break;
		case ChannelCodes.CREATE_TIEZI_FAIL:
			ShowToast.showShortToast(this, "发帖失败");
			break;
		default:
			break;
		}
	}

	public void switchFragment(Fragment fragment){
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
	}

	public class LoginReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(
					BroadcastString.ACTION_LOGIN_SUCCESS_RETURN_TO_MAIN)) {
				ShowToast.showShortToast(context, "跳转成功");
			}
		}

	}
	
	public class FragmentChangeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(BroadcastString.ACTION_FRAGMENT_ALLMIBO)){
				if (mShowFragment != FragmentAllMiBo
						.getInstance(MainActivity.this)) {
					mShowFragment = FragmentAllMiBo
							.getInstance(MainActivity.this);
				}
			}else if(intent.getAction().equals(BroadcastString.ACTION_FRAGMENT_HOTMIBO)){
				if (mShowFragment != FragmentHotMiBo
						.getInstance(MainActivity.this)) {
					mShowFragment = FragmentHotMiBo
							.getInstance(MainActivity.this);
				}
			}else if(intent.getAction().equals(BroadcastString.ACTION_FRAGMENT_FRIENDMIBO)){
				if (mShowFragment != FragmentFriendMiBo
						.getInstance(MainActivity.this)) {
					mShowFragment = FragmentFriendMiBo
							.getInstance(MainActivity.this);
				}
			}
			
			if(mShowFragment!=null){
				switchFragment(mShowFragment);
			}
		}
		
	}

}
