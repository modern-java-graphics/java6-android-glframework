/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.commons.java6.opengl.utils;

/**
 * IGLVersion
 */
public interface IGLVersion
{
   public int getMajorVersion();

   public int getMinorVersion();

   public String getVersionString();

   public boolean lessThan(IGLVersion glVersion);
}
