package com.example.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    // MutableStateList automatycznie powiadamia Compose o zmianach
    private val _todoList = mutableStateListOf<TodoItemStruct>()
    val todoList: List<TodoItemStruct> get() = _todoList

    init {
        // Wczytanie zapisanych zadań przy starcie
        _todoList.addAll(TodoManager.todoList)
    }

    fun addTodoItem(text: String) {
        if (text.isBlank()) return

        TodoManager.addTodoItem(text)
        // synchronizacja z TodoManager
        _todoList.clear()
        _todoList.addAll(TodoManager.todoList)
    }

    fun removeTodoItem(id: Int) {
        TodoManager.removeTodoItem(id)
        // synchronizacja z TodoManager
        _todoList.clear()
        _todoList.addAll(TodoManager.todoList)
    }
}
