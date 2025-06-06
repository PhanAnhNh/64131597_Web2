package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "thamgiacongtrinh")
@IdClass(ThamGiaCTEntity.ThamgiacongtrinhId.class)
public class ThamGiaCTEntity {
	@Id
    @Column(name = "NKH_ID", nullable = false)
    private int nkhId;

    @Id
    @Column(name = "CONGTRINH_ID", length = 20, nullable = false)
    private String congtrinhId;

    public ThamGiaCTEntity() {}

    public ThamGiaCTEntity(int nkhId, String congtrinhId) {
        this.nkhId = nkhId;
        this.congtrinhId = congtrinhId;
    }

    public int getNkhId() {
        return nkhId;
    }

    public void setNkhId(int nkhId) {
        this.nkhId = nkhId;
    }

    public String getCongtrinhId() {
        return congtrinhId;
    }

    public void setCongtrinhId(String congtrinhId) {
        this.congtrinhId = congtrinhId;
    }

    public static class ThamgiacongtrinhId implements Serializable {
        private int nkhId;
        private String congtrinhId;

        public ThamgiacongtrinhId() {}

        public ThamgiacongtrinhId(int nkhId, String congtrinhId) {
            this.nkhId = nkhId;
            this.congtrinhId = congtrinhId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ThamgiacongtrinhId)) return false;
            ThamgiacongtrinhId that = (ThamgiacongtrinhId) o;
            return nkhId == that.nkhId && Objects.equals(congtrinhId, that.congtrinhId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nkhId, congtrinhId);
        }
    }
}
