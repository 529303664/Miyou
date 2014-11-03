package com.luluandroid.miyou;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import miyou.extra.ShowToast;
import utilclass.AsyncImageLoader;

import android.support.v7.app.ActionBarActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

public class ShowImageActivity extends ActionBarActivity implements
		ViewFactory, OnTouchListener {
	private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	private boolean isplay = false;
	private ImageSwitcher mImageSwitcher;
	private Map<String, String> ImageUrl = new HashMap<String, String>();
	private int currentPosition;// 当前选中的图片序号
	private float downX;// 按下点的x坐标

	private Animation leftIn;
	private Animation leftOut;
	private Animation rightIn;
	private Animation rightOut;

	/**
	 * 点点数组
	 */
	private ImageView[] tips;

	/**
	 * 装载点点的容器
	 */
	private LinearLayout linearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_image);
		loadAnimation();
		initComponent();
		loadImageUrl();
		initTips();

	}

	private void loadImageUrl() {
		ImageUrl.put("image0", "http://img4.3lian.com/sucai/img1/23/24.gif");
		ImageUrl.put(
				"image1",
				"http://img1.gamersky.com/image2014/07/20140730LL_1/gamersky_11origin_21_2014730947C85.jpg");
		ImageUrl.put("image2", "http://img10.3lian.com/c1/newpic/10/08/22.jpg");
		loadImage4(ImageUrl.get("image0"), "image0");// 测试默认是0
		currentPosition = 0;// 测试默认是0
	}

	private void loadAnimation() {
		leftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
		leftOut = AnimationUtils.loadAnimation(this, R.anim.left_out);
		rightIn = AnimationUtils.loadAnimation(this, R.anim.rigtht_in);
		rightOut = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}

	private void initTips() {
		tips = new ImageView[ImageUrl.size()];
		for (int i = 0; i < ImageUrl.size(); i++) {
			ImageView tImageView = new ImageView(this);
			tips[i] = tImageView;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.rightMargin = 3;
			layoutParams.leftMargin = 3;
			tImageView
					.setBackgroundResource(R.drawable.page_indicator_unfocused);
			linearLayout.addView(tImageView, layoutParams);
		}

		setImageBackGround(0);// 测试默认是0
	}

	private void initComponent() {
		linearLayout = (LinearLayout) findViewById(R.id.viewGroup);
		mImageSwitcher = (ImageSwitcher) findViewById(R.id.image_switch1);
		mImageSwitcher.setFactory(this);// 设置Factory
		/*mImageSwitcher.setImageDrawable(getResources().getDrawable(
				R.drawable.default_picture));*/
		mImageSwitcher.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_image, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private Drawable isImageCacheHave(String imageName){
		if(imageCache.containsKey(imageName))
			return imageCache.get(imageName).get();
		return null;
	}
	
	// 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	private void loadImage4(final String url, String imageName) {
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
			mImageSwitcher.setVisibility(View.VISIBLE);
			Drawable cacheImage = null;
			cacheImage = isImageCacheHave(imageName);
			if(cacheImage!=null){
				((ImageSwitcher) findViewById(R.id.image_switch1))
				.setImageDrawable(cacheImage);
				return;
			}
				cacheImage = asyncImageLoader.loadDrawable(this, url,
					imageName, new AsyncImageLoader.ImageCallback() {
						// 请参见实现：如果第一次加载url时下面方法会执行
						@Override
						public void imageLoaded(Drawable imageDrawable) {
							// TODO Auto-generated method stub
							((ImageSwitcher) findViewById(R.id.image_switch1))
									.setImageDrawable(imageDrawable);
							imageCache.put(url, new SoftReference<Drawable>(imageDrawable));

						}
					});
			if (cacheImage != null) {
				((ImageSwitcher) findViewById(R.id.image_switch1))
						.setImageDrawable(cacheImage);
			}
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackGround(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems)
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			else
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			float lastX = event.getX();
			if (lastX > downX) { // 向左划，返回上一张
				ShowToast.showShortToast(this, "向左划");
				mImageSwitcher.setInAnimation(leftIn);
				mImageSwitcher.setOutAnimation(leftOut);
				if (currentPosition > 0) {
					currentPosition--;
				} else {
					currentPosition = ImageUrl.size() - 1;
				}
				loadImage4(ImageUrl.get("image" + currentPosition), "image"
						+ currentPosition);
				setImageBackGround(currentPosition);
			}
			if (lastX < downX) {//向右划,读取下一张
				ShowToast.showShortToast(this, "向右划");
				mImageSwitcher.setInAnimation(rightIn);
				mImageSwitcher.setOutAnimation(rightOut);
				if (currentPosition < ImageUrl.size() - 1) {
					currentPosition++;
				} else {
					currentPosition = 0;
				}
				loadImage4(ImageUrl.get("image" + currentPosition), "image"
						+ currentPosition);
				setImageBackGround(currentPosition);
			}
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		final ImageView i = new ImageView(this);
		i.setBackgroundColor(0xff000000);
		i.setScaleType(ImageView.ScaleType.CENTER_CROP);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return i;
	}

}
