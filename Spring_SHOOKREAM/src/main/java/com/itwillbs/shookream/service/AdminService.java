package com.itwillbs.shookream.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itwillbs.shookream.mapper.AdminMapper;
import com.itwillbs.shookream.vo.CancelVo;
import com.itwillbs.shookream.vo.MemberVo;
import com.itwillbs.shookream.vo.OrderVo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.imageVo;

@Service
public class AdminService {
	@Autowired
	private AdminMapper mapper;
	
	// 상품 등록 insertProduct() 메서드
		// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
		public int insertProduct(ProductVo product) {
			return mapper.insertProduct(product);
		}
		
		// 상품 이미지 등록 insertProduct() 메서드
		// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
		public int insertImg(imageVo image, int product_idx) {
			return mapper.insertImage(image, product_idx);
		}
		
		// 상품 수정 updateProduct() 메서드
		public int updateProduct(int product_idx, ProductVo product, imageVo image) {
			mapper.modifyProduct(product_idx, product, image);
		    return 1;
		}
		// 상품 수정 - 이미지 수정 updateImage() 메서드
//		public int updateImage(int product_idx, ProductVo product, imageVo image) {
//			return mapper.modifyImage(product_idx, product, image);
//		}

		// 상품 삭제 deleteProduct() 메서드
		public int deleteProduct(int product_idx) {
			return mapper.removeProduct(product_idx);
		}
		// 상품 목록 조회 - getProductList()
		public List<ProductVo> getProductList() {
			return mapper.selectProductList();
		}
		// 이미지 목록 조회 
		public List<imageVo> getImgList(int product_idx) {
			return mapper.selectImgList(product_idx);
		}
//		public List<imageVo> getImgList() {
//			return mapper.selectImgList();
//		}
		
		public ProductVo getProduct(int product_idx) {
			return mapper.selectProduct(product_idx);
		}

		public imageVo getImage(int product_idx) {
			return mapper.selectImage(product_idx);
		}
		
		//회원목록
		public List<MemberVo> getMemberInfo() {
			return mapper.selectMember();
		}

		//주문목록
		public List<OrderVo> getOrderList() {
			return mapper.selectOrderList();
		}
		
		//주문목록 - 삭제
		public int deleteOrder(int order_idx) {
			return mapper.delectOrder(order_idx);
		}

		public List<CancelVo> getCancelList() {
			return mapper.getCancelList();
		}

		public String getToken() throws IOException {

		    HttpsURLConnection conn = null;

		    URL url = new URL("https://api.iamport.kr/users/getToken");

		    conn = (HttpsURLConnection) url.openConnection();

		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Content-type", "application/json");
		    conn.setRequestProperty("Accept", "application/json");
		    conn.setDoOutput(true);
		    JsonObject json = new JsonObject();

		    json.addProperty("imp_key", "4725107137406238");
		    json.addProperty("imp_secret", "X7yLcUDDEoAn5pi1ev41t1NiAVnKDNoAYtaYNeSxLBJZ3VyqnRM0eO62PRHA9ERWOdFMK1uqM6T8dugx");

		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

		    bw.write(json.toString());
		    bw.flush();
		    bw.close();

		    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

		    Gson gson = new Gson();

		    String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();

		    System.out.println("response : " + response);

		    String token = gson.fromJson(response, Map.class).get("access_token").toString();

		    br.close();
		    conn.disconnect();

		    return token;
		}

		public CancelVo getCancelInfo(int cancel_idx) {
			return mapper.getCancelInfo(cancel_idx);
		}

		public void Canceldelete(int cancel_idx) {
			mapper.Canceldelete(cancel_idx);
		}

		public void OrderListProgressModify(int order_idx) {
			mapper.OrderListProgressModify(order_idx);
		}

		public int getProduct_idx(int order_idx) {
			return mapper.getProduct_idx(order_idx);
		}	
	
}
