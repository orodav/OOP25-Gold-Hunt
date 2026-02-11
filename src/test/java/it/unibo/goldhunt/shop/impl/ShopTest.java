package it.unibo.goldhunt.shop.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopActionEffect;
import it.unibo.goldhunt.shop.api.ShopActionResult;
import it.unibo.goldhunt.shop.api.ShopActionType;
import it.unibo.goldhunt.shop.api.ShopItem;
import it.unibo.goldhunt.shop.api.ShopStopReason;

/**
 * Testing class for Shop implementations.
 */
class ShopTest {

    private static final int TWO_NEGATIVE = -2;
    private static final int SEVEN_POSITIVE = 7;
    private static final int FIVE_POSITIVE = 5;
    private static final int NINETYSEVEN_POSITIVE = 97;

    private Position pos(final int x, final int y) {
        return new Position(x, y);
    }

    private Inventory emptyInventory() {
        return new InventoryImpl();
    }

    private PlayerOperations playerWithGold(final int gold) {
        return new PlayerImpl(pos(0, 0), 3, gold, emptyInventory());
    }

    private enum StubItem implements ItemTypes {
        SHIELD,
        PICKAXE;

        @Override
        public PlayerOperations applyEffect(final PlayerOperations player) {
            if (player == null) {
                throw new IllegalArgumentException("player");
            }
            return player;
        }

        @Override
        public String shortString() {
            return name().substring(0, 2).toLowerCase(Locale.ROOT);
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }

        @Override
        public KindOfItem getItem() {
            return KindOfItem.SHIELD;
        }
    }

    private ShopItem item(final ItemTypes t, final int price) {
        return new ShopItem(t, price);
    }

    private Shop shop(final PlayerOperations p, final int maxPurchases, final ShopItem... items) {
        return new ShopImpl(p, List.of(items), maxPurchases);
    }

    // test

    @Test
    void constructorShouldThrowIfPlayerNull() {
        assertThrows(IllegalArgumentException.class,
            () -> new ShopImpl(null, List.of(item(StubItem.SHIELD, 3)), 1)
        );
    }

    @Test
    void constructorShouldThrowIfCatalogNull() {
        assertThrows(IllegalArgumentException.class,
            () -> new ShopImpl(playerWithGold(10), null, 1)
        );
    }

    @Test
    void constructorShouldThrowIfMaxPurchasesNegative() {
        assertThrows(IllegalArgumentException.class,
            () -> new ShopImpl(playerWithGold(10), List.of(item(StubItem.SHIELD, 3)), -1)
        );
    }

    @Test
    void constructorShouldThrowIfCatalogContainsNull() {
        final List<ShopItem> catalog = new ArrayList<>();
        catalog.add(null);
        assertThrows(IllegalArgumentException.class, 
            () -> new ShopImpl(playerWithGold(10), catalog, 1)
        );
    }

    @Test
    void constructorShouldThrowIfCatalogContainsNullType() {
        assertThrows(IllegalArgumentException.class,
            () -> new ShopImpl(
                playerWithGold(10),
                List.of(new ShopItem(null, 3)), 1)
        );
    }

    @Test
    void constructorShouldThrowIfCatalogContainsNonPositivePrice() {
        assertThrows(IllegalArgumentException.class,
            () -> new ShopImpl(
                playerWithGold(10),
                List.of(item(StubItem.SHIELD, 0)), 1)
        );
        assertThrows(IllegalArgumentException.class,
            () -> new ShopImpl(
                playerWithGold(10), 
                List.of(item(StubItem.SHIELD, TWO_NEGATIVE)), 1)
        );
    }

    @Test
    void costructorShouldThrowIfCatalogContainsDuplicateType() {
        assertThrows(IllegalArgumentException.class, 
            () -> new ShopImpl(
                playerWithGold(10), 
                List.of(item(StubItem.SHIELD, 3), item(StubItem.SHIELD, FIVE_POSITIVE)), 
                2
            )
        );
    }

    // buy()

    @Test
    void buyShouldThrowIfTypeNull() {
        final Shop s = shop(playerWithGold(10), 1, item(StubItem.SHIELD, 3));
        assertThrows(
            IllegalArgumentException.class,
            () -> s.buy(null));
    }

    @Test
    void buyShouldBlockIfItemNotSold() {
        final Shop s = shop(playerWithGold(10), 1, item(StubItem.SHIELD, 3));

        final ShopActionResult r = s.buy(StubItem.PICKAXE);

        assertEquals(ShopActionType.BUY, r.type());
        assertEquals(ShopStopReason.ITEM_NOT_SOLD, r.reason());
        assertEquals(ShopActionEffect.BLOCKED, r.effect());
        assertEquals(1, r.remainingPurchases());
        assertEquals(10, r.player().goldCount());
        assertEquals(0, r.player().inventory().quantity(StubItem.SHIELD));
        assertEquals(0, r.player().inventory().quantity(StubItem.PICKAXE));
    }

