<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>带点啥</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="/resources/wx/css/mui.min.css">
    <link rel="stylesheet" href="/resources/wx/css/app.css"/>
</head>

<body>

<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
    <aside id="offCanvasSide" class="mui-off-canvas-right filter">
        <div id="offCanvasSideScroll" class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <table>
                    <tbody>
                    <tr>
                        <th>姓名</th>
                        <td>
                            <input type="search" class="mui-input-clear buyer-name" placeholder="买手姓名">
                        </td>
                    </tr>
                    <tr>
                        <th>国家</th>
                        <td id="countryList">
                            <a href="javascript:void(0)" class="search-buyer buyer-country active" country="-1"> 全部</a>
                        </td>
                    </tr>
                    <tr>
                        <th>身份</th>
                        <td id="identifyList">
                            <a class="search-buyer buyer-type active" type="-1">全部</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="filter-btn"><button type="button" class="mui-btn mui-btn-primary">筛选</button></div>
            </div>
        </div>
    </aside>
    <div class="mui-inner-wrap">
        <header class="mui-bar mui-bar-nav">
            <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
            <h1 class="mui-title">带点啥-全部买手</h1>
            <a id="offCanvasBtn" class="mui-icon mui-action-menu mui-icon-bars mui-pull-right"></a>
        </header>
        <nav class="mui-bar mui-bar-tab bottom-nav">
            <a class="mui-tab-item top" action="/wx/top">
                <span class="mui-icon mui-icon-home"></span>
                <span class="mui-tab-label">首页</span>
            </a>
            <a class="mui-tab-item category" action="/wx/needs">
                <span class="mui-icon mui-icon-bars"></span>
                <span class="mui-tab-label">求购</span>
            </a>
            <a class="mui-tab-item buyer mui-active" action="/wx/buyers">
                <span class="mui-icon mui-icon-contact"></span>
                <span class="mui-tab-label">买手</span>
            </a>
            <a class="mui-tab-item messages" action="/wx/messages">
                <span class="mui-icon mui-icon-chatbubble"></span>
                <span class="mui-tab-label">消息</span>
            </a>
            <a class="mui-tab-item account" action="/wx/account">
                <span class="mui-icon mui-icon-gear"></span>
                <span class="mui-tab-label">我的</span>
            </a>
        </nav>
        <div id="offCanvasContentScroll"  class="mui-content mui-scroll-wrapper">
            <div class="mui-scroll">
                <ul class="mui-table-view mui-grid-view buyer-wrap" style="margin-top: -15px;"></ul>
                <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
            </div>
        </div>
        <div class="mui-off-canvas-backdrop"></div>
    </div>
