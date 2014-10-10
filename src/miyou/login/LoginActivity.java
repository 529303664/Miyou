package miyou.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import application.control.Conf;
import application.control.ManagerApplication;

import java.util.ArrayList;
import java.util.List;

import miyou.MainActivity;
import miyou.extra.ShowToast;
import miyou.user.User;
import statics.BroadcastString;
import cn.bmob.v3.Bmob;

import com.luluandroid.miyou.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {
	

	LocalBroadcastManager lbm;
	SignAndLoginReceiver signAndLoginReceiver;
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	/*
	 * private static final String[] DUMMY_CREDENTIALS = new String[] {
	 * "foo@example.com:hello", "bar@example.com:world" };
	 */
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserSignAndLoginTask mAuthTask = null;
	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	private TextView forgetTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		ManagerApplication.getInstance().addActivity(this);
		// Set up the login form.

		initBroadcast();
		initComponent();
		ManagerApplication.getInstance().addActivity(this);
	}

	private void initComponent() {
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		populateAutoComplete();

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptSignOrLogin(false);
							return true;
						}
						return false;
					}
				});

		forgetTextView = (TextView) findViewById(R.id.email_forgetpassword);
		forgetTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View forcusView = null;
				if(!checkValidEmail(forcusView,mEmailView.getText().toString())){
					User.getUserIsntance().resetUserPassword(LoginActivity.this, mEmailView.getText().toString());
				}
				forcusView = null;
			}
		});

		Button mEmailLoginInButton = (Button) findViewById(R.id.email_login_in_button);
		mEmailLoginInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptSignOrLogin(false);
			}
		});

		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				attemptSignOrLogin(true);
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
	}

	private void initBroadcast() {
		signAndLoginReceiver = new SignAndLoginReceiver();
		lbm = LocalBroadcastManager.getInstance(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadcastString.ACTION_LOGIN_FAILURE);
		filter.addAction(BroadcastString.ACTION_LOGIN_SUCCESS);
		filter.addAction(BroadcastString.ACTION_SIGNIN_FAILURE);
		filter.addAction(BroadcastString.ACTION_SIGNIN_SUCCESS);
		filter.addAction(BroadcastString.ACTION_EMAIL_RESET_SUCCESS);
		filter.addAction(BroadcastString.ACTION_EMAIL_RESET_FAILURE);
		lbm.registerReceiver(signAndLoginReceiver, filter);
	}

	private void populateAutoComplete() {
		if (VERSION.SDK_INT >= 14) {
			// Use ContactsContract.Profile (API 14+)
			getLoaderManager().initLoader(0, null, this);
		} else if (VERSION.SDK_INT >= 8) {
			// Use AccountManager (API 8+)
			new SetupEmailAutoCompleteTask().execute(null, null);
		}
	}

	private boolean checkValidEmail(View focusView,String email){
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			focusView.requestFocus();
			return false;
		} else if (!isEmailValid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			focusView.requestFocus();
			return false;
		}
		return true;
	}
	
	private boolean checkValidPassword(View focusView,String password){
		if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			focusView.requestFocus();
			return false;
		}else
		return true;
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptSignOrLogin(boolean toSignIn) {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		View focusView = null;

		// Check for a valid email address.
		if(!checkValidEmail(focusView,email)) return;
		
		// Check for a valid password, if the user entered one.
		if(!checkValidPassword(focusView,password))return;
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			if (toSignIn) {
				mAuthTask = new UserSignAndLoginTask(true, email, password);
			} else {
				mAuthTask = new UserSignAndLoginTask(false, email, password);
			}
			mAuthTask.execute((Void) null);
	}

	private boolean isEmailValid(String email) {
		// TODO: Replace this with your own logic
		return email.contains("@");
	}

	private boolean isPasswordValid(String password) {
		// TODO: Replace this with your own logic
		return password.length() > 4;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	/**
	 * Use an AsyncTask to fetch the user's email addresses on a background
	 * thread, and update the email text field with results on the main UI
	 * thread.
	 */
	class SetupEmailAutoCompleteTask extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... voids) {
			ArrayList<String> emailAddressCollection = new ArrayList<String>();

			// Get all emails from the user's contacts and copy them to a list.
			ContentResolver cr = getContentResolver();
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					null, null, null);
			while (emailCur.moveToNext()) {
				String email = emailCur
						.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				emailAddressCollection.add(email);
			}
			emailCur.close();

			return emailAddressCollection;
		}

		@Override
		protected void onPostExecute(List<String> emailAddressCollection) {
			addEmailsToAutoComplete(emailAddressCollection);
		}
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserSignAndLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;
		private boolean isSignIn;

		UserSignAndLoginTask(boolean isSignIn, String email, String password) {
			mEmail = email;
			mPassword = password;
			this.isSignIn = isSignIn;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			if (isSignIn) {
				User.getUserIsntance().toSignIn(LoginActivity.this, mEmail,
						mPassword);
			} else {
				User.getUserIsntance().toLogin(LoginActivity.this, mEmail,
						mPassword);
			}
			return true;
			// TODO: register the new account here.
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			isSignIn = false;
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction(BroadcastString.ACTION_LOGIN_SUCCESS_RETURN_TO_MAIN);
		if(lbm.sendBroadcast(intent))
		{
			Log.i("returntomain", "成功发送跳转广播");
		}
		lbm.unregisterReceiver(signAndLoginReceiver);
		super.onDestroy();
		
	}

	public void AfterLogin() {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public class SignAndLoginReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			showProgress(false);
			if (intent.getAction().equals(BroadcastString.ACTION_LOGIN_SUCCESS)) {
				AfterLogin();
			} else if (intent.getAction().equals(BroadcastString.ACTION_LOGIN_FAILURE)) {
				ShowToast.showShortToast(context,
						"登录失败" + User.getUserIsntance().whatHappen);
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			} else if (intent.getAction().equals(BroadcastString.ACTION_SIGNIN_SUCCESS)) {

				ShowToast.showShortToast(context, "注册成功..……正在跳转到主页面……");
				attemptSignOrLogin(false);

			} else if (intent.getAction().equals(BroadcastString.ACTION_SIGNIN_FAILURE)) {

				ShowToast.showShortToast(LoginActivity.this,
						"注册失败：" + User.getUserIsntance().whatHappen);
				return;
			}else if(intent.getAction().equals(BroadcastString.ACTION_EMAIL_RESET_SUCCESS)){
				ShowToast.showShortToast(LoginActivity.this,
						"重置密码成功，请到该邮箱进行密码重置" );
			}else if(intent.getAction().equals(BroadcastString.ACTION_EMAIL_RESET_FAILURE)){
				ShowToast.showShortToast(LoginActivity.this,
						"重置密码失败：" + User.getUserIsntance().whatHappen);
			}
		}

	}
}
