<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-各国特卖</h1>
    <a action="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<div class="mui-content mui-row mui-fullscreen">
    <div class="mui-col-xs-3">
        <div id="segmentedControls" class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-vertical"></div>
    </div>
    <div id="segmentedControlContents" class="mui-col-xs-9" style="border-left: 1px solid #c8c7cc;"></div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	var countryService = function(country) {
	    var page = $(".paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    url = '/service/list?page='+page+'&pageSize='+ daidian.config.pageSize +'&country=' + country +'&category=special'+ '&rmd=' + new Date().getTime();
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('#'+country+' ul').append(loadHtml);
	    $.ajax({
	        type    : 'get',
	        url     :  url,
	        success : function(data){
	            $('#'+country+' ul .mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                    $('.country-service-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有特产</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $('#' + country + ' .paging-bar').show().attr('page',page);
	                } else {
	                    $('#' + country + ' .paging-bar').hide();
	                }
	
	                var services = data.data.data || [];
	                var serviceHtml =[];
	                services.forEach(function(s) {
	                    var serviceUser = s.userInfo || {};
	
	                    serviceHtml.push('<li class="mui-table-view-cell mui-media mui-col-xs-12">');
	                    
	                    if(daidian.session.user && daidian.session.user.userId == serviceUser.userId && daidian.session.user.identityAuth == 1){
	                    	serviceHtml.push('		 <a action="/wx/account-service">');
	                    }else{
	                    	serviceHtml.push('<a action="/wx/user?id='+serviceUser.userId+'">');
	                    }
	                    
	                    serviceHtml.push('    <div class="service-buyer">');
	                    serviceHtml.push('      <span class="user-img"><img src="'+ getPictureUrl(serviceUser.avatar,2) +'"></span>');
	                    serviceHtml.push('      <span class="user-info">');
	                    serviceHtml.push('          <div class="user-name">'+(serviceUser.nickName)+'</div>');
	                    serviceHtml.push('          <div class="user-location">'+((dataconfig.countrys[serviceUser.country] || {}).name || '')+'</div>');
	                    serviceHtml.push('      </span>');
	                    serviceHtml.push('      <button class="mui-btn mui-icon mui-icon-chat" userId='+serviceUser.userId+'></button>');    
	                    serviceHtml.push('    </div>');
	                    serviceHtml.push('</a>');
	
	                    serviceHtml.push('<a action="/wx/service?id='+ s.id+'">');
	                    serviceHtml.push('    <img class="mui-media-object service-cover mui-pull-left" src="'+ getPictureUrl(s.cover,1)+'">');
	                    serviceHtml.push('    <div class="mui-media-body">');
	                    serviceHtml.push('      <div class="service-title mui-ellipsis">'+ s.title+'</div>');
	                    serviceHtml.push('      <p class="service-desc mui-ellipsis-2">'+ s.description+'</p>');
	                    serviceHtml.push('      <div class="service-bar">');
	                    serviceHtml.push('        <button class="mui-btn-danger mui-btn-outlined service-price">¥ '+ s.price+'</button>');
	                    serviceHtml.push('      </div>');
	                    serviceHtml.push('    </div>');
	                    serviceHtml.push('</a>');
	
	                    serviceHtml.push('</li>').push;
	                });
	                $('#'+country+' ul').append(serviceHtml.join(''));
	            } else {
	                mui.toast('加载各国特色失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载各国特色失败，请刷新页面重试');
	        }
	    });
	};
	
	$(function () {
	    $('body').on('tap','.mui-control-item',function(evt){
	        var target = $(evt.target);
	        var country = target.attr('country');
	        $('#'+country+' ul').html('');
	        $('.paging-bar','.mui-control-content[id='+country+']').attr('page',0).trigger('tap');
	    });
	
	    $('body').on('tap','.paging-bar',function(evt){
	    	evt.preventDefault();
	    	
	        var target = $(evt.target);
	        if(!target.hasClass('paging-bar')) {
	            target = target.closest('.paging-bar');
	        }
	        var country = target.attr('country');
	        countryService(country);
	    });

	    var countrys = dataconfig.countrys || {};
		var countryHtml = [],countryDetailHtml = [],active;
		for (var c in countrys) {
			var country = countrys[c];
			active = (c == 'dubai') ? 'mui-active' : '';
			countryHtml.push('<a class="mui-control-item '+active+'" country="'+c+'" href="#'+c+'">' + countrys[c]['name'] + '</a>');
			
			countryDetailHtml.push('<div id="'+c+'" class="mui-control-content '+active+'">');
			countryDetailHtml.push('   <div class="country-brand-info">');
			countryDetailHtml.push('       <img class="mui-media-object" src="'+country.cover+'">');
			countryDetailHtml.push('       <div class="service-title">'+country.code+' '+country.name);
			countryDetailHtml.push('           <div class="panel-more mui-pull-right"><a action="/wx/country?country='+c+'">查看详情</a></div>');
			countryDetailHtml.push('       </div>');
			countryDetailHtml.push('       <p class="service-desc">'+country.desc+'</p>');
			countryDetailHtml.push('   </div>');
			countryDetailHtml.push('   <div class="mui-table-view-cell mui-table-panel mui-col-xs-12">');
			countryDetailHtml.push('       <div class="panel-title">特产</div>');
			countryDetailHtml.push('   </div>');
			countryDetailHtml.push('   <ul class="mui-table-view mui-grid-view country-service-wrap service-row"></ul>');
			countryDetailHtml.push('   <div class="paging-bar" style="display: none" page=0 country="'+c+'"><a>加载更多</a></div>');
			countryDetailHtml.push('</div>');
		}
 	    $('#segmentedControls').append(countryHtml.join(''));
 	    $('#segmentedControlContents').append(countryDetailHtml.join(''));
 	    
 	    $('.mui-control-item.mui-active:eq(0)').trigger('tap');
	});
</script>
<%@ include  file="common/footer.jsp"%>