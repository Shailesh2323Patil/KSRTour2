@file:OptIn(ExperimentalMaterial3Api::class)

package com.kesaritours.task2.presentation.studentsInfo

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kesaritours.task2.R
import com.kesaritours.task2.domain.model.Student

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudentInfoScreen(
    onNavigationListener: () -> Unit,
    studentList: List<Student>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically),
                            text = LocalContext.current.getString(R.string.student_list),
                            textAlign = TextAlign.Start
                        )

                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                                .clickable { onNavigationListener() }
                                .padding(end = 10.dp),
                            text = LocalContext.current.getString(R.string.add_student),
                            textAlign = TextAlign.End,
                            color = Color.Red,
                            fontSize = 15.sp
                        )
                    }
                }
            )
        } ,
        floatingActionButton = { FloatingButton(onNavigationListener) }
    ) { innerPadding ->
        StudentList(
            modifier = Modifier.padding(innerPadding) ,
            studentList = studentList
        )
    }
}

@Composable
fun StudentList(
    modifier: Modifier,
    studentList: List<Student>
) {
    var context = LocalContext.current

    if(studentList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(studentList) { student ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 2.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = ("${context.getString(R.string.student_name)} : ${student.name}"),
                                textAlign = TextAlign.Start,
                                color = Color.White,
                                fontSize = 17.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = ("${context.getString(R.string.selected_course)} : ${student.course}"),
                                textAlign = TextAlign.Start,
                                color = Color.White,
                                fontSize = 17.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            for(subject in student.subjects) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = ("${subject.subject} : ${subject.marks} / 100"),
                                    textAlign = TextAlign.Start,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontStyle = FontStyle.Normal
                                )
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = ("${context.getString(R.string.total_marks)} : ${student.totalMarks} / 300"),
                                textAlign = TextAlign.Start,
                                color = Color.White,
                                fontSize = 17.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = ("${context.getString(R.string.average_marks)} : ${student.averageMarks}"),
                                textAlign = TextAlign.Start,
                                color = Color.White,
                                fontSize = 17.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
    else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = LocalContext.current.getString(R.string.no_data_found_please_add_student),
                textAlign = TextAlign.Center,
                color = Color.Green,
                fontSize = 17.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FloatingButton(
    onNavigationListener: () -> Unit
) {
    FloatingActionButton(
        onClick = { onNavigationListener() }
    ) {
        Icon(Icons.Default.Add, contentDescription = "add")
    }
}
