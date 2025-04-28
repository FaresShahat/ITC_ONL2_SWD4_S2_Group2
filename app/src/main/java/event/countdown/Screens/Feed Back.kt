@file:Suppress("UnusedMaterial3ScaffoldPaddingParameter")

package event.countdown.Screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestOrProblem(navController: NavController) {
    val context = LocalContext.current
    var feedback by remember { mutableStateOf("") }
    var isButtonPressed by remember { mutableStateOf(false) }
    var isTextFieldFocused by remember { mutableStateOf(false) }

    // Create an interaction source to detect focus changes
    val interactionSource = remember { MutableInteractionSource() }

    // Observe focus changes using LaunchedEffect
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction: Interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> isTextFieldFocused = true
                is FocusInteraction.Unfocus -> isTextFieldFocused = false
            }
        }
    }

    // أنيميشن لتغيير لون الزر
    val buttonBackgroundColor by animateColorAsState(
        targetValue = if (isButtonPressed) Color(0xFF00B7B7) else Color.Cyan,
        animationSpec = tween(durationMillis = 300)
    )

    // أنيميشن لتغيير لون الحدود عند التركيز
    val borderColor by animateColorAsState(
        targetValue = if (isTextFieldFocused) Color(0xFF00E5FF) else Color(0xFF444444),
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0D1B2A), Color(0xFF1B263B))
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Feedback",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.shadow(4.dp, RoundedCornerShape(8.dp))
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "We appreciate your feedback!\nLet us know any suggestions or problems.",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 28.dp)
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Use BasicTextField with OutlinedTextFieldDefaults.DecorationBox for custom border thickness
                BasicTextField(
                    value = feedback,
                    onValueChange = { newValue: String -> feedback = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shadow(
                            elevation = 6.dp,
                            shape = RoundedCornerShape(16.dp),
                            clip = true
                        ),
                    textStyle = TextStyle(color = Color.White),
                    interactionSource = interactionSource,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                ) { innerTextField ->
                    OutlinedTextFieldDefaults.DecorationBox(
                        value = feedback,
                        innerTextField = innerTextField,
                        enabled = true,
                        singleLine = false,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        placeholder = { Text("Type here...", color = Color(0xFFBBBBBB)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF2C2C2C),
                            unfocusedContainerColor = Color(0xFF2C2C2C),
                            cursorColor = Color.Cyan,
                            focusedBorderColor = borderColor,
                            unfocusedBorderColor = borderColor
                        ),
                        container = {
                            OutlinedTextFieldDefaults.ContainerBox(
                                enabled = true,
                                isError = false,
                                interactionSource = interactionSource,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFF2C2C2C),
                                    unfocusedContainerColor = Color(0xFF2C2C2C),
                                    cursorColor = Color.Cyan,
                                    focusedBorderColor = borderColor,
                                    unfocusedBorderColor = borderColor
                                ),
                                shape = RoundedCornerShape(16.dp),
                                focusedBorderThickness = 2.dp,
                                unfocusedBorderThickness = 1.dp
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = {
                        isButtonPressed = true
                        Toast.makeText(context, "Thank you!", Toast.LENGTH_SHORT).show()
                        feedback = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(buttonBackgroundColor, Color(0xFF00A3A3))
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Text(
                        "Submit",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSuggestOrProblem() {
    SuggestOrProblem(navController = rememberNavController())
}