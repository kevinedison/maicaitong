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
<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
    <div class="mui-inner-wrap">
        <header class="mui-bar mui-bar-nav">
            <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
            <h1 class="mui-title">卖菜通-服务检索</h1>     
        </header>
        <div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper">
            <div class="mui-scroll">
            	<div>
	        		<form class="mui-input-group service-search">
				        <div class="mui-input-row">
				            <input type="text" id="search_name" placeholder="请输入商品名或菜农昵称，支持模糊检索">
				        </div>
				        <div class="mui-button-row">
				            <button type="button" id="search_button" class="mui-btn mui-btn-primary">检索</button>
				        </div>
	    			</form>
        		</div>
                <ul class="mui-table-view service-row">
                    <li class="mui-table-view-cell mui-table-panel mui-col-xs-12" id="buyer_wrap" style="display:none">
                        <div class="panel-title">菜农列表</div>
                        <div class="service-wrap" id="buyer_list" ></div>
                    </li>
                    <li class="mui-table-view-cell mui-table-panel mui-col-xs-12" id="service_wrap" style="display:none">
                        <div class="panel-title">服务列表</div>
                        <div class="service-wrap" id="service_list"></div>
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
<script type="text/javascript">
	var filterService = function(serviceName) {
	    var page = $(".paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var url =  '/service/list?pageSize='+daidian.config.pageSize+'&page='+page+'&title='+serviceName;

	    url = url + "&rmd=" + new Date().getTime();
	    var loadHtml = '<div class="mui-loading" id="service_load"><div class="mui-spinner"></div></div>';
	    $('#service_list').append(loadHtml);
	    $.ajax({
	        type    : 'get',
	        url     :  url,
	        success : function(data){
	            $('#service_load').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    $(".paging-bar").hide();
	                    $('#service_wrap').hide();
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $(".paging-bar").show().attr('page',page);
	                } else {
	                    $(".paging-bar").hide();
	                }
	                
	                $('#service_wrap').show();
	
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
	                $('#service_list').append(serviceHtml.join(''));
	            } else {
	                mui.toast('检索服务失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('检索服务失败，请刷新页面重试');
	        }
	    });
	};
	
	var filterBuyer = function(buyerName){
		var url =  '/buyer/list?pageSize=999&page=1&nickName='+buyerName;
		
		var loadHtml = '<div class="mui-loading" id="buyer_load"><div class="mui-spinner"></div></div>';
        $('#buyer_list').append(loadHtml);
        
        $.ajax({
        	type : 'get',
        	url  : url,
        	success : function(data){
        		$('#buyer_load').remove();
        		if(data && data.code == '000000'){
        			if(!data.data.count || data.data.count == 0) {
        				$('#buyer_wrap').hide();
	                    return;
	                }
        			
        			$('#buyer_wrap').show();
        			
        			var buyers = data.data.data || [];
        	        var buyerHtml =[];
        	        buyers.forEach(function(b) {
        	            var serviceType = JSON.parse(b.servicetype || '{}');
        	            buyerHtml.push('<li class="mui-table-view-cell mui-media mui-col-xs-12" userId="'+ b.userId +'">');
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
        	        $('#buyer_list').append(buyerHtml.join(''));
        		}else {
	                mui.toast('检索菜农失败，请刷新页面重试');
	            }
        	},
        	error : function(){
        		mui.toast('检索菜农失败，请刷新页面重试');
        	}    	
        });
	};
	
	$(function() {
	    $('body').on('tap','#search_button',function(evt){
	        var target = $(evt.target);
	        
	        var searchName= $('#search_name').val() || '';
	        
	        $('#buyer_list').html('');
	        $('#service_list').html('');
	        
	        filterBuyer(searchName);
	        filterService(searchName);
	    });
	
	    mui('#offCanvasContentScroll').scroll();
		
	    $('.filter-btn button').click(function(evt){
	        $('.service-wrap').html('');
	        $('.paging-bar').attr('page',0).hide().trigger('tap');
	        mui('#offCanvasWrapper').offCanvas('close');
	    });
	
	    $('body').on('tap','.paging-bar',function(evt){
	        evt.preventDefault();
	        var searchName= $('#search_name').val() || '';
	        filterService(searchName);
	    });
	});
</script>
<%@ include  file="common/footer.jsp"%>