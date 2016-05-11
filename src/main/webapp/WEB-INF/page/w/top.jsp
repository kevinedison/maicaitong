<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<article class="top-poster">
    <div class="image-bg" style="background-image: url(/resources/w/images/img/bg-idx.jpg);"></div>
    <div class="poster-content">
        <div class="content-title">
            <h2>不一样的海淘体验</h2>
            <p>有带点啥,全球商品任我挑选</p>
        </div>
        <div class="bg-search">
            <input type="text" placeholder="想带点啥" class="search-input">
            <a class="search-btn">搜索</a>
            <div class="m-pop-down m-sch-lst" style="display: none;">
                <ul id="cityList"></ul>
            </div>

            <div class="m-pop-down m-sch-tags" id="hotCity" style="display: none;">
                <p class="m-tt">热卖国家</p>
                <p class="m-sch-tag">
                    <a href="http://www.nilai.com/destination/xinjiapo_xinjiapo">新加坡</a>
                    <a href="http://www.nilai.com/destination/taiwan_taibei">台北</a>
                    <a href="http://www.nilai.com/destination/taiwan_kending">垦丁</a>
                    <a href="http://www.nilai.com/destination/taiwan_taizhong">台中</a>
                    <a href="http://www.nilai.com/destination/riben_dongjing">东京</a>
                    <a href="http://www.nilai.com/destination/riben_jingdou">京都</a>
                    <a href="http://www.nilai.com/destination/riben_daban">大阪</a>
                    <a href="http://www.nilai.com/destination/riben_chongsheng">冲绳</a>
                    <a href="http://www.nilai.com/destination/hanguo_shouer">首尔</a>
                    <a href="http://www.nilai.com/destination/hanguo_jizhoudao">济州岛</a>
                    <a href="http://www.nilai.com/destination/taiguo_mangu">曼谷</a>
                    <a href="http://www.nilai.com/destination/faguo_bali">巴黎</a>
                    <a href="http://www.nilai.com/destination/deguo_bolin">柏林</a>
                    <a href="http://www.nilai.com/destination/yidali_milan">米兰</a>
                    <a href="http://www.nilai.com/destination/yidali_luoma">罗马</a>
                    <a href="http://www.nilai.com/destination/xibanya_basailuonei">巴塞罗那</a>
                </p>
            </div>
            <p class="m-pop-down m-sch-err" id="noSearch" style="display: none;">没有匹配的目的地</p>
        </div>
        <p class="content-step">
            <a href="http://www.nilai.com/recruit" target="_blank">招募海外买手 &gt;</a>
            <a href="http://www.nilai.com/security" target="_blank">买家七大保障 &gt;</a>
        </p>
    </div>
</article>

<article class="theme-index">
    <div class="grid-960">
        <div class="list-theme">
            <a href="http://www.nilai.com/activity/guide/riben" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="/images/img/category-milk.jpg"></div>
                <div class="list-item-ct">
                    <p class="item-ct-tag">大牌奶粉</p>
                </div>
            </a>
            <a href="http://www.nilai.com/activity/guide/riben" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="/images/img/caregory-cosmetics.jpg"></div>
                <div class="list-item-ct">
                    <p class="item-ct-tag">化妆品</p>
                </div>
            </a>
            <a href="http://www.nilai.com/activity/guide/riben" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="/images/img/category-bag.jpg"></div>
                <div class="list-item-ct">
                    <p class="item-ct-tag">名牌包</p>
                </div>
            </a>
            <a href="http://www.nilai.com/activity/guide/riben" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="/images/img/category-milk.jpg"></div>
                <div class="list-item-ct">
                    <p class="item-ct-tag">各国特卖</p>
                </div>
            </a>
        </div>
    </div>
