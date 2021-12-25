package com.ananananzhuo.pageanim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.ananananzhuo.pageanim.ui.theme.ComposeNavigationPageAnimSampleTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationPageAnimSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

const val home = "home"
const val main = "main"
const val slide_horizonal_anim = "水平滑动进入和水平滑动退出动画"
const val slide_fadein_and_fadeout_anim = "淡入和淡出动画"
const val slide_expandin_an_shinkout_anim = "膨胀进入和收缩退出动画"
const val scalein_and_scaleout="放大进入和缩小退出"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Greeting(name: String) {
    val controller = rememberAnimatedNavController()
    val flag = remember {
        mutableStateOf(slide_horizonal_anim)
    }
    AnimatedNavHost(navController = controller,
        startDestination = home,
        enterTransition = {//加号可以拼接两个动画
            enterAnim(flag.value)
        },
        exitTransition = {
            exitAnim(flag.value)
        }) {

        composable(home) {
            Home(controller, flag)
        }
        composable(main) {
            Main(controller, flag)
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun enterAnim(flag: String): EnterTransition {
    return when (flag) {
        slide_horizonal_anim -> {
            slideInHorizontally(animationSpec = tween(1000), initialOffsetX = {
                -it
            })
        }
        slide_fadein_and_fadeout_anim->{
            fadeIn(animationSpec = tween(1000), initialAlpha = 0f)
        }
        slide_expandin_an_shinkout_anim->{
            expandIn(animationSpec = tween(1000), expandFrom = Alignment.TopStart){
                IntSize(0,0)
            }
        }
        scalein_and_scaleout->{
            scaleIn(animationSpec = tween(1000), initialScale = 0f, transformOrigin = TransformOrigin(0f,0f))
        }
        else -> {
            slideInHorizontally(animationSpec = tween(1000), initialOffsetX = {
                -it
            })
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun exitAnim(flag: String): ExitTransition {
    return when (flag) {
        slide_horizonal_anim -> {
            slideOutHorizontally(animationSpec = tween(1000), targetOffsetX = {
                it
            })
        }
        slide_fadein_and_fadeout_anim->{
            fadeOut(animationSpec = tween(1000), targetAlpha = 0f)
        }
        slide_expandin_an_shinkout_anim->{
            shrinkOut(animationSpec = tween(1000), shrinkTowards = Alignment.BottomEnd) {//缩小80%
                it*4/5
            }
        }
        scalein_and_scaleout->{
            scaleOut(animationSpec = tween(1000), targetScale = 0f, transformOrigin = TransformOrigin(1f,1f))
        }
        else -> {
            slideOutHorizontally(animationSpec = tween(1000), targetOffsetX = {
                it
            })
        }
    }
}

@Composable
fun Home(controller: NavHostController, flag: MutableState<String>) {
    val showdialog = remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            controller.navigate(main)
        }) {
            Text(text = "点击跳转main页面")
        }
        Button(onClick = {
            showdialog.value = true
        }) {
            Text(text = "更改进入退出动画")
        }
    }

    ShowDialog(showdialog = showdialog, flag)
}

@Composable
fun Main(controller: NavHostController, flag: MutableState<String>) {
    val showdialog = remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Green),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            controller.navigate(home)
        }) {
            Text(text = "点击跳转home页面")
        }
        Button(onClick = {
            showdialog.value = true
        }) {
            Text(text = "更改进入退出动画")
        }
    }
    ShowDialog(showdialog, flag)
}

val amins = mutableListOf(
    slide_horizonal_anim,
    slide_fadein_and_fadeout_anim,
    slide_expandin_an_shinkout_anim,
    scalein_and_scaleout
)

@Composable
fun ShowDialog(showdialog: MutableState<Boolean>, flag: MutableState<String>) {
    if (showdialog.value) {
        Dialog(
            onDismissRequest = {
                showdialog.value = false
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
        ) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                amins.forEach {
                    Column(
                        Modifier
                            .width(300.dp)
                            .height(55.dp)
                            .background(Color.White)
                            .clickable {
                                flag.value = it
                                showdialog.value = false
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), contentAlignment = Alignment.Center){
                            Text(text = it)
                        }
                        Divider()
                    }
                }
            }

        }
    }
}