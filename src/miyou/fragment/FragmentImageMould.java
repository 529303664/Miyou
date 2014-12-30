package miyou.fragment;

import miyou.adapters.MouldAdapter;

import com.luluandroid.miyou.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentImageMould extends Fragment {

	private Context context;
	private static FragmentImageMould mFragmentImageMould = null;
	private TextView returnTextView;
	private GridView mGridView;
	private Integer[] mThumbIds = {
			R.drawable.model_black,R.drawable.model_pink,R.drawable.model_purple,
			R.drawable.model_sand,R.drawable.model_slight,R.drawable.model_table,
			R.drawable.model_table_texture
	};
	private FragmentImageMould(Context context){
		this.context = context;
	}

	public static FragmentImageMould getInstance(Context context){
		if(mFragmentImageMould ==null){
			mFragmentImageMould = new FragmentImageMould(context);
		}
		return mFragmentImageMould;
	}
		
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_create_tiezi_mould, container, false);
		return view;
	}

	private void initView(){
		returnTextView = (TextView)getActivity().findViewById(R.id.create_tiezi_mould_return);
		mGridView = (GridView)getActivity().findViewById(R.id.create_tiezi_gridview);
		if(mGridView==null){
			System.out.println("mGridView为空");
		}
		mGridView.setAdapter(new MouldAdapter(getActivity(),mThumbIds));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				//点击不同的素材模板给发帖模块背景进行配色
				((ImageView)getActivity().findViewById(R.id.create_tiezi_imageview1)).setBackgroundResource(mThumbIds[position]);
				Toast.makeText(context, ""+position,Toast.LENGTH_SHORT).show();//显示信息; 
			}
		});
		returnTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager()
				.beginTransaction()
				.replace(
						R.id.create_tiezi_container,
						FragmentCreatetieziNavigation
								.getInstance(context)).commit();
			}
		});
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
	
	
}
