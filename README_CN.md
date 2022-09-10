# BannerViewPager

[![License](https://img.shields.io/github/license/zhpanvip/BannerViewPager)](https://github.com/zhpanvip/BannerViewPager/blob/master/LICENSE)
![MinSdk](https://img.shields.io/badge/API-19%2B-brightgreen)
[![JitPack](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BannerViewPager-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7961)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ViewPagerIndicator-brightgreen.svg?style=flat)](https://github.com/zhpanvip/viewpagerindicator)
[![Stars](https://img.shields.io/github/stars/zhpanvip/BannerViewPager)](https://github.com/zhpanvip/BannerViewPager/stargazers)


## [English](https://github.com/zhpanvip/BannerViewPager) | 中文


> 腾讯视频、QQ音乐、酷狗音乐、支付宝、天猫、淘宝、优酷视频、喜马拉雅、网易云音乐、哔哩哔哩、全民K歌等App的Banner样式都可以通过BannerViewPager实现哦！

## 使用方法

## [1.快速开始](https://github.com/zhpanvip/BannerViewPager/wiki/06.快速开始)

## [2.API文档](https://github.com/zhpanvip/BannerViewPager/wiki/07.API文档)

## [3.多类型Item View](https://github.com/zhpanvip/BannerViewPager/wiki/08.多类型Item)

## [4.使用ViewBinding与DataBinding](https://github.com/zhpanvip/BannerViewPager/wiki/09.使用ViewBinding与DataBinding)

## [5.自定义Indicator](https://github.com/zhpanvip/BannerViewPager/wiki/10.自定义Indicator)


## 效果预览

 ### [点击或扫描二维码下载apk](https://www.pgyer.com/bannerviewpager)
 由于蒲公英平台限制，每个版本最多只能下载10次。其他版本下载链接：[[Download V3.5.0](https://www.pgyer.com/6c1abffc266a799fee559f1edc2cf6ff),[Download V3.4.0](https://www.pgyer.com/8c1deb4b38ea81d9c62e639dcdeeba39)

![扫描下载Demo](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/qrcode.png)


### 1.PageStyle

[一屏多页示例](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/banner/fragment/PageFragment.kt)

| MULTI_PAGE |MULTI_PAGE_SCALE | MULTI_PAGE_OVERLAP |
|--|--|--|
| ![MULTI_PAGE](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/page_style_multi.gif) |![MULTI_PAGE](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/page_style_multi_scale.gif) |![MULTI_PAGE](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/page_style_multi_overlay.gif) |

### 更多页面样式

使用setPageStyle() 和 setRevealWidth()方法可以实现多种页面样式。

| Style 1 | Style 2 | Style 3 |
|--|--|--|
| ![MULTI_PAGE1](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/page_style_1.gif) |![MULTI_PAGE2](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/page_style_2.gif) |![MULTI_PAGE3](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/page_style_3.gif) |

### 2.[Indicator](https://github.com/zhpanvip/viewpagerindicator)

目前指示器已经从BannerViewPager中分离出来，现在单独为一个仓库，新的仓库地址为[ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)，你可以点击连接了解更多关于[ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)的信息。

#### (1)IndicatorStyle 与 IndicatorSlideMode

[Indicator使用示例](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/banner/fragment/IndicatorFragment.java)

BannerViewPager目前已支持三种IndicatorViewStyle,以及五种IndicatorSlideMode,分别如下：

| 属性 | CIRCLE | DASH | ROUND_RECT |
|--|--|--|--|
| NORMAL| ![CIRCLE_NORMAL](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/slide_circle_normal.gif) | ![DASH_NORMAL](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_dash_normal.gif) | ![ROUND_RECT_NORMAL](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_round_rect_normal.gif) |
| SMOOTH| ![CIRCLE_SMOOTH](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/slide_circle_smooth.gif) | ![DASH_SMOOTH](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_dash_smooth.gif) | ![ROUND_RECT_SMOOTH](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_round_rect_smooth.gif) |
| WORM| ![CIRCLE_WORM](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/slide_circle_worm.gif) | ![DASH_WORM](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_dash_worm.gif) | ![ROUND_WORM](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_round_rect_worm.gif) |
| COLOR| ![CIRCLE_COLOR](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/slide_circle_color.gif) | ![DASH_COLOR](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_dash_color.gif) | ![ROUND_COLOR](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_round_rect_color.gif) |
| SCALE| ![CIRCLE_SCALE](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/slide_circle_scale.gif) | ![DASH_SCALE](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_dash_scale.gif) | ![ROUND_SCALE](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/indicator/style_round_rect_scale.gif) |

#### (2)Custom Indicator

同时BannerViewPager还提供了自定义IndicatorView的功能。只要继承BaseIndicatorView或者实现IIndicator接口，并重写相应方法，就可以为所欲为的打造任意的Indicator了。

[自定义Indicator示例](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/banner/fragment/OthersFragment.java)

| Figure Indicator | Drawable Indicator | Indicator below of Banner |
|--|--|--|
| ![CIRCLE](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/style_custum.gif) | ![DASH](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/style_custom2.gif) | ![NORMAL](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/banner/style_custom1.gif) |



## 联系

 **如果使用中有任何问题可以加群交流:**   <a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=yHQU7OuoIazbu8vXGt1wC37RsPzhnR61&jump_from=webapi"><img border="0" 
  src="https://pub.idqqimg.com/wpa/images/group.png" alt="QQ群60902509" title="QQ群60902509">
 </a>
| QQ群 | 微信群 |
|--|--|
| ![QQ Group](https://cdn.jsdelivr.net/gh/zhpanvip/images/project/group/qq_group.png) |  ![WeChat](https://github.com/zhpanvip/images/blob/master/project/group/wechat.png) |


## 感谢

[玩Android](https://wanandroid.com/)

[finite-cover-flow](https://github.com/KoderLabs/finite-cover-flow)

[zguop-banner](https://github.com/zguop/banner)

License
-------

    Copyright 2017-2020 zhpanvip

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.






