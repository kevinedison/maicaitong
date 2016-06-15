<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>卖菜通</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="/resources/wx/css/mui.min.css">
    <link rel="stylesheet" href="/resources/wx/css/app.css"/>
</head>

<body>

<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
    <aside id="offCanvasSide" class="mui-off-canvas-right order-form">
        <div id="offCanvasSideScroll" class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <form class="mui-input-group service-add">
                    <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                        <div class="panel-title">商品信息</div>
                    </li>
                    <div class="mui-input-row" id="need-pic">
                        <label>商品图片</label>
                        <div class="row image-list" style="min-height:85px;height:auto">
                            <div class="image-item image-upload" number="multi" style="background-image: url('/resources/wx/images/iconfont-tianjia.png')"></div>
                        </div>
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>图片大小480*480,提供更多地图片给买家,避免多次沟通</p>
                    </div>
                    <div class="row" style="height:auto;">
                        <label style="padding-left: 15px;">描述</label>
                        <textarea class="mui-text-right need-service-desc" rows="5" placeholder="商品描述"></textarea>
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>商品简单描述,包括保质期,是否有发票等</p>
                    </div>
                    <div class="mui-input-row">
                        <label>时间</label>
                        <input type="text" class="mui-text-right need-time" placeholder="时间">
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>直邮需要的大概时间</p>
                    </div>
                    <div class="mui-input-row">
                        <label>原价</label>
                        <input type="number" min=0 class="mui-text-right need-orgin-price" placeholder="原价">
                    </div>
                    <div class="mui-input-row">
                        <label>直邮费</label>
                        <input type="number" min=0 class="mui-text-right need-post-price" placeholder="直邮费">
                    </div>
                    <div class="mui-input-row">
                        <label>服务费出价</label>
                        <input type="number" min=0  class="mui-text-right need-service-price" placeholder="服务费" readonly="readonly">
                    </div>
                    <div class="mui-input-row">
                        <label>加收</label>
                        <input type="number" min=0  class="mui-text-right need-add-service-price"  placeholder="加收服务费">
                    </div>
                    <div class="mui-input-row">
                        <label>总价</label>
                        <input type="number" min=0  class="mui-text-right need-total-money" placeholder="总价" readonly="readonly">
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>单位RMB,商品总价+总直邮费用</p>
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>单位RMB,是否需要多加服务费用,建议跟买家沟通后再确定</p>
                        <p>最终订单价格:菜农确定的商品及邮寄费用+买家确定的服务费用</p>
                    </div>
                </form>
                <a class="order-submit"><span>提交</span></a>
            </div>
        </div>
    </aside>
    <div class="mui-inner-wrap">
        <header class="mui-bar mui-bar-nav">
            <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
            <h1 class="mui-title">卖菜通-</h1>
            <a action="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>            
        </header>
        
        <nav class="mui-bar mui-bar-tab user-nav need-bottom-nav" id="not-acp" style="display:none">
            <a class="mui-tab-item contact">
                <span class="mui-tab-label">咨询</span>
            </a>
            <a class="mui-tab-item wanna">
                <span class="mui-tab-label">接单</span>
            </a>
        </nav>
        
        <nav class="mui-bar mui-bar-tab user-nav need-bottom-nav" id="acp" style="display:none">
            <a class="mui-tab-item contact">
                <span class="mui-tab-label">咨询</span>
            </a>
        </nav>
        
        <div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper">
            <div class="mui-scroll">
                <div class="mui-content">
                    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed list-wrap">
                        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                            <div class="panel-title">买家信息</div>
                        </li>
                        <li class="mui-table-view-cell need-user-info" style="min-height:130px;"></li>
                    </ul>
                    <div class="dividing"></div>
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
                            <div class="panel-title">菜农列表</div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="mui-off-canvas-backdrop"></div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/mui.zoom.js"></script>
