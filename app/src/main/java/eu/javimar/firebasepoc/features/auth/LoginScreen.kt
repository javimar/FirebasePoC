package eu.javimar.firebasepoc.features.auth

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.features.auth.components.SocialMediaButton
import eu.javimar.firebasepoc.features.auth.state.SignInEvent
import eu.javimar.firebasepoc.features.auth.state.SignInState

@Composable
fun LoginScreen(
    state: SignInState,
    onEvent: (SignInEvent) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == ComponentActivity.RESULT_OK) {
                onEvent(SignInEvent.SignIntent(result.data))
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        ClickableText(
            text = AnnotatedString("Don't have an account? Register"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp),
            onClick = {

            },
            style = TextStyle(
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
            )
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_firebase),
                contentDescription = "Firebase",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Firebase Android Samples by JaviMar",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 30.sp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            TextField(
                label = {
                    Text(text = "Correo electrónico")
                },
                value = state.email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = {

                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                label = {
                    Text(text = "Contraseña")
                },
                value = state.password,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = {

                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
            ) {
                Button(
                    onClick = {

                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Login".uppercase())
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ClickableText(
                text = AnnotatedString("¿Olvidaste tu contraseña?"),
                onClick = {

                },
                style = TextStyle(
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                )
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "-------- or --------",
                style = TextStyle(color = Color.Gray)
            )

            Spacer(modifier = Modifier.height(25.dp))

            SocialMediaButton(
                onClick = {
                    onEvent(SignInEvent.GuestLogin)
                },
                text = "Continue as guest",
                icon = R.drawable.ic_incognito,
                color = Color(0xFF363636)
            )

            Spacer(modifier = Modifier.height(16.dp))

            SocialMediaButton(
                onClick = {

                },
                text = "Continue with Google",
                icon = R.drawable.ic_google,
                color = Color(0xFFF1F1F1)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen(
        state = SignInState(),
        onEvent = {}
    )
}