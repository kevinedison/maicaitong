<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>带点啥</title>
    <link rel="stylesheet" href="/resources/m/css/framework7.material.min.css">
    <link rel="stylesheet" href="/resources/m/css/my-app.css">
  </head>
  <body>
     <div class="statusbar-overlay"></div>
     <div class="panel-overlay"></div>
     <aside class="panel panel-left panel-cover">
      <div class="view navbar-fixed">
        <div class="pages">
          <div data-page="panel-left" class="page left-panel">
            <div class="page-content">
              <div class="account-info">
                 <div class="item-content">
                    <div class="item-media"><img src="/resources/m/img/images/pomotion-korea.png" width=65 height=65></div>
                    <div class="item-inner">
                      <div class="item-title"><span class="name">何学苗</span></div>
                      <div class="item-title"><span class="location">迪拜</span></div>
                    </div>
                    <p class="introduce"></p>
                  </div>
              </div>
              <div class="list-block left-menu-list">
                <ul>
                  <li><a href="order.html" class="item-link close-panel">
                      <div class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                          <div class="item-title">订单</div>
                          <div class="item-after"><span class="badge bg-pink">2</span></div>
                        </div>
                      </div></a></li>
                  <li><a href="receivers.html" class="item-link close-panel">
                      <div class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                          <div class="item-title">收货人信息维护</div>
                        </div>
                      </div></a></li>
                  <li><a href="/resources/m/model/buyer-apply.html" class="item-link close-panel">
                      <div class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                          <div class="item-title">成为买手</div>
                        </div>
                      </div></a></li>
                  <li><a href="/resources/m/model/user-agreement.html" class="item-link close-panel">
                      <div class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                          <div class="item-title">服务协议</div>
                        </div>
                      </div></a></li>
                 <li><a href="/resources/m/model/about.html" class="item-link close-panel">
                      <div class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                          <div class="item-title">关于</div>
                        </div>
                      </div></a></li>    
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
     </aside>
     <article class="views">
      <div class="view view-main">
        <div class="pages navbar-fixed">
          <div data-page="index" class="page">
            <div class="navbar">
              <div class="navbar-inner">
                <div class="left"><a href="#" class="open-panel link icon-only"><i class="icon icon-bars"></i></a></div>
                <div class="center">带点啥</div>
                <div class="right"><a href="/resources/m/model/search.html" class="link icon-only"><i class="icon icon-search"></i></a></div>
              </div>
            </div>
            <div class="toolbar tabbar">
			   <div class="toolbar-inner identity-tab"><a href="#user" class="tab-link active">用户</a><a href="#buyer" class="tab-link">买手</a></div>
			</div>
			<div class="page-content tab-content ">
			    <div class="tabs">
			      <div id="user" class="tab active"> 
			        <div class="page-content tab-inner-content user-recommendation">
			        
			          <a href="/resources/m/model/backet.html" class="floating-button backet">44</a>
			          
			          <div data-pagination=".swiper-pagination" data-loop="true" data-speed="400" class="swiper-container swiper-init user-slider" >
					      <div class="swiper-pagination"></div>
					      <div class="swiper-wrapper">
					        <div class="swiper-slide"><a href="detail.html"><img src="/resources/m/img/images/pomotion-korea.png" width="100%"></a></div>
					      </div>
					   </div>
					   
					   <div class="content-block color-button-bar">
						  <div class="row">
					        <div class="col-25"></div>
					        <div class="col-25"><a href="/resources/m/model/tao.html" class="color-button color-button-tao">海淘</a></div>
					        <div class="col-25"><a href="/resources/m/model/discover.html" class="color-button color-button-discover">发现</a></div>
					        <div class="col-25"></div>
					      </div>
					   </div>	
					   
					   <div class="content-block-title">关注带点啥</div>
					   <img src="/resources/m/img/images/cellglo-footer.png" width="100%">
					   
					   <div class="content-block-title">猜你喜欢</div>
					   <div class="card-list">
					       <div class="card goods">
						      <div class="card-content">
						        <img src="/resources/m/img/images/pomotion-korea.png" width="100%">
						        <div class="card-content-inner">
						          <p class="name"><a href="/resources/m/model/goods.html">What a nice photo i took yesterday!</a></p>
						          <p class="description">What a nice photo i took yesterday!What a nice photo i took yesterday!What a nice photo i took yesterday!What a nice photo i took yesterday!What a nice photo i took yesterday!</p>
						          <div class="item-content buyer-bar">
				                    <div class="item-media avatar"><img src="/resources/m/img/images/pomotion-korea.png"></div>
				                    <div class="item-inner buyer-info">
				                      <div class="item-title"><span class="name"><a href="/resources/m/model/buyer.html">何学苗</a></span><span class="location">迪拜</span><span class="identify">专职买手</span></div>
				                    </div>
				                  </div>
						          <div class="action-bar">
						             <span class="price">￥0000 <i class="price-detail">(￥8800+￥800+￥99)</i></span>
						             <span class="add-backet"><i class="icon icon-f7"></i></span>
						          </div>
						        </div>
						      </div>
						    </div>
							<div class="card goods">
						      <div class="card-content">
						        <img src="/resources/m/img/images/pomotion-korea.png" width="100%">
						        <div class="card-content-inner">
						          <p class="name"><a href="detail.html">What a nice photo i took yesterday!</a></p>
						          <p class="description">What a nice photo i took yesterday!What a nice photo i took yesterday!What a nice photo i took yesterday!What a nice photo i took yesterday!What a nice photo i took yesterday!</p>
						          <div class="item-content buyer-bar">
				                    <div class="item-media avatar"><img src="/resources/m/img/images/pomotion-korea.png"></div>
				                    <div class="item-inner buyer-info">
				                      <div class="item-title"><span class="name">何学苗</span><span class="location">迪拜</span><span class="identify">专职买手</span></div>
				                    </div>
				                  </div>
						          <div class="action-bar">
						             <span class="price">￥0000 <i class="price-detail">(￥8800+￥800+￥99)</i></span>
						             <span class="add-backet"><i class="icon icon-f7"></i></span>
						          </div>
						        </div>
						      </div>
						    </div>
						</div>	
			        </div>
			      </div>
			      <div id="buyer" class="tab">
			         <div class="page-content tab-inner-content">
			            <div class="buyer-apply">
			               <img src="/resources/m/img/images/buyer-apply.jpg" width="100%">
			               <h3>成为第一批华人时尚买手</h3>
			               <p>何学苗 迪拜 专职买手</p>
			               <a href="/resources/m/model/buyer-apply-agreement.html" class="button button-fill buyer-apply-button">成为买手</a>
			            </div>   
					 </div>  
			      </div>
			    </div>
			</div>
          </div>
        </div>
      </div>
    </article>
    
    <div class="picker-modal picker-modal-backet">
      <div class="close-bar"><i class="icon icon-plus close-picker"></i></div>
      <div class="picker-modal-inner">
        <div class="add-backet-detail">
            <div class="row goods">
	          <div class="col-33"><img src="/resources/m/img/images/pomotion-korea.png" width=80 height=80></div>
	          <div class="col-66">
	            <div class="name">Billie Jean</div>
	            <div class="price">Billie Jean</div>
	            <div class="price-detail">Billie Jean</div>
	          </div>
	        </div>
            <div class="row backet-number-choose">
	          <div class="col-33 title">购买数量</div>
	          <div class="col-66" style="text-align:right">
	             <div class="numbox">
					<button class="button button-fill" type="button">-</button>
					<input class="mui-input-numbox" type="number" value=1 min=1/>
					<button class="button button-fill" type="button">+</button>
				 </div>
	          </div>
	        </div>
	        <p class="buttons-row"><a class="button button-fill button-raised">加入购物车</a></p>
	      </div>            
      </div>
    </div>
    <script type="text/javascript" src="/resources/m/js/framework7.min.js"></script>
    <script type="text/javascript" src="/resources/m/js/my-app.js"></script>
  </body>
</html>