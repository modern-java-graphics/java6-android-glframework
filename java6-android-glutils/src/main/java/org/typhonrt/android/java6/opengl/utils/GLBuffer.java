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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static android.opengl.GLES30.*;

/**
 * GLBuffer
 *
 * target - type of buffer; IE GL_ARRAY_BUFFER, GL_SHADER_STORAGE_BUFFER, etc.
 * size - size in unit blocks of data
 * stride - size of bytes per unit block of data
 */
public final class GLBuffer
{
   private int targetType;

   private int size;
   private int stride;

   private int bufferID;

   public GLBuffer(int targetType, int size, int stride)
   {
      this.targetType = targetType;
      this.size = size;
      this.stride = stride;

      bufferID = AndroidGLES20Util.createBuffer();

      bind();

      glBufferData(targetType, size * stride, null, GL_STATIC_DRAW);

      unbind();
   }

   public GLBuffer bind()
   {
      glBindBuffer(targetType, bufferID);

      return this;
   }

   public void dispose()
   {
      AndroidGLES20Util.deleteBuffer(bufferID);
   }

   public GLBuffer unbind()
   {
      glBindBuffer(targetType, 0);

      return this;
   }

   // GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT
   public ByteBuffer map(int accessBitfield)
   {
      bind();

      ByteBuffer b = (ByteBuffer)glMapBufferRange(targetType, 0, size * stride, accessBitfield);
      b.order(ByteOrder.nativeOrder());

      return b;
   }

   public GLBuffer unmap()
   {
      bind();
      glUnmapBuffer(targetType);

      return this;
   }

   public int getBufferID()
   {
      return bufferID;
   }

   public int getSize()
   {
      return size;
   }

   public int getStride() { return stride; }

   private static final String s_STR_COLON = ": ";

   public void debugPrint()
   {
      ByteBuffer buffer = map(GL_MAP_READ_BIT);

      for (int cntr = buffer.position(), length = buffer.remaining(); cntr < length; cntr++)
      {
         System.err.println(cntr +s_STR_COLON +buffer.get(cntr));
      }

      unmap();
   }
}
