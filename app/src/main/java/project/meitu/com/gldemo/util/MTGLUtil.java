package project.meitu.com.gldemo.util;


import android.graphics.Color;
import android.opengl.GLES20;
import android.support.annotation.ColorInt;
import android.util.Log;

public class MTGLUtil {
    private static final String TAG = "[T_openGL]";

    public static void d(String content) {
        Log.d(TAG, content);
    }

    /**
     * 输出着色器信息
     */
    public static void d(int shader) {
        d(GLES20.glGetShaderInfoLog(shader));
    }


    public static float[] getColor(@ColorInt int color) {
        float red = (float) Color.red(color) / 255f;
        float green = (float) Color.green(color) / 255f;
        float blue = (float) Color.blue(color) / 255f;
        return new float[]{red, green, blue};
    }

    public static float[] getColor(String color) {
        return getColor(Color.parseColor(color));
    }
}
