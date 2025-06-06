package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.io.Serializable;
import java.util.Objects;

public class SangTacId implements Serializable{
	private Integer nkhId;
    private String baiBaoId;

    public SangTacId() {}

    public SangTacId(Integer nkhId, String baiBaoId) {
        this.nkhId = nkhId;
        this.baiBaoId = baiBaoId;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SangTacId)) return false;
        SangTacId that = (SangTacId) o;
        return Objects.equals(nkhId, that.nkhId) && Objects.equals(baiBaoId, that.baiBaoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nkhId, baiBaoId);
    }
}
