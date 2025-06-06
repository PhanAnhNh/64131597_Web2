package nhat.pa._cntt.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentid;

    @ManyToOne
    @JoinColumn(name = "patientid", nullable = false)
    private PatientsEntity patient;

    @ManyToOne
    @JoinColumn(name = "doctorid", nullable = false)
    private DoctorsEntity doctor;

    @Column(nullable = false)
    private LocalDate appointmentdate;

    @Column(nullable = false)
    private LocalTime appointmenttime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String reason;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    public enum Status {
        Đã_xác_nhận, Chưa_xác_nhận
    }

    // Getters and Setters
    public Integer getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(Integer appointmentid) {
        this.appointmentid = appointmentid;
    }

    public PatientsEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientsEntity patient) {
        this.patient = patient;
    }

    public DoctorsEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorsEntity doctor) {
        this.doctor = doctor;
    }

    public LocalDate getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(LocalDate appointmentdate) {
        this.appointmentdate = appointmentdate;
    }

    public LocalTime getAppointmenttime() {
        return appointmenttime;
    }

    public void setAppointmenttime(LocalTime appointmenttime) {
        this.appointmenttime = appointmenttime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