</article>
<article class="country-index">
    <div class="grid-960">
        <h3 class="list-title-bg">热卖国家</h3>
        <div class="list-index">
            <a href="http://www.nilai.com/activity/guide/riben" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="/images/img/riben.jpg"></div>
                <div class="list-item-ct">
                    <p class="item-ct-tag">纯美  ·  浪漫</p>
                    <p class="item-ct-place">日本</p>
                    <p class="item-ct-info">91名当地人，228个当地服务</p>
                </div>
            </a>
            <a href="http://www.nilai.com/activity/guide/taiwan" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="/images/img/taiwan.jpg"></div>
                <div class="list-item-ct">
                    <p class="item-ct-tag">美食  ·  温泉</p>
                    <p class="item-ct-place">台湾</p>
                    <p class="item-ct-info">62名当地人，144个当地服务</p>
                </div>
            </a>
            <a href="http://www.nilai.com/activity/guide/hanguo" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="/images/img/hanguo.jpg"></div>
                <div class="list-item-ct">
                    <p class="item-ct-tag">购物  ·  游街</p>
                    <p class="item-ct-place">韩国</p>
                    <p class="item-ct-info">38名当地人，91个当地服务</p>
                </div>
            </a>
        </div>

        <div class="place-list">
            <div class="place-list-wrap" style="display:none">
                <ul>
                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/taiwan.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/taiwan" target="_blank">台湾<span>Taiwan</span></a>
                    </li>
                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/xianggang.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/xianggang" target="_blank">香港<span>Hong Kong</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/riben.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/riben" target="_blank">日本<span>Japan</span></a>
                    </li>
                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/hanguo.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/hanguo" target="_blank">韩国<span>Korea</span></a>
                    </li>
                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/taiguo.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/taiguo" target="_blank">泰国<span>Thailand</span></a>
                    </li>
                </ul>

                <ul>
                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/xinjiapo.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/xinjiapo" target="_blank">新加坡<span>Singapore</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/yidali.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/yidali" target="_blank">意大利<span>Italy</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/faguo.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/faguo" target="_blank">法国<span>France</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/xibanya.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/xibanya" target="_blank">西班牙<span>Spain</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/yingguo.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/yingguo" target="_blank">英国<span>Britain</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/deguo.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/deguo" target="_blank">德国<span>Germany</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/eluosi.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/eluosi" target="_blank">俄罗斯<span>Russia</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/jieke.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/jieke" target="_blank">捷克<span>Czech Republic</span></a>
                    </li>
                </ul>

                <ul>
                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/meiguo.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/meiguo" target="_blank">美国<span>America</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/jianada.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/jianada" target="_blank">加拿大<span>Canada</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/aodaliya.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/aodaliya" target="_blank">澳大利亚<span>Australian</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/xinxilan.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/xinxilan" target="_blank">新西兰<span>New Zealand</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/moxige.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/moxige" target="_blank">墨西哥<span>Mexico</span></a>
                    </li>

                    <li class="m-tt"><img src="http://static.nilai.com/product/images/flag/xila.jpg" class="ico-flag">
                        <a href="http://www.nilai.com/destination/xila" target="_blank">希腊<span>Greece</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="text-center"><a class="out-line-btn-blue place-list-show">查看所有国家</a></div>
    </div>
</article>

<div class="buyer-index">
    <div class="grid-960">
        <div class="buyer-list">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <div class="swiper-slide" style="background-image:url(/resources/w/images/img/promotion-family.jpg)"></div>
                    <div class="swiper-slide" style="background-image:url(/resources/w/images/img/promotion-mijing.jpg)"></div>
                </div>
                <div class="swiper-pagination swiper-pagination-white"></div>
            </div>

            <a href="http://www.nilai.com/people/18046" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="http://static.nilai.com/product/file/v12221630/images/index/18046.jpg"></div>
                <div class="list-item-ct">
                    <p class="m-name">冯伟-Wendy，新加坡</p>
                    <p class="m-info">曾是华文老师，现在是明星导游</p>
                    <p class="m-rate">21条评价</p>
                </div>
                <div class="list-item-more">
                    <p class="m-name">冯伟-Wendy，新加坡</p>
                    <p class="m-info">曾是华文老师，现在是明星导游</p>
                    <p class="m-txt">曾经的华文老师，现在的旅行达人。在新加坡生活二十余年，喜欢一切美好的事物，她会用善良和专业带你领略美丽的新加坡。</p>
                </div>
            </a>
            <a href="http://www.nilai.com/people/15013" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="http://static.nilai.com/product/file/v12221630/images/index/15013.jpg"></div>
                <div class="list-item-ct">
                    <p class="m-name">陈翰，台北</p>
                    <p class="m-info">有格调的旅行家</p>
                    <p class="m-rate">30条评价</p>
                </div>
                <div class="list-item-more">
                    <p class="m-name">陈翰，台北</p>
                    <p class="m-info">有品位的生活家，有格调的旅行家</p>
                    <p class="m-txt">精于美食、摄影、旅行的小资一族，爱生活爱旅行，想象一下坐著他的车或跟著他骑的重机骑行在美丽的台湾海岸线是一种什么样的浪漫吧。</p>
                </div>
            </a>
            <a href="http://www.nilai.com/people/50679" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="http://static.nilai.com/product/file/v12221630/images/index/50679.jpg"></div>
                <div class="list-item-ct">
                    <p class="m-name">程成，大阪</p>
                    <p class="m-info">线路规划达人</p>
                    <p class="m-rate">2条评价</p>
                </div>
                <div class="list-item-more">
                    <p class="m-name">程成，大阪</p>
                    <p class="m-info">服装业设计师、线路规划达人</p>
                    <p class="m-txt">东京、京都、大阪、首尔……去过许多地方之后，才发现最爱的依然是居住了十年的大阪府。这里可以辐射到京都、神户与奈良，也有不亚于东京的商业圈和文化底蕴。在大阪深度旅行是怎样的体验？让我来为你全方位解密。</p>
                </div>
            </a>
            <a href="http://www.nilai.com/people/145961" class="list-item" target="_blank">
                <div class="list-item-bg"><img src="http://static.nilai.com/product/file/v12221630/images/index/145961.jpg"></div>
                <div class="list-item-ct">
                    <p class="m-name">朴文勇，首尔</p>
                    <p class="m-info">旅行社创始人</p>
                    <p class="m-rate">5条评价</p>
                </div>
                <div class="list-item-more">
                    <p class="m-name">朴文勇，首尔</p>
                    <p class="m-info">旅行社创始人</p>
                    <p class="m-txt">陆毅和严屹宽都对他的服务赞不绝口！欧巴待人接物十分细致、周到、有耐心，更有全新保姆车接驾，让你在旅途中享受明星般的待遇！欧巴是韩国人，对家乡的文化有着自己的理解，更难得的是中文也很了得！</p>
                </div>
            </a>
        </div>
    </div>
