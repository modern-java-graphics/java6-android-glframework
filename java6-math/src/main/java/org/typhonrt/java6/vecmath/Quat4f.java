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
package org.typhonrt.java6.vecmath;

/**
 * A 4 element quaternion represented by single precision floating point x,y,z,w coordinates.
 *
 * @author  Michael Leahy
 */
public class Quat4f extends Tuple4f
{
   /**
    * Constructs and initializes a Quat4f from the specified xyzw coordinates.
    *
    * @param x the x coordinate
    * @param y the y coordinate
    * @param z the z coordinate
    * @param w the w scalar component
    */
   public Quat4f(float x, float y, float z, float w)
   {
      super(x, y, z, w);
   }

   /**
    * Constructs and initializes a Quat4f from the specified Quat4f.
    *
    * @param q1 the Quat4f containing the initialization x y z w data
    */
   public Quat4f(Quat4f q1)
   {
      super(q1);
   }

   /**
    * Constructs and initializes a Quat4f from the specified Tuple4f.
    *
    * @param t1 the Tuple4f containing the initialization x y z w data
    */
   public Quat4f(Tuple4f t1)
   {
      super(t1);
   }

   /**
    * Constructs and initializes a Quat4f to (0,0,0,0).
    */
   public Quat4f() {}

   /**
    * Sets the value of this quaternion to the conjugate of quaternion q1.
    *
    * @param q1 the source vector
    */
   public final void conjugate(Quat4f q1)
   {
      x = -q1.x;
      y = -q1.y;
      z = -q1.z;
      w = q1.w;
   }

   /**
    * Negate the value of of each of this quaternion's x,y,z coordinates
    * in place.
    */
   public final void conjugate()
   {
      x = -x;
      y = -y;
      z = -z;
   }

   /**
    * Sets the value of this quaternion to the quaternion product of
    * quaternions q1 and q2 (this = q1 * q2).
    * Note that this is safe for aliasing (e.g. this can be q1 or q2).
    *
    * @param q1 the first quaternion
    * @param q2 the second quaternion
    */
   public final void mul(Quat4f q1, Quat4f q2)
   {
      // store on stack for aliasing-safty
      set(
       q1.x * q2.w + q1.w * q2.x + q1.y * q2.z - q1.z * q2.y,
       q1.y * q2.w + q1.w * q2.y + q1.z * q2.x - q1.x * q2.z,
       q1.z * q2.w + q1.w * q2.z + q1.x * q2.y - q1.y * q2.x,
       q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z
      );
   }

   /**
    * Sets the value of this quaternion to the quaternion product of
    * itself and q1 (this = this * q1).
    *
    * @param q1 the other quaternion
    */
   public final void mul(Quat4f q1)
   {
      // store on stack for aliasing-safty
      set(
       x * q1.w + w * q1.x + y * q1.z - z * q1.y,
       y * q1.w + w * q1.y + z * q1.x - x * q1.z,
       z * q1.w + w * q1.z + x * q1.y - y * q1.x,
       w * q1.w - x * q1.x - y * q1.y - z * q1.z
      );
   }

   /**
    * Multiplies quaternion q1 by the inverse of quaternion q2 and places
    * the value into this quaternion.  The value of both argument quaternions
    * is preservered (this = q1 * q2^-1).
    *
    * @param q1 the left quaternion
    * @param q2 the right quaternion
    */
   public final void mulInverse(Quat4f q1, Quat4f q2)
   {
      double n = norm();
      // zero-div may occur.
      n = (n == 0.0 ? n : 1 / n);
      // store on stack once for aliasing-safty
      set(
       (float) ((q1.x * q2.w - q1.w * q2.x - q1.y * q2.z + q1.z * q2.y) * n),
       (float) ((q1.y * q2.w - q1.w * q2.y - q1.z * q2.x + q1.x * q2.z) * n),
       (float) ((q1.z * q2.w - q1.w * q2.z - q1.x * q2.y + q1.y * q2.x) * n),
       (float) ((q1.w * q2.w + q1.x * q2.x + q1.y * q2.y + q1.z * q2.z) * n)
      );
   }

   /**
    * Multiplies this quaternion by the inverse of quaternion q1 and places
    * the value into this quaternion.  The value of the argument quaternion
    * is preserved (this = this * q^-1).
    *
    * @param q1 the other quaternion
    */
   public final void mulInverse(Quat4f q1)
   {
      double n = norm();
      // zero-div may occur.
      n = (n == 0.0 ? n : 1 / n);
      // store on stack once for aliasing-safty
      set(
       (float) ((x * q1.w - w * q1.x - y * q1.z + z * q1.y) * n),
       (float) ((y * q1.w - w * q1.y - z * q1.x + x * q1.z) * n),
       (float) ((z * q1.w - w * q1.z - x * q1.y + y * q1.x) * n),
       (float) ((w * q1.w + x * q1.x + y * q1.y + z * q1.z) * n)
      );
   }


   // helper
   private float norm()
   {
      return x * x + y * y + z * z + w * w;
   }

