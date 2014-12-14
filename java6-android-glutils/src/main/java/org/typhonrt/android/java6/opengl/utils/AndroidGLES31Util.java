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