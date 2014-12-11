package miyou.fragment;

import utilclass.FragmentTools;
import utilclass.ImageManager;

import com.luluandroid.miyou.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import application.control.Conf;

public class FragmentCreatetieziNavigation extends Fragment {

	private Context context;
	private static FragmentCreatetieziNavigation mFragmentCreatetieziNavigation = null;
	private TextView takePhoto, gallary, resourceModel,adjustEffect;
	private FragmentCreatetieziNavigation(Context context) {
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_create_tiezi_navigation,
				container, false);
		return view;
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

	private void initView() {
		takePhoto = (TextView) getActivity().findViewById(
				R.id.create_tiezi_takephoto);
		gallary = (TextView) getActivity().findViewById(
				R.id.create_tiezi_gallary);
		resourceModel = (TextView) getActivity().findViewById(
				R.id.create_tiezi_model);
		adjustEffect = (TextView) getActivity().findViewById(
				R.id.create_tiezi_ps);
		resourceModel = (TextView)getActivity().findViewById(R.id.create_tiezi_model);
		takePhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageManager.pictureCrop(getActivity(), Conf.TAKE_PICTURE);
			}
		});
		gallary.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageManager.pictureCrop(getActivity(), Conf.CHOOSE_PICTURE);
			}
		});
		adjustEffect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().beginTransaction().replace(R.id.
				 create_tiezi_container,
				 FragmentCreatetieziChangeBar.getInstance
				 (getActivity())).commit();
				}
		});
		resourceModel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().beginTransaction().replace(R.id.create_tiezi_container,FragmentImageMould.getInstance(getActivity())).commit();
			}
		});
	}


	public static FragmentCreatetieziNavigation getInstance(Context context) {
		if (mFragmentCreatetieziNavigation == null) {
			mFragmentCreatetieziNavigation = new FragmentCreatetieziNavigation(
					context);
		}
		return mFragmentCreatetieziNavigation;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	

}
