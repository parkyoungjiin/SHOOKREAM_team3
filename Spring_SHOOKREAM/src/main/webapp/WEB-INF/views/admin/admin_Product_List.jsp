<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <c:set var="path" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 목록</title>
<!-- 삭제 버튼에 confirm 추가 -->
<script>
	function deleteProduct(idx) {
		var result = confirm("삭제하시겠습니까?");

		if (result) {
			location.href="ProductDeletePro.po?product_idx=" + idx;
		} else {
			alert("삭제가 취소되었습니다");
		}
	};
</script>
<meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>상품 조회</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="${path}/resources/css/styles.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
        <script type="text/javascript">
			<%
			String sId = (String)session.getAttribute("sId");
			String id = request.getParameter("id");
			if(sId == null || !sId.equals("admin")) { 
			%>
				alert("잘못된 접근입니다!")
				location.href=history.back();
			<% 
			} 
			%>
		</script>
		<style type="text/css">
			* {
				font-family: "Noto Sans KR", sans-serif;
				
			}
 			table{ 
 			width: 100%; 
     		table-layout: fixed; 
     		text-align: center; 
 			} 
		</style>
</head>
 <body class="sb-nav-fixed">
	
  	<!-- TOP -->
       <jsp:include page="./inc2/top.jsp"></jsp:include>
          
    <!-- SIDE --> 
       <jsp:include page="./inc2/side.jsp"></jsp:include>            
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">상품 목록</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="Admin.ad">Dashboard</a></li>
<!--                             <li class="breadcrumb-item active">Tables</li> -->
                        </ol>
                        <div class="card mb-4">
<!--                             <div class="card-body"> -->
<!--                                 DataTables is a third party plugin that is used to generate the demo table below. For more information about DataTables, please visit the -->
<!--                                 <a target="_blank" href="https://datatables.net/">official DataTables documentation</a> -->
<!--                                 . -->
<!--                             </div> -->
                        </div>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                상품 목록 조회
                            </div>
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                            <th>번호</th>
                                            <th>상품명</th>
                                            <th width="150px">이미지</th>
                                            <th>브랜드</th>
                                            <th>가격</th>
                                            <th>재고</th>
                                            <th>등록일</th>
                                            <th>수정</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                      <c:forEach var="product" items="${productList }">
										<tr>
										<td id="product_idx">${product.product_idx }</td>
										<td>${product.product_name } <br>(색상 : ${product.product_color })</td>   
										<td><img src="${path}/resources/upload/${product.image_main_file }" class="img-thumbnail" onError="this.onerror=null; this.src='resources/images/noImg.JPG';" alt="..." style="width:100px; height:100px;"></td>
<%-- 										<td><img src="${path}/resources/upload/${product.image_main_file }" class="img-thumbnail" onError="this.onerror=null; this.src='resources/images/noImg.JPG';" alt="..." style="width:100px; height:100px;"></td> --%>
<%-- 										<td><img src="${path}/resources/upload/${image.image_main_file }" class="img-thumbnail" onError="this.onerror=null; this.src='resources/images/noImg.JPG';" alt="..." style="width:100px; height:100px;"></td> --%>
										<td>${product.product_brand }</td>
										<td><fmt:formatNumber value="${product.product_price }" pattern="#,###원"></fmt:formatNumber> </td>
										<td><fmt:formatNumber value="${product.product_amount }" pattern="#개"></fmt:formatNumber></td>
										<td><fmt:formatDate value="${product.product_date}" pattern="yyyy-MM-dd"/></td>
										<td>
										
										<button type="button" class="btn btn-light" onclick="location.href ='ProductModifyForm.po?product_idx=${product.product_idx}'">수정
										</button>
											<input type="hidden" name="image_main_file" value="${product.image_main_file}">
<%-- 										<button type="button" class="btn btn-light" onclick= "deleteProduct(${product.product_idx})">삭제</button> --%>
										<form action="ProductDeletePro.po" method="post" onsubmit="location.href='ProductList.po'">
											<input type="hidden" name="product_idx" value="${product.product_idx}">
											<input type="hidden" name="image_main_file" value="${product.image_main_file}">
											<input type="submit" class="btn btn-light" value="삭제">
										</form>
										</td>
										</tr> 
										</c:forEach>  
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2022</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
<!--         <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script> -->
        <script src="${path}/resources/js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="${path}/resources/js/datatables-simple-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
       
    </body>
</html>