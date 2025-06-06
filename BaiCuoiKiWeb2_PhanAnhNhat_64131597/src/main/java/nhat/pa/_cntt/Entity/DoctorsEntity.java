package nhat.pa._cntt.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class DoctorsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorid;

    @OneToOne
    @JoinColumn(name = "userid", nullable = true, unique = true)
    private UsersEntity user;

    @Column(nullable = false)
    private String fullname;
    
    @Column
    private String profileImage;

    private String department;

    private String phone;

    private String qualifications;

    private String workinghours;
    
    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column
    private String email;

    @Column
    private String gender;

    public enum Status {
        hoạt_động, không_hoạt_động
    }

    // Getters and Setters
    public Integer getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(Integer doctorid) {
        this.doctorid = doctorid;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getWorkinghours() {
        return workinghours;
    }

    public void setWorkinghours(String workinghours) {
        this.workinghours = workinghours;
    }
    
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }   
}