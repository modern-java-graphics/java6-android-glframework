/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.java6.vecmath;

/**
 * A 4 element vector that is represented by single precision floating point x,y,z,w coordinates.
 *
 * @author  Michael Leahy
 */
public class Vector4f extends Tuple4f
{
   /**
    * Constructs and initializes a Vector4f from the specified xyzw coordinates.
    *
    * @param x the x coordinate
    * @param y the y coordinate
    * @param z the z coordinate
    * @param w the w coordinate
    */
   public Vector4f(float x, float y, float z, float w)
   {
      super(x, y, z, w);
   }

   /**
    * Constructs and initializes a Vector4f from the specified array of length 4.
    *
    * @param v the array of length 4 containing xyzw in order
    */
   public Vector4f(float v[])
   {
      super(v);
   }

   /**
    * Constructs and initializes a Vector4f to (0,0,0,0).
    */
   public Vector4f() {}

   /**
    * Constructs and initializes a Vector4f from the specified Tuple4f.
    *
    * @param t1 the Tuple4f containing the initialization x y z w data
    */
   public Vector4f(Tuple4f t1)
   {
      super(t1);
   }

   /**
    * Constructs and initializes a Vector4f from the specified Tuple3f.
    * The x,y,z  components of this point are setComponent to the corresponding
    * components
    * of tuple t1. The w component of this point is setComponent to 0.
    *
    * @param t1 the tuple to be copied
    * @since Java3D 1.2
    */
   public Vector4f(Tuple3f t1)
   {
      super(t1.x, t1.y, t1.z, 0);
   }

   /**
    * Returns the (4-space) angle in radians between this vector and
    * the vector parameter; the return value is constrained to the
    * range [0,PI].
    *
    * @param v1 the other vector
    * @return the angle in radians in the range [0,PI]
    */
   public final float angle(Vector4f v1)
   {
      // numerically, domain error may occur
      return (float) Math.acos((x * v1.x + y * v1.y + z * v1.z + w * v1.w) /
       Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z + v1.w * v1.w) / Math.sqrt(x * x + y * y + z * z + w * w));
      /*
      // zero div may occur.
      double d = x * v1.x + y * v1.y + z * v1.z + w * v1.w;  // Dot product
      double v1_length = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z + v1.w * v1.w);  // Length v1
      double v_length = Math.sqrt(x * x + y * y + z * z + w * w);  // Length

      // numerically, domain error may occur
      return (float)Math.acos(d / v1_length / v_length);
      */
   }

   /**
    * Computes the dot product of the this vector and vector v1.
    *
    * @param v1 the other vector
    * @return the dot product of this vector and v1
    */
   public final float dot(Vector3f v1)
   {
      return x * v1.x + y * v1.y + z * v1.z;
   }

   /**
    * Computes the dot product of the this vector and vector v1.
    *
    * @param v1 the other vector
    * @return the dot product of this vector and v1
    */
   public final float dot(Vector4f v1)
   {
      return x * v1.x + y * v1.y + z * v1.z + w * v1.w;
   }

   /**
    * Returns the squared length of this vector.
    *
    * @return the squared length of this vectoras a float
    */
   public final float lengthSquared()
   {
      return x * x + y * y + z * z + w * w;
   }

   /**
    * Returns the length of this vector.
    *
    * @return the length of this vector as a float
    */
   public final float length()
   {
      return (float) Math.sqrt(x * x + y * y + z * z + w * w);
   }

   /**
    * Sets the value of this vector to the normalization of vector v1.
    *
    * @param v1 the un-normalized vector
    */
   public final void normalize(Vector4f v1)
   {
      set(v1);
      normalize();
   }
}
