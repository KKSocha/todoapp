package com.example.todoapp

import java.time.Instant
import java.util.Date
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.compose.runtime.mutableStateListOf

object TodoManager {
    private val _todoList = mutableStateListOf<TodoItemStruct>()
    val todoList: List<TodoItemStruct> get() = _todoList
    private var _id: Int = 0;

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        prefs = context.getSharedPreferences(
            "todo_prefs",
            Context.MODE_PRIVATE
        )

        load()
    }

    fun addTodoItem(content: String)
    {
        _id += 1

        _todoList.add(
            TodoItemStruct(
                id = _id,
                text = content,
                date = Date.from(Instant.now())
            )
        )

        save()
    }

    fun removeTodoItem(id: Int)
    {
        _todoList.removeIf { it.id == id }

        save()
    }

    private fun save()
    {
        val json = gson.toJson(_todoList)
        prefs.edit().putString("todos", json).apply()
    }

    private fun load()
    {
        val json = prefs.getString("todos", null)
        if (!json.isNullOrEmpty())
        {
            val type = object : TypeToken<List<TodoItemStruct>>() {}.type
            val list: List<TodoItemStruct> = gson.fromJson(json, type)
            _todoList.clear()
            _todoList.addAll(list)
            _id = _todoList.maxOfOrNull { it.id } ?: 0
        }
    }
}