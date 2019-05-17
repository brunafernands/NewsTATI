package br.edu.ifpr.stiehl.todolist.entidades

data class NewsResult (
    var status: Long,
    var totalResults: Long,
    var articles: List<Article>
)