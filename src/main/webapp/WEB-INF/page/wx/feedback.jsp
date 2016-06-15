<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<style>
    body{background-color: #efeff4;}
    .feedback p { padding: 10px 15px 0;}
    .feedback textarea {height: 100px;margin-bottom: 0 !important;padding-bottom: 0 !important;border: none !important;text-align: left!important;}
    .feedback button#submit {width: 90%; height: 46px;left: 50%; -webkit-transform: translate(-50%);  margin-top: 15px;}
    .feedback .image-item{margin-bottom:0px!important;margin-top: 8px;}
</style>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-买家协议</h1>
    <a href="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<div class="mui-content">
    <div class="feedback service-add">
		<p>问题和意见</p>
		<div class="row mui-input-row">
			<textarea id='question' class="mui-input-clear question" placeholder="请详细描述你的问题和意见..."></textarea>
		</div>
		<p>图片(选填,提供问题截图)</p>
	    <div class="row image-list" style="min-height:85px;height:auto;background-color:#fff">
            <div class="image-item image-upload" number="multi" style="background-image: url('/resources/wx/images/iconfont-tianjia.png')"></div>
        </div>
		<button id='submit' type="button" class="mui-btn mui-btn-green">提交</button>
	</div>
</div>
<%@ include  file="common/js.jsp"%>
<script type="text/javascript">
      $('#submit').click(function(evt){
	        $('.feedback').find('.input-error').removeClass('input-error');
	        
	        var feedback = {};
	        feedback.question = $('#question').val() || '';
	        if(feedback.question.trim().length == 0) {
	            mui.toast('反馈意见不能为空');
	            $('#question').addClass('input-error');
	            return;
	        }
	
	        var picture = [];
	        $('.image-list').each(function(index,dom){
	            $(dom).find('.image-item').each(function(index,input){
	                if($(input).attr('pic-path')) {
	                    picture.push($(input).attr('pic-path'));
	                }
	            });
	        });
	        feedback.picture = JSON.stringify(picture);
	        
	        var btn = $('#submit');
	        btn.text('正在保存').attr('disabled',true);
	
	        $.ajax({
	           type : "post",
	           url  : "/user/feedback?rmd="+new Date().getTime(),
	           contentType : "application/json;charset=UTF-8",
	           data        : JSON.stringify(feedback),
	           success     : function(data){
	                btn.text('保存').attr('disabled',false);
	                if(data.code == '000000') {
	                    mui.toast('保存成功，非常感谢你提出的宝贵意见');
	                    window.location.href= "/wx/top?rmd="+new Date().getTime();
	                } else {
	                    mui.toast('保存失败');
	                }
	           },
	           error     : function() {
	               btn.text('保存').attr('disabled',false);
	               mui.toast('保存出现异常');
	           }
	       });
	    });
</script>	    
<%@ include  file="common/footer.jsp"%>