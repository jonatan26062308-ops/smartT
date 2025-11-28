package or.school.android.navigationdrawer;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    Handler handler;
    private ImageView imageView;
    private Animation animation;
    private ProgressBar progressBar;
    private LinearLayout layout;
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        MyReceiver = new MyReceiver();
        broadcastIntent();

            registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            layout = (LinearLayout) findViewById(R.id.splashLayout);
            imageView = (ImageView) findViewById(R.id.ivSplashIcon);
            animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    imageView.startAnimation(animation);
                    // Animation has ended, hide the ProgressBar
                    progressBar.setVisibility(View.GONE);
                    //מאזין לאנימציה
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                            // Make the ProgressBar visible initially
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                startActivity(new Intent(Splash.this, HomeActivity.class));
                                finish();
                            } else if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                                startActivity(new Intent(Splash.this, MainActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }
            }, 3000);
        }
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION));


    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

}