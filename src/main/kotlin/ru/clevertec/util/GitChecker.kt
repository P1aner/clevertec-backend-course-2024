package ru.clevertec.util

import java.util.regex.Pattern

private const val MODIFIED = "modified:"
private const val UNTRACKED = "Untracked files:"


class GitChecker {

    fun hasTagOnCommit(): Boolean {
        return GitListener().getGitTagOnCurrentCommit().isNotBlank()
    }

    fun hasModifiedFiles(): Boolean {
        val matcher = Pattern.compile(MODIFIED).matcher(GitListener().getGitStatus())
        return matcher.find()
    }

    fun hasUntrackedFiles(): Boolean {
        val matcher = Pattern.compile(UNTRACKED).matcher(GitListener().getGitStatus())
        return matcher.find()
    }
}