   /**
    * Sets the value of this quaternion to quaternion inverse of quaternion q1.
    *
    * @param q1 the quaternion to be inverted
    */
   public final void inverse(Quat4f q1)
   {
      double n = q1.norm();
      // zero-div may occur.
      x = (float) (-q1.x / n);
      y = (float) (-q1.y / n);
      z = (float) (-q1.z / n);
      w = (float) (q1.w / n);
   }

   /**
    * Sets the value of this quaternion to the quaternion inverse of itself.
    */
   public final void inverse()
   {
      double n = norm();
      // zero-div may occur.
      x = (float) (-x / n);
      y = (float) (-y / n);
      z = (float) (-z / n);
      w = (float) (w / n);
   }

   /**
    * Sets the value of this quaternion to the normalized value
    * of quaternion q1.
    *
    * @param q1 the quaternion to be normalized.
    */
   public final void normalize(Quat4f q1)
   {
      double n = Math.sqrt(q1.norm());
      // zero-div may occur.
      x = (float) (q1.x / n);
      y = (float) (q1.y / n);
      z = (float) (q1.z / n);
      w = (float) (q1.w / n);
   }

   /**
    * Sets the value of this quaternion to the rotational component of
    * the passed matrix.
    *
    * @param m1 the matrix4f
    */
   public final void set(Matrix4f m1)
   {
      setFromMat(
       m1.m00, m1.m01, m1.m02,
       m1.m10, m1.m11, m1.m12,
       m1.m20, m1.m21, m1.m22
      );
   }

   /**
    * Sets the value of this quaternion to the rotational component of
    * the passed matrix.
    *
    * @param m1 the matrix3f
    */
   public final void set(Matrix3f m1)
   {
      setFromMat(
       m1.m00, m1.m01, m1.m02,
       m1.m10, m1.m11, m1.m12,
       m1.m20, m1.m21, m1.m22
      );
   }

   public final void set(Quat4f q1)
   {
      x = q1.x;
      y = q1.y;
      z = q1.z;
      w = q1.w;
   }

   /**
    * Performs a great circle interpolation between this quaternion and the
    * quaternion parameter and places the result into this quaternion.
    *
    * @param q1    the other quaternion
    * @param alpha the alpha interpolation parameter
    */
   public final void interpolate(Quat4f q1, double alpha)
   {
      // From Hoggar.
      normalize();
      double n1 = Math.sqrt(q1.norm());
      // zero-div may occur.
      double x1 = q1.x / n1;
      double y1 = q1.y / n1;
      double z1 = q1.z / n1;
      double w1 = q1.w / n1;

      // t is cosine (dot product)
      double t = x * x1 + y * y1 + z * z1 + w * w1;

      // same quaternion (avoid domain error)
      if (1.0 <= Math.abs(t))
         return;

      // t is now theta
      t = Math.acos(t);

      double sin_t = Math.sin(t);

      // same quaternion (avoid zero-div)
      if (sin_t == 0.0)
         return;

      double s = Math.sin((1.0 - alpha) * t) / sin_t;
      t = Math.sin(alpha * t) / sin_t;

      // setComponent values
      x = (float) (s * x + t * x1);
      y = (float) (s * y + t * y1);
      z = (float) (s * z + t * z1);
      w = (float) (s * w + t * w1);
   }

   /**
    * Performs a great circle interpolation between quaternion q1 and
    * quaternion q2 and places the result into this quaternion.
    *
    * @param q1    the first quaternion
    * @param q2    the second quaternion
    * @param alpha the alpha interpolation parameter
    */
   public final void interpolate(Quat4f q1, Quat4f q2, double alpha)
   {
      set(q1);
      interpolate(q2, alpha);
   }

   // helper method
   private void setFromMat(double m00, double m01, double m02,
    double m10, double m11, double m12,
    double m20, double m21, double m22)
   {
      // From Ken Shoemake
      // (ftp://ftp.cis.upenn.edu/pub/graphics/shoemake)

      double s;
      double tr = m00 + m11 + m22;
      if (tr >= 0.0)
      {
         s = Math.sqrt(tr + 1.0);
         w = (float) (s * 0.5);
         s = 0.5 / s;
         x = (float) ((m21 - m12) * s);
         y = (float) ((m02 - m20) * s);
         z = (float) ((m10 - m01) * s);
      }
      else
      {
         double max = Math.max(Math.max(m00, m11), m22);
         if (max == m00)
         {
            s = Math.sqrt(m00 - (m11 + m22) + 1.0);
            x = (float) (s * 0.5);
            s = 0.5 / s;
            y = (float) ((m01 + m10) * s);
            z = (float) ((m20 + m02) * s);
            w = (float) ((m21 - m12) * s);
         }
         else if (max == m11)
         {
            s = Math.sqrt(m11 - (m22 + m00) + 1.0);
            y = (float) (s * 0.5);
            s = 0.5 / s;
            z = (float) ((m12 + m21) * s);
            x = (float) ((m01 + m10) * s);
            w = (float) ((m02 - m20) * s);
         }
         else
         {
            s = Math.sqrt(m22 - (m00 + m11) + 1.0);
            z = (float) (s * 0.5);
            s = 0.5 / s;
            x = (float) ((m20 + m02) * s);
            y = (float) ((m12 + m21) * s);
            w = (float) ((m10 - m01) * s);
         }
      }
   }
}
