<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<style>
    html, body { height: 100%;  margin: 0px;  padding: 0px;  overflow: hidden;  -webkit-touch-callout: none;  -webkit-user-select: none;  }
    footer {position: fixed;width: 100%;height: 50px;min-height: 50px;border-top: solid 1px #bbb;left: 0px;bottom: 0px;overflow: hidden;padding: 0px 50px;background-color: #fafafa;}
    .footer-left {position: absolute;width: 50px;height: 50px;left: 0px;bottom: 0px;text-align: center;vertical-align: middle;line-height: 100%;padding: 12px 4px;}
    .footer-right {position: absolute;width: 50px;height: 50px;right: 0px;bottom: 0px;text-align: center;vertical-align: middle;line-height: 100%;padding: 12px 5px;display: inline-block;}
    .footer-center {height: 100%;padding: 5px 0px;}
    .footer-center [class*=input] {width: 100%;height: 100%;border-radius: 5px;}
    .footer-center .input-text {background: #fff;border: solid 1px #ddd;padding: 10px !important;font-size: 16px !important;line-height: 18px !important;font-family: verdana !important;overflow: hidden;}
    footer .mui-icon {color: #000;}
    footer .mui-icon:active {color: #007AFF !important;}

    .mui-content {height: 100%;padding: 44px 0px 50px 0px;overflow: auto;background-color: #eaeaea;}
    #msg-list {height: 100%;padding-top:5px;}
    .msg-item {padding: 8px;clear: both;}
    .msg-item .mui-item-clear {clear: both;}
    .msg-item .msg-user-img{width: 38px;height: 38px;display: inline-block;border-radius: 50%;vertical-align: top;text-align: center;float: left;color: #ddd;}
    .msg-item .msg-content {display: inline-block;border-radius: 5px;border: solid 1px #d3d3d3;background-color: #FFFFFF;color: #333;padding: 8px;vertical-align: top;font-size: 15px;position: relative;margin: 0px 8px;max-width: 75%;min-width: 35px;float: left;}
    .msg-item .msg-content .msg-content-inner {word-wrap: break-word;}
    .msg-item .msg-content .msg-content-arrow {position: absolute;border: solid 1px #d3d3d3;border-right: none;border-top: none;background-color: #FFFFFF;width: 10px;height: 10px;left: -5px;top: 12px;-webkit-transform: rotateZ(45deg);transform: rotateZ(45deg);}

    .msg-item-self .msg-user-img, .msg-item-self .msg-content {float: right;}
    .msg-item-self .msg-content .msg-content-arrow {left: auto;right: -5px;-webkit-transform: rotateZ(225deg);transform: rotateZ(225deg);}
    .msg-item-self .msg-content, .msg-item-self .msg-content .msg-content-arrow {background-color: #fff;color: #666;border-color: #e8e8e8;}
    .cancel {background-color: darkred;}

    .msg-time-item{display: block;text-align: center;}
    .msg-time{background-color: #ddd; color: #fff;  display: inline-block;  border-radius: 8px;  padding: 0px 10px;}
    .message-paging-bar{margin-top:5px;}

    input[type="file"] {position: absolute;left: 0px;top: 0px;width: 100%;height: 100%;opacity: 0;cursor: pointer;z-index: 0;  }
    .image-upload-process{width: 100%;display: block;position: absolute;text-align: center;top: 25px;left: 0;color: #666;font-size: 16px;font-weight: bold;}
</style>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">消息</h1>
</header>
<div class="mui-content">
    <div class="mui-text-center"><span class="mui-badge mui-badge-blue message-paging-bar" page=0 style="display: none">更多消息</span></div>
    <div id='msg-list'></div>
</div>
<footer>
    <div class="footer-left">
        <i id='msg-image' number="message" class="mui-icon mui-icon-image image-upload" style="font-size: 22px;"></i>
    </div>
    <div class="footer-center">
        <textarea id='msg-text' type="text" class='input-text'></textarea>
    </div>
    <label for="" class="footer-right">
        <i id='msg-type' class="mui-icon mui-icon-paperplane" style="font-size: 28px;"></i>
    </label>
</footer>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/ajaxfileupload.js"></script>
<script src="/resources/wx/js/mui.zoom.js"></script>
<script src="/resources/wx/js/mui.previewimage.js"></script>
<script type="text/javascript">
	var loadMsgList = function(id,to){
	    var page = $(".message-paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	    var queryUrl = "/user/message";
	    if (id && to){
	        queryUrl += "?relationId="+id+"&toUser="+to;
	    }else if(id){
	        queryUrl += "?relationId="+id;
	    }else if(to){
	        queryUrl += "?toUser="+to;
	    }
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('#msg-list').append(loadHtml);
	    $.ajax({
	        type    : "get",
	        url     : queryUrl+"&page="+page+"&pageSize="+daidian.config.pageSize+"&rmd="+new Date().getTime(),
	        success : function(data){
	            $('#msg-list .mui-loading').remove();
	            if (data && data.code == '000000'){
	                if(!data.data || !data.data.count || data.data.count == 0) {
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $('.message-paging-bar').show().attr('page',page);
	                } else {
	                    $('.message-paging-bar').hide();
	                }
	
	                var messages = data.data.data || [];
	                var msgHtml = [];
	                messages.forEach(function(curData) {
	                    if (curData.message_time){
	                        msgHtml.push('<div class="msg-time-item"><div class="msg-time">'+new Date(curData.operateTime).format('yyyy-MM-dd hh:mm:ss')+'</div></div>');
	                    }
	                    var content = curData.message;
	                    if(curData.messageType == 'image') {
	                    	content = '<img src="'+getPictureUrl(content,3,'list')+'" style="height:100px;width: 100px;" data-preview-src="'+getPictureUrl(content,3)+'" data-preview-group="1">';
	                    }
	                    
	                    if((daidian.session.user.userId != curData.senderId)){
	                        msgHtml.push('  <div class="msg-item self">');
	                        msgHtml.push('      <img class="msg-user-img" src="'+getPictureUrl(curData.sender.avatar,2)+'" alt="" />');
	                        msgHtml.push('      <div class="msg-content">');
	                        msgHtml.push('          <div class="msg-content-inner">'+content+'</div>');
	                        msgHtml.push('          <div class="msg-content-arrow"></div>');
	                        msgHtml.push('      </div>');
	                        msgHtml.push('      <div class="mui-item-clear"></div>');
	                        msgHtml.push('  </div>');
	                    }else{
	                        msgHtml.push('  <div class="msg-item msg-item-self">');
	                        msgHtml.push('      <img class="msg-user-img" src="'+getPictureUrl(curData.sender.avatar,2)+'" alt="" />');
	                        msgHtml.push('      <div class="msg-content">');
	                        msgHtml.push('          <div class="msg-content-inner">'+content+'</div>');
	                        msgHtml.push('          <div class="msg-content-arrow"></div>');
	                        msgHtml.push('      </div>');
	                        msgHtml.push('      <div class="mui-item-clear"></div>');
	                        msgHtml.push('  </div>');
	                    }
	                });
	                $('#msg-list').prepend(msgHtml.join(' '));
	                
	                mui.previewImage();
	                if(page == 1) {
	                	$('#msg-list').scrollTop( $('#msg-list')[0].scrollHeight );
	                }
	            }else{
	                mui.toast("加载消息失败");
	            }
	        },
	        error   : function(){
	            mui.toast("加载消息列表失败");
	        }
	    });
	};
	
	var addNewMessage = function(message) {
	    var self_avatar = (daidian.session.user || {}).avatar;
	    var msgHtml = [];
	    if(message.messageType == 'text') {
	        msgHtml.push('  <div class="msg-item msg-item-self">');
	        msgHtml.push('      <img class="msg-user-img" src="'+getPictureUrl(self_avatar,2)+'" alt="" />');
	        msgHtml.push('      <div class="msg-content">');
	        msgHtml.push('          <div class="msg-content-inner">'+message.message+'</div>');
	        msgHtml.push('          <div class="msg-content-arrow"></div>');
	        msgHtml.push('      </div>');
	        msgHtml.push('      <div class="mui-item-clear"></div>');
	        msgHtml.push('  </div>');
	    } else if(message.messageType == 'image') {
	        msgHtml.push('  <div class="msg-item msg-item-self">');
	        msgHtml.push('      <img class="msg-user-img" src="'+getPictureUrl(self_avatar,2)+'" alt="" />');
	        msgHtml.push('      <div class="msg-content">');
	        msgHtml.push('          <div class="msg-content-inner"><img src="" id="'+message.messageDomId+'" style="height:100px;width: 100px;"></div>');
	        msgHtml.push('          <div class="msg-content-arrow"></div>');
	        msgHtml.push('      </div>');
	        msgHtml.push('      <div class="mui-item-clear"></div>');
	        msgHtml.push('  </div>');
	    }
	    $('#msg-list').append(msgHtml.join(' '));
	};
	
	var saveMessage = function(message) {
		$('#msg-text').val('');
		var buttons = $();
		$.ajax({
            type    :   "post",
            url     :   "/user/message",
            contentType : "application/json;charset=UTF-8",
            data : JSON.stringify(message),
            success : function(data){
                $('#msg-list').scrollTop( $('#msg-list')[0].scrollHeight );
                if(data.code == '000000') {
                    mui.toast('发送成功');
                } else {
                    mui.toast('发送出现异常');
                }
            },
            error : function(){
                mui.toast('发送错误');
            }
        });
	};
	
	var loadReceiver = function(userId) {
		$.ajax({
	        url     : '/profile?id=' + userId+'&rmd='+new Date().getTime(),
	        success : function(data){
	            if(data && data.code == '000000') {
	            	var user = data.data || {};
	            	var name = user.nickName || '';
	            	$('.mui-title').text(name);
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
		if(!checkSession()) {return;};
		
		var id = getQueryString('id');
		var to = getQueryString('to');
		if(to) {
			loadReceiver(to);
		}
		
	    $('body').on('tap','#msg-type',function(evt){
	        var content = $('#msg-text').val() || '';
	        if(content.trim().length == 0) {
	        	mui.toast('消息内容为空');
	        	return;
	        }
	        var message = {};
	        message.relationId = getQueryString('id');
	        message.receiverId = getQueryString('to');
	        message.message = content;
	        message.messageType='text';
	        addNewMessage(message);
	        saveMessage(message);
	    });
	
	    $('body').on('tap', '.image-upload[number=message]', function(evt) {
	        var target = $(evt.target);	
	        var message = {};
	        message.messageDomId = new Date().getTime() + Math.round(4);
	        message.relationId = getQueryString('id');
	        message.receiverId = getQueryString('to');
	        
	        
	        message.messageType='image';
	        wx.chooseImage({
	    	    count: 1, // 默认9
	    	    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    	    success: function (res) {
	    	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	    	        if(!localIds || localIds.length == 0) {
	    	        	mui.toast('没有选中任何图片');
	    	        	return;
	    	        }
	    	        var localId = localIds[0];
	    	        
	    	        message.message = localId;
	    	        addNewMessage(message);
	    	        
	    	        wx.uploadImage({
	    	            localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
	    	            isShowProgressTips: 1, // 默认为1，显示进度提示
	    	            success: function (res) {
	    	                var serverId = res.serverId; // 返回图片的服务器端ID
	    	                $.ajax({
	    	  	      	      type : "get",
	    	  	      	      url  : "/user/wx/file/download?id="+serverId,
	    	  	      	      success     : function(data){
	    	  	      	        if(data.code == '000000') {
	    	  	      	          mui.toast('发送成功');
		    	  	      	      var showPath = daidian.config.imageAccessUrl + data.data;
		  	                      $('#' + message.messageDomId).attr('src',showPath);
		  	                      
		  	                      message.message = data.data;
		  	                      saveMessage(message);
	    	  	      	        } else {
	    	  	      	          mui.toast('上传图片失败,失败原因:'+data.msg);
	    	  	      	        }
	    	  	      	      },
	    	  	      	      error     : function() {
	    	  	      	        mui.toast('上传图片出现异常,请重试');
	    	  	      	      }
	    	  	      	    });
	    	            }
	    	        });
	    	    }
	    	});
	    });
	
	
	    $('body').on('tap','.message-paging-bar',function(evt){
	        evt.preventDefault();
	        loadMsgList(id,to);
	    });
	
	    $('.message-paging-bar').trigger('tap');
	});
</script>
<%@ include  file="common/footer.jsp"%>