package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "tacgiasach")
@IdClass(TacGiaSachEntity.TacGiaSachId.class)
public class TacGiaSachEntity {

	@Id
    @Column(name = "NKH_ID", nullable = false)
    private int nkhId;

    @Id
    @Column(name = "SACH_ID", length = 30, nullable = false)
    private String sachId;

    // Constructors
    public TacGiaSachEntity() {}

    public TacGiaSachEntity(int nkhId, String sachId) {
        this.nkhId = nkhId;
        this.sachId = sachId;
    }

    // Getters and Setters
    public int getNkhId() {
        return nkhId;
    }

    public void setNkhId(int nkhId) {
        this.nkhId = nkhId;
    }

    public String getSachId() {
        return sachId;
    }

    public void setSachId(String sachId) {
        this.sachId = sachId;
    }

    // Inner class for composite primary key
    public static class TacGiaSachId implements Serializable {
        private int nkhId;
        private String sachId;

        // Constructors, equals, hashCode...
        public TacGiaSachId() {}

        public TacGiaSachId(int nkhId, String sachId) {
            this.nkhId = nkhId;
            this.sachId = sachId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TacGiaSachId)) return false;
            TacGiaSachId that = (TacGiaSachId) o;
            return nkhId == that.nkhId && sachId.equals(that.sachId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nkhId, sachId);
        }
    }
}

