package com.divanshu.reap.finalProject.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

    @OneToOne
    @JoinColumn(name = "RemainingBadges")
    private GivenBadges givingBadges;// badgesAvailabe

    @OneToOne
    @JoinColumn(name = "ReceivedBadges")
    private ReceivedBadges receivedBadges; //badgesReceived

    @Embedded
    private Badges badges;

    public User(@NotEmpty(message = "Please enter first name") String firstname, @NotEmpty(message = "Please enter last name") String lastname, @NotEmpty(message = "Please enter email address") @Email String email, @NotEmpty(message = "Please enter password") @Size(min = 6, message = "Password should be of {min} length") String password, Date dateCreated, String status, String userRole, GivenBadges givingBadges, ReceivedBadges receivedBadges, String resetToken) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
        this.status = status;
        this.userRole = userRole;
        this.givingBadges = givingBadges;
        this.receivedBadges = receivedBadges;
        this.resetToken = resetToken;
    }

    public Badges getBadges() {
        return badges;
    }

    @Column(name = "reset_token")
    private String resetToken;

    public User() {
    }

    public void setBadges(Badges badges) {
        this.badges = badges;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public GivenBadges getGivingBadges() {
        return givingBadges;
    }

    public void setGivingBadges(GivenBadges givingBadges) {
        this.givingBadges = givingBadges;
    }

    public ReceivedBadges getReceivedBadges() {
        return receivedBadges;
    }

    public void setReceivedBadges(ReceivedBadges receivedBadges) {
        this.receivedBadges = receivedBadges;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
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
                ", givingBadges=" + givingBadges +
                ", receivedBadges=" + receivedBadges +
                ", resetToken='" + resetToken + '\'' +
                '}';
    }
}
