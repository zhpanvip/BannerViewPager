package com.example.zhpan.circleviewpager.bean;

import com.example.zhpan.circleviewpager.net.BannerData;

import java.util.List;

public class DataWrapper {

    private List<ArticleWrapper.Article> articleList;

    private List<BannerData> dataBeanList;

    public DataWrapper(List<ArticleWrapper.Article> articleList, List<BannerData> dataBeanList) {
        this.articleList = articleList;
        this.dataBeanList = dataBeanList;
    }

    public List<BannerData> getDataBeanList() {
        return dataBeanList;
    }

    public List<ArticleWrapper.Article> getArticleList() {
        return articleList;
    }

}
