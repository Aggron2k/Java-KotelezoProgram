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
    public static int ItemStack(AbstractItem item){
        if (item.isStackelheto() && item.getType() == ItemType.LOG){
            return 15;
        }
        if (item.isStackelheto() && item.getType() == ItemType.STONE){
            return 10;
        }
        if (item.isStackelheto() && item.getType() == ItemType.TWIG){
            return 20;
        }
        if (item.isStackelheto() && item.getType() == ItemType.RAW_CARROT ||
                item.getType() == ItemType.COOKED_CARROT ||
                item.getType() == ItemType.RAW_BERRY ||
                item.getType() == ItemType.COOKED_BERRY){
            return 10;
        }
        return 0;
    }

    @Override
    public boolean addItem(AbstractItem item) {
        for (int i = 0; i < slots.length; i++) {
            if (getItem(i) == null){
                System.out.println("??? getitem i == null: "+getItem(i));
                if (!item.isStackelheto()){
                    if (item.getAmount() == 1){ //pl AXE
                        slots[i] = item;
                        return true; //1db, 1 fajta nem stackelhető item
                    }
                    else{
                        slots[i] = item;//több, 1 fajta nem stackelhető item, tovabb megy
                        item.setAmount(item.getAmount()-1); //NEM BIZTOS HOGY ÍGY JÓ!
                    }
                }
                else{
                    //ide kellene a stackelheto itemek vizsgalni
                    if (item.getAmount() <= ItemStack(item)){
                        System.out.println(i+ " - i");
                        System.out.println("item.getAmount():" + item.getAmount() + "<= ItemStack(item)"+ItemStack(item));
                        slots[i] = item;
                        System.out.println("sikerult hozzadni");
                        return true; //Néhány db, 1fajta stackelhető item
                    }
                    if (item.getAmount() > ItemStack(item)){
                            System.out.println(i+ " - i");
                            System.out.println("item.getAmount():" + item.getAmount() + "> ItemStack(item)"+ItemStack(item));

                        int segedam = item.getAmount();
                            System.out.println("segedam:"+segedam);
                        item.setAmount(ItemStack(item));
                            System.out.println("setAmount:"+ItemStack(item));
                            System.out.println("ellenorzes hogy sikerult e és addoljuk ezt a mennyiseget: getAmount:"+item.getAmount());

                        slots[i] = item;
                        item.setAmount(segedam - ItemStack(item));
                            System.out.println("SET: segedam:"+segedam+"- ItemStack(item)"+ItemStack(item));
                            System.out.println("tovabblepunk");
                    }
                }
            }
            if (getItem(i) != null){
                if (item.isStackelheto()){
                    if (slots[i].getType() == item.getType()){
                        if (slots[i].getAmount() <= ItemStack(item)){
                            slots[i] = item;
                            return true;
                        }
                        if (slots[i].getAmount() > ItemStack(item)){
                            int segedam = item.getAmount();
                            item.setAmount(ItemStack(item));
                            slots[i] = item;
                            item.setAmount(segedam - ItemStack(item));
                        }
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
                if (amount < slots[i].getAmount()) {
                    slots[i].setAmount(slots[i].getAmount() - amount);
                    return true;
                }
                if (amount > slots[i].getAmount()) {
                    int kimaradt = amount - slots[i].getAmount();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean swapItems(int index1, int index2) {
        if (index1 != index2 && index1 >= 0 && index2 >= 0 && index1 <= slots.length && index2 <= slots.length) {
            return true;
        }
        return false;
    }

    @Override
    public boolean moveItem(int index, int newIndex) {
        AbstractItem elem = slots[index];
        if (index >= 0 && index < slots.length && newIndex >= 0 && newIndex <= slots.length) {
            if (slots[newIndex] == null) {
                if (newIndex > 0) {
                    //slots.add(newIndex - 1, elem);
                } else {
                    //slots.add(newIndex + 1, elem);
                }
            } else {
                //slots.remove(index);
                //slots.add(newIndex, elem);
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
        if (index <= -1 || index > slots.length){
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
