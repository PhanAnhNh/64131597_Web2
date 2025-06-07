package Nhom2.DoAnWeb.NTUScientistInfor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sangtac")
@IdClass(SangTacId.class)
public class SangTacEntity {
    @Id
    @Column(name = "NKH_ID")
    private Integer nkhId;

    @Id
    @Column(name = "TENBAIBAO_ID")
    private String baiBaoId;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "NKH_ID", insertable = false, updatable = false)
    private NhaKhoaHoc nhaKhoaHoc;

    @ManyToOne
    @JoinColumn(name = "TENBAIBAO_ID", insertable = false, updatable = false)
    private BaiBaoKHEntity baiBao;

    // Getters and setters
    public Integer getNkhId() {
        return nkhId;
    }

    public void setNkhId(Integer nkhId) {
        this.nkhId = nkhId;
    }

    public String getBaiBaoId() {
        return baiBaoId;
    }

    public void setBaiBaoId(String baiBaoId) {
        this.baiBaoId = baiBaoId;
    }

    public NhaKhoaHoc getNhaKhoaHoc() {
        return nhaKhoaHoc;
    }

    public void setNhaKhoaHoc(NhaKhoaHoc nhaKhoaHoc) {
        this.nhaKhoaHoc = nhaKhoaHoc;
    }

    public BaiBaoKHEntity getBaiBao() {
        return baiBao;
    }

    public void setBaiBao(BaiBaoKHEntity baiBao) {
        this.baiBao = baiBao;
    }
}
