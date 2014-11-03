package miyou.createtiezi;

import miyou.MainActivity;
import miyou.extra.ShowToast;
import miyou.fragment.FragmentCreatetieziNavigation;
import statics.ChannelCodes;

import com.luluandroid.miyou.R;

import android.view.LayoutInflater;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import application.control.ManagerApplication;

public class CreateTieziActivity extends ActionBarActivity {

	private ActionBar actionbar;
	private Fragment currentFragment;
	private ImageView navigationLfBt,navigationRtBt;
	private FrameLayout navigationFrameLayout;
	private boolean NvIsOpen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_tiezi);
		if (savedInstanceState == null) {
			currentFragment = FragmentCreatetieziNavigation.getInstance(this);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.create_tiezi_container,currentFragment).commit();
		}
		InitActionBar();
		initComponent();
		ManagerApplication.getInstance().addActivity(this);
	}

	private void initComponent(){
		navigationLfBt = (ImageView)findViewById(R.id.create_tiezi_controlbar_left_button);
		navigationRtBt = (ImageView)findViewById(R.id.create_tiezi_controlbar_right_button);
		navigationFrameLayout = (FrameLayout)findViewById(R.id.create_tiezi_container);
		NvIsOpen = false;
		final Animation navigationEtAnimation = AnimationUtils.loadAnimation(this, R.anim.create_tiezi_navigation_enter);
		final Animation navigationExAnimation = AnimationUtils.loadAnimation(this, R.anim.create_tiezi_navigation_exit);
		navigationLfBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!NvIsOpen){
					navigationLfBt.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_collapse));
					navigationFrameLayout.startAnimation(navigationEtAnimation);
					navigationFrameLayout.setVisibility(View.VISIBLE);
					NvIsOpen = true;
				}else{
					navigationLfBt.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_expand));
					navigationFrameLayout.startAnimation(navigationExAnimation);
					navigationFrameLayout.setVisibility(View.GONE);
					NvIsOpen = false;
				}
			}
		});
	}
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_tiezi, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();
		switch(id){
		case R.id.home:
			/*Intent intent = new Intent(this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);*/
			finish();
		case R.id.action_create_tiezi_write:
			//数据发送
			setResult(ChannelCodes.CREATE_TIEZI_SUCCESS);
			finish();
		default:
			break;
		}
		/*if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
	
	public void InitActionBar(){
		actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);		
		
	}
	
	public void switchFragment(Fragment fragment){
		getSupportFragmentManager().beginTransaction().replace(R.id.create_tiezi_container, fragment).commit();
	}
}
