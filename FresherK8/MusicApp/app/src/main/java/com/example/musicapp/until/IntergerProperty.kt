package com.example.musicapp.until

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class IntergerProperty(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    defaultValue: Int,
) : ReadWriteProperty<Any, Int> {

    private var value: Int = sharedPreferences.getInt(key, defaultValue)

    override fun getValue(thisRef: Any, property: KProperty<*>): Int = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
        this.value = value
    }

    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Int = value

    operator fun setValue(nothing: Nothing?, property: KProperty<*>, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
        this.value = value
    }
}

