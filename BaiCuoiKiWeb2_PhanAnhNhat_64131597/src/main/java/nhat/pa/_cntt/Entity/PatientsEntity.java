package nhat.pa._cntt.Entity;

   import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Table(name = "patients")
public class PatientsEntity {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer patientid;

       @OneToOne
       @JoinColumn(name = "userid", unique = true)
       private UsersEntity user;

       @Column(nullable = false)
       private String fullname;

       private String email;

       private String phone;

       private LocalDate dateofbirth;

       @Enumerated(EnumType.STRING)
       private Gender gender;

       private String address;

       @Column(name = "createdat")
       private LocalDateTime createdAt;

       public enum Gender {
           nam, nữ, khác
       }

       // Getters and Setters
       public Integer getPatientid() {
           return patientid;
       }

       public void setPatientid(Integer patientid) {
           this.patientid = patientid;
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

       public String getEmail() {
           return email;
       }

       public void setEmail(String email) {
           this.email = email;
       }

       public String getPhone() {
           return phone;
       }

       public void setPhone(String phone) {
           this.phone = phone;
       }

       public LocalDate getDateofbirth() {
           return dateofbirth;
       }

       public void setDateofbirth(LocalDate dateofbirth) {
           this.dateofbirth = dateofbirth;
       }

       public Gender getGender() {
           return gender;
       }

       public void setGender(Gender gender) {
           this.gender = gender;
       }

       public String getAddress() {
           return address;
       }

       public void setAddress(String address) {
           this.address = address;
       }

       public LocalDateTime getCreatedAt() {
           return createdAt;
       }

       public void setCreatedAt(LocalDateTime createdAt) {
           this.createdAt = createdAt;
       }
   }