package com.sandip.quiz.utils

object Colorpicker {
    private val colors = arrayOf(
        "#2DA818",
        "#0D97FF",
        "#D1422C",
        "#C78A08",
        "#902CFF",
        "#948c41",
        "#eda061",
        "#FF291A",
        "#62fcaf",
        "#12A389",
        "#9E1910",
        "#FFCC0D",
        "#54366e",
        "#569AA3",
        "#4c6587",
        "#9E7A05",
        "#CA0DFE"
    )
    var colorindex = 0

    fun getcolor(): String {
        colorindex = (colorindex + 1) % colors.size
        return colors[colorindex]
    }
}
