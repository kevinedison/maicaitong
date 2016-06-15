<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-我的订单</h1>
    <a action="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
			<a class="mui-control-item" status="-1">全部</a>
			<a class="mui-control-item" status="2">待发货</a>
			<a class="mui-control-item" status="3">待收货</a>
			<a class="mui-control-item" status="4">待结算</a>
		</div>
        <div class="mui-slider-group">
            <div id="service" class="mui-slider-item mui-control-content">
                <ul class="mui-table-view list-wrap service-wrap"></ul>
                <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
            </div>
        </div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">	
	var loadAndRenderSaleOrder = function(status){
	    var page = $('#service .paging-bar').attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	    
	    var url = '/user/sale/order?pageSize='+daidian.config.pageSize+'&page='+page+'&rmd='+new Date().getTime();
	    if(status != '-1') {
	    	url = url + "&status="+status;
	    }
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.service-wrap').append(loadHtml);
	    $.ajax({
	        type    :   'get',
	        url     :   url,
	        success :   function(data){
	            $('.service-wrap .mui-loading').remove();
	            if (data && data.code == '000000'){
	                if(!data.data || !data.data.count || data.data.count == 0) {
	                    $('.service-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有服务订单</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $('#service .paging-bar').show().attr('page',page);
	                } else {
	                    $('#service .paging-bar').hide();
	                }
	
	                var orders = data.data.data || {};
	                var orderHtml = [];
	                var tempDate = $('.order-list-date').last().attr('date');;
	                orders.forEach(function(curData) {
	                	var date = new Date(curData.operateTime).format('yyyy-MM-dd');
	                	if(!tempDate || tempDate != date) {
	                		orderHtml.push('<div class="mui-text-left notice-msg order-list-date" date="'+date+'">'+date+'</div>');
	                		tempDate = date;
	                	}
	                	
	                    var service = curData.userService;
	                    var buyer = curData.buyerUser;
	                    orderHtml.push('<li class="mui-table-view-cell mui-media mui-col-xs-12 service-row">');
	                    orderHtml.push('            <div class="service-buyer">');
	                    if(daidian.session.user && daidian.session.user.userId == buyer.userId && daidian.session.user.identityAuth == 1){
	                    	orderHtml.push('               <a action="/wx/account-service">');
	                    }else{
	                    	orderHtml.push('               <a action="/wx/user?id='+buyer.userId+'">');
	                    }
	                    orderHtml.push('                <span class="user-img"><img src="'+getPictureUrl(buyer.avatar,2)+'"/></span>');
	                    orderHtml.push('                <span class="user-info">');
	                    orderHtml.push('                    <div class="user-name">'+(buyer.nickName)+'</div>');
	                    orderHtml.push('                    <div class="user-location">'+((dataconfig.countrys[buyer.country] || {}).name || '')+'</div>');
	                    orderHtml.push('                </span>');
	                    orderHtml.push('                </a>');
	                    orderHtml.push('                <a action="/wx/message?to='+buyer.userId+'">');
	                    orderHtml.push('                    <button class="mui-btn mui-icon mui-icon-chat"></button>');
	                    orderHtml.push('                </a>');
	                    orderHtml.push('            </div>');
	
	                    orderHtml.push('         <div class="service-info">');
	                    if(service.cover){
	                    	orderHtml.push('            <img class="mui-media-object mui-pull-left" src="'+getPictureUrl(service.cover,1,'list')+'">');
	                    }else{
	                    	var pictures = JSON.parse(service.picture) || [];
	                    	orderHtml.push('            <img class="mui-media-object mui-pull-left" src="'+getPictureUrl(pictures[0],1,'list')+'">');
	                    }
	                    orderHtml.push('            <div class="mui-media-body">');
	                    if(service.id.indexOf('N') == 0){
	                    	orderHtml.push('              <a action="/wx/need?id='+service.id+'">');
	                    }else{
	                    	orderHtml.push('              <a action="/wx/service?id='+service.id+'">');
	                    }     
	                    orderHtml.push('                  <div class="service-title mui-ellipsis">'+service.title+'</div>');
	                    orderHtml.push('                  <div class="service-desc mui-ellipsis-2">'+service.description+'</div>');
	                    orderHtml.push('              </a>');
	                    orderHtml.push('         <div class="service-bar">');
	                    orderHtml.push('            <button class="mui-btn-danger mui-btn-outlined service-price">¥ '+service.price+'</button>');
	                    orderHtml.push('            <div class="service-status mui-pull-right">');
	                    if(curData.status == 2) {
	                    	orderHtml.push('         <a type="button" class="mui-btn mui-btn-danger order-operate" orderId="'+curData.id+'" status='+curData.status+' >确定发货</a>');
	                    }else if(curData.status == 4){
	                    	if (curData.payType == 1){
	                    		// 直接支付 状态为4即为已结算
	                    		orderHtml.push('        <span class="mui-badge mui-badge-warning">'+daidian.config.orderStatus[6]+'</span>');
	                    	}else{
	                    		orderHtml.push('         <a type="button" class="mui-btn mui-btn-danger order-settle" orderId="'+curData.id+'" status='+curData.status+' >结算</a>');
	                    	}              	
	                    } 
	                    else {
	                    	orderHtml.push('        <span class="mui-badge mui-badge-warning">'+daidian.config.orderStatus[curData.status]+'</span>');
	                    }
	                    orderHtml.push('            </div>');
	                    orderHtml.push('         </div>');
	                    orderHtml.push('            </div>');
	                    orderHtml.push('         </div>');
	                    orderHtml.push('         <div class="order-id clearfix mui-pull-left">订单编号：'+ curData.id + '</div>');
	                    orderHtml.push('         <div class="order-id more-info mui-pull-right">');
	                    orderHtml.push('           <a action="/wx/order-pay?type=1&id='+curData.id+'" class=""> 订单详情</a>');
	                    orderHtml.push('         </div>');
	                    orderHtml.push('    </div>');
	                    orderHtml.push('</li>');
	                });
	                $('.service-wrap').append(orderHtml.join(''));
	            }else{
	                mui.toast('加载订单出现异常');
	            }
	        },
	        error   :   function(){
	            mui.toast('加载订单失败');
	        }
	    });
	};
	
	$(function () {
		if(!checkSession()) {return;};
		var status = getQueryString('status');
		
	    $('body').on('tap','.mui-control-item', function (evt) {
	    	var target = $(evt.target);
	    	if(target.hasClass('mui-active')) {
	    		return;
	    	} else {
	    		$('.mui-control-item').removeClass('mui-active');
	    		target.addClass('mui-active');
	    	}
	    	
	    	$('.service-wrap').html('');
	    	$('.paging-bar').hide().attr('page',0);
	    	var status = target.attr('status') || '-1';
	    	loadAndRenderSaleOrder(status);
	    });
	    
	    $('#service .paging-bar').click(function(evt) {
	        evt.preventDefault();
	        var status = $('.mui-control-item.mui-active').attr('status') || '-1';
	        loadAndRenderSaleOrder(status);
	    });
	    
	    $('body').on('tap','.order-operate', function (evt) {
	    	evt.preventDefault();
	    	var target = $(evt.target);
	        var order = {};
	        order.id= target.attr('orderId');
	        order.status = 3;
	        $.ajax({
                type : "put",
                url  : "/user/order",
                data : JSON.stringify(order),		
                contentType : "application/json;charset=UTF-8",
                success     : function(data){
                    if(data.code == '000000') {
                        mui.toast('提交成功');
                        window.location.href = "/wx/account-service-order?status="+status+"&rmd="+new Date().getTime();
                    } else {
                        mui.toast('提交失败');
                    }
                },
                error     : function() {
                	mui.toast('提交失败');
                }
            });
	    });
	    
	    $('body').on('tap','.order-settle',function(evt){
	    	evt.preventDefault();
	    	var target = $(evt.target);
	    	var order = {};
	    	order.id = target.attr('orderId');
	    	
	    	$.ajax({
	    		type : "post",
	    		url  : "/user/settle",
	    		data : JSON.stringify(order),
	    		contentType : "application/json;charset=UTF-8",
	    		success : function(data){
	    			if(data.code == '000000'){
	    				mui.toast('结算成功');
	    			}else{
	    				mui.toast(data.msg);
	    			}
	    		},
	    		error	: function(){
	    			mui.toast('结算失败');
	    		}
	    	});
	    });
	    
	    
	    

		var status = getQueryString('status') || '-1';
		$('.mui-control-item[status='+status+']').trigger('tap');
	});
</script>
<%@ include  file="common/footer.jsp"%>