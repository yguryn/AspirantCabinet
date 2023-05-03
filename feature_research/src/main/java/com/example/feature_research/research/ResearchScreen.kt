package com.example.feature_research.research

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.core.model.Article
import com.example.core.model.Research

@Composable
fun ResearchScreen(
    navController: NavController,
    researchViewModel: ResearchViewModel
) {
    val listOfResearch = researchViewModel.listOfResearch.collectAsStateWithLifecycle()
    val listOfResearchHere: List<Research> = listOfResearch.value
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.verticalScroll(state = scrollState)
        ) {
            ThesisCard(listOfResearch.value)
            ArticleCard(listOfResearch.value) {
                Log.d("TTT", "${listOfResearchHere[0].listOfArticles}")
                listOfResearchHere[0].listOfArticles.add(it)
                Log.d("TTT", "${listOfResearchHere[0].listOfArticles}")
                researchViewModel.updateResearch(listOfResearchHere[0])
            }
            ArticleCard(listOfResearch.value) {

            }
        }
    }
}

@Composable
fun ArticleCard(listOfResearch: List<Research>, articleUploadListener: (Article) -> Unit) {

    val nameValue = remember { mutableStateOf(TextFieldValue()) }
    val linkValue = remember { mutableStateOf(TextFieldValue()) }

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(
                text = "Інформація про публікацію наукових статей",
                fontSize = 24.sp
            )
            Text(text = "Додати статтю", fontSize = 18.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Назва")
                TextField(
                    value = nameValue.value,
                    onValueChange = { nameValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Назва") },
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Посилання")
                TextField(
                    value = linkValue.value,
                    onValueChange = { linkValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "https://example.com") },
                )
            }
            Button(onClick = {
                articleUploadListener.invoke(
                    Article(
                        name = nameValue.value.text,
                        url = linkValue.value.text,
                        status = "Sent"
                    )
                )
            }) { Text(text = "Upload") }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Список статей:")
            }
            ListOfArticles(listOfResearch)
        }
    }
}

@Composable
fun ThesisCard(listOfResearch: List<Research>) {

    val nameValue = remember { mutableStateOf(TextFieldValue()) }
    val linkValue = remember { mutableStateOf(TextFieldValue()) }

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(
                text = "Інформація про тези",
                fontSize = 24.sp
            )
            Text(text = "Додати тезу", fontSize = 18.sp)

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Назва")
                TextField(
                    value = nameValue.value,
                    onValueChange = { nameValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Назва") },
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Посилання")
                TextField(
                    value = linkValue.value,
                    onValueChange = { linkValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "https://example.com") },
                )
            }
            Button(onClick = { /*TODO*/ }) { Text(text = "Upload") }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Список тез:")
            }
            ListOfArticles(listOfResearch)
        }
    }
}

@Composable
fun ListOfArticles(listOfArticles: List<Research>) {
    var counter = 0
    Log.d("TTT", "${listOfArticles[counter].listOfArticles}")
    if (listOfArticles[counter].listOfArticles.isNotEmpty()) {
        Log.d("TTT", "RTY")
        Column(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            listOfArticles[counter].listOfArticles.forEach { res ->
                val textColor =
                    if (res.status == "Sent") Color.Yellow else if (res.status == "Approved") Color.Green else Color.Red
                Text(
                    text = res.url,
                    modifier = Modifier
                        .border(width = 4.dp, color = textColor)
                        .padding(8.dp)
                )
                counter++
            }
        }
    }
}


//{
//    if (listOfResearch.isEmpty() || listOfResearch.first().id == "") {
//        Text(
//            text = "There are no items in the list",
//            modifier = Modifier
//                .background(Color.Red)
//                .fillMaxSize()
//                .wrapContentSize(align = Alignment.Center)
//        )
//    } else {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//        ) {
//            items(listOfResearch) { item ->
//                repeat(15) {
//                    Card(
//                        shape = RoundedCornerShape(8.dp),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                            .clickable {
//                                navController.navigate("login")
//                            }
//                            .background(Color.Transparent)
//                    ) {
//                        Row(modifier = Modifier.background(Color.Red)) {
//                            Text(text = item.id, modifier = Modifier.padding(16.dp))
//                            Text(text = item.objectResearch, modifier = Modifier.padding(12.dp))
//                            Text(text = item.subjectResearch)
//                        }
//                    }
//                }
//            }
//        }
//    }
//    FloatingActionButton(
//        onClick = { navController.navigate("addResearch") },
//        modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp),
//        elevation = FloatingActionButtonDefaults.elevation()
//    ) {
//        Icon(Icons.Filled.Add, contentDescription = "Add")
//    }
//}