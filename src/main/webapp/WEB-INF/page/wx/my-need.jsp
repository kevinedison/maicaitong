<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">带点啥-我的求带</h1>
    <a action="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>    
</header>
<div class="mui-content">
    <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
        <div class="panel-title">商品信息</div>
    </li>
    <div id="need-pic-slider" class="mui-slider" style="height:300px;">
       <div class="mui-slider-group"></div>
       <div class="mui-slider-indicator"></div>
    </div>
    <div class="mui-table-view-cell need-info"></div>

    <div class="dividing"></div>
    <ul class="mui-table-view need-buyer-wrap">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">买手列表</div>
        </li>
    </ul>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script src="/resources/wx/js/ajaxfileupload.js"></script>
<script type="text/javascript">
    var loadAndRenderNeed = function(needId){
    	$.ajax({
            type : "get",
            url  : "/need?id="+needId,
            success     : function(data){
                if(data && data.code == '000000') {
                	var need = data.data || {};
                	$('.mui-title').text('带点啥-'+need.title);
                	if(need.status == 2) {
                		$('.need-bottom-nav').hide();
                	}
                	
                	var user = need.userInfo || {};
                	var userHtml = [];
                	
                	
                	if(daidian.session.user && daidian.session.user.userId == user.userId && daidian.session.user.identityAuth == 1){
                		userHtml.push('<a action="/wx/account-service">');
                    }else{
                    	userHtml.push('<a action="/wx/user?id='+user.userId+'" class="user-profile service-user">');
                    }
                	
                	userHtml.push('  <div class="user-img"><img src="'+getPictureUrl(user.avatar,2)+'" /></div>');
                	userHtml.push('  <h4 class="user-name">'+(user.nickName)+'</h4>');
                	userHtml.push('  <div class="location-info">'+(user.hometown||'')+'</div>');
                	userHtml.push('</a>');
                	$('.need-user-info').append(userHtml.join(''));    
                	
                	if(need.picture) {
                		var picHtml = [],picIndicatorHtml = [];
                        var pictures = JSON.parse(need.picture || '') || [];
                        pictures.forEach(function(pic) {
                        	picHtml.push(' <div class="mui-slider-item"><img src="'+getPictureUrl(pic,1)+'"></div>');
                        });    
                        $('#need-pic-slider .mui-slider-group').append(picHtml.join(''));
                        
                        picIndicatorHtml.push('<div class="mui-indicator mui-active"></div>');
                        for (var i = 1;i<pictures.length;i++){
                        	picIndicatorHtml.push('<div class="mui-indicator"></div>');
                        }
                        $('#need-pic-slider .mui-slider-indicator').append(picIndicatorHtml.join(''));
                        mui("#need-pic-slider").slider({ interval: 0 });
                    }
                	
                    var needHtml = [];
                    needHtml.push('<table>');
                    needHtml.push('    <tbody>');
                    needHtml.push('    </tr>');
                    needHtml.push('    <tr>');
                    needHtml.push('        <th>名称</th>');
                    needHtml.push('        <td>'+need.title+'</td>');
                    needHtml.push('    </tr>');
                    needHtml.push('    <tr>');
                    needHtml.push('        <th>描述</th>');
                    needHtml.push('        <td>'+need.description+'</td>');
                    needHtml.push('    </tr>');
                    needHtml.push('    <tr>');
                    needHtml.push('        <th>要求</th>');
                    needHtml.push('        <td>'+need.servicerequire+'</td>');
                    needHtml.push('    </tr>');
                    needHtml.push('    <tr>');
                    needHtml.push('        <th>数量</th>');
                    needHtml.push('        <td>'+need.count+' 个</td>');
                    needHtml.push('    </tr>');
                    needHtml.push('    <tr>');
                    needHtml.push('        <th>服务费</th>');
                    needHtml.push('        <td><button class="mui-btn-danger mui-btn-outlined service-price">¥ '+need.price+'</button></td>');
                    needHtml.push('    </tr>');
                    needHtml.push('    </tbody>');
                    needHtml.push('</table>');
                    
                    $('.need-info').append(needHtml.join(''));
                    
                    if(need.needAcceptList && need.needAcceptList.length > 0) {
                    	renderAcceptBuyer(need.needAcceptList);
                    }
                } else {
                	 mui.toast('查询求带详情失败');
                }
            },
            error     : function() {
                mui.toast('查询求带详情失败');
            }
        });
    };
    
    var renderAcceptBuyer = function(accepts) {
    	var html = [];
    	(accepts || []).forEach(function(acp){
    		var user = acp.acpUserInfo || {};
    		html.push('<li class="mui-table-view-cell">');
    		
    		
    		if(daidian.session.user && daidian.session.user.userId == user.userId && daidian.session.user.identityAuth == 1){
    			html.push('<a action="/wx/account-service">');
            }else{
            	html.push('    <a action="/wx/user?id='+user.userId+'">');
            }
    		
    		html.push('        <div class="oa-contact-cell mui-table">');
    		html.push('            <div class="oa-contact-avatar mui-table-cell">');
    		html.push('                <img src="'+getPictureUrl(user.avatar,2)+'">');
    		html.push('            </div>');
    		html.push('            <div class="oa-contact-content mui-table-cell">');
    		html.push('                <div class="mui-clearfix">');
    		html.push('                    <h4 class="oa-contact-name">'+(user.nickName)+'</h4>');
    		html.push('                    <span class="oa-contact-position mui-h6">'+(daidian.config.identityType[user.identify]||'')+'</span>');
    		html.push('                    <span class="mui-pull-right"> <button class="mui-btn mui-btn-outlined mui-icon mui-icon-flag">'+(user.orderCount||0)+'</button></span>');
    		html.push('                </div>');
    		html.push('                <p class="oa-contact-email mui-h6">');
    		html.push(                     (dataconfig.countrys[user.country] || {}).name);
    		html.push('                    <span class="mui-pull-right"> <button class="mui-btn mui-btn-outlined mui-icon mui-icon-chatboxes">'+(user.commentCount||0)+'</button></span>');
    		html.push('                </p>');
    		html.push('            </div>');
    		html.push('        </div>');
    		html.push('    </a>');
    		html.push('    <div class="need-image-list">');
    		(acp.pictures || []).forEach(function(p) {
    		   html.push('       <img class="mui-col-xs-3" src="'+getPictureUrl(p,1,'list')+'"/>');
    		});
    		html.push('    </div>');
    		html.push('    <p>'+acp.description+'</p>');
    		html.push('    <button class="mui-btn-danger mui-btn-outlined service-price">价格 ¥ '+acp.price+' / 时间'+acp.time+'天</button>');
            if(acp.status == 1) {
            	html.push('    <button class="mui-pull-right mui-btn-danger accept-btn" targetId="'+acp.id+'">确认接受</div>');
            } else if(acp.status == 2) {
            	html.push('    <div class="mui-pull-right"><span class="mui-badge mui-badge-danger">已被接单</span></div>');
            } else if(acp.status == 3) {
            	html.push('    <div class="mui-pull-right"><span class="mui-badge mui-badge-danger">未成交</span></div>');
            } else if(acp.status == 4) {
            	html.push('    <div class="mui-pull-right"><span class="mui-badge mui-badge-success">成交</span></div>');
            }
    		html.push('</li>');
    	});
    	$('.need-buyer-wrap').append(html.join(''));
    }
    
	$(function() {
		if(!checkSession()) {return;};
		
	    var needId = getQueryString('id');
	    loadAndRenderNeed(needId);
	   
	    mui('#offCanvasSideScroll').scroll();
	    mui('#offCanvasContentScroll').scroll();
	
	    $('body').on('tap','.wanna',function(evt){
	        mui('#offCanvasWrapper').offCanvas('show');
	    });
	
	    $('body').on('tap','.order-submit',function(evt) {
	        var accept = {};
	
	        var desc = $('.need-service-desc').val() || '';
	        if(desc.trim().length == 0) {
	            mui.toast('描述不能为空');
	            $('.need-service-desc').addClass('input-error');
	            return;
	        }
	
	        var price = $('.need-price').val() || '';
	        if(price.trim().length == 0) {
	            mui.toast('商品价格不能为空');
	            $('.need-price').addClass('input-error');
	            return;
	        }
	
	        var time = $('.need-time').val() || '';
	        if(time.trim().length == 0) {
	            mui.toast('时间不能为空');
	            $('.need-time').addClass('input-error');
	            return;
	        }
	
	        var serviceprice = $('.need-service-money').val() || '';
	        if(serviceprice.trim().length == 0) {
	            mui.toast('服务费用不能为空');
	            $('.need-service-money').addClass('input-error');
	            return;
	        }
	
	        var picture = [];
	        $('#need-pic').each(function(index,dom){
	            $(dom).find('.image-item').each(function(index,input){
	                if($(input).attr('pic-path')) {
	                    picture.push($(input).attr('pic-path'));
	                }
	            });
	        });
	
	        accept.desc = desc;
	        accept.price = price;
	        accept.time = time;
	        accept.serviceprice = serviceprice;
	        accept.picture = JSON.stringify(picture);
	        accept.operTime = new Date().format('yyyy-MM-dd hh:mm:ss');
	        accept.needId = needId;
	
	        var btn = $('.order-submit');
	        btn.text('正在提交').attr('disabled',true);
	
	        $.ajax({
	            type : "post",
	            url  : "/api/acpneed",
	            contentType : "application/json;charset=UTF-8",
	            data        : JSON.stringify(accept),
	            success     : function(data){
	                btn.text('接单中').attr('disabled',false);
	                if(data.code == '000000') {
	                    mui.toast('接单成功');
	                    window.location.href="/need/"+needId;
	                } else {
	                    btn.text('接单').attr('disabled',false);
	                    mui.toast(data.msg);
	                }
	            },
	            error     : function() {
	                btn.text('接单').attr('disabled',false);
	                mui.toast('接单出现异常');
	            }
	        });
	    });
	
	    $('body').on('change','.order-number',function(evt){
	        var target = $(evt.target);
	        var price = target.attr('price');
	        var totalPrice = parseInt(price) * parseInt(target.val());
	        $('.total-price').html('¥ ' + totalPrice);
	    });
	
	    $('body').on('tap','.contact',function(evt){
	         window.location.href="/wx/message?to="+needId;
	    });
	    
	    $('body').on('tap','.accept-btn',function(evt){
	    	var target = $(evt.target);
	        var targetId = target.attr('targetId');
	    	var needConfirm = {};
	    	needConfirm.needId = needId;
	    	needConfirm.id = targetId;
	    	
	    	var btn = $('.accept-btn');
	        btn.text('正在提交').attr('disabled',true);
	    	
	    	$.ajax({
	            type : "post",
	            url  : "/user/need/confirm",
	            contentType : "application/json;charset=UTF-8",
	            data        : JSON.stringify(needConfirm),
	            success     : function(data){
	                btn.text('确认中').attr('disabled',false);
	                if(data.code == '000000') {
	                    mui.toast('确认订单成功');
	                    window.location.href="/wx/order-pay?id="+data.data;
	                } else {
	                    btn.text('确认接受').attr('disabled',false);
	                    mui.toast(data.msg);
	                }
	            },
	            error     : function() {
	                btn.text('确认接受').attr('disabled',false);
	                mui.toast('确认订单出现异常');
	            }
	        });
	    });
	
	    $('body').on('tap','.paging-bar',function(evt){
	        evt.preventDefault();
	        loadAndRenderUserComment(serviceId);
	    });
	
	    $('body').on('tap','.more-info',function(evt){
	        var target = $(evt.target);
	        if(!target.hasClass('more-info')) {
	            target = target.closest('.more-info');
	        }
	        $('.service-more-info').slideDown('300');
	        target.slideUp(300);
	    });
	
	    $('.paging-bar').trigger('tap');
	});
</script>
<%@ include  file="common/footer.jsp"%>