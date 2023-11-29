package com.example.feature_research.research.research

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.Article
import com.example.core.model.Research
import com.postgraduate.cabinet.ui.R

@Composable
fun ArticleCard(
    research: State<Research>,
    semester: Int,
    articleUploadListener: (Article) -> Unit,
    articleDeleteListener: (Article) -> Unit,
) {
    val context = LocalContext.current
    val nameValue = remember { mutableStateOf(TextFieldValue()) }
    val linkValue = remember { mutableStateOf(TextFieldValue()) }
    val supportNameText = remember { mutableStateOf(String()) }
    val supportLinkText = remember { mutableStateOf(String()) }

    val (sentCount, approvedCount, onInspectionCount, declinedCount) = remember(
        research.value,
        semester
    ) {
        val articles = research.value.listOfArticles.filter { it.semester == semester }
        val sentCount = articles.size
        val approvedCount = articles.count { it.status == "Approve" }
        val onInspectionCount = articles.count { it.status == "Sent" }
        val declinedCount = articles.size - (approvedCount + onInspectionCount)
        listOf(sentCount, approvedCount, onInspectionCount, declinedCount)
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
                text = "${stringResource(id = R.string.research_topic)}: ",
                fontSize = 24.sp
            )
            Text(
                text = research.value.objectResearch,
                fontSize = 20.sp
            )
            Text(
                text = "${stringResource(id = R.string.information_about_completed_works)}:",
                fontSize = 20.sp
            )
            Text(
                text = "${stringResource(id = R.string.sent_tasks)}: $sentCount",
                fontSize = 16.sp
            )
            Text(
                text = "${stringResource(id = R.string.approved_tasks)}: $approvedCount",
                fontSize = 16.sp
            )
            Text(
                text = "${stringResource(id = R.string.on_inspection)}: $onInspectionCount",
                fontSize = 16.sp
            )
            Text(
                text = "${stringResource(id = R.string.declined)}: $declinedCount",
                fontSize = 16.sp
            )
            Text(
                text = stringResource(id = R.string.add_task),
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = nameValue.value,
                    onValueChange = { nameValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = stringResource(id = R.string.name)) },
                    supportingText = { Text(text = supportNameText.value, color = Color.Red) }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = linkValue.value,
                    onValueChange = { linkValue.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = stringResource(id = R.string.link_example)) },
                    supportingText = { Text(text = supportLinkText.value, color = Color.Red) }
                )
            }
            Button(
                onClick = {
                    handleArticleUpload(
                        nameValue,
                        linkValue,
                        supportNameText,
                        supportLinkText,
                        semester,
                        articleUploadListener,
                        context
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.brand_yellow)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { Text(text = stringResource(id = R.string.send), color = Color.Black) }

            Text(text = "${stringResource(id = R.string.list_of_jobs)}:")

            ListOfArticles(research.value.listOfArticles, semester) {
                articleDeleteListener.invoke(it)
            }
        }
    }
}

private fun handleArticleUpload(
    nameValue: MutableState<TextFieldValue>,
    linkValue: MutableState<TextFieldValue>,
    supportNameText: MutableState<String>,
    supportLinkText: MutableState<String>,
    semester: Int,
    articleUploadListener: (Article) -> Unit,
    context: Context
) {
    val (nameError, linkError) = checkIsDocumentValid(
        nameValue.value.text,
        linkValue.value.text,
        context
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
}

fun checkIsDocumentValid(name: String, link: String, context: Context): Pair<String, String> {
    var nameError = ""
    var linkError = ""
    if (name.isEmpty()) {
        nameError = context.getString(R.string.enter_correct_name)
    }
    if (link.isEmpty()) {
        linkError = context.getString(R.string.enter_correct_link)
    }
    return Pair(nameError, linkError)
}

fun openLink(url: String, context: Context) {
    try {
        val intent = { Intent(Intent.ACTION_VIEW, Uri.parse(url)) }
        context.startActivity(intent.invoke())
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            context,
            context.getString(R.string.unable_to_open_link),
            Toast.LENGTH_SHORT
        ).show()
    }
}