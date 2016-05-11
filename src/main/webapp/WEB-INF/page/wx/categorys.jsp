<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">带点啥-所有分类</h1>
    <a href="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<div class="mui-content">
    <ul class="mui-table-view list-wrap service-category filter"></ul>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	$(function() { 
		var categoryHtml = [];
    	var categorys = daidian.categorys || [];
        for (var c in categorys){
        	if(c == 'special') {
        		continue;
        	}
        	var category = categorys[c] || {};
        	
        	categoryHtml.push('<li class="mui-table-view-cell">');
        	categoryHtml.push('<h4 style="font-size:16px;text-align: center;">' + category.name + '</h4>');
		    
        	categoryHtml.push('<table style="padding:0px;"><tbody><td style="border-bottom: 0px;">');
        	var brands = JSON.parse(category.brand || "[]") || [];
        	brands.forEach(function(bk) {
        		categoryHtml.push('<a action="/wx/search?type='+c+'&brand='+bk+'" style="font-size: 13px;">'+((daidian.brands[bk] || {}).name || '')+'</a>');
        	});
        	categoryHtml.push('</td></tr>');
        	categoryHtml.push('</tbody></table></li>');         
        }
        $('.service-category').html(categoryHtml.join(''));
	});
</script>
<%@ include  file="common/footer.jsp"%>