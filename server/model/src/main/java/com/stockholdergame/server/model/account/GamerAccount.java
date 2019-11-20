package com.stockholdergame.server.model.account;

import com.stockholdergame.server.model.Identifiable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.Locale;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

/**
 * @author Alexander Savin
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "a_gamer_accounts",
        uniqueConstraints = {@UniqueConstraint(columnNames = "user_name"),
                             @UniqueConstraint(columnNames = "email")})
public class GamerAccount implements Identifiable<Long> {

    private static final long serialVersionUID = -5757247885853730831L;

    @Id
    @Column(name = "gamer_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "status_id")
    @Enumerated
    private AccountStatus status;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "removal_date")
    private Date removalDate;

    @Type(type = "locale")
    @Column(name = "locale_id", nullable = false)
    private Locale locale;

    @Column(name = "subtopic_name", nullable = false)
    private String subtopicName;

    @Column(name = "is_bot", nullable = false)
    @Type(type = "boolean")
    private Boolean isBot;

    @OneToOne(mappedBy = "gamerAccount", cascade = ALL, fetch = LAZY)
    private Profile profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getRemovalDate() {
        return removalDate;
    }

    public void setRemovalDate(Date removalDate) {
        this.removalDate = removalDate;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getSubtopicName() {
        return subtopicName;
    }

    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    public Boolean getIsBot() {
        return isBot;
    }

    public void setIsBot(Boolean isBot) {
        this.isBot = isBot;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userName", userName)
                .append("email", email)
                .append("status", status)
                .append("registrationDate", registrationDate)
                .append("language", locale)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GamerAccount)) {
            return false;
        }
        GamerAccount ga = (GamerAccount) o;
        return new EqualsBuilder()
                .append(id, ga.id)
                .append(userName, ga.userName)
                .append(email, ga.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(userName)
                .append(email)
                .toHashCode();
    }
}
