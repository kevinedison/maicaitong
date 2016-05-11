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
    <link rel="stylesheet" type="text/css" href="/resources/wx/css/app.css"/>
</head>
<body>

<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
    <aside id="offCanvasSide" class="mui-off-canvas-right order-form">
        <div id="offCanvasSideScroll" class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                        <div class="panel-title">订单信息</div>
                    </li>
                    
                    <div class="service-price-detail" style="display:none">
	                    <li class="mui-table-view-cell">
	                        <div class="mui-pull-left">原价</div>
	                        <div class="mui-pull-right origin-price">¥ </div>
	                    </li>
	                     <li class="mui-table-view-cell">
	                        <div class="mui-pull-left">直邮费</div>
	                        <div class="mui-pull-right post-price">¥ </div>
	                    </li>
	                     <li class="mui-table-view-cell">
	                        <div class="mui-pull-left">服务费</div>
	                        <div class="mui-pull-right single-service-price">¥ </div>
	                    </li>
                    </div>
                    
                    <li class="mui-table-view-cell">
                        <div class="mui-pull-left">出售单价</div>
                        <div class="mui-pull-right price-text service-price">¥ </div>
                    </li>
                    <li class="mui-table-view-cell">
                        <div class="mui-pull-left">
                            数量
                        </div>
                        <div class="mui-numbox mui-pull-right" data-numbox-min='1' data-numbox-max='99999' style="width: 150px;">
                            <button class="mui-btn mui-btn-numbox-minus" type="button">-</button>
                            <input class="mui-input-numbox order-number" type="number" max=9999 min=1 maxlength="4"/>
                            <button class="mui-btn mui-btn-numbox-plus" type="button">+</button>
                        </div>
                    </li>
                    <li class="mui-table-view-cell">
                        <div class="mui-pull-right">
                           <div class="total-price">¥</div>
                           <div class="total-price-detail text-center"></div>
                        </div>
                    </li>
                </ul>

                <a class="order-submit"><span>提交订单</span></a>
            </div>
        </div>
    </aside>
    <div class="mui-inner-wrap">
        <header class="mui-bar mui-bar-nav">
            <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
            <h1 class="mui-title">带点啥-</h1>
            <a action="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
        </header>

        <nav class="mui-bar mui-bar-tab service-nav">
            <a class="mui-tab-item favorite">
                <span class="mui-tab-label">收藏</span>
            </a>
            <a class="mui-tab-item contact">
                <span class="mui-tab-label">咨询</span>
            </a>
            <a class="mui-tab-item booking">
                <span class="mui-tab-label">预定</span>
            </a>
        </nav>
        <div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper">
            <div class="mui-scroll">
                <div class="service-info">
                   <img class="mui-media-object" style="min-height:300px;">
                </div>
          
                <div class="dividing"></div>
                <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
                    <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                        <div class="panel-title">买手信息</div>
                    </li>
                    <li class="mui-table-view-cell service-buyer-info" style="min-height:150px;"></li>
                </ul>

                <div class="dividing"></div>
                <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed comment-wrap">
                    <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                        <div class="panel-title">评论列表</div>
                    </li>
                </ul>
                <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
            </div>
        </div>
        <div class="mui-off-canvas-backdrop"></div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script src="/resources/wx/js/mui.zoom.js"></script>
