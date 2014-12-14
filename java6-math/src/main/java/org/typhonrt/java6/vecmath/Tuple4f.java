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
 * A generic 4 element tuple that is represented by single precision floating point x,y,z and w coordinates.
 *
 * @author  Michael Leahy
 */
public class Tuple4f
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
    * The w coordinate.
    */
   public float w;

   /**
    * Constructs and initializes a Tuple4f from the specified xyzw coordinates.
    *
    * @param x the x coordinate
    * @param y the y coordinate
    * @param z the z coordinate
    * @param w the w coordinate
    */
   public Tuple4f(float x, float y, float z, float w)
   {
      set(x, y, z, w);
   }

   /**
    * Constructs and initializes a Tuple4f from the specified array.
    *
    * @param t the array of length 4 containing xyzw in order
    */
   public Tuple4f(float t[])
   {
      set(t);
   }

   /**
    * Constructs and initializes a Tuple4f from the specified Tuple4f.
    *
    * @param t1 the Tuple4f containing the initialization x y z w data
    */
   public Tuple4f(Tuple4f t1)
   {
      set(t1);
   }

   /**
    * Constructs and initializes a Tuple4f to (0,0,0,0).
    */
   public Tuple4f()
   {
      x = 0.0f;
      y = 0.0f;
      z = 0.0f;
      w = 0.0f;
   }

   /**
    * Sets the value of this tuple to the specified xyzw coordinates.
    *
    * @param x the x coordinate
    * @param y the y coordinate
    * @param z the z coordinate
    * @param w the w coordinate
    */
   public final Tuple4f set(float x, float y, float z, float w)
   {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
      return this;
   }

   /**
    * Sets the value of this tuple from the 4 values specified in the array.
    *
    * @param t the array of length 4 containing xyzw in order
    */
   public final Tuple4f set(float t[])
   {
      // ArrayIndexOutOfBounds is thrown if t.length < 4
      x = t[0];
      y = t[1];
      z = t[2];
      w = t[3];
      return this;
   }

   /**
    * Sets the value of this tuple from the 4 values specified in the array.
    *
    * @param t the array of length 4 containing xyzw in order
    */
   public final Tuple4f set(float t[], int index)
   {
      // ArrayIndexOutOfBounds is thrown if t.length < index + 3
      x = t[index];
      y = t[index + 1];
      z = t[index + 2];
      w = t[index + 3];
      return this;
   }

   /**
    * Sets the value of this tuple to the value of tuple t1.
    *
    * @param t1 the tuple to be copied
    */
   public final Tuple4f set(Tuple4f t1)
   {
      x = t1.x;
      y = t1.y;
      z = t1.z;
      w = t1.w;
      return this;
   }

   /**
    * Sets the x,y,z components of this point to the corresponding
    * components of tuple t1. The w component of this point is setComponent to 0.
    *
    * @param t1 the tuple to be copied
    * @since Java3D 1.2
    */
   public final Tuple4f set(Tuple3f t1)
   {
      x = t1.x;
      y = t1.y;
      z = t1.z;
      w = 0.0f;
      return this;
   }

   /**
    * Copies the value of the elements of this tuple into the array t[].
    *
    * @param t the array that will contain the values of the vector
    */
   public final Tuple4f get(float t[])
   {
      // ArrayIndexOutOfBounds is thrown if t.length < 4
      t[0] = x;
      t[1] = y;
      t[2] = z;
      t[3] = w;
      return this;
   }

   /**
    * Copies the value of the elements of this tuple into the array t[].
    *
    * @param t the array that will contain the values of the vector
    */
   public final Tuple4f get(float t[], int index)
   {
      // ArrayIndexOutOfBounds is thrown if t.length < index + 3
      t[index] = x;
      t[index + 1] = y;
      t[index + 2] = z;
      t[index + 3] = w;
      return this;
   }

   /**
    * Gets the value of this tuple and copies the values into the Tuple4f.
    *
    * @param t Tuple4f object into which that values of this object are copied
    */
   public final Tuple4f get(Tuple4f t)
   {
      t.x = x;
      t.y = y;
      t.z = z;
      t.w = w;
      return this;
   }

   /**
    * Sets the value of this tuple to the vector sum of tuples t1 and t2.
    *
    * @param t1 the first tuple
    * @param t2 the second tuple
    */
   public final Tuple4f add(Tuple4f t1, Tuple4f t2)
   {
      x = t1.x + t2.x;
      y = t1.y + t2.y;
      z = t1.z + t2.z;
      w = t1.w + t2.w;
      return this;
   }

   /**
    * Sets the value of this tuple to the vector sum of itself and tuple t1.
    *
    * @param t1 the other tuple
    */
   public final Tuple4f add(Tuple4f t1)
   {
      x += t1.x;
      y += t1.y;
      z += t1.z;
      w += t1.w;
      return this;
   }


   /**
    * Sets the value of this tuple to the vector difference of tuple t1 and t2 (this = t1 - t2).
    *
    * @param t1 the first tuple
    * @param t2 the second tuple
    */
   public final Tuple4f sub(Tuple4f t1, Tuple4f t2)
   {
      x = t1.x - t2.x;
      y = t1.y - t2.y;
      z = t1.z - t2.z;
      w = t1.w - t2.w;
      return this;
   }

   /**
    * Sets the value of this tuple to the vector difference of itself and tuple t1 (this = this - t1).
    *
    * @param t1 the other tuple
    */
   public final Tuple4f sub(Tuple4f t1)
   {
      x -= t1.x;
      y -= t1.y;
      z -= t1.z;
      w -= t1.w;
      return this;
   }

   /**
    * Calculates the midpoint between this tuple and another.
    * (e.g. x from x value, y from y, etc.)
    *
    * @param t The other tuple to subtract
    */
   public final Tuple4f mid(Tuple4f t)
   {
      x = (x + t.x) / 2;
      y = (y + t.y) / 2;
      z = (z + t.z) / 2;
      w = (w + t.w) / 2;
      return this;
   }

   /**
    * Sets the value of this tuple to the negation of tuple t1.
    *
    * @param t1 the source vector
    */
   public final Tuple4f negate(Tuple4f t1)
   {
      x = -t1.x;
      y = -t1.y;
      z = -t1.z;
      w = -t1.w;
      return this;
   }

   /**
    * Negates the value of this vector in place.
    */
   public final Tuple4f negate()
   {
      x = -x;
      y = -y;
      z = -z;
      w = -w;
      return this;
   }

   /**
    * Normalizes this vector in place.
    */
   public final void normalize()
   {
      double d = Math.sqrt(x * x + y * y + z * z + w * w);

      // zero-div may occur.
      x /= d;
      y /= d;
      z /= d;
      w /= d;
   }

   /**
    * Multiplies each of the x,y,z components of the Tuple4f parameter by 1/w,
    * places the projected values into this point, and places a 1 as the w
    * parameter of this point.
    *
    * @param p1 the source Tuple4f, which is not modified
    */
   public final void project(Tuple4f p1)
   {
      // zero div may occur.
      x = p1.x / p1.w;
      y = p1.y / p1.w;
      z = p1.z / p1.w;
      w = 1.0f;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of tuple t1.
    *
    * @param s  the scalar value
    * @param t1 the source tuple
    */
   public final Tuple4f scale(float s, Tuple4f t1)
   {
      x = s * t1.x;
      y = s * t1.y;
      z = s * t1.z;
      w = s * t1.w;
      return this;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of itself.
    *
    * @param s the scalar value
    */
   public final Tuple4f scale(float s)
   {
      x *= s;
      y *= s;
      z *= s;
      w *= s;
      return this;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of tuple t1 and then
    * adds tuple t2 (this = s*t1 + t2).
    *
    * @param s  the scalar value
    * @param t1 the tuple to be multipled
    * @param t2 the tuple to be added
    */
   public final Tuple4f scaleAdd(float s, Tuple4f t1, Tuple4f t2)
   {
      x = s * t1.x + t2.x;
      y = s * t1.y + t2.y;
      z = s * t1.z + t2.z;
      w = s * t1.w + t2.w;
      return this;
   }

   /**
    * Sets the value of this tuple to the scalar multiplication of itself and then
    * adds tuple t1 (this = s*this + t1).
    *
    * @param s  the scalar value
    * @param t1 the tuple to be added
    */
   public final Tuple4f scaleAdd(float s, Tuple4f t1)
   {
      x = s * x + t1.x;
      y = s * y + t1.y;
      z = s * z + t1.z;
      w = s * z + t1.w;
      return this;
   }

   /**
    * Returns a hash number based on the data values in this object.
    * Two different Tuple4f objects with identical data  values
    * (ie, returns true for equals(Tuple4f) ) will return the same hash number.
    * Two vectors with different data members may return the same hash value,
    * although this is not likely.
    */
   @Override
   public int hashCode()
   {
      return Float.floatToIntBits(x) ^
       Float.floatToIntBits(y) ^
       Float.floatToIntBits(z) ^
       Float.floatToIntBits(w);
   }

   /**
    * Returns true if all of the data members of Tuple4f t1 are equal to the corresponding
    * data members in this
    *
    * @param t1 the vector with which the comparison is made.
    */
   public boolean equals(Tuple4f t1)
   {
      return t1 != null && x == t1.x && y == t1.y && z == t1.z && w == t1.w;
   }

   /**
    * Returns true if the L-infinite distance between this tuple and tuple t1 is
    * less than or equal to the epsilon parameter, otherwise returns false. The L-infinite
    * distance is equal to MAX[abs(x1-x2), abs(y1-y2), abs(z1-z2), abs(w1-w2)].
    *
    * @param t1      the tuple to be compared to this tuple
    * @param epsilon the threshold value
    */
   public boolean epsilonEquals(Tuple4f t1, float epsilon)
   {
      return (Math.abs(t1.x - this.x) <= epsilon) &&
       (Math.abs(t1.y - this.y) <= epsilon) &&
       (Math.abs(t1.z - this.z) <= epsilon) &&
       (Math.abs(t1.w - this.w) <= epsilon);
   }

   /**
    * Returns a string that contains the values of this Tuple4f. The form is (x,y,z,w).
    *
    * @return the String representation
    */
   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder(VecmathStrings.s_STR_OPEN_X);
      sb.append(x).append(VecmathStrings.s_STR_Y).append(y).append(VecmathStrings.s_STR_Z).append(z).append(VecmathStrings.s_STR_W).append(w).append(VecmathStrings.s_STR_CLOSE);
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
   public final Tuple4f clamp(float min, float max, Tuple4f t)
   {
      set(t);
      clamp(min, max);
      return this;
   }

   /**
    * Clamps the minimum value of the tuple parameter to the min parameter
    * and places the values into this tuple.
    *
    * @param min the lowest value in the tuple after clamping
    * @param t   the source tuple, which will not be modified
    */
   public final Tuple4f clampMin(float min, Tuple4f t)
   {
      set(t);
      clampMin(min);
      return this;
   }

   /**
    * Clamps the maximum value of the tuple parameter to the max parameter and
    * places the values into this tuple.
    *
    * @param max the highest value in the tuple after clamping
    * @param t   the source tuple, which will not be modified
    */
   public final Tuple4f clampMax(float max, Tuple4f t)
   {
      set(t);
      clampMax(max);
      return this;
   }


   /**
    * Sets each component of the tuple parameter to its absolute value and
    * places the modified values into this tuple.
    *
    * @param t the source tuple, which will not be modified
    */
   public final Tuple4f absolute(Tuple4f t)
   {
      set(t);
      absolute();
      return this;
   }

   /**
    * Clamps this tuple to the range [low, high].
    *
    * @param min the lowest value in this tuple after clamping
    * @param max the highest value in this tuple after clamping
    */
   public final Tuple4f clamp(float min, float max)
   {
      clampMin(min);
      clampMax(max);
      return this;
   }

   /**
    * Clamps the minimum value of this tuple to the min parameter.
    *
    * @param min the lowest value in this tuple after clamping
    */
   public final Tuple4f clampMin(float min)
   {
      if (x < min)
         x = min;
      if (y < min)
         y = min;
      if (z < min)
         z = min;
      if (w < min)
         w = min;
      return this;
   }

   /**
    * Clamps the maximum value of this tuple to the max parameter.
    *
    * @param max the highest value in the tuple after clamping
    */
   public final Tuple4f clampMax(float max)
   {
      if (x > max)
         x = max;
      if (y > max)
         y = max;
      if (z > max)
         z = max;
      if (w > max)
         w = max;
      return this;
   }

   /**
    * Sets each component of this tuple to its absolute value.
    */
   public final Tuple4f absolute()
   {
      if (x < 0.0)
         x = -x;
      if (y < 0.0)
         y = -y;
      if (z < 0.0)
         z = -z;
      if (w < 0.0)
         w = -w;
      return this;
   }

   /**
    * Linearly interpolates between tuples t1 and t2 and places the
    * result into this tuple: this = (1-alpha)*t1 + alpha*t2.
    *
    * @param t1    the first tuple
    * @param t2    the second tuple
    * @param alpha the alpha interpolation parameter
    */
   public final Tuple4f interpolate(Tuple4f t1, Tuple4f t2, float alpha)
   {
      set(t1);
      interpolate(t2, alpha);
      return this;
   }


   /**
    * Linearly interpolates between this tuple and tuple t1 and places the
    * result into this tuple: this = (1-alpha)*this + alpha*t1.
    *
    * @param t1    the first tuple
    * @param alpha the alpha interpolation parameter
    */
   public final Tuple4f interpolate(Tuple4f t1, float alpha)
   {
      float beta = 1 - alpha;
      x = beta * x + alpha * t1.x;
      y = beta * y + alpha * t1.y;
      z = beta * z + alpha * t1.z;
      w = beta * w + alpha * t1.w;
      return this;
   }

   /**
    * Returns if this Tuple is normalized by checking for length being 1
    * @return
    */
   public final boolean isNormal()
   {
      // return Math.abs(length - 1.0F) < 0.001F;
      return Math.abs((float) Math.sqrt(x * x + y * y + z * z + w * w) - 1.0F) < 0.001F;
   }

   /**
    * Returns if the x, y, z, w components are 1
    *
    * @return boolean
    */
   public final boolean isOne()
   {
      return x == 1 && y == 1 && z == 1 && w == 1;
   }

   /**
    * Returns if the x, y, z, w components are within unity magnitudes
    *
    * @return boolean
    */
   public final boolean isUnity()
   {
      return (x >= -1 && x <= 1) && (y >= -1 && y <= 1) && (z >= -1 && z <= 1) && (w >= -1 && w <= 1);
   }

   /**
    * Returns if the x, y, z, w components are 0
    *
    * @return boolean
    */
   public final boolean isZero()
   {
      return x == 0 && y == 0 && z == 0 && w == 0;
   }

   /**
    * Computes the square of the distance between this tuple and tuple t1.
    *
    * @param t1 the other tuple
    * @return the square of distance between this tuple and t1
    */
   public final float distanceSquaredF(Tuple4f t1)
   {
      float dx = x - t1.x;
      float dy = y - t1.y;
      float dz = z - t1.z;
      float dw = w - t1.w;
      return dx * dx + dy * dy + dz * dz + dw * dw;
   }

   /**
    * Returns the distance between this tuple and tuple t1.
    *
    * @param t1 the other tuple
    * @return the distance between this tuple and tuple t1.
    */
   public final float distanceF(Tuple4f t1)
   {
      float dx = x - t1.x;
      float dy = y - t1.y;
      float dz = z - t1.z;
      float dw = w - t1.w;
      return (float)Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
   }

   /**
    * Computes the L-1 (Manhattan) distance between this tuple and tuple t1.
    * The L-1 distance is equal to abs(x1-x2) + abs(y1-y2)
    * + abs(z1-z2) + abs(w1-w2).
    *
    * @param t1 the other tuple
    * @return L-1 distance
    */
   public final float distanceL1F(Tuple4f t1)
   {
      return Math.abs(x - t1.x) + Math.abs(y - t1.y) + Math.abs(z - t1.z) + Math.abs(w - t1.w);
   }

   /**
    * Computes the L-infinite distance between this tuple and tuple t1.
    * The L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2), abs(z1-z2), abs(w1-w2)].
    *
    * @param t1 the other tuple
    * @return L-infinite distance
    */
   public final float distanceLinfF(Tuple4f t1)
   {
      return Math.max(Math.max(Math.abs(x - t1.x), Math.abs(y - t1.y)),
       Math.max(Math.abs(z - t1.z), Math.abs(w - t1.w)));
   }

   /**
    * Computes the square of the distance between this tuple and tuple t1.
    *
    * @param t1 the other tuple
    * @return the square of distance between this tuple and t1
    */
   public final double distanceSquaredD(Tuple4f t1)
   {
      float dx = x - t1.x;
      float dy = y - t1.y;
      float dz = z - t1.z;
      float dw = w - t1.w;
      return dx * dx + dy * dy + dz * dz + dw * dw;
   }

   /**
    * Returns the distance between this tuple and tuple t1.
    *
    * @param t1 the other tuple
    * @return the distance between this tuple and tuple t1.
    */
   public final double distanceD(Tuple4f t1)
   {
      float dx = x - t1.x;
      float dy = y - t1.y;
      float dz = z - t1.z;
      float dw = w - t1.w;
      return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
   }

   /**
    * Computes the L-1 (Manhattan) distance between this tuple and tuple t1.
    * The L-1 distance is equal to abs(x1-x2) + abs(y1-y2)
    * + abs(z1-z2) + abs(w1-w2).
    *
    * @param t1 the other tuple
    * @return L-1 distance
    */
   public final double distanceL1D(Tuple4f t1)
   {
      return Math.abs(x - t1.x) + Math.abs(y - t1.y)
       + Math.abs(z - t1.z) + Math.abs(w - t1.w);
   }

   /**
    * Computes the L-infinite distance between this tuple and tuple t1.
    * The L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2), abs(z1-z2), abs(w1-w2)].
    *
    * @param t1 the other tuple
    * @return L-infinite distance
    */
   public final double distanceLinfD(Tuple4f t1)
   {
      return Math.max(Math.max(Math.abs(x - t1.x), Math.abs(y - t1.y)),
       Math.max(Math.abs(z - t1.z), Math.abs(w - t1.w)));
   }
}
