package com.example.qltv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ManagerScreen(
    bookList: MutableList<Book>,
    onAddBook: (Book) -> Unit,
    onBorrowBook: (Book) -> Unit
) {

    var newBookTitle by remember { mutableStateOf("") }
    var newBookAuthor by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Danh sách Sách", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(bookList) { book ->
                BookItem(
                    book = book,
                    onBorrowBook = onBorrowBook
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = newBookTitle,
            onValueChange = { newBookTitle = it },
            label = { Text("Tên sách mới") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = newBookAuthor,
            onValueChange = { newBookAuthor = it },
            label = { Text("Tác giả") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (newBookTitle.isNotBlank()) {
                    val newId = if (bookList.isEmpty()) 1 else bookList.maxOf { it.id } + 1
                    onAddBook(Book(newId, newBookTitle, newBookAuthor))
                    newBookTitle = ""
                    newBookAuthor = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Thêm")
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onBorrowBook: (Book) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "ID: ${book.id}")
                Text(text = "Tên: ${book.title}")
                Text(text = "Tác giả: ${book.author}")
                Text(text = if (book.isBorrowed) "Đã mượn" else "Chưa mượn")
            }
            if (!book.isBorrowed) {
                Button(enabled = true, modifier = Modifier.fillMaxSize().align(Alignment.Top),
                    onClick = { onBorrowBook(book) }
                ) {
                    Text("Mượn")
                }
            }
        }
    }
}
