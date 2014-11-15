package miyou.createtiezi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import miyou.MainActivity;
import miyou.extra.ShowToast;
import miyou.fragment.FragmentCreatetieziNavigation;
import statics.ChannelCodes;
import utilclass.ImageManager;
import utilclass.ImageTools;

import com.luluandroid.miyou.R;

import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import application.control.Conf;
import application.control.ManagerApplication;

public class CreateTieziActivity extends ActionBarActivity {

	private ActionBar actionbar;
	private Fragment currentFragment;
	private ProgressBar progressBar;
	private loadPictureAsynctask myLoadTask;
	private FrameLayout navigationFrameLayout;
	private ImageView navigationLfBt, navigationRtBt, imageBackgroud;
	private boolean NvIsOpen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_tiezi);
		if (savedInstanceState == null) {
			currentFragment = FragmentCreatetieziNavigation.getInstance(this);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.create_tiezi_container, currentFragment).commit();
		}
		InitActionBar();
		initComponent();
		ManagerApplication.getInstance().addActivity(this);
	}

	private void initComponent() {
		navigationLfBt = (ImageView) findViewById(R.id.create_tiezi_controlbar_left_button);
		navigationRtBt = (ImageView) findViewById(R.id.create_tiezi_controlbar_right_button);
		navigationFrameLayout = (FrameLayout) findViewById(R.id.create_tiezi_container);
		progressBar = (ProgressBar) findViewById(R.id.create_tiezi_progressBar1);
		imageBackgroud = (ImageView) findViewById(R.id.create_tiezi_imageview1);
		NvIsOpen = false;
		final Animation navigationEtAnimation = AnimationUtils.loadAnimation(
				this, R.anim.create_tiezi_navigation_enter);
		final Animation navigationExAnimation = AnimationUtils.loadAnimation(
				this, R.anim.create_tiezi_navigation_exit);
		navigationLfBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!NvIsOpen) {
					navigationLfBt.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.ic_action_collapse));
					navigationFrameLayout.startAnimation(navigationEtAnimation);
					navigationFrameLayout.setVisibility(View.VISIBLE);
					NvIsOpen = true;
				} else {
					navigationLfBt.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.ic_action_expand));
					navigationFrameLayout.startAnimation(navigationExAnimation);
					navigationFrameLayout.setVisibility(View.GONE);
					NvIsOpen = false;
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_tiezi, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
		switch (id) {
		case R.id.home:
			/*
			 * Intent intent = new Intent(this,MainActivity.class);
			 * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 * startActivity(intent);
			 */
			finish();
		case R.id.action_create_tiezi_write:
			// 数据发送
			setResult(ChannelCodes.CREATE_TIEZI_SUCCESS);
			finish();
		default:
			break;
		}
		/*
		 * if (id == R.id.action_settings) { return true; }
		 */
		return super.onOptionsItemSelected(item);
	}

	public void InitActionBar() {
		actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
	}

	public void switchFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.create_tiezi_container, fragment).commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		case Conf.TAKE_PICTURE:// 调用相机获得照片后的操作
			// 将保存在本地的图片取出并缩小后显示在界面上
			Bitmap bitmap = BitmapFactory.decodeFile(Conf.APP_SDCARD_CACHE_PATH
					+ "/" + "image.jpg");
			if (bitmap == null)
				break;
			// 处理图片
			myLoadTask = new loadPictureAsynctask(progressBar, imageBackgroud,
					true);
			myLoadTask.execute(bitmap);
			break;
		case Conf.CHOOSE_PICTURE:
			ContentResolver resolver = getContentResolver();
			// 照片的原始资源地址
			if (data == null)
				break;
			Uri originalUri = data.getData();

			try {
				Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,
						originalUri);
				if (photo != null) {
					// 处理图片
					myLoadTask = new loadPictureAsynctask(progressBar,
							imageBackgroud, false);
					myLoadTask.execute(photo);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Conf.CROP:
			Uri uri = null;
			if (data != null) {
				uri = data.getData();
			} else {
				String fileName = getSharedPreferences("tempImage",
						Context.MODE_PRIVATE).getString("tempName", "");
				uri = Uri.fromFile(new File(Conf.APP_SDCARD_CACHE_PATH,
						fileName));
			}
			// 按手机屏幕分辨率的一半形式裁剪图片
			ImageManager.cropImage(CreateTieziActivity.this, uri,
					ImageManager.getPhoneWidth(this) / 2,
					ImageManager.getPhoneHeight(this) / 2, Conf.CROP_PICTURE);
			break;
		case Conf.CROP_PICTURE:
			Bitmap photo = null;
			if (data == null)
				break;
			Uri photoUri = data.getData();
			if (photoUri != null)
				photo = BitmapFactory.decodeFile(photoUri.getPath());
			if (photo == null) {
				Bundle extra = data.getExtras();
				if (extra != null) {
					photo = (Bitmap) extra.get("data");
				} else
					break;
			}
			// 处理图片
			myLoadTask = new loadPictureAsynctask(progressBar, imageBackgroud,
					false);
			myLoadTask.execute(photo);
			break;
		default:
			break;
		}
	}

	class loadPictureAsynctask extends AsyncTask<Bitmap, Integer, String> {

		private Bitmap pictureImage;
		private ProgressBar progressBar;
		private ImageView imageBackgroud;
		private boolean flag;// 压缩和不压缩标志位
		private String fileSavePath = "";

		public loadPictureAsynctask(ProgressBar progressBar,
				ImageView imageBackgroud, boolean flag) {
			super();
			this.progressBar = progressBar;
			this.imageBackgroud = imageBackgroud;
			this.flag = flag;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			ShowToast.showShortToast(CreateTieziActivity.this, "照片本地保存成功");
			fileSavePath = "";
			progressBar.setIndeterminate(false);
			progressBar.setVisibility(View.GONE);
			if (pictureImage != null) {
				pictureImage.recycle();
			}
			super.onCancelled();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if ("success".equals(result)) {
				imageBackgroud.setImageBitmap(pictureImage);
				Log.i("userImageFilePath", "执行到了照片这一步");
				Log.i("userImageFilePath", fileSavePath);
				// 下面开始上传图片
			}
			ShowToast.showShortToast(CreateTieziActivity.this, "照片本地保存"
					+ result);
			progressBar.setIndeterminate(false);
			progressBar.setVisibility(View.GONE);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			progressBar.setIndeterminate(true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(Bitmap... params) {
			// TODO Auto-generated method stub
			// 字节流形式存储图片
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ByteArrayInputStream isBm = null;
			String strfilename = String.valueOf(System.currentTimeMillis());
			if (flag == true) {// 压缩
				// 保存原图片的40%质量作为最终显示的图片
				params[0].compress(CompressFormat.JPEG, 40, baos);

			} else {
				params[0].compress(CompressFormat.JPEG, 100, baos);
			}
			isBm = new ByteArrayInputStream(baos.toByteArray());
			try {
				pictureImage = ImageTools
						.compressBySize(
								isBm,
								ImageManager
										.getPhoneWidth(CreateTieziActivity.this) / 2,
								ImageManager
										.getPhoneHeight(CreateTieziActivity.this) / 2);
				ImageTools.deletePhotoAtPathAndName(Conf.APP_SDCARD_CACHE_PATH,
						"image" + ".jpg");
				ImageTools.savePhotoToSDCard(pictureImage,
						Conf.APP_SDCARD_CACHE_PATH, strfilename, ".jpg");
				fileSavePath = Conf.APP_SDCARD_CACHE_PATH + strfilename
						+ ".jpg";
				params[0].recycle();
				baos.close();
				isBm.close();
				return "success";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.i("Compress", "照片压缩失败" + e.toString());
				e.printStackTrace();
				return "failure";
			}

		}

	}

}
