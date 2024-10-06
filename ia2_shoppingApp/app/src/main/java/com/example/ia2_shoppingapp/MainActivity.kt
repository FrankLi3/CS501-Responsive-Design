package com.example.ia2_shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ia2_shoppingapp.ui.theme.Ia2_shoppingAppTheme

data class Item(val description: String, val amount: String = "", val isChecked: Boolean = false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ia2_shoppingAppTheme {
                shoppingApp()
            }
        }
    }
}

@Composable
fun shoppingApp() {
    var itemList by remember { mutableStateOf(listOf<Item>()) }
    var popUp by remember { mutableStateOf(false) }
    var newTask by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Top Banner
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Shopping App",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                if (itemList.isEmpty()) {
                    // Show message if shopping list is empty
                    item {
                        Text(
                            text = "click '+' to add item",
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    // Update item list with checkbox and amount
                    items(itemList.size) { index ->
                        val item = itemList[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = { isChecked ->
                                    itemList = itemList.toMutableList().apply {
                                        this[index] = this[index].copy(isChecked = isChecked)
                                    }
                                }
                            )
                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Text(
                                    text = item.description,
                                    color = if (item.isChecked) Color.Gray else Color.Black,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text(
                                        text = "Amount: ",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    IntOnly(
                                        value = item.amount,
                                        onValueChange = { newAmount ->
                                            itemList = itemList.toMutableList().apply {
                                                this[index] = this[index].copy(amount = newAmount)
                                            }
                                        },
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                        Divider()
                    }
                }
            }

            // Button banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    // Button to add new item
                    FloatingActionButton(
                        onClick = { popUp = true },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add New Task")
                    }
                }
            }


            // Pop-up to add new item
            if (popUp) {
                AlertDialog(
                    onDismissRequest = { popUp = false },
                    title = {
                        Text(
                            text = "Add new item",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    text = {
                        Column {
                            Text(
                                text = "Item name:",
                                style = MaterialTheme.typography.bodySmall
                            )
                            BasicTextField(
                                value = newTask,
                                onValueChange = { newTask = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    },
                    // Only add item if it is not blank
                    confirmButton = {
                        Button(onClick = {
                            if (newTask.isNotBlank()) {
                                itemList = itemList + Item(newTask)
                                newTask = ""
                                popUp = false
                            }
                        }) {
                            Text("Add")
                        }
                    },
                    // Cancel adding task
                    dismissButton = {
                        Button(onClick = { popUp = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

// Only allow integers in text field
@Composable
fun IntOnly(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ia2_shoppingAppTheme {
        shoppingApp()
    }
}