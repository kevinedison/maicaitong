<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<link href="/resources/wx/css/mui.picker.css" rel="stylesheet" />
<link href="/resources/wx/css/mui.poppicker.css" rel="stylesheet" />
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-菜农认证</h1>
    <a href="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<div class="mui-content">
    <div class="buyer-identifying" style="display:none">菜农认证中</div>
    <form class="mui-input-group account-edit">
        <div class="mui-input-row">
            <label>真实名称</label>
            <input type="text" id="real_name" placeholder="真实名称">
        </div>
        <div class="mui-text-right notice-msg">
            <p>用真实姓名才能通过菜农认证</p>
        </div>
        <!--div class="mui-input-row">
            <label>菜农身份</label>
            <select class="user-identity-row" id="user_identity" style="direction: rtl;padding-right:10px;"></select>
        </div>
        <div class="mui-input-row customize_user_identity" style="display:none">
            <label>自定义身份</label>
            <input  type="text" placeholder="自定义身份">
        </div>
        <div class="mui-text-right notice-msg">
            <p>用真实身份才能能更好获取买家信任</p>
        </div-->
        <div class="mui-input-row">
            <label>所在省市</label>
            <input id='cityPicker' type="text" placeholder="所在省市" readonly="readonly">
        </div>
        <div class="mui-text-right notice-msg">
            <p>选择目前所在省市,为买家提供最贴切服务</p>
        </div>
        <div class="mui-input-row">
            <label>证件号码</label>
            <input type="text" id="certificate_no" placeholder="证件号码">
        </div>
        <div class="mui-input-row" style="height:100%">
            <label>证件图片</label>
            <img class="image-item image-upload" src="/resources/wx/images/iconfont-tianjia.png" number="single" id="certificate_pic"  style="width:120px;height:120px;">
        </div>
        <div class="mui-text-right notice-msg">
            <p>身份证、护照号码及清晰正面照片(480*480正方形)。目前只支持二代身份证、护照。您的个人信息只作为实名认证时使用，我们将严格为您保密，保护您的个人隐私。</p>
        </div>
        <div class="row user-intro-row">
            <label style="padding-left:15px;">自我介绍</label>
            <textarea id="user-intro" rows="5" placeholder="自我介绍" maxlength="120"></textarea>
        </div>
        <div class="mui-text-right notice-msg">
            <p>简单的自我介绍，增加买家的信任感，最多100字</p>
        </div>
        <div class="mui-input-row mui-checkbox mui-left">
			<label>已阅并同意遵守菜农协议</label>
			<input name="checkbox" type="checkbox" class="buyer-agreement-check">
		</div>
					
        <div class="mui-button-row">
            <button type="button" id="buyer_auth_commit" class="mui-btn mui-btn-primary" disabled="disabled">保存</button>
        </div>

        <div class="buyer-agreement"><a href="/wx/agreement-buyer" class="text-center local-info"> 「查看服务协议」 </a></div>
    </form>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/mui.picker.js"></script>
