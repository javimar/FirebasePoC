package eu.javimar.firebasepoc.features.auth.forgotpass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.features.auth.forgotpass.state.ForgotPassEvent
import eu.javimar.firebasepoc.features.auth.forgotpass.state.ForgotPassState
import eu.javimar.firebasepoc.features.auth.utils.getErrorRes

@Composable
fun ForgotPasswordScreen(
    state: ForgotPassState,
    buttonState: Boolean,
    onEvent: (ForgotPassEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp).padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.signup_form_recover_pass_title),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 32.sp,
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                onValueChange = {
                    onEvent(ForgotPassEvent.EmailChanged(it))
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

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = {
                        onEvent(ForgotPassEvent.RecoverClicked)
                    },
                    enabled = buttonState,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_form_recover_pass_action)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPreview() {
    ForgotPasswordScreen(
        state = ForgotPassState(),
        buttonState = true,
        onEvent = {},
        snackbarHostState = SnackbarHostState()
    )
}