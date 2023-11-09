package io.lb.lbtasks.task.presentation.listing

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.core.util.filterBy
import io.lb.lbtasks.sign_in.domain.model.UserData
import io.lb.lbtasks.task.domain.model.Task
import io.lb.lbtasks.task.domain.use_cases.TaskUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCases: TaskUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(TaskState())
    val state: StateFlow<TaskState> = _state

    private val tasks = mutableListOf<Task>()
    private var searchJob: Job? = null
    private var getTasksJob: Job? = null

    private var recentlyDeletedTask: Task? = null
    var userData: UserData? = null

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.SearchedForTask -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    event.filter.takeIf {
                        it.isNotEmpty()
                    }?.let {
                        delay(300L)
                    }
                    _state.update {
                        state.value.copy(
                            tasks = tasks.filterBy(event.filter),
                        )
                    }
                }
            }
            is TaskEvent.RequestDelete -> {
                with(event.task) {
                    deleteTask(this)
                    recentlyDeletedTask = this
                }
            }
            is TaskEvent.RestoreTask -> {
                recentlyDeletedTask?.let {
                    restoreTask(it)
                }
            }
        }
    }

    fun getTasks(userData: UserData) {
        getTasksJob?.cancel()
        getTasksJob = useCases.getTasksUseCase(userData).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    tasks.clear()

                    result.data?.let { tasks ->
                        this.tasks.addAll(tasks)

                        _state.update {
                            it.copy(
                                tasks = tasks,
                            )
                        }
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            loading = result.isLoading,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun restoreTask(task: Task) {
        viewModelScope.launch {
            with(task) {
                userData?.let {
                    useCases.insertTaskUseCase(
                        userData = it,
                        title = title,
                        description = description ?: "",
                        taskType = taskType,
                        deadlineDate = deadlineDate ?: "",
                        deadlineTime = deadlineTime ?: ""
                    )
                    getTasks(it)
                }
            }
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch {
            userData?.let {
                useCases.deleteTaskUseCase(it, task)
                getTasks(it)
            }
        }
    }

    fun clear() {
        _state.update { TaskState() }
    }
}
