package Nhom2.DoAnWeb.NTUScientistInfor.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "nkh")
public class NhaKhoaHoc {
	@Id
    @Column(name = "NKH_ID")
	
    private Integer id;

    @Column(name = "TENTAIKHOAN", nullable = false, length = 30)
    
    private String tenTaiKhoan;

    @Column(name = "HOTEN", nullable = false, length = 70)
    private String hoTen;

    @Column(name = "GIOITINH", nullable = false)
    private String gioiTinh;

    @Column(name = "NAMSINH", nullable = false)
    private Integer namSinh;

    @Column(name = "EMAIL", nullable = false, length = 60)
    private String email;

    @Column(name = "HOCVI", length = 50)
    private String hocVi;

    @Column(name = "NGANHDAOTAO", length = 100)
    private String nganhDaoTao;

    @Column(name = "LINHVUCNGHIENCUU", length = 200)
    private String linhVucNghienCuu;

    @Column(name = "CHUYENMON", length = 200)
    private String chuyenMon;

    @Column(name = "HINHANH", length = 100)
    private String hinhAnh;

    @ManyToOne
    @JoinColumn(name = "TENTAIKHOAN", referencedColumnName = "TENTAIKHOAN", insertable = false, updatable = false)
    private TaiKhoanEntity taiKhoan;

    @ManyToMany
    @JoinTable(
        name = "nghiencuu", // Updated to match database table
        joinColumns = @JoinColumn(name = "NKH_ID"),
        inverseJoinColumns = @JoinColumn(name = "DETAI_ID")
    )
    @JsonManagedReference
    private List<DetaiKHEntitty> nkh_nghiencuu = new ArrayList<>();
    
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "thamgiacongtrinh",
        joinColumns = @JoinColumn(name = "NKH_ID"),
        inverseJoinColumns = @JoinColumn(name = "CONGTRINH_ID")
    )
    @JsonManagedReference
    
    private List<CongtrinhEntity> nkh_thamgiacongtrinh = new ArrayList<>();
    // Getter và Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Integer getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Integer namSinh) {
        this.namSinh = namSinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHocVi() {
        return hocVi;
    }

    public void setHocVi(String hocVi) {
        this.hocVi = hocVi;
    }

    public String getNganhDaoTao() {
        return nganhDaoTao;
    }

    public void setNganhDaoTao(String nganhDaoTao) {
        this.nganhDaoTao = nganhDaoTao;
    }

    public String getLinhVucNghienCuu() {
        return linhVucNghienCuu;
    }

    public void setLinhVucNghienCuu(String linhVucNghienCuu) {
        this.linhVucNghienCuu = linhVucNghienCuu;
    }

    public String getChuyenMon() {
        return chuyenMon;
    }

    public void setChuyenMon(String chuyenMon) {
        this.chuyenMon = chuyenMon;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public TaiKhoanEntity getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoanEntity taiKhoan) {
        this.taiKhoan = taiKhoan;
        if (taiKhoan != null) {
            this.tenTaiKhoan = taiKhoan.getTenTaiKhoan();
        }
    }

    public List<DetaiKHEntitty> getNkh_nghiencuu() {
        return nkh_nghiencuu;
    }

    public void setNkh_nghiencuu(List<DetaiKHEntitty> nkh_nghiencuu) {
        this.nkh_nghiencuu = nkh_nghiencuu;
    }
    
    public List<CongtrinhEntity> getNkh_thamgiacongtrinh() {
        return nkh_thamgiacongtrinh;
    }

    public void setNkh_thamgiacongtrinh(List<CongtrinhEntity> nkh_thamgiacongtrinh) {
        this.nkh_thamgiacongtrinh = nkh_thamgiacongtrinh;
    }

}
