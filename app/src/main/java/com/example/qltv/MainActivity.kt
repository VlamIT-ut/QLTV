package com.example.qltv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qltv.ui.theme.QLTVTheme


enum class Screen(val route: String, val icon: @Composable () -> Unit, val label: Int) {
    Manager(
        "manager",
        { Icon(Icons.Filled.Home, contentDescription = "Manager") },
        R.string.manager_screen
    ),
    User(
        "user",
        { Icon(Icons.Filled.Person, contentDescription = "User") },
        R.string.user_screen
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )
        setContent {
            QLTVTheme {
                LibraryManagementApp()
            }
        }
    }
}

@Composable
fun LibraryManagementApp() {
    val navController = rememberNavController()

    // Sample data
    val bookList = remember {
        mutableStateListOf(
            Book(1, "Sách 01", "Tác giả A"),
            Book(2, "Sách 02", "Tác giả B"),
            Book(3, "Sách 03", "Tác giả C"),
        )
    }
    val user: MutableState<User> = remember {
        mutableStateOf(User(1, "Nguyen Van A"))
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        modifier = Modifier.fillMaxSize().padding(30.dp)
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxWidth().border(2.dp, color = Color.Black,
            RoundedCornerShape(8.dp)
        ).background(color = Color.Cyan).height(40.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            ) { Text("Quản Lý Thư Viện",
            textAlign = TextAlign.Center,fontSize = 30.sp,
            color = Color.Red, fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,)
        }

        AppNavHost(
            navController = navController,
            bookList = bookList,
            user = user,
            innerPadding = innerPadding,

        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    bookList: MutableList<Book>,
    user: MutableState<User>,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Manager.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Manager.route) {
            ManagerScreen(
                bookList = bookList,
                onAddBook = { newBook ->
                    bookList.add(newBook)
                },
                onBorrowBook = { book ->
                    book.isBorrowed = true
                    user.value.borrowedBooks.add(book)
                }
            )
        }
        composable(Screen.User.route) {
            UserScreen(
                user = user.value,
                onChangeUserName = { newName ->
                    user.value = user.value.copy(name = newName)
                }
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(Screen.Manager, Screen.User)
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = screen.icon,
                label = { Text(stringResource(screen.label)) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DefaultScreen(){
    QLTVTheme {
        LibraryManagementApp()
    }
}