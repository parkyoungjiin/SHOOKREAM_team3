# Spring_SHOOKREAM
Spring으로 개발한 신발 전문 쇼핑몰입니다.

---

## 목차
1. [서비스 소개](#1-서비스-소개)
2. [기능 목록](#2-기능-목록)
3. [프로젝트 화면](#3-프로젝트-화면)
4. [설계과정 산출물](#4-설계과정-산출물)

## 1. 서비스 소개
> 운동화를 찾는 사람들만을 위한 쇼핑몰로, 여러 브랜드가 입점하여 판매하는 샵인샵 쇼핑몰 형태의 서비스를 제공합니다.

- 참여인원 : Backend 6명
- 개발기간
  - 2022.12.12 ~ 2023.01.12(JSP 1.0 버전)<br>
  - 2023.02.26 ~ 2023.03.26(Spring 2.0 버전)<br>
- 개발 언어 : JAVA, JSP, JavaScript (JS), HTML5, CSS
- 프레임워크 및 라이브러리
  - FE : BootStrap, JQuery, AJAX 
  - BE : Spring, MySQL, Mybatis
  - 라이브러리 : Kakao 주소 API, i'mport의 결제 API, 채널톡 API
  - IDE : Eclipse(STS4)


## 2. 주요 기능
❗️ 고객
- 로그인, 아이디 및 비밀번호 찾기(임시비밀번호)
- 회원가입
  - 정규표현식을 이용한 아이디 검증 및 아이디 중복확인
  - 이메일 인증
  - 다음 주소 API
- 회원정보 수정

❗️ 상품
- 상품 상세페이지
  - 찜 기능, 장바구니 담기
- 장바구니
- 상품 구매
  - i'mport의 결제 API
- 최근 본 상품

❗️ 게시판 및 문의
- 게시판 및 1:1문의
  - 채널톡 API

❗️ 관리자(admin) 페이지
  - 상품 관리(등록, 수정, 삭제)
  - 회원 관리
  - 게시판 관리(등록, 수정, 삭제)
  - 주문 관리
  - 쿠폰 관리(발급, 수정)

## 3. 담당 기능 화면
### 3-1. 상품 등록
![img](https://user-images.githubusercontent.com/112313165/278384612-540b703f-007e-4d97-b234-15acaef0eaea.png) 

### 3-2. 상품 목록 & 상품 수정
![img](https://user-images.githubusercontent.com/112313165/278384745-8950f748-05c7-4df7-8c7b-feaf93730315.png) 
![img](https://user-images.githubusercontent.com/112313165/278384739-5a38398b-6db6-43c2-bc4c-23e398fc1e4e.png)

### 3-3. 장바구니
![img](https://user-images.githubusercontent.com/112313165/278384731-339ac2cb-57c4-4a65-b479-87d6e3d59a95.png)
- 장바구니 체크박스
![gif](https://user-images.githubusercontent.com/112313165/278381116-1d384027-aeaf-4f71-bcfc-421d8ded9d55.gif)
- 장바구니 수량 변경
![gif](https://user-images.githubusercontent.com/112313165/278381293-a7fc069e-6549-4fc3-89fc-131a2e97c9ad.gif)
## 4. 설계과정 산출물
![img](https://user-images.githubusercontent.com/112313165/278380815-346909b8-7dbf-4f8a-a5d5-7a2007bf329a.png)