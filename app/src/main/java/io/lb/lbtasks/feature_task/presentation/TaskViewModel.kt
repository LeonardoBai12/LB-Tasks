package io.lb.lbtasks.feature_task.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.lbtasks.core.util.Resource
import io.lb.lbtasks.core.util.filterBy
import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.domain.use_cases.TaskUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }

    init {
        getTasks()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.SearchedForMeal -> {
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
                deleteTask(event.task)
            }
            is TaskEvent.RequestInsert -> {
                insertTask(
                    title = event.title,
                    description = event.description,
                    taskType = event.taskType,
                    deadlineDate = event.deadlineDate,
                    deadlineTime = event.deadlineTime
                )
            }
            is TaskEvent.RequestUpdate -> {
                updateTask(event.task)
            }
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            useCases.getTasksUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            tasks.addAll(it)

                            _state.value = state.value.copy(
                                tasks = it,
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            tasks = emptyList(),
                        )

                        result.message?.let {
                            _eventFlow.emit(UiEvent.ShowToast(it))
                        }
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            loading = result.isLoading,
                        )
                    }
                }
            }
        }
    }

    private fun insertTask(
        title: String,
        description: String = "",
        taskType: String,
        deadlineDate: String,
        deadlineTime: String
    ) {
        viewModelScope.launch {
            useCases.insertTaskUseCase(
                title = title,
                description = description,
                taskType = taskType,
                deadlineDate = deadlineDate,
                deadlineTime = deadlineTime,
            )
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            useCases.updateTaskUseCase(task)
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch {
            useCases.deleteTaskUseCase(task)
        }
    }
}
