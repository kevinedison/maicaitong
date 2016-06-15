<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>卖菜通</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="/resources/wx/css/mui.min.css">
    <link rel="stylesheet" href="/resources/wx/css/app.css"/>
</head>
<body>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-</h1>
</header>
<div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view service-row">
            <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                <div class="panel-title">列表</div>
            </li>
            <div class="service-wrap"></div>
        </ul>
        <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	var filterService = function(category) {
	    var page = $(".paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var url =  '/service/list?pageSize='+daidian.config.pageSize+'&page='+page+'&category='+category;
	    url = url + "&rmd=" + new Date().getTime();
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.service-wrap').append(loadHtml);
	    $.ajax({
	        type    : 'get',
	        url     :  url,
	        success : function(data){
	            $('.mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    $(".paging-bar").hide();
	                    $('.service-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有服务</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $(".paging-bar").show().attr('page',page);
	                } else {
	                    $(".paging-bar").hide();
	                }
	
	                var services = data.data.data || [];
	                var serviceHtml =[];
	                services.forEach(function(s) {
	                    var serviceUser = s.userInfo || {};
	                    serviceHtml.push('<li class="mui-table-view-cell mui-media mui-col-xs-12">');
	                    
	                    if(daidian.session.user && daidian.session.user.userId == serviceUser.userId && daidian.session.user.identityAuth == 1){
	                    	serviceHtml.push('		 <a action="/wx/account-service">');
	                    }else{
	                    	serviceHtml.push('<a action="/wx/user?id='+serviceUser.userId+'">');
	                    }
	                    
	                    
	                    serviceHtml.push('    <div class="service-buyer">');
	                    serviceHtml.push('      <span class="user-img"><img src="'+ getPictureUrl(serviceUser.avatar,2) +'"></span>');
	                    serviceHtml.push('      <span class="user-info">');
	                    serviceHtml.push('          <div class="user-name">'+(serviceUser.nickName)+'</div>');
	                    serviceHtml.push('          <div class="user-location">'+((dataconfig.countrys[serviceUser.country] || {}).name || '')+'</div>');
	                    serviceHtml.push('      </span>');
	                    serviceHtml.push('      <button class="mui-btn mui-icon mui-icon-chat" userId='+serviceUser.userId+'></button>');          
	                    serviceHtml.push('    </div>');
	                    serviceHtml.push('</a>');
	
	                    serviceHtml.push('<a action="/wx/service?id='+ s.id+'">');
	                    serviceHtml.push('    <img class="mui-media-object service-cover mui-pull-left" src="'+ getPictureUrl(s.cover,'1','list')+'">');
	                    serviceHtml.push('    <div class="mui-media-body">');
	                    serviceHtml.push('      <div class="service-title mui-ellipsis">'+ s.title+'</div>');
	                    serviceHtml.push('      <p class="service-desc mui-ellipsis-2">'+ s.description+'</p>');
	                    serviceHtml.push('      <div class="service-bar">');
	                    serviceHtml.push('        <button class="mui-btn-danger mui-btn-outlined service-price">¥ '+ s.price+'</button>');
	                    serviceHtml.push('        <div class="service-number">');
	                    serviceHtml.push('          <button class="mui-btn mui-btn-outlined mui-icon mui-icon-flag" type="service" serviceId="'+s.id+'"> '+ s.ordercount +' </button>');
	                    serviceHtml.push('        </div>');
	                    serviceHtml.push('      </div>');
	                    serviceHtml.push('    </div>');
	                    serviceHtml.push('</a>');
	
	                    serviceHtml.push('</li>');
	                });
	                $('.service-wrap').append(serviceHtml.join(''));
	            } else {
	                mui.toast('加载服务失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载服务失败，请刷新页面重试');
	        }
	    });
	};
	

	$(function() {
		var searchType = getQueryString('type') || 'milk';
		var category = daidian.categorys[searchType];
 	    $('.mui-title').text('卖菜通-'+category.name);
 	    
 	    mui('#offCanvasContentScroll').scroll();
 	    
	    $('body').on('tap','.paging-bar',function(evt){
	        evt.preventDefault();
	        filterService(searchType);
	    });
	
	    $('.paging-bar').trigger('tap');
	});
</script>
<%@ include  file="common/footer.jsp"%>