</div>
<%@ include file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type = "text/javascript">
    var filterBuyer = function() {
        var page = $(".paging-bar").attr('page');
        if(!page || page < 1) {
            page = 0;
        }
        page++;

        var url =  '/buyer/list?pageSize='+daidian.config.pageSize+'&page='+page+'&rmd='+new Date().getTime();
        var countryDom = $('.buyer-country.active');
        if(countryDom) {
            var country = countryDom.attr('country');
            if(country && country != -1) {
                url = url + "&country=" + country;
            }
        }

        var typeDom = $('.buyer-type.active');
        if(typeDom) {
            var type = typeDom.attr('type');
            if(type && type != -1) {
                url = url + "&identity=" + type;
            }
        }

        var name = $('.buyer-name').val();
        if(name) {
            url = url + "&real_name=" + name;
        }

        url = url + "&rmd=" + new Date().getTime();

        var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
        $('.buyer-wrap').append(loadHtml);
        $.ajax({
            type    : 'get',
            url     :  url,
            success : function(data){
                $('.buyer-wrap .mui-loading').remove();
                if(data && data.code == '000000') {
                    if(!data.data.count || data.data.count == 0) {
                        $(".paging-bar").hide();
                        $('.buyer-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有买手</li>');
                        return;
                    }
                    if((page * daidian.config.pageSize) < data.data.count) {
                        $(".paging-bar").show().attr('page',page);
                    } else {
                        $(".paging-bar").hide();
                    }

                    var buyers = data.data.data || [];
                    var buyerHtml =[];
                    buyers.forEach(function(b) {
                        var serviceType = JSON.parse(b.servicetype || '{}');
                        buyerHtml.push('<li class="mui-table-view-cell" style="border-top: 1px solid #e8e8e8;display: block;" userId="'+ b.userId +'">');
                        if(daidian.session.user && daidian.session.user.userId == b.userId && daidian.session.user.identityAuth == 1){
                        	buyerHtml.push('               <a action="/wx/account-service">');
	                    }else{
	                    	buyerHtml.push('<a action=/wx/user?id='+b.userId+' class="mui-navigate-right">');
	                    }
                        buyerHtml.push('    <div class="mui-slider-cell">');
                        buyerHtml.push('        <div class="oa-contact-cell mui-table">');
                        buyerHtml.push('            <div class="oa-contact-avatar mui-table-cell">');
                        buyerHtml.push('                <img src="'+ getPictureUrl(b.avatar,2) +'" />');
                        buyerHtml.push('            </div>');
                        buyerHtml.push('            <div class="oa-contact-content mui-table-cell">');
                        buyerHtml.push('                <div class="mui-clearfix">');
                        buyerHtml.push('                    <h4 class="oa-contact-name mui-pull-left">'+ (b.nickName)+'</h4>');
                        buyerHtml.push('                    <span class="oa-contact-position mui-h6 mui-pull-left">（'+ ((dataconfig.countrys[b.country] || {}).name || '') +' '+(daidian.config.identityType[b.identity] || b.identity) +'）</span>');
                        buyerHtml.push('                </div>');
                        
                        buyerHtml.push('	<div class="mui-table oa-contact-business" style="margin-bottom:5px;">');
                        buyerHtml.push('	    <h3>营</h3>');
            			buyerHtml.push('		<div class="business-list">');
            			
            			var type = JSON.parse(b.servicetype || '[]');
            			(type || []).forEach(function(cate) {
            				buyerHtml.push('      <span class="service-type">'+(daidian.config.serviceType[cate] || cate)+'</span>');
            			}); 
            			buyerHtml.push('		</div>');
            			buyerHtml.push('	</div>');
            			
                        buyerHtml.push('            </div>');
                        buyerHtml.push('        </div>');
                        buyerHtml.push('    </div>');
                        
	        	   		if(b.introduce) {
	        	   			buyerHtml.push('        <p class="mui-ellipsis-2" style="text-align: left;padding-top: 5px;">'+ (b.introduce||'') +'</p>');
	        	   		}
	        	   		
                        buyerHtml.push('</a>');
                        buyerHtml.push('</li>');
                    });
                    $('.buyer-wrap').append(buyerHtml.join(''));
                } else {
                    mui.toast('加载买手失败，请刷新页面重试');
                }
            },
            error : function() {
                mui.toast('加载买手失败，请刷新页面重试');
            }
        });
    };

   var initSearchFilter = function() {
	   var countryHtml = [];
	   var countrys = dataconfig.countrys||{};
       for(var c in countrys) {
         var country = countrys[c] || {};
         countryHtml.push('<a class="search-buyer buyer-country" country="'+c+'">'+country.name+'</a>');
       }
       $('#countryList').append(countryHtml.join(''));
       
       var identifyHtml = [];
	   var identifys = daidian.config.identityType||{};
       for(var i in identifys) {
         identifyHtml.push('<a class="search-buyer buyer-type" type="'+i+'">'+identifys[i] || ''+'</a>');
       }
       $('#identifyList').append(identifyHtml.join(''));
    };
    
    $(function() {
    	initSearchFilter();
    	
        mui('#offCanvasSideScroll').scroll();
        mui('#offCanvasContentScroll').scroll();

        $('body').on('tap','.search-buyer',function(evt){
            var target = $(evt.target);
            if(target.hasClass('search-buyer')) {
                if(target.hasClass('buyer-country')) {
                    $('.buyer-country').removeClass('active');
                }
                if(target.hasClass('buyer-type')) {
                    $('.buyer-type').removeClass('active');
                }
                target.addClass('active');
            }
        });


        $('body').on('tap','#offCanvasBtn',function(evt){
            mui('#offCanvasWrapper').offCanvas('show');
        });

        $('.filter-btn button').click(function(evt){
            $('.buyer-wrap').html('');
            $('.paging-bar').attr('page',0).hide().trigger('tap');
            mui('#offCanvasWrapper').offCanvas('close');
        });

        $('body').on('tap','.paging-bar',function(evt){
            filterBuyer();
        });

        $('.paging-bar').trigger('tap');
    });
</script>
<%@ include  file="common/footer.jsp"%>