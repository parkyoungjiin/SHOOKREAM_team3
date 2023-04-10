<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>주문 상세 내용</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<!-- icon CDN -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<!-- 네이버아이디로그인 -->
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<!-- 구글 아이디 로그인 -->
<meta name="google-signin-client_id" content="1047574308186-h6ehte2k4901kjn1u3g5vnonbf2g56on.apps.googleusercontent.com">
 <style type="text/css">
 	 input[id="b"] {
        position: relative;
        top: 1.5px;
      }
      label[for="c"] {
        position: relative;
        top: -1.5px;
      }
 
 </style>

</head>
<body class="w3-content">
<!-- Sidebar/menu -->
<%-- <jsp:include page="../inc/side.jsp"/> --%>

<!-- Top menu on small screens -->
<!-- <header class="w3-bar w3-top w3-hide-large w3-black w3-xlarge"> -->
<!--   <div class="w3-bar-item w3-padding-24 w3-wide">SHOOKREAM</div> -->
<!--   <a href="javascript:void(0)" class="w3-bar-item w3-button w3-padding-24 w3-right" onclick="w3_open()"><i class="fa fa-bars"></i></a> -->
<!-- </header> -->

<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:100px;margin-top: 20px;margin-right: 100px;">
	
 <!-- Push down content on small screens -->
 <div class="w3-hide-large" style="margin-top:83px"></div>
 
 <!-- Top header -->
<!--  <div style="float: right;"> -->
<%--  <jsp:include page="../inc/top.jsp"/> --%>
<!-- </div> -->
  
  <!-- Top header -->
  
<!--   <div> -->
<!--   <header class="w3-container w3-xlarge"> -->
<!--     <p class="w3-left">주문하기</p> -->
<!--     <p class="w3-right"> -->
<!--     </p> -->
<!-- </header> -->
   
   <div id="form_area">
		  <table class="table" style="font-weight: bold;">
			<thead>
			    <tr>
			      <th scope="col" colspan="8" style="font-size: x-large; text-align: center">주문내역 목록</th>
			    </tr>
		   </thead>
		  <thead  class="table-primary" >
		    <tr>
		      <th scope="col" class ="th_cart"colspan="2">상품명</th>
		<!--       <th scope="col">상품명</th> -->	
		      <th scope="col"  class ="th_cart">상품금액</th>
		      <th scope="col"  class ="th_cart">할인금액</th>
		      <th scope="col"  class ="th_cart">주문금액</th>
		      <th scope="col"  class ="th_cart">수량</th>
		      <th scope="col"  class ="th_cart">배송정보</th>
		    </tr>
		  </thead>
		  <tbody>
		  	<!-- 카트 리스트가 없을 때 처리 -->
		    <c:if test="${order eq null or empty order}">
					<tr>
						<td colspan="8" style="text-align: center;"><b>주문내역이 없습니다.</b></td>
					</tr>
			</c:if>
			<!-- 카트 리스트가 있을 때 처리 -->
			<c:if test="${order ne null and not empty order}">
			<!-- 카트 목록(foreach로 처리) -->
			    <tr>
			      <td><a href="ProductInfoForm.po?product_idx=${order.product_idx }"><img src="upload/${product.image_main_file }"  alt="없음!" class="img-thumbnail" width="150" height="150" ></a></td>
			      <td class ="td_cart" style="text-align:left;">
			      <span style="font-size: 20px; font-weight: bold;"> ${order.order_product_name }<br></span>
			      <span style="color: #91949A;">색상 : ${order.order_color } / 사이즈 : ${order.order_size }</span>
			      </td>
				  <td class ="td_cart" id="cart_price"><fmt:formatNumber value="${product.product_price }" pattern="#,###원"></fmt:formatNumber></td>
			      <td class ="td_cart" id="cart_discount_price"><fmt:formatNumber value="${product.product_price * (product.product_discount_price / 100)}" pattern="#,###원"></fmt:formatNumber></td>
			      <td class ="td_cart" id="cart_order_price" ><fmt:formatNumber value="${order.order_price - product.product_price * (product.product_discount_price / 100)}" pattern="#,###원"></fmt:formatNumber></td> 
			      <td class ="td_cart" style="vertical-align: middle;">
