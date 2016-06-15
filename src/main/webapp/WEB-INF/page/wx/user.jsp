<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-</h1>
    <a action="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<nav class="mui-bar mui-bar-tab user-nav">
    <a class="mui-tab-item contact">
        <span class="mui-tab-label">咨询</span>
    </a>
    <a class="mui-tab-item add-contact">
        <span class="mui-tab-label">加为好友</span>
    </a>
</nav>
<div class="mui-content">
    <div class="user-profile"></div>
    <ul class="mui-table-view user-service-wrap service-row" style="display:none">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-milk-title" style="display:none">
            <div class="panel-title">大牌奶粉</div>
        </li>
        <div class="user-milk-service"></div>
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-bag-title" style="display:none">
            <div class="panel-title">名牌包</div>
        </li>
        <div class="user-bag-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-cosmetics-title" style="display:none">
            <div class="panel-title">化妆品</div>
        </li>
        <div class="user-cosmetics-service"></div>
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-special-title" style="display:none">
            <div class="panel-title">特卖</div>
        </li>
        <div class="user-special-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-accessory-title" style="display:none">
            <div class="panel-title">配饰</div>
        </li>
        <div class="user-accessory-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-vitacost-title" style="display:none">
            <div class="panel-title">母婴</div>
        </li>
        <div class="user-vitacost-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-digital-title" style="display:none">
            <div class="panel-title">数码</div>
        </li>
        <div class="user-digital-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-food-title" style="display:none">
            <div class="panel-title">食物</div>
        </li>
        <div class="user-food-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-homefurnish-title" style="display:none">
            <div class="panel-title">家居</div>
        </li>
        <div class="user-homefurnish-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-outdoorsport-title" style="display:none">
            <div class="panel-title">运动户外</div>
        </li>
        <div class="user-outdoorsport-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-healthproduct-title" style="display:none">
            <div class="panel-title">保健品</div>
        </li>
        <div class="user-healthproduct-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-dress-title" style="display:none">
            <div class="panel-title">男/女装</div>
        </li>
        <div class="user-dress-service"></div>
        
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12 user-other-title" style="display:none">
            <div class="panel-title">其他</div>
        </li>
        <div class="user-other-service"></div>
    </ul>
    <div class="dividing-h"></div>
    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed comment-wrap">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">评论列表</div>
        </li>
        <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
    </ul>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	var loadAndRenderUserService = function(userId){
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.user-service-wrap').append(loadHtml);
	    $.ajax({
	        url     : '/service/list?userId=' + userId + '&pageSize=9999&page=1',
	        success : function(data){
	            $('.user-service-wrap .mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    $('.user-service-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有服务</li>');
	                    return;
	                }
	
	                var services = data.data.data || [];
	                var milkHtml=[],bagHtml=[],cosmeticsHtml=[],specialHtml=[],accessoryHtml=[],vitacostHtml=[],digitalHtml=[],foodHtml=[],otherHtml=[],homefurnishHtml=[],outdoorsportHtml=[],healthproductHtml=[],dressHtml=[];
	                services.forEach(function(service) {
	                	var html = [];
	                	html.push('<li class="mui-table-view-cell mui-media">');
	                	html.push('    <a action="/wx/service?id='+service.id+'">');
	                	html.push('        <img class="mui-media-object mui-pull-left" src="'+getPictureUrl(service.cover,1,'list')+'">');
	                	html.push('        <div class="mui-media-body">');
	                	html.push('            <div class="service-title mui-ellipsis">'+service.title+'</div>');
	                	html.push('            <div class="service-desc mui-ellipsis-2">'+service.description+'</div>');
	                	html.push('            <div class="service-bar">');
	                	html.push('                <button class="mui-btn-danger mui-btn-outlined service-price">¥ '+service.price+'</button>');
	                	html.push('                <div class="service-number">');
	                	html.push('                    <button class="mui-btn mui-btn-outlined mui-icon mui-icon-flag" type="service" serviceId="'+service.id+'"> '+service.ordercount+'</button>');
	                	html.push('                </div>');
	                	html.push('            </div>');
	                	html.push('        </div>');
	                	html.push('    </a>');
	                	html.push('</li>');
	                	if(service.category == 'milk') {
	                		milkHtml.push(html.join(''));
	                	} else if(service.category == 'bag') {
	                		bagHtml.push(html.join(''));
	                	} else if(service.category == 'cosmetics') {
	                		cosmeticsHtml.push(html.join(''));
	                	} else if(service.category == 'special') {
	                		specialHtml.push(html.join(''));
	                	} else if(service.category == 'accessory') {
	                		accessoryHtml.push(html.join(''));
	                	} else if(service.category == 'vitacost') {
	                		vitacostHtml.push(html.join(''));
	                	} else if(service.category == 'digital') {
	                		digitalHtml.push(html.join(''));
	                	} else if(service.category == 'food') {
	                		foodHtml.push(html.join(''));
	                	} else if(service.category == 'homefurnish') {
	                		homefurnishHtml.push(html.join(''));
	                	} else if(service.category == 'outdoorsport') {
	                		outdoorsportHtml.push(html.join(''));
	                	} else if(service.category == 'healthproduct') {
	                		healthproductHtml.push(html.join(''));
	                	} else if(service.category == 'dress') {
	                		dressHtml.push(html.join(''));
	                	} else if(service.category == 'other') {
	                		otherHtml.push(html.join(''));
	                	}
	                	
	                });
	                if(milkHtml.length > 0) {
	                	$('.user-milk-title').show();
	                	$('.user-milk-service').append(milkHtml.join(''));
	                }
	                if(bagHtml.length > 0) {
	                	$('.user-bag-title').show();
	                	$('.user-bag-service').append(bagHtml.join(''));
	                }
	                if(cosmeticsHtml.length > 0) {
	                	$('.user-cosmetics-title').show();
	                	$('.user-cosmetics-service').append(cosmeticsHtml.join(''));
	                }
	                if(specialHtml.length > 0) {
	                	$('.user-special-title').show();
	                	$('.user-special-service').append(specialHtml.join(''));
	                }
	                if(accessoryHtml.length > 0) {
	                	$('.user-accessory-title').show();
	                	$('.user-accessory-service').append(accessoryHtml.join(''));
	                }
	                if(vitacostHtml.length > 0) {
	                	$('.user-vitacost-title').show();
	                	$('.user-vitacost-service').append(vitacostHtml.join(''));
	                }
	                if(digitalHtml.length > 0) {
	                	$('.user-digital-title').show();
	                	$('.user-digital-service').append(digitalHtml.join(''));
	                }
	                if(foodHtml.length > 0) {
	                	$('.user-food-title').show();
	                	$('.user-food-service').append(foodHtml.join(''));
	                }
	                if(homefurnishHtml.length > 0) {
	                	$('.user-homefurnish-title').show();
	                	$('.user-homefurnish-service').append(homefurnishHtml.join(''));
	                }
	                if(outdoorsportHtml.length > 0) {
	                	$('.user-outdoorsport-title').show();
	                	$('.user-outdoorsport-service').append(outdoorsportHtml.join(''));
	                }
	                if(healthproductHtml.length > 0) {
	                	$('.user-healthproduct-title').show();
	                	$('.user-healthproduct-service').append(healthproductHtml.join(''));
	                }
	                if(dressHtml.length > 0) {
	                	$('.user-dress-title').show();
	                	$('.user-dress-service').append(dressHtml.join(''));
	                }
	                if(otherHtml.length > 0) {
	                	$('.user-other-title').show();
	                	$('.user-other-service').append(otherHtml.join(''));
	                }
	            } else {
	                mui.toast('加载用户服务失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载用户服务失败，请刷新页面重试');
	        }
	    });
	};

	var loadAndRenderUserComment = function(userId){
	    var page = $(".paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.comment-wrap').append(loadHtml);
	    $.ajax({
	        url     : '/comment/list?tarUserId=' + userId + '&pageSize=' + daidian.config.pageSize + '&page=' + page,
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
	                var html = [];
	                comments.forEach(function(curData) {
	                	var user = curData.user || {};
	                	var service = curData.serviceInfo || {};
	                    html.push('<li class="mui-table-view-cell comment-info">');
	                    html.push('    <div class="mui-table">');
	                    
	                    if(daidian.session.user && daidian.session.user.userId == user.userId && daidian.session.user.identityAuth == 1){
	                    	html.push('		 <a action="/wx/account-service">');
	                    }else{
	                    	html.push('      <a action="/wx/user?id='+user.userId+'">');
	                    }
	                    
	                    html.push('         <div class="mui-table-cell mui-col-xs-2 mui-text-left">');
	                    html.push('            <div class="user-img"><img src="'+getPictureUrl(user.avatar,2)+'" /></div>');
	                    html.push('         </div>');
	                    html.push('      </a>');
	                    html.push('      <div class="mui-table-cell mui-col-xs-7">');
	                    html.push('          <h4 class="comment-rate">'+getRate(curData.rate)+'</h4>');
	                    html.push('          <h5 class="comment-user">'+(user.nickName)+'</h5>');
	                    html.push('      </div>');
	                    html.push('      <div class="mui-table-cell mui-col-xs-3 mui-text-right">');
	                    html.push('            <span>'+new Date(curData.operateTime).format('MM-dd')+'</span>');
	                    html.push('     </div>');
	                    html.push('   </div> ');
	                    html.push('   <div class="mui-table"> ');
	                    html.push('     <div class="mui-table-cell mui-col-xs-12">');
	                    html.push('          <p class="mui-h6">'+curData.comment+'</p>');
	                    
	                    var pictures = JSON.parse((curData.pictures || '[]')) || [];
	                    html.push('<div class="comment-image-list">');
	                    pictures.forEach(function(picture){
	                    	html.push('   <img class="mui-col-xs-3" src="'+getPictureUrl(picture,1,'list')+'"/>');
	                    });
	                    html.push('</div>');
	                    
	                    html.push('     </div>');
	                    html.push('   </div>');
	                    html.push('   <div class="mui-table">');
	                    html.push('      <div class="mui-table-cell mui-col-xs-12 mui-text-right service-title">');
	                    html.push('          <a action="/wx/service?id='+curData.serviceId+'" class="mui-h6">'+(service.title || '')+'</a>');
	                    html.push('      </div>');
	                    html.push('   </div>');
	                });
	                $('.comment-wrap').append(html.join(''));
	            } else {
	                mui.toast('加载用户评论失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载用户评论失败，请刷新页面重试');
	        }
	    });
	};
	var loadAndRenderUser = function(userId) {
		$.ajax({
	        url     : '/profile?id=' + userId+'&rmd='+new Date().getTime(),
	        success : function(data){
	            if(data && data.code == '000000') {
	            	var user = data.data || {};
	            	
	            	$('.mui-title').text('卖菜通-'+(user.nickName))
	            	var infoHtml = [];
	            	infoHtml.push('<div class="user-img"><img src="'+getPictureUrl(user.avatar,2)+'" /></div>');
	            	infoHtml.push('<h4 class="user-name">'+((user.nickName))+ '</h4>');
	            	infoHtml.push('<div class="user-number">');
	            	if(user.identityAuth && 1 == user.identityAuth){
	            		infoHtml.push('<a action="/wx/order?user='+userId+'" class="mui-btn mui-btn-outlined mui-icon mui-icon-flag">订单 '+(user.orderCount||0)+'</a>');
		            	infoHtml.push('<a action="/wx/comment?user='+userId+'" class="mui-btn mui-btn-outlined mui-icon mui-icon-chatboxes">评论 '+(user.commentCount||0)+'</a>');
	            	} else {
	            		infoHtml.push('<a action="/wx/favorite?user='+userId+'" class="mui-btn mui-btn-outlined mui-icon mui-icon-star">收藏 '+(user.favoriteCount || 0)+'</a>');
	            	}
	            	
	            	infoHtml.push('<dl class="dl-horizontal">');
	                if(user.identityAuth && 1 == user.identityAuth){
		                infoHtml.push('<dt>海外所在地</dt>');
		                infoHtml.push('<dd>'+(dataconfig.countrys[user.country] || {}).name+'</dd>');
	                }

	                if(user.hometown){
	                	infoHtml.push('<dt>家乡</dt>');
	                	infoHtml.push('<dd>'+user.hometown+'</dd>');
	                }

	                if(user.age){
	                	infoHtml.push('<dt>年龄段</dt>');
	                	infoHtml.push('<dd>'+daidian.config.ageRange[user.age] || ''+'</dd>');
	                }

	                if(user.constellation) {
	                	infoHtml.push('<dt>星座</dt>');
	                	infoHtml.push('<dd>'+daidian.config.constellationConfig[user.constellation]||''+'</dd>');
	                }

	                if(user.identityAuth && 1 == user.identityAuth && user.identity){
	                	infoHtml.push('<dt>身份</dt>');
	                	infoHtml.push('<dd>'+(daidian.config.identityType[user.identity] || user.identity) +'</dd>');
	                }
	                infoHtml.push('</dl>');
	            	infoHtml.push('</div>');	                
	                $('.user-profile').append(infoHtml.join(''));
	                
	                if(user.identityAuth && 1 == user.identityAuth) {
	                	$('.user-service-wrap').show();
	                	loadAndRenderUserService(userId);
	                }
	                
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
	    var userId = getQueryString('id') || 'o853pt1EmuCezWwbYNAQWNU9Phqw';
	    loadAndRenderUser(userId);
	    
	    $('body').on('tap','.user-nav',function(evt){
	        var target = $(evt.target);
	        if(!target.hasClass('mui-tab-item')) {
	            target = target.closest('.mui-tab-item');
	        }
	
	        if(target.hasClass('contact')) {
	            window.location.href="/wx/message?to="+userId+"&rmd="+new Date().getTime();
	        } else if(target.hasClass('add-contact')) {
	        	var account = {};
	        	account.contactUserId = userId;
	            target.text('正在添加').attr('disabled',true);
	            $.ajax({
	                type    :   "post",
	                url     :   "/user/contact",
	                contentType : "application/json;charset=UTF-8",
	                data : JSON.stringify(account),
	                success : function(data){
	                    target.text('加为好友').attr('disabled',false);
	                    if(data.code == '000000') {
	                        mui.toast('添加成功');
	                        target.remove();
	                    } else {
	                        mui.toast(data.msg);
	                    }
	                },
	                error : function(data){
	                    target.text('加为好友').attr('disabled',false);
	                    mui.toast(data.msg);
	                }
	            });
	        }
	    });
	
	    $('.paging-bar').click(function(evt) {
	        evt.preventDefault();
	        loadAndRenderUserComment(userId);
	    });
	    $('.paging-bar').trigger('click');
	});
</script>
<%@ include  file="common/footer.jsp"%>