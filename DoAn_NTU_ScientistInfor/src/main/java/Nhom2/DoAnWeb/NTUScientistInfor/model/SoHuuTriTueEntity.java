package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "sohuutritue")
public class SoHuuTriTueEntity {

    @Id
    @Column(name = "SHTT_ID", length = 20, nullable = false)
    private String id;

    @Column(name = "TENBANG", length = 300, nullable = false)
    private String tenBang;

    @Column(name = "NAMCAP")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date namCap;

    @Enumerated(EnumType.STRING)
    @Column(name = "TOCHUCCAP")
    private ToChucCap toChucCap;

    @Column(name = "NOIDUNG", columnDefinition = "TEXT")
    private String noiDung;

    @OneToMany(mappedBy = "shttId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NhanSHTTEntity> nhanSHTT;

    public enum ToChucCap {
        Cục_Sở_hữu_trí_tuệ_Bộ_KH_CN,
        Bộ_Văn_hóa_Thể_thao_và_Du_lịch_Cục_Bản_quyền_Tác_giả
    }

    // Constructors
    public SoHuuTriTueEntity() {}

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenBang() {
        return tenBang;
    }

    public void setTenBang(String tenBang) {
        this.tenBang = tenBang;
    }

    public Date getNamCap() {
        return namCap;
    }

    public void setNamCap(Date namCap) {
        this.namCap = namCap;
    }

    public ToChucCap getToChucCap() {
        return toChucCap;
    }

    public void setToChucCap(ToChucCap toChucCap) {
        this.toChucCap = toChucCap;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public List<NhanSHTTEntity> getNhanSHTT() {
        return nhanSHTT;
    }

    public void setNhanSHTT(List<NhanSHTTEntity> nhanSHTT) {
        this.nhanSHTT = nhanSHTT;
    }
}