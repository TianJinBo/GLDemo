package project.meitu.com.gldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class TestGLSurfaceView extends GLSurfaceView {
    public TestGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
    }

    public TestGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
    }
}
