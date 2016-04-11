/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.typhonrt.java6.vecmath;

/**
 * A single precision floating point 3 by 3 matrix.
 *
 * @author  Michael Leahy
 */
public class Matrix3f
{
   /**
    * The first element of the first row.
    */
   public float m00;

   /**
    * The second element of the first row.
    */
   public float m01;

   /**
    * third element of the first row.
    */
   public float m02;

   /**
    * The first element of the second row.
    */
   public float m10;

   /**
    * The second element of the second row.
    */
   public float m11;

   /**
    * The third element of the second row.
    */
   public float m12;

   /**
    * The first element of the third row.
    */
   public float m20;

   /**
    * The second element of the third row.
    */
   public float m21;

   /**
    * The third element of the third row.
    */
   public float m22;

   /**
    * Constructs a new matrix with the same values as the Matrix3f parameter.
    *
    * @param m1 The source matrix.
    */
   public Matrix3f(Matrix3f m1)
   {
      m00 = m1.m00;
      m01 = m1.m01;
      m02 = m1.m02;
      m10 = m1.m10;
      m11 = m1.m11;
      m12 = m1.m12;
      m20 = m1.m20;
      m21 = m1.m21;
      m22 = m1.m22;
   }

   /**
    * Constructs and initializes a Matrix3f to all zeros.
    */
   public Matrix3f()
   {
      setZero();
   }

   /**
    * Returns a string that contains the values of this Matrix3f.
    *
    * @return the String representation
    */
   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder();

      sb.append(VecmathStrings.s_STR_OPEN).append(VecmathStrings.s_STR_LINE_SEPARATOR).append(VecmathStrings.s_STR_OPEN_INDENT).append(m00).append('\t').append(
       m01).append('\t').append(m02).append(VecmathStrings.s_STR_CLOSE).append(VecmathStrings.s_STR_LINE_SEPARATOR);

      sb.append(VecmathStrings.s_STR_OPEN_INDENT).append(m10).append('\t').append(m11).append('\t').append(m22).append(
       VecmathStrings.s_STR_CLOSE).append(VecmathStrings.s_STR_LINE_SEPARATOR);

      sb.append(VecmathStrings.s_STR_OPEN_INDENT).append(m20).append('\t').append(m21).append('\t').append(m22).append(
       VecmathStrings.s_STR_DOUBLE_CLOSE);

