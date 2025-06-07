package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "congtrinh")
public class CongtrinhEntity {
	@Id
	@Column(name="CONGTRINH_ID")
	private String congTrinhID;
	
	@Column(name="TENCONGTRINH")
	private String tenCongTrinh;
	
	@Column(name ="HINHTHUC")
	private String hinhThuc;
	
	@Column(name="QUYMO")
	private String quyMo;
	
	@Column(name="NOIAPDUNG")
	private String noiApDung;
	
	@Column(name="NAMBATDAU")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate namBatDau;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="NAMKETTHUC")
	private LocalDate namKetThuc;
	
	@ManyToMany(mappedBy = "nkh_thamgiacongtrinh")
    @JsonBackReference
    private List<NhaKhoaHoc> thamGia = new ArrayList<>();
	
	public CongtrinhEntity() {};
	
	public String getCongTrinhID() {
	    return congTrinhID;
	}

	public void setCongTrinhID(String congTrinhID) {
	    this.congTrinhID = congTrinhID;
	}

	public String getTenCongTrinh() {
	    return tenCongTrinh;
	}

	public void setTenCongTrinh(String tenCongTrinh) {
	    this.tenCongTrinh = tenCongTrinh;
	}

	public String getHinhThuc() {
	    return hinhThuc;
	}

	public void setHinhThuc(String hinhThuc) {
	    this.hinhThuc = hinhThuc;
	}

	public String getQuyMo() {
	    return quyMo;
	}

	public void setQuyMo(String quyMo) {
	    this.quyMo = quyMo;
	}

	public String getNoiApDung() {
	    return noiApDung;
	}

	public void setNoiApDung(String noiApDung) {
	    this.noiApDung = noiApDung;
	}

	public LocalDate getNamBatDau() {
	    return namBatDau;
	}

	public void setNamBatDau(LocalDate namBatDau) {
	    this.namBatDau = namBatDau;
	}

	public LocalDate getNamKetThuc() {
	    return namKetThuc;
	}

	public void setNamKetThuc(LocalDate namKetThuc) {
	    this.namKetThuc = namKetThuc;
	}

	public List<NhaKhoaHoc> getThamGia() {
	    return thamGia;
	}

	public void setThamGia(List<NhaKhoaHoc> thamGia) {
	    this.thamGia = thamGia;
	}

}

@Converter(autoApply = true)
class LocalDateAttributeConverter implements AttributeConverter<LocalDate, java.sql.Date> {
    @Override
    public java.sql.Date convertToDatabaseColumn(LocalDate attribute) {
        return (attribute == null ? null : java.sql.Date.valueOf(attribute));
    }

    @Override
    public LocalDate convertToEntityAttribute(java.sql.Date dbData) {
        return (dbData == null ? null : dbData.toLocalDate());
    }
}
