package com.stockholdergame.server.model.account;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import com.stockholdergame.server.model.Identifiable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "a_profiles")
@GenericGenerator(name = "ProfileGenerator", strategy = "foreign",
        parameters = {@Parameter(name = "property", value = "gamerAccount")})
public class Profile implements Identifiable<Long> {

    private static final long serialVersionUID = -9088857818644198963L;

    @Id
    @Column(name = "gamer_id")
    @GeneratedValue(generator = "ProfileGenerator")
    private Long id;

    @Column(name = "sex")
    @Enumerated(STRING)
    private Sex sex;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "about")
    private String about;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "detected_country")
    private String detectedCountry;

    @Column(name = "detected_region")
    private String detectedRegion;

    @Column(name = "detected_city")
    private String detectedCity;

    @Column(name = "avatar")
    @Lob
    private byte[] avatar;

    @Column(name = "small_avatar")
    @Lob
    private byte[] smallAvatar;

    @OneToOne(fetch = LAZY)
    @PrimaryKeyJoinColumn
    private GamerAccount gamerAccount;

    public Profile() {
    }

    public Profile(GamerAccount gamerAccount) {
        this.gamerAccount = gamerAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = StringUtils.trimToNull(about);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = StringUtils.trimToNull(country);
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = StringUtils.trimToNull(region);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = StringUtils.trimToNull(city);
    }

    public String getDetectedCountry() {
        return detectedCountry;
    }

    public void setDetectedCountry(String detectedCountry) {
        this.detectedCountry = StringUtils.trimToNull(detectedCountry);
    }

    public String getDetectedRegion() {
        return detectedRegion;
    }

    public void setDetectedRegion(String detectedRegion) {
        this.detectedRegion = StringUtils.trimToNull(detectedRegion);
    }

    public String getDetectedCity() {
        return detectedCity;
    }

    public void setDetectedCity(String detectedCity) {
        this.detectedCity = StringUtils.trimToNull(detectedCity);
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public byte[] getSmallAvatar() {
        return smallAvatar;
    }

    public void setSmallAvatar(byte[] smallAvatar) {
        this.smallAvatar = smallAvatar;
    }

    public GamerAccount getGamerAccount() {
        return gamerAccount;
    }

    public void setGamerAccount(GamerAccount gamerAccount) {
        this.gamerAccount = gamerAccount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                //.append("id", id)
                .append("sex", sex)
                .append("birthday", birthday)
                .append("country", country)
                .append("region", region)
                .append("city", city)
                .toString();
    }

    /*@Override
    public boolean equals(Object o) {
        if (!(o instanceof Profile)) {
            return false;
        }
        Profile profile = (Profile) o;
        return new EqualsBuilder()
                //.append(id, profile.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                //.append(id)
                .toHashCode();
    } */
}
