package eu.javimar.firebasepoc.features.profile

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.features.BottomBar
import eu.javimar.firebasepoc.features.auth.components.LogoutDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CheckProfilePicture(
                            photoUrl = state.user?.photoUrl,
                            size = 40.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if(!state.user?.displayName.isNullOrEmpty())
                                    "Hola ${state.user?.displayName}" else "Welcome",
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = if(!state.user?.email.isNullOrEmpty())
                                    "${state.user?.email}" else "Anonymous",
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis)
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(ProfileEvent.SignOut)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CheckProfilePicture(
                photoUrl = state.user?.photoUrl,
                size = 150.dp
            )
            if(state.user?.displayName != null) {
                Text(
                    text = state.user.displayName!!,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            if (state.showExitDialog) {
                LogoutDialog(
                    onConfirmLogout = {
                        //onLogoutConfirmed()
                        //showDialog = false
                    },
                    onDismiss = {
                        //showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun CheckProfilePicture(
    photoUrl: Uri?,
    size: Dp
) {
    if(photoUrl != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Profile picture",
            placeholder = painterResource(id = R.drawable.profile),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(size))
    } else {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Default Profile picture",
            modifier = Modifier
                .padding(end = 8.dp)
                .size(size)
                .clip(CircleShape)
        )
    }
}

@Preview
@Composable
fun ProfilePreview() {
    ProfileScreen(
        state = ProfileState(),
        onEvent = {},
        navController = rememberNavController()
    )
}