package miyou.fragment;

import java.util.ArrayList;
import java.util.List;

import miyou.adapters.MiBoAdapter;
import miyou.mibo.Mibos;

import com.luluandroid.miyou.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

public class FragmentHotMiBo extends Fragment {

	private Context context;
	private LayoutInflater inflater;
	private ViewGroup container; 
	private ListView mListView;
	private MiBoAdapter miboAdapter;
	private static FragmentHotMiBo mFragmentHotMiBo=null;
	private FragmentHotMiBo(Context context){
		this.context = context;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.container = container;
		this.inflater = inflater;
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_hot_mibo, container,false);
		initView();
		return view;
	}
	
	private void initView(){
		mListView = (ListView)getActivity().findViewById(R.id.fragment_hot_mibo_listview);
		if(mListView== null){
			Log.i("热门秘博", "mListView为空");
			return;
		}
		initAdapter();
	}
	private void initAdapter(){
		miboAdapter = new MiBoAdapter(getActivity(), getItems());
		mListView.setAdapter(miboAdapter);
	}
	
	public static FragmentHotMiBo getInstance(Context context){
		if(mFragmentHotMiBo==null){
			mFragmentHotMiBo = new FragmentHotMiBo(context);
		}
		return mFragmentHotMiBo;
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
	
}