    @Test
    void buyShouldBlockIfNotEnoughGold() {
        final Shop s = shop(playerWithGold(2), 1, item(StubItem.SHIELD, 3));

        final ShopActionResult r = s.buy(StubItem.SHIELD);

        assertEquals(ShopActionType.BUY, r.type());
        assertEquals(ShopStopReason.NOT_ENOUGH_GOLD, r.reason());
        assertEquals(ShopActionEffect.BLOCKED, r.effect());
        assertEquals(1, r.remainingPurchases());
        assertEquals(2, r.player().goldCount());
        assertEquals(0, r.player().inventory().quantity(StubItem.SHIELD));
    }

    @Test
    void buyShouldApplyWhenEnoughGold() {
        final Shop s = shop(playerWithGold(10), 2, item(StubItem.SHIELD, 3));

        final ShopActionResult r = s.buy(StubItem.SHIELD);

        assertEquals(ShopActionType.BUY, r.type());
        assertEquals(ShopStopReason.NONE, r.reason());
        assertEquals(ShopActionEffect.APPLIED, r.effect());
        assertEquals(1, r.remainingPurchases());
        assertEquals(SEVEN_POSITIVE, r.player().goldCount());
        assertEquals(1, r.player().inventory().quantity(StubItem.SHIELD));
        assertNotSame(s, r.shop());
    }

    @Test
    void buyShouldBlockIfAlreadyBoughtSameItemInSession() {
        final Shop s = shop(playerWithGold(10), 3, item(StubItem.SHIELD, 3));

        final ShopActionResult first = s.buy(StubItem.SHIELD);
        assertEquals(ShopActionEffect.APPLIED, first.effect());
        assertEquals(2, first.remainingPurchases());
        assertEquals(SEVEN_POSITIVE, first.player().goldCount());
        assertEquals(1, first.player().inventory().quantity(StubItem.SHIELD));

        final ShopActionResult second = first.shop().buy(StubItem.SHIELD);

        assertEquals(ShopStopReason.ALREADY_BOUGHT, second.reason());
        assertEquals(ShopActionEffect.BLOCKED, second.effect());
        assertEquals(2, second.remainingPurchases());
        assertEquals(SEVEN_POSITIVE, second.player().goldCount());
        assertEquals(1, second.player().inventory().quantity(StubItem.SHIELD));
    }

    @Test
    void buyShouldBlockWhenLimitReached() {
        final Shop s = shop(
            playerWithGold(100),
            1,
            item(StubItem.SHIELD, 3),
            item(StubItem.PICKAXE, 5)
        );

        final ShopActionResult first = s.buy(StubItem.SHIELD);
        assertEquals(ShopActionEffect.APPLIED, first.effect());
        assertEquals(0, first.remainingPurchases());
        assertEquals(NINETYSEVEN_POSITIVE, first.player().goldCount());
        assertEquals(1, first.player().inventory().quantity(StubItem.SHIELD));

        final ShopActionResult second = first.shop().buy(StubItem.PICKAXE);

        assertEquals(ShopStopReason.LIMIT_REACHED, second.reason());
        assertEquals(ShopActionEffect.BLOCKED, second.effect());
        assertEquals(0, second.remainingPurchases());
        assertEquals(NINETYSEVEN_POSITIVE, second.player().goldCount());
        assertEquals(0, second.player().inventory().quantity(StubItem.PICKAXE));
    }

    @Test
    void buyShouldUpdateShopStateAcrossMultiplePurchases() {
        final Shop s = shop(
            playerWithGold(10),
            2,
            item(StubItem.SHIELD, 3),
            item(StubItem.PICKAXE, 5)
        );

        final ShopActionResult first = s.buy(StubItem.SHIELD);
        assertEquals(ShopActionEffect.APPLIED, first.effect());
        assertEquals(1, first.remainingPurchases());
        assertEquals(SEVEN_POSITIVE, first.player().goldCount());
        assertEquals(1, first.player().inventory().quantity(StubItem.SHIELD));

        final ShopActionResult second = first.shop().buy(StubItem.PICKAXE);
        assertEquals(ShopActionEffect.APPLIED, second.effect());
        assertEquals(0, second.remainingPurchases());
        assertEquals(2, second.player().goldCount());
        assertEquals(1, second.player().inventory().quantity(StubItem.PICKAXE));
    }
}
