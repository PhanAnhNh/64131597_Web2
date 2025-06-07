package Nhom2.DoAnWeb.NTUScientistInfor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "taikhoan")
public class TaiKhoanEntity {
	@Id
    @Column(name = "TENTAIKHOAN", length = 30, nullable = false)
    private String tenTaiKhoan;

    @Column(name = "MATKHAU", length = 250, nullable = false)
    private String matKhau;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOAITAIKHOAN", nullable = false)
    private LoaiTaiKhoan loaiTaiKhoan;

    public enum LoaiTaiKhoan {
        Admin,
        NKH
    }

    // Constructors, Getters, Setters

    public TaiKhoanEntity() {}
    
    public TaiKhoanEntity(String tenTaiKhoan, String matKhau, LoaiTaiKhoan loaiTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public LoaiTaiKhoan getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }
}
