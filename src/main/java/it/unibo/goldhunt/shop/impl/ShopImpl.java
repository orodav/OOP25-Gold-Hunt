package it.unibo.goldhunt.shop.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopActionEffect;
import it.unibo.goldhunt.shop.api.ShopActionResult;
import it.unibo.goldhunt.shop.api.ShopActionType;
import it.unibo.goldhunt.shop.api.ShopItem;
import it.unibo.goldhunt.shop.api.ShopStopReason;

/**
 * Immutable shop implementation.
 */
public final class ShopImpl implements Shop {

    private final PlayerOperations player;
    private final int maxPurchases;
    private final int purchasesDone;
    private final Set<ItemTypes> boughtThisSession;
    private final Map<ItemTypes, ShopItem> catalog;
    private final List<ShopItem> itemView;

    /**
     * Builds a shop with a fixed catalog and a maximum number of purchases.
     */
    public ShopImpl(
        final PlayerOperations player,
        final List<ShopItem> catalog,
        final int maxPurchases
    ) {
        if (player == null) {
            throw new IllegalArgumentException("player can't be null");
        }
        if (catalog == null) {
            throw new IllegalArgumentException("catalog can't be null");
        }
        if (maxPurchases < 0) {
            throw new IllegalArgumentException("maxPurchases must be >= 0");
        }
        

        this.player = player;
        this.maxPurchases = maxPurchases;
        this.purchasesDone = 0;
        this.boughtThisSession = Set.of();
        final List<ShopItem> list = new ArrayList<>(catalog.size());
        final Map<ItemTypes, ShopItem> map = new HashMap<>();
        for (final ShopItem item : catalog) {
            if (item == null) {
                throw new IllegalArgumentException("catalog contains null item");
            }
            if (item.type() == null) {
                throw new IllegalArgumentException("item type can't be null");
            }
            if (item.price() <= 0) {
                throw new IllegalArgumentException("price must be > 0");
            }
            if (map.containsKey(item.type())) {
                throw new IllegalArgumentException("duplicate item in catalog: " + item.type());
            }
            map.put(item.type(), item);
            list.add(item);
        }
        this.catalog = Map.copyOf(map);
        this.itemView = List.copyOf(list);
    }

    private ShopImpl(
        final PlayerOperations player,
        final Map<ItemTypes, ShopItem> catalog,
        final List<ShopItem> itemView,
        final int maxPurchases,
        final int purchasesDone,
        final Set<ItemTypes> boughtThisSession
    ) {
        this.player = player;
        this.catalog = catalog;
        this.itemView = itemView;
        this.maxPurchases = maxPurchases;
        this.purchasesDone = purchasesDone;
        this.boughtThisSession = boughtThisSession;
    }

    @Override
    public List<ShopItem> items() {
        return this.itemView;
    }

    @Override
    public int remainingPurchases() {
        final int result = this.maxPurchases - this.purchasesDone;
        return result < 0 ? 0 : result;
    }

    @Override
    public ShopActionResult buy(final ItemTypes type) {
        if (type == null) {
            throw new IllegalArgumentException("type can't be null");
        }

        final ShopItem shopItem = this.catalog.get(type);
        if (shopItem == null) {
            return blocked(ShopStopReason.ITEM_NOT_SOLD);
        }
        if (this.purchasesDone >= this.maxPurchases) {
            return blocked(ShopStopReason.LIMIT_REACHED);
        }
        if (this.boughtThisSession.contains(type)) {
            return blocked(ShopStopReason.ALREADY_BOUGHT);
        }
        if (this.player.goldCount() < shopItem.price()) {
            return blocked(ShopStopReason.NOT_ENOUGH_GOLD);
        }
        final Inventory updatedInv = this.player.inventory().add(type, 1);
        final PlayerOperations updatedPlayer = this.player
            .addGold(-shopItem.price())
            .withInventory(updatedInv);

        final Set<ItemTypes> updatedBought = new HashSet<>(this.boughtThisSession);
        updatedBought.add(type);

        final Shop updatedShop = new ShopImpl(
            updatedPlayer,
            this.catalog,
            this.itemView,
            this.maxPurchases,
            this.purchasesDone + 1,
            Set.copyOf(updatedBought)
        );

        return new ShopActionResult(
            ShopActionType.BUY,
            ShopStopReason.NONE,
            ShopActionEffect.APPLIED,
            updatedShop.remainingPurchases(),
            updatedPlayer,
            updatedShop
        );
    }

    private ShopActionResult blocked(final ShopStopReason reason) {
        final Shop sameShop = new ShopImpl(
            this.player,
            this.catalog,
            this.itemView,
            this.maxPurchases,
            this.purchasesDone,
            this.boughtThisSession
        );

        return new ShopActionResult(
            ShopActionType.BUY,
            reason,
            ShopActionEffect.BLOCKED,
            sameShop.remainingPurchases(),
            this.player,
            sameShop
        );
    }
}
