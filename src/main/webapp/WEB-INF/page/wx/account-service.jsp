<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
	<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	<h1 class="mui-title">卖菜通-服务列表</h1>
	<a href="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<div class="mui-content" style="display:none">
	<ul class="mui-table-view list-wrap service-wrap">
		<li class="mui-table-view-cell mui-media">
			<div class="mui-text-center">
				<a action="/wx/account-service-edit"
					class="mui-icon mui-icon-plusempty add-service"></a>新增服务
			</div>
		</li>
		<div class="notice-msg">单行左滑有上线，下线，编辑删除功能。</div>
	</ul>
</div>
<%@ include file="common/js.jsp"%>
<script type="text/javascript">
    var loadAndRenderUserService = function() {
    	var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
        $('.service-wrap').append(loadHtml);
    	$.ajax({
	        url     : '/user/service/list?pageSize=9999&page=1',
	        success : function(data){
	            $('.service-wrap .mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    return;
	                }
	
	                var services = data.data.data || [];
	                var html = [];
	                services.forEach(function(service) {
	                	html.push('<li class="mui-table-view-cell mui-media service-row">');
	                	html.push('   <div class="mui-slider-right mui-disabled">');
	                	if(service.status == 1) {
	                		html.push('        <a class="mui-btn mui-btn-royal" sId="'+service.id+'">下线</a>');
	                	}else if(service.status == -1){
	                		html.push('        <a class="mui-btn mui-btn-yellow" sId="'+service.id+'">编辑</a>');
	                		html.push('        <a class="mui-btn mui-btn-red" sId="'+service.id+'">删除</a>');
	                	}else{
	                		html.push('        <a class="mui-btn mui-btn-yellow" sId="'+service.id+'">编辑</a>');
	                		html.push('        <a class="mui-btn mui-btn-blue" sId="'+service.id+'">上线</a>');
	                		html.push('        <a class="mui-btn mui-btn-red" sId="'+service.id+'">删除</a>');
	                	}
	                	
	                	html.push('    </div>');
	                	html.push('    <div class="mui-slider-handle">');
	                	html.push('        <a action="/wx/service?id='+service.id+'">');
	                	html.push('            <img class="mui-media-object mui-pull-left" src="'+getPictureUrl(service.cover,1,'list')+'">');
	                	html.push('            <div class="mui-media-body">');
	                	html.push('                <div class="mui-h4 service-title mui-ellipsis">'+service.title+'</div>');
	                	html.push('                <div class="service-desc mui-ellipsis-2">'+service.description+'</div>');
	                	html.push('                <div class="service-bar">');
	                	html.push('                    <button class="mui-btn-danger mui-btn-outlined service-price mui-pull-right">¥ '+(service.price || 0)+'</button>');
	                	html.push('                    <div class="service-number">');
	                	if(service.status == 1) {
	                		html.push(' <button class="mui-btn mui-btn-outlined mui-icon mui-icon-flag" type="service" serviceId="'+service.id+'">'+(service.ordercount||0)+'</button>');
	                	} else {
	                		html.push('                        <button type="button" class="mui-btn mui-btn-warning">'+(service.status == 0 ? '等待上线': service.status == -1?'草稿':'已下线')+'</button>');
	                	}

	                	html.push('                    </div>');
	                	html.push('                </div>');
	                	html.push('            </div>');
	                	html.push('        </a>');
	                	html.push('    </div>');
	                	html.push('</li>');
	                });
	                $('.service-wrap').append(html.join(''));
	            } else {
	                mui.toast('加载用户服务失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载用户服务失败，请刷新页面重试');
	        }
	    });
    };
	$(function () {
		if(!checkSession()) {return;}
		
		var shareId = getQueryString('shareId');
		if(shareId){
			window.location.href="/wx/user?id="+shareId;
			return;
		}
		
		$(".mui-content").show();
		
	    $('body').on('touchstart','.mui-slider-right', function (evt) {
	        var target = $(evt.target);
	        var sId = target.attr('sId');
	
	        if(sId && target.hasClass('mui-btn-yellow')) {
	             window.location.href="/wx/account-service-edit?id="+sId;
	        } else if(sId && target.hasClass('mui-btn-red')) {
	            target.text('正在删除').attr('disabled',false);
	            $.ajax({
	                type : "delete",
	                url  : "/user/service?id="+sId+'&rmd='+new Date().getTime(),
	                contentType : "application/json;charset=UTF-8",
	                success     : function(data){
	                    target.text('删除').attr('disabled',false);
	                    if(data.code == '000000') {
	                        mui.toast('删除服务成功');
	                        target.closest('li').slideUp(500);
	                    } else {
	                        mui.toast('删除服务失败');
	                    }
	                },
	                error     : function() {
	                    target.text('删除').attr('disabled',false);
	                    mui.toast('删除服务失败');
	                }
	            });
	        } else if(sId && target.hasClass('mui-btn-royal')) {
	            target.text('正在下线').attr('disabled',false);
	            var service = {};
	            service.id = sId;
	            service.status = 2;
	            $.ajax({
	                type : "put",
	                url  : "/user/service",
	                contentType : "application/json;charset=UTF-8",
	                data : JSON.stringify(service),
	                success     : function(data){
	                    target.text('下线').attr('disabled',false);
	                    if(data.code == '000000') {
	                        mui.toast('下线服务成功');
	                        window.location.href = '/wx/account-service';
	                    } else {
	                        mui.toast('下线服务失败');
	                    }
	                },
	                error     : function() {
	                    target.text('下线').attr('disabled',false);
	                    mui.toast('下线服务失败');
	                }
	            });
	        } else if (sId && target.hasClass('mui-btn-blue')){
	            target.text('正在上线').attr('disabled',false);
	            var service = {};
	            service.id = sId;
	            service.status = 1;
	            $.ajax({
	                type : "put",
	                url  : "/user/service",
	                contentType : "application/json;charset=UTF-8",
	                data : JSON.stringify(service),
	                success     : function(data){
	                    target.text('上线').attr('disabled',false);
	                    if(data.code == '000000') {
	                        mui.toast('上线服务成功');
	                        window.location.href = '/wx/account-service';
	                    } else {
	                        mui.toast('上线服务失败');
	                    }
	                },
	                error     : function() {
	                    target.text('上线').attr('disabled',false);
	                    mui.toast('上线服务失败');
	                }
	            });
	        }
	    });
	    loadAndRenderUserService();
	});
</script>
<%@ include file="common/footer.jsp"%>