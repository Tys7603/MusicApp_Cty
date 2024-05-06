package com.example.musicapp.shared.utils

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanProperty(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    defaultValue: Boolean,
) : ReadWriteProperty<Any, Boolean> {

    var value: Boolean = sharedPreferences.getBoolean(key, defaultValue)

    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
        this.value = value
    }

    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Boolean = value

    operator fun setValue(nothing: Nothing?, property: KProperty<*>, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
        this.value = value
    }
}

