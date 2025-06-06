package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "detaikh")
public class DetaiKHEntitty {

    public enum CapDeTai {
        Đề_tài_cấp_Khoa_Công_Nghệ_Thông_Tin,
        Đề_tài_cấp_Đại_học_Nha_Trang,
        Đề_tài_cấp_Tỉnh,
        Đề_tài_cấp_Bộ;
        
        @Override
        public String toString() {
            return name().replace("_", " ");
        }
    }
    

    public enum DonViChuTri {
        Khoa_Công_Nghệ_Thông_Tin,
        Trường_Đại_học_Nha_Trang;
        
        @Override
        public String toString() {
            return name().replace("_", " ");
        }
    }

    public enum TinhTrang {
        Đã_hoàn_thành,
        Đang_thực_hiện;
        
        @Override
        public String toString() {
            return name().replace("_", " ");
        }
    }

    public enum XepLoai {
        Chưa_xếp_loại,
        Tốt;
        
        @Override
        public String toString() {
            return name().replace("_", " ");
        }
    }

    @Id
    @Column(name = "DETAI_ID")
    private String deTaiId;

    @Column(name = "TENDETAI", nullable = false, length = 300)
    private String tenDeTai;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAPDETAI", nullable = false)
    private CapDeTai capDeTai;

    @Enumerated(EnumType.STRING)
    @Column(name = "DONVICHUTRI", nullable = false)
    private DonViChuTri donViChuTri;

    @Column(name = "MUCTIEU", columnDefinition = "TEXT")
    private String mucTieu;

    @Column(name = "NOIDUNG", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "KETQUA", columnDefinition = "TEXT")
    private String ketQua;

    @Column(name = "NAMBATDAU")
    private Date namBatDau;

    @Column(name = "NAMKETTHUC")
    private Date namKetThuc;

    @Enumerated(EnumType.STRING)
    @Column(name = "TINHTRANG", nullable = false)
    private TinhTrang tinhTrang;

    @Enumerated(EnumType.STRING)
    @Column(name = "XEPLOAI", nullable = false)
    private XepLoai xepLoai;

    @ManyToMany(mappedBy = "nkh_nghiencuu")
    @JsonBackReference
    private List<NhaKhoaHoc> nhaKhoaHocs = new ArrayList<>();

    // Getters and Setters
    public String getDeTaiId() {
        return deTaiId;
    }

    public void setDeTaiId(String deTaiId) {
        this.deTaiId = deTaiId;
    }

    public String getTenDeTai() {
        return tenDeTai;
    }

    public void setTenDeTai(String tenDeTai) {
        this.tenDeTai = tenDeTai;
    }

    public CapDeTai getCapDeTai() {
        return capDeTai;
    }

    public void setCapDeTai(CapDeTai capDeTai) {
        this.capDeTai = capDeTai;
    }

    public DonViChuTri getDonViChuTri() {
        return donViChuTri;
    }

    public void setDonViChuTri(DonViChuTri donViChuTri) {
        this.donViChuTri = donViChuTri;
    }

    public String getMucTieu() {
        return mucTieu;
    }

    public void setMucTieu(String mucTieu) {
        this.mucTieu = mucTieu;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }

    public Date getNamBatDau() {
        return namBatDau;
    }

    public void setNamBatDau(Date namBatDau) {
        this.namBatDau = namBatDau;
    }

    public Date getNamKetThuc() {
        return namKetThuc;
    }

    public void setNamKetThuc(Date namKetThuc) {
        this.namKetThuc = namKetThuc;
    }

    public TinhTrang getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(TinhTrang tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public XepLoai getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(XepLoai xepLoai) {
        this.xepLoai = xepLoai;
    }

    public List<NhaKhoaHoc> getNhaKhoaHocs() {
        return nhaKhoaHocs;
    }

    public void setNhaKhoaHocs(List<NhaKhoaHoc> nhaKhoaHocs) {
        this.nhaKhoaHocs = nhaKhoaHocs;
    }
}