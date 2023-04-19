package prog1.kotprog.dontstarve.solution.inventory;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

import java.util.*;


public class Inventory implements BaseInventory {

    private List<AbstractItem> slots;

    public Inventory() {
        slots = new ArrayList<AbstractItem>();
        for (int i = 0; i < 10; i++) {
            slots.add(null);
        }
    }

    @Override
    public boolean addItem(AbstractItem item) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i) == null) {
                slots.add(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public AbstractItem dropItem(int index) {
        for (int i = 0; i < slots.size(); i++) {
            if (i == index) {
                AbstractItem eldobando = slots.get(i);
                slots.remove(i);
                return eldobando;
            }
        }
        return null;
    }

    @Override
    public boolean removeItem(ItemType type, int amount) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getType() == type) {
                if (amount == slots.get(i).getAmount()) {
                    slots.remove(i);
                    return true;
                }
                if (amount < slots.get(i).getAmount()) {
                    slots.get(i).setAmount(slots.get(i).getAmount() - amount);
                    return true;
                }
                if (amount > slots.get(i).getAmount()) {
                    int kimaradt = amount - slots.get(i).getAmount();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean swapItems(int index1, int index2) {
        if (index1 != index2 && index1 >= 0 && index2 >= 0 && index1 <= slots.size() && index2 <= slots.size()) {
            Collections.swap(slots, index1, index2);
            return true;
        }
        return false;
    }

    @Override
    public boolean moveItem(int index, int newIndex) {
        AbstractItem elem = slots.get(index);
        if (index >= 0 && index < slots.size() && newIndex >= 0 && newIndex <= slots.size()) {
            if (slots.get(newIndex) == null) {
                if (newIndex > 0) {
                    slots.add(newIndex - 1, elem);
                } else {
                    slots.add(newIndex + 1, elem);
                }
            } else {
                slots.remove(index);
                slots.add(newIndex, elem);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean combineItems(int index1, int index2) {
        return false;
    }

    @Override
    public boolean equipItem(int index) {
        return false;
    }

    @Override
    public EquippableItem unequipItem() {
        return null;
    }

    @Override
    public ItemType cookItem(int index) {
        return null;
    }

    @Override
    public ItemType eatItem(int index) {
        for (int i = 0; i < slots.size(); i++) {
            if (i == index) {
                if (slots.get(i).getType().equals(ItemType.COOKED_BERRY) ||
                        slots.get(i).getType().equals(ItemType.RAW_BERRY) ||
                        slots.get(i).getType().equals(ItemType.COOKED_CARROT) ||
                        slots.get(i).getType().equals(ItemType.RAW_CARROT)) {
                    slots.get(i).setAmount(slots.get(i).getAmount() - 1);
                    return slots.get(i).getType();
                }
            }
        }
        return null;
    }

    @Override
    public int emptySlots() {
        int urese = 0;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i) == null) {
                urese++;
            }
        }
        return urese;
    }

    @Override
    public EquippableItem equippedItem() {
        return null;
    }

    @Override
    public AbstractItem getItem(int index) {
        if (index <= -1 || index > slots.size()) {
            return null;
        } else {
            return slots.get(index);
        }
    }

}
