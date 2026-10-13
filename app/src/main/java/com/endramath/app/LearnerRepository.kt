package com.endramath.app

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/** Versioned, app-private local storage. Writes are synchronous before navigation. */
class LearnerRepository(context: Context) {
    private val prefs = context.getSharedPreferences("endramath_local_v1", Context.MODE_PRIVATE)
    var storageError: Boolean = false
        private set
    fun learners(): List<Learner> = try {
        val array = JSONObject(prefs.getString("profiles", "{\"version\":1,\"learners\":[]}")!!).getJSONArray("learners")
        (0 until array.length()).map { i ->
            val o = array.getJSONObject(i)
            Learner(o.getString("id"), o.getString("name"), o.getInt("age"), o.getString("grade"),
                Companion.entries.firstOrNull { it.name == o.optString("companion") } ?: Companion.NONE)
        }
    } catch (_: Exception) { storageError = true; emptyList() }
    fun save(learner: Learner): Boolean {
        val list = learners().toMutableList()
        if (storageError) return false // Never overwrite unreadable existing profiles.
        val index = list.indexOfFirst { it.id == learner.id }
        if (index < 0) list.add(learner) else list[index] = learner
        val array = JSONArray()
        list.forEach { array.put(JSONObject().put("id", it.id).put("name", it.name).put("age", it.age).put("grade", it.grade).put("companion", it.companion.name)) }
        return prefs.edit().putString("profiles", JSONObject().put("version", 1).put("learners", array).toString()).putString("selected", learner.id).commit()
    }
    fun selected(): Learner? = learners().firstOrNull { it.id == prefs.getString("selected", null) } ?: learners().firstOrNull()
    fun select(id: String): Boolean = learners().any { it.id == id } && prefs.edit().putString("selected", id).commit()
    var largeText: Boolean
        get() = prefs.getBoolean("large_text", false)
        set(value) { check(prefs.edit().putBoolean("large_text", value).commit()) }
}
