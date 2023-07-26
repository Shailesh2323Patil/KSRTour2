package com.kesaritours.task2.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kesaritours.task2.domain.model.Subject
import com.kesaritours.task2.data.local.entity.StudentEntity
import com.kesaritours.task2.domain.usecase.AddStudentInfoUseCase
import com.kesaritours.task2.domain.usecase.FetchStudentInfoUseCase
import com.kesaritours.task2.presentation.studentDetail.MutableStudent
import com.kesaritours.task2.presentation.studentsInfo.StudentInfoState
import com.kesaritours.task2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsInfoViewModel @Inject constructor(
    private val addStudentInfoUseCase: AddStudentInfoUseCase,
    private val fetchStudentInfoUseCase: FetchStudentInfoUseCase
) : ViewModel(){

    private val _studentInfoFlow = mutableStateOf(StudentInfoState())
    val studentInfoState : State<StudentInfoState> = _studentInfoFlow

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var studentName by mutableStateOf(String())
    var courseName by mutableStateOf(String())
    var mutableStudentList = mutableListOf(MutableStudent())

    private var saveJob: Job? = null
    private var fetchStudentInfoJob: Job? = null

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }

    fun insertStudentInfo() {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            var listSubject : MutableList<Subject> = mutableListOf<Subject>()

            var totalNumber = 0
            var averageNumber = 0

            mutableStudentList.map {
                listSubject.add(Subject(subject = it.subject.value, marks = it.marks.value))

                totalNumber += it.marks.value
                averageNumber += it.marks.value
            }

            averageNumber /= 3

            var studentEntity = StudentEntity(
                name = studentName,
                course = courseName,
                subjects = listSubject,
                totalMarks = totalNumber ,
                averageMarks = averageNumber
            )

            addStudentInfoUseCase.studentEntity = studentEntity

            addStudentInfoUseCase()
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _studentInfoFlow.value = _studentInfoFlow.value.copy(
                                studentInfoItem = result.data ?: emptyList() ,
                                isLoading = false ,
                                addStudent = false
                            )
                        }
                        is Resource.Error -> {
                            _studentInfoFlow.value = _studentInfoFlow.value.copy(
                                studentInfoItem = emptyList() ,
                                isLoading = false ,
                                addStudent = false
                            )
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    result.message ?: "Something Went Wrong"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _studentInfoFlow.value = _studentInfoFlow.value.copy(
                                studentInfoItem = emptyList() ,
                                isLoading = true ,
                                addStudent = false
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun fetchStudentInfo() {
        fetchStudentInfoJob?.cancel()
        fetchStudentInfoJob = viewModelScope.launch {
            fetchStudentInfoUseCase()
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _studentInfoFlow.value = _studentInfoFlow.value.copy(
                                studentInfoItem = result.data ?: emptyList() ,
                                isLoading = false ,
                                addStudent = false
                            )
                        }
                        is Resource.Error -> {
                            _studentInfoFlow.value = _studentInfoFlow.value.copy(
                                studentInfoItem = result.data ?: emptyList() ,
                                isLoading = false ,
                                addStudent = false
                            )
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    result.message ?: "Something Went Wrong"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _studentInfoFlow.value = _studentInfoFlow.value.copy(
                                studentInfoItem = emptyList() ,
                                isLoading = true ,
                                addStudent = false
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun redirectAddStudent() {
        _studentInfoFlow.value = _studentInfoFlow.value.copy(
            studentInfoItem = emptyList() ,
            isLoading = false ,
            addStudent = true
        )
    }
}