</div>

<div class="video-poster">
    <h2>出发吧，世界等你来</h2>
    <p>安心旅行，在当地感受如朋友接待般的「心旅行」</p>
    <a href="javascript:void('播放视频');" class="m-btn-play j-vid-open" onclick="_hmt.push(['_trackEvent', 'index-video', 'click', 'index-video'])"></a>
</div>
<article class="share-index">
    <div class="index-tabs">
        <div class="grid-960">
            <h3 class="list-title-bg">和你分享旅行的温暖</h3>
            <div class="m-tabs-bd">
                <div class="m-tab-bd" style="display: none;">
                    <div class="m-user">
                        <img src="http://static.nilai.com/product/file/v12221630/images/index/user/mint.jpg" alt="" class="m-ava">
                        <p class="m-name">大头Mint</p>
                        <span class="m-info">与 Johnson Liu 相遇于新加坡</span>
                    </div>
                    <p class="m-txt">异常丰富的一天，对当地多民族特色的民俗聚集地来了一次深入的文化之旅。也撇开游客聚集地，带我们从拉茶到海南鸡饭，尝遍马来、印度、华人区美食。<br>私人定制般的旅行，让我看到不一样的新加坡。</p>
                </div>
                <div class="m-tab-bd" style="display: block;">
                    <div class="m-user">
                        <img src="http://static.nilai.com/product/file/v12221630/images/index/user/liwq.jpg" alt="" class="m-ava">
                        <p class="m-name">LiWQ</p>
                        <span class="m-info">与 刘超 相遇于首尔</span>
                    </div>
                    <p class="m-txt">赞赞赞 这次首尔行特别成功开心 多亏有了妹子陪伴 买东西的时候帮忙提好多 看我一个人那上重 专门帮忙送到房子里 想要吃的东西 除了炸鸡因为没开门 其他全部都吃到了 推荐的吃的也很好吃 总体来说好的没话说</p>
                </div>
                <div class="m-tab-bd" style="display: none;">
                    <div class="m-user">
                        <img src="http://static.nilai.com/product/images/20151018/b16c4d2b-4137-4d05-b7f0-14e3d7892aa9.jpg!300x300" alt="" class="m-ava">
                        <p class="m-name">小P娃～</p>
                        <span class="m-info">与 陳震宇 相遇于台北</span>
                    </div>
                    <p class="m-txt">陈先生人很nice~驾车技术先要赞一下～很稳～舒适度很好～交流毫无障碍～地导经验很丰富～国内也去过很多地方~所以可以交流的话题很多～态度好～专业度高～相处起来氛围很好～不管送机还是台北一日游～行程安排都刚刚好～以后跑其他地方都可以直接配对了～哈哈哈</p>
                </div>
            </div>
            <ul class="m-tabs-hd f-cb">
                <li class="">
                    <a href="http://www.nilai.com/people/17033" target="_blank">
                        <img src="http://static.nilai.com/product/images/20141220/3c00109d-2fc6-4846-b6ed-e8fcfb5fc1f1.jpg!300x300" alt="" class="m-ava">
                        <div class="m-ct">
                            <p class="m-name">Johnson Liu，新加坡</p>
                            <span class="m-info">新加坡的“百科全书”</span>
                        </div>
                    </a>
                </li>
                <li class="on">
                    <a href="http://www.nilai.com/people/22286" target="_blank">
                        <img src="http://static.nilai.com/product/images/20150319/adb5fff7-9150-4711-bc1c-0ae9c55323b0.jpg!300x300" alt="" class="m-ava">
                        <div class="m-ct">
                            <p class="m-name">刘超，首尔</p>
                            <span class="m-info">美食爱好者 购物达人</span>
                        </div>
                    </a>
                </li>
                <li class="">
                    <a href="http://www.nilai.com/people/22271" target="_blank">
                        <img src="http://static.nilai.com/product/images/20150318/8e737b6a-ef8b-4857-a007-5faa96f40c1d.jpg!300x300" alt="" class="m-ava">
                        <div class="m-ct">
                            <p class="m-name">陳震宇，台北</p>
                            <span class="m-info">有故事的宝马小叔</span>
                        </div>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</article>
<%@ include  file="common/js.jsp"%>
<script type="text/javascript">
    $(function () {
        var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            paginationClickable: '.swiper-pagination',
            spaceBetween: 30,
            effect: 'fade'
        });

        $('.place-list-show').click(function(evt) {
            var target = $(evt.target);
            if(!target.hasClass('open')) {
                $('.place-list-wrap').slideDown(300);
                target.addClass('open').text('收起');
            } else {
                $('.place-list-wrap').slideUp(300);
                target.removeClass('open').text('查看所有国家');
            }
        });
    });
</script>
<%@ include  file="common/footer.jsp"%>