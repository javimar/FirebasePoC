package eu.javimar.firebasepoc.features.auth.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.javimar.coachpoc.R

@Composable
fun SocialMediaButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    icon: Int,
    color: Color
) {
    var click by remember { mutableStateOf(false) }

    Surface(
        onClick = onClick,
        modifier = modifier
            .clickable {
                click = !click
            },
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            width = 1.dp,
            color = if(icon == R.drawable.ic_incognito) color else Color.Gray),
        color = color
    ) {
        Row(
            modifier = modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                modifier = Modifier.size(24.dp),
                contentDescription = text,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = if(icon == R.drawable.ic_incognito) Color.White else Color.Black)
            click = true
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SocialMediaButtonPreview() {
    SocialMediaButton(
        onClick = {},
        text =  "Google",
        icon = R.drawable.ic_incognito,
        color = Color(0xFF363636)
    )
}
