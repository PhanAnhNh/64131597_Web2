package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "baibaokh")
public class BaiBaoKHEntity {
    @Id
    @Column(name = "BAIBAO_ID", length = 20, nullable = false)
    private String baiBaoId;

    @Column(name = "TAPCHI_ID", length = 20, nullable = false)
    private String tapChiId;

    @Column(name = "TENBAIBAO", length = 300, nullable = false)
    private String tenBaiBao;

    @Column(name = "LINHVUC", length = 50)
    private String linhVuc;

    @Column(name = "NOIXUATBAN", length = 100)
    private String noiXuatBan;

    @Column(name = "NAMDANG")
    private Date namDang;

    @Column(name = "LIENKET", length = 100)
    private String lienKet;

    @Transient // Không lưu vào cơ sở dữ liệu
    private List<Integer> tacGiaIds;

    // Map the relationship to SangTacEntity
    @OneToMany(mappedBy = "baiBao", fetch = FetchType.LAZY)
    private List<SangTacEntity> sangTacs;

    // Constructors
    public BaiBaoKHEntity() {}

    public BaiBaoKHEntity(String baiBaoId, String tapChiId, String tenBaiBao, String linhVuc, String noiXuatBan, Date namDang, String lienKet) {
        this.baiBaoId = baiBaoId;
        this.tapChiId = tapChiId;
        this.tenBaiBao = tenBaiBao;
        this.linhVuc = linhVuc;
        this.noiXuatBan = noiXuatBan;
        this.namDang = namDang;
        this.lienKet = lienKet;
    }

    // Getters and Setters
    public String getBaiBaoId() {
        return baiBaoId;
    }

    public void setBaiBaoId(String baiBaoId) {
        this.baiBaoId = baiBaoId;
    }

    public String getTapChiId() {
        return tapChiId;
    }

    public void setTapChiId(String tapChiId) {
        this.tapChiId = tapChiId;
    }

    public String getTenBaiBao() {
        return tenBaiBao;
    }

    public void setTenBaiBao(String tenBaiBao) {
        this.tenBaiBao = tenBaiBao;
    }

    public String getLinhVuc() {
        return linhVuc;
    }

    public void setLinhVuc(String linhVuc) {
        this.linhVuc = linhVuc;
    }

    public String getNoiXuatBan() {
        return noiXuatBan;
    }

    public void setNoiXuatBan(String noiXuatBan) {
        this.noiXuatBan = noiXuatBan;
    }

    public Date getNamDang() {
        return namDang;
    }

    public void setNamDang(Date namDang) {
        this.namDang = namDang;
    }

    public String getLienKet() {
        return lienKet;
    }

    public void setLienKet(String lienKet) {
        this.lienKet = lienKet;
    }

    public List<Integer> getTacGiaIds() {
        return tacGiaIds;
    }

    public void setTacGiaIds(List<Integer> tacGiaIds) {
        this.tacGiaIds = tacGiaIds;
    }

    public List<SangTacEntity> getSangTacs() {
        return sangTacs;
    }

    public void setSangTacs(List<SangTacEntity> sangTacs) {
        this.sangTacs = sangTacs;
    }
}