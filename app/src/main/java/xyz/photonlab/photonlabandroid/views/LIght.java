package xyz.photonlab.photonlabandroid.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.io.Serializable;
import java.util.List;

public class Light implements Serializable {

    private boolean checked = false;
    private boolean settled = false;

    private int colorIdle = Color.argb(255, 230, 230, 230);
    private int colorSettle = Color.argb(255, 230, 230, 141);

    int currentColor;

    static float RADIUS = 100f;
    private static final float deg60 = (float) Math.PI / 3;
    private static final float deg30 = (float) Math.PI / 6;

    private Paint paint = new Paint();

    private float x;
    private float y;

    private Path path;
    private int direction = 1;//from 0 to 5

    public Light(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(List<Light> lights) {
        this.path = new Path();
        path.moveTo(x + RADIUS, y);
        for (int i = 1; i < 6; i++) {
            path.lineTo(x + RADIUS * (float) Math.cos(i * deg60), y + RADIUS * (float) Math.sin(i * deg60));
        }
        path.close();
        //find the source light
        float sx = x + (RADIUS * 2) * (float) Math.cos(2 * (direction + 1) * deg30 - deg30);
        float sy = y + (RADIUS * 2) * (float) Math.sin(2 * (direction + 1) * deg30 - deg30);
        boolean flag = false;
        for (Light light : lights) {
            if (light.equals(this))
                continue;
            if (Math.abs(light.getX() - sx) < 10 && Math.abs(light.getY() - sy) < 10) {
                if (light.isSettled() && Math.abs(light.getDirection() - direction) != 3)
                    flag = true;
                break;
            }
        }

        setSettled(flag);

        if (this.isSettled()) {
            currentColor = colorSettle;
        } else {
            currentColor = colorIdle;
        }
    }

    void draw(Canvas canvas) {
        paint.setColor(currentColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(this.path, paint);
        if (checked) {
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.argb(100, 33, 33, 230));
            canvas.drawPath(path, paint);
        }
        paint.setStrokeWidth(0.2f * RADIUS);
        paint.setColor(Color.argb(150, 110, 120, 103));
        float interfazeRadius = RADIUS;
        canvas.drawLine(x + interfazeRadius * (float) Math.cos(direction * deg60),
                y + interfazeRadius * (float) Math.sin(direction * deg60),
                x + interfazeRadius * (float) Math.cos((direction + 1) * deg60),
                y + interfazeRadius * (float) Math.sin((direction + 1) * deg60), paint);
        //canvas.drawCircle(sx, sy, 20, paint);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}