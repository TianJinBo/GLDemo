package project.meitu.com.gldemo.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glTexParameteri;

public class MTGLHelper {
    /**
     * 编译着色器
     *
     * @param type     着色器类型 {@link GLES20#GL_VERTEX_SHADER} {@link GLES20#GL_FRAGMENT_SHADER}
     * @param resource 源码
     * @return 着色器对象
     */
    public static int compileShader(int type, String resource) {
        //创建着色器，返回着色器对象
        int shader = GLES20.glCreateShader(type);
        //将着色器代码传入该对象
        GLES20.glShaderSource(shader, resource);
        //编译着色器
        GLES20.glCompileShader(shader);
        //返回着色器对象
        return shader;
    }

    /**
     * 链接顶点着色器和片段着色器
     *
     * @param vertexShader   顶点着色器
     * @param fragmentShader 片段着色器
     * @return 程序对象
     */
    public static int linkProgram(int vertexShader, int fragmentShader) {
        //创建program
        int programId = glCreateProgram();
        //附着着色器到程序中
        glAttachShader(programId, vertexShader);
        glAttachShader(programId, fragmentShader);
        //链接程序
        glLinkProgram(programId);
        //返回程序
        return programId;
    }

    public static int loadTexture(Context context, int resourcesId) {
        int[] textureId = new int[1];
        //生成纹理
        glGenTextures(1, textureId, 0);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourcesId);
        //绑定纹理对象
        glBindTexture(GL_TEXTURE_2D, textureId[0]);
        //纹理过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        //加载图片->纹理
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        return textureId[0];
    }

}
