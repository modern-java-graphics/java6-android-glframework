/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.commons.java6.opengl.utils;

/**
 * XeGLUnknown -- Provides an extensible enum implementation for unknown OpenGL ES version info.
 */
public enum XeGLUnknown implements IGLVersion
{
   GL_UNKNOWN(0, 0, "Unknown");

   public final int     majorVersion, minorVersion;

   public final String  versionString;

   private XeGLUnknown(int majorVersion, int minorVersion, String versionString)
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
