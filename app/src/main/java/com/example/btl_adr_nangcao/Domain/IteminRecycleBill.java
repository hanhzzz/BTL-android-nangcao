package com.example.btl_adr_nangcao.Domain;

public class IteminRecycleBill {
    private String billId;
    private int cartNumberInCart;
    private int cartPrice;
    private String cartTitle;

    public IteminRecycleBill() {
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public int getCartNumberInCart() {
        return cartNumberInCart;
    }

    public void setCartNumberInCart(int cartNumberInCart) {
        this.cartNumberInCart = cartNumberInCart;
    }

    public int getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(int cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getCartTitle() {
        return cartTitle;
    }

    public void setCartTitle(String cartTitle) {
        this.cartTitle = cartTitle;
    }
}
