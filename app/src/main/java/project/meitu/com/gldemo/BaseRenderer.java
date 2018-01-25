package project.meitu.com.gldemo;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import project.meitu.com.gldemo.util.MTGLHelper;
import project.meitu.com.gldemo.util.MTGLUtil;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glViewport;

public abstract class BaseRenderer implements GLSurfaceView.Renderer {


    protected int mProgram;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        MTGLUtil.d("onSurfaceCreated");
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //编译顶点着色器
        int vertexShader = MTGLHelper.compileShader(GL_VERTEX_SHADER, vertexShader());
        //编译片段着色器
        int fragmentShader = MTGLHelper.compileShader(GL_FRAGMENT_SHADER, fragmentShader());
        //链接到程序中
        mProgram = MTGLHelper.linkProgram(vertexShader, fragmentShader);
        //告诉openGl使用该程序
        glUseProgram(mProgram);

        onSurfaceCrated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        MTGLUtil.d("onSurfaceChanged");
        glViewport(0, 0, width, height);//设置视口（viewport)的尺寸

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        MTGLUtil.d("onDrawFrame");
        glClear(GL_COLOR_BUFFER_BIT);//清空屏幕，并使用glClearColor的颜色填充屏幕
        onDrawFrame();
    }


    /**
     * 顶点着色器
     */
    protected abstract String vertexShader();

    /**
     * 片段着色器
     */
    protected abstract String fragmentShader();

    /**
     * 创建surface
     */
    protected abstract void onSurfaceCrated();

    /**
     * 绘制
     */
    protected abstract void onDrawFrame();
}
