/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.java6.math;

/**
 * MathUtil
 */
public final class MathUtil
{
   public static final int s_FLOAT_SIZE_BYTES = Float.SIZE / 8;
   public static final int s_INTEGER_SIZE_BYTES = Integer.SIZE / 8;
   public static final int s_LONG_SIZE_BYTES = Long.SIZE / 8;

   public static final int s_VEC4F_SIZE_BYTES = s_FLOAT_SIZE_BYTES * 4;

   private static final int s_RAND_MAX = 64000;

   private MathUtil()
   {
   }

   public static byte randByte()
   {
      return (byte) ((int) (Math.random() * s_RAND_MAX) & 0xff);
   }

   public static float frand()
   {
      return (float) Math.random();
   }

   public static float sfrand()
   {
      return frand() * 2.0f - 1.0f;
   }

   public static int nextPow2(int val)
   {
      val--;
      for (int i = 1; i < Integer.SIZE; i <<= 1)
      {
         val = val | val >> i;
      }
      return val + 1;
   }
}
