<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	* {
		font-family: "Noto Sans KR", sans-serif;
	}
</style>
</head>
<body>
<table class="table">
  <thead  class="table-dark" >
    <tr>
      <th scope="col">#</th>
      <th scope="col">name</th>
      <th scope="col">price</th>
      <th scope="col">Use</th>
      <th scope="col">StartDate</th>
      <th scope="col">EndDate</th>
      <th scope="col">Button</th>
    </tr>
  </thead>
  <tbody>
   <c:forEach var="coupon" items="${couponList }">
     <c:choose>
      	<c:when test="${coupon.coupon_isUse eq 0 }">
    <tr>
      <th scope="row" id="idx">${coupon.coupon_idx }</th>
      <td>${coupon.coupon_name }</td>
      <td id="price">${coupon.coupon_price }</td>
      <td>사용 가능</td>
      <td>${coupon.coupon_start }</td>
      <td>${coupon.coupon_end }</td>
      <td>
      <button type="button" class="btn btn-dark" id="useCoupon" value="${coupon.coupon_price}" onclick="useCoupon(${coupon.coupon_price},${coupon.coupon_idx})">적용하기</button>
    </tr>
    	</c:when>
      </c:choose>
     </c:forEach>
  </tbody>
</table>
<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
<script type="text/javascript">
	function useCoupon(price,idx) {
		// 부모창(주문 / 결제페이지)에서 값 가져오기 
		//1. 할인 금액
		var discount_price = parseInt(opener.$("#order_discount_price").val());

		//2. 총 결제금액
		var order_total_price = parseInt(opener.$("#order_total_price").val());
		
		//3. 쿠폰 적용한 할인금액
		 discount_price = (discount_price + price).toLocaleString("en-US");
		//4. 쿠폰 적용한 총 결제금액
		 order_total_price = (order_total_price - price).toLocaleString("en-US");
		//5. priceValue 값에 콤마
		price = price.toLocaleString("en-US");
// 		alert(order_price);
// 		alert(order_total_price);
		//클릭 시 부모창에 값 들어감.
		 window.opener.document.getElementById( "coupon_idx" ).value = idx;
		//상품 할인쿠폰 input 값에 들어감.
		 window.opener.document.getElementById( "priceValue" ).value = price;
		// 마지막 span영역에 쿠폰 적용된 할인가격, 총 결제금액이 들어감.
		 window.opener.document.getElementById( "discount_area" ).innerText = discount_price;
		 window.opener.document.getElementById( "order_total_area" ).innerText = order_total_price;
		 window.opener.document.getElementById( "order_total_price_pay" ).value = order_total_price;
		 close();
	}
	

</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>