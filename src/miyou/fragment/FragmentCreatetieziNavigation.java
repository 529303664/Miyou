package miyou.fragment;

import com.luluandroid.miyou.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCreatetieziNavigation extends Fragment {
	private Context context;
	private static FragmentCreatetieziNavigation mFragmentCreatetieziNavigation=null;
	private FragmentCreatetieziNavigation(Context context){
		this.context = context;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_create_tiezi_navigation, container, false);
		return view;
	}
	
	public static FragmentCreatetieziNavigation getInstance(Context context){
		if(mFragmentCreatetieziNavigation==null){
			mFragmentCreatetieziNavigation = new FragmentCreatetieziNavigation(context);
		}
		return mFragmentCreatetieziNavigation;
	}
}
