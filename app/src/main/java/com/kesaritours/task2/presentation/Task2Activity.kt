package com.kesaritours.task2.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kesaritours.task2.presentation.studentDetail.StudentDetailScreen
import com.kesaritours.task2.presentation.studentsInfo.StudentInfoScreen
import com.kesaritours.task2.presentation.viewModel.StudentsInfoViewModel
import com.kesaritours.task2.ui.theme.KesriTravelsTask2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Task2Activity : ComponentActivity() {

    private val viewModel : StudentsInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)
        setContent {
            KesriTravelsTask2Theme {
                val state = viewModel.studentInfoState.value
                val snackBarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                LaunchedEffect(key1 = true, block = {
                    viewModel.eventFlow.collectLatest { event ->
                        when(event) {
                            is StudentsInfoViewModel.UiEvent.ShowSnackBar -> {
                                scope.launch {
                                    snackBarHostState.showSnackbar(message = event.message)
                                }
                            }
                        }
                    }
                })

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Box(
                        modifier = Modifier.background(Color.White)
                    ) {
                        StudentInfoScreen(
                            studentList = state.studentInfoItem,
                            onNavigationListener = {
                                viewModel.redirectAddStudent()
                            }
                        )

                        if(state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        if(state.addStudent) {
                            StudentDetailScreen(
                                studentsInfoViewModel = viewModel,
                                saveData = {
                                    viewModel.insertStudentInfo()
                                }
                            )
                        }
                    }
                }
            }
        }

        viewModel.fetchStudentInfo()
    }
}

