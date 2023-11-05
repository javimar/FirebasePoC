package eu.javimar.firebasepoc.features.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.features.auth.components.SafePassSpecification
import eu.javimar.firebasepoc.features.auth.signup.state.SignUpEvent
import eu.javimar.firebasepoc.features.auth.signup.state.SignUpState
import eu.javimar.firebasepoc.features.auth.utils.getErrorRes
import eu.javimar.firebasepoc.ui.theme.Purple40

@Composable
fun SignUpScreen(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(32.dp).padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.signup_form_create_account),
                style = TextStyle(fontSize = 32.sp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.signup_form_email),
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
                    onEvent(SignUpEvent.EmailChanged(it))
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

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.signup_form_pass),
                    )
                },
                trailingIcon = {
                    val image = if(passwordVisible)
                        painterResource(id = R.drawable.is_visible)
                    else
                        painterResource(id = R.drawable.ic_visible_not)
                    IconButton(
                        modifier = Modifier
                            .width(42.dp)
                            .padding(end = 16.dp),
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            painter = image,
                            tint = Purple40,
                            contentDescription = null
                        )
                    }
                },
                value = state.password,
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                onValueChange = {
                    onEvent(SignUpEvent.PasswordChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            SafePassSpecification(
                okLength = state.checkPassLength,
                okSymbols = state.checkPassSymbol,
                okChars = state.checkPassMayusMinusNumber
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.signup_form_confirm_pass),                )
                },
                trailingIcon = {
                    val image = if(passwordVisible)
                        painterResource(id = R.drawable.is_visible)
                    else
                        painterResource(id = R.drawable.ic_visible_not)
                    IconButton(
                        modifier = Modifier
                            .width(42.dp)
                            .padding(end = 16.dp),
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            painter = image,
                            tint = Purple40,
                            contentDescription = null
                        )
                    }
                },
                value = state.confirmPassword,
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                onValueChange = {
                    onEvent(SignUpEvent.ConfirmPasswordChanged(it))
                }
            )
            if(state.confirmPasswordError != null) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(id = getErrorRes(state.confirmPasswordError)),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        onEvent(SignUpEvent.RegisterClicked)
                    },
                    shape = RoundedCornerShape(50.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_form_register)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            ClickableText(
                text = AnnotatedString(
                    stringResource(id = R.string.signup_form_has_account)
                ),
                onClick = {
                    onEvent(SignUpEvent.AlreadyAccount)
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUpScreen(
        SignUpState(),
        onEvent = {},
        snackbarHostState = SnackbarHostState()
    )
}