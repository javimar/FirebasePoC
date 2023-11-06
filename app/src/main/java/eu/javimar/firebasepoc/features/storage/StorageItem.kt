package eu.javimar.firebasepoc.features.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.javimar.domain.storage.model.FileStorageInfo
import eu.javimar.firebasepoc.core.components.RenderFileIcon
import eu.javimar.firebasepoc.ui.theme.Pink80
import eu.javimar.firebasepoc.ui.theme.Purple80
import eu.javimar.firebasepoc.ui.theme.PurpleGrey80

@Composable
fun StorageItem(
    fileInfo: FileStorageInfo,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable {
                onEventClick(fileInfo.url)
            }
            .padding(vertical = 4.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Purple80, PurpleGrey80, Pink80
                    ),
                ),
                RoundedCornerShape(16.dp),
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                RenderFileIcon(
                    fileUrl = fileInfo.url,
                    contentType = fileInfo.contentType
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = modifier
                ) {
                    Text(
                        text = fileInfo.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = fileInfo.created,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "${fileInfo.sizeBytes} bytes",
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
//            Text(
//                text = fileInfo.contentType,
//                style = MaterialTheme.typography.titleMedium,
//            )
//            Text(
//                text = fileInfo.path,
//                style = MaterialTheme.typography.titleMedium,
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    StorageItem(
        FileStorageInfo(
            url = "",
            path = "/Images/baby.jpg",
            name = "Entreno 2023331100.jpg",
            contentType = "application/pdf",
            sizeBytes = "54280",
            created = "26 abril"
        ),
        onEventClick = {},
    )
}