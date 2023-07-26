@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.kesaritours.task2.presentation.studentDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.kesaritours.task2.R
import com.kesaritours.task2.domain.model.Subject
import com.kesaritours.task2.presentation.viewModel.StudentsInfoViewModel
import com.kesaritours.task2.util.Constants
import java.lang.Exception

private const val TAG = "StudentDetailScreen"
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudentDetailScreen(
    studentsInfoViewModel: StudentsInfoViewModel,
    saveData : () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = LocalContext.current.getString(R.string.student_information)) }
            )
        }
    ) {
        var selectedSubjectList by remember { mutableStateOf(listOf<Subject>()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 10.dp, end = 10.dp),

            horizontalAlignment = Alignment.Start
        ) {
            showNameEditableTexts(studentsInfoViewModel = studentsInfoViewModel)

            Spacer(modifier = Modifier.height(10.dp))

            showSubjectsDropDown(
                studentsInfoViewModel = studentsInfoViewModel ,
                onSelectedCourse = { subjectList ->
                    selectedSubjectList = subjectList
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            if(selectedSubjectList.isNotEmpty()) {
                showSubject(
                    selectedCourse = selectedSubjectList,
                    studentsInfoViewModel = studentsInfoViewModel
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    onClick = {
                        saveData()
                    }
                ) {
                    Text(text = LocalContext.current.getString(R.string.save))
                }
            }
        }
    }
}

@Composable
fun showNameEditableTexts(
    studentsInfoViewModel: StudentsInfoViewModel
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = studentsInfoViewModel.studentName,
        onValueChange = {
            studentsInfoViewModel.studentName = it
        },
        label = {
            Text(text = LocalContext.current.getString(R.string.student_name), color = Color.Black)
        },
        singleLine = true
    )
}

@Composable
fun showSubjectsDropDown(
    studentsInfoViewModel: StudentsInfoViewModel,
    onSelectedCourse: (List<Subject>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var texFilledSize by remember {
        mutableStateOf(Size.Zero)
    }

    val icon = if (expanded) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = studentsInfoViewModel.courseName,
            enabled = false,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    texFilledSize = it.size.toSize()
                },
            label = { Text(text = LocalContext.current.getString(R.string.selected_course)) },
            trailingIcon = {
                Icon(
                    imageVector = icon, contentDescription = "",
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Constants.listCourse.forEach { course ->
                DropdownMenuItem(
                    onClick = {
                        studentsInfoViewModel.courseName = course.course
                        onSelectedCourse(course.subjectList)
                        expanded = false
                    },
                    text = {
                        Text(text = course.course, color = Color.Black)
                    }
                )
            }
        }
    }
}

@Composable
fun showSubject(
    selectedCourse: List<Subject> ,
    studentsInfoViewModel: StudentsInfoViewModel
) {
    var mutableStudentList = studentsInfoViewModel.mutableStudentList

    mutableStudentList.clear()

    selectedCourse.map {
        mutableStudentList.add(MutableStudent(mutableStateOf(it.subject) , mutableStateOf(it.marks) ))
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(mutableStudentList) { item ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.subject.value
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = ":")

                    Spacer(modifier = Modifier.width(10.dp))

                    TextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f),
                        value = item.marks.value.toString(),
                        onValueChange = {
                            try {
                                if(it.toInt() <= 100) {
                                    item.marks.value = it.toInt()
                                }
                            }
                            catch (exception : Exception) {
                                Log.e(TAG, exception.message.toString())
                            }
                        },
                        label = {
                            Text(text = LocalContext.current.getString(R.string.marks), color = Color.Black)
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = "/")

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = "100")
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

data class MutableStudent (
    var subject : MutableState<String> = mutableStateOf(String()) ,
    var marks : MutableState<Int> = mutableStateOf(0)
)