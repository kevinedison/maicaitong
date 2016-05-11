<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
  <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
  <h1 class="mui-title">带点啥-发布服务</h1>
  <a action="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<div class="mui-content">
  <form class="mui-input-group service-add">
    <div class="mui-table-view-divider">商品信息</div>

    <div class="mui-input-row">
      <label>商品分类</label>
      <select class="service-category" style="direction: rtl;padding-right:10px;">
      </select>
    </div>
    
    <div class="mui-input-row">
      <label>标题</label>
      <input type="text" class="service-title" placeholder="请输入名称">
    </div>
    <div class="mui-text-right notice-msg">
      <p>让买家快速了解商品,建议规格+品牌</p>
    </div>
    <div class="row">
      <label style="padding-left:15px;">简单描述</label>
      <textarea  rows="5" placeholder="描述" class="service-desc"></textarea>
    </div>
    <div class="mui-text-right notice-msg">
      <p>简短描述商品规格,直邮要求;还有就是直邮国家.</p>
    </div>
    <div class="mui-table-view-divider">商品图文</div>
    <div class="mui-input-row user-avatar-row">
      <label>商品主图</label>
      <img class="image-item image-upload mui-pull-right" number="single" id="service-cover" src="/resources/wx/images/iconfont-tianjia.png" style="width: 120px; height: 120px;">
    </div>
    <div class="mui-text-right notice-msg">
      <p>商品主图,大小为480*480</p>
      <p>在微信公众账号中回复:委托+微信账号就可以委托平台优化商品图片,整理个人主页,一天之后响应.</p>
    </div>
    <div class="mui-input-row mui-text-center">
      <a class="mui-icon mui-icon-plusempty add-service-desc"></a>追加图文
    </div>
    <div class="mui-text-right notice-msg">
      <p>多追加图文描述,让买家尽肯能多了解商品信息,避免频繁沟通占用大量时间</p>
    </div>
    <div class="mui-table-view-divider">价格信息</div>
    
    <div class="mui-input-row">
         <label>价格透明</label>
         <div class="mui-switch service-price-type mui-active" style="margin-top: 2px;">
             <div class="mui-switch-handle"></div>
         </div>
    </div>
    <div class="service-price-group">
	    <div class="mui-input-row">
	      <label>原价</label>
	      <input type="number" min=0 class="service-origin-price" placeholder="原价">
	    </div>
	    <div class="mui-input-row">
	      <label>运费</label>
	      <input type="number" min=0 class="service-post-price" placeholder="直邮费用">
	    </div>
	    <div class="mui-input-row">
	      <label>服务费</label>
	      <input type="number" min=0 class="service-service-price" placeholder="服务费用">
	    </div>   
    </div>
    
    <div class="mui-input-row">
      <label>总价</label>
      <input type="number" class="edit-service-total-price" placeholder="总价" readonly="readonly">
    </div>
    <div class="mui-text-right notice-msg">
      <p>单位RMB</p>
    </div>
    <div class="mui-button-row">
      <button type="button" class="mui-btn mui-btn-success save-service-btn">保存</button>
      <button type="button" class="mui-btn mui-btn-primary add-service-btn">直接上线</button>
    </div>

    <div class="buyer-agreement"><a href="agreement.html" class="text-center local-info"> 查看服务协议 </a></div>
  </form>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/ajaxfileupload.js"></script>