<script src="/resources/wx/js/mui.previewimage.js"></script>
<script type="text/javascript">
	var loadAndRenderUserComment = function(serviceId){
	    var page = 0;
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.comment-wrap').append(loadHtml);
	    $.ajax({
	        url     : '/comment/list?serviceId='+serviceId+'&pageSize='+daidian.config.pageSize+'&page='+page,
	        success : function(data){
	            $('.comment-wrap .mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data || !data.data.count || data.data.count == 0) {
	                    $('.comment-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有评论</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $(".paging-bar").show().attr('page',page);
	                } else {
	                    $(".paging-bar").hide();
	                }
	
	                var comments = data.data.data || [];
	                var commentHtml = [];
	                comments.forEach(function(curData) {
	                	var user = curData.user || {};
	                	var service = curData.serviceInfo || {};
	                    commentHtml.push('<li class="mui-table-view-cell comment-info">');
	                    commentHtml.push('    <div class="mui-table">');
	                    commentHtml.push('      <div class="mui-table-cell mui-col-xs-2 mui-text-left">');
	                    commentHtml.push('         <div class="user-img"><img src="'+getPictureUrl(user.avatar,2)+'" /></div>');
	                    commentHtml.push('      </div>');
	                    commentHtml.push('      <div class="mui-table-cell mui-col-xs-7">');
	                    commentHtml.push('          <h4 class="comment-rate">'+getRate(curData.rate)+'</h4>');
	                    commentHtml.push('          <h5 class="comment-user">'+(user.nickName)+'</h5>');
	                    commentHtml.push('      </div>');
	                    commentHtml.push('      <div class="mui-table-cell mui-col-xs-3 mui-text-right">');
	                    commentHtml.push('            <span>'+new Date(curData.operateTime).format('MM-dd')+'</span>');
	                    commentHtml.push('     </div>');
	                    commentHtml.push('   </div> ');
	                    commentHtml.push('   <div class="mui-table"> ');
	                    commentHtml.push('     <div class="mui-table-cell mui-col-xs-12">');
	                    commentHtml.push('          <p class="mui-h6">'+curData.comment+'</p>');
	                    
	                    var pictures = JSON.parse((curData.pictures || '[]')) || [];
	                    commentHtml.push('<div class="comment-image-list">');
	                    pictures.forEach(function(picture){
	                    	commentHtml.push('   <img class="mui-col-xs-3" src="'+getPictureUrl(picture,1,'list')+'"/>');
	                    });
	                    commentHtml.push('</div>');
	                    
	                    commentHtml.push('     </div>');
	                    commentHtml.push('   </div>');
	                    commentHtml.push('   <div class="mui-table">');
	                    commentHtml.push('      <div class="mui-table-cell mui-col-xs-12 mui-text-right service-title">');
	                    commentHtml.push('          <a action="/wx/service?id='+service.serviceId+'" class="mui-h6">'+service.title+'</a>');
	                    commentHtml.push('      </div>');
	                    commentHtml.push('   </div>');
	                });
	                $('.comment-wrap').append(commentHtml.join(''));
	            } else {
	                mui.toast('加载用户评论失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载用户评论失败，请刷新页面重试');
	        }
	    });
	};
	
	var loadRenderServiceInfo = function(serviceId) {
		$.ajax({
	        url     : '/service?id=' + serviceId+'&rmd='+new Date().getTime(),
	        success : function(data){
	            if(data && data.code == '000000') {
	            	var service = data.data || {};
	        		$('.mui-title').text('带点啥-'+service.title)
	        		if(service.priceType == 1) {
	        			$('.service-price-detail').show();
	        			$('.origin-price').text('¥ '+ service.originPrice);
	        			$('.post-price').text('¥ '+ service.postPrice);
	        			$('.single-service-price').text('¥ '+ service.servicePrice);
	        		}		
	        		$('.service-price').text('¥ '+ service.price);
	        		$('.order-number').attr('price',service.price);
	        		$('.total-price').text('¥ ' + service.price );
	        		$('.total-price-detail').text('(1 * ' + service.price +')');
	        		
	        		$('.service-nav .contact').attr('userId',service.userId);
	        		
	        		var html = [];
	        		$('.service-info .mui-media-object').attr('src',getPictureUrl(service.cover,1));
	        		html.push('<h4 class="service-title">'+service.title+'</h4>');
	        		html.push('<p class="service-desc">'+service.description+'</p>');
	        		
	        		html.push('<div class="service-number">');
	        		html.push(' <a action="/wx/order?target='+service.id+'" class="mui-btn mui-btn-outlined mui-icon mui-icon-flag">订单 '+service.ordercount+'</a>');
	        		html.push(' <a action="/wx/comment?target='+service.id+'" class="mui-btn mui-btn-outlined mui-icon mui-icon-chatboxes">评论 '+service.commentcount+'</a>');
	        		html.push('</div>');
	        		
	        		html.push('<dl class="dl-horizontal">');
	        		var category = daidian.categorys[service.category]||{};
	        		if(category.name){
	        			html.push('<dt>分类</dt>');
	        			html.push('<dd>'+category.name+'</dd>');
	        	    }

	        		if(service.priceType == 1) {
	        			html.push('<dt>原价</dt>');
	        			html.push('<dd>¥ '+ (service.originPrice || 0)+'</dd>');
	        			
	        			html.push('<dt>直邮费</dt>');
	        			html.push('<dd>¥ '+(service.postPrice || 0)+'</dd>');
	        			
	        			html.push('<dt>服务费</dt>');
	        			html.push('<dd>¥ '+(service.servicePrice || 0)+'</dd>');
	        		}
	        		
	        		if(service.price){
	        			html.push('<dt>总价</dt>');
	        			html.push('<dd class="price-text">¥ '+service.price+'</dd>');
	        	    }
	        		
	        		html.push('</dl>');
	        		
	        		html.push('<div class="mui-content-padded service-more-info">');
	        		var serviceDetail = JSON.parse(service.servicedetail || '[]');
	        		serviceDetail.forEach(function(detail) {
	        		    var pictures = detail.pictures || [];
	        		    pictures.forEach(function(pic){
	        		    	html.push('<img class="mui-col-xs-4" style="height:100px;margin: 0px 3px;width:30%" src="'+getPictureUrl(pic,1,'list')+'" data-preview-src="'+getPictureUrl(pic,1)+'" data-preview-group="1" />');
	        		    }); 
	        		    html.push('<p>'+decodeURIComponent(detail.desc)+'</p>');  
	        		}); 
	        		html.push('</div>');
	        		
	        		$('.service-info').append(html.join(''));
	        		
	        		var userInfo = [];
	        		var user = service.userInfo || {};
	        		
	        		if(daidian.session.user && daidian.session.user.userId == user.userId && daidian.session.user.identityAuth == 1){
	        			userInfo.push('<a action="/wx/account-service">');
                    }else{
                    	userInfo.push('<a action="/wx/user?id='+user.userId+'">');
                    }
	        		userInfo.push('<div class="user-profile service-user">');
	        		userInfo.push('  <div class="user-img" style="float:left;width: 80px;"><img src="'+getPictureUrl(user.avatar,2)+'" /></div>');
	        		userInfo.push('  <div style="float: left;padding-left: 10px; padding-top: 10px;"><h4 class="user-name">'+((user.nickName) || '') +' <span>'+ (daidian.config.identityType[user.identity] || user.identity) +'</span></h4>');
	        		userInfo.push('  <div class="location-info" style="padding-top:6px;text-align:left">'+(dataconfig.countrys[user.country] || {}).name+'</div></div>');
	        		
	        		userInfo.push('</div>');
	        		userInfo.push('</a>');
	        		if(user.introduce) {
	        			userInfo.push('  <div style="padding-top: 10px;">'+user.introduce+'</div>');
	        		}
	        		
	        		$('.service-buyer-info').append(userInfo.join(''));
	        		
	        		mui.previewImage();
	    			wx.ready(function(){
		   	        	 wx.onMenuShareTimeline(getShareContent("timeLine"));
		   	        	 wx.onMenuShareAppMessage(getShareContent("app"));
		   	        });
	            } else {
	                mui.toast('加载用户信息失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载用户信息失败，请刷新页面重试');
	        }
	    });
	};
	
	$(function() {
	    var serviceId = getQueryString('id');
	    loadRenderServiceInfo(serviceId);
	
	    mui('#offCanvasSideScroll').scroll();
	    mui('#offCanvasContentScroll').scroll();
	
	    $('body').on('tap','.service-nav',function(evt){
	        var target = $(evt.target);
	        if(!target.hasClass('mui-tab-item')) {
	            target = target.closest('.mui-tab-item');
	        }
	
	        if(target.hasClass('favorite')) {
	            var favorite = {};
	            favorite.targetId = serviceId;
	            $.ajax({
	                type  : "post",
	                url   : "/user/favorite",
	                contentType   : "application/json;charset=UTF-8",
	                data    : JSON.stringify(favorite),
	                success :  function(data){
	                    if(data.code == '000000') {
	                        mui.toast('收藏成功');
	                        window.location.href="/wx/service?id="+ serviceId+"&rmd="+new Date().getTime();
	                    } else {
	                        mui.toast(data.msg);
	                    }
	                },
	                error   : function(){
	                    mui.toast('收藏出现异常');
	                }
	            });
	        }else if(target.hasClass('cancelfavorite')){
	            $.ajax({
	                type  : "delete",
	                url   : "/user/favorite?serviceId="+serviceId+"?rmd="+new Date().getTime(),
	                success :  function(data){
	                    if(data.code == '000000') {
	                        mui.toast('取消收藏成功');
	                        window.location.href="/wx/service?id="+ serviceId+"&rmd="+new Date().getTime();
	                    } else {
	                        mui.toast(data.msg);
	                    }
	                },
	                error   : function(){
	                    mui.toast('取消收藏出现异常');
	                }
	            });
	        } else if(target.hasClass('contact')) {
	        	var userId = target.attr('userId');
	            window.location.href="/wx/message?to="+userId+"&rmd="+new Date().getTime();
	        } else if(target.hasClass('booking')) {
	        	$('.order-number').val(1);
	            mui('#offCanvasWrapper').offCanvas('show');
	        }
	    });
	    
	    $('body').on('tap','.order-submit',function(evt) {
	        var order = {};
	        order.count = $('.order-number').val();
	        order.serviceId = serviceId;
	        
	        var btn = $('.order-submit');
	        btn.text('正在提交').attr('disabled',true);
	
	        $.ajax({
	            type : "post",
	            url  : "/user/order",
	            contentType : "application/json;charset=UTF-8",
	            data        : JSON.stringify(order),
	            success     : function(data){
	                btn.text('提交订单').attr('disabled',false);
	                if(data.code == '000000' && data.data) {
	                    mui.toast('提交订单成功');
	                    window.location.href="/wx/order-pay?id="+ data.data;
	                } else {
	                    mui.toast(data.msg);
	                }
	            },
	            error     : function() {
	                btn.text('提交订单').attr('disabled',false);
	                mui.toast('提交订单出现异常');
	            }
	        });
	
	    });
	
	    $('body').on('change','.order-number',function(evt){
	       var target = $(evt.target);
	       var price = target.attr('price');
	       var totalPrice = price * target.val();
	       $('.total-price-detail').html('(' + target.val() + ' * ' + price + ')');
	       $('.total-price').html('¥ ' + totalPrice.toFixed(2));
	    });
	    
	    $('body').on('input','.order-number',function(evt){
	    	
	    	var target = $(evt.target);
	    	var val = target.val();
	    	if(val > 99999) {
	    		target.val(99999);
	    	}
	    	if(val <= 0) {
	    		val = 1;
	    		target.val(1);
	    	}
	    	
	        var price = target.attr('price');
	        var totalPrice = price * target.val();
	        $('.total-price-detail').html(target.val() + ' * ' + price);
	        $('.total-price').html('¥ ' + totalPrice.toFixed(2));
	    });
	
	    $('body').on('tap','.paging-bar',function(evt){
	        evt.preventDefault();
	        loadAndRenderUserComment(serviceId);
	    });
	
	    $('.paging-bar').trigger('tap');
	});
</script>
<%@ include  file="common/footer.jsp"%>