<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">带点啥-订单列表</h1>
    <a action="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<div class="mui-content">
    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed list-wrap order-wrap">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">订单列表</div>
        </li>
    </ul>
    <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	var loadAndRenderUserComment = function(userId,serviceId){
	    var page = $(".paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var url = '/order/list?pageSize='+daidian.config.pageSize+'&page='+page;
	    if(userId){
	    	url = url +  '&saleId='+userId;
	    }
	    
	    if(serviceId){
	    	url = url +  '&serviceId='+serviceId;
	    }
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.order-wrap').append(loadHtml);
	    $.ajax({
	        type    : 'get',
	        url     : url,
	        success : function(data){
	        	$('.mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    $('.order-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有订单</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $(".paging-bar").show().attr('page',page);
	                } else {
	                    $(".paging-bar").hide();
	                }
	                
	                var orderHtml = [];
	                (data.data.data || []).forEach(function(curData) {
	                	var service = curData.userService || {};
                        var buyer = curData.buyerUser  || {};
                        orderHtml.push('<li class="mui-table-view-cell mui-media">');
                        orderHtml.push('    <div class="mui-slider-handle">');
                        
                        if(daidian.session.user && daidian.session.user.userId == buyer.userId && daidian.session.user.identityAuth == 1){
                        	orderHtml.push('               <a action="/wx/account-service">');
	                    }else{
	                    	orderHtml.push('        <a action="/wx/user?id='+buyer.userId+'">');
	                    }
                        orderHtml.push('        <span class="oa-contact-avatar mui-pull-left"><img src="'+ getPictureUrl(buyer.avatar,2) +'"></span>');
                        orderHtml.push('		</a>');
                        orderHtml.push('        <div class="mui-media-body">');
                        orderHtml.push('            <div class="order-buyer">');
                        orderHtml.push('                <span class="buyer-name">' + ((buyer.nickName) || '--')+ '</span>');
                        orderHtml.push('                <span class="buyer-country">' + (((dataconfig.countrys[buyer.country] || {}).name || buyer.hometown) || '') + '</span>');
                        orderHtml.push('                <span class="buyer-time mui-pull-right">' + new Date(curData.operateTime).format('MM-dd')+'</span>');
                        orderHtml.push('            </div>');
                        orderHtml.push('            <div class="order-service">');
                        orderHtml.push('               <button class="mui-btn-danger mui-btn-outlined service-price">¥ ' + service.price + '</button>');
                        orderHtml.push('               <div class="service-title mui-pull-right">');
                        orderHtml.push('                  <a action="/wx/service?id='+service.id+'" class="mui-ellipsis">' + service.title +'</a>');
                        orderHtml.push('               </div>');
                        orderHtml.push('            </div>');
                        orderHtml.push('        </div> ');
                        orderHtml.push('    </div>');
                        orderHtml.push('</li>');
	                });
	
	                $('.order-wrap').append(orderHtml.join(''))
	            } else {
	                mui.toast('加载评论失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载评论失败，请刷新页面重试');
	        }
	    });
	};
	
	$(function() {
	    var userId = getQueryString('user');
	    var serviceId = getQueryString('target');
	    
	    $('.paging-bar').click(function(evt) {
	        evt.preventDefault();
	        loadAndRenderUserComment(userId,serviceId);
	    });
	    $('.paging-bar').trigger('click');
	});
</script>
<%@ include  file="common/footer.jsp"%>