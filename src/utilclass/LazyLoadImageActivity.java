package utilclass;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.luluandroid.miyou.R;

public class LazyLoadImageActivity extends Activity {
	private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lazyloadimage);
		loadImage4("http://img4.3lian.com/sucai/img1/23/24.gif", R.id.image1,
				"image1");
		loadImage4("http://www.baidu.com/img/baidu_logo.gif", R.id.image2,
				"image2");
		loadImage4("http://cache.soso.com/30d/img/web/logo.gif", R.id.image3,
				"image3");
		loadImage4("http://www.baidu.com/img/baidu_logo.gif", R.id.image4,
				"image4");
		loadImage4("http://cache.soso.com/30d/img/web/logo.gif", R.id.image5,
				"image5");
	}

	@Override
	protected void onDestroy() {
		asyncImageLoader.shutDownExecutorService();
		super.onDestroy();
	}

	// 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	private void loadImage4(final String url, final int id, String imageName) {
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = asyncImageLoader.loadDrawable(this, url,
				imageName, new AsyncImageLoader.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					@Override
					public void imageLoaded(Drawable imageDrawable) {
						// TODO Auto-generated method stub
							((ImageView) findViewById(id))
							.setImageDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			((ImageView) findViewById(id)).setImageDrawable(cacheImage);
		}
	}

}
