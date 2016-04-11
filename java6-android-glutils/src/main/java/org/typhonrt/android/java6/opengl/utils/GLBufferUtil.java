/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.android.java6.opengl.utils;

import org.typhonrt.java6.math.MathUtil;

import java.nio.ByteBuffer;

import static android.opengl.GLES30.*;

/**
 * GLBufferUtil
 */
public final class GLBufferUtil
{
   private GLBufferUtil() {}

   public static final int s_QUAD_BUFFER_STRIDE = 5 * MathUtil.s_FLOAT_SIZE_BYTES;   // x, y, z, u, v; buffer stride in bytes
   public static final int s_QUAD_UV_OFFSET = 3 * MathUtil.s_FLOAT_SIZE_BYTES;       // offset to u/v in bytes

   public static GLBuffer createQuadVertexUVBuffer(float aspectRatio)
   {
      GLBuffer buffer = new GLBuffer(GL_ARRAY_BUFFER, 4, s_QUAD_BUFFER_STRIDE);

      ByteBuffer b = buffer.map(GL_MAP_WRITE_BIT);

      b.putFloat(aspectRatio).putFloat(-1f).putFloat(0f).putFloat(1f).putFloat(0f);
      b.putFloat(-aspectRatio).putFloat(-1f).putFloat(0f).putFloat(0f).putFloat(0f);
      b.putFloat(aspectRatio).putFloat(1f).putFloat(0f).putFloat(1f).putFloat(1f);
      b.putFloat(-aspectRatio).putFloat(1f).putFloat(0f).putFloat(0f).putFloat(1f);

      b.flip();

      buffer.unmap();

      return buffer;
   }
}
