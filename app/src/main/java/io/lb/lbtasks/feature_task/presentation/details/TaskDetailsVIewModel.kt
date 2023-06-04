package io.lb.lbtasks.feature_task.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.lbtasks.core.util.TASK
import io.lb.lbtasks.feature_task.domain.model.Task
import io.lb.lbtasks.feature_task.domain.use_cases.TaskUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TaskDetailsVIewModel @Inject constructor(
    private val useCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(TaskDetailsState())
    val state: State<TaskDetailsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var task: Task? = null

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
        object Back: UiEvent()
    }

    init {
        val savedJson: String? = savedStateHandle[TASK]

        savedJson?.let {
            task = Task.fromJson(it)
        }
    }

    fun onEvent(event: TaskDetailsEvent) {
        when (event) {
            is TaskDetailsEvent.RequestInsert -> {
                insertTask(
                    title = event.title,
                    description = event.description,
                    date = event.date,
                    time = event.time,
                )
            }
            is TaskDetailsEvent.RequestUpdate -> {
                updateTask(
                    title = event.title,
                    description = event.description,
                    date = event.date,
                    time = event.time,
                )
            }
            is TaskDetailsEvent.RequestDelete -> {
                deleteTask()
            }
        }
    }

    private fun insertTask(
        title: String,
        description: String,
        date: String,
        time: String
    ) {
        viewModelScope.launch {
            try {
                task ?: return@launch

                useCases.insertTaskUseCase(
                    title = title,
                    description = description,
                    taskType = task!!.taskType,
                    deadlineDate = date,
                    deadlineTime = time,
                )

                _eventFlow.emit(UiEvent.Back)
            } catch (e: Exception) {
                e.message?.let {
                    _eventFlow.emit(UiEvent.ShowToast(it))
                }
            }
        }
    }

    private fun updateTask(
        title: String,
        description: String,
        date: String,
        time: String
    ) {
        viewModelScope.launch {
            try {
                task ?: return@launch

                useCases.updateTaskUseCase(
                    task = task!!,
                    title = title,
                    description = description,
                    deadlineDate = date,
                    deadlineTime = time,
                )

                _eventFlow.emit(UiEvent.Back)
            } catch (e: Exception) {
                e.message?.let {
                    _eventFlow.emit(UiEvent.ShowToast(it))
                }
            }
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            task ?: return@launch
            useCases.deleteTaskUseCase(task!!)
        }
    }
}
