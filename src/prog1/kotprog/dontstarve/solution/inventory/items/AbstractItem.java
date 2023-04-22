package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * Egy általános itemet leíró osztály.
 */
public abstract class AbstractItem {
    /**
     * Az item típusa.
     * @see ItemType
     */
    private final ItemType type;

    /**
     * Az item mennyisége.
     */
    private int amount;

    public AbstractItem newitem(int  amount) {
        return switch (this.type){
            case AXE -> new ItemAxe();
            case COOKED_BERRY -> new ItemCookedBerry(amount);
            case COOKED_CARROT -> new ItemCookedCarrot(amount);
            case FIRE -> new ItemFire();
            case LOG -> new ItemLog(amount);
            case PICKAXE -> new ItemPickaxe();
            case RAW_BERRY -> new ItemRawBerry(amount);
            case RAW_CARROT -> new ItemRawCarrot(amount);
            case SPEAR -> new ItemSpear();
            case STONE -> new ItemStone(amount);
            case TORCH -> new ItemTorch();
            case TWIG -> new ItemTwig(amount);
        };

    }

    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     * @param type az item típusa
     * @param amount az item mennyisége
     */
    public AbstractItem(ItemType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    /**
     * A type gettere.
     * @return a tárgy típusa
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Az amount gettere.
     * @return a tárgy mennyisége
     */
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
