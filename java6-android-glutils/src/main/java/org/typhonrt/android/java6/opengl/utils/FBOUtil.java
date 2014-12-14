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

import org.typhonrt.commons.java6.opengl.utils.FBOData;

import static android.opengl.GLES20.*;

/**
 * FBOUtil
 */
public class FBOUtil
{
   public static FBOData initialize(FBOData fbo)
   {
      return initialize(fbo, fbo.width, fbo.height);
   }

   public static FBOData initialize(FBOData fbo, int width, int height)
   {
      return initialize(fbo, width, height, GL_RGBA);
   }

   public static FBOData initialize(FBOData fbo, int width, int height, int format)
   {
      fbo.width = width;
      fbo.height = height;

      fbo.bufferID = AndroidGLES20Util.createFrameBuffer();

      glBindFramebuffer(GL_FRAMEBUFFER, fbo.bufferID);
      AndroidGLES20Util.checkGlError();

      fbo.textureID = AndroidGLES20Util.createTexture(format, width, height);

      glBindTexture(GL_TEXTURE_2D, fbo.textureID);

      glTexImage2D(GL_TEXTURE_2D, 0, format, fbo.width, fbo.height, 0, format, GL_UNSIGNED_BYTE, null);

      AndroidGLES20Util.checkGlError();

      glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, fbo.textureID, 0);

      glBindFramebuffer(GL_FRAMEBUFFER, 0);

      glBindTexture(GL_TEXTURE_2D, 0);

      return fbo;
   }

   public static void makeCurrentRenderTarget(FBOData fbo)
   {
      glBindFramebuffer(GL_FRAMEBUFFER, fbo.bufferID);
   }

   public static void makeCurrentAndClear(FBOData fbo)
   {
      glBindFramebuffer(GL_FRAMEBUFFER, fbo.bufferID);

      glClearColor(0, 0, 0, 0);
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
   }

   public static boolean isValid(FBOData fbo)
   {
      return fbo.textureID != 0 && fbo.bufferID != 0;
   }

   public static void release(FBOData fbo)
   {
      if (fbo.textureID != 0)
      {
         AndroidGLES20Util.deleteTexture(fbo.textureID);
      }

      if (fbo.bufferID != 0)
      {
         AndroidGLES20Util.deleteFrameBuffer(fbo.bufferID);
      }

      fbo.textureID = fbo.bufferID = 0;
   }

   public static void releaseAsRenderTarget()
   {
      glBindFramebuffer(GL_FRAMEBUFFER, 0);
   }
}
