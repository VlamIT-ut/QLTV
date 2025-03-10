package com.example.qltv

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserScreen(
    user: User,
    onChangeUserName: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // Biến tạm để nhập tên mới
    var newName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Thông tin người dùng", textAlign = TextAlign.Center, fontSize = 24.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.ExtraBold,)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Tên người dùng: ${user.name}")
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            showDialog = true
            newName = user.name // gán tên hiện tại vào ô nhập
        }) {
            Text("Đổi")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Sách đã mượn:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(user.borrowedBooks) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Column {
                            Text(text = "ID: ${book.id}")
                            Text(text = "Tên: ${book.title}")
                            Text(text = "Tác giả: ${book.author}")
                        }
                    }
                }
            }
        }
    }

    // Dialog đổi tên
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Đổi tên người dùng") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Tên mới") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onChangeUserName(newName)
                        showDialog = false
                    }
                ) {
                    Text("Xác nhận")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Hủy")
                }
            }
        )
    }
}
