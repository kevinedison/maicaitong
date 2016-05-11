<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">带点啥-评论列表</h1>
    <a href="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<div class="mui-content">
    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed comment-wrap">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">评论列表</div>
        </li>
    </ul>
    <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
</div>
<%@ include  file="common/js.jsp"%>
<script type="text/javascript">
	var loadAndRenderUserComment = function(userId,serviceId){
	    var page = $(".paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var url = '/comment/list?pageSize='+daidian.config.pageSize+'&page='+page;
	    if(userId){
	    	url = url +  '&tarUserId='+userId;
	    }
	    
	    if(serviceId){
	    	url = url +  '&serviceId='+serviceId;
	    }
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.comment-wrap').append(loadHtml);
	    $.ajax({
	        type    : 'get',
	        url     : url,
	        success : function(data){
	        	$('.mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data ||!data.data.count || data.data.count == 0) {
	                    $('.comment-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有评论</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $(".paging-bar").show().attr('page',page);
	                } else {
	                    $(".paging-bar").hide();
	                }
	                var commentHtml = [];
	
	                (data.data.data || []).forEach(function(curData) {
	                	var user = curData.user || {};
	                	var service = curData.serviceInfo || {};	
	                    commentHtml.push('<li class="mui-table-view-cell comment-info">');
	                    commentHtml.push('    <div class="mui-table">');
	                    
	                    
	                    if(daidian.session.user && daidian.session.user.userId == user.userId && daidian.session.user.identityAuth == 1){
	                    	commentHtml.push('		<a action="/wx/account-service">');
		                }else{
		                	commentHtml.push('      <a action="/wx/user?id='+user.userId+'">');
		                }
	                    
	                    commentHtml.push('        <div class="mui-table-cell mui-col-xs-2 mui-text-left">');
	                    commentHtml.push('           <div class="user-img"><img src="'+getPictureUrl(user.avatar,2)+'" /></div>');
	                    commentHtml.push('        </div>');
	                    commentHtml.push('      </a>');
	                    commentHtml.push('      <div class="mui-table-cell mui-col-xs-7">');
	                    commentHtml.push('          <h4 class="comment-rate">'+getRate(curData.rate)+'</h4>');
	                    commentHtml.push('          <h5 class="comment-user">'+user.nickName+'</h5>');
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
	                    commentHtml.push('          <a class="mui-h6" action="/wx/service?id='+service.id+'">'+(service.title || '')+'</a>');
	                    commentHtml.push('      </div>');
	                    commentHtml.push('   </div>');
	                    commentHtml.push('</li>');
	                });
	
	                $('.comment-wrap').append(commentHtml.join(''))
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