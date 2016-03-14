package twiscode.masakuuser.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;

import org.json.JSONObject;

import java.util.HashMap;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.font.MaterialDesignIconsTextView;
import twiscode.masakuuser.Utilities.kbv.KenBurnsView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SplashScreensActivity extends Activity {

	public static final String SPLASH_SCREEN_OPTION = "twiscode.masakuuser.Activity.SplashScreensActivity";
	public static final String SPLASH_SCREEN_OPTION_1 = "Fade in + Ken Burns";
	public static final String SPLASH_SCREEN_OPTION_2 = "Down + Ken Burns";
	public static final String SPLASH_SCREEN_OPTION_3 = "Down + fade in + Ken Burns";
	
	private KenBurnsView mKenBurns;
	private TextView mLogo;
	private TextView welcomeText;
	private DatabaseHandler db;
	private ProgressBar mProgressBar;
	private int mWaited = 0;
	private Activity mActivity;
	ApplicationManager appManager;
	Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); //Removing ActionBar
		setContentView(R.layout.activity_splash_screen);
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("fonts/Gotham.ttf")
				.setFontAttrId(R.attr.fontPath)
				.build());
		mActivity = this;
		ctx = this;
		db = new DatabaseHandler(this);
		appManager = new ApplicationManager(ctx);
		mProgressBar = (ProgressBar) findViewById(R.id.splash_progress);
		mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
		mLogo = (TextView) findViewById(R.id.logo);
		welcomeText = (TextView) findViewById(R.id.welcome_text);
		ApplicationData.cart = new HashMap<>();
		ApplicationData.isFirstSpeed = true;
		String category = SPLASH_SCREEN_OPTION_3;
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(SPLASH_SCREEN_OPTION)) {
			category = extras.getString(SPLASH_SCREEN_OPTION, SPLASH_SCREEN_OPTION_1);
		}
		setAnimation(category);



	}

	@Override
	public void onBackPressed() {
		finish();
	}
	
	/** Animation depends on category.
	 * */
	private void setAnimation(String category) {
		if (category.equals(SPLASH_SCREEN_OPTION_1)) {
			mKenBurns.setImageResource(R.drawable.pic);
			animation1();
		} else if (category.equals(SPLASH_SCREEN_OPTION_2)) {
			mLogo.setTextColor(R.color.main_color_500);
			mKenBurns.setImageResource(R.drawable.pic);
			animation2();
		} else if (category.equals(SPLASH_SCREEN_OPTION_3)) {
			mKenBurns.setImageResource(R.drawable.pic);
			animation2();
			animation3();
		}

		final Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {

					for (int i = 0; i <= 1000; i++) {
						sleep(5);
						mProgressBar.setProgress(mWaited / 10);
						mWaited += 1;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					int countUser = db.getuserCount();
					Log.d("db count", "" + countUser);
					ApplicationData.isFirstLogin = true;
					if (countUser > 0) {
                        /*
                        Intent i = new Intent(getBaseContext(), ActivityHome.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        */
						new RefreshToken(mActivity).execute();
					} else {
						Intent i = new Intent(getBaseContext(), ActivityLogin.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
						finish();
					}
				}
			}
		};
		splashThread.start();
	}

	private void animation1() {
		ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
		scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleXAnimation.setDuration(1200);
		ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
		scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleYAnimation.setDuration(1200);
		ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
		alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		alphaAnimation.setDuration(1200);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
		animatorSet.setStartDelay(500);
		animatorSet.start();
	}
	
	private void animation2() {
		mLogo.setAlpha(1.0F);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
		mLogo.startAnimation(anim);
	}
	
	private void animation3() {
		ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
		alphaAnimation.setStartDelay(1700);
		alphaAnimation.setDuration(500);
		alphaAnimation.start();
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	private class RefreshToken extends AsyncTask<String, Void, String> {
		String message="";
		private Activity activity;
		private Context context;
		private Resources resources;
		private ProgressDialog progressDialog;
		String error = "";

		public RefreshToken(Activity activity) {
			super();
			this.activity = activity;
			this.context = activity.getApplicationContext();
			this.resources = activity.getResources();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
            /*
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Signing Up. . .");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            */
		}

		@Override
		protected String doInBackground(String... params) {
			try {


				JSONControl jsControl = new JSONControl();
				Log.d("last token", appManager.getUserToken());
				JSONObject responseRegister = jsControl.postRefreshToken(appManager.getUserToken());
				Log.d("json refreshtoken", responseRegister.toString());
				String token = responseRegister.getString("token");
				appManager.setUserToken(token);

				JSONObject response = jsControl.getInit();
				Log.d("json response init", response.toString());
				String notice =  response.getString("notice");
				String couponHint = response.getString("couponHint");
				ApplicationData.notice = notice;
				ApplicationData.couponHint = couponHint;

				return "OK";

			} catch (Exception e) {
				e.printStackTrace();
			}
			return "FAIL";

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			//progressDialog.dismiss();
			switch (result) {
				case "FAIL":
					Intent i = new Intent(getBaseContext(), ActivityLogin.class);
					startActivity(i);
					finish();
					break;
				case "OK":
					try {
						ModelUser user = appManager.getUser();
						ApplicationData.login_id = user.getId();
						ApplicationData.name = user.getNama();
						ApplicationData.email = user.getEmail();
						ApplicationData.phoneNumber = user.getPonsel();
						VisitorInfo visitorData = new VisitorInfo.Builder()
								.name(appManager.getUser().getNama())
								.email(appManager.getUser().getEmail())
								.phoneNumber(appManager.getUser().getPonsel())
								.build();
						ZopimChat.setVisitorInfo(visitorData);
                        /*if(user.getEmail()=="" || user.getEmail().isEmpty()){
                            ApplicationData.hasEmail = false;
                        }else{
                            ApplicationData.hasEmail = true;
                        }*/
						ApplicationData.isFirstLogin = true;

					}
					catch (Exception ex){
						ex.printStackTrace();
					}
					Intent j = new Intent(getBaseContext(), Main.class);
					startActivity(j);
					finish();
					break;
			}
		}


	}

}
