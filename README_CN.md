# BannerViewPager

[![License](https://img.shields.io/github/license/zhpanvip/BannerViewPager)](https://github.com/zhpanvip/BannerViewPager/blob/master/LICENSE)
![MinSdk](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
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

 ### [点击或扫描二维码下载apk](https://oss.pgyer.com/897714a57f869b81c9fbf06c695c4e32.apk?auth_key=1612114117-9e090627d79640f7db877dee2bf9c4f5-0-2aac46487fb89208426d5c48b6eb83b5&response-content-disposition=attachment%3B+filename%3Dapp-release.apk) 
 由于蒲公英平台限制，每个版本最多只能下载10次。其他版本下载链接：[V3.4.0下载](https://oss.pgyer.com/6fcaeef41306bfa593aa4ad3dcc03302.apk?auth_key=1612113481-a4743e9042a138f4e690a68a11418570-0-44800e0e340193fa3021dbfb847521a9&response-content-disposition=attachment%3B+filename%3Dapp-release.apk),[V3.1.4下载](https://oss.pgyer.com/d679c49e67f8d17a34bfbf8462a8ad7e.apk?auth_key=1612113481-43f94663c061279f6e57b1166686eb68-0-238007a8ecbb6c7248ce5df9d767dcd1&response-content-disposition=attachment%3B+filename%3Dapp-release.apk)

![扫描下载Demo](https://gitee.com/zhpanvip/images/raw/master/project/banner/qrcode.png)


### 1.PageStyle

[一屏多页Demo](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/PageFragment.java)

| MULTI_PAGE |MULTI_PAGE_SCALE | MULTI_PAGE_OVERLAP |
|--|--|--|
| ![MULTI_PAGE](https://gitee.com/zhpanvip/images/raw/master/project/banner/page_style_multi.gif) |![MULTI_PAGE](https://gitee.com/zhpanvip/images/raw/master/project/banner/page_style_multi_scale.gif) |![MULTI_PAGE](https://gitee.com/zhpanvip/images/raw/master/project/banner/page_style_multi_overlay.gif) |

### 2.[Indicator](https://github.com/zhpanvip/viewpagerindicator)

目前指示器已经从BannerViewPager中分离出来，现在单独为一个仓库，新的仓库地址为[ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)，你可以点击连接了解更多关于[ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)的信息。

#### (1)IndicatorStyle 与 IndicatorSlideMode

BannerViewPager目前已支持三种IndicatorViewStyle,以及五种IndicatorSlideMode,分别如下：

| 属性 | CIRCLE | DASH | ROUND_RECT |
|--|--|--|--|
| NORMAL| ![CIRCLE_NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_normal.gif) | ![DASH_NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_normal.gif) | ![ROUND_RECT_NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_normal.gif) |
| SMOOTH| ![CIRCLE_SMOOTH](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_smooth.gif) | ![DASH_SMOOTH](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_smooth.gif) | ![ROUND_RECT_SMOOTH](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_smooth.gif) |
| WORM| ![CIRCLE_WORM](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_worm.gif) | ![DASH_WORM](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_worm.gif) | ![ROUND_WORM](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_worm.gif) |
| COLOR| ![CIRCLE_COLOR](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_color.gif) | ![DASH_COLOR](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_color.gif) | ![ROUND_COLOR](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_color.gif) |
| SCALE| ![CIRCLE_SCALE](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_scale.gif) | ![DASH_SCALE](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_scale.gif) | ![ROUND_SCALE](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_scale.gif) |

#### (2)Custom Indicator

同时BannerViewPager还提供了自定义IndicatorView的功能。只要继承BaseIndicatorView或者实现IIndicator接口，并重写相应方法，就可以为所欲为的打造任意的Indicator了。

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/OthersFragment.java)

| Figure Indicator | Drawable Indicator | Indicator below of Banner |
|--|--|--|
| ![CIRCLE](https://gitee.com/zhpanvip/images/raw/master/project/banner/style_custum.gif) | ![DASH](https://gitee.com/zhpanvip/images/raw/master/project/banner/style_custom2.gif) | ![NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/banner/style_custom1.gif) |



## 有问题可以扫码加QQ群交流

 ![QQ交流群60902509](https://gitee.com/zhpanvip/images/raw/master/project/group/qq_group.png)



## <span id="Sponsor"> 赞赏 </span>


**如果您觉得BVP库还不错，您可以对BVP打赏哦，您的支持将是我持续维护的动力。**

| 支付宝 | 微信支付 |
|--|--|
| ![NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/pay/pay_alipay.jpg) |  ![SMOOTH](https://gitee.com/zhpanvip/images/raw/master/project/pay/pay_wechat.png) |


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






