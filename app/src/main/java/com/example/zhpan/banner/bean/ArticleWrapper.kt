package com.example.zhpan.banner.bean

import com.example.zhpan.banner.net.BannerData

class ArticleWrapper {

    var datas: List<Article>? = null

    class Article {
        var title: String? = null

        var link: String? = null

        var author: String? = null

        var publishTime: Long = 0

        var type: Int = 0

        var bannerData: List<BannerData>? = null
    }
}