<script src="/resources/wx/js/dataconfig.js?rmd=00001"></script>
<script type="text/javascript">
	var renderService = function(serviceId) {
		$.ajax({
	        url     : '/user/service?id=' + serviceId+'&rmd='+new Date().getTime(),
	        success : function(data){
	            if(data && data.code == '000000') {
	            	var service = data.data || {};
	        	    var category = service.category || $('.service-category').val();
	        	
	        	    $('.service-category').val(category);
	        	  
        	    	if(service.priceType == 1) {
        	    		$('.service-price-type').addClass('mui-active');
        	    		$('.service-price-group').fadeIn(200);
        	    		
        	    		$('.service-origin-price').val(service.originPrice);
    	        		$('.service-post-price').val(service.postPrice);
    	        		$('.service-service-price').val(service.servicePrice);
        	    	} else {
        	    		$('.service-price-type').removeClass('mui-active');
        	    		$('.service-price-group').fadeOut(200);
        	    	}
	        	    
	        	    $('.service-title').val(service.title);
	        	    $('.service-desc').val(decodeURIComponent(service.description));
	        	    $('#service-cover').attr('pic-path',service.cover).attr('src',getPictureUrl(service.cover,1,'list'));
	        	    $('.edit-service-total-price').val(service.price);
	        	    
	        	    var detail = [];
	        	    var serviceDetails = JSON.parse((service.servicedetail) || '[]');
	        	    serviceDetails.forEach(function(d) {
		        	      detail.push('<div class="mui-input-row mui-text-left service-detail-group">');
		        	      detail.push(' <div class="group-close">X</div>');
		        	      detail.push(' <label>商品图文</label>');
		        	      detail.push(' <div class="row image-list" style="min-height:85px;height:auto">');
	        	          (d.pictures || []).forEach(function(p) {
	        	        	  detail.push('<div class="image-item" style="background-image:url('+getPictureUrl(p,1,'list')+')" pic-path="'+p+'"><div class="image-close">X</div></div>');
	        	          });
		        	      detail.push('   <div class="image-item image-upload" number="multi" style="background-image:url(/resources/wx/images/iconfont-tianjia.png)"></div>');
		        	      detail.push('   <textarea  placeholder="描述" class="service-desc" style="width:100%;padding: 10px 15px;text-align: left;">'+decodeURIComponent(d.desc)+'</textarea>');
		        	      detail.push(' </div>');
		        	      detail.push('</div>');
	        	    });
	        	    $('.add-service-desc').before(detail.join(''));
	            } else {
	                mui.toast('加载服务详情失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载服务详情失败，请刷新页面重试');
	        }
	    });
	};
	
	
    var initPage = function() {
    	var categoryHtml = [];
    	var categorys = daidian.categorys || [];
        for (var c in categorys){
        	categoryHtml.push(' <option value="'+c+'"> '+(categorys[c] || {}).name+' </option>');
        }
        $('.service-category').html(categoryHtml.join(''));
    };
    
    
	$(function () {
	  initPage();
	  if(!checkSession()) {return;};
	  
      var serviceId = getQueryString('id');	 
	
	  $('body').on('click','.group-close',function(evt) {
	      $(evt.target).closest('.service-detail-group').remove();
	  });
	
	  $('body').on('change','.service-origin-price,.service-post-price,.service-service-price',function(evt) {
	      var originPrice = $('.service-origin-price').val() || 0;
		  var postPrice = $('.service-post-price').val() || 0;
		  var servicePrice = $('.service-service-price').val() || 0;
		  $('.edit-service-total-price').val((parseFloat(originPrice) + parseFloat(postPrice) + parseFloat(servicePrice)));
		  
	  });
	  
	  $('body').on('toggle','.service-price-type',function(evt){
	        evt.preventDefault();
	        var target = $(evt.target);
	        if(target.hasClass('mui-active')) {
	        	$('.service-price-group').fadeIn(200);
        		$('.edit-service-total-price').attr('readonly',true);
	        } else {
	        	$('.service-price-group').fadeOut(200);
	        	$('.edit-service-total-price').attr('readonly',false);
	        }
	   });
	  
	  $('.add-service-desc').click(function(evt) {
	      var descHtml = [];
	      descHtml.push('<div class="mui-input-row mui-text-left service-detail-group">');
	      descHtml.push(' <div class="group-close">X</div>');
	      descHtml.push(' <label>商品图文</label>');
	      descHtml.push(' <div class="row image-list" style="min-height:85px;height:auto">');
	      descHtml.push('   <div class="image-item image-upload" number="multi" style="background-image:url(/resources/wx/images/iconfont-tianjia.png)"></div>');
	      descHtml.push(' </div>');
	      descHtml.push(' <textarea  placeholder="描述" class="service-desc" style="width:100%;padding: 10px 15px;text-align: left;"></textarea>');
	      descHtml.push('</div>');
	      $('.add-service-desc').before(descHtml.join(''));
	  });
	
	
	  $('.add-service-btn').click(function(evt){
	    $('.service-add').find('.input-error').removeClass('input-error');
	
	    var service = {};
	    if(serviceId) {
	    	service.id = serviceId;
	    }
	    
	    service.category = $('.service-category').val();
	    
	    service.title = $('.service-title').val() || '';
	    if(service.title.trim().length == 0) {
	      mui.toast('标题不能为空');
	      $('.service-title').addClass('input-error');
	      return;
	    }
	
	    service.description = $('.service-desc').val() || '';
	    if(service.description.trim().length == 0) {
	      mui.toast('简单描述不能为空');
	      $('.service-desc').addClass('input-error');
	      return;
	    }
	
	    service.cover = $('#service-cover').attr('pic-path') || '';
	    if(service.cover.trim().length == 0) {
	      mui.toast('商品主图不能为空');
	      return;
	    }
	
	    var serviceDetailGroup = [];
	    $('.service-detail-group').each(function(index,dom){
	      var dd = {};
	      var pic = [];
	        $(dom).find('.image-item').each(function(index,input){
	        if($(input).attr('pic-path')) {
	          pic.push($(input).attr('pic-path'));
	        }
	      });
	      dd.pictures = pic;
	      dd.desc = encodeURIComponent($(dom).find('.service-desc').val() || '');
	      serviceDetailGroup.push(dd);
	    });
	
	    service.servicedetail = JSON.stringify(serviceDetailGroup);
	
	    var priceType = $('.service-price-type').hasClass('mui-active') ? '1':'0';
	    service.priceType = priceType;
	    
	    if(priceType == 1) {
    		 var originPrice = $('.service-origin-price').val();
    		 if(originPrice.trim().length == 0) {
    			 mui.toast('原价格不能为空');
    	  	     $('.service-origin-price').addClass('input-error');
    	  	     return; 
    		 }
    		 service.originPrice = originPrice;
    		 
    		 var postPrice = $('.service-post-price').val();
    		 if(postPrice.trim().length == 0) {
    			 mui.toast('直邮费不能为空');
    	  	     $('.service-post-price').addClass('input-error');
    	  	     return; 
    		 }
    		 service.postPrice = postPrice;
    		
    		 var servicePrice = $('.service-service-price').val();
    		 if(servicePrice.trim().length == 0) {
    			 mui.toast('服务费不能为空');
    	  	     $('.service-service-price').addClass('input-error');
    	  	     return; 
    		 }
    		 service.servicePrice = servicePrice;
    	}
	    
	    service.price = $('.edit-service-total-price').val() || '';
	    if(service.price.trim().length == 0) {
	      mui.toast('价格不能为空');
	      $('.service-title').addClass('input-error');
	      return;
	    }
	    service.status=1;//直接上线
	    var btn = $('.add-service-btn');
	    btn.text('正在保存').attr('disabled',true);
	
	    $.ajax({
	      type :  (serviceId ? 'put' : 'post'),
	      url  : "/user/service",
	      contentType : "application/json;charset=UTF-8",
	      data        : JSON.stringify(service),
	      success     : function(data){
	        if(data.code == '000000') {
	          mui.toast('发布服务成功');
	          window.location.href = '/wx/account-service?rmd='+new Date().getTime();
	        } else {
	          mui.toast('发布服务失败,失败原因:'+data.msg);
	        }
	        btn.text('直接上线').attr('disabled',false);
	      },
	      error     : function() {
	        btn.text('直接上线').attr('disabled',false);
	        mui.toast('发布服务出现异常,请重试');
	      }
	    });
	  });
	  
	  $('.save-service-btn').click(function(evt){
		    var service = {};
		    if(serviceId) {
		    	service.id = serviceId;
		    }
		    
		    service.category = $('.service-category').val();
		
		    service.title = $('.service-title').val() || '';
		    service.description = $('.service-desc').val() || '';
		    service.cover = $('#service-cover').attr('pic-path') || '';
		    var serviceDetailGroup = [];
		    $('.service-detail-group').each(function(index,dom){
		      var dd = {};
		      var pic = [];
		        $(dom).find('.image-item').each(function(index,input){
		        if($(input).attr('pic-path')) {
		          pic.push($(input).attr('pic-path'));
		        }
		      });
		      dd.pictures = pic;
		      dd.desc = encodeURIComponent($(dom).find('.service-desc').val() || '');
		      serviceDetailGroup.push(dd);
		    });
		
		    service.servicedetail = JSON.stringify(serviceDetailGroup);
		
		    var priceType = $('.service-price-type').hasClass('mui-active') ? '1':'0';
		    service.priceType = priceType;
		    
		    if(priceType == 1) {
	    		 var originPrice = $('.service-origin-price').val();
	    		 service.originPrice = originPrice;
	    		 
	    		 var postPrice = $('.service-post-price').val();
	    		 service.postPrice = postPrice;
	    		
	    		 var servicePrice = $('.service-service-price').val();
	    		 service.servicePrice = servicePrice;
	    	}
		    
		    service.price = $('.edit-service-total-price').val() || '';
		    var btn = $('.save-service-btn');
		    btn.text('正在保存').attr('disabled',true);
		
		    $.ajax({
		      type :  (serviceId ? 'put' : 'post'),
		      url  : "/user/servicetemp",
		      contentType : "application/json;charset=UTF-8",
		      data        : JSON.stringify(service),
		      success     : function(data){
		        btn.text('保存').attr('disabled',false);
		        if(data.code == '000000') {
		          mui.toast('发布服务成功');
		          window.location.href = '/wx/account-service?rmd='+new Date().getTime();
		        } else {
		          mui.toast('保存服务失败,失败原因:'+data.msg);
		        }
		      },
		      error     : function() {
		        btn.text('保存').attr('disabled',false);
		        mui.toast('保存服务出现异常,请重试');
		      }
		    });
		  });
	  
	  if(serviceId) {
		  renderService(serviceId);
	  }
	});
</script>
<%@ include  file="common/footer.jsp"%>