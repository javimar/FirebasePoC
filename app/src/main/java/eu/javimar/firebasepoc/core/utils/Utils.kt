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

fun convertMillisToDate(utcMillis: Long): String {
    val instant = Instant.ofEpochMilli(utcMillis)
    val zoneId = ZoneId.of("UTC") // set the time zone to UTC
    return instant.atZone(zoneId).toLocalDate().formatDateToStringLong()
}

fun convertEpochToDateTime(epochSeconds: Long): String {
    val instant = Instant.ofEpochSecond(epochSeconds)
    val myZoneId = ZoneId.systemDefault() // use the system's default time zone
    val localDateTime = instant.atZone(myZoneId).toLocalDateTime()
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val dateStr = localDateTime.toLocalDate().format(dateFormatter)
    val timeStr = localDateTime.toLocalTime().format(timeFormatter)
    return "$dateStr $timeStr"
}

fun LocalDate.formatDateToStringLong(): String = format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
