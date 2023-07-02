package com.barinov.simpleplayer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center


import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.barinov.simpleplayer.R
import kotlinx.coroutines.delay

@Composable
fun AlertdialogComponent(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    icon: @Composable (() -> Unit)? = null, //if null then ignore above
    msg: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    properties: DialogProperties? //if null then ignore above
) {
    val openDialog by remember { mutableStateOf(false) }
    if (!openDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = title,
            text = msg,
            dismissButton = dismissButton,
            confirmButton = confirmButton,
        )
    }
}

@Composable
fun AlertDialogMainBlock(
    messageRef: Int,
    checkedOnStart: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    Column() {
        Text(
            text = stringResource(id = messageRef),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(18.dp))
        Checkbox(checked = checkedOnStart, onCheckedChange = {
            onCheckChanged.invoke(it)
        })
    }
}

//@Composable
//fun AlertDialogButtonsBlock(
//    onClick: DialogButtonActionsWithCheckBox<Boolean>,
//    checkState: Boolean
//){
//    Row() {
//        ElevatedButton(onClick = {
//            onClick.onPositiveButtonClicked(checkState)
//        }) {
//            Text(text = stringResource(id = android.R.string.cancel))
//        }
//
//        Spacer(modifier = Modifier.width(26.dp))
//
//        ElevatedButton(onClick = {
//            onClick.onPositiveButtonClicked(checkState)
//        }) {
//            Text(text = stringResource(id = android.R.string.ok))
//        }
//    }
//}


@Composable
fun AddFromCurrentDirImageButton(
    onClick: () -> Unit
) {
//    DisposableEffect(key1 = , effect = )
//    val infiniteTransition = rememberInfiniteTransition()
//    val animation by infiniteTransition.animateFloat(
//        initialValue = 60.0f,
//        targetValue = 70.0f,
//        animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse)
//    )
    IconButton(
        onClick = { onClick.invoke() },
        Modifier.size(50.dp)
    ) {
        Icon(painter = painterResource(id = R.drawable.add_tracks), "")
//        ExpandedMenu(arrayOf(), {})
    }
}

@Composable
fun selectableRipple() = rememberRipple(color = selectable_color)


@Composable
fun ExpandedMenu(
    refs: Array<Int>, //strings
    onClick: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        AnimatedVisibility(visible = refs.isNotEmpty()) {
            IconButton(onClick = {
                expanded = !expanded
            }
            ) {
                AnimatedMoreIcon()
//                Icon(Icons.Default.MoreVert, "", tint = Color(0xDDDDDDDD))
//            Text("DropDown")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
//            modifier = Modifier.fillMaxWidth()
            ) {
                refs.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClick(label)
                        }) {
                        Text(stringResource(id = label))
                    }
                }
            }
        }
    }
}


@Composable
fun WavesAnimatedHome(
    onClick: () -> Unit = {}
) {

    val waves = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(4000, easing = FastOutLinearInEasing),
        repeatMode = RepeatMode.Restart,
    )
    waves.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 1000L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = animationSpec
            )
        }
    }

    val dys = waves.map { it.value }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        // Waves
        dys.forEach { dy ->
            Box(
                Modifier
                    .size(250.dp)
                    .align(Center)
                    .graphicsLayer {
                        scaleX = dy * 4 + 1
                        scaleY = dy * 4 + 1
                        alpha = 1 - dy
                    },
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color(0xDD8FF39C), shape = CircleShape)
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.play_icon),
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    remember { MutableInteractionSource() },
                    selectableRipple()
                ) {
                    onClick.invoke()
                }
                .size(250.dp)
        )
    }

}



@Composable
fun AnimatedExpandButton(
    onClick: () -> Unit
) {

    Icon(Icons.Default.MoreVert, "", tint = Color.White)
}

@Composable
fun AnimatedMoreIcon() {

    val dots = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )

//    val animationSpec = infiniteRepeatable<Float>(
//        animation = tween(4000, easing = FastOutLinearInEasing),
//        repeatMode = RepeatMode.Restart,
//    )

    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 150L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 3000
                        0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
                        0.5f at 300 with LinearOutSlowInEasing // for 15-75 ms
                        0.2f at 800 with LinearOutSlowInEasing // for 0-15 ms
                        0.0f at 3000
                    },
                    repeatMode = RepeatMode.Restart,
                )
            )
        }
    }

    val dys = dots.map { it.value }

    val travelDistance = with(LocalDensity.current) { 10.dp.toPx() }

    Column(
//        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
//        horizontalAlignment = Arrangement.h
    ) {
        dys.forEachIndexed { index, dy ->
            Box(
                Modifier
                    .size(4.dp)
                    .graphicsLayer {
                        translationY = -dy * travelDistance
                    },
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.White, shape = CircleShape)
                )
            }

            if (index != dys.size - 1) {
                Spacer(modifier = Modifier.height(7.dp))
            }
        }
    }
}


