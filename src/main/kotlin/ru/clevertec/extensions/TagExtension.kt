package ru.clevertec.extensions

interface TagExtension {

    var isGitInstalled: Boolean
    var hasTagOnCommit: Boolean
    var tagOnCommit: String
}