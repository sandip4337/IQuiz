package com.sandip.quiz.model

import com.sandip.quiz.model.ques_element

data class quiz(
    var id: String = "",
    var title: String = "",
    var Question: MutableMap<String, ques_element> = mutableMapOf()
)
