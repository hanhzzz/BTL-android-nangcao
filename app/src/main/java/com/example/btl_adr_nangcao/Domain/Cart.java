package com.example.btl_adr_nangcao.Domain;

public class Cart {
    private String cartTitle;
    private double cartPrice;
    private String cartImagePath;
    private double cartStar;
    private String cartId;
    private String cartDescription;
    private double totalPrice;
    private int cartNumberInCart;

    public Cart() {
    }

    public String getCartTitle() {
        return cartTitle;
    }

    public void setCartTitle(String cartTitle) {
        this.cartTitle = cartTitle;
    }

    public double getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(double cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getCartImagePath() {
        return cartImagePath;
    }

    public void setCartImagePath(String cartImagePath) {
        this.cartImagePath = cartImagePath;
    }

    public double getCartStar() {
        return cartStar;
    }

    public void setCartStar(double cartStar) {
        this.cartStar = cartStar;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCartDescription() {
        return cartDescription;
    }

    public void setCartDescription(String cartDescription) {
        this.cartDescription = cartDescription;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCartNumberInCart() {
        return cartNumberInCart;
    }

    public void setCartNumberInCart(int cartNumberInCart) {
        this.cartNumberInCart = cartNumberInCart;
    }
}
