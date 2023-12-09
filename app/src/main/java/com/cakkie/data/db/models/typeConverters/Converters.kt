package com.smcdao.peniwallet.core.data.db.models.typeConverters

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Date


class Converters {
    // BigDecimal converter
    @TypeConverter
    fun bigDecimalToString(value: BigDecimal): String {
        return value.toPlainString()
    }

    @TypeConverter
    fun stringToBigDecimal(value: String): BigDecimal {
        return BigDecimal(value)
    }

    // BigInteger converter
    @TypeConverter
    fun bigIntegerToString(value: BigInteger): String {
        return value.toString()
    }

    @TypeConverter
    fun stringToBigInteger(value: String): BigInteger {
        return BigInteger(value)
    }

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    //list of string converter
    @TypeConverter
    fun stringListToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun stringToStringList(string: String): List<String> {
        return string.split(",")
    }
}