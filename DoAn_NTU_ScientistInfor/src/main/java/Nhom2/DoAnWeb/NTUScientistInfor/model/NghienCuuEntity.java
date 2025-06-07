package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "nghiencuu")
@IdClass(NghienCuuEntity.NghienCuuId.class)
public class NghienCuuEntity {
	@Id
    @Column(name = "NKH_ID", nullable = false)
    private int nkhId;

    @Id
    @Column(name = "DETAI_ID", length = 50, nullable = false)
    private String detaiId;

    // Constructors
    public NghienCuuEntity() {}

    public NghienCuuEntity(int nkhId, String detaiId) {
        this.nkhId = nkhId;
        this.detaiId = detaiId;
    }

    // Getters and Setters
    public int getNkhId() {
        return nkhId;
    }

    public void setNkhId(int nkhId) {
        this.nkhId = nkhId;
    }

    public String getDetaiId() {
        return detaiId;
    }

    public void setDetaiId(String detaiId) {
        this.detaiId = detaiId;
    }
 
    public static class NghienCuuId implements Serializable {
        private int nkhId;
        private String detaiId;

        public NghienCuuId() {}

        public NghienCuuId(int nkhId, String detaiId) {
            this.nkhId = nkhId;
            this.detaiId = detaiId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NghienCuuId)) return false;
            NghienCuuId that = (NghienCuuId) o;
            return nkhId == that.nkhId && Objects.equals(detaiId, that.detaiId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nkhId, detaiId);
        }
    }
}

