package me.daylight.ktzs.entity;

public class User {
    private Long id;

    private String idNumber;

    private String name;

    private String phone;

    private String role;

    private Boolean isLeave;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean isLeave() {
        return isLeave;
    }

    public void setLeave(Boolean isLeave) {
        this.isLeave = isLeave;
    }
}
