package com.example.zhpan.circleviewpager.bean

class ArticleWrapper {

    var datas: List<Article>? = null

    class Article {
        var title: String? = null

        var link: String? = null

        var author: String? = null

        var publishTime: Long = 0

        var type: Int = 0

        var pagers: List<Int>? = null
    }


}
