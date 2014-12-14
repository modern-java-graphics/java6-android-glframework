/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.typhonrt.android.java6.opengl.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.opengl.GLES20.*;

public class AndroidGLES20Util extends AndroidGLESUtil
{
   protected AndroidGLES20Util() {}

   private static final String               s_LOG_TAG = "AndroidGLES20Util";

   private static final ThreadLocal<int[]>   s_BUILD_PROGRAM_STATUS = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_BUILD_SHADER_STATUS = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_CREATE_BUFFER_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_CREATE_FRAMEBUFFER_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_CREATE_TEXTURE_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_CURRENT_FRAMEBUFFER_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_DELETE_BUFFER_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_DELETE_FRAMEBUFFER_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_DELETE_TEXTURE_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_LOAD_TEXTURE_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_GET_PROGRAM_ID = new ThreadLocal<int[]>();

   public static int buildProgramFromAssets(Resources resources, String shaderFileName, int shaderType)
   {
      String shaderSource = loadFromAssets(resources, shaderFileName);

      return buildProgram(shaderSource, shaderType);
   }

   public static int buildProgramFromAssets(Resources resources, String vertexFileName, String fragmentFileName)
   {
      String vertexSource = loadFromAssets(resources, vertexFileName);
      String fragmentSource = loadFromAssets(resources, fragmentFileName);

      return buildProgram(vertexSource, fragmentSource);
   }

   public static int buildProgram(String shaderSource, int shaderType)
   {
      int computeShader = buildShader(shaderSource, shaderType);
      if (computeShader == 0) return 0;

      int program = glCreateProgram();
      glAttachShader(program, computeShader);

      glLinkProgram(program);

      int[] status = s_BUILD_PROGRAM_STATUS.get();
      if (status == null)
      {
         status = new int[1];
         s_BUILD_PROGRAM_STATUS.set(status);
      }

      glGetProgramiv(program, GL_LINK_STATUS, status, 0);
      if (status[0] != GL_TRUE)
      {
         String error = glGetProgramInfoLog(program);
         Log.d(s_LOG_TAG, "Error while linking program:\n" + error);
         glDeleteShader(computeShader);
         glDeleteProgram(program);
         return 0;
      }

      return program;
   }

   public static int buildProgram(String vertexSource, String fragmentSource)
   {
      int vertexShader = buildShader(vertexSource, GL_VERTEX_SHADER);
      if (vertexShader == 0) return 0;

      int fragmentShader = buildShader(fragmentSource, GL_FRAGMENT_SHADER);
      if (fragmentShader == 0) return 0;

      int program = glCreateProgram();
      glAttachShader(program, vertexShader);
      glAttachShader(program, fragmentShader);

      glLinkProgram(program);

      int[] status = s_BUILD_PROGRAM_STATUS.get();
      if (status == null)
      {
         status = new int[1];
         s_BUILD_PROGRAM_STATUS.set(status);
      }

      glGetProgramiv(program, GL_LINK_STATUS, status, 0);
      if (status[0] != GL_TRUE)
      {
         String error = glGetProgramInfoLog(program);
         Log.d(s_LOG_TAG, "Error while linking program:\n" +error);
         glDeleteShader(vertexShader);
         glDeleteShader(fragmentShader);
         glDeleteProgram(program);
         return 0;
      }

      return program;
   }

   public static int buildShader(String source, int type)
   {
      int shader = glCreateShader(type);

      glShaderSource(shader, source);

      glCompileShader(shader);

      int[] status = s_BUILD_SHADER_STATUS.get();
      if (status == null)
      {
         status = new int[1];
         s_BUILD_SHADER_STATUS.set(status);
      }

      glGetShaderiv(shader, GL_COMPILE_STATUS, status, 0);
      if (status[0] != GL_TRUE)
      {
         String error = glGetShaderInfoLog(shader);
         Log.d(s_LOG_TAG, "Error while compiling shader:\n" +error);
         glDeleteShader(shader);
         return 0;
      }

      return shader;
   }

   public static int checkGlError()
   {
      return checkGlError(false);
   }

   public static int checkGlError(boolean throwException)
   {
      int error = glGetError();
      if (error != GL_NO_ERROR)
      {
         Log.d(s_LOG_TAG, "GL error = 0x" +Integer.toHexString(error));
         if (throwException)
         {
            throw new RuntimeException("GL ERROR = 0x" + Integer.toHexString(error));
         }
      }

      return error;
   }

   /**
    * Create a new texture and set it up
    */
   public static int createTexture(int internalFormat, int width, int height)
   {
      int[] texture = s_CREATE_TEXTURE_ID.get();
      if (texture == null)
      {
         texture = new int[1];
         s_CREATE_TEXTURE_ID.set(texture);
      }

      glGenTextures(1, texture, 0);
      glBindTexture(GL_TEXTURE_2D, texture[0]);

      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

      glBindTexture(GL_TEXTURE_2D, 0);

      return texture[0];
   }

