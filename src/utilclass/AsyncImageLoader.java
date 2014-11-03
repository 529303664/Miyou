package utilclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import miyou.extra.ShowToast;

import com.ant.liao.GifView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import application.control.Conf;

public class AsyncImageLoader {
	private ExecutorService mExecutorService = Executors.newFixedThreadPool(5);// 固定5个线程来执行任务
	private final Handler mHandler = new Handler();
	private InputStream ins;
	
	public void shutDownExecutorService() {
		mExecutorService.shutdown();
	}

	public Drawable loadDrawable(final Context context, final String imageUrl,
			final String imageName, final ImageCallback callback) {
		int imageUrlSuffix = imageUrl.lastIndexOf('.');
		if (imageUrlSuffix < 0)
			return null;

		if (new File(Conf.APP_SDCARD_PATH + "/cache", imageName
				+ imageUrl.substring(imageUrlSuffix)).exists()) {
			Bitmap tempbitmap1 = ImageTools.getPhotoFromSDCard(
					Conf.APP_SDCARD_PATH + "/cache", imageName,
					imageUrl.substring(imageUrlSuffix));

			Drawable drawable = ImageTools.bitmapToDrawable(tempbitmap1);
			/* callback.imageLoaded(drawable); */
			/* tempbitmap1.recycle(); */
			return drawable;
		}

		mExecutorService.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ins = new URL(imageUrl).openStream();
					final Drawable drawable = Drawable
							.createFromResourceStream(context.getResources(),
									null, ins, imageName);
					ShowToast
							.showShortToast(
									context,
									"运行到这一步："
											+ "imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));");
					mHandler.post(new Runnable() {// 未执行第二次更新
						@Override
						public void run() {
							// TODO Auto-generated method stub
							callback.imageLoaded(drawable);

							Bitmap tempbitmap = ImageTools
									.drawableToBitmap(drawable);
							ImageTools.savePhotoToSDCard(tempbitmap,
									Conf.APP_SDCARD_PATH + "/cache", imageName,
									imageUrl.substring(imageUrl
											.lastIndexOf('.')));
							tempbitmap.recycle();
						}
					});

				} catch (NullPointerException e) {
					Log.e("nullpoint", e.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new RuntimeException(e);
				}
			}

		});
		return null;
	}

	protected Drawable loadImageFromUrl(final Context context, String imageUrl,
			String srcname) {
		try {
			return Drawable.createFromResourceStream(context.getResources(),
					null, new URL(imageUrl).openStream(), srcname);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable);
	}

}
