package com.stockholdergame.server.services.game.impl;

import static java.text.MessageFormat.format;
import com.stockholdergame.server.dao.GameVariantDao;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.model.game.variant.CardOperation;
import com.stockholdergame.server.model.game.variant.CardQuantity;
import com.stockholdergame.server.model.game.variant.GameCardGroup;
import com.stockholdergame.server.model.game.variant.GameShare;
import com.stockholdergame.server.model.game.variant.GameVariant;
import com.stockholdergame.server.model.game.variant.GameVariantInfo;
import com.stockholdergame.server.model.game.variant.PriceOperation;
import com.stockholdergame.server.services.game.GameVariantService;
import com.stockholdergame.server.util.registry.Registry;
import com.stockholdergame.server.util.registry.impl.RegistryImpl;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 20.11.2010 20.48.24
 */
@Service
public class GameVariantServiceImpl implements GameVariantService {

    private static final String CARD_NOT_FOUND_MESSAGE =
            "Card not found for this game variant. cardId = {0}, gameVariantId = {1}";

    private static final String OPERATION_NOT_FOUND_MESSAGE =
            "Card does not contain operation. cardId = {0}, priceOperationId = {1}";

    @Autowired
    private GameVariantDao gameVariantDao;

    private Registry<Long, GameVariantInfo> registry = new RegistryImpl<>(3);

    public void setGameVariantDao(GameVariantDao gameVariantDao) {
        this.gameVariantDao = gameVariantDao;
    }

    public List<GameVariant> getGameVariants() {
        return gameVariantDao.findAllActiveVariants();
    }

    public GameVariantInfo getGameVariantInfo(Long gameVariantId) {
        Validate.notNull(gameVariantId);

        if (!registry.contains(gameVariantId)) {
            putInRegistry(findGameVariant(gameVariantId));
        }
        return registry.get(gameVariantId);
    }

    public GameVariantDto getGameVariantById(Long gameVariantId) {
        GameVariant gameVariant = findGameVariant(gameVariantId);
        return DtoMapper.map(gameVariant, GameVariantDto.class);
    }

    GameVariant findGameVariant(Long gameVariantId) {
        GameVariant gameVariant = gameVariantDao.findByPrimaryKey(gameVariantId);
        if (gameVariant == null) {
            throw new BusinessException(BusinessExceptionType.GAME_VARIANT_NOT_FOUND, gameVariantId);
        }
        return gameVariant;
    }

    private void putInRegistry(GameVariant gameVariant) {
        GameVariantInfoImpl info = new GameVariantInfoImpl();
        info.gameVariant = gameVariant;
        info.shareIds = createShareIdsSet(gameVariant.getShares());
        createCardMap(info, gameVariant.getCardGroups());
        registry.put(gameVariant.getId(), info);
    }

    private Set<Long> createShareIdsSet(Set<GameShare> shares) {
        Set<Long> shareIds = new HashSet<>();
        for (GameShare share : shares) {
            shareIds.add(share.getId().getShareId());
        }
        return shareIds;
    }

    private void createCardMap(GameVariantInfoImpl info, Set<GameCardGroup> cardGroups) {
        Map<Long, Map<Long, CardOperation>> cardOperationsMap = new HashMap<>();
        Map<Long, Set<Long>> cardGroupsMap = new HashMap<>();
        Map<Long, Integer> cardQuantityMap = new HashMap<>();
        for (GameCardGroup cardGroup : cardGroups) {
            Set<Long> cardIds = new HashSet<>();
            cardQuantityMap.put(cardGroup.getId().getCardGroupId(), cardGroup.getGamerCardQuantity());
            cardGroupsMap.put(cardGroup.getId().getCardGroupId(), cardIds);
            for (CardQuantity cardQuantity : cardGroup.getCardGroup().getCardQuantities()) {
                cardIds.add(cardQuantity.getCard().getId());
                cardOperationsMap.put(cardQuantity.getCard().getId(),
                        createOperationsMap(cardQuantity.getCard().getCardOperations()));
            }
        }
        info.cardQuantityMap = cardQuantityMap;
        info.cardGroupsMap = cardGroupsMap;
        info.cardOperationsMap = cardOperationsMap;
    }

    private Map<Long, CardOperation> createOperationsMap(Set<CardOperation> cardOperations) {
        Map<Long, CardOperation> operationMap = new HashMap<>();
        for (CardOperation cardOperation : cardOperations) {
            operationMap.put(cardOperation.getPriceOperation().getId(), cardOperation);
        }
        return operationMap;
    }

    private class GameVariantInfoImpl implements GameVariantInfo {

        GameVariant gameVariant;

        Map<Long, Integer> cardQuantityMap;

        Map<Long, Set<Long>> cardGroupsMap;

        Set<Long> shareIds;

        Map<Long, Map<Long, CardOperation>> cardOperationsMap;

        public Long getGameVariantId() {
            return gameVariant.getId();
        }

        public int getMovesQuantity() {
            return gameVariant.getMovesQuantity();
        }

        public boolean hasShare(Long shareId) {
            return shareIds.contains(shareId);
        }

        public boolean hasCard(Long cardId) {
            return cardOperationsMap.containsKey(cardId);
        }

        public boolean hasCardOperation(Long cardId, Long priceOperationId) {
            return cardOperationsMap.containsKey(cardId) && cardOperationsMap.get(cardId).containsKey(priceOperationId);
        }

        public Set<Long> getShareIds() {
            return Collections.unmodifiableSet(shareIds);
        }

        public Set<Long> getCardGroupIds() {
            return Collections.unmodifiableSet(cardGroupsMap.keySet());
        }

        public Set<Long> getCardIdsInGroup(Long cardGroupId) {
            return Collections.unmodifiableSet(cardGroupsMap.get(cardGroupId));
        }

        public Integer getGamerCardQuantity(Long cardGroupId) {
            return cardQuantityMap.get(cardGroupId);
        }

        public Set<Long> getPriceOperationIds(Long cardId) {
            checkCardExistence(cardId);
            return cardOperationsMap.get(cardId).keySet();
        }

        public Long getShareIdForPriceOperation(Long cardId, Long priceOperationId) {
            checkCardExistence(cardId);
            checkPriceOperationExistence(cardId, priceOperationId);
            return cardOperationsMap.get(cardId).get(priceOperationId).getShareId();
        }

        public PriceOperation getPriceOperation(Long cardId, Long priceOperationId) {
            checkCardExistence(cardId);
            checkPriceOperationExistence(cardId, priceOperationId);
            return cardOperationsMap.get(cardId).get(priceOperationId).getPriceOperation();
        }

        private void checkCardExistence(Long cardId) {
            if (!cardOperationsMap.containsKey(cardId)) {
                throw new ApplicationException(format(CARD_NOT_FOUND_MESSAGE, cardId, gameVariant.getId()));
            }
        }

        private void checkPriceOperationExistence(Long cardId, Long priceOperationId) {
            if (!cardOperationsMap.get(cardId).containsKey(priceOperationId)) {
                throw new ApplicationException(format(OPERATION_NOT_FOUND_MESSAGE, cardId, priceOperationId));
            }
        }
    }
}
