package eu.javimar.firebasepoc.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import eu.javimar.coachpoc.R

@Composable
fun CoilImage(
    fileUrl: String,
    contentType: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val painter: Painter
    if(contentType.contains("image/", ignoreCase = true)) {
        painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(LocalContext.current)
                .data(data = fileUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                    transformations(
                        RoundedCornersTransformation(topLeft = 20f, topRight = 20f, bottomLeft = 20f, bottomRight = 20f)
                    )
                })
                .build()
        )
    } else {
        painter = painterResource(id = R.drawable.ic_file)
    }
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .padding(6.dp)
            .size(72.dp)
            .clip(CircleShape),
        contentScale = contentScale,
    )
}