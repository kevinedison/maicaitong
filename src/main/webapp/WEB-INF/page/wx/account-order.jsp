<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<style>
 .mui-control-item.mui-active{border-bottom: 2px solid #007aff!important;}
</style>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-我的订单</h1>
    <a action="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<nav class="comment-panel order-form" style="display:none">
    <form class="mui-input-group service-add">
       <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
           <div class="panel-title">评论</div>
           <span class="mui-icon mui-icon-close-filled mui-pull-right close-comment-panel"></span>
       </li>
       <div class="mui-input-row comment-info" style="height:auto;">
           <label>评分 </label>
           <div class="comment-rate">
                <div class="comment-star">
                   <span class="mui-icon mui-icon-star"></span><span class="mui-icon mui-icon-star"></span><span class="mui-icon mui-icon-star"></span><span class="mui-icon mui-icon-star"></span><span class="mui-icon mui-icon-star"></span>
                </div>
	            <div class="mui-input-range" style="padding:0px 10px 0px 80px;">
		           <input type="range" id='block-range' value="5" min="1" max="5" >
		        </div>
	       </div>
       </div>
       <div class="mui-input-row" id="comment-pic">
           <label>追加图片</label>
           <div class="row image-list" style="min-height:85px;height:auto">
               <div class="image-item image-upload" number="multi" style="background-image: url('/resources/wx/images/iconfont-tianjia.png')"></div>
           </div>
       </div>
       <div class="mui-input-row" style="height:auto;">
           <label>评价</label>
           <textarea class="mui-text-right comment-content" rows="5" placeholder="商品描述"></textarea>
       </div>
   </form>
   <a class="order-submit comment-submit"><span>提交</span></a>
</nav>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
			<a class="mui-control-item" status="-1">全部</a>
			<a class="mui-control-item" status="1">待付款</a>
			<a class="mui-control-item" status="2">待发货</a>
			<a class="mui-control-item" status="3">待收货</a>
			<a class="mui-control-item" status="99">待评论</a>
		</div>
        <div class="mui-slider-group">
            <div id="consume" class="mui-slider-item mui-control-content mui-active">
                <ul class="mui-table-view list-wrap consume-wrap"></ul>
                <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
            </div>
        </div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/ajaxfileupload.js"></script>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	var loadAndRenderBuyerOrder = function(status){
	    var page = $('#consume .paging-bar').attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	    
	    var url = '/user/buy/order?pageSize='+daidian.config.pageSize+'&page='+page+'&rmd='+new Date().getTime();
	    if(status != '-1') {
	    	url = url + "&status="+status;
	    }
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.consume-wrap').append(loadHtml);
	    $.ajax({
	        type    :   'get',
	        url     :   url,
	        success :   function(data){
	            $('.consume-wrap .mui-loading').remove();
	            if (data && data.code == '000000'){
	                if(!data.data || !data.data.count || data.data.count == 0) {
	                    $('.consume-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有消费订单</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $('#consume .paging-bar').show().attr('page',page);
	                } else {
	                    $('#consume .paging-bar').hide();
	                }
	
	                var orders = data.data.data || {};
	                var orderHtml = [];
	                orders.forEach(function(curData) {
	                    var service = curData.userService;
	                    var saler = curData.salerUser;
	                    orderHtml.push('<li class="mui-table-view-cell mui-media mui-col-xs-12 service-row">');
	                    orderHtml.push('    <div class="mui-slider-right mui-disabled">');
	                    if(curData.status == 1) {
	                    	orderHtml.push('        <a class="mui-btn mui-btn-red" oId='+curData.id+'>删除</a>');
	                    }
	                    
	                    orderHtml.push('    </div>');
	                    orderHtml.push('    <div class="mui-slider-handle">');
	                    orderHtml.push('            <div class="service-buyer">');
	                    if(daidian.session.user && daidian.session.user.userId == saler.userId && daidian.session.user.identityAuth == 1){
	                    	orderHtml.push('               <a action="/wx/account-service">');
	                    }else{
	                    	orderHtml.push('               <a action="/wx/user?id='+saler.userId+'">');
	                    }
	                    orderHtml.push('                <span class="user-img"><img src="'+getPictureUrl(saler.avatar,2)+'"/></span>');
	                    orderHtml.push('                <span class="user-info">');
	                    orderHtml.push('                    <div class="user-name">'+(saler.nickName)+'</div>');
	                    orderHtml.push('                    <div class="user-location">'+((dataconfig.countrys[saler.country] || {}).name || '')+'</div>');
	                    orderHtml.push('                </span>');
	                    orderHtml.push('                </a>');
	                    orderHtml.push('                <a action="/wx/message?to='+saler.userId+'">');
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
	                    orderHtml.push('                  <div class="service-title" style="padding-bottom: 5px;">'+service.title+'</div>');
	                    orderHtml.push('                  <div class="service-desc mui-ellipsis">'+service.description+'</div>');
	                    orderHtml.push('              </a>');
	                    orderHtml.push('         <div class="service-bar">');
	                    orderHtml.push('            <button class="mui-btn-danger mui-btn-outlined service-price">¥ '+service.price+'</button>');
	                    orderHtml.push('            <div class="service-status mui-pull-right">');
	                    if(curData.status == 1) {
	                    	orderHtml.push('         <button type="button" class="mui-btn mui-btn-danger order-operate" orderId="'+curData.id+'" status='+curData.status+' >立即支付</button>');
	                    }else if(curData.status == 2) {
	                    	orderHtml.push('        <span class="mui-badge mui-badge-warning">'+daidian.config.orderStatus[curData.status]+'</span>');
	                    } 
	                    else if(curData.status == 3) {
	                    	orderHtml.push('         <button type="button" class="mui-btn mui-btn-danger order-operate" orderId="'+curData.id+'" status='+curData.status+' >确定收货</button>');
	                    } else if((curData.status == 4 || curData.status == 6 || curData.status == 7 || curData.status == 8) && curData.isComment == 0) {
	                    	orderHtml.push('         <button type="button" class="mui-btn mui-btn-danger order-comment" orderId="'+curData.id+'" status='+curData.status+' serviceId='+service.id+' targetUserId='+curData.saleId+' >评论</button>');
	                    } else {
	                    	orderHtml.push('        <span class="mui-badge mui-badge-warning">已完成</span>');
	                    }
	                    
	                    orderHtml.push('            </div>');
	                    orderHtml.push('         </div>');
	                    orderHtml.push('            </div>');
	                    orderHtml.push('         </div>');
	                    orderHtml.push('         <div class="order-id clearfix mui-pull-left">订单编号：'+curData.id+'</div>');
	                    orderHtml.push('         <div class="order-id more-info mui-pull-right">');
	                    orderHtml.push('           <a href="/wx/order-pay?id='+curData.id+'" class=""> 订单详情</a>');
	                    orderHtml.push('         </div>');
	                    orderHtml.push('    </div>');
	                    orderHtml.push('</li>');
	                });
	                $('.consume-wrap').append(orderHtml.join(''));
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
			
	    $('body').on('touchstart','.mui-slider-right .mui-btn-red', function (evt) {
	        var target = $(evt.target);
	        var oId = target.attr('oId');
	
	        if(oId) {
	            target.text('正在删除').attr('disabled',false);
	            $.ajax({
	                type : "delete",
	                url  : "/user/order?id="+oId+'&rmd='+new Date().getTime(),
	                contentType : "application/json;charset=UTF-8",
	                success     : function(data){
	                    target.text('删除').attr('disabled',false);
	                    if(data.code == '000000') {
	                        mui.toast('删除订单成功');
	                        target.closest('li').slideUp(500);;
	                    } else {
	                        mui.toast('删除订单失败');
	                    }
	                },
	                error     : function() {
	                    target.text('删除').attr('disabled',false);
	                    mui.toast('删除订单失败');
	                }
	            });
	        }
	    });
	
	    $('body').on('tap','.mui-control-item', function (evt) {
	    	var target = $(evt.target);	  
	    	if(target.hasClass('mui-active')) {
	    		return;
	    	} else {
	    		$('.mui-control-item').removeClass('mui-active');
	    		target.addClass('mui-active');
	    	}
	    	
	    	$('.consume-wrap').html('');
	    	
	    	$('.paging-bar').hide().attr('page',0);
	    	var status = target.attr('status') || '-1';
	    	loadAndRenderBuyerOrder(status);
	    });
	    
	    $('#consume .paging-bar').click(function(evt) {
	        evt.preventDefault();
	        var status = $('.mui-control-item.mui-active').attr('status') || '-1';
	        loadAndRenderBuyerOrder(status);
	    });
	    
	    $('body').on('tap','.order-operate', function (evt) {
	    	evt.preventDefault();
	    	var target = $(evt.target);
	        var status = target.attr('status');
	        var orderId = target.attr('orderId');
	        if(status == 1) {
	        	window.location.href = "/wx/order-pay?id="+orderId+"&rmd="+new Date().getTime();
	        } else if(status == 3) {
	        	var order = {};
		        order.id= target.attr('orderId');
		        order.status = 4;
	        	$.ajax({
	                type : "put",
	                url  : "/user/order",
	                data : JSON.stringify(order),		
	                contentType : "application/json;charset=UTF-8",
	                success     : function(data){
	                    if(data.code == '000000') {
	                    	window.location.reload();
	                        mui.toast('提交成功');
	                    } else {
	                        mui.toast('提交失败');
	                    }
	                },
	                error     : function() {
	                	mui.toast('提交失败');
	                }
	            });
	        }
	    });	
	    
	    $('body').on('tap','.order-comment',function(evt){
	    	evt.preventDefault();
	    	var target = $(evt.target);
	    	var status = target.attr('status');
	        var orderId = target.attr('orderId');
	        var targetUserId = target.attr('targetUserId');
	    	
	    	$('.comment-submit').attr('orderId',orderId).attr('serviceId',target.attr('serviceId')).attr('tarUserId',targetUserId);
        	$('.comment-panel').slideDown(300);
	    });
    	
	    $('body').on('tap','.close-comment-panel', function (evt) {
	    	var target = $(evt.target);
	    	target = target.closest('.comment-panel');
	    	target.slideUp(300);
	    });
	    
	    $('body').on('input','input[type=range]', function (evt) {
	    	var target = $(evt.target);
	    	var value = target.val();
	    	$('.comment-star').html(getRate(value));
	    });
	    
	    
	    $('body').on('tap','.comment-submit', function (evt) {
	    	var target = $(evt.target);
	    	var comment = {};
	    	comment.orderId = target.attr('orderId');
	    	comment.serviceId = target.attr('serviceId');
	    	comment.tarUserId = target.attr('tarUserId');
	    	comment.rate= $('input[type=range]').val() || 5;
	    	
	    	var picture = [];
	        $('#comment-pic').each(function(index,dom){
	            $(dom).find('.image-item').each(function(index,input){
	                if($(input).attr('pic-path')) {
	                    picture.push($(input).attr('pic-path'));
	                }
	            });
	        });
	        
	    	comment.pictures = JSON.stringify(picture);
	    	comment.comment =  $('.comment-content').val();
	    	$.ajax({
	            type : "post",
	            url  : "/user/comment",
	            data : JSON.stringify(comment),		
	            contentType : "application/json;charset=UTF-8",
	            success     : function(data){
	                if(data.code == '000000') {
	                	window.location.reload();
	                    mui.toast('提交成功');
	                } else {
	                    mui.toast('提交失败');
	                }
	            },
	            error     : function() {
	            	mui.toast('提交失败');
	            }
	        });
	    });
	    
	    var status = getQueryString('status') || '-1';
	    $('.mui-control-item[status='+status+']').trigger('tap');
	});
</script>
<%@ include  file="common/footer.jsp"%>