package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sach")
public class SachEntity {
	@Id
    @Column(name = "SACH_ID", length = 30, nullable = false)
    private String id;

    @Column(name = "TENSACH", length = 200, nullable = false)
    private String tenSach;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOAISACH", nullable = false)
    private LoaiSach loaiSach;

    @Column(name = "NAMXUATBAN")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate namXuatBan;

    @Enumerated(EnumType.STRING)
    @Column(name = "NHAXUATBAN", nullable = false)
    private NhaXuatBan nhaXuatBan;

    public enum LoaiSach {
        Giáo_trình,
        Sách_chuyên_khảo,
        Chương_sách,
        Sách_tham_khảo
    }

    public enum NhaXuatBan {
        NXB_Đại_học_Nha_Trang,
        NXB_Lao_Động
    }

    // Constructors, Getters, Setters

    public SachEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public LoaiSach getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(LoaiSach loaiSach) {
        this.loaiSach = loaiSach;
    }

    public LocalDate getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(LocalDate namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public NhaXuatBan getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(NhaXuatBan nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }
}
