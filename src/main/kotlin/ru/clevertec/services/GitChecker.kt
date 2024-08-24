package ru.clevertec.services

import java.util.regex.Pattern

private const val MODIFIED = "modified:"
private const val UNTRACKED = "Untracked files:"
private const val INSTALLED = "not a git repository"


class GitChecker {

    fun isGitInstalled(): Boolean {
        val matcher = Pattern.compile(INSTALLED).matcher(GitCommandExecutor().getGitStatus())
        return !matcher.find()
    }

    fun hasTagOnCommit(): Boolean {
        return GitCommandExecutor().getGitTagOnCurrentCommit().isNotBlank()
    }

    fun hasModifiedFiles(): Boolean {
        val matcher = Pattern.compile(MODIFIED).matcher(GitCommandExecutor().getGitStatus())
        return matcher.find()
    }

    fun hasUntrackedFiles(): Boolean {
        val matcher = Pattern.compile(UNTRACKED).matcher(GitCommandExecutor().getGitStatus())
        return matcher.find()
    }
}