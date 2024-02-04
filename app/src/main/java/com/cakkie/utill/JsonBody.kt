package com.cakkie.utill

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException


//object JsonBody {
//    fun generate(value: List<Pair<String, Any?>>): String {
//        val newline = System.getProperty("line.separator")
//        return value.associate {
//            "\"${it.first}\"" to if (it.second is String) {
//                if ((it.second as String).contains('{')) it.second else "\"${
//                    newline?.let { it1 ->
//                        (it.second as String).replace(
//                            it1, "\\n"
//                        )
//                    }
//                }\""
//            } else it.second
//
//        }.filter { it.value != null }
//            .toString().replace("=", ":")
//            .trimIndent()
//    }
//}

object JsonBody {
    fun generate(value: List<Pair<String, Any?>>): String {
        val newline = System.getProperty("line.separator")

        return value
            .filter { it.second != null }
            .map { entry ->
                "\"${entry.first}\"" to when (val value = entry.second) {
                    is String -> {
                        if (value.contains('{')) {
                            value
                        } else {
                            "\"${newline?.let { it1 -> value.replace(it1, "\\n") }}\""
                        }
                    }

                    is List<*> -> {
                        val listString = value.filterNotNull().joinToString(",", "[", "]") {
                            when (it) {
                                is String -> "\"$it\""
                                else -> it.toString()
                            }
                        }
                        listString
                    }

                    else -> value.toString()
                }
            }
            .joinToString(",", "{", "}") { it.first + ":" + it.second }
    }
}

//fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
//    when (val value = this[it]) {
//        is JSONArray -> {
//            val map = (0 until value.length()).associate {
//                Pair(it.toString(), value[it])
//            }
//            JSONObject(map).toMap().values.toList()
//        }
//
//        is JSONObject -> value.toMap()
//        JSONObject.NULL -> null
//        else -> value
//    }
//}
//
//fun String.toJson(): Map<String, *> {
//    val json = JSONObject(this)
//    return json.toMap()
//}
//
//fun String.toJsonArray(): List<Map<String, *>> {
//    val json = JSONArray(this)
//    val list = mutableListOf<Map<String, *>>()
//    for (i in 0 until json.length()) {
//        list.add(json.getJSONObject(i).toMap())
//    }
//    return list
//}
//
//fun String.toJsonObject(): Map<String, *> {
//    val json = JSONObject(this)
//    return json.toMap()
//}
//
//fun String.toJsonObjectList(): List<Map<String, *>> {
//    val json = JSONObject(this)
//    val list = mutableListOf<Map<String, *>>()
//    for (i in 0 until json.length()) {
//        list.add(json.getJSONObject(i.toString()).toMap())
//    }
//    return list
//}

//json to object
val gson = Gson()
fun <T> String.toObject(clazz: Class<T>): T {
    return gson.fromJson(this, clazz)
}

fun <T> String.toObjectList(clazz: Class<T>): List<T> {
    val list = mutableListOf<T>()
    try {
        val json = JSONArray(this)
        for (i in 0 until json.length()) {
            list.add(gson.fromJson(json.getJSONObject(i).toString(), clazz))
        }
    } catch (e: JSONException) {
        e.printStackTrace()
        // Handle the JSONException, log it, or throw a custom exception if needed.
    }
    return list
}

//to json string
fun Any.toJson(): String {
    return gson.toJson(this)
}