<script src="/resources/wx/js/mui.poppicker.js"></script>
<script src="/resources/wx/js/ajaxfileupload.js"></script>
<script src="/resources/wx/js/dataconfig.js"></script>
<script src="/resources/wx/js/country.data.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var initPage = function() {		
		var iHtml = [];
		for(var i in daidian.config.identityType) {
			iHtml.push('<option value="'+i+'">'+daidian.config.identityType[i]+'</option>');
		}
		iHtml.push('<option value="customize">自定义</option>');
		$('#user_identity').append(iHtml.join(''));
	};

	var renderUser = function(user) {
		if(user.identityAuth == 2 ) {
			$('.buyer-identifying').fadeIn(150);
		} else if(user.identityAuth == 3) {
			$('.buyer-identifying').fadeIn(150).text((user.remark || '认证信息有误'));
		}
		
		$('#real_name').val(user.realName || '');
		if(user.identity) {
			$('#user_identity').val(user.identity);
		}
		if(user.country) {
			var country = ((dataconfig.countrys[user.country] || {}).name || '');
			$('#cityPicker').val(country).attr('data',user.country);
		}
		
		$('#certificate_no').val(user.certificateNo || '');
		if(user.certificate){
			$('#certificate_pic').attr('pic-path',user.certificate).attr('src',getPictureUrl(user.certificate,5,'list'));
		}
		
		$('#user-intro').val((user.introduce || ''));
    };
    
	$(function () {
		initPage();
		if(!checkSession()) {return;};
    	var user = daidian.session.user;
    	
	    var cityPicker =  new mui.PopPicker({layer: 1});
	    cityPicker.setData(countryData);
	    $('#cityPicker').click(function(evt) {
	        cityPicker.show(function(items) {
	            var result = (items[0] || {}).text;
	            var code = (items[0] || {}).value;
	            $('#cityPicker').val(result).attr('data',code);
	        });
	    });
	
	    $('.buyer-agreement-check').change(function(evt){
	    	if($('.buyer-agreement-check').prop('checked')) {
	    		$('#buyer_auth_commit').attr('disabled',false);
	    	} else {
	    		$('#buyer_auth_commit').attr('disabled',true);
	    	}
	    });
	    
	    $('#buyer_auth_commit').click(function(evt){
	        $('.account-edit').find('.input-error').removeClass('input-error');
	        
	        var account = {};
	        account.realName = $('#real_name').val() || '';
	        if(account.realName.trim().length == 0) {
	            mui.toast('真实姓名不能为空');
	            $('#real_name').addClass('input-error');
	            return;
	        }
	
	        account.identity = $('#user_identity').val() || '';
	        if(account.identity == 'customize'){
	        	var cus_identity = $('.customize_user_identity input').val() || '';
	        	if(cus_identity.trim().length == 0) {
	        		mui.toast('自定义身份不能为空');
		            $('.customize_user_identity input').addClass('input-error');
		            return;
	        	}
	        	account.identity = cus_identity;
	        }
	        account.country = $('#cityPicker').attr('data') || '';
	        if(account.country.trim().length == 0) {
	            mui.toast('海外城市不能为空');
	            $('#cityPicker').addClass('input-error');
	            return;
	        }
	        
	        account.certificateNo = $('#certificate_no').val() || '';
	        if(account.certificateNo.trim().length == 0) {
	            mui.toast('证件号码不能为空');
	            $('#certificate_no').addClass('input-error');
	            return;
	        }
	        account.certificate = $('#certificate_pic').attr('pic-path');
	        if(account.certificate.trim().length == 0) {
	            mui.toast('证件图片不能为空');
	            $('#certificate_pic').addClass('input-error');
	            return;
	        }
	        account.introduce = $('#user-intro').val();
	        if(account.certificate.trim().length == 0) {
	            mui.toast('自我介绍不能为空');
	            $('#user-intro').addClass('input-error');
	            return;
	        }
	        
	        var btn = $('#buyer_auth_commit');
	        btn.text('正在保存').attr('disabled',true);
	
	        $.ajax({
	           type : "put",
	           url  : "/user/buyerauth?rmd="+new Date().getTime(),
	           contentType : "application/json;charset=UTF-8",
	           data        : JSON.stringify(account),
	           success     : function(data){
	                btn.text('保存').attr('disabled',false);
	                if(data.code == '000000') {
	                    mui.toast('保存认证成功');
	                    window.location.href= "/wx/account?rmd="+new Date().getTime();
	                } else {
	                    mui.toast('实名认证失败');
	                }
	           },
	           error     : function() {
	               btn.text('保存').attr('disabled',false);
	               mui.toast('实名认证出现异常');
	           }
	       });
	    });
	    
	    $('#user_identity').change(function(evt){
	    	if($(this).val() == 'customize') {
	    		$('.customize_user_identity').fadeIn(200);
	    	} else {
	    		$('.customize_user_identity').fadeOut(200);
	    	}
	    });
	    
	    renderUser(user);
	});
</script>
<%@ include  file="common/footer.jsp"%>