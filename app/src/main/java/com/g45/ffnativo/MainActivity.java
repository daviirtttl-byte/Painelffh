package com.g45.ffnativo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;
import rikka.shizuku.Shizuku;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setBackgroundColor(Color.BLACK);
        l.setGravity(Gravity.CENTER);
        TextView t = new TextView(this);
        t.setText("Aguardando Shizuku...");
        t.setTextColor(Color.WHITE);
        Button btn = new Button(this);
        btn.setText("INICIAR PAINEL");
        btn.setOnClickListener(v -> { startService(new Intent(this, OverlayService.class)); finish(); });
        l.addView(t); l.addView(btn); setContentView(l);
        
        if (!Settings.canDrawOverlays(this)) {
            startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
        }
        if (Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            t.setText("Shizuku Autorizado!"); t.setTextColor(Color.GREEN);
        } else {
            Shizuku.requestPermission(1001);
        }
    }
}
