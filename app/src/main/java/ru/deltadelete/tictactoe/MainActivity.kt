@file:OptIn(ExperimentalMaterial3Api::class)

package ru.deltadelete.tictactoe

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.deltadelete.tictactoe.pages.AboutPage
import ru.deltadelete.tictactoe.pages.TicTacToePage
import ru.deltadelete.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Layout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Layout() {
    val controller = rememberNavController();
    Scaffold(
        bottomBar = { NavBar(controller) },
        topBar = {
            SmallTopAppBar({ Text(stringResource(R.string.app_name)) }) }
    ) {
        NavConf(controller, it)
    }
}

@Composable
fun NavBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState();
        val currentDestination = navBackStackEntry?.destination;
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == "TicTacToe" } == true,
            label = { Text(stringResource(R.string.app_name)) },
            icon = { Icon(Icons.Filled.Close, contentDescription = null) },
            onClick = { navController.navigate("TicTacToe") }
        )
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == "AboutPage" } == true,
            label = { Text(stringResource(R.string.about)) },
            icon = { Icon(Icons.Filled.Info, contentDescription = null) },
            onClick = { navController.navigate("AboutPage") }
        )
    }
}

@Composable
fun NavConf(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController, startDestination = "TicTacToe", modifier = Modifier.padding(paddingValues)) {
        composable("TicTacToe") {
            TicTacToePage()
        }
        composable("AboutPage") {
            AboutPage()
        }
    }
}

const val REDMI_NOTE_9_PRO = "spec:width=1080px,height=2232px,dpi=440,isRound=true,chinSize=0px,orientation=portrait"

@Preview(
    showBackground = true,
    showSystemUi = false,
    device = REDMI_NOTE_9_PRO,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 128
)
@Composable
fun DefaultPreview() {
    TicTacToeTheme {
        Layout()
    }
}