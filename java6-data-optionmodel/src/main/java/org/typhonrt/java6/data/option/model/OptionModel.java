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
package org.typhonrt.java6.data.option.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * OptionModel
 */
public class OptionModel implements IOptionModel
{
   private String          title;
   private AtomicInteger   intValue;
   private OptionType      optionType;

   private float           minValue, maxValue;

   public OptionModel(String title, boolean value)
   {
      this.title = title;
      intValue = new AtomicInteger();
      optionType = OptionType.BOOLEAN;

      minValue = 0;
      maxValue = 1;

      setRangePercent(value ? 1f : 0f);
   }

   public OptionModel(String title, float percent)
   {
      this(title, percent, 0, 1);
   }

   public OptionModel(String title, float percent, float minValue, float maxValue)
   {
      this.title = title;
      this.minValue = minValue;
      this.maxValue = maxValue;

      intValue = new AtomicInteger();
      optionType = OptionType.FLOAT;

      setRangePercent(percent);
   }

   @Override
   public float getFloatValue()
   {
      return minValue + (getRangePercent() * (maxValue - minValue));
   }

   @Override
   public OptionType getOptionType()
   {
      return optionType;
   }

   @Override
   public boolean getBooleanValue()
   {
      return getRangePercent() >= 0.5f;
   }

   @Override
   public float getRangePercent()
   {
      return Float.intBitsToFloat(intValue.get());
   }

   @Override
   public String getTitle()
   {
      return title;
   }

   @Override
   public void setRangePercent(float value)
   {
      intValue.lazySet(Float.floatToIntBits(Math.max(0, Math.min(value, 1))));
   }
}
