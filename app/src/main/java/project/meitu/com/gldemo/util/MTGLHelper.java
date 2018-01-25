package project.meitu.com.gldemo.util;


import android.opengl.GLES20;

import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glLinkProgram;

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

}
