package miyou.fragment;

import utilclass.FragmentTools;
import utilclass.ImageManager;
import utilclass.ImageTools;

import com.luluandroid.miyou.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class FragmentCreatetieziChangeBar extends Fragment {

	private Context context;
	private static FragmentCreatetieziChangeBar mFragmentCreatetieziChangeBar = null;
	private SeekBar mSeekBar;
	private TextView tips, returnText, brightnessText;// 分别是提示，返回按钮，亮度按钮，模糊按钮
	private CheckBox mitnessText;
	private int brightnessProgress = 0;
	private Drawable tempDrawable,mitTempDrawable;//分别是未经模糊处理，经过模糊处理的drawable

	private FragmentCreatetieziChangeBar(Context context) {
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
		View view = inflater.inflate(R.layout.fragment_create_tiezi_changebar,
				container, false);

		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	public static FragmentCreatetieziChangeBar getInstance(Context context) {
		if (mFragmentCreatetieziChangeBar == null) {
			mFragmentCreatetieziChangeBar = new FragmentCreatetieziChangeBar(
					context);
		}
		return mFragmentCreatetieziChangeBar;
	}


	private void initView() {
		mSeekBar = (SeekBar) getActivity().findViewById(
				R.id.create_tiezi_seekBar1);
		tips = (TextView) getActivity().findViewById(
				R.id.create_tiezi_changebar_tips);
		returnText = (TextView) getActivity().findViewById(
				R.id.create_tiezi_changebar_return);
		brightnessText = (TextView) getActivity().findViewById(
				R.id.create_tiezi_changebar_brightness);
		mitnessText = (CheckBox) getActivity().findViewById(
				R.id.create_tiezi_changebar_mistiness);
		
		//tempDrawable 暂时保存背景的图片以便进行亮度更改
		tempDrawable = ((ImageView) getActivity().findViewById(
				R.id.create_tiezi_imageview1)).getBackground();
		
		brightnessText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSeekBar.setProgress(brightnessProgress);
				tips.setText("当前" + "亮度：" + brightnessProgress);
			}
		});
		mitnessText.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (mitnessText.isChecked()) {
					tempDrawable = ((ImageView) getActivity().findViewById(
							R.id.create_tiezi_imageview1)).getBackground();
					mitTempDrawable = ImageManager.blur(
							ImageTools.drawableToBitmap(tempDrawable),
							((ImageView) getActivity().findViewById(
									R.id.create_tiezi_imageview1)),
							getActivity(), false);
					((ImageView) getActivity().findViewById(
							R.id.create_tiezi_imageview1))
							.setBackgroundDrawable(mitTempDrawable);
					((ImageView) getActivity().findViewById(
							R.id.create_tiezi_imageview1))
							.setScaleType(ImageView.ScaleType.FIT_START);
				} else {
					((ImageView) getActivity().findViewById(
							R.id.create_tiezi_imageview1))
							.setBackgroundDrawable(tempDrawable);
					((ImageView) getActivity().findViewById(
							R.id.create_tiezi_imageview1))
							.setScaleType(ImageView.ScaleType.FIT_START);
				}
			}
		});
		returnText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				getFragmentManager()
						.beginTransaction()
						.replace(
								R.id.create_tiezi_container,
								FragmentCreatetieziNavigation
										.getInstance(getActivity())).commit();
			}
		});
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				seekBar.getProgress();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ImageView tempImageView = ((ImageView) getActivity()
						.findViewById(R.id.create_tiezi_imageview1));
				Bitmap tempBitmap;
				if(mitnessText.isChecked()){
					tempBitmap =  ImageManager.BrightnessChange(ImageTools
							.drawableToBitmap(mitTempDrawable), progress);
				}else{
					 tempBitmap =  ImageManager.BrightnessChange(ImageTools
							.drawableToBitmap(tempDrawable), progress);
				}
				
				/*tempImageView.setImageBitmap(tempBitmap);*/
				tempImageView.setBackgroundDrawable(ImageTools.bitmapToDrawable(tempBitmap));
				tempImageView.setScaleType(ImageView.ScaleType.FIT_START);
				mSeekBar.setProgress(progress);
				brightnessProgress = mSeekBar.getProgress();
				tips.setText("当前" + "亮度：" + progress);
			}
		});
	}
}
