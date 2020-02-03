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
            return generateVerticalBitmap(paint, content, dp2px(3));
        }
    }

    private Bitmap generateVerticalBitmap(Paint paint, String content, int lineSpacing) {
        Rect rect = new Rect();
        float bitmapWidth = 0;
        int bitmapHeight = 0;
        for (int i = 0; i < content.length(); i++) {
            float measureWidth = paint.measureText(content.charAt(i) + "");
            if (measureWidth > bitmapWidth) {
                bitmapWidth = measureWidth;
            }
            paint.getTextBounds(content, i, i + 1, rect);
            if (i != 0) {
                bitmapHeight += lineSpacing;
            }
            int height = rect.width() / 2 > rect.height() ? rect.width() / 2 : rect.height();
            bitmapHeight += height;
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
            int height = rect.width() / 2 > rect.height() ? rect.width() / 2 : rect.height();
            int bottom = drawedHeight + (height - rect.height()) / 2 - rect.top;
            canvas.drawText(content.charAt(i) + "", left, bottom, paint);
                drawedHeight += height;
                drawedHeight += lineSpacing;
        }
        canvas.restore();
        return bitmap;
    }

    private Bitmap generateHorizontalBitmap(Paint paint, String content) {
        float bitmapWidth = paint.measureText(content);
        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        int bitmapHeight = rect.height();
        Bitmap bitmap = Bitmap.createBitmap((int) bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        canvas.drawColor(Color.RED);
        canvas.drawText(content, 0, -rect.top, paint);
        canvas.restore();
        return bitmap;
    }

    private int dp2px(float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
