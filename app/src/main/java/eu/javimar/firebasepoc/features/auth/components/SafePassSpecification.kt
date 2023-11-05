package eu.javimar.firebasepoc.features.auth.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.core.components.ImagePainter
import eu.javimar.firebasepoc.ui.theme.Positive

@Composable
fun SafePassSpecification(
    modifier: Modifier = Modifier,
    okLength: Boolean,
    okSymbols: Boolean,
    okChars: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        SpecRow(
            painter = R.drawable.ic_bullet,
            text = R.string.signup_password_requirement_length,
            isOk = okLength
        )

        SpecRow(
            painter = R.drawable.ic_bullet,
            text = R.string.signup_password_requirement_symbols,
            isOk = okSymbols
        )

        SpecRow(
            painter = R.drawable.ic_bullet,
            text = R.string.signup_password_requirement_characters,
            isOk = okChars
        )
    }
}

@Composable
fun SpecRow(
    @DrawableRes painter: Int,
    @StringRes text: Int,
    isOk: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if(isOk) {
                ImagePainter(
                    modifier = Modifier.width(16.dp),
                    painter = R.drawable.ic_check,
                    color = Positive
                )
            } else {
                ImagePainter(
                    modifier = Modifier.width(4.dp),
                    painter = painter,
                    color = MaterialTheme.colorScheme.error
                )

            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.labelSmall
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SafePassSpecificationPreview() {
    SafePassSpecification(
        okLength = true,
        okSymbols = false,
        okChars = false,
    )
}