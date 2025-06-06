package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "giaithuong")
public class GiaithuongEntity {
    @Id
    @Column(name = "GT_ID", length = 20, nullable = false)
    private String id;

    @Column(name = "TENGIAITHUONG", length = 100, nullable = false)
    private String tenGiaiThuong;

    @Column(name = "DONVITANG", length = 255)
    private String donViTang;

    @Enumerated(EnumType.STRING)
    @Column(name = "NOIDUNG", nullable = true)
    private NoiDung noiDung;

    @Column(name = "NAMTANG")
    private Date namTang;

    @OneToMany(mappedBy = "gtId", fetch = FetchType.LAZY)
    private List<NhanGTEntity> nhanGiaiThuong = new ArrayList<>();

    public enum NoiDung {
        Nhất, Nhì, Ba, Khác
    }

    // Constructors
    public GiaithuongEntity() {}

    public GiaithuongEntity(String id, String tenGiaiThuong, String donViTang, NoiDung noiDung, Date namTang) {
        this.id = id;
        this.tenGiaiThuong = tenGiaiThuong;
        this.donViTang = donViTang;
        this.noiDung = noiDung;
        this.namTang = namTang;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenGiaiThuong() {
        return tenGiaiThuong;
    }

    public void setTenGiaiThuong(String tenGiaiThuong) {
        this.tenGiaiThuong = tenGiaiThuong;
    }

    public String getDonViTang() {
        return donViTang;
    }

    public void setDonViTang(String donViTang) {
        this.donViTang = donViTang;
    }

    public NoiDung getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(NoiDung noiDung) {
        this.noiDung = noiDung;
    }

    public Date getNamTang() {
        return namTang;
    }

    public void setNamTang(Date namTang) {
        this.namTang = namTang;
    }

    public List<NhanGTEntity> getNhanGiaiThuong() {
        return nhanGiaiThuong;
    }

    public void setNhanGiaiThuong(List<NhanGTEntity> nhanGiaiThuong) {
        this.nhanGiaiThuong = nhanGiaiThuong;
    }
}