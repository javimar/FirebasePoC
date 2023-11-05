package eu.javimar.firebasepoc.features.auth.login

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.features.auth.components.SocialMediaButton
import eu.javimar.firebasepoc.features.auth.login.state.LoginEvent
import eu.javimar.firebasepoc.features.auth.login.state.LoginState
import eu.javimar.firebasepoc.features.auth.utils.getErrorRes

@Composable
fun LoginScreen(
    state: LoginState,
    buttonState: Boolean,
    onEvent: (LoginEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == ComponentActivity.RESULT_OK) {
                onEvent(LoginEvent.GoogleSignIntent(result.data))
            }
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        AnimatedVisibility(
            visible = !state.hasUser,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp).padding(padding),
                contentAlignment = Alignment.Center
            ) {
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
                        text = stringResource(id = R.string.signup_form_samples),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 30.sp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                text = stringResource(id = R.string.signup_form_email)
                            )
                        },
                        value = state.email,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email
                        ),
                        onValueChange = {
                            onEvent(LoginEvent.EmailChanged(it))
                        }
                    )
                    if(state.emailError != null) {
                        Text(
                            modifier = Modifier.align(Alignment.Start),
                            text = stringResource(id = getErrorRes(state.emailError)),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                text = stringResource(id = R.string.signup_form_pass)
                            )
                        },
                        value = state.password,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Next)
                                keyboardController?.hide()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Password
                        ),
                        onValueChange = {
                            onEvent(LoginEvent.PasswordChanged(it))
                        }
                    )
                    if(state.passwordError != null) {
                        Text(
                            modifier = Modifier.align(Alignment.Start),
                            text = stringResource(id = getErrorRes(state.passwordError)),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Button(
                            onClick = {
                                onEvent(LoginEvent.UserPassLogin)
                            },
                            enabled = buttonState,
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.signup_form_login).uppercase()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    ClickableText(
                        text = AnnotatedString(
                            stringResource(id = R.string.signup_form_forgot_pass)
                        ),
                        onClick = {
                            onEvent(LoginEvent.ForgotPass)
                        },
                        style = TextStyle(
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.Underline,
                        )
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = stringResource(id = R.string.signup_form_continue_or),
                        style = TextStyle(color = Color.Gray)
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    SocialMediaButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onEvent(LoginEvent.GuestLogin)
                        },
                        text = stringResource(id = R.string.signup_form_continue_guest),
                        icon = R.drawable.ic_incognito,
                        color = Color(0xFF363636)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SocialMediaButton(
                        onClick = {
                            onEvent(LoginEvent.GoogleLogin(launcher))
                        },
                        text = stringResource(id = R.string.signup_form_continue_google),
                        icon = R.drawable.ic_google,
                        color = Color(0xFFF1F1F1)
                    )

                    ClickableText(
                        text = AnnotatedString(
                            stringResource(id = R.string.signup_form_not_account)
                        ),
                        modifier = Modifier
                            .padding(16.dp),
                        onClick = {
                            onEvent(LoginEvent.SignUp)
                        },
                        style = TextStyle(
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.Underline,
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen(
        state = LoginState(),
        onEvent = {},
        buttonState = true,
        snackbarHostState = SnackbarHostState()
    )
}