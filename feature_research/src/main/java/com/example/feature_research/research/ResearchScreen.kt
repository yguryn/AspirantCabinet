package com.example.feature_research.research

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.model.Article
import com.example.core.model.Research
import com.example.core.model.Thesis
import com.example.feature_research.ArticleStatus
import com.example.feature_research.R

@Composable
fun ResearchScreen(
    navController: NavController,
    researchViewModel: ResearchViewModel,
    semester: Int
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {
        researchViewModel.getAllResearches()
    }

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
            ThesisCard(listOfResearch, semester, {
                listOfResearchHere[0].listOfThesis.add(it)
                researchViewModel.updateResearch(listOfResearchHere[0])
            }, {
                listOfResearchHere[0].listOfThesis.remove(it)
                researchViewModel.updateResearch(listOfResearchHere[0])
            })
            ArticleCard(listOfResearch, semester, {
                listOfResearchHere[0].listOfArticles.add(it)
                researchViewModel.updateResearch(listOfResearchHere[0])
            }) {
                listOfResearchHere[0].listOfArticles.remove(it)
                researchViewModel.updateResearch(listOfResearchHere[0])
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(
    listOfResearch: State<List<Research>>,
    semester: Int,
    articleUploadListener: (Article) -> Unit,
    articleDeleteListener: (Article) -> Unit,
) {

    val nameValue = remember { mutableStateOf(TextFieldValue()) }
    val linkValue = remember { mutableStateOf(TextFieldValue()) }
    val supportNameText = remember { mutableStateOf(String()) }
    val supportLinkText = remember { mutableStateOf(String()) }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
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
                TextField(
                    value = nameValue.value,
                    onValueChange = { nameValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Назва") },
                    supportingText = { Text(text = supportNameText.value, color = Color.Red) }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = linkValue.value,
                    onValueChange = { linkValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "https://example.com") },
                    supportingText = { Text(text = supportLinkText.value, color = Color.Red) }
                )
            }
            Button(
                onClick = {
                    val (nameError, linkError) = checkIsDocumentValid(
                        nameValue.value.text,
                        linkValue.value.text
                    )
                    supportNameText.value = nameError
                    supportLinkText.value = linkError
                    if (supportNameText.value.isEmpty() && supportLinkText.value.isEmpty()) {
                        articleUploadListener.invoke(
                            Article(
                                name = nameValue.value.text,
                                url = linkValue.value.text,
                                status = "Sent",
                                semester = semester
                            )
                        )
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) { Text(text = "Надіслати") }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Список статей:")
            }
            ListOfArticles(listOfResearch, semester) {
                articleDeleteListener.invoke(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThesisCard(
    listOfResearch: State<List<Research>>,
    semester: Int,
    thesisUploadListener: (Thesis) -> Unit,
    thesisDeleteListener: (Thesis) -> Unit,
) {

    val nameValue = remember { mutableStateOf(TextFieldValue()) }
    val linkValue = remember { mutableStateOf(TextFieldValue()) }
    val supportNameText = remember { mutableStateOf(String()) }
    val supportLinkText = remember { mutableStateOf(String()) }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
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
                TextField(
                    value = nameValue.value,
                    onValueChange = { nameValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Назва") },
                    supportingText = { Text(text = supportNameText.value, color = Color.Red) }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = linkValue.value,
                    onValueChange = { linkValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "https://example.com") },
                    supportingText = { Text(text = supportLinkText.value, color = Color.Red) }
                )
            }
            Button(onClick = {
                val (nameError, linkError) = checkIsDocumentValid(
                    nameValue.value.text,
                    linkValue.value.text
                )
                Log.d("TTT", "${nameValue.value.text}")
                Log.d("TTT", "${linkValue.value.text}")
                supportNameText.value = nameError
                supportLinkText.value = linkError
                Log.d("TTT", "${supportNameText.value}")
                Log.d("TTT", "${supportLinkText.value}")
                if (supportNameText.value.isEmpty() && supportLinkText.value.isEmpty()) {
                    thesisUploadListener.invoke(
                        Thesis(
                            status = "Sent",
                            url = "https://www.google.com/",
                            name = nameValue.value.text,
                            semester = semester
                        )
                    )
                }
            }, modifier = Modifier.align(Alignment.CenterHorizontally)) { Text(text = "Надіслати") }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Список тез:")
            }
            ListOfTheses(listOfResearch, semester) {
                thesisDeleteListener.invoke(it)
            }
        }
    }
}

@Composable
fun ListOfArticles(
    listOfArticles: State<List<Research>>,
    semester: Int,
    onDeleteListener: (Article) -> Unit,
) {
    var counter = 0
    val context = LocalContext.current
    if (listOfArticles.value.isNotEmpty()) {
        val listOfElements =
            listOfArticles.value[0].listOfArticles.filter { it.semester == semester }
        Column(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            if (listOfElements.isNotEmpty()) {
                listOfElements.forEach { res ->
                    val textColor: Color
                    val text: String
                    when (res.status) {
                        "Sent" -> {
                            textColor = Color.Yellow
                            text = ArticleStatus.SENT.status
                        }
                        "Approve" -> {
                            textColor = Color.Green
                            text = ArticleStatus.APPROVED.status
                        }
                        else -> {
                            textColor = Color.Red
                            text = ArticleStatus.REJECTED.status
                        }
                    }
                    Row {
                        Text(
                            text = text,
                            modifier = Modifier
                                .border(width = 4.dp, color = textColor)
                                .padding(8.dp)
                        )
                        Text(
                            text = res.name,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    openLink(url = res.url, context = context)
                                }
                        )

                        Image(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Delete",
                            modifier = Modifier
                                .padding(6.dp)
                                .clickable { onDeleteListener.invoke(res) },
                        )
                        counter++
                    }
                }
            }
        }
    }
}

