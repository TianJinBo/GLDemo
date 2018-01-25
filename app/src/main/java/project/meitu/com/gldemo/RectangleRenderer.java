package project.meitu.com.gldemo;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glVertexAttribPointer;

public class RectangleRenderer extends BaseRenderer {
    /** 每个float占4个字节 */
    private static final int BYTE_PER_FLOAT = 4;
    /** 标记每个点由两个坐标组成 */
    private static final int POSITION_COMPONENT_COUNT = 2;
    /** 标记每个点颜色值的个数 */
    private static final int COLOR_COMPONENT_COUNT = 3;
    /** 每个点实际所占字节数 */
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTE_PER_FLOAT;
    /** 顶点坐标属性 */
//    float[] rectangleVertices = new float[]{/* 三角形1 */-0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f,
//    /* 三角形2 */-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
//    /* 线 */-0.5f, 0, 0.5f, 0,
//    /* 点1 */0, 0.25f,
//    /* 点2 */0, -0.25f};
    float[] rectangleVertices_fun = new float[]{ /* x,y,r,g,b */
            /* 三角形扇 */
            0f, 0f, 1f, 1f, 1f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            /* 线 */
            -0.5f, 0, 1f, 0f, 0f,
            0.5f, 0, 1f, 0f, 0f,
            /* 点1 */
            0, -0.25f, 0f, 0f, 1f,
            /* 点2 */
            0, 0.25f, 1f, 0f, 0f

    };
    /** 存储在本地的内存 */
    private FloatBuffer vertexData;

    /**
     * uniform color的位置
     */
    private int a_ColorLocation;
    /**
     * attribute position 的位置
     */
    private int a_PositionLocation;

    public RectangleRenderer() {
        super();
        //创建本地内存
        vertexData = ByteBuffer.allocateDirect(rectangleVertices_fun.length * BYTE_PER_FLOAT)//分配本地内存，不被回收
                .order(ByteOrder.nativeOrder())//告诉字节缓冲区按照本地字节序排序
                .asFloatBuffer();
        vertexData.put(rectangleVertices_fun);//把数据从虚拟机复制到本地内存
    }

    @Override
    protected String vertexShader() {
        return "attribute vec4 a_Position;" +
                "attribute vec4 a_Color;" +
                "varying vec4 v_Color;" +
                "void main(){" +
                "v_Color = a_Color;" +
                "gl_Position = a_Position;" +
                "gl_PointSize = 10.0;" +
                "}";
    }

    @Override
    protected String fragmentShader() {
        return "precision mediump float;" +
                "varying vec4 v_Color;" +
                "void main(){" +
                "gl_FragColor = v_Color;" +
                "}";
    }

    @Override
    protected void onSurfaceCrated() {
        //获取attribute a_Color位置
        a_ColorLocation = glGetAttribLocation(mProgram, "a_Color");
        //获取attribute a_Position位置
        a_PositionLocation = glGetAttribLocation(mProgram, "a_Position");
        //缓冲区指针位置置为0
        vertexData.position(0);
        //告诉gl取点的位置
        glVertexAttribPointer(a_PositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        //允许gl获取点
        glEnableVertexAttribArray(a_PositionLocation);

        //缓冲区指针位置置为第一个顶点的颜色数据
        vertexData.position(POSITION_COMPONENT_COUNT);
        //告诉gl取点的位置
        glVertexAttribPointer(a_ColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        //允许gl获取点
        glEnableVertexAttribArray(a_ColorLocation);
    }

    @Override
    protected void onDrawFrame() {
        //更新u_Color的颜色
//        glUniform4f(a_ColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        //画三角形，从0开始取6组顶点
//        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
//        //画线
////        glUniform4f(a_ColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);
//        //画点
////        glUniform4f(a_ColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 2);
//        glDrawArrays(GL_POINTS, 9, 1);

    }
}
