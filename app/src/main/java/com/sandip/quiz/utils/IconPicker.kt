package com.sandip.quiz.utils

import com.sandip.quiz.R


object IconPicker {
    private val Icons = arrayOf(
        R.drawable.book,
        R.drawable.brain,
        R.drawable.pic7,
        R.drawable.exam,
        R.drawable.pic1,
        R.drawable.quiz,
        R.drawable.pic3,
        R.drawable.pic4,
        R.drawable.pic5,
        R.drawable.pic2,
        R.drawable.pic8
    )
    var iconindex = 0

    fun getIcon(): Int {
        iconindex = (iconindex + 1) % Icons.size
        return Icons[iconindex]
    }
}