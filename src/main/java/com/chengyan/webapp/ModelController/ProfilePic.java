package com.chengyan.webapp.ModelController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "`Profile_pics`")
public class ProfilePic {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonProperty("file_name")
    @Column(name = "file_name", nullable = false, updatable = false)
//    @Pattern(regexp = "^[a-zA-Z0-9\\-_+\\\\s]+\\.(jpg|jpeg|png)$")
//    @Pattern(regexp = "\\.(jpg|jpeg|png)$")
    private String filename;

    @Column(name = "url", nullable = false, updatable = false)
    private String url;

    @JsonProperty("upload_date")
    @UpdateTimestamp
    @Column(name = "upload_date", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime uploadTime;

    @JsonProperty("user_id")
    @Column(name = "user_id", unique = true, updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    public ProfilePic() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProfilePic{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", uploadTime=" + uploadTime +
                ", userId=" + userId +
                '}';
    }
}
