package eu.javimar.firebasepoc.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.ui.theme.NiceBlue

@Composable
fun RenderFileIcon(
    fileUrl: String,
    contentType: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val painter: Painter

    with(contentType) {
        when{
            contains("image", ignoreCase = true) -> {
                painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = fileUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            transformations(
                                RoundedCornersTransformation(topLeft = 20f, topRight = 20f, bottomLeft = 20f, bottomRight = 20f))
                        }).build())
            }
            contains("video", ignoreCase = true) -> painter = painterResource(id = R.drawable.ic_video_file)
            contains("pdf", ignoreCase = true) -> painter = painterResource(id = R.drawable.ic_pdf_file)
            contains("officedocument.wordprocessingml", ignoreCase = true) -> painter = painterResource(id = R.drawable.ic_ms_word_file)
            contains("audio", ignoreCase = true) -> painter = painterResource(id = R.drawable.ic_audio_file)
            contains("spreadsheet", ignoreCase = true) -> painter = painterResource(id = R.drawable.ic_excel_file)
            else -> painter = painterResource(id = R.drawable.ic_other_file)
        }
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .padding(6.dp)
            .size(72.dp)
            .clip(CircleShape),
        colorFilter = if(!contentType.contains("image", ignoreCase = true)) ColorFilter.tint(NiceBlue) else null,
        contentScale = contentScale,
    )
}