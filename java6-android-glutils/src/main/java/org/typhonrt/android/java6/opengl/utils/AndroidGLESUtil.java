/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.android.java6.opengl.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;

import org.typhonrt.commons.java6.opengl.utils.IGLVersion;
import org.typhonrt.commons.java6.opengl.utils.XeGLES2;
import org.typhonrt.commons.java6.opengl.utils.XeGLES3;
import org.typhonrt.commons.java6.opengl.utils.XeGLUnknown;

/**
 * AndroidGLESUtil
 *
 * Note: Ideally the methods below should be implemented via composition so that version checks can be upgraded
 * dynamically. The TyphonRT middleware enables this pattern.
 */
public class AndroidGLESUtil
{
   protected AndroidGLESUtil() {}

   public static IGLVersion getGLVersion(Context context)
   {
      IGLVersion glVersion = XeGLUnknown.GL_UNKNOWN;

      final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
      final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();

      String glesVersionString = configurationInfo.getGlEsVersion();

      // This is messy...
      if (XeGLES2.GLES2_0.versionString.equals(glesVersionString))
      {
         glVersion = XeGLES2.GLES2_0;
      }
      else if (XeGLES3.GLES3_0.versionString.equals(glesVersionString))
      {
         glVersion = XeGLES3.GLES3_0;
      }
      else if (XeGLES3.GLES3_1.versionString.equals(glesVersionString))
      {
         glVersion = XeGLES3.GLES3_1;
      }

      return glVersion;
   }

   /**
    * Returns appropriate IGLVersion for major / minor version numbers.
    *
    * @param majorVersion
    * @param minorVersion
    *
    * @return IGLVersion
    */
   public static IGLVersion getGLVersion(int majorVersion, int minorVersion)
   {
      IGLVersion glVersion = XeGLUnknown.GL_UNKNOWN;

      switch (majorVersion)
      {
         case 2:
            glVersion = XeGLES2.GLES2_0;
            break;

         case 3:
            if (minorVersion == 0)
            {
               glVersion = XeGLES3.GLES3_0;
            }
            else if (minorVersion == 1)
            {
               glVersion = XeGLES3.GLES3_1;
            }
            break;
      }

      return glVersion;
   }

   /**
    * Checks the GL_EXTENSIONS String for the given "extension". This method must only be called when a valid context
    * and surface is valid.
    *
    * @param extension
    * @return boolean
    */
   public static boolean hasExtension(String extension)
   {
      String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);

      boolean foundExtension = false;

      if (extensions != null)
      {
         foundExtension = extensions.toLowerCase().contains(extension.toLowerCase());
      }

      return foundExtension;
   }
}
