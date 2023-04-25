package prog1.kotprog.dontstarve.solution.inventory;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemAxe;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

import java.sql.SQLOutput;
import java.util.*;


public class Inventory implements BaseInventory {

    public AbstractItem[] slots;
    public EquippableItem equippeditem;

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
            return false;
        }
        else{
            //Stackelhetoek
            for (int i = 0; i < slots.length; i++) {
                //ha van indexen item
                if (getItem(i) != null){
                    if (slots[i].getType() == item.getType()){
                        if (slots[i].getAmount() < ItemStack(slots[i])){
                            int segedam = ItemStack(slots[i]) - slots[i].getAmount();
                            if (item.getAmount() <= segedam){
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
        int kivonando = amount;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].getType() == type ) {
                if (kivonando > slots[i].getAmount()) {// 16 > 15
                    kivonando = kivonando - slots[i].getAmount();
                    slots[i] = null;
                    continue;
                }
                if (kivonando < slots[i].getAmount()) { // 16 < 15
                    int belso = slots[i].getAmount() - kivonando;
                    AbstractItem item2 = new AbstractItem(type, belso) {
                        public ItemType getType() {
                            return super.getType();
                        }
                        @Override
                        public void setAmount(int belso) { // 15
                            super.setAmount(belso);
                        }
                    };
                    slots[i] = item2;
                    return true;
                }
                if (amount == slots[i].getAmount()) {
                    slots[i] = null; //ez jo
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
        }
        return false;
    }

    @Override
    public boolean combineItems(int index1, int index2) {
        if (index1 >= 0 && index1 < slots.length && index2 >= 0 && index2 < slots.length){
            for (int i = 0; i < slots.length; i++) {
                if (slots[index1].getType() == slots[index2].getType()){
                    AbstractItem item = new AbstractItem(slots[index1].getType(), slots[index1].getAmount()) {
                        @Override
                        public ItemType getType() {
                            return super.getType();
                        }

                        @Override
                        public int getAmount() {
                            return super.getAmount();
                        }

                        @Override
                        public void setAmount(int amount) {
                            super.setAmount(amount);
                        }
                    };
                    if (slots[index1].getAmount() + slots[index2].getAmount() <= ItemStack(item)){
                        item.setAmount(slots[index1].getAmount() + slots[index2].getAmount());
                        slots[index1] = item;
                        slots[index2] = null;
                        return true;
                    }
                    if (slots[index1].getAmount() + slots[index2].getAmount() > ItemStack(item)){
                        if (slots[index1].getAmount() == ItemStack(item)){
                            return false;
                        }
                        if (slots[index1].getAmount() < ItemStack(item)){
                            int szabadhelyindex1en = ItemStack(item) - slots[index1].getAmount();
                            AbstractItem item1 = new AbstractItem(slots[index1].getType(), ItemStack(slots[index1])) {
                                @Override
                                public ItemType getType() {
                                    return super.getType();
                                }

                                @Override
                                public int getAmount() {
                                    return super.getAmount();
                                }
                            };
                            slots[index1] = item1;
                            AbstractItem item2 = new AbstractItem(slots[index2].getType(), slots[index2].getAmount()) {
                                @Override
                                public ItemType getType() {
                                    return super.getType();
                                }

                                @Override
                                public int getAmount() {
                                    return super.getAmount();
                                }

                                @Override
                                public void setAmount(int amount) {
                                    super.setAmount(amount - szabadhelyindex1en);
                                }
                            };
                            slots[index2] = item2;
                            return true;
                        }
                    }
                }
            }
        }
        return false; //index 2őt rakod bele az index 1be
    }

    @Override
    public boolean equipItem(int index) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null){
                if (index >= 0 && index < slots.length){
                    if (equippeditem == null){
                        if (i == index){
                            equippeditem = (EquippableItem) slots[i];
                            slots[i] = null;
                            return true;
                        }
                    }
                    else{
                        if (i == index){
                            AbstractItem tohand = slots[i];
                            slots[i] = null;
                            unequipItem();
                            equippeditem = (EquippableItem) tohand;
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    @Override
    public EquippableItem unequipItem() {
        if (equippeditem != null){
            for (int i = 0; i < slots.length; i++) {
                if (slots[i] == null){
                    slots[i] = equippeditem;
                    equippeditem = null;
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public ItemType cookItem(int index) {
        if (index >= 0 && index < slots.length){
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null){
                if (index == i){
                        if (slots[i].getType() == ItemType.RAW_BERRY && slots[i].getAmount() == 1){
                            AbstractItem raw_food = new AbstractItem(slots[i].getType(), slots[i].getAmount()){};
                            slots[i] = null;
                            return raw_food.getType();
                        }
                        if (slots[i].getType() == ItemType.RAW_CARROT && slots[i].getAmount() == 1){
                            AbstractItem raw_food = new AbstractItem(slots[i].getType(), slots[i].getAmount()){};
                            slots[i] = null;
                            return raw_food.getType();
                        }
                        if (slots[i].getType() == ItemType.RAW_BERRY && slots[i].getAmount() > 1){
                            AbstractItem raw_food = new AbstractItem(slots[i].getType(), slots[i].getAmount()) {};
                            raw_food.setAmount(raw_food.getAmount()-1);
                            slots[i] = raw_food;
                            return raw_food.getType();
                        }
                        if (slots[i].getType() == ItemType.RAW_CARROT && slots[i].getAmount() > 1){
                            AbstractItem raw_food = new AbstractItem(slots[i].getType(), slots[i].getAmount()) {};
                            raw_food.setAmount(raw_food.getAmount()-1);
                            slots[i] = raw_food;
                            return raw_food.getType();
                        }
                }
            }
        }
        }

        return null;
    }

    @Override
    public ItemType eatItem(int index) {
        for (int i = 0; i < slots.length; i++) {
            if (i == index) {
                if (slots[i] !=null){
                    if (slots[i].getType() == ItemType.COOKED_BERRY ||
                            slots[i].getType() == ItemType.RAW_BERRY ||
                            slots[i].getType() == ItemType.COOKED_CARROT ||
                            slots[i].getType() == ItemType.RAW_CARROT) {
                        if (slots[i].getAmount() == 1){
                            AbstractItem megevett = slots[i];
                            slots[i] = null;
                            return megevett.getType();
                        }
                        else{
                            AbstractItem hamham = slots[i];
                            hamham.setAmount(slots[i].getAmount() - 1);
                            slots[i] = hamham;
                            return hamham.getType();
                        }
                    }
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
        if (equippeditem != null){
            return equippeditem;
        }else{
            return null;
        }
    }

    @Override
    public AbstractItem getItem(int index) {
        if (index <= -1 || index >= slots.length){
        }
        else{
            if (slots[index] == null){
                return null;
            }
            else{
                return slots[index];
            }
        }
        return null;
    }

}
