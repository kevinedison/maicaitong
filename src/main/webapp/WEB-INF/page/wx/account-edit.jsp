<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<link href="/resources/wx/css/mui.picker.css" rel="stylesheet" />
<link href="/resources/wx/css/mui.poppicker.css" rel="stylesheet" />
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-个人资料完善</h1>
    <a href="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<div class="mui-content">
    <form class="mui-input-group account-edit">
        <div class="row user-avatar-row">
            <label style="padding-left:15px;padding-top:10px;">头像</label>
            <img class="image-item image-upload" number="single" src="/resources/wx/images/iconfont-tianjia.png" />
        </div>
        <div class="mui-text-right notice-msg">
            <p>头像不能超过1M,大小为160*160的正方形</p>
        </div>
        <div class="mui-input-row">
            <label>昵称</label>
            <input type="text"  placeholder="请输入名称" id="user-name">
        </div>
        <div class="mui-input-row user-gender-row">
            <label>性别</label>
            <div class="mui-switch mui-active" id="user-gender">
                <div class="mui-switch-handle"></div>
            </div>
        </div>
        <div class="mui-input-row">
            <label>家乡</label>
            <input id='cityPicker' type="text" placeholder="选择家乡">
        </div>
        <div class="mui-input-row user-age-row">
            <label>年龄段</label>
            <select id="user-age" style="direction: rtl;padding-right:10px;"></select>
        </div>
        <div class="mui-input-row user-constellation-row">
            <label>星座</label>
            <select id="user-constellation" style="direction: rtl;padding-right:10px;">
            </select>
        </div>
        <div class="row user-intro-row">
            <label style="padding-left:15px;">自我介绍</label>
            <textarea id="user-intro" rows="5" placeholder="自我介绍" maxlength="120"></textarea>
        </div>
        <div class="mui-text-right notice-msg">
            <p>自我介绍不要超过100个字</p>
        </div>
        <div class="mui-button-row">
            <button type="button"  class="mui-btn mui-btn-primary user-info-submit">保存</button>
        </div>
    </form>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/mui.picker.js"></script>
<script src="/resources/wx/js/mui.poppicker.js"></script>
<script src="/resources/wx/js/ajaxfileupload.js"></script>
<script src="/resources/wx/js/city.data-3.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">

    var initPage = function() {
    	var ageHtml = [];
    	for(var a in daidian.config.ageRange) {
    		ageHtml.push('<option value="'+a+'">'+daidian.config.ageRange[a]+'</option>');
    	}
    	$('#user-age').append(ageHtml.join(''));
    	
    	var coHtml = [];
    	for(var c in daidian.config.constellationConfig) {
    		coHtml.push('<option value="'+c+'">'+daidian.config.constellationConfig[c]+'</option>');
    	}
    	$('#user-constellation').append(coHtml.join(''));
    };
    
    var renderUser = function(user) {
    	$('.user-avatar-row .image-item').attr('pic-path',user.avatar).attr('src', getPictureUrl(user.avatar,2));
    	$('#user-name').val(user.nickName || '');
    	if(user.gender == 1) {
    		$('#user-gender').addClass('mui-active');
    	} else {
    		$('#user-gender').removeClass('mui-active');
    	}
    	
    	if(user.hometown) {
    		$('#cityPicker').val(user.hometown);
    	}
	    
	    $('#user-age').val(user.age || 'age');
	    $('#user-constellation').val(user.constellation || 'aries');
	    $('#user-intro').val((user.introduce || ''));
    };
    
	$(function () {
		initPage();
		
		if(!checkSession()) {return;};
    	
    	var user = daidian.session.user;
    	
	    var cityPicker =  new mui.PopPicker({layer: 3});
	    cityPicker.setData(cityData3);
	    $('#cityPicker').click(function(evt) {
	        cityPicker.show(function(items) {
	            var result = (items[0] || {}).text + " " + (items[1] || {}).text + " " + (items[2] || {}).text;
	            $('#cityPicker').val(result);
	        });
	    });
	
	    $('.user-info-submit').click(function(evt) {
	       $('.account-edit').find('.input-error').removeClass('input-error');
	       var account = {};
	       account.avatar = $('.user-avatar-row .image-item').attr('pic-path') || '';
	       account.nickName = $('#user-name').val() || '';
	       if(account.nickName.trim().length == 0) {
	           mui.toast('昵称不能为空');
	           $('#user-name').addClass('input-error');
	           return;
	       }
	       account.gender = $('#user-gender').hasClass('mui-active') ? '1':'0';
	       account.hometown = $('#cityPicker').val() || '';
	       account.age = $('#user-age').val();
	       account.constellation = $('#user-constellation').val();
	       account.introduce = $('#user-intro').val();
	
	       var btn = $('.user-info-submit');
	       btn.text('正在保存').attr('disabled',true);
	       $.ajax({
	            type    :   "put",
	            url     :   "/user/info?rmd="+new Date().getTime(),
	            contentType : "application/json;charset=UTF-8",
	            data : JSON.stringify(account),
	            success : function(data){
	                btn.text('保存').attr('disabled',false);
	                if(data.code == '000000') {
	                    mui.toast('保存成功');
	                    window.location.href= "/wx/account?rmd="+new Date().getTime();
	                } else {
	                    mui.toast('保存失败');
	                }
	            },
	            error : function(){
	                btn.text('保存').attr('disabled',false);
	                mui.toast('保存失败');
	            }
	       });
	    });
	    
	    renderUser(user);
	});
</script>
<%@ include  file="common/footer.jsp"%>