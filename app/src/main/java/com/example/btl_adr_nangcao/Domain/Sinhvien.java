package com.example.btl_adr_nangcao.Domain;

public class Sinhvien {
    private String MaSV;
    private String HotenSV;
    private String Diachi;
    private String Ngaysinh;
    private String Gioitinh;
    private String Email;
    private String Diemtrungbinh;

    public Sinhvien() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getHotenSV() {
        return HotenSV;
    }

    public void setHotenSV(String hotenSV) {
        HotenSV = hotenSV;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getNgaysinh() {
        return Ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        Ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        Gioitinh = gioitinh;
    }

    public String getDiemtrungbinh() {
        return Diemtrungbinh;
    }

    public void setDiemtrungbinh(String diemtrungbinh) {
        Diemtrungbinh = diemtrungbinh;
    }
}
