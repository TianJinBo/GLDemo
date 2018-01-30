package project.meitu.com.gldemo;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import project.meitu.com.gldemo.util.MTGLHelper;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by meitu on 2018/1/30.
 */

public class PicRenderer extends BaseRenderer {
    private static final int BYTE_PER_FLOAT = 4;
    private static final String U_TEXTURE_UNIT = "u_TextureUnit";

    private static final String A_POSITION = "a_Position";
    private static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    private int uTextureUnitLocation;

    private int aPositionLocation;
    private int aTextureCoordiantesLocation;

    private FloatBuffer floatBuffer;
    private FloatBuffer textureBuffer;
    private static final int VERTEX_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    //    float[] vertexPoints = new float[]{//x,y,s,t
//            1, 1, 1, 0,
//            -1, 1, 0, 0,
//            -1, -1, 0, 1,
//            1, 1, 1, 0,
//            -1, -1, 0, 1,
//            1, -1, 1, 1
//    };
    float[] vertexPoints = new float[]{//x,y,s,t
            -1, -1,
            -1, 1,
            1, -1,
            1, 1
    };
    float[] texturePoints = new float[]{
            0, 1,
            0, 0,
            1, 1,
            1, 0
    };
    private int mTexture;

    private Context mContext;

    public PicRenderer(Context context) {
        mContext = context;
        floatBuffer = ByteBuffer.allocateDirect(vertexPoints.length * BYTE_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatBuffer.put(vertexPoints);
        textureBuffer = ByteBuffer.allocateDirect(texturePoints.length * BYTE_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureBuffer.put(texturePoints);
    }

    @Override
    protected String vertexShader() {
        return "attribute vec4 a_Position;" +
                "attribute vec2 a_TextureCoordinates;" +
                "varying vec2 v_TextureCoordinates;" +
                "void main(){" +
                "v_TextureCoordinates = a_TextureCoordinates;" +
                "gl_Position = a_Position;" +
                "}";
    }

    @Override
    protected String fragmentShader() {
        return "precision mediump float;" +
                "uniform sampler2D u_TextureUnit;" +
                "varying vec2 v_TextureCoordinates;" +
                "void main(){" +
                "gl_FragColor = texture2D(u_TextureUnit,v_TextureCoordinates);" +
                "}";
    }

    @Override
    protected void onSurfaceCrated() {

        uTextureUnitLocation = GLES20.glGetUniformLocation(mProgram, U_TEXTURE_UNIT);
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
        aTextureCoordiantesLocation = GLES20.glGetAttribLocation(mProgram, A_TEXTURE_COORDINATES);

        mTexture = MTGLHelper.loadTexture(mContext, R.drawable.aaa);

        floatBuffer.position(0);
        glVertexAttribPointer(aPositionLocation, VERTEX_COMPONENT_COUNT, GL_FLOAT, false, 0, floatBuffer);
        glEnableVertexAttribArray(aPositionLocation);

        textureBuffer.position(0);
        glVertexAttribPointer(aTextureCoordiantesLocation, TEXTURE_COORDINATES_COMPONENT_COUNT, GL_FLOAT, false, 0, textureBuffer);
        glEnableVertexAttribArray(aTextureCoordiantesLocation);


    }

    @Override
    protected void onDrawFrame() {
        //设置活动纹理单元
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //绑定纹理到单元
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture);
        //把纹理单元传递给 textureUnit
        GLES20.glUniform1i(uTextureUnitLocation, 0);
        GLES20.glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }

}
