package com.chengyan.webapp.ModelController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "`Users`")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonProperty("first_name")
    @Column(name = "first_name", nullable = false)
    @Size(min = 2)
    private String firstName;

    @JsonProperty("last_name")
    @Column(name = "last_name", nullable = false)
    @Size(min = 2, message = "len")
    private String lastName;

    @Column(name = "username", updatable = false, nullable = false)
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String username; // email check

    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    // ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{6,20}$") // at least 1 lowercase, 1 digit. 20 >= len >= 6
    private String passwordBeforeEncoded;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @CreationTimestamp
    @Column(name = "account_created", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime account_created;

    //YYYY-MM-DDTHH:mm:ss.sssZ
    @UpdateTimestamp
    @Column(name = "account_updated", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime account_updated;

    @Column(name = "verified", nullable = false)
    private int verified = 0;

    @Column(name = "verified_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime verifiedAt = null;

    public User() {
    }

    public User(String firstName, String lastName, String username, String passwordBeforeEncoded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordBeforeEncoded = passwordBeforeEncoded;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String obtainPasswordBeforeEncoded() {
        return passwordBeforeEncoded;
    }

    public void setPasswordBeforeEncoded(String passwordBeforeEncoded) {
        this.passwordBeforeEncoded = passwordBeforeEncoded;
    }

    public String obtainPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getAccount_created() {
        return account_created;
    }

    public void setAccount_created(LocalDateTime account_created) {
        this.account_created = account_created;
    }

    public LocalDateTime getAccount_updated() {
        return account_updated;
    }

    public void setAccount_updated(LocalDateTime account_updated) {
        this.account_updated = account_updated;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verified_at) {
        this.verifiedAt = verified_at;
    }

    public boolean isVerified() {
        return 1 == getVerified();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", passwordBeforeEncoded='" + passwordBeforeEncoded + '\'' +
                ", password='" + password + '\'' +
                ", account_created=" + account_created +
                ", account_updated=" + account_updated +
                '}';
    }
}
