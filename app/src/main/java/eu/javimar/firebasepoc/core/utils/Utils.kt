package eu.javimar.firebasepoc.core.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun createFileName(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    return now.format(formatter)
}

fun createFolderNameWithDateTime(folderName: String): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH.mm")
    val formattedDateTime = now.format(formatter)
    return "$folderName $formattedDateTime"
}

fun convertMillisToLocalDate(utcMillis: Long): String {
    val instant = Instant.ofEpochMilli(utcMillis)
    val zoneId = ZoneId.of("UTC") // set the time zone to UTC
    return instant.atZone(zoneId).toLocalDate().formatDateToStringShort()
}

fun LocalDate.formatDateToStringShort(): String = format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
