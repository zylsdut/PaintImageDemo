package com.example.paintimagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrinterId;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView leftIv;
    private ImageView rightIv;
    private ImageView centerIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leftIv = findViewById(R.id.iv_left);
        rightIv = findViewById(R.id.iv_right);
        centerIv = findViewById(R.id.iv_center);
        findViewById(R.id.btn_generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap rightBitmap = doGenerate("拐一年摇一年缘分那", false);
                rightIv.setImageBitmap(rightBitmap);
                Bitmap leftBitmap = doGenerate("吃一堑长一智谢谢啊", false);
                leftIv.setImageBitmap(leftBitmap);
                Bitmap centerBitmap = doGenerate("自学成才", true);
                centerIv.setImageBitmap(centerBitmap);
            }
        });
    }

    private Bitmap doGenerate(String content, boolean horizontal) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(dp2px(30));
        paint.setColor(Color.BLACK);
        if (horizontal) {
            return generateHorizontalBitmap(paint, content);
        } else {
            return generateVerticalBitmap(paint, content);
        }
    }

    private Bitmap generateVerticalBitmap(Paint paint, String content) {
        float bitmapWidth = 0;
        Paint.FontMetrics metrics = paint.getFontMetrics();
        for (int i = 0; i < content.length(); i++) {
            float measureWidth = paint.measureText(content.charAt(i) + "");
            if (measureWidth > bitmapWidth) {
                bitmapWidth = measureWidth;
            }
        }
        float bitmapHeight = (metrics.descent - metrics.ascent) * content.length();
        Bitmap bitmap = Bitmap.createBitmap((int) bitmapWidth, (int) bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        canvas.drawColor(Color.RED);
        int drawedHeight = 0;
        for (int i = 0; i < content.length(); i++) {
            float width = paint.measureText(content.charAt(i) + "");
            int left =((int)bitmapWidth - (int) width) / 2;
            float bottom = drawedHeight - metrics.ascent;
            canvas.drawText(content.charAt(i) + "", left, (int) bottom, paint);
            drawedHeight += (metrics.descent - metrics.ascent);
        }
        canvas.restore();
        return bitmap;
    }

    /*private Bitmap generateVerticalBitmap(Paint paint, String content) {
        Rect rect = new Rect();
        float bitmapWidth = 0;
        int bitmapHeight = 0;
        Paint.FontMetrics metrics = paint.getFontMetrics();
        for (int i = 0; i < content.length(); i++) {
            float measureWidth = paint.measureText(content.charAt(i) + "");
            if (measureWidth > bitmapWidth) {
                bitmapWidth = measureWidth;
            }
            paint.getTextBounds(content, i, i + 1, rect);
            bitmapHeight += rect.height();
        }
        Bitmap bitmap = Bitmap.createBitmap((int) bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        canvas.drawColor(Color.RED);
        int drawedHeight = 0;
        for (int i = 0; i < content.length(); i++) {
            paint.getTextBounds(content, i, i + 1, rect);
            float width = paint.measureText(content.charAt(i) + "");
            int left =((int)bitmapWidth - (int) width) / 2;
            canvas.drawText(content.charAt(i) + "", left, drawedHeight - rect.top, paint);
            drawedHeight += rect.height();
        }
        canvas.restore();
        return bitmap;
    }*/

    private Bitmap generateHorizontalBitmap(Paint paint, String content) {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        Log.d("zyl", "top = " + metrics.top + "   asent = " + metrics.ascent + "   desent = " + metrics.descent + "   bottom = " + metrics.bottom + "   leading = " + metrics.leading);
        float bitmapWidth = paint.measureText(content);
        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        Log.d("zyl", "rectHeight = " + rect.height()  + "   rectTop = " + rect.top);
        float bitmapHeight = metrics.descent - metrics.ascent;
        Bitmap bitmap = Bitmap.createBitmap((int) bitmapWidth, (int) bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        canvas.drawColor(Color.RED);
        canvas.drawText(content, 0, -metrics.ascent, paint);
        canvas.restore();
        return bitmap;
    }

    private int dp2px(float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
