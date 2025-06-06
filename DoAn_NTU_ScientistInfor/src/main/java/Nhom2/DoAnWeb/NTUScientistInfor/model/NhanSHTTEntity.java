package Nhom2.DoAnWeb.NTUScientistInfor.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "nhanshtt")
@IdClass(NhanSHTTEntity.NhanshttId.class)
public class NhanSHTTEntity {

    @Id
    @Column(name = "NKH_ID", nullable = false)
    private int nkhId;

    @Id
    @Column(name = "SHTT_ID", length = 20, nullable = false)
    private String shttId;

    @ManyToOne
    @JoinColumn(name = "NKH_ID", referencedColumnName = "NKH_ID", insertable = false, updatable = false)
    private NhaKhoaHoc nhaKhoaHoc;

    public NhanSHTTEntity() {}

    public NhanSHTTEntity(int nkhId, String shttId) {
        this.nkhId = nkhId;
        this.shttId = shttId;
    }

    public int getNkhId() {
        return nkhId;
    }

    public void setNkhId(int nkhId) {
        this.nkhId = nkhId;
    }

    public String getShttId() {
        return shttId;
    }

    public void setShttId(String shttId) {
        this.shttId = shttId;
    }

    public NhaKhoaHoc getNhaKhoaHoc() {
        return nhaKhoaHoc;
    }

    public void setNhaKhoaHoc(NhaKhoaHoc nhaKhoaHoc) {
        this.nhaKhoaHoc = nhaKhoaHoc;
    }

    // ID class for composite key
    public static class NhanshttId implements Serializable {
        private int nkhId;
        private String shttId;

        public NhanshttId() {}

        public NhanshttId(int nkhId, String shttId) {
            this.nkhId = nkhId;
            this.shttId = shttId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NhanshttId)) return false;
            NhanshttId that = (NhanshttId) o;
            return nkhId == that.nkhId && Objects.equals(shttId, that.shttId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nkhId, shttId);
        }
    }
}