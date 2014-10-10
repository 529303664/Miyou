package miyou.fragment;

import com.luluandroid.miyou.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHotMiBo extends Fragment {

	private Context context;
	private LayoutInflater inflater;
	private ViewGroup container; 
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
		return view;
	}
	
	public static FragmentHotMiBo getInstance(Context context){
		if(mFragmentHotMiBo==null){
			mFragmentHotMiBo = new FragmentHotMiBo(context);
		}
		return mFragmentHotMiBo;
	}
	
}
