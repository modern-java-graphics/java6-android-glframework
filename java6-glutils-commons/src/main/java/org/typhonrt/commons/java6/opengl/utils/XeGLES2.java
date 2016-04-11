/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.commons.java6.opengl.utils;

/**
 * XeGLES2 -- Provides an extensible enum implementation for OpenGL ES 2.0 version info.
 */
public enum XeGLES2 implements IGLVersion
{
   GLES2_0(2, 0, "2.0");

   public final int     majorVersion, minorVersion;

   public final String  versionString;

   private XeGLES2(int majorVersion, int minorVersion, String versionString)
   {
      this.majorVersion = majorVersion;
      this.minorVersion = minorVersion;
      this.versionString = versionString;
   }

   @Override
   public int getMajorVersion()
   {
      return majorVersion;
   }

   @Override
   public int getMinorVersion()
   {
      return minorVersion;
   }

   @Override
   public String getVersionString()
   {
      return versionString;
   }

   @Override
   public boolean lessThan(IGLVersion glVersion)
   {
      int thatGLMajorVersion = glVersion.getMajorVersion();
      int thatGLMinorVersion = glVersion.getMinorVersion();

      return majorVersion < thatGLMajorVersion ||
       (majorVersion == thatGLMajorVersion && minorVersion < thatGLMinorVersion);
   }
}
