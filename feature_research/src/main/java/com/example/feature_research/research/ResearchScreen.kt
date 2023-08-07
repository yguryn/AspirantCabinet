package com.example.feature_research.research

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.model.Article
import com.example.core.model.Research
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

    val research = researchViewModel.listOfResearch.collectAsStateWithLifecycle()
    val researchHere: Research = research.value
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.verticalScroll(state = scrollState)
        ) {
            ArticleCard(research, semester, {
                researchHere.listOfArticles.add(it)
                researchViewModel.updateResearch(researchHere)
            }) {
                researchHere.listOfArticles.remove(it)
                researchViewModel.updateResearch(researchHere)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(
    research: State<Research>,
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
                text = "Тема дослідження: ",
                fontSize = 24.sp
            )
            Text(
                text = research.value.objectResearch,
                fontSize = 20.sp
            )
            Text(
                text = "Інформація про виконані роботи:",
                fontSize = 20.sp
            )
            Text(
                text = "Відправлено робіт: ${research.value.listOfArticles.filter { it.semester == semester }.size}",
                fontSize = 16.sp,
            )
            Text(
                text = "Прийнято робіт: ${research.value.listOfArticles.filter { it.status == "Approve" && it.semester == semester }.size}",
                fontSize = 16.sp
            )
            Text(
                text = "На перевірці: ${research.value.listOfArticles.filter { it.status == "Sent" && it.semester == semester }.size}",
                fontSize = 16.sp
            )
            Text(
                text = "Відхилено: ${research.value.listOfArticles.filter { it.status != "Sent" && it.status != "Approve" && it.semester == semester }.size}",
                fontSize = 16.sp
            )
            Text(text = "Додати роботу", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
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
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = com.postgraduate.cabinet.ui.R.color.brand_yellow)),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) { Text(text = "Надіслати", color = Color.Black) }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Список робіт:")
            }
            ListOfArticles(research.value.listOfArticles, semester) {
                articleDeleteListener.invoke(it)
            }
        }
    }
}


@Composable
fun ListOfArticles(
    listOfArticles: MutableList<Article>,
    semester: Int,
    onDeleteListener: (Article) -> Unit,
) {
    var counter = 0
    val context = LocalContext.current
    val listOfElements = listOfArticles.filter { it.semester == semester }
    Column(
        modifier = Modifier
            .height(250.dp)
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
                Column {

                    Row {
                        Text(
                            text = text,
                            modifier = Modifier
                                .border(width = 4.dp, color = textColor)
                                .padding(8.dp)
                        )
                        Text(
                            text = res.name,
                            color = Color.Blue,
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
                    val comment: String = if(res.supervisorComment == ""){
                        ""
                    }else {
                        "Коментар викладача: ${res.supervisorComment}"
                    }
                    Text(
                        text = comment,
                        modifier = Modifier
                            .padding(8.dp)
                    )
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