<%-- 			      <button id="minus_btn${status.index }" class="btn btn-outline-dark btn-sm" onclick="amount_adjust_minus(this)"  style ="width: 30px; height: 35px; font-size: 15px;">-</button> --%>
<%-- 				  <input type="text" class="form-control" id="cart_count_id${status.index }" name="cart_count" value="${param.order_count }" required="required" readonly="readonly" style="width: 40px; text-align: center; display: inline-block;"> --%>
<%-- 			      <button id="plus_btn${status.index }"class="btn btn-outline-dark btn-sm" onclick="amount_adjust_plus(this)"  style ="width: 30px; height: 35px; font-size: 15px;">+</button> --%>
			       		${order.order_count } 개
			      </td>
			      <td class ="td_cart">무료배송</td>
			    </tr>
		    </c:if>
		  </tbody>
		</table>
   
	 <!-- 배송 정보 -->
<table class="table" id="delivery_table" style="border-collapse: separate; border-spacing: 0 13px; font-size: 18px">
	  <thead>
	    <tr>
	      <th scope="col" colspan="8" style="font-size: x-large;">배송 정보</th>
	    </tr>
	  </thead>
	  <tbody>
	   <tr>
	    	<th colspan="2">
	    		주문 하시는 분
	    	</th>
	    	<td colspan="6">
	    		${member.member_id} 님<br>
	    		${member.member_email }<br>
	    		${fn:substring(member.member_phone,0,3) } -  
	    		${fn:substring(member.member_phone,3,7) } -  
	    		${fn:substring(member.member_phone,7,12) }  
	    		<br>
	    	</td>
	   </tr>
	   <tr>
	   <th colspan="2">받으시는 분</th>   
	   <td>
	   	  <div id="address">
	   	  <div class="row mb-3">
                <label for="th" id="title_label" class="col-md-2 col-lg-2 col-form-label">성함</label>
                <div class="col-md-8 col-lg-3">
                  <input name="order_name" type="text" class="form-control" id="order_name" value="${order_detail.order_name}">
                </div>
           </div>
	 	 <div class="row mb-3">
               <label for="th" id="title_label" class="col-md-2 col-lg-2 col-form-label">연락처</label>
               <div class="col-md-8 col-lg-4">
                 <input name="order_phone" type="text" class="form-control" id="order_phone" value="${order_detail.order_phone }">
               </div>
           </div>
	 	 <div class="row mb-3">
                <label for="th" id="title_label" class="col-md-2 col-lg-2 col-form-label">주소</label>
                <div class="col-md-8 col-lg-5">
                  <input name="order_addr1" type="text" class="form-control" id="order_addr1" value="${fn:split(order_detail.order_address,',')[0]}">
                </div>
           </div>
	 	 <div class="row mb-3">
              <label for="th" id="title_label" class="col-md-2 col-lg-2 col-form-label"></label>
              <div class="col-md-8 col-lg-5">
                <input name="order_addr2" type="text" class="form-control" id="order_addr2" value="${fn:split(order_detail.order_address,',')[1]}">
              </div>
           </div>
	   </div>
	 
	   </td>
	   </tr>
	   <tr>
	   	<th colspan="2">배송 메세지</th>
	   	<td>
	   		${order_detail.order_content }
	   	 </td>
	   </tr>
	  </tbody>
	</table>
	
<!-- 할인 정보 및 총 결제가격 표시	 -->

