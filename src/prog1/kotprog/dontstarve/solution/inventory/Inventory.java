package prog1.kotprog.dontstarve.solution.inventory;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemAxe;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

import java.sql.SQLOutput;
import java.util.*;


public class Inventory implements BaseInventory {

    public AbstractItem[] slots;

    public Inventory() {
        slots = new AbstractItem[10];
    }
    public static boolean IsStackelheto(AbstractItem item){
        if (item.getType() == ItemType.LOG ||
                item.getType() == ItemType.LOG ||
                item.getType() == ItemType.STONE ||
                item.getType() == ItemType.TWIG ||
                item.getType() == ItemType.RAW_CARROT ||
                item.getType() == ItemType.COOKED_CARROT ||
                item.getType() == ItemType.RAW_BERRY ||
                item.getType() == ItemType.COOKED_BERRY){
            return true;
        }
        return false;
    }
    public static int ItemStack(AbstractItem item){
        if (IsStackelheto(item) && item.getType() == ItemType.LOG){
            return 15;
        }
        if (IsStackelheto(item) && item.getType() == ItemType.STONE){
            return 10;
        }
        if (IsStackelheto(item) && item.getType() == ItemType.TWIG){
            return 20;
        }
        if (IsStackelheto(item) && item.getType() == ItemType.RAW_CARROT ||
                item.getType() == ItemType.COOKED_CARROT ||
                item.getType() == ItemType.RAW_BERRY ||
                item.getType() == ItemType.COOKED_BERRY){
            return 10;
        }
        return 0;
    }

    @Override
    public boolean addItem(AbstractItem item) {
        if (!IsStackelheto(item)){
            for (int i = 0; i < slots.length; i++) {
                if (getItem(i) == null){
                    if (item.getAmount() == 1){
                        slots[i] = item;
                        return true; //1db, 1 fajta nem stackelhető item...
                    }
                }
            }
        }
        else{
            //Stackelhetoek
            for (int i = 0; i < slots.length; i++) {
                //ha van indexen item
                if (getItem(i) != null){
                    if (item.getType() == slots[i].getType()){
                        int segedam = ItemStack(slots[i]) - slots[i].getAmount();
                        if (slots[i].getAmount() <= segedam){
                            slots[i].setAmount(slots[i].getAmount() + item.getAmount());
                            return true;
                        }
                        else{
                            slots[i].setAmount(ItemStack(slots[i]));
                            item.setAmount(item.getAmount() - segedam);
                        }

                    }
                }
            }
            for (int i = 0; i < slots.length; i++) {
                //nincs az indexen item
                if (getItem(i) == null){
                    if (item.getAmount() <= ItemStack(item)){
                        slots[i] = item;
                        return true;
                    }
                    else{
                        AbstractItem item2 = item.newitem(ItemStack(item));
                        slots[i] = item2;
                        item.setAmount(item.getAmount()-ItemStack(item));
                    }
                }
            }
        }
        return false;
    }

    @Override
    public AbstractItem dropItem(int index) {
        for (int i = 0; i < slots.length; i++) {
            if (i == index) {
                AbstractItem eldobando = slots[i];
                slots[i] = null;
                return eldobando;
            }
        }
        return null;
    }

    @Override
    public boolean removeItem(ItemType type, int amount) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].getType() == type) {
                if (amount == slots[i].getAmount()) {
                    slots[i] = null;
                    return true;
                }
                if (amount <= slots[i].getAmount()) {
                    slots[i].setAmount(slots[i].getAmount() - amount);
                    return true;
                }
                if (amount > slots[i].getAmount()) {
                    for (int j = 0; j < slots.length; j++) {
                        int segedam = amount - slots[i].getAmount();
                        if (slots[j].getType() == type){
                            if (segedam > 0){
                                slots[i] = null;
                                //folytatas;
                            }
                        }
                    }
                    int kimaradt = amount - slots[i].getAmount();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean swapItems(int index1, int index2) {
        if (index1 != index2 && index1 >= 0 && index2 >= 0 && index1 < slots.length && index2 < slots.length) {
            AbstractItem temp = slots[index1];
            slots[index1] = slots[index2];
            slots[index2] = temp;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveItem(int index, int newIndex) {
        if (index >= 0 && index < slots.length && newIndex >= 0 && newIndex < slots.length) {
            if (slots[newIndex] == null) {
                if (slots[index] != null){
                    slots[newIndex] = slots[index];
                    slots[index] = null;
                    return true;
                }
            } else {
                return false;
            }
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
        return false;
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
        for (int i = 0; i < slots.length; i++) {
            if (i == index) {
                if (slots[i].getType().equals(ItemType.COOKED_BERRY) ||
                        slots[i].getType().equals(ItemType.RAW_BERRY) ||
                        slots[i].getType().equals(ItemType.COOKED_CARROT) ||
                        slots[i].getType().equals(ItemType.RAW_CARROT)) {
                    slots[i].setAmount(slots[i].getAmount() - 1);
                    return slots[i].getType();
                }
            }
        }
        return null;
    }

    @Override
    public int emptySlots() {
        int urese = 0;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
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
        if (index <= -1 || index >= slots.length+1){
            throw new ArrayIndexOutOfBoundsException();
        }
        else{
            if (slots[index] == null){
                return null;
            }
            else{
                return slots[index];
            }
        }

    }

}
