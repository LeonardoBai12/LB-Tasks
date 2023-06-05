package io.lb.lbtasks.feature_task.presentation.listing

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.lbtasks.core.util.filterBy
import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.domain.use_cases.TaskUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCases: TaskUseCases
) : ViewModel() {
    private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val tasks = mutableListOf<Task>()
    private var searchJob: Job? = null
    private var getTasksJob: Job? = null

    private var recentlyDeletedTask: Task? = null

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }

    init {
        getTasks()
    }

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
                    _state.value = state.value.copy(
                        tasks = tasks.filterBy(event.filter),
                    )
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
                    insertTask(it)
                }
            }
        }
    }

    private fun getTasks() {
        getTasksJob?.cancel()
        getTasksJob = useCases.getTasksUseCase().onEach { result ->
            _state.value = state.value.copy(
                tasks = result,
            )
        }.launchIn(viewModelScope)
    }

    private fun insertTask(task: Task) {
        viewModelScope.launch {
            with(task) {
                useCases.insertTaskUseCase(
                    title = title,
                    description = description ?: "",
                    taskType = taskType,
                    deadlineDate = deadlineDate ?: "",
                    deadlineTime = deadlineTime ?: ""
                )
            }
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch {
            useCases.deleteTaskUseCase(task)
        }
    }
}
