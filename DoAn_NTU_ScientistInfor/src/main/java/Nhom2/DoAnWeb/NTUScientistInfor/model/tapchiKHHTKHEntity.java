package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tapchikh_htkh")
public class tapchiKHHTKHEntity {
	@Id
    @Column(name = "TAPCHI_ID", length = 20, nullable = false)
    private String tapChiId;

    @Column(name = "TENTAPCHI", length = 255)
    private String tenTapChi;

    @Column(name = "TOCHUC", length = 200)
    private String toChuc;

    @Enumerated(EnumType.STRING)
    @Column(name = "PHAMVI", nullable = true)
    private PhamViEnum phamVi;

    @Column(name = "MUCCHATLUONG", length = 200)
    private String mucChatLuong;

    @Column(name = "NAMDANG")
    private LocalDate namDang;

    public enum PhamViEnum {
        Trong_nước,
        Nước_ngoài
    }

    // Constructors, Getters, Setters

    public tapchiKHHTKHEntity() {}

    public String getTapChiId() {
        return tapChiId;
    }

    public void setTapChiId(String tapChiId) {
        this.tapChiId = tapChiId;
    }

    public String getTenTapChi() {
        return tenTapChi;
    }

    public void setTenTapChi(String tenTapChi) {
        this.tenTapChi = tenTapChi;
    }

    public String getToChuc() {
        return toChuc;
    }

    public void setToChuc(String toChuc) {
        this.toChuc = toChuc;
    }

    public PhamViEnum getPhamVi() {
        return phamVi;
    }

    public void setPhamVi(PhamViEnum phamVi) {
        this.phamVi = phamVi;
    }

    public String getMucChatLuong() {
        return mucChatLuong;
    }

    public void setMucChatLuong(String mucChatLuong) {
        this.mucChatLuong = mucChatLuong;
    }

    public LocalDate getNamDang() {
        return namDang;
    }

    public void setNamDang(LocalDate namDang) {
        this.namDang = namDang;
    }
}
