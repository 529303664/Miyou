package miyou.fragment;

import java.util.ArrayList;
import java.util.List;

import miyou.adapters.MiBoAdapter;
import miyou.mibo.Mibos;

import com.luluandroid.miyou.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentAllMiBo extends Fragment {

	private Context context;
	private static FragmentAllMiBo mFragmentAllMiBo=null;
	private ListView mListView;
	private MiBoAdapter miBoAdapter;
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
	
	private void initView(){
		mListView = (ListView)getActivity().findViewById(R.id.fragment_mibo_listview);
		initAdpter();
	}
	
	private void initAdpter(){
		miBoAdapter = new MiBoAdapter(getActivity(), getItems());
		mListView.setAdapter(miBoAdapter);
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
	
}
