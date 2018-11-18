package com.divanshu.reap.finalProject.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    @NotEmpty(message = "Please enter first name")
    private String firstname;

    @Column(name = "last_name", nullable = false)
    @NotEmpty(message = "Please enter last name")
    private String lastname;

    @Column(name = "user_email", nullable = false, unique = true)
    @NotEmpty(message = "Please enter email address")
    @Email
    private String email;

    @Column(name = "user_password", nullable = false)
    @NotEmpty(message = "Please enter password")
    @Size(min = 6, message = "Password should be of {min} length")
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "user_created")
    private Date dateCreated;

    @Column(name = "status")
    private String status;

    private String userRole;

    @Embedded
    private Badges badges;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "USER_ID")
            , inverseJoinColumns = @JoinColumn(name = "APPRECIATION_ID"))
    List<Appreciation> appreciationList = new ArrayList<>();
    @Column(name = "reset_token")
    private String resetToken;
    @NotNull
    private Integer totalPoints = 0;

    public User() {
    }

    public User(@NotEmpty(message = "Please enter first name") String firstname, @NotEmpty(message = "Please enter last name") String lastname, @NotEmpty(message = "Please enter email address") @Email String email, @NotEmpty(message = "Please enter password") @Size(min = 6, message = "Password should be of {min} length") String password, Date dateCreated, String status, String userRole, Badges badges, String resetToken, List<Appreciation> appreciationList, @NotNull Integer totalPoints) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
        this.status = status;
        this.userRole = userRole;
        this.badges = badges;
        this.resetToken = resetToken;
        this.appreciationList = appreciationList;
        this.totalPoints = totalPoints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Badges getBadges() {
        return badges;
    }

    public void setBadges(Badges badges) {
        this.badges = badges;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public List<Appreciation> getAppreciationList() {
        return appreciationList;
    }

    public void setAppreciationList(List<Appreciation> appreciationList) {
        this.appreciationList = appreciationList;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateCreated=" + dateCreated +
                ", status='" + status + '\'' +
                ", userRole='" + userRole + '\'' +
                ", badges=" + badges +
                ", resetToken='" + resetToken + '\'' +
                ", appreciationList=" + appreciationList +
                ", totalPoints=" + totalPoints +
                '}';
    }
}