<script src="/resources/wx/js/mui.previewimage.js"></script>
<script src="/resources/wx/js/dataconfig.js"></script>
<script src="/resources/wx/js/ajaxfileupload.js"></script>
<script type="text/javascript">
    var userId;
    var loadAndRenderNeed = function(needId){
    	$.ajax({
            type : "get",
            url  : "/need?id="+needId,
            success     : function(data){
                if(data && data.code == '000000') {
                	var need = data.data || {};
                	$('.mui-title').text('卖菜通-'+need.title);
                
                	if(need.status == 1) {
                		if(need.acpFlag){
                			$('#acp').slideDown(200);
                		}else{
                			$('#not-acp').slideDown(200);
                		}	
                	}
                	
                	var user = need.userInfo || {};
                	userId = user.userId;
                    var userHtml = [];
                    
                    if(daidian.session.user && daidian.session.user.userId == user.userId && daidian.session.user.identityAuth == 1){
                    	userHtml.push('<a action="/wx/account-service">');
                    }else{
                    	userHtml.push('<a action="/wx/user?id='+user.userId+'" class="user-profile service-user">');
                    }
                    
                    userHtml.push('<div class="user-img"><img src="'+getPictureUrl(user.avatar,2)+'" /></div>');
                    userHtml.push('<h4 class="user-name">'+(user.nickName)+'</h4>');
                    userHtml.push('<div class="location-info">'+((dataconfig.countrys[user.country] || {}).name || user.hometown || '')+'</div>');
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
                    
                    $('.need-service-price').val(need.price);
                    $('.need-total-money').val(need.price);
                    
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
    		html.push('                    <span class="oa-contact-position mui-h6">'+(daidian.config.identityType[user.identity]||user.identity)+'</span>');
    		html.push('                    <span class="mui-pull-right"> <button class="mui-btn mui-btn-outlined mui-icon mui-icon-flag" type="buyer" userId="'+user.userId+'">'+(user.orderCount||0)+'</button></span>');
    		html.push('                </div>');
    		html.push('                <p class="oa-contact-email mui-h6">');
    		html.push(                     (dataconfig.countrys[user.country] || {}).name);
    		html.push('                    <span class="mui-pull-right"> <button class="mui-btn mui-btn-outlined mui-icon mui-icon-chatboxes" type="buyer" userId="'+user.userId+'">'+(user.commentCount||0)+'</button></span>');
    		html.push('                </p>');
    		html.push('            </div>');
    		html.push('        </div>');
    		html.push('    </a>');
    		html.push('    <div class="need-image-list" style="padding-top: 5px;">');
    		(JSON.parse(acp.picture || '') || []).forEach(function(p) {
    		   html.push('       <img class="mui-col-xs-3" src="'+getPictureUrl(p,1,'list')+'" data-preview-src="'+getPictureUrl(p,1)+'" data-preview-group="1" />');
    		});
    		html.push('    </div>');
    		html.push('    <p>'+acp.description+'</p>');
    		html.push('    <button class="mui-btn-danger mui-btn-outlined service-price" style="margin-top: 5px;">总价格 ¥ '+acp.price+' / 预估时间'+acp.time+'天</button>');
            if(acp.status == 1) {
            	if(daidian.session.user && daidian.session.user.userId == acp.needUser && daidian.session.user.identityAuth == 1){
            		$('#acp').hide();
            		$('#not-acp').hide();
            		html.push('    <button class="mui-pull-right mui-btn-danger accept-btn" targetId="'+acp.id+'">确认接受</div>');
            	}else{
            		html.push('    <div class="mui-pull-right" style="padding-top: 5px;"><span class="mui-badge mui-badge-warning">等待买家确认</span></div>');
            	}
            } else if(acp.status == 2) {
            	html.push('    <div class="mui-pull-right" style="padding-top: 5px;"><span class="mui-badge mui-badge-danger">已生成订单</span></div>');
            } else if(acp.status == 3) {
            	html.push('    <div class="mui-pull-right" style="padding-top: 5px;"><span class="mui-badge mui-badge-danger">未成交</span></div>');
            } else if(acp.status == 4) {
            	html.push('    <div class="mui-pull-right" style="padding-top: 5px;"><span class="mui-badge mui-badge-success">成交</span></div>');
            }
            html.push('    <div style="padding-top:10px;text-align: left;font-size: 12px;"><div>原价格：¥ '+acp.originprice+'</div><div>服务费：¥ '+acp.serviceprice + ' +  ¥ ' + acp.addprice+'（还价）</div><div>直邮费：¥ '+acp.postprice+'（含关税）</div><div>预估时间：'+acp.time+' 天</div></div>');
    		html.push('</li>');
    	});
    	$('.need-buyer-wrap').append(html.join(''));
    	
    	mui.previewImage();
    }
    
	$(function() {
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
	        
	        var picture = [];
	        $('#need-pic').each(function(index,dom){
	            $(dom).find('.image-item').each(function(index,input){
	                if($(input).attr('pic-path')) {
	                    picture.push($(input).attr('pic-path'));
	                }
	            });
	        });
	
	        var time = $('.need-time').val() || '';
	        if(time.trim().length == 0) {
	            mui.toast('时间不能为空');
	            $('.need-time').addClass('input-error');
	            return;
	        }
	        
	        var originPrice = $('.need-orgin-price').val() || 0;
	        /**
	        if(originPrice.trim().length == 0) {
	            mui.toast('商品原价不能为空');
	            $('.need-orgin-price').addClass('input-error');
	            return;
	        }*/
	        
	        var postPrice = $('.need-post-price').val() || 0;
	        /**
	        if(postPrice.trim().length == 0) {
	            mui.toast('商品直邮费用不能为空');
	            $('.need-post-price').addClass('input-error');
	            return;
	        }*/
	        
	        var addServicePrice = $('.need-add-service-price').val() || 0;
	        /**
	        if(addServicePrice.trim().length == 0) {
	            mui.toast('商品加收服务费用不能为空');
	            $('.need-add-service-price').addClass('input-error');
	            return;
	        }*/
	        var servicePrice = $('.need-service-price').val() || 0;
	        var price = $('.need-total-money').val() || 0;
	
	        accept.needId = needId;
	        
	        accept.description = desc;
	        accept.time = time;
	        accept.picture = JSON.stringify(picture);
	        accept.originprice = originPrice;
	        accept.postprice = postPrice;
	        accept.addprice = addServicePrice;
	        accept.serviceprice = servicePrice;
	        accept.price = price;
	
	        var btn = $('.order-submit');
	        btn.text('正在提交').attr('disabled',true);
	
	        $.ajax({
	            type : "post",
	            url  : "/user/need/acp",
	            contentType : "application/json;charset=UTF-8",
	            data        : JSON.stringify(accept),
	            success     : function(data){
	                btn.text('接单中').attr('disabled',false);
	                if(data.code == '000000') {
	                    mui.toast('接单成功');
	                    window.location.reload();
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
	    
        $('body').on('change','.need-orgin-price,.need-post-price,.need-add-service-price',function(evt) {
	      var originPrice = $('.need-orgin-price').val() || 0;
		  var postPrice = $('.need-post-price').val() || 0;
		  var servicePrice = $('.need-service-price').val() || 0;
		  var addServicePrice = $('.need-add-service-price').val() || 0;
		  $('.need-total-money').val((parseFloat(originPrice) + parseFloat(postPrice) + parseFloat(servicePrice) + parseFloat(addServicePrice)));
	    });
	
        $('body').on('input','.need-orgin-price,.need-post-price,.need-add-service-price',function(evt) {
  	      var originPrice = $('.need-orgin-price').val() || 0;
  		  var postPrice = $('.need-post-price').val() || 0;
  		  var servicePrice = $('.need-service-price').val() || 0;
  		  var addServicePrice = $('.need-add-service-price').val() || 0;
  		  $('.need-total-money').val((parseFloat(originPrice) + parseFloat(postPrice) + parseFloat(servicePrice) + parseFloat(addServicePrice)));
  	    });
        
	    $('body').on('tap','.contact',function(evt){
	        window.location.href="/wx/message?to="+userId;
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
	
	    $('.paging-bar').trigger('tap');
	});
</script>
<%@ include  file="common/footer.jsp"%>