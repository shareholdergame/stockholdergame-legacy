package com.stockholdergame.server.model.game;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Alexander Savin
 *         Date: 20.5.12 22.58
 */
@Entity
@Table(name = "m_game_events")
public class GameEvent implements Serializable {

    private static final long serialVersionUID = -509247421220421734L;

    @Id
    @Column(name = "game_event_id")
    private Long id;

    @Column(name = "gamer_id")
    private Long gamerId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "created")
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
