/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.android.java6.opengl.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLUtils;

import static android.opengl.GLES30.*;

public class AndroidGLES30Util extends AndroidGLES20Util
{
   protected AndroidGLES30Util() {}

   private static final ThreadLocal<int[]>   s_CREATE_TEXTURE_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_CREATE_SAMPLER_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_CREATE_VERTEX_ARRAY_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_GET_INTERGER_IV = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_LOAD_TEXTURE_ID = new ThreadLocal<int[]>();

   /**
    * Create a new texture and set it up. The main difference with AndroidGLES30Util.createTexture is that it also
    * specifically sets the internal format with glTexStorage2D which is necessary to use this texture with compute
    * shaders.
    */
   public static int createTexture(int internalFormat, int width, int height)
   {
      int texture[] = s_CREATE_TEXTURE_ID.get();
      if (texture == null)
      {
         texture = new int[1];
         s_CREATE_TEXTURE_ID.set(texture);
      }

      glGenTextures(1, texture, 0);
      glBindTexture(GL_TEXTURE_2D, texture[0]);

      glTexStorage2D(GL_TEXTURE_2D, 1, internalFormat, width, height);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

      glBindTexture(GL_TEXTURE_2D, 0);

      return texture[0];
   }

   public static int createSampler()
   {
      int sampler[] = s_CREATE_SAMPLER_ID.get();
      if (sampler == null)
      {
         sampler = new int[1];
         s_CREATE_SAMPLER_ID.set(sampler);
      }

      glGenSamplers(1, sampler, 0);
      return sampler[0];
   }

   public static int createVertexArray()
   {
      int vao[] = s_CREATE_VERTEX_ARRAY_ID.get();
      if (vao == null)
      {
         vao = new int[1];
         s_CREATE_VERTEX_ARRAY_ID.set(vao);
      }

      glGenVertexArrays(1, vao, 0);
      return vao[0];
   }

   public static int getGetIntegeri_v(int target, int index)
   {
      int value[] = s_GET_INTERGER_IV.get();
      if (value == null)
      {
         value = new int[1];
         s_GET_INTERGER_IV.set(value);
      }

      glGetIntegeri_v(target, index, value, 0);

      return value[0];
   }

   /**
    * Loads a texture from an Android drawable resource performing any flipping as given. The main difference with
    * AndroidGLES30Util.loadTexture is that it also specifically sets the internal format with glTexStorage2D which
    * is necessary to use this texture with compute shaders.
    *
    * @param resources
    * @param resource
    * @param flip
    *
    * @return
    */
   public static int loadTexture(Resources resources, int resource, int internalFormat, boolean flip)
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

      // It turns out we can still use the convenience method "GLUtils.texSubImage2D" below, but for compute shaders
      // it is important to make an explicit call to glTexStorage2D with an internal format of GL_RGBA8.
      glTexStorage2D(GL_TEXTURE_2D, 1, internalFormat, width, height);
      checkGlError();

      GLUtils.texSubImage2D(GL_TEXTURE_2D, 0, 0, 0, bitmap);
      checkGlError();

      bitmap.recycle();

      glBindTexture(GL_TEXTURE_2D, 0);

      return texture;
   }
}