@Composable
fun ListOfTheses(
    listOfTheses: State<List<Research>>,
    semester: Int,
    onDeleteListener: (Thesis) -> Unit,
) {
    var counter = 0
    val context = LocalContext.current
    if (listOfTheses.value.isNotEmpty()) {
        val listOfElements =
            listOfTheses.value[0].listOfThesis.filter { it.semester == semester }
        Column(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            if (listOfElements.isNotEmpty()) {
                listOfElements.forEach { res ->
                    val textColor: Color
                    val text: String
                    when (res.status) {
                        "Sent" -> {
                            textColor = Color.Yellow
                            text = ArticleStatus.SENT.status
                        }
                        "Approved" -> {
                            textColor = Color.Green
                            text = ArticleStatus.APPROVED.status
                        }
                        else -> {
                            textColor = Color.Red
                            text = ArticleStatus.REJECTED.status
                        }
                    }
                    Row {
                        Text(
                            text = text,
                            modifier = Modifier
                                .border(width = 4.dp, color = textColor)
                                .padding(8.dp)
                        )
                        Text(
                            text = res.name,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    openLink(url = res.url, context = context)
                                }
                        )

                        Image(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Delete",
                            modifier = Modifier
                                .padding(6.dp)
                                .clickable { onDeleteListener.invoke(res) },
                        )
                        counter++
                    }
                }
            }
        }
    }
}


fun checkIsDocumentValid(name: String, link: String): Pair<String, String> {
    var nameError = ""
    var linkError = ""
    if (name.isEmpty()) {
        nameError = "Введіть коректно назву"
    }
    if (link.isEmpty()) {
        linkError = "Введіть коректно посилання"
    }
    return Pair(nameError, linkError)
}

fun openLink(url: String, context: Context) {
    try {
        val intent = { Intent(Intent.ACTION_VIEW, Uri.parse(url)) }
        context.startActivity(intent.invoke())
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Неможливо відкрити посилання", Toast.LENGTH_SHORT).show()
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