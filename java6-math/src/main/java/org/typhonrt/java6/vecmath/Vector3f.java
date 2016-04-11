/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.java6.vecmath;

/**
 * A 3 element vector that is represented by single precision floating point x,y,z coordinates. If this value
 * represents a normal, then it should be normalized.
 *
 * @author  Michael Leahy
 */
public class Vector3f extends Tuple3f
{
   public static final Vector3f s_X = new Vector3f(1.0f, 0.0f, 0.0f);
   public static final Vector3f s_Y = new Vector3f(0.0f, 1.0f, 0.0f);
   public static final Vector3f s_Z = new Vector3f(0.0f, 0.0f, 1.0f);

   /**
    * Constructs and initializes a Vector3f from the specified xyz coordinates.
    *
    * @param x the x coordinate
    * @param y the y coordinate
    * @param z the z coordinate
    */
   public Vector3f(float x, float y, float z)
   {
      super(x, y, z);
   }

   /**
    * Constructs and initializes a Vector3f from the specified array of length 3.
    *
    * @param v the array of length 3 containing xyz in order
    */
   public Vector3f(float v[])
   {
      super(v);
   }

   /**
    * Constructs and initializes a Vector3f to (0,0,0).
    */
   public Vector3f() {}

   /**
    * Constructs and initializes a Vector3f from the specified Tuple3f.
    *
    * @param t1 the Tuple3f containing the initialization x y z data
    */
   public Vector3f(Tuple3f t1)
   {
      super(t1);
   }
}
