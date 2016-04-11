/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.android.java6.opengl.utils;

import org.typhonrt.java6.math.MathUtil;

import static android.opengl.GLES31.*;

public class AndroidGLES31Util extends AndroidGLES30Util
{
   protected AndroidGLES31Util() {}

   public static final String s_STR_AEP_EXT = "ANDROID_extension_pack_es31a";

   private static final ThreadLocal<int[]>   s_CREATE_PROGRAM_PIPELINE_ID = new ThreadLocal<int[]>();
   private static final ThreadLocal<int[]>   s_GET_PROGRAM_PIPELINE_ID = new ThreadLocal<int[]>();

   /**
    *
    */
   public static int createProgramPipeline()
   {
      int pipeline[] = s_CREATE_PROGRAM_PIPELINE_ID.get();
      if (pipeline == null)
      {
         pipeline = new int[1];
         s_CREATE_PROGRAM_PIPELINE_ID.set(pipeline);
      }

      glGenProgramPipelines(1, pipeline, 0);

      return pipeline[0];
   }

   /**
    * Returns the max equal power workgroup size that is equal or below GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS. This
    * value can be used to dynamically set the proper layout sizes for compute shaders given the supported hardware
    * configuration.
    *
    * @param dimensions specifies the dimensions to calculate [1-3]
    * @return int
    */
   public static int getMaxComputePowerWorkGroupSize(int dimensions)
   {
      if (dimensions < 1 || dimensions > 3)
      {
         throw new IllegalArgumentException("Error: getMaxComputePowerWorkGroupSize requires dimension [1-3].");
      }

      int maxInv = getIntegerv(GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS);

      int power = 4;
      int nextPower = 4;
      int total = power;

      while (total <= maxInv)
      {
         power = nextPower;
         nextPower = MathUtil.nextPow2(power + 1);
         total = (int)Math.pow(nextPower, dimensions);
      }

      return power;
   }

   public static int getProgramPipelineiv(int programID, int pName)
   {
      int pipeline[] = s_GET_PROGRAM_PIPELINE_ID.get();
      if (pipeline == null)
      {
         pipeline = new int[1];
         s_GET_PROGRAM_PIPELINE_ID.set(pipeline);
      }

      glGetProgramPipelineiv(programID, pName, pipeline, 0);

      return pipeline[0];
   }
}