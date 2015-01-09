package miyou.fragment;

import java.util.ArrayList;
import java.util.List;

import statics.BroadcastString;
import miyou.adapters.MiBoAdapter;
import miyou.mibo.Mibos;

import com.luluandroid.miyou.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentAllMiBo extends Fragment {

	private Context context;
	private static FragmentAllMiBo mFragmentAllMiBo=null;
	private ListView mListView;
	private MiBoAdapter miBoAdapter;
	private FragmentAllMiboReceiver mFragmentAllMiboReceiver;
	private LocalBroadcastManager lbm;
	private FragmentAllMiBo(Context context){
		this.context = context;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_all_mibo, container, false);
		return view;
	}
	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initBroadcastReceiver();
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		if(lbm != null)
		lbm.unregisterReceiver(mFragmentAllMiboReceiver);
		super.onStop();
	}
	private void initView(){
		mListView = (ListView)getActivity().findViewById(R.id.fragment_mibo_listview);
		initAdpter();
	}
	
	private void initAdpter(){
		miBoAdapter = new MiBoAdapter(getActivity(), getItems());
		mListView.setAdapter(miBoAdapter);
	}
	
	private void initBroadcastReceiver(){
		if(lbm == null)
		lbm = LocalBroadcastManager.getInstance(getActivity());
		mFragmentAllMiboReceiver = new FragmentAllMiboReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadcastString.ACTION_FRAGMENT_ALLMIBO_LISTVIEW_TOP);
		lbm.registerReceiver(mFragmentAllMiboReceiver, filter);
		
	}
	
	private void DetroyBroadcastReceiver(){
		lbm.unregisterReceiver(mFragmentAllMiboReceiver);
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	public static FragmentAllMiBo getInstance(Context context){
		if(mFragmentAllMiBo==null){
			mFragmentAllMiBo = new FragmentAllMiBo(context);
		}
		return mFragmentAllMiBo;
	}
	
	//测试用的list范例
	private List<Mibos>getItems(){
		List<Mibos> mibos = new ArrayList<Mibos>();
		Mibos mibo;
		for(int i = 0;i<10;i++){
			mibo=new Mibos("哇哈哈","从前有"+i+"个小朋友，他很乖"+"\ue32d",i,i*3);
			mibos.add(mibo);
		}
		return mibos;
	}
	
	public class FragmentAllMiboReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(BroadcastString.ACTION_FRAGMENT_ALLMIBO_LISTVIEW_TOP)){
				mListView.setSelection(0);
			}
		}
		
	}
	
}
