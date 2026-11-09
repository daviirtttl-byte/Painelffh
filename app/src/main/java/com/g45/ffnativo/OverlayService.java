package com.g45.ffnativo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OverlayService extends Service {
    private WindowManager wm;
    private View menu;
    private ESPView espView;

    static { System.loadLibrary("ffnativo"); }
    public native void toggleEsp(boolean enable);

    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        espView = new ESPView(this);
        WindowManager.LayoutParams p1 = new WindowManager.LayoutParams(-1, -1, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, PixelFormat.TRANSLUCENT);
        wm.addView(espView, p1);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#E6000000"));
        bg.setCornerRadius(40);
        bg.setStroke(3, Color.parseColor("#00FFCC"));
        layout.setBackground(bg);
        layout.setPadding(30, 30, 30, 30);

        Button btnEsp = new Button(this);
        btnEsp.setText("ESP: OFF");
        btnEsp.setOnClickListener(v -> {
            boolean isOn = btnEsp.getText().toString().contains("ON");
            btnEsp.setText(isOn ? "ESP: OFF" : "ESP: ON");
            btnEsp.setBackgroundColor(isOn ? Color.DKGRAY : Color.GREEN);
            espView.setEspEnabled(!isOn);
            toggleEsp(!isOn);
        });

        layout.addView(btnEsp);
        WindowManager.LayoutParams p2 = new WindowManager.LayoutParams(-2, -2, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        p2.gravity = Gravity.TOP | Gravity.LEFT; p2.x = 100; p2.y = 200;
        menu = layout;
        wm.addView(menu, p2);
    }
    @Override public IBinder onBind(Intent i) { return null; }
    class ESPView extends View {
        private Paint paint; private boolean isEnabled = false;
        public ESPView(Context c) { super(c); paint = new Paint(); paint.setColor(Color.RED); paint.setStyle(Paint.Style.STROKE); paint.setStrokeWidth(5f); }
        public void setEspEnabled(boolean b) { isEnabled = b; invalidate(); }
        @Override protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (isEnabled) canvas.drawRect(canvas.getWidth()/2f - 100, canvas.getHeight()/2f - 200, canvas.getWidth()/2f + 100, canvas.getHeight()/2f + 200, paint);
            invalidate();
        }
    }
}