      return sb.toString();
   }

   /**
    * Sets this Matrix3f to identity.
    */
   public final void setIdentity()
   {
      m00 = 1.0f;
      m01 = 0.0f;
      m02 = 0.0f;
      m10 = 0.0f;
      m11 = 1.0f;
      m12 = 0.0f;
      m20 = 0.0f;
      m21 = 0.0f;
      m22 = 1.0f;
   }

   /**
    * Sets the scale component of the current matrix by factoring out the
    * current scale (by doing an SVD) from the rotational component and
    * multiplying by the new scale.
    *
    * @param scale the new scale amount
    */
   public final void setScale(float scale)
   {
      SVD(this);
      mul(scale);
   }

   /**
    * Sets the specified element of this matrix3d to the value provided.
    *
    * @param row    the row number to be modified (zero indexed)
    * @param column the column number to be modified (zero indexed)
    * @param value  the new value
    */
   public final void setElement(int row, int column, float value)
   {
      if (row == 0)
      {
         if (column == 0)
         {
            m00 = value;
         }
         else if (column == 1)
         {
            m01 = value;
         }
         else if (column == 2)
         {
            m02 = value;
         }
         else
         {
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
         }
      }
      else if (row == 1)
      {
         if (column == 0)
         {
            m10 = value;
         }
         else if (column == 1)
         {
            m11 = value;
         }
         else if (column == 2)
         {
            m12 = value;
         }
         else
         {
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
         }
      }
      else if (row == 2)
      {
         if (column == 0)
         {
            m20 = value;
         }
         else if (column == 1)
         {
            m21 = value;
         }
         else if (column == 2)
         {
            m22 = value;
         }
         else
         {
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
         }
      }
      else
      {
         throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + row);
      }
   }

   /**
    * Retrieves the value at the specified row and column of this matrix.
    *
    * @param row    the row number to be retrieved (zero indexed)
    * @param column the column number to be retrieved (zero indexed)
    * @return the value at the indexed element
    */
   public final float getElement(int row, int column)
   {
      if (row == 0)
      {
         if (column == 0)
         {
            return m00;
         }
         else if (column == 1)
         {
            return m01;
         }
         else if (column == 2)
         {
            return m02;
         }
         else
         {
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
         }
      }
      else if (row == 1)
      {
         if (column == 0)
         {
            return m10;
         }
         else if (column == 1)
         {
            return m11;
         }
         else if (column == 2)
         {
            return m12;
         }
         else
         {
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
         }
      }
      else if (row == 2)
      {
         if (column == 0)
         {
            return m20;
         }
         else if (column == 1)
         {
            return m21;
         }
         else if (column == 2)
         {
            return m22;
         }
         else
         {
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
         }
      }
      else
      {
         throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + row);
      }
   }


   /**
    * Sets the specified row of this matrix3d to the three values provided.
    *
    * @param row the row number to be modified (zero indexed)
    * @param x   the first column element
    * @param y   the second column element
    * @param z   the third column element
    */
   public final void setRow(int row, float x, float y, float z)
   {
      switch (row)
      {
         case 0:
            m00 = x;
            m01 = y;
            m02 = z;
            break;

         case 1:
            m10 = x;
            m11 = y;
            m12 = z;
            break;

         case 2:
            m20 = x;
            m21 = y;
            m22 = z;
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + row);
      }
   }

   /**
    * Sets the specified row of this matrix3d to the Vector provided.
    *
    * @param row the row number to be modified (zero indexed)
    * @param v   the replacement row
    */
   public final void setRow(int row, Vector3f v)
   {
      switch (row)
      {
         case 0:
            m00 = v.x;
            m01 = v.y;
            m02 = v.z;
            break;

         case 1:
            m10 = v.x;
            m11 = v.y;
            m12 = v.z;
            break;

         case 2:
            m20 = v.x;
            m21 = v.y;
            m22 = v.z;
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + row);
      }
   }

   /**
    * Copies the matrix values in the specified row into the
    * array parameter.
    *
    * @param row the matrix row
    * @param v   The array into which the matrix row values will be copied
    */
   public final void getRow(int row, float v[])
   {
      switch (row)
      {
         case 0:
            v[0] = m00;
            v[1] = m01;
            v[2] = m02;
            break;

         case 1:
            v[0] = m10;
            v[1] = m11;
            v[2] = m12;
            break;

         case 2:
            v[0] = m20;
            v[1] = m21;
            v[2] = m22;
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + row);
      }
   }

   /**
    * Copies the matrix values in the specified row into the
    * vector parameter.
    *
    * @param row the matrix row
    * @param v   The vector into which the matrix row values will be copied
    */
   public final void getRow(int row, Vector3f v)
   {
      switch (row)
      {
         case 0:
            v.x = m00;
            v.y = m01;
            v.z = m02;
            break;

         case 1:
            v.x = m10;
            v.y = m11;
            v.z = m12;
            break;

         case 2:
            v.x = m20;
            v.y = m21;
            v.z = m22;
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + row);
      }
   }

   /**
    * Sets the specified row of this matrix3d to the four values provided.
    *
    * @param row the row number to be modified (zero indexed)
    * @param v   the replacement row
    */
   public final void setRow(int row, float v[])
   {
      switch (row)
      {
         case 0:
            m00 = v[0];
            m01 = v[1];
            m02 = v[2];
            break;

         case 1:
            m10 = v[0];
            m11 = v[1];
            m12 = v[2];
            break;

         case 2:
            m20 = v[0];
            m21 = v[1];
            m22 = v[2];
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + row);
      }
   }

   /**
    * Sets the specified column of this matrix3d to the three values provided.
    *
    * @param column the column number to be modified (zero indexed)
    * @param x      the first row element
    * @param y      the second row element
    * @param z      the third row element
    */
   public final void setColumn(int column, float x, float y, float z)
   {
      switch (column)
      {
         case 0:
            m00 = x;
            m10 = y;
            m20 = z;
            break;

         case 1:
            m01 = x;
            m11 = y;
            m21 = z;
            break;

         case 2:
            m02 = x;
            m12 = y;
            m22 = z;
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
      }
   }

   /**
    * Sets the specified column of this matrix3d to the vector provided.
    *
    * @param column the column number to be modified (zero indexed)
    * @param v      the replacement column
    */
   public final void setColumn(int column, Vector3f v)
   {
      switch (column)
      {
         case 0:
            m00 = v.x;
            m10 = v.y;
            m20 = v.z;
            break;

         case 1:
            m01 = v.x;
            m11 = v.y;
            m21 = v.z;
            break;

         case 2:
            m02 = v.x;
            m12 = v.y;
            m22 = v.z;
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
      }
   }

   /**
    * Sets the specified column of this matrix3d to the four values provided.
    *
    * @param column the column number to be modified (zero indexed)
    * @param v      the replacement column
    */
   public final void setColumn(int column, float v[])
   {
      switch (column)
      {
         case 0:
            m00 = v[0];
            m10 = v[1];
            m20 = v[2];
            break;

         case 1:
            m01 = v[0];
            m11 = v[1];
            m21 = v[2];
            break;

         case 2:
            m02 = v[0];
            m12 = v[1];
            m22 = v[2];
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
      }
   }

   /**
    * Copies the matrix values in the specified column into the vector
    * parameter.
    *
    * @param column the matrix column
    * @param v      The vector into which the matrix row values will be copied
    */
   public final void getColumn(int column, Vector3f v)
   {
      switch (column)
      {
         case 0:
            v.x = m00;
            v.y = m10;
            v.z = m20;
            break;

         case 1:
            v.x = m01;
            v.y = m11;
            v.z = m21;
            break;

         case 2:
            v.x = m02;
            v.y = m12;
            v.z = m22;
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
      }
   }

   /**
    * Copies the matrix values in the specified column into the array
    * parameter.
    *
    * @param column the matrix column
    * @param v      The array into which the matrix row values will be copied
    */
   public final void getColumn(int column, float v[])
   {
      switch (column)
      {
         case 0:
            v[0] = m00;
            v[1] = m10;
            v[2] = m20;
            break;

         case 1:
            v[0] = m01;
            v[1] = m11;
            v[2] = m21;
            break;

         case 2:
            v[0] = m02;
            v[1] = m12;
            v[2] = m22;
            break;

         default:
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + column);
      }
   }


   /**
    * Performs an SVD normalization of this matrix to calculate and return the
    * uniform scale factor. This matrix is not modified.
    *
    * @return the scale factor of this matrix
    */
   public final float getScale()
   {
      return SVD(null);
   }


   /**
    * Adds a scalar to each component of this matrix.
    *
    * @param scalar The scalar adder.
    */
   public final void add(float scalar)
   {
      m00 += scalar;
      m01 += scalar;
      m02 += scalar;
      m10 += scalar;
      m11 += scalar;
      m12 += scalar;
      m20 += scalar;
      m21 += scalar;
      m22 += scalar;
   }

   /**
    * Adds a scalar to each component of the matrix m1 and places
    * the result into this. Matrix m1 is not modified.
    *
    * @param scalar The scalar adder.
    * @param m1     The original matrix values.
    */
   public final void add(float scalar, Matrix3f m1)
   {
      set(m1);
      add(scalar);
   }


   /**
    * Sets the value of this matrix to the matrix sum of matrices m1 and m2.
    *
    * @param m1 the first matrix
    * @param m2 the second matrix
    */
   public final void add(Matrix3f m1, Matrix3f m2)
   {
      // note this is alias safe.
      set(
       m1.m00 + m2.m00,
       m1.m01 + m2.m01,
       m1.m02 + m2.m02,
       m1.m10 + m2.m10,
       m1.m11 + m2.m11,
       m1.m12 + m2.m12,
       m1.m20 + m2.m20,
       m1.m21 + m2.m21,
       m1.m22 + m2.m22
      );
   }

   /**
    * Sets the value of this matrix to sum of itself and matrix m1.
    *
    * @param m1 the other matrix
    */
   public final void add(Matrix3f m1)
   {
      m00 += m1.m00;
      m01 += m1.m01;
      m02 += m1.m02;
      m10 += m1.m10;
      m11 += m1.m11;
      m12 += m1.m12;
      m20 += m1.m20;
      m21 += m1.m21;
      m22 += m1.m22;
   }

   /**
    * Sets the value of this matrix to the matrix difference
    * of matrices m1 and m2.
    *
    * @param m1 the first matrix
    * @param m2 the second matrix
    */
   public final void sub(Matrix3f m1, Matrix3f m2)
   {
      set(
       m1.m00 - m2.m00,
       m1.m01 - m2.m01,
       m1.m02 - m2.m02,
       m1.m10 - m2.m10,
       m1.m11 - m2.m11,
       m1.m12 - m2.m12,
       m1.m20 - m2.m20,
       m1.m21 - m2.m21,
       m1.m22 - m2.m22
      );
   }

   /**
    * Sets the value of this matrix to the matrix difference of itself
    * and matrix m1 (this = this - m1).
    *
    * @param m1 the other matrix
    */
   public final void sub(Matrix3f m1)
   {
      m00 -= m1.m00;
      m01 -= m1.m01;
      m02 -= m1.m02;
      m10 -= m1.m10;
      m11 -= m1.m11;
      m12 -= m1.m12;
      m20 -= m1.m20;
      m21 -= m1.m21;
      m22 -= m1.m22;
   }

   /**
    * Sets the value of this matrix to its transpose.
    */
   public final void transpose()
   {
      float tmp = m01;
      m01 = m10;
      m10 = tmp;

      tmp = m02;
      m02 = m20;
      m20 = tmp;

      tmp = m12;
      m12 = m21;
      m21 = tmp;

   }

   /**
    * Sets the value of this matrix to the transpose of the argument matrix
    *
    * @param m1 the matrix to be transposed
    */
   public final void transpose(Matrix3f m1)
   {
      // alias-safe
      set(m1);
      transpose();
   }

   /**
    * Sets the value of this matrix to the matrix conversion of the
    * (single precision) quaternion argument.
    *
    * @param q1 the quaternion to be converted
    */
   public final void set(Quat4f q1)
   {
      setFromQuat(q1.x, q1.y, q1.z, q1.w);
   }

   /**
    * Sets the value of this matrix to the double value of the Matrix3f
    * argument.
    *
    * @param m1 the matrix3f
    */
   public final void set(Matrix3f m1)
   {
      m00 = m1.m00;
      m01 = m1.m01;
      m02 = m1.m02;
      m10 = m1.m10;
      m11 = m1.m11;
      m12 = m1.m12;
      m20 = m1.m20;
      m21 = m1.m21;
      m22 = m1.m22;
   }

   /**
    * Sets the values in this Matrix3f equal to the row-major array parameter
    * (ie, the first four elements of the array will be copied into the first
    * row of this matrix, etc.).
    */
   public final void set(float m[])
   {
      m00 = m[0];
      m01 = m[1];
      m02 = m[2];
      m10 = m[3];
      m11 = m[4];
      m12 = m[5];
      m20 = m[6];
      m21 = m[7];
      m22 = m[8];
   }

   /**
    * Sets the value of this matrix to the matrix inverse
    * of the passed matrix m1.
    *
    * @param m1 the matrix to be inverted
    */
   public final void invert(Matrix3f m1)
   {
      set(m1);
      invert();
   }

   /**
    * Sets the value of this matrix to its inverse.
    */
   public final void invert()
   {
      double s = determinant();
      if (s == 0.0)
         return;
      s = 1 / s;
      // alias-safe way.
      set(
       m11 * m22 - m12 * m21, m02 * m21 - m01 * m22, m01 * m12 - m02 * m11,
       m12 * m20 - m10 * m22, m00 * m22 - m02 * m20, m02 * m10 - m00 * m12,
       m10 * m21 - m11 * m20, m01 * m20 - m00 * m21, m00 * m11 - m01 * m10
      );
      mul((float) s);
   }

   /**
    * Computes the determinant of this matrix.
    *
    * @return the determinant of the matrix
    */
   public final float determinant()
   {
      // less *,+,- calculation than expanded expression.
      return m00 * (m11 * m22 - m21 * m12)
       - m01 * (m10 * m22 - m20 * m12)
       + m02 * (m10 * m21 - m20 * m11);
   }

   /**
    * Sets the value of this matrix to a scale matrix with the
    * passed scale amount.
    *
    * @param scale the scale factor for the matrix
    */
   public final void set(float scale)
   {
      m00 = scale;
      m01 = 0.0f;
      m02 = 0.0f;
      m10 = 0.0f;
      m11 = scale;
      m12 = 0.0f;
      m20 = 0.0f;
      m21 = 0.0f;
      m22 = scale;
   }


   /**
    * Sets the value of this matrix to a rotation matrix about the x axis
    * by the passed angle.
    *
    * @param angle the angle to rotate about the X axis in radians
    */
   public final void rotX(float angle)
   {
      double c = Math.cos(angle);
      double s = Math.sin(angle);
      m00 = 1.0f;
      m01 = 0.0f;
      m02 = 0.0f;
      m10 = 0.0f;
      m11 = (float) c;
      m12 = (float) -s;
      m20 = 0.0f;
      m21 = (float) s;
      m22 = (float) c;
   }

   /**
    * Sets the value of this matrix to a rotation matrix about the y axis
    * by the passed angle.
    *
    * @param angle the angle to rotate about the Y axis in radians
    */
   public final void rotY(float angle)
   {
      double c = Math.cos(angle);
      double s = Math.sin(angle);
      m00 = (float) c;
      m01 = 0.0f;
      m02 = (float) s;
      m10 = 0.0f;
      m11 = 1.0f;
      m12 = 0.0f;
      m20 = (float) -s;
      m21 = 0.0f;
      m22 = (float) c;
   }

   /**
    * Sets the value of this matrix to a rotation matrix about the z axis
    * by the passed angle.
    *
    * @param angle the angle to rotate about the Z axis in radians
    */
   public final void rotZ(float angle)
   {
      double c = Math.cos(angle);
      double s = Math.sin(angle);
      m00 = (float) c;
      m01 = (float) -s;
      m02 = 0.0f;
      m10 = (float) s;
      m11 = (float) c;
      m12 = 0.0f;
      m20 = 0.0f;
      m21 = 0.0f;
      m22 = 1.0f;
   }

   /**
    * Multiplies each element of this matrix by a scalar.
    *
    * @param scalar The scalar multiplier.
    */
   public final void mul(float scalar)
   {
      m00 *= scalar;
      m01 *= scalar;
      m02 *= scalar;
      m10 *= scalar;
      m11 *= scalar;
      m12 *= scalar;
      m20 *= scalar;
      m21 *= scalar;
      m22 *= scalar;
   }

   /**
    * Multiplies each element of matrix m1 by a scalar and places the result
    * into this. Matrix m1 is not modified.
    *
    * @param scalar The scalar multiplier.
    * @param m1     The original matrix.
    */
   public final void mul(float scalar, Matrix3f m1)
   {
      set(m1);
      mul(scalar);
   }

   /**
    * Sets the value of this matrix to the result of multiplying itself
    * with matrix m1.
    *
    * @param m1 the other matrix
    */
   public final void mul(Matrix3f m1)
   {
      mul(this, m1);
   }

   /**
    * Sets the value of this matrix to the result of multiplying
    * the two argument matrices together.
    *
    * @param m1 the first matrix
    * @param m2 the second matrix
    */
   public final void mul(Matrix3f m1, Matrix3f m2)
   {
      // alias-safe way.
      set(
       m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20,
       m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21,
       m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22,

       m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20,
       m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21,
       m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22,

       m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20,
       m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21,
       m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22
      );
   }

   /**
    * Multiplies this matrix by matrix m1, does an SVD normalization of the
    * result, and places the result back into this matrix this =
    * SVDnorm(this*m1).
    *
    * @param m1 the matrix on the right hand side of the multiplication
    */
   public final void mulNormalize(Matrix3f m1)
   {
      mul(m1);
      SVD(this);
   }


   /**
    * Multiplies matrix m1 by matrix m2, does an SVD normalization of the
    * result, and places the result into this matrix this = SVDnorm(m1*m2).
    *
    * @param m1 the matrix on the left hand side of the multiplication
    * @param m2 the matrix on the right hand side of the multiplication
    */
   public final void mulNormalize(Matrix3f m1, Matrix3f m2)
   {
      mul(m1, m2);
      SVD(this);
   }

   /**
    * Multiplies the transpose of matrix m1 times the transpose of matrix m2,
    * and places the result into this.
    *
    * @param m1 The matrix on the left hand side of the multiplication
    * @param m2 The matrix on the right hand side of the multiplication
    */
   public final void mulTransposeBoth(Matrix3f m1, Matrix3f m2)
   {
      mul(m2, m1);
      transpose();
   }

   /**
    * Multiplies matrix m1 times the transpose of matrix m2, and places the
    * result into this.
    *
    * @param m1 The matrix on the left hand side of the multiplication
    * @param m2 The matrix on the right hand side of the multiplication
    */
   public final void mulTransposeRight(Matrix3f m1, Matrix3f m2)
   {
      // alias-safe way.
      set(
       m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02,
       m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12,
       m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22,

       m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02,
       m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12,
       m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22,

       m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02,
       m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12,
       m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22
      );
   }


   /**
    * Multiplies the transpose of matrix m1 times matrix m2, and places the
    * result into this.
    *
    * @param m1 The matrix on the left hand side of the multiplication
    * @param m2 The matrix on the right hand side of the multiplication
    */
   public final void mulTransposeLeft(Matrix3f m1, Matrix3f m2)
   {
      // alias-safe way.
      set(
       m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20,
       m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21,
       m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22,

       m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20,
       m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21,
       m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22,

       m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20,
       m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21,
       m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22
      );
   }

   /**
    * Performs singular value decomposition normalization of this matrix.
    */
   public final void normalize()
   {
      SVD(this);
   }

   /**
    * Perform singular value decomposition normalization of matrix m1 and
    * place the normalized values into this.
    *
    * @param m1 Provides the matrix values to be normalized
    */
   public final void normalize(Matrix3f m1)
   {
      set(m1);
      SVD(this);
   }

   /**
    * Perform cross product normalization of this matrix.
    */
   public final void normalizeCP()
   {
      // domain error may occur
      mul((float) Math.pow(determinant(), -1.0 / 3.0));
   }

   /**
    * Perform cross product normalization of matrix m1 and place the
    * normalized values into this.
    *
    * @param m1 Provides the matrix values to be normalized
    */
   public final void normalizeCP(Matrix3f m1)
   {
      set(m1);
      normalizeCP();
   }


   /**
    * Returns true if all of the data members of Matrix3f m1 are
    * equal to the corresponding data members in this Matrix3f.
    *
    * @param m1 The matrix with which the comparison is made.
    * @return true or false
    */
   public boolean equals(Matrix3f m1)
   {
      return m1 != null
       && m00 == m1.m00
       && m01 == m1.m01
       && m02 == m1.m02
       && m10 == m1.m10
       && m11 == m1.m11
       && m12 == m1.m12
       && m20 == m1.m20
       && m21 == m1.m21
       && m22 == m1.m22;
   }

   /**
    * Returns true if the Object o1 is of type Matrix3f and all of the data
    * members of t1 are equal to the corresponding data members in this
    * Matrix3f.
    *
    * @param o1 the object with which the comparison is made.
    */
   @Override
   public boolean equals(Object o1)
   {
      return o1 != null && (o1 instanceof Matrix3f) && equals((Matrix3f) o1);
   }

   /**
    * Returns true if the L-infinite distance between this matrix and matrix
    * m1 is less than or equal to the epsilon parameter, otherwise returns
    * false. The L-infinite distance is equal to MAX[i=0,1,2,3 ; j=0,1,2,3 ;
    * abs(this.m(i,j) - m1.m(i,j)]
    *
    * @param m1      The matrix to be compared to this matrix
    * @param epsilon the threshold value
    */
   public boolean epsilonEquals(Matrix3f m1, double epsilon)
   {
      return Math.abs(m00 - m1.m00) <= epsilon
       && Math.abs(m01 - m1.m01) <= epsilon
       && Math.abs(m02 - m1.m02) <= epsilon

       && Math.abs(m10 - m1.m10) <= epsilon
       && Math.abs(m11 - m1.m11) <= epsilon
       && Math.abs(m12 - m1.m12) <= epsilon

       && Math.abs(m20 - m1.m20) <= epsilon
       && Math.abs(m21 - m1.m21) <= epsilon
       && Math.abs(m22 - m1.m22) <= epsilon;
   }

   /**
    * Returns a hash number based on the data values in this
    * object.  Two different Matrix3f objects with identical data values
    * (ie, returns true for equals(Matrix3f) ) will return the same hash
    * number.  Two objects with different data members may return the
    * same hash value, although this is not likely.
    *
    * @return the integer hash value
    */
   @Override
   public int hashCode()
   {
      return Float.floatToIntBits(m00) ^
       Float.floatToIntBits(m01) ^
       Float.floatToIntBits(m02) ^
       Float.floatToIntBits(m10) ^
       Float.floatToIntBits(m11) ^
       Float.floatToIntBits(m12) ^
       Float.floatToIntBits(m20) ^
       Float.floatToIntBits(m21) ^
       Float.floatToIntBits(m22);
   }

   /**
    * Sets this matrix to all zeros.
    */
   public final void setZero()
   {
      m00 = 0.0f;
      m01 = 0.0f;
      m02 = 0.0f;
      m10 = 0.0f;
      m11 = 0.0f;
      m12 = 0.0f;
      m20 = 0.0f;
      m21 = 0.0f;
      m22 = 0.0f;
   }

   /**
    * Negates the value of this matrix: this = -this.
    */
   public final void negate()
   {
      m00 = -m00;
      m01 = -m01;
      m02 = -m02;
      m10 = -m10;
      m11 = -m11;
      m12 = -m12;
      m20 = -m20;
      m21 = -m21;
      m22 = -m22;
   }

   /**
    * Sets the value of this matrix equal to the negation of of the Matrix3f
    * parameter.
    *
    * @param m1 The source matrix
    */
   public final void negate(Matrix3f m1)
   {
      set(m1);
      negate();
   }

   /**
    * Transforms the input tuple with this Matrix3f and places the result also into input.
    *
    * @param inputOutput    the input tuple to be transformed; result is stored in input.
    */
   public final void transform(Tuple3f inputOutput)
   {
      inputOutput.set(
       m00 * inputOutput.x + m01 * inputOutput.y + m02 * inputOutput.z,
       m10 * inputOutput.x + m11 * inputOutput.y + m12 * inputOutput.z,
       m20 * inputOutput.x + m21 * inputOutput.y + m22 * inputOutput.z
      );
   }

   /**
    * Transforms the input tuple with this Matrix3f and places the result into output. Input must not be the same
    * object as output.
    *
    * @param input    the input tuple to be transformed.
    * @param output   the transformed tuple
    */
   public final void transform(Tuple3f input, Tuple3f output)
   {
      output.x = m00 * input.x + m01 * input.y + m02 * input.z;
      output.y = m10 * input.x + m11 * input.y + m12 * input.z;
      output.z = m20 * input.x + m21 * input.y + m22 * input.z;
   }

   /**
    * Sets 9 values
    */
   private void set(float m00, float m01, float m02,
    float m10, float m11, float m12,
    float m20, float m21, float m22)
   {
      this.m00 = m00;
      this.m01 = m01;
      this.m02 = m02;
      this.m10 = m10;
      this.m11 = m11;
      this.m12 = m12;
      this.m20 = m20;
      this.m21 = m21;
      this.m22 = m22;
   }

   /**
    * Performs SVD on this matrix and gets scale and rotation.
    * Rotation is placed into rot.
    *
    * @param rot the rotation factor.
    * @return scale factor
    */
   private float SVD(Matrix3f rot)
   {
      // this is a simple svd.
      // Not complete but fast and reasonable.

      /*
       * SVD scale factors(squared) are the 3 roots of
       *
       *     | xI - M*MT | = 0.
       *
       * This will be expanded as follows
       *
       * x^3 - A x^2 + B x - C = 0
       *
       * where A, B, C can be denoted by 3 roots x0, x1, x2.
       *
       * A = (x0+x1+x2), B = (x0x1+x1x2+x2x0), C = x0x1x2.
       *
       * An average of x0,x1,x2 is needed here. C^(1/3) is a cross product
       * normalization factor.
       * So here, I use A/3. Note that x should be sqrt'ed for the
       * actual factor.
       */

      float s = (float) Math.sqrt(
       (
        m00 * m00 + m10 * m10 + m20 * m20 +
         m01 * m01 + m11 * m11 + m21 * m21 +
         m02 * m02 + m12 * m12 + m22 * m22
       ) / 3.0
      );

      // zero-div may occur.
      float t = (s == 0.0f ? 0.0f : 1.0f / s);

      if (rot != null)
      {
         if (rot != this)
            rot.set(this);
         rot.mul(t);
      }

      return s;
   }

   private void setFromQuat(float x, float y, float z, float w)
   {
      float n = x * x + y * y + z * z + w * w;
      float s = (n > 0.0f) ? (2.0f / n) : 0.0f;

      float xs = x * s, ys = y * s, zs = z * s;
      float wx = w * xs, wy = w * ys, wz = w * zs;
      float xx = x * xs, xy = x * ys, xz = x * zs;
      float yy = y * ys, yz = y * zs, zz = z * zs;

      m00 = 1.0f - (yy + zz);
      m01 = xy - wz;
      m02 = xz + wy;
      m10 = xy + wz;
      m11 = 1.0f - (xx + zz);
      m12 = yz - wx;
      m20 = xz - wy;
      m21 = yz + wx;
      m22 = 1.0f - (xx + yy);
   }

   private void setFromAxisAngle(float x, float y, float z, float angle)
   {
      // Taken from Rick's which is taken from Wertz. pg. 412
      // Bug Fixed and changed into right-handed by hiranabe
      float n = (float)Math.sqrt(x * x + y * y + z * z);
      // zero-div may occur
      n = 1 / n;
      x *= n;
      y *= n;
      z *= n;
      float c = (float)Math.cos(angle);
      float s = (float)Math.sin(angle);
      float omc = 1.0f - c;
      m00 = c + x * x * omc;
      m11 = c + y * y * omc;
      m22 = c + z * z * omc;

      float tmp1 = x * y * omc;
      float tmp2 = z * s;
      m01 = tmp1 - tmp2;
      m10 = tmp1 + tmp2;

      tmp1 = x * z * omc;
      tmp2 = y * s;
      m02 = tmp1 + tmp2;
      m20 = tmp1 - tmp2;

      tmp1 = y * z * omc;
      tmp2 = x * s;
      m12 = tmp1 - tmp2;
      m21 = tmp1 + tmp2;
   }
}
