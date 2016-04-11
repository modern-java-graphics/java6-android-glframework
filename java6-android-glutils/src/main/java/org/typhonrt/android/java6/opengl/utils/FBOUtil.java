/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
