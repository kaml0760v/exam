package com.codedecode.loginregister.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.codedecode.loginregister.R;
import com.codedecode.loginregister.helper.SQLiteHandler;
import com.codedecode.loginregister.helper.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import static com.codedecode.loginregister.app.AppController.TAG;

public class MainActivity extends Activity {

	DatabaseReference db1;
	private Resources mResources;
	private ImageView mImageView;
	private SQLiteHandler db;
	private SessionManager session;
	private Context mContext;
	private TextView textView,textView2;
	float weight  = 0 ;
	final int DROP_RATE = 50;
	final int DROP_FACTOR = 20;
	Boolean alert = true;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnLogout = findViewById(R.id.btnLogout);
		textView = findViewById(R.id.text1);
		textView2 = findViewById(R.id.text2);
		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
//			calliing logout function if session time out
			logoutUser();
		}

//		// Fetching user details from SQLite
//		HashMap<String, String> user = db.getUserDetails();
//
//		String name = user.get("name");
//		String email = user.get("email");
//
//		// Displaying the user details on the screen
//		txtName.setText(name);
//		txtEmail.setText(email);

		// Logout button click event
//		event declaration for logout button
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});

		// Get the application context
		mContext = getApplicationContext();

		// Get the Resources
		mResources = getResources();

		// Get the widgets reference from XML layout
//		mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
//		mButton = (Button) findViewById(R.id.btn);
		mImageView = (ImageView) findViewById(R.id.iv);
		// getting reference of firebase database in db1
		db1= FirebaseDatabase.getInstance().getReference().child("testing");
		//event for fetching weight data from the FireBase
		db1.addValueEventListener(new ValueEventListener() {
			//overridden method if value change in realtime database
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for(DataSnapshot snapshot1:snapshot.getChildren())
				{
					//storing the weight of saline in variable
					String weight_value = snapshot1.getValue().toString();
					//converting the weight in float
					weight = Float.parseFloat(weight_value);
					//subtracting the IV tube and angel weight
					weight = (float) (weight-0.017-0.014);
					// Initialize a new Bitmap
					Bitmap bitmap = Bitmap.createBitmap(
							350, // Width
							635, // Height
							Bitmap.Config.ARGB_8888 // Config
					);

					// Initialize a new Canvas instance
					Canvas canvas = new Canvas(bitmap);

					// Draw a solid color on the canvas as background
					canvas.drawColor(Color.WHITE);

					// Initialize a new Paint instance to draw the rounded rectangle
					Paint paint = new Paint();
					paint.setStyle(Paint.Style.FILL);
					paint.setColor(Color.CYAN);
					paint.setAntiAlias(true);
					//initializing the border of paint
					Paint paintstroke = new Paint();
					paintstroke.setStyle(Paint.Style.STROKE);
					paintstroke.setColor(Color.BLUE);
					paintstroke.setStrokeWidth(10);

					// Initialize a new Paint instance to draw the rounded rectangle
					Paint painttwo = new Paint();
					paint.setStyle(Paint.Style.FILL);
					painttwo.setColor(Color.WHITE);
					painttwo.setAntiAlias(true);
					// Set an offset value in pixels to draw rounded rectangle on canvas
					int offset = 50;

                /*
                    public RectF (float left, float top, float right, float bottom)
                        Create a new rectangle with the specified coordinates. Note: no range
                        checking is performed, so the caller must ensure that
                        left <= right and top <= bottom.

                    Parameters
                        left  The X coordinate of the left side of the rectangle
                        top  The Y coordinate of the top of the rectangle
                        right  The X coordinate of the right side of the rectangle
                        bottom  The Y coordinate of the bottom of the rectangle
                */
					// Initialize a new RectF instance
					RectF rectF = new RectF(
							offset, // left
							offset, // top
							canvas.getWidth() - offset, // right
							canvas.getHeight() - offset // bottom
					);

					// Initialize a new RectF instance
					RectF rectFtwo = new RectF(
							offset, // left
							offset, // top
							(canvas.getWidth()) - offset, // right
							canvas.getHeight()-offset -(weight) // bottom
					);
                /*
                    public void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
                        Draw the specified round-rect using the specified paint. The roundrect
                        will be filled or framed based on the Style in the paint.

                    Parameters
                        rect : The rectangular bounds of the roundRect to be drawn
                        rx : The x-radius of the oval used to round the corners
                        ry : The y-radius of the oval used to round the corners
                        paint : The paint used to draw the roundRect
                */

					// Define the corners radius of rounded rectangle
					int cornersRadius = 25;
					// Finally, draw the rounded corners rectangle object on the canvas
					canvas.drawRoundRect(
							rectF, // rect
							cornersRadius, // rx
							cornersRadius, // ry
							paint // Paint
					);
					// draw the second rounded corner rectangle of white color
					canvas.drawRoundRect(
							rectFtwo, // rect
							cornersRadius, // rx
							0, // ry
							painttwo // Paint
					);
					canvas.drawRoundRect(rectF,cornersRadius,cornersRadius,paintstroke);

					// Display the newly created bitmap on app interface
					mImageView.setImageBitmap(bitmap);
					mImageView.setImageBitmap(bitmap);
					float temp = (weight*500)/535;
					// calculation for the estimated time
					int ESTIMATE_TIME = (int)temp/(DROP_RATE/DROP_FACTOR);
					//displaying the estimate time in textbox
					textView2.setText("Saline Bottle will be finish in\n "+String.valueOf(ESTIMATE_TIME)+" Minutes");

					//Log.d("hello","weight is "+String.format("%.2f",temp));
					//displaying the available fluid in textbox
					textView.setText(String.valueOf(String.format("%.2f",temp))+" ML");
					// condition for notification
					if(temp <= 125 && alert == true){
						gen_Notification();
						if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                        {
                            NotificationChannel channel = new NotificationChannel("Custom Notification","Custom Notification",NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            manager.createNotificationChannel(channel);
                        }
						alert = false;
					}
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menus,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		if(id ==R.id.logout)
		{
			logoutUser();
		}
		return super.onOptionsItemSelected(item);
	}
	// function for the notification generation
	private void gen_Notification() {
	    Log.d("hello","notification");
		NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"Custom Notification");
		// title of the notification
		builder.setContentTitle("Help");
		//content of the notification
		builder.setContentText("Please visit Bed No. 1");
		//setting the Icon of notification
		builder.setSmallIcon(R.drawable.logo_round);
		builder.setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
        manager.notify(1,builder.build());

	}

	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void logoutUser() {
		session.setLogin(false);
		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}