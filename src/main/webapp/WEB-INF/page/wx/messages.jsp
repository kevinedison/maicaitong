<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">带点啥-消息</h1>
    <a action="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<nav class="mui-bar mui-bar-tab bottom-nav">
    <a class="mui-tab-item top" action="/wx/top">
        <span class="mui-icon mui-icon-home"></span>
        <span class="mui-tab-label">首页</span>
    </a>
    <a class="mui-tab-item needs" action="/wx/needs">
        <span class="mui-icon mui-icon-bars"></span>
        <span class="mui-tab-label">求购</span>
    </a>
    <a class="mui-tab-item buyers" action="/wx/buyers">
        <span class="mui-icon mui-icon-contact"></span>
        <span class="mui-tab-label">买手</span>
    </a>
    <a class="mui-tab-item messages mui-active" action="/wx/messages">
        <span class="mui-icon mui-icon-chatbubble"></span>
        <span class="mui-tab-label">消息</span>
    </a>
    <a class="mui-tab-item account" action="/wx/account">
        <span class="mui-icon mui-icon-gear"></span>
        <span class="mui-tab-label">我的</span>
    </a>
</nav>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control  chat-tabs">
            <a class="mui-control-item mui-active" href="#message">消息</a>
            <a class="mui-control-item" href="#contact">通讯录</a>
        </div>

        <div class="mui-slider-group">
            <div id="message" class="mui-slider-item mui-control-content mui-active">
                <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed"></ul>
            </div>
            <div id="contact" class="mui-slider-item mui-control-content">
                <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed"></ul>
            </div>
        </div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
    var loadAndRenderMessage = function() {
    	var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('#message ul').append(loadHtml);
    	$.ajax({
            type    :   "get",
            url     :   "/user/message/list?page=1&pageSize=9999&rmd="+new Date().getTime(),
            success :   function(data){
            	$('#message .mui-loading').remove();
                if(data.code == '000000') {
                	if(!data.data.count || data.data.count == 0) {
	                    $('#message ul').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有消息</li>');
	                    return;
	                }
                	
                	var messages = data.data.data || [];
	                var messageHtml = [];
	                messages.forEach(function(message) {
	                	var user = message.contactInfo || {};
	                	messageHtml.push('<li class="mui-table-view-cell">');
		                messageHtml.push('    <div class="mui-slider-right mui-disabled">');
		                messageHtml.push('        <a class="mui-btn mui-btn-red message-relation-delete" targetId="'+message.id+'">删除</a>');
		                messageHtml.push('    </div>');
		                messageHtml.push('    <div class="mui-slider-cell mui-slider-handle">');
		                messageHtml.push('        <a action="/wx/message?id='+message.id+'&to='+message.contactUserId+'">');
		                messageHtml.push('            <div class="oa-contact-cell mui-table">');
		                messageHtml.push('                <div class="oa-contact-avatar mui-table-cell">');
		                if (message.unread && message.unread > 0){
		                	messageHtml.push('					  <span class="mui-badge">'+message.unread+'</span>');
		                }
		                messageHtml.push('                    <img src="'+getPictureUrl(user.avatar,2)+'" />');
		                messageHtml.push('                </div>');
		                messageHtml.push('                <div class="oa-contact-content mui-table-cell">');
		                messageHtml.push('                    <div class="mui-clearfix">');
		                messageHtml.push('                        <h4 class="oa-contact-name">'+(user.nickName)+'</h4>');
		                messageHtml.push('                        <span class="oa-contact-position mui-h6" style="padding-left:10px;">'+(((dataconfig.countrys[user.country] || {}).name || user.hometown) || '')+'</span>');
		                messageHtml.push('                        <span class="message-time mui-h6">'+new Date(message.updateTime).format('MM-dd')+'</span>');
		                messageHtml.push('                    </div>');
		                var content = (message.recentMessage || '');
		                if(message.messageType == 'image') {
		                	content = '<span style="font-size:13px;" class="mui-icon mui-icon-image">  图片消息 <span>';
		                }
		                
		                messageHtml.push('                    <p class="oa-contact-email mui-ellipsis mui-h6">'+content+'</p>');
		                messageHtml.push('                </div>');
		                messageHtml.push('            </div>');
		                messageHtml.push('        </a>');
		                messageHtml.push('    </div>');
		                messageHtml.push('</li>');
	                });
	                $('#message ul').append(messageHtml.join(''));
                } else {
                	mui.toast('加载用户消息失败');
                }
            },
            error   :   function(data){
                mui.toast('加载用户消息失败');
            }
        });
    };

    var loadAndRenderContact = function() {
    	var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('#contact ul').append(loadHtml);
    	$.ajax({
            type    :   "get",
            url     :   "/user/contact/list?page=1&pageSize=9999&rmd="+new Date().getTime(),
            success :   function(data) {
            	$('#contact .mui-loading').remove();
                if(data.code == '000000') {
                	if(!data.data.count || data.data.count == 0) {
	                    $('#contact ul').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有好友</li>');
	                    return;
	                }
                	var contacts = data.data.data || [];
	                var contactHtml = [];
	                contacts.forEach(function(contact) {
	                	var user  = contact.contactInfo || {};
	                	contactHtml.push('<li class="mui-table-view-cell">');
	                	contactHtml.push('    <div class="mui-slider-right mui-disabled">');
	                	
	                	
	                	if(daidian.session.user && daidian.session.user.userId == user.userId && daidian.session.user.identityAuth == 1){
	                		contactHtml.push('		  <a action="/wx/account-service">');
	                    }else{
	                    	contactHtml.push('        <a class="mui-btn mui-btn-grey" action="/wx/user?id='+user.userId+'">首页</a>');
	                    }
	                	
	                	contactHtml.push('        <a class="mui-btn mui-btn-yellow" action="/wx/message?to='+user.userId+'">发消息</a>');
	                	contactHtml.push('        <a class="mui-btn mui-btn-red contact-delete" targetId="'+contact.id+'">删除</a>');
	                	contactHtml.push('    </div>');
	                	contactHtml.push('    <div class="mui-slider-cell mui-slider-handle">');
	                	contactHtml.push('        <div class="oa-contact-cell mui-table ">');
	                	contactHtml.push('            <div class="oa-contact-avatar mui-table-cell">');
	                	contactHtml.push('                <img src="'+getPictureUrl(user.avatar,2)+'" />');
	                	contactHtml.push('            </div>');
	                	contactHtml.push('            <div class="oa-contact-content mui-table-cell">');
	                	contactHtml.push('                <div class="mui-clearfix">');
	                	contactHtml.push('                    <h4 class="oa-contact-name">'+(user.nickName)+'</h4>');
	                	
	                	var identify = (user.identityAuth == 1) ? (daidian.config.identityType[user.identity] || user.identity) : '买家';
	                	var country = ((dataconfig.countrys[user.country] || {}).name ||  '').name || user.hometown || '';
	                	contactHtml.push('                    <span class="oa-contact-position mui-h6" style="padding-left:8px;">'+country + ' ' +identify+'</span>');
	                	contactHtml.push('                </div>');
	                	contactHtml.push('                <p class="oa-contact-email mui-h6">'+(user.introduce || '')+'</p>');
	                	contactHtml.push('            </div>');
	                	contactHtml.push('        </div>');
	                	contactHtml.push('    </div>');
	                	contactHtml.push('</li>');
	                });
	                $('#contact ul').append(contactHtml.join(''));
                } else {
                    mui.toast('加载好友列表失败');
                }
            },
            error   :   function(data){
                mui.toast('加载好友列表失败');
            }
        });
    };
	$(function() {
		if(!checkSession()) {return;};
		
	    $('body').on('tap','.message-relation-delete',function(evt){
	        var target = $(evt.target);
	        var targetId = target.attr('targetId');
	        $.ajax({
	            type    :   "delete",
	            url     :   "/user/messagerel?id="+targetId+"&rmd="+new Date().getTime(),
	            success :   function(data){
	                if(data.code == '000000') {
	                    mui.toast('删除成功');
	                    target.closest('.mui-table-view-cell').slideUp(500);
	                } else {
	                    mui.toast(data.msg);
	                }
	            },
	            error   :   function(data){
	                mui.toast(data.msg);
	            }
	        });
	    });
	
	    $('body').on('tap','#contact .mui-table-view-cell',function(evt){
	        var target = $(evt.target);
	        if(!target.hasClass('mui-table-view-cell')) {
	            target = target.closest('.mui-table-view-cell');
	        }
	        mui.swipeoutOpen(target[0], 'right');
	    });
	
	    $('body').on('tap','.contact-delete', function (evt) {
	        var target = $(evt.target);
	        var targetId = target.attr('targetId');
	        $.ajax({
	            type    :      "delete",
	            url     :      "/user/contact?id="+targetId+"&rmd="+new Date().getTime(),
	            success :      function(data){
	                if(data.code == '000000'){
	                    mui.toast('删除成功');
	                    target.closest('.mui-table-view-cell').slideUp(500);
	                } else {
	                    mui.toast(data.msg);
	                }
	            },
	            error   :   function(data){
	                mui.toast(data.msg);
	            }
	        });
	    });
	    loadAndRenderMessage();
	    loadAndRenderContact();
	});
</script>
<%@ include  file="common/footer.jsp"%>