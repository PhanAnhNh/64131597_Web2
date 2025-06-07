package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "nhangiaithuong")
@IdClass(NhanGTEntity.NhangiaithuongId.class)
public class NhanGTEntity {
    @Id
    @Column(name = "NKH_ID", nullable = false)
    private int nkhId;

    @Id
    @Column(name = "GT_ID", length = 20, nullable = false)
    private String gtId;

    @ManyToOne
    @JoinColumn(name = "NKH_ID", referencedColumnName = "NKH_ID", insertable = false, updatable = false)
    private NhaKhoaHoc nhaKhoaHoc;

    public NhanGTEntity() {}

    public NhanGTEntity(int nkhId, String gtId) {
        this.nkhId = nkhId;
        this.gtId = gtId;
    }

    public int getNkhId() {
        return nkhId;
    }

    public void setNkhId(int nkhId) {
        this.nkhId = nkhId;
    }

    public String getGtId() {
        return gtId;
    }

    public void setGtId(String gtId) {
        this.gtId = gtId;
    }

    public NhaKhoaHoc getNhaKhoaHoc() {
        return nhaKhoaHoc;
    }

    public void setNhaKhoaHoc(NhaKhoaHoc nhaKhoaHoc) {
        this.nhaKhoaHoc = nhaKhoaHoc;
    }

    public static class NhangiaithuongId implements Serializable {
        private int nkhId;
        private String gtId;

        public NhangiaithuongId() {}

        public NhangiaithuongId(int nkhId, String gtId) {
            this.nkhId = nkhId;
            this.gtId = gtId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NhangiaithuongId)) return false;
            NhangiaithuongId that = (NhangiaithuongId) o;
            return nkhId == that.nkhId && Objects.equals(gtId, that.gtId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nkhId, gtId);
        }
    }
}