   public static boolean checkFrameBufferStatus()
   {
      int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);

      return status == GL_NO_ERROR || status == GL_FRAMEBUFFER_COMPLETE;
   }

   public static int createBuffer()
   {
      int[] buffers = s_CREATE_BUFFER_ID.get();
      if (buffers == null)
      {
         buffers = new int[1];
         s_CREATE_BUFFER_ID.set(buffers);
      }

      glGenBuffers(1, buffers, 0);
      return buffers[0];
   }

   public static int createFrameBuffer()
   {
      int[] framebuffer = s_CREATE_FRAMEBUFFER_ID.get();
      if (framebuffer == null)
      {
         framebuffer = new int[1];
         s_CREATE_FRAMEBUFFER_ID.set(framebuffer);
      }

      glGenFramebuffers(1, framebuffer, 0);

      return framebuffer[0];
   }

   public static void deleteBuffer(int bufferID)
   {
      int[] buffer = s_DELETE_BUFFER_ID.get();
      if (buffer == null)
      {
         buffer = new int[1];
         s_DELETE_BUFFER_ID.set(buffer);
      }

      buffer[0] = bufferID;

      glDeleteBuffers(1, buffer, 0);
   }

   public static void deleteFrameBuffer(int frameBufferID)
   {
      int[] frameBuffer = s_DELETE_FRAMEBUFFER_ID.get();
      if (frameBuffer == null)
      {
         frameBuffer = new int[1];
         s_DELETE_FRAMEBUFFER_ID.set(frameBuffer);
      }

      frameBuffer[0] = frameBufferID;

      glDeleteFramebuffers(1, frameBuffer, 0);
   }

   /**
    * Delete the texture by pointer reference
    */
   public static void deleteTexture(int textureID)
   {
      int[] texture = s_DELETE_TEXTURE_ID.get();
      if (texture == null)
      {
         texture = new int[1];
         s_DELETE_TEXTURE_ID.set(texture);
      }

      texture[0] = textureID;

      glDeleteTextures(1, texture, 0);
   }

   public static int getCurrentFrameBufferTarget()
   {
      int[] frameBuffer = s_CURRENT_FRAMEBUFFER_ID.get();
      if (frameBuffer == null)
      {
         frameBuffer = new int[1];
         s_CURRENT_FRAMEBUFFER_ID.set(frameBuffer);
      }

      glGetIntegerv(GL_FRAMEBUFFER_BINDING, frameBuffer, 0);

      return frameBuffer[0];
   }

   public static int getProgramiv(int programID, int pName)
   {
      int program[] = s_GET_PROGRAM_ID.get();
      if (program == null)
      {
         program = new int[1];
         s_GET_PROGRAM_ID.set(program);
      }

      glGetProgramiv(programID, pName, program, 0);

      return program[0];
   }

   private static String loadFromAssets(Resources resources, String fileName)
   {
      BufferedReader reader = null;
      StringBuilder sb = new StringBuilder();

      try
      {
         reader = new BufferedReader(new InputStreamReader(resources.getAssets().open(fileName), "UTF-8"));

         // do reading, usually loop until end of file reading
         String mLine = reader.readLine();
         while (mLine != null)
         {
            sb.append(mLine).append('\n');
            mLine = reader.readLine();
         }
      }
      catch (IOException e)
      {
         //log the exception
      }
      finally
      {
         if (reader != null)
         {
            try
            {
               reader.close();
            }
            catch (IOException e)
            {
               //log the exception
            }
         }
      }

      return sb.toString();
   }

   public static int loadTexture(Resources resources, int resource, boolean flip)
   {
      int[] textures = s_LOAD_TEXTURE_ID.get();
      if (textures == null)
      {
         textures = new int[1];
         s_LOAD_TEXTURE_ID.set(textures);
      }

      glActiveTexture(GL_TEXTURE0);
      glGenTextures(1, textures, 0);

      int texture = textures[0];
      glBindTexture(GL_TEXTURE_2D, texture);

      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

      Bitmap bitmap = BitmapFactory.decodeResource(resources, resource);

      final int width = bitmap.getWidth();
      final int height = bitmap.getHeight();

      if (flip)
      {
         Matrix matrix = new Matrix();
         matrix.setScale(1, -1);
         matrix.postTranslate(0, height);
         Bitmap flipBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

         bitmap.recycle();
         bitmap = flipBitmap;
      }

      GLUtils.texImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bitmap, GL_UNSIGNED_BYTE, 0);

      bitmap.recycle();

      glBindTexture(GL_TEXTURE_2D, 0);

      return texture;
   }
}