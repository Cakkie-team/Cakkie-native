package com.cakkie.data.db.models.typeConverters

import androidx.room.TypeConverter
import com.cakkie.networkModels.Comment
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Date


class Converters {
    var gson: Gson = Gson()

    @TypeConverter
    fun fromCommentList(comments: List<Comment>): String {
        return gson.toJson(comments)
    }

    @TypeConverter
    fun toCommentList(json: String): List<Comment> {
        val listType: Type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(json, listType)
    }

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

    @TypeConverter
    fun intListToString(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun stringToIntList(string: String): List<Int> {
        return string.split(",").map { it.toInt() }
    }
}