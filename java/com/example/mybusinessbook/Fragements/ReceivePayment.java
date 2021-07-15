package com.example.mybusinessbook.Fragements;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybusinessbook.Activities.CustomerTransaction;
import com.example.mybusinessbook.R;
import com.example.mybusinessbook.helper.MyNotificationPublisher;
import com.example.mybusinessbook.registration;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceivePayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceivePayment extends Fragment {

    public static final String NOTIFICATION_CHANNEL_ID = "1001" ;
    DatePickerDialog picker;
    public ReceivePayment() {
        // Required empty public constructor
    }

    public static ReceivePayment newInstance(){
        return new ReceivePayment();
    }
    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_receive_payment, container, false);
        Bundle extras = this.getArguments();
        String name = extras.getString("Name");
        String number = extras.getString("Number");
//        System.out.println("hi"+name);
//        Log.d("Name","Name is"+name);
        String Choice = extras.getString("Choice","Receive");
        TextView txtname= v.findViewById(R.id.textView);
        if(Choice.equals("Land")){
            txtname.setText("You Land to "+name);
        }else{
            txtname.setText("You Got from "+name);
        }

        TextInputLayout edtAmount = v.findViewById(R.id.Amount);
        TextInputLayout edtDetails = v.findViewById(R.id.Details);
        TextInputLayout edtDate = v.findViewById(R.id.date);
        Button btnSave = v.findViewById(R.id.btnsave);
        edtDate.getEditText().setOnClickListener(c-> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            edtDate.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, year, month, day);
            picker.show();
        });
        String amount = edtAmount.getEditText().getText().toString().trim();
        String Details = edtDetails.getEditText().getText().toString().trim();
        String Date = edtDate.getEditText().getText().toString().trim();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CustomerTransaction.class);
                PendingIntent pi=PendingIntent.getActivity(getActivity(), 0, intent,0);
//                SmsManager sms=SmsManager.getDefault();
//                sms.sendTextMessage(number, null, amount, pi,null);
                if(NotificationManagerCompat.from(getActivity()).areNotificationsEnabled()){
                    if(Choice.equals("Land")){
                        ScheduleNotification(getNotification(amount,name),10);
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Scuccessfully added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void ScheduleNotification(Notification notification, int i) {
        Intent notificationIntent = new Intent( getActivity(), MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( getActivity(), 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + i ;
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }

    private Notification getNotification(String amount, String name) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(),NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle("Reminder");
        builder.setContentText("You have to collect "+ amount +" from "+name);
        builder.setSmallIcon(R.mipmap.ic_launcher_round ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build();
    }

}