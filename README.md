# BannerViewPager

[![License](https://img.shields.io/github/license/zhpanvip/BannerViewPager)](https://github.com/zhpanvip/BannerViewPager/blob/master/LICENSE)
![MinSdk](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
[![JitPack](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BannerViewPager-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7961)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ViewPagerIndicator-brightgreen.svg?style=flat)](https://github.com/zhpanvip/viewpagerindicator)
[![Stars](https://img.shields.io/github/stars/zhpanvip/BannerViewPager)](https://github.com/zhpanvip/BannerViewPager/stargazers)


## English | [中文](https://github.com/zhpanvip/BannerViewPager/blob/master/README_CN.md)

> Tencent Video,QQ Music,KuGou,AliPay,Tmall,TaoBao,YouKu,Himalaya,NetEase Music,Bilibili ect. All of above App's Banner can be implements By BannerViewPager.


## Usage

## [1.Quick Starts](https://github.com/zhpanvip/BannerViewPager/wiki/01.Quick-Starts)

## [2.Document API](https://github.com/zhpanvip/BannerViewPager/wiki/02.Docment)

## [3.Multiple View Types](https://github.com/zhpanvip/BannerViewPager/wiki/03.Multiple-View-Types)

## [4.Use ViewBinding And DataBinding](https://github.com/zhpanvip/BannerViewPager/wiki/04.Use-ViewBinding-And-DataBinding)

## [5.Custom IndicatorView](https://github.com/zhpanvip/BannerViewPager/wiki/05.Custom-IndicatorView)


## Preview

 ### [Click here or scan the QR code to download demo apk](https://www.pgyer.com/bannerviewpager).
 
 Due to the download platform restrictions, each version can only be downloaded up to 10 times. Download links for other versions: [Download V3.4.0](https://www.pgyer.com/8c1deb4b38ea81d9c62e639dcdeeba39),[Download V3.1.4](https://www.pgyer.com/1206755b946b8b7d537b4c7c954234b1)

![QRCode](https://gitee.com/zhpanvip/images/raw/master/project/banner/qrcode.png)


### 1.PageStyle

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/banner/fragment/PageFragment.java)

| MULTI_PAGE |MULTI_PAGE_SCALE | MULTI_PAGE_OVERLAP |
|--|--|--|
| ![MULTI_PAGE](https://gitee.com/zhpanvip/images/raw/master/project/banner/page_style_multi.gif) |![MULTI_PAGE](https://gitee.com/zhpanvip/images/raw/master/project/banner/page_style_multi_scale.gif) |![MULTI_PAGE](https://gitee.com/zhpanvip/images/raw/master/project/banner/page_style_multi_overlay.gif) |

### [2.Indicator](https://github.com/zhpanvip/viewpagerindicator)

The Indicator library was split from BannerViewPager,the new repo is [ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)，Click the link to see more information about [ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)

#### (1)setIndicatorStyle and setIndicatorSlideMode

BannerViewPager supports three Indicator Styles and five Indicator Slide mode now.

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/banner/fragment/IndicatorFragment.java)

| Attrs | CIRCLE | DASH | ROUND_RECT |
|--|--|--|--|
| NORMAL| ![CIRCLE_NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_normal.gif) | ![DASH_NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_normal.gif) | ![ROUND_RECT_NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_normal.gif) |
| SMOOTH| ![CIRCLE_SMOOTH](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_smooth.gif) | ![DASH_SMOOTH](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_smooth.gif) | ![ROUND_RECT_SMOOTH](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_smooth.gif) |
| WORM| ![CIRCLE_WORM](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_worm.gif) | ![DASH_WORM](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_worm.gif) | ![ROUND_WORM](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_worm.gif) |
| COLOR| ![CIRCLE_COLOR](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_color.gif) | ![DASH_COLOR](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_color.gif) | ![ROUND_COLOR](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_color.gif) |
| SCALE| ![CIRCLE_SCALE](https://gitee.com/zhpanvip/images/raw/master/project/indicator/slide_circle_scale.gif) | ![DASH_SCALE](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_dash_scale.gif) | ![ROUND_SCALE](https://gitee.com/zhpanvip/images/raw/master/project/indicator/style_round_rect_scale.gif) |
#### (2)Custom Indicator

It's also support to custom indicator style,just need extends BaseIndicatorView or implement the IIndicator and override methods, then you can draw Indicators for whatever you want.

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/banner/fragment/OthersFragment.java)

| Figure Indicator | Drawable Indicator | Indicator below of Banner |
|--|--|--|
| ![CIRCLE](https://gitee.com/zhpanvip/images/raw/master/project/banner/style_custum.gif) | ![DASH](https://gitee.com/zhpanvip/images/raw/master/project/banner/style_custom2.gif) | ![NORMAL](https://gitee.com/zhpanvip/images/raw/master/project/banner/style_custom1.gif) |


## Contact

 **If you have any question regard to BannerViewPager, please scan the QR code and join the QQ group to communicate.**
 
![QQ交流群60902509](https://gitee.com/zhpanvip/images/raw/master/project/group/qq_group.png)

## <span id="Sponsor"> Donate </span>

**如果您觉得BVP库还不错，您可以对BVP打赏哦，您的支持将是我持续维护的动力。**

| AliPay | WeChat |
|--|--|
| ![AliPay](https://gitee.com/zhpanvip/images/raw/master/project/pay/pay_alipay.jpg) |  ![WeChat](https://gitee.com/zhpanvip/images/raw/master/project/pay/pay_wechat.png) |


## Thanks

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






