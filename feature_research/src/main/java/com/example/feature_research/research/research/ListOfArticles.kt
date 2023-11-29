package com.example.feature_research.research.research

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.model.Article
import com.example.feature_research.ArticleStatus
import com.example.feature_research.R

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
                    val comment: String = if (res.supervisorComment == "") {
                        ""
                    } else {
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
