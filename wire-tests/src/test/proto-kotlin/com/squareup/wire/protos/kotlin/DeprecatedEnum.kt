// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: deprecated_enum.proto
package com.squareup.wire.protos.kotlin

import com.squareup.wire.EnumAdapter
import com.squareup.wire.ProtoAdapter
import com.squareup.wire.WireEnum
import kotlin.Deprecated
import kotlin.Int
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

enum class DeprecatedEnum(private val value: Int) : WireEnum {
  @Deprecated(message = "DISABLED is deprecated")
  DISABLED(1),

  @Deprecated(message = "ENABLED is deprecated")
  ENABLED(2),

  ON(3),

  OFF(4);

  override fun getValue(): Int = value

  companion object {
    @JvmField
    val ADAPTER: ProtoAdapter<DeprecatedEnum> = object : EnumAdapter<DeprecatedEnum>(
      DeprecatedEnum::class.java
    ) {
      override fun fromValue(value: Int): DeprecatedEnum = DeprecatedEnum.fromValue(value)
    }

    @JvmStatic
    fun fromValue(value: Int): DeprecatedEnum = when (value) {
      1 -> DISABLED
      2 -> ENABLED
      3 -> ON
      4 -> OFF
      else -> throw IllegalArgumentException("""Unexpected value: $value""")
    }
  }
}
