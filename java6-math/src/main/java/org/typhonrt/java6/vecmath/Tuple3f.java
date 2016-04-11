/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.java6.vecmath;

/**
 * A generic 3 element tuple that is represented by single precision floating point x,y and z coordinates.
 *
 * @author  Michael Leahy
 */
public class Tuple3f
{
   /**
    * The x coordinate.
    */
   public float x;

   /**
    * The y coordinate.
    */
   public float y;

   /**
    * The z coordinate.
    */
   public float z;

   /**
    * Constructs and initializes a Tuple3f from the specified xyz coordinates.
    *
    * @param x the x coordinate
    * @param y the y coordinate
    * @param z the z coordinate
    */
   public Tuple3f(float x, float y, float z)
   {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   /**
    * Constructs and initializes a Tuple3f from the specified array.
    *
    * @param t the array of length 3 containing xyz in order
    */
   public Tuple3f(float t[])
   {
      // ArrayIndexOutOfBounds is thrown if t.length < 3
      this.x = t[0];
      this.y = t[1];
      this.z = t[2];
   }

   /**
    * Constructs and initializes a Tuple3f from the specified Tuple3f.
    *
    * @param t1 the Tuple3f containing the initialization x y z data
    */
   public Tuple3f(Tuple3f t1)
   {
      x = t1.x;
      y = t1.y;
      z = t1.z;
   }

   /**
    * Constructs and initializes a Tuple3f to (0,0,0).
    */
   public Tuple3f()
   {
      x = 0.0f;
      y = 0.0f;
      z = 0.0f;
   }

   /**
    * Sets the value of this tuple to the specified xyz coordinates.
    *
    * @param x the x coordinate
    * @param y the y coordinate
    * @param z the z coordinate
    */
   public final void set(float x, float y, float z)
   {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   /**
    * Sets the value of this tuple from the 3 values specified in the array.
    *
    * @param t the array of length 3 containing xyz in order
    */
   public final void set(float t[])
   {
      // ArrayIndexOutOfBounds is thrown if t.length < 3
      x = t[0];
      y = t[1];
      z = t[2];
   }

   /**
    * Sets the value of this tuple from the 3 values specified in the array.
    *
    * @param t the array of length 3 containing xyz in order
    */
   public final void set(float t[], int index)
   {
      // ArrayIndexOutOfBounds is thrown if t.length < index + 2
      x = t[index];
      y = t[index + 1];
      z = t[index + 2];
   }

   /**
    * Sets the value of this tuple to the value of the Tuple3f argument.
    *
    * @param t1 the tuple to be copied
    */
   public final void set(Tuple3f t1)
   {
      x = t1.x;
      y = t1.y;
      z = t1.z;
   }

   /**
    * Copies the value of the elements of this tuple into the array t[].
    *
    * @param t the array that will contain the values of the vector
    */
   public final void get(float t[])
   {
      // ArrayIndexOutOfBounds is thrown if t.length < 3
      t[0] = x;
      t[1] = y;
      t[2] = z;
   }

   /**
    * Copies the value of the elements of this tuple into the array t[].
    *
    * @param t the array that will contain the values of the vector
    */
   public final void get(float t[], int index)
   {
      // ArrayIndexOutOfBounds is thrown if t.length < index + 2
      t[index] = x;
      t[index + 1] = y;
      t[index + 2] = z;
   }

   /**
    * Gets the value of this tuple and copies the values into the Tuple3f.
    *
    * @param t Tuple3f object into which that values of this object are copied
    */
   public final void get(Tuple3f t)
   {
      t.x = x;
      t.y = y;
      t.z = z;
   }

   /**
    * Sets the value of this tuple to the vector sum of tuples t1 and t2.
    *
    * @param t1 the first tuple
    * @param t2 the second tuple
    */
   public final void add(Tuple3f t1, Tuple3f t2)
   {
      x = t1.x + t2.x;
      y = t1.y + t2.y;
      z = t1.z + t2.z;
   }

   /**
    * Sets the value of this tuple to the vector sum of itself and tuple t1.
    *
    * @param t1 the other tuple
    */
   public final void add(Tuple3f t1)
   {
      x += t1.x;
      y += t1.y;
      z += t1.z;
   }


   /**
    * Sets the value of this tuple to the vector difference of tuple t1 and t2 (this = t1 - t2).
    *
    * @param t1 the first tuple
    * @param t2 the second tuple
    */
   public final void sub(Tuple3f t1, Tuple3f t2)
   {
      x = t1.x - t2.x;
      y = t1.y - t2.y;
      z = t1.z - t2.z;
   }

   /**
    * Sets the value of this tuple to the vector difference of itself and tuple t1 (this = this - t1).
    *
    * @param t1 the other tuple
    */
   public final void sub(Tuple3f t1)
   {
      x -= t1.x;
      y -= t1.y;
      z -= t1.z;
   }

   /**
    * Calculates the midpoint between this tuple and another.
    * (e.g. x from x value, y from y, etc.)
    *
    * @param t The other tuple to subtract
    */
   public final void mid(Tuple3f t)
   {
      x = (x + t.x) / 2;
      y = (y + t.y) / 2;
      z = (z + t.z) / 2;
   }

   /**
    * Sets the value of this tuple to the negation of tuple t1.
    *
    * @param t1 the source vector
    */
   public final void negate(Tuple3f t1)
   {
      x = -t1.x;
      y = -t1.y;
      z = -t1.z;
   }

   /**
    * Negates the value of this vector in place.
    */
   public final void negate()
   {
      x = -x;
      y = -y;
      z = -z;
   }

   /**
    * Multiplies each of the x,y,z components of the Tuple4f parameter
    * by 1/w and places the projected values into this point.
    *
    * @param p1 the source Tuple4f, which is not modified
    */
   public final void project(Tuple4f p1)
   {
      // zero div may occur.
      x = p1.x / p1.w;
      y = p1.y / p1.w;
      z = p1.z / p1.w;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of tuple t1.
    *
    * @param s  the scalar value
    * @param t1 the source tuple
    */
   public final void scale(float s, Tuple3f t1)
   {
      x = s * t1.x;
      y = s * t1.y;
      z = s * t1.z;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of itself.
    *
    * @param s the scalar value
    */
   public final void scale(float s)
   {
      x *= s;
      y *= s;
      z *= s;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of itself.
    *
    * @param scaleX the scalar value
    * @param scaleY the scalar value
    * @param scaleZ the scalar value
    */
   public final void scale(float scaleX, float scaleY, float scaleZ)
   {
      x *= scaleX;
      y *= scaleY;
      z *= scaleZ;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of tuple t1 and then
    * adds tuple t2 (this = s*t1 + t2).
    *
    * @param s  the scalar value
    * @param t1 the tuple to be multipled
    * @param t2 the tuple to be added
    */
   public final void scaleAdd(float s, Tuple3f t1, Tuple3f t2)
   {
      x = s * t1.x + t2.x;
      y = s * t1.y + t2.y;
      z = s * t1.z + t2.z;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of itself and then
    * adds tuple t1 (this = s*this + t1).
    *
    * @param s  the scalar value
    * @param t1 the tuple to be added
    */
   public final void scaleAdd(float s, Tuple3f t1)
   {
      x = s * x + t1.x;
      y = s * y + t1.y;
      z = s * z + t1.z;
   }

   /**
    * Returns a hash number based on the data values in this object.
    * Two different Tuple3f objects with identical data  values
    * (ie, returns true for equals(Tuple3f) ) will return the same hash number.
    * Two vectors with different data members may return the same hash value,
    * although this is not likely.
    */
   @Override
   public int hashCode()
   {
      int xbits = Float.floatToIntBits(x);
      int ybits = Float.floatToIntBits(y);
      int zbits = Float.floatToIntBits(z);
      return xbits ^ ybits ^ zbits;
   }

   /**
    * Returns true if all of the data members of Tuple3f t1 are equal to the corresponding
    * data members in this
    *
    * @param t1 the vector with which the comparison is made.
    */
   public boolean equals(Tuple3f t1)
   {
      return t1 != null && x == t1.x && y == t1.y && z == t1.z;
   }

   /**
    * Returns true if the L-infinite distance between this tuple and tuple t1 is
    * less than or equal to the epsilon parameter, otherwise returns false. The L-infinite
    * distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
    *
    * @param t1      the tuple to be compared to this tuple
    * @param epsilon the threshold value
    */
   public boolean epsilonEquals(Tuple3f t1, float epsilon)
   {
      return (Math.abs(t1.x - this.x) <= epsilon) &&
       (Math.abs(t1.y - this.y) <= epsilon) &&
       (Math.abs(t1.z - this.z) <= epsilon);
   }

   /**
    * Returns a string that contains the values of this Tuple3f. The form is (x,y,z).
    *
    * @return the String representation
    */
   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder(VecmathStrings.s_STR_OPEN_X);
      sb.append(x).append(VecmathStrings.s_STR_Y).append(y).append(VecmathStrings.s_STR_Z).append(z).append(VecmathStrings.s_STR_CLOSE);
      return sb.toString();
   }

   /**
    * Clamps the tuple parameter to the range [low, high] and places the values
    * into this tuple.
    *
    * @param min the lowest value in the tuple after clamping
    * @param max the highest value in the tuple after clamping
    * @param t   the source tuple, which will not be modified
    */
   public final void clamp(float min, float max, Tuple3f t)
   {
      set(t);
      clamp(min, max);
   }

   /**
    * Clamps the minimum value of the tuple parameter to the min parameter
    * and places the values into this tuple.
    *
    * @param min the lowest value in the tuple after clamping
    * @param t   the source tuple, which will not be modified
    */
   public final void clampMin(float min, Tuple3f t)
   {
      set(t);
      clampMin(min);
   }

   /**
    * Clamps the maximum value of the tuple parameter to the max parameter and
    * places the values into this tuple.
    *
    * @param max the highest value in the tuple after clamping
    * @param t   the source tuple, which will not be modified
    */
   public final void clampMax(float max, Tuple3f t)
   {
      set(t);
      clampMax(max);
   }


   /**
    * Sets each component of the tuple parameter to its absolute value and
    * places the modified values into this tuple.
    *
    * @param t the source tuple, which will not be modified
    */
   public final void absolute(Tuple3f t)
   {
      set(t);
      absolute();
   }

   /**
    * Clamps this tuple to the range [low, high].
    *
    * @param min the lowest value in this tuple after clamping
    * @param max the highest value in this tuple after clamping
    */
   public final void clamp(float min, float max)
   {
      clampMin(min);
      clampMax(max);
   }

   /**
    * Clamps the minimum value of this tuple to the min parameter.
    *
    * @param min the lowest value in this tuple after clamping
    */
   public final void clampMin(float min)
   {
      if (x < min)
         x = min;
      if (y < min)
         y = min;
      if (z < min)
         z = min;
   }

   /**
    * Clamps the maximum value of this tuple to the max parameter.
    *
    * @param max the highest value in the tuple after clamping
    */
   public final void clampMax(float max)
   {
      if (x > max)
         x = max;
      if (y > max)
         y = max;
      if (z > max)
         z = max;
   }

   /**
    * Sets each component of this tuple to its absolute value.
    */
   public final void absolute()
   {
      if (x < 0.0)
         x = -x;
      if (y < 0.0)
         y = -y;
      if (z < 0.0)
         z = -z;
   }

   /**
    * Linearly interpolates between tuples t1 and t2 and places the
    * result into this tuple: this = (1-alpha)*t1 + alpha*t2.
    *
    * @param t1    the first tuple
    * @param t2    the second tuple
    * @param alpha the alpha interpolation parameter
    */
   public final void interpolate(Tuple3f t1, Tuple3f t2, float alpha)
   {
      set(t1);
      interpolate(t2, alpha);
   }


   /**
    * Linearly interpolates between this tuple and tuple t1 and places the
    * result into this tuple: this = (1-alpha)*this + alpha*t1.
    *
    * @param t1    the first tuple
    * @param alpha the alpha interpolation parameter
    */
   public final void interpolate(Tuple3f t1, float alpha)
   {
      float beta = 1 - alpha;
      x = beta * x + alpha * t1.x;
      y = beta * y + alpha * t1.y;
      z = beta * z + alpha * t1.z;
   }

   /**
    * Returns if this Tuple is normalized by checking for length being 1
    * @return
    */
   public final boolean isNormal()
   {
      // return Math.abs(length - 1.0F) < 0.001F;
      return Math.abs((float) Math.sqrt(x * x + y * y + z * z) - 1.0F) < 0.001F;
   }

   /**
    * Returns if the x, y, z components are 1
    *
    * @return boolean
    */
   public final boolean isOne()
   {
      return x == 1 && y == 1 && z == 1;
   }

   /**
    * Returns if the x, y, z components are within unity magnitudes
    *
    * @return boolean
    */
   public final boolean isUnity()
   {
      return (x >= -1 && x <= 1) && (y >= -1 && y <= 1) && (z >= -1 && z <= 1);
   }

   /**
    * Returns if the x, y, z components are 0
    *
    * @return boolean
    */
   public final boolean isZero()
   {
      return x == 0 && y == 0 && z == 0;
   }

   /**
    * Computes the square of the distance between this tuple and tuple t1.
    *
    * @param t1 the other tuple
    * @return the square of distance between these two tuples as a float
    */
   public final float distanceSquaredF(Tuple3f t1)
   {
      float dx = x - t1.x;
      float dy = y - t1.y;
      float dz = z - t1.z;
      return dx * dx + dy * dy + dz * dz;
   }

   /**
    * Returns the distance between this tuple and tuple t1.
    *
    * @param t1 the other tuple
    * @return the distance between these two tuples as a float
    */
   public final float distanceF(Tuple3f t1)
   {
      float dx = x - t1.x;
      float dy = y - t1.y;
      float dz = z - t1.z;
      return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
   }

   /**
    * Computes the L-1 (Manhattan) distance between this tuple and tuple t1.
    * The L-1 distance is equal to abs(x1-x2) + abs(y1-y2).
    *
    * @param t1 the other tuple
    */
   public final float distanceL1F(Tuple3f t1)
   {
      return Math.abs(x - t1.x) + Math.abs(y - t1.y) + Math.abs(z - t1.z);
   }

   /**
    * Computes the L-infinite distance between this tuple and tuple t1.
    * The L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
    *
    * @param t1 the other tuple
    */
   public final float distanceLinfF(Tuple3f t1)
   {
      return Math.max(Math.max(Math.abs(x - t1.x), Math.abs(y - t1.y)), Math.abs(z - t1.z));
   }

   /**
    * Computes the square of the distance between this tuple and tuple t1.
    *
    * @param t1 the other tuple
    * @return the square of distance between these two tuples as a float
    */
   public final double distanceSquaredD(Tuple3f t1)
   {
      float dx = x - t1.x;
      float dy = y - t1.y;
      float dz = z - t1.z;
      return dx * dx + dy * dy + dz * dz;
   }

   /**
    * Returns the distance between this tuple and tuple t1.
    *
    * @param t1 the other tuple
    * @return the distance between these two tuples as a float
    */
   public final double distanceD(Tuple3f t1)
   {
      float dx = x - t1.x;
      float dy = y - t1.y;
      float dz = z - t1.z;
      return Math.sqrt(dx * dx + dy * dy + dz * dz);
   }

   /**
    * Computes the L-1 (Manhattan) distance between this tuple and tuple t1.
    * The L-1 distance is equal to abs(x1-x2) + abs(y1-y2).
    *
    * @param t1 the other tuple
    */
   public final double distanceL1D(Tuple3f t1)
   {
      return Math.abs(x - t1.x) + Math.abs(y - t1.y) + Math.abs(z - t1.z);
   }

   /**
    * Computes the L-infinite distance between this tuple and tuple t1.
    * The L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
    *
    * @param t1 the other tuple
    */
   public final double distanceLinfD(Tuple3f t1)
   {
      return Math.max(Math.max(Math.abs(x - t1.x), Math.abs(y - t1.y)), Math.abs(z - t1.z));
   }

   /**
    * Returns the squared length of this vector.
    *
    * @return the squared length of this vector
    */
   public final float lengthSquared()
   {
      return x * x + y * y + z * z;
   }

   /**
    * Returns the length of this vector.
    *
    * @return the length of this vector
    */
   public final float length()
   {
      return (float) Math.sqrt(x * x + y * y + z * z);
   }

   /**
    * Sets this vector to be the vector cross product of vectors v1 and v2.
    *
    * @param v1 the first vector
    * @param v2 the second vector
    */
   public final void cross(Vector3f v1, Vector3f v2)
   {
      set(
       v1.y * v2.z - v1.z * v2.y,
       v1.z * v2.x - v1.x * v2.z,
       v1.x * v2.y - v1.y * v2.x
      );
   }

   /**
    * Computes the dot product of the this vector and vector v1.
    *
    * @param v1 the other vector
    */
   public final float dot(Vector3f v1)
   {
      return x * v1.x + y * v1.y + z * v1.z;
   }

   /**
    * Sets the value of this vector to the normalization of vector v1.
    *
    * @param v1 the un-normalized vector
    */
   public final void normalize(Vector3f v1)
   {
      set(v1);
      normalize();
   }

   /**
    * Normalizes this vector in place.
    */
   public final void normalize()
   {
      float d = (float)Math.sqrt(x * x + y * y + z * z);

      // zero-div may occur.
      x /= d;
      y /= d;
      z /= d;
   }

   /**
    * Returns the angle in radians between this vector and
    * the vector parameter; the return value is constrained to the
    * range [0,PI].
    *
    * @param v1 the other vector
    * @return the angle in radians in the range [0,PI]
    */
   public final float angle(Vector3f v1)
   {
      // return (double)Math.acos(dot(v1)/v1.length()/v.length());
      // Numerically, near 0 and PI are very bad condition for acos.
      // In 3-space, |atan2(sin,cos)| is much stable.

      float xx = y * v1.z - z * v1.y;
      float yy = z * v1.x - x * v1.z;
      float zz = x * v1.y - y * v1.x;
      float cross = (float)Math.sqrt(xx * xx + yy * yy + zz * zz);

      return (float) Math.abs(Math.atan2(cross, dot(v1)));
   }
}
