package com.example.zhpan.circleviewpager.bean;

import java.util.List;

public class ArticleWrapper {

    private List<Article> datas;

    public List<Article> getDatas() {
        return datas;
    }

    public void setDatas(List<Article> datas) {
        this.datas = datas;
    }

    public static class Article {
        private String title;

        private String link;

        private String author;

        private long publishTime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }
    }


}
