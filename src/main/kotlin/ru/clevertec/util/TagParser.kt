package ru.clevertec.util

import java.util.regex.Matcher
import java.util.regex.Pattern

private const val GIT_TAG_VERSION = "v\\d*\\.\\d*-*[a-z]*"
private const val GIT_TAG_VERSION_GROUPS = "v(\\d*)\\.(\\d*)-*[a-z]*"

class TagParser {

    fun getMajorNumberOfGreatestVersion(tag: String): Int {
        var major = 0
        var minor = 0
        val matcher: Matcher =
            Pattern.compile(GIT_TAG_VERSION, Pattern.MULTILINE).matcher(tag)
        while (matcher.find()) {
            val majorVersion = TagParser().getMajorVersion(matcher.group(0))
            val minorVersion = TagParser().getMinorVersion(matcher.group(0))
            if (majorVersion > major) {
                major = majorVersion
                minor = minorVersion
            } else if (majorVersion == major && minorVersion > minor) {
                minor = minorVersion
            }
        }
        return major
    }

    fun getMinorNumberOfGreatestVersion(tag: String): Int {
        var major = 0
        var minor = 0
        val matcher: Matcher =
            Pattern.compile(GIT_TAG_VERSION, Pattern.MULTILINE)
                .matcher(tag)
        while (matcher.find()) {
            val majorVersion = TagParser().getMajorVersion(matcher.group(0))
            val minorVersion = TagParser().getMinorVersion(matcher.group(0))
            if (majorVersion > major) {
                major = majorVersion
                minor = minorVersion
            } else if (majorVersion == major && minorVersion > minor) {
                minor = minorVersion
            }
        }
        return minor
    }

    private fun getMajorVersion(tagWithVersion: String): Int {
        val matcher = Pattern.compile(GIT_TAG_VERSION_GROUPS).matcher(tagWithVersion)
        return if (matcher.find()) {
            matcher.group(1).toInt()
        } else {
            0
        }
    }

    private fun getMinorVersion(tagWithVersion: String): Int {
        val matcher = Pattern.compile(GIT_TAG_VERSION_GROUPS).matcher(tagWithVersion)
        return if (matcher.find()) {
            matcher.group(2).toInt()
        } else {
            0
        }
    }
}