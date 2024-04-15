package com.example.musicapp.until

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanProperty(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    defaultValue: Boolean,
) : ReadWriteProperty<Any, Boolean> {

    private var value = sharedPreferences.getBoolean(key, defaultValue)

    override fun getValue(thisRef: Any, property: KProperty<*>) = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
        this.value = value
    }
}
