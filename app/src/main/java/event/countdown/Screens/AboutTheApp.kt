@file:Suppress("UnusedMaterial3ScaffoldPaddingParameter")

package event.countdown.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import event.countdown.R
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.automirrored.filled.ArrowBack

data class TeamMember(
    val name: String,
    val githubUrl: String,
    val whatsappUrl: String,
    val linkedInUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTheApp(navController: NavController) {
    val context = LocalContext.current
    val openUrl: (String) -> Unit = { url ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    val teamMembers = listOf(
        TeamMember(
            name = "Fares Shahaht Fawy",
            githubUrl = "https://github.com/FaresShahat22",
            whatsappUrl = "https://wa.me/+201112052332",
            linkedInUrl = "https://www.linkedin.com/in/fares-shahat22?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"
        ),
        TeamMember(
            name = "seif el-din karam",
            githubUrl = "https://github.com/seifkaram11",
            whatsappUrl = "https://wa.me/+201070065331",
            linkedInUrl = "https://linkedin.com/in/seif-el-den-karam-salah-7352a8350"
        ),
        TeamMember(
            name = "Abdo Eltahrany",
            githubUrl = "https://github.com/abdsalama",
            whatsappUrl = "https://wa.me/+201097226802",
            linkedInUrl = "https://www.linkedin.com/in/abd-elrahman-el-tahrany-363422280/"
        ),
        TeamMember(
            name = "Youssef Mohammed",
            githubUrl = "https://github.com/yousef-ELHMISI",
            whatsappUrl = "https://wa.me/+201095878322",
            linkedInUrl = "https://www.linkedin.com/in/youssef-mohamed-911407252?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"
        ),
        TeamMember(
            name = "Abdo",
            githubUrl = "",
            whatsappUrl = "https://wa.me/+20",
            linkedInUrl = ""
        ),
        TeamMember(
            name = "Ahmed Mohammed",
            githubUrl = "https://github.com/ahmedelnagar90",
            whatsappUrl = "https://wa.me/+201022940256",
            linkedInUrl = "https://www.linkedin.com/in/ahmed-elnagar-784928288?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0D1B2A),
                        Color(0xFF1B263B)
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "About",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "This application helps you manage and track events in your daily life 🗓️. Stay organized, set reminders 🔔, and never miss an important date again! 🎉",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Text(
                    text = "Team Members",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                teamMembers.forEach { member ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xAAFFFFFF)
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily.Cursive,
                                            fontSize = 26.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFF0D1B2A)
                                        )
                                    ) {
                                        append(member.name)
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { openUrl(member.githubUrl) },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF000000))
                                        .shadow(4.dp, CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.github),
                                        contentDescription = "GitHub",
                                        modifier = Modifier.size(28.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                IconButton(
                                    onClick = { openUrl(member.whatsappUrl) },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF25D366))
                                        .shadow(4.dp, CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.whatsapp),
                                        contentDescription = "WhatsApp",
                                        modifier = Modifier.size(28.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                IconButton(
                                    onClick = { openUrl(member.linkedInUrl) },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFFFFFF))
                                        .shadow(4.dp, CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.linkedin),
                                        contentDescription = "LinkedIn",
                                        modifier = Modifier.size(28.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAbout() {
    val navController = rememberNavController()
    AboutTheApp(navController)
}