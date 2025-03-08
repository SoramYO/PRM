package com.example.lab8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Khai báo hằng số cho Notification Channel
    private static final String CHANNEL_ID = "test_channel_id";
    private static final String CHANNEL_NAME = "Test Channel";
    private static final String CHANNEL_DESCRIPTION = "Channel for test notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tạo Notification Channel cho Android 8.0 trở lên
        createNotificationChannel();

        Button notifyButton = findViewById(R.id.notifyButton);

        // Đặt sự kiện nhấn cho nút
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    /**
     * Tạo Notification Channel nếu Android version là Oreo trở lên.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Tạo channel với mức độ ưu tiên mặc định
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Lấy NotificationManager từ hệ thống và đăng ký channel
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Xây dựng và gửi thông báo.
     */
    private void sendNotification() {
        // Tạo bitmap cho icon lớn
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        // Sử dụng NotificationCompat để tạo thông báo, cung cấp channel ID đã tạo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Test push notification")
                .setContentText("Message push notification")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Gửi thông báo qua NotificationManagerCompat
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(getNotificationId(), builder.build());
    }

    /**
     * Sinh mã ID cho thông báo dựa trên thời gian hiện tại.
     */
    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}
