<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-收藏列表</h1>
    <a action="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<div class="mui-content">
    <ul class="mui-table-view favorite-wrap">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">收藏列表</div>
        </li>
    </ul>
    <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	var loadAndRenderUserFavorite = function(userId) {
	    var page = $(".paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.favorite-wrap').append(loadHtml);
	    $.ajax({
	        type    : 'get',
	        url     : '/user/favorite/list?pageSize='+daidian.config.pageSize+'&page='+page,
	        success : function(data){
	            $('.favorite-wrap .mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    $('.favorite-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有收藏服务</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $(".paging-bar").show().attr('page',page);
	                } else {
	                    $(".paging-bar").hide();
	                }
	                var html = [];
	
	                (data.data.data || []).forEach(function(curData) {
	                    var service = curData.content || {};
	                    var user = curData.simpUser || {};
	                    html.push('<li class="mui-table-view-cell mui-media">');
	                    html.push('    <div class="mui-slider-right mui-disabled">');
	                    html.push('        <a class="mui-btn mui-btn-red" fId="'+curData.id+'">删除</a>');
	                    html.push('    </div>');
	                    html.push('    <div class="mui-slider-handle">');
	                    html.push('        <a action="/wx/service?id='+service.id+'">');
	                    html.push('            <img class="mui-media-object mui-pull-left" src="'+getPictureUrl(service.cover,1,'list')+'">');
	                    html.push('            <div class="mui-media-body">');
	                    html.push('                <div class="buyer-info"><span class="buyer-name">'+(user.nickName)+'</span>    <span class="user-location">'+((dataconfig.countrys[user.country] || {}).name || '')+'</span></div>');
	                    html.push('                <div class="service-title mui-ellipsis-2">'+service.title+'</div>');
	                    html.push('                <div class="service-bar">');
	                    html.push('                    <button class="mui-btn-danger mui-btn-outlined service-price">¥ '+service.price+'</button>');
	                    html.push('                    <div class="service-number">');
	                    html.push('                        <button class="mui-btn mui-btn-outlined mui-icon mui-icon-flag">'+ service.ordercount+' </button>');
	                    html.push('                        <button class="mui-btn mui-btn-outlined mui-icon mui-icon-chatboxes">'+ service.commentcount+' </button>');
	                    html.push('                    </div>');
	                    html.push('                </div>');
	                    html.push('            </div>');
	                    html.push('        </a>');
	                    html.push('    </div>');
	                    html.push('</li>');
	                });
	
	                $('.favorite-wrap').append(html.join(''))
	            } else {
	                mui.toast('加载评论失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载评论失败，请刷新页面重试');
	        }
	    });
	};
	$(function () {
		if(!checkSession()) {return;};
		
	    $('.paging-bar').click(function(evt) {
	        evt.preventDefault();
	        loadAndRenderUserFavorite();
	    });
	    $('.paging-bar').trigger('click');
	
	    $('body').on('touchstart','.mui-slider-right .mui-btn-red', function (evt) {
	        var target = $(evt.target);
	        var fId = target.attr('fId');
	
	        if(fId) {
	            target.text('正在删除').attr('disabled',false);
	            $.ajax({
	                type : "delete",
	                url  : "/user/favorite?id="+fId+'&rmd='+new Date().getTime(),
	                contentType : "application/json;charset=UTF-8",
	                success     : function(data){
	                    target.text('删除').attr('disabled',false);
	                    if(data.code == '000000') {
	                        mui.toast('删除收藏成功');
	                        target.closest('li').slideUp(500);
	                    } else {
	                        mui.toast('删除收藏失败');
	                    }
	                },
	                error     : function() {
	                    target.text('删除').attr('disabled',false);
	                    mui.toast('删除收藏失败');
	                }
	            });
	        }
	    });
	});
</script>
<%@ include  file="common/footer.jsp"%>