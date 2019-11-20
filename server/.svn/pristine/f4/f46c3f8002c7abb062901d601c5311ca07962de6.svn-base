package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.InvitationDao;
import com.stockholdergame.server.model.game.Invitation;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.model.game.Invitation_;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 18.2.11 22.25
 */
@Repository
public class InvitationDaoImpl extends BaseDao<Invitation, Long> implements InvitationDao {

    public Invitation findCreatedInvitationByGameIdAndInviteeName(Long gameId, String userName) {
        return findSingleObject("Invitation.findCreatedInvitationByGameIdAndInviteeName", gameId, userName);
    }

    @SuppressWarnings("unchecked")
    public Long[] countUserInvitations(Long userId) {
        List<Long> countMyInvits = (List<Long>) findByNamedQuery("Invitation.countMyInvitations", userId);
        List<Long> countInvitsForMe = (List<Long>) findByNamedQuery("Invitation.countInvitationsForMe", userId);
        return new Long[]{
                countMyInvits != null && !countMyInvits.isEmpty() ? countMyInvits.get(0) : 0,
                countInvitsForMe != null && !countInvitsForMe.isEmpty() ? countInvitsForMe.get(0) : 0
        };
    }

    @SuppressWarnings("unchecked")
    public List<Invitation> findByParameters(final Long inviterId,
                                             final Long inviteeId,
                                             final InvitationStatus... statuses) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Invitation> criteria = cb.createQuery(Invitation.class);
        Root<Invitation> from = criteria.from(Invitation.class);
        criteria.select(from);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (inviterId != null) {
            predicates.add(cb.equal(from.get(Invitation_.inviterId), inviterId));
        }
        if (inviteeId != null) {
            predicates.add(cb.equal(from.get(Invitation_.inviteeId), inviteeId));
        }
        if (statuses != null && statuses.length > 0) {
            CriteriaBuilder.In in = cb.in(from.get(Invitation_.status));
            for (InvitationStatus status : statuses) {
                in.value(status);
            }
            predicates.add(in);
        }
        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        criteria.orderBy(cb.asc(from.get(Invitation_.createdTime)));
        Query query = em.createQuery(criteria);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public int countInvitationsByGameIdAndStatus(Long gameId, InvitationStatus status) {
        List<Long> counts = (List<Long>) findByNamedQuery("Invitation.countInvitationsByGameId", gameId, status);
        return counts != null && counts.size() > 0 ? counts.get(0).intValue() : 0;
    }

    public List<Invitation> findByGameId(Long gameId) {
        return findList("Invitation.findByGameId", gameId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Invitation> findExpired(Date date, int limit) {
        Query query = em.createNamedQuery("Invitation.findExpired");
        query.setParameter(1, date);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Invitation> findByInviterId(Long gamerId) {
        return findList("Invitation.findByInviterId", gamerId);
    }
}
