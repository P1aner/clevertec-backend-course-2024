package ru.clevertec.services

private const val GIT = "git"
private const val BRANCH = "branch"
private const val TAG = "tag"
private const val FETCH = "fetch"
private const val STATUS = "status"
private const val SHOW = "show"
private const val PUSH = "push"
private const val PULL = "pull"
private const val HEAD = "HEAD"
private const val SHOW_CURRENT = "--show-current"
private const val SORT_CREATOR_DATE = "--sort=creatordate"
private const val MERGED = "--merged"
private const val POINTS_AT = "--points-at"
private const val ORIGIN = "origin"
private const val REMOTE = "remote"

class GitCommandExecutor {

    fun createGitTag(version: String) {
        executeGitCommand(TAG, version)
    }

    fun getGitBranches(): String {
        return executeGitCommand(BRANCH)
    }

    fun getGitCurrentBranch(): String {
        return executeGitCommand(BRANCH, SHOW_CURRENT)
    }

    fun getGitShow(): String {
        return executeGitCommand(SHOW)
    }

    fun getGitStatus(): String {
        return executeGitCommand(STATUS)
    }

    fun getGitRemote(): String {
        return executeGitCommand(REMOTE)
    }

    fun getGitTagsOnCurrentCommit(): String {
        return executeGitCommand(TAG, SORT_CREATOR_DATE, MERGED)
    }

    fun getGitTagOnCurrentCommit(): String {
        return executeGitCommand(TAG, POINTS_AT, HEAD)
    }

    fun runGitFetch(): String {
        return executeGitCommand(FETCH)
    }

    fun runGitPull(): String {
        return executeGitCommand(PULL)
    }

    fun runGitPush(): String {
        return executeGitCommand(PUSH, ORIGIN, HEAD)
    }

    private fun executeGitCommand(vararg param: String): String {
        val process: Process = Runtime.getRuntime().exec(arrayOf(GIT).plus(param))
        process.waitFor()
        return process.inputStream.bufferedReader().lineSequence().joinToString("\n")
    }
}