package miyou.fragment;

import com.luluandroid.miyou.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentAllMiBo extends Fragment {

	private Context context;
	private static FragmentAllMiBo mFragmentAllMiBo=null;
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
	
	public static FragmentAllMiBo getInstance(Context context){
		if(mFragmentAllMiBo==null){
			mFragmentAllMiBo = new FragmentAllMiBo(context);
		}
		return mFragmentAllMiBo;
	}
	
	
}
