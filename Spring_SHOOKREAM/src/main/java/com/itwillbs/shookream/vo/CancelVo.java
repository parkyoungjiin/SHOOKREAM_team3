package com.itwillbs.shookream.vo;

public class CancelVo {
private int cancel_idx;
private String cancel_content;
private String cancel_product_name;
private int cancel_price;
private String cancel_reason;
private String imp_uid;
private int order_idx;
private String cancel_img;


public String getCancel_img() {
	return cancel_img;
}
public void setCancel_img(String cancel_img) {
	this.cancel_img = cancel_img;
}
public int getOrder_idx() {
	return order_idx;
}
public void setOrder_idx(int order_idx) {
	this.order_idx = order_idx;
}
public String getImp_uid() {
	return imp_uid;
}
public void setImp_uid(String imp_uid) {
	this.imp_uid = imp_uid;
}
public String getcancel_reason() {
	return cancel_reason;
}
public void setcancel_reason(String cancel_reason) {
	this.cancel_reason = cancel_reason;
}
public int getcancel_idx() {
	return cancel_idx;
}
public void setcancel_idx(int cancel_idx) {
	this.cancel_idx = cancel_idx;
}
public String getcancel_content() {
	return cancel_content;
}
public void setcancel_content(String cancel_content) {
	this.cancel_content = cancel_content;
}
public String getcancel_product_name() {
	return cancel_product_name;
}
public void setcancel_product_name(String cancel_product_name) {
	this.cancel_product_name = cancel_product_name;
}
public int getcancel_price() {
	return cancel_price;
}
public void setcancel_price(int cancel_price) {
	this.cancel_price = cancel_price;
}
@Override
public String toString() {
	return "CancelVo [cancel_idx=" + cancel_idx + ", cancel_content=" + cancel_content + ", cancel_product_name="
			+ cancel_product_name + ", cancel_price=" + cancel_price + ", cancel_reason=" + cancel_reason + ", imp_uid="
			+ imp_uid + ", order_idx=" + order_idx + ", cancel_img=" + cancel_img + "]";
}

}
