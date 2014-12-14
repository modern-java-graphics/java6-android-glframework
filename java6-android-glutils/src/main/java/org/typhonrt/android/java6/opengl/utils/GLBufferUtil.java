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