<!-- 배송 정보 -->
<table class="table" id="delivery_table" style="border-collapse: separate; border-spacing: 0 13px; font-size: 18px">
  	  <input type="hidden" id="coupon_idx" >
	  <thead>
	    <tr>
	      <th scope="col" colspan="6" style="font-size: x-large;">할인 금액</th>
	    </tr>
	  </thead>
	  <tbody>
	   <tr>
		<th colspan="2">즉시 할인금액</th>
			<td colspan="4"><input type="text" class="form-control" readonly="readonly" value="<fmt:formatNumber value='${product.product_price * (product.product_discount_price / 100)}'></fmt:formatNumber>" style="width: 100px; display: inline-block; text-align: right; font-size: 18px ">원 할인 
	   		</td>
	   </tr>
	   <tr>
		 <c:if test="${coupon eq null or empty coupon}">
					<tr>
						<th colspan="2">사용된 쿠폰</th>`
						<td colspan="8" style="text-align:left;"><b>사용된 쿠폰이 없습니다.</b></td>
					</tr>
		</c:if>
		<c:if test="${coupon ne null or not empty coupon}">
		<th colspan="2">사용된 쿠폰</th>
			<td>${coupon.coupon_name }</td>
			<td colspan="8"><input type="text" class="form-control" id="priceValue" readonly="readonly" value="${coupon.coupon_benefit_price }" style="width: 100px; display: inline-block; font-size: 18px">원 할인 
	   	</td>
	   </c:if>
	   </tr>
	   </tbody>
 </table>
   
	 
	 <!-- 결제금액 영역 -->
		<div class="container px-4 text-center" id="totalResult" style="margin-top: 30px; font-size: 25px;">
		  <div class="row gx-5" >
		    <div class="col">
				<div class="p-3 border bg-light" style="font-size: 25px; ">
					<span style="margin-right: 12px">상품 금액</span> 
					<span style="font-size: 27px;">
						<fmt:formatNumber pattern="#,###" value="${product.product_price }"></fmt:formatNumber>
					</span>
					<span style="font-size: 27px; margin-right: 25px;">원</span>
					
					<span class="material-symbols-outlined" style="margin-right: 30px; font-size: 25px">do_not_disturb_on</span>	
									
					<span style="margin-right: 12px">할인 금액</span> 
					<span style="font-size: 27px;" id="discount_area">
						<fmt:formatNumber pattern="#,###" value="${product.product_price * (product.product_discount_price / 100)}"></fmt:formatNumber>
					</span>
					<span style="font-size: 27px; margin-right: 25px;">원</span>
					
					<span class="material-symbols-outlined" style="margin-right: 30px; font-size: 25px">equal</span>
					
					<span style="margin-right: 12px">총 결제금액</span> 
					<span style="font-size: 27px; color: blue;" id="order_total_area">
						<fmt:formatNumber pattern="#,###" value="${order.order_price }"></fmt:formatNumber>
					</span>
					<span style="font-size: 27px; margin-right: 25px;">원</span>
					
					<br>
		      	<input type="hidden" id="order_discount_price" value="${product.product_price * (product.product_discount_price / 100)}">
		      	<input type="hidden" id="order_total_price" value="${product.product_price - product.product_price * (product.product_discount_price / 100)}">
				</div>	    
				<input type="button" value="주문 취소"class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#modalDialogScrollable_pGroup" onclick="order_cancel()">		    
		    	<button type="button" class="btn btn-primary" onclick="window.close()">취소</button>
		    </div>
		  </div>
	    </div>
	    
<!-- </footer> -->
<!--  <footer> -->
<%--   	<jsp:include page="../inc/footer.jsp"/> --%>
<!--   </footer>  -->
<!-- Newsletter Modal -->
		<div id="newsletter" class="w3-modal">
		  <div class="w3-modal-content w3-animate-zoom" style="padding:32px">
		    <div class="w3-container w3-white w3-center">
		      <i onclick="document.getElementById('newsletter').style.display='none'" class="fa fa-remove w3-right w3-button w3-transparent w3-xxlarge"></i>
		      <h2 class="w3-wide" id="modal_test">NEWSLETTER</h2>
		      <p>Join our mailing list to receive updates on new arrivals and special offers.</p>
		      <p><input class="w3-input w3-border" type="text" placeholder="Enter e-mail"></p>
		      <button type="button" class="w3-button w3-padding-large w3-red w3-margin-bottom" onclick="document.getElementById('newsletter').style.display='none'">Subscribe</button>
		    </div>
		  </div>
		</div>
		 <!-- Modal Dialog Scrollable 2 -->
			 <!-- ================================================ 구매 거래처 검색2(모달) ================================================ -->
              <div class="modal fade" id="modalDialogScrollable_pGroup" tabindex="-1">
                <div class="modal-dialog modal-dialog-scrollable">
                  <div class="modal-content" style="width: 600px;">
                    <div class="modal-header">
                      <h5 class="modal-title">취소 사유</h5>
                    </div>
                    <div class="modal-body" id="modal-body-buyer" style="text-align: center;">
		                <!-- 상품 -->
		                <div class="row mb-3">
			              <label for="th" id="title_label" class="col-md-3 col-lg-3 col-form-label">상품</label>
			              	<div class="col-md-8 col-lg-5">
			                	<div class="input-group mb-6">
			                	<input name="order_addr2" type="text" id="cancle_product_name" class="form-control" value="${order.order_product_name }" id="new_order_addr2">
			             	  </div>
			             	 </div>
				        </div>
				        <!-- 취소 내용  -->
				        <div class="row mb-3">
			              <label for="th" id="title_label" class="col-md-3 col-lg-3 col-form-label">취소내용</label>
			              	<div class="col-md-8 col-lg-7">
			                	<div class="input-group mb-6">
				                	<select class="form-select" id="cancle_content">
							   			<option>직접입력</option>
							   			<option>사이즈가 안맞음 </option>
							   			<option>전화 부탁 드립니다</option>
							   			<option>소화전에 넣어 주세요</option>
							   		</select>
							   		
							   		<p><input type="checkbox" value="false" id="my_checkbox" style="margin-left: 5px; margin-top: 20px;" onclick='is_checked()'><span>직접 입력</span></p>
			             	  </div>
			             	 </div>
				        </div>
				        
				        <div class="row mb-3">
			              <label for="th" id="title_label" class="col-md-3 col-lg-3 col-form-label"></label>
			              	<div class="col-md-8 col-lg-7">
			             	  <textarea class="form-control" id="cancle_reason" rows="3" cols="30" style="width: 264px;" readonly="readonly"></textarea>
			             	</div>
				        </div>
				       
			   		</div>
						<input type="button" class="btn btn-secondary" value="환불신청" onclick="cancle_register()" style="width: 300px; margin-left: 170px; ">			        	 
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-primary" data-bs-dismiss="modal" style="float: right;">Close</button>
<!--                       <button type="button" class="btn btn-primary">Save</button> -->
                    </div>
                  </div>
                </div>
              </div><!-- End Modal Dialog Scrollable-->
	</div>
</div>




<!-- ------------------------------------------------------------------------------------------------------------>
<!-- 카카오 주소 API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
window.onload = function(){
    document.getElementById("address_kakao").addEventListener("click", function(){ //주소입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
                document.getElementById("new_order_addr1").value = data.address; // 주소 넣기
                document.querySelector("input[id=new_order_addr1]").focus(); //상세입력 포커싱
            }
        }).open();
    });
}
</script>
<script src="../js/jquery-3.6.3.js"></script>
<script type="text/javascript">
	
	function is_checked() {
		  // 1. checkbox element를 찾습니다.
		  const checkbox = document.getElementById('my_checkbox');
		  // 2. checked 속성을 체크합니다.
		  const is_checked = checkbox.checked;
		  
		  if(is_checked == true){
		  	document.getElementById('cancle_reason').readOnly = false; // readonly 활성화
		  	$("#cancle_content option:eq(0)").prop("selected",true);
		  }else {
			document.getElementById('cancle_reason').readOnly = true; // readonly 비활성화  
			document.getElementById('cancle_reason').value="";
			
		  }
}

	function cancle_register() {
		var cancel_content = $("#cancle_content").val();
		var cancel_reason = $("#cancle_reason").val();
		var cancel_product_name = $ ("#cancle_product_name").val();
		var cancel_price = "${order.order_price }";
		var check = confirm("간변결제는 부분 취소가 불가합니다. 진행하시겠습니까?");
		
		if(check){
		$.ajax({
			type: "GET",
			url: "ProductOrderCancel.po?order_idx="+${order.order_idx},
			data : {
				"cancel_content" : cancel_content,
				"cancel_reason" : cancel_reason,
				"cancel_product_name" : cancel_product_name,
				"cancel_price" : cancel_price,
				"imp_uid":"${order.imp_uid}",
				"order_idx" : "${order.order_idx}"
			}
		})
		.done(function(count) { // 요청 성공 시
			if(count == '0'){
				alert("주문취소 신청이 완료 되었습니다");
				var id = '${sessionScope.sId}';
				window.close();
				opener.location.reload();
			}else {
				alert("이미 환불 신청이 완료 되었습니다!");
			}
		})
	}
}	
</script>





<!-- 자바스크립트 부분 -->
<script type="text/javascript">
function CouponCheck() {
	let url = "CouponListForm.po?member_idx="+${sessionScope.member_idx}+"&product_price="+${product.product_price - product.product_price * (product.product_discount_price / 100)};  // 테스트용 파라미터임!
	let name = "Coupon List";
	let attr = "width=900, height=600, top=200, left=510"

	window.open(url, name, attr);
}

</script>
<script>

// Accordion 
function myAccFunc() {
  var x = document.getElementById("demoAcc");
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
  } else {
    x.className = x.className.replace(" w3-show", "");
  }
}

function myAccFunc1() {
	  var x = document.getElementById("cusAcc");
	  if (x.className.indexOf("w3-show") == -1) {
	    x.className += " w3-show";
	  } else {
	    x.className = x.className.replace(" w3-show", "");
	  }
	}

// Click on the "Jeans" link on page load to open the accordion for demo purposes
document.getElementById("myBtn").click();


// Open and close sidebar
function w3_open() {
  document.getElementById("mySidebar").style.display = "block";
  document.getElementById("myOverlay").style.display = "block";
}
 
function w3_close() {
  document.getElementById("mySidebar").style.display = "none";
  document.getElementById("myOverlay").style.display = "none";
}
</script>
<!-- Channel Plugin Scripts -->
<script>
  (function() {
    var w = window;
    if (w.ChannelIO) {
      return (window.console.error || window.console.log || function(){})('ChannelIO script included twice.');
    }
    var ch = function() {
      ch.c(arguments);
    };
    ch.q = [];
    ch.c = function(args) {
      ch.q.push(args);
    };
    w.ChannelIO = ch;
    function l() {
      if (w.ChannelIOInitialized) {
        return;
      }
      w.ChannelIOInitialized = true;
      var s = document.createElement('script');
      s.type = 'text/javascript';
      s.async = true;
      s.src = 'https://cdn.channel.io/plugin/ch-plugin-web.js';
      s.charset = 'UTF-8';
      var x = document.getElementsByTagName('script')[0];
      x.parentNode.insertBefore(s, x);
    }
    if (document.readyState === 'complete') {
      l();
    } else if (window.attachEvent) {
      window.attachEvent('onload', l);
    } else {
      window.addEventListener('DOMContentLoaded', l, false);
      window.addEventListener('load', l, false);
    }
  })();
  ChannelIO('boot', {
    "pluginKey": "552ea0bb-d4a5-4c70-8ba7-463b7682c434"
  });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>
