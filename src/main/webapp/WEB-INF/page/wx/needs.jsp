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
    <style>
      .need-switch:before{  content: '所有';}
      .need-switch.mui-active:before {content: '我的';}
    </style>
</head>
<body>
<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
    <aside id="offCanvasSide" class="mui-off-canvas-right order-form">
        <div id="offCanvasSideScroll" class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <form class="mui-input-group service-add">
                    <div class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                        <div class="panel-title">商品信息</div>
                    </div>
                    <div class="mui-input-row">
                        <label>标题</label>
                        <input type="text" class="mui-text-right" id="need_title" placeholder="标题">
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>让菜农快速了解商品,建议规格+品牌</p>
                    </div>
                    <div class="mui-input-row" id="service-pic">
                        <label>商品图片</label>
                        <div class="row image-list" style="min-height:85px;height:auto">
                            <div class="image-item image-upload" number="multi" style="background-image: url('/resources/wx/images/iconfont-tianjia.png')"></div>
                        </div>
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>图片大小480*480,提供更多地图片让菜农快速了解商品,避免多次沟通</p>
                    </div>
                    <div class="row" style="height:auto;">
                        <label style="padding-left: 15px;">描述</label>
                        <textarea class="mui-text-right" id="need_des" rows="3" placeholder="商品描述"></textarea>
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>描述商品规格,品牌,预期代买国家等</p>
                    </div>
                    <div class="row" style="height:auto;">
                        <label style="padding-left: 15px;">要求</label>
                        <textarea class="mui-text-right" id="need_require" rows="3" placeholder="简单要求"></textarea>
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>简单描述代买要求,比如发票,时间等</p>
                    </div>
                    <div class="dividing"></div>

                    <div class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                        <div class="panel-title">价格</div>
                    </div>

                    <div class="mui-input-row">
                        <label>数量</label>
                        <div class="mui-numbox" data-numbox-min='1'>
                            <button class="mui-btn mui-btn-numbox-minus" type="button">-</button>
                            <input class="mui-input-numbox" id="need_count" type="number"/>
                            <button class="mui-btn mui-btn-numbox-plus" type="button">+</button>
                        </div>
                    </div>
                    <div class="mui-input-row">
                        <label>服务费出价</label>
                        <input type="number" class="mui-text-right" id="need-service-price" placeholder="服务费用" min=0>
                    </div>
                    <div class="mui-text-right notice-msg">
                        <p>单位RMB,代买服务费用,建议不要包含商品价钱及邮寄费用,最终订单价格:菜农确定的商品及邮寄费用+买家确定的服务费用</p>
                    </div>
                </form>
                <a class="order-submit">提交</a>
            </div>
        </div>
    </aside>
    <div class="mui-inner-wrap">
        <header class="mui-bar mui-bar-nav">
            <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
            <h1 class="mui-title">卖菜通-求购</h1>
            <span class="mui-pull-right add-need"><i class="mui-icon mui-icon-plus"></i><i style="font-style:normal;line-height:1px">发布求购</i></span>
        </header>
        <nav class="mui-bar mui-bar-tab bottom-nav">
		    <a class="mui-tab-item top" action="/wx/top">
		        <span class="mui-icon mui-icon-home"></span>
		        <span class="mui-tab-label">首页</span>
		    </a>
		    <a class="mui-tab-item needs mui-active" action="/wx/needs">
		        <span class="mui-icon mui-icon-bars"></span>
		        <span class="mui-tab-label">求购</span>
		    </a>
		    <a class="mui-tab-item buyers" action="/wx/buyers">
		        <span class="mui-icon mui-icon-contact"></span>
		        <span class="mui-tab-label">菜农</span>
		    </a>
		    <a class="mui-tab-item messages" action="/wx/messages">
		        <span class="mui-icon mui-icon-chatbubble"></span>
		        <span class="mui-tab-label">消息</span>
		    </a>
		    <a class="mui-tab-item account" action="/wx/account">
		        <span class="mui-icon mui-icon-gear"></span>
		        <span class="mui-tab-label">我的</span>
		    </a>
		</nav>
        <div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper">
            <div class="mui-scroll">
	            <ul class="mui-table-view mui-table-view-chevron">
					<li id="switch" class="mui-table-view-cell">
						<div class="need-list-title">所有求购</div>
						<div class="mui-switch need-switch">
							<div class="mui-switch-handle"></div>
						</div>
					</li>
				</ul>
                <div id="need">
                    <ul class="mui-table-view list-wrap need-wrap"></ul>
                    <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
                </div>
                <div id="myneed" style="display:none">
                    <ul class="mui-table-view list-wrap my-wrap" ></ul>
                    <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
                </div>
            </div>
        </div>
        <div class="mui-off-canvas-backdrop"></div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	var loadAndRenderNeeds = function(){
	    var page = $("#need .paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.need-wrap').append(loadHtml);
	    $.ajax({
	        url     : '/need/list?pageSize='+daidian.config.pageSize+'&page='+page+"&rmd="+new Date().getTime(),
	        success : function(data){
	            $('#need .mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    $('.need-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有求购商品</li>');
	                    return;
	                }
	
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $("#need .paging-bar").show().attr('page',page);
	                } else {
	                    $("#need .paging-bar").hide();
	                }
	
	                var needs = data.data.data || [];
	                var needHtml = [];
	                needs.forEach(function(curData) {
	                    var pictures = JSON.parse(curData.picture) || [];
	                    needHtml.push('<li class="mui-table-view-cell">');
	                    
	                    if(daidian.session.user && daidian.session.user.userId == curData.userId){
	                    	needHtml.push('     <a action="/wx/my-need?id='+curData.id+'">');
	                    }else{
	                    	needHtml.push('     <a action="/wx/need?id='+curData.id+'">');
	                    }
	                    needHtml.push('         <div class="mui-table">');
	                    needHtml.push('             <div class="mui-table-cell">');
	                    needHtml.push('                 <h4 class="mui-ellipsis-2" style="padding-top:3px;">'+curData.title+'</h4>');
	                    needHtml.push('                 <p class="mui-h6 mui-ellipsis-2">'+curData.description+'</p>');
	                    
	                    needHtml.push('                 <div style="padding-top:10px;">');
	                    if(pictures && pictures.length > 0) {
	                    	needHtml.push('             <img class="mui-col-xs-12" src="'+getPictureUrl(pictures[0],1)+'"/>');
	                    }
	                    needHtml.push('                 </div>');
	                    
	                    needHtml.push('                 <button class="mui-btn-danger mui-btn-outlined service-price">服务费出价 ¥ '+curData.price+' RMB/ 数量 '+curData.count+'</button>');
	                    
	                    if(curData.status == 1) {
	                    	needHtml.push('    <span class="mui-badge" style="margin-top:10px;float: right;">求购中</span>');
	                    } else if(curData.status == 2) {
	                    	needHtml.push('    <span class="mui-badge mui-badge-danger" style="margin-top:10px;float: right;"">订单处理中</span>');
	                    } else if(curData.status == 3) {
	                    	needHtml.push('    <span class="mui-badge mui-badge-danger" style="margin-top:10px;float: right;">未成交</span>');
	                    } else if(curData.status == 4) {
	                    	needHtml.push('    <span class="mui-badge mui-badge-success" style="margin-top:10px;float: right;">成交</span>');
	                    }
	                    
	                    needHtml.push('             </div> ');
	                    needHtml.push('         </div>');
	                    needHtml.push('     </a>');
	                    needHtml.push('</li>');
	                });
	                $('.need-wrap').append(needHtml.join(''));
	            } else {
	                mui.toast('加载求购列表失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载求购列表失败，请刷新页面重试');
	        }
	    });
	};
	
	var loadAndRenderMyNeeds = function(){
	    var page = $("#myneed .paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.my-wrap').append(loadHtml);
	    $.ajax({
	        url     : '/user/need/list?pageSize='+daidian.config.pageSize+'&page='+page+'&rmd='+new Date().getTime(),
	        success : function(data){
	            $('.my-wrap .mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    $('.my-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有求购商品</li>');
	                    return;
	                }
	
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $("#myneed .paging-bar").show().attr('page',page);
	                } else {
	                    $("#myneed .paging-bar").hide();
	                }
	
	                var needs = data.data.data || [];
	
	                needs.forEach(function(curData) {
	                    var needHtml = [];
	                    var pictures = JSON.parse(curData.picture) || [];
	                    needHtml.push('<li class="mui-table-view-cell myneed-row">');
	                    needHtml.push('     <div class="mui-slider-right mui-disabled">');
	                    needHtml.push('         <a class="mui-btn mui-btn-yellow" sId="'+curData.id+'">编辑</a>');
	                    needHtml.push('         <a class="mui-btn mui-btn-red" sId="'+curData.id+'">删除</a>');
	                    needHtml.push('     </div>');
	                    needHtml.push('     <div class="mui-slider-handle">');
	                    needHtml.push('             <a action="/wx/my-need?id='+curData.id+'">');
	                    needHtml.push('                 <div class="mui-table">');
	                    needHtml.push('                     <div class="mui-table-cell mui-col-xs-10">');
	                    needHtml.push('                         <h4 class="mui-ellipsis-2" style="padding-top:3px;">'+curData.title+'</h4>');
	                    needHtml.push('                         <p class="mui-h6 mui-ellipsis-2">'+curData.description+'</p>');
	                    if(pictures && pictures.length > 0) {
	                    	needHtml.push('                     <img class="mui-col-xs-12" src="'+getPictureUrl(pictures[0],1)+'"/>');
	                    }
	                    needHtml.push('                         <button class="mui-btn-danger mui-btn-outlined service-price">服务费出价 ¥ '+curData.price+' RMB/ 数量 '+curData.count+'</button>');
	                    needHtml.push('                     </div> ');
	                    needHtml.push('                     <div class="mui-table-cell mui-col-xs-2 mui-text-center">');
	                    needHtml.push('                        <div class="mui-h6">'+new Date(curData.operateTime).format('MM-dd')+ '</div>');
	                    
	                    
	                    if(curData.status == 1) {
	                    	needHtml.push('    <span class="mui-badge" style="margin-top:20px;">求购中</span>');
	                    } else if(curData.status == 2) {
	                    	needHtml.push('    <span class="mui-badge mui-badge-success">成交</span>');
	                    } else if(curData.status == 3) {
	                    	needHtml.push('    <span class="mui-badge mui-badge-danger">未成交</span>');
	                    }
	                    
	                    
	               
	                    needHtml.push('                     </div>');
	                    needHtml.push('             </div>');
	                    needHtml.push('           </a>');
	                    needHtml.push('	    </div>');
	                    needHtml.push('</li>');
	                    $(needHtml.join(' ')).data('json', curData).appendTo('.my-wrap');
	                });
	            } else {
	                mui.toast('加载求购列表失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载求购列表失败，请刷新页面重试');
	        }
	    });
	};
	
	var emptyForm = function() {
	    $('#need_title').val('');
	    $('#need_des').val('');
	    $('.image-list').empty().append('<div class="image-item image-upload" number="multi" style="background-image: url(/resources/wx/images/iconfont-tianjia.png)"></div>');
	    $('#need_require').val('');
	    $('#need_count').val(1);
	    $('#need-service-price').val('');
	};
	
	$(function() {
	    mui('#offCanvasSideScroll').scroll();
	    mui('#offCanvasContentScroll').scroll();
	
	    $('body').on('tap','.add-need',function(evt){
	        emptyForm();
	        mui('#offCanvasWrapper').offCanvas('show');
	    });
	
	    $('body').on('dragend','#offCanvasWrapper',function(evt){
	        if($(this).hasClass('mui-active')){
	            emptyForm();
	        }
	    });
	    
	    $('body').on('toggle','.need-switch',function(evt){
	        evt.preventDefault();
	        var target = $(evt.target);
	        if(target.hasClass('mui-active')) {
	        	$('#need').hide();
	            $('#myneed').show();
	            if(!checkSession()) {return;};
	            $('.my-wrap').html('');
	            $('#myneed .paging-bar').trigger('tap');
	        } else {
	           $('#need').show();
		       $('#myneed').hide();
	        }
	    });
	
	    $('body').on('tap','.order-submit',function(evt) {
	        var need = {};
	
	        var title = $('#need_title').val();
	        if(!title || title.trim().length == 0){
	            mui.toast('商品标题不能为空');
	            $('#need_title').addClass('input-error');
	            return;
	        }
	
	        var des = $('#need_des').val();
	        if(!des || des.trim().length == 0){
	            mui.toast('商品描述不能为空');
	            $('#need_des').addClass('input-error');
	            return;
	        }
	
	        var picture = [];
	        $('#service-pic').each(function(index,dom){
	            $(dom).find('.image-item').each(function(index,input){
	                if($(input).attr('pic-path')) {
	                    picture.push($(input).attr('pic-path'));
	                }
	            });
	        });
	
	        var require = $('#need_require').val() || '';
	        var count = $('#need_count').val();
	        var price = $('#need-service-price').val();
	        if(!price){
	            mui.toast('服务费用不能为空');
	            $('#service-price').addClass('input-error');
	            return;
	        }
	
	        var needId = $('.order-submit').attr('needId');
	        var ajaxType = 'post';
	        if(needId) {
	            need.id =  needId;
	            ajaxType = 'put';
	        }
	
	        need.title = title;
	        need.description = des;
	        need.servicerequire = require;
	        need.count = count;
	        need.price = price;
	        need.picture = JSON.stringify(picture);
	
	        var btn = $('.order-submit');
	        btn.text('正在提交').attr('disabled',true);
	
	        $.ajax({
	            type : ajaxType,
	            url  : "/user/need",
	            contentType : "application/json;charset=UTF-8",
	            data        : JSON.stringify(need),
	            success     : function(data){
	                btn.text('发布求带').attr('disabled',false);
	                if(data.code == '000000') {
	                    mui.toast('发布求带服务成功');
	                    window.location.reload();
	                } else {
	                    mui.toast('发布求带服务失败');
	                }
	            },
	            error     : function() {
	                btn.text('提交').attr('disabled',false);
	                mui.toast('发布服务出现异常');
	            }
	        });
	
	    });

	
	    $('body').on('tap','.mui-slider-right', function (evt) {
	        var target = $(evt.target);
	        var sId = target.attr('sId');
	        if(sId && target.hasClass('mui-btn-yellow')) {
	            if(!target.hasClass('.myneed-row')) {
	                target = target.closest('.myneed-row');
	                var data = target.data('json');
	                if(data) {
	                    emptyForm();
	                    mui('#offCanvasWrapper').offCanvas('show');
	                    $('.order-submit').attr('needId',data.id || '');
	
	                    $('#need_title').val(data.title || '');
	                    $('#need_des').val(data.description || '');
	                    var pics = [];
	                    (JSON.parse(data.picture || '[]')).forEach(function(p) {
	                        pics.push('<div class="image-item" style="background-image: url('+p+');" pic-path="'+p+'"><div class="image-close">X</div></div></div>');
	                    });
	                    $('.image-list').prepend(pics.join(' '));
	
	                    $('#need_require').val(data.servicerequire|| '');
	                    $('#need_count').val(data.count||1);
	                    $('#need-service-price').val(data.price||0);
	                }
	            }
	       } else if(sId && target.hasClass('mui-btn-red')) {
	            target.text('正在删除').attr('disabled',false);
	            $.ajax({
	                type : "delete",
	                url  : "/user/need?id="+sId+'&rmd='+new Date().getTime(),
	                success     : function(data){
	                    target.text('删除').attr('disabled',false);
	                    if(data.code == '000000') {
	                        mui.toast('删除求购服务成功');
	                        target.closest('li').slideUp(500);
	                    } else {
	                        mui.toast('删除求购服务失败');
	                    }
	                },
	                error     : function() {
	                    target.text('删除').attr('disabled',false);
	                    mui.toast('删除求购服务失败');
	                }
	            });
	        }
	    });
	
	    $('body').on('tap','#need .paging-bar',function(evt){
	        evt.preventDefault();
	        loadAndRenderNeeds();
	    });
	    $('#need .paging-bar').trigger('tap');
	
	    $('body').on('tap','#myneed .paging-bar',function(evt){
	        evt.preventDefault();
	        loadAndRenderMyNeeds();
	    });
	});
</script>
<%@ include  file="common/footer.jsp"%>