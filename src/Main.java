
import prog1.kotprog.dontstarve.solution.inventory.Inventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemTwig;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        AbstractItem[] items = inventory.slots;
        AbstractItem log = new AbstractItem(ItemType.LOG, 16) {
            @Override
            public boolean isStackelheto() {
                return true;
            }
        };
        boolean siker = inventory.addItem(log);
        if (siker){
            System.out.println("igen");
        }else{
            System.out.println("nem");
        }

        for (int i = 0; i < items.length; i++) {
            if (items[i] != null){
                System.out.println(items[i].getType() + " - "+ items[i].getAmount());
            }else{
                System.out.println("null");
            }
        }









//        Map<ItemType, Integer> maxstack;
//        maxstack = new HashMap<>();
//        maxstack.put(ItemType.AXE, 1);
//        maxstack.put(ItemType.PICKAXE, 1);
//        maxstack.put(ItemType.TORCH, 1);
//        maxstack.put(ItemType.SPEAR, 1);
//        maxstack.put(ItemType.LOG, 15);
//        maxstack.put(ItemType.STONE, 10);
//        maxstack.put(ItemType.TWIG, 20);
//        maxstack.put(ItemType.RAW_BERRY, 10);
//        maxstack.put(ItemType.COOKED_BERRY, 10);
//        maxstack.put(ItemType.COOKED_CARROT, 10);
//
//        for (Map.Entry<ItemType,Integer>entry : maxstack.entrySet()) {
//            System.out.println(entry.getKey() +" : "+entry.getValue());
//
//        }
//
//
//        getItem(9);

        //add item elso proba
        //ha a tipusuk megegyezik
//                    String ajtem = String.valueOf(item.getType());
//                    int mennyiseg = item.getAmount();
//                    for (Map.Entry<ItemType,Integer>stackciklus : maxstack.entrySet()) {
//                        System.out.println(stackciklus.getKey() +" : "+stackciklus.getValue());
//                        if (stackciklus.getKey().equals(ajtem)){
//                            if (item.getAmount() > 1){
//
//                            }
//                        }
//
//                    }

        //add item masodik proba
//        public boolean addItem(AbstractItem item) {
//            for (int i = 0; i < slots.size(); i++) {
//                if (slots.get(i) == null){
//                    slots.add(item);//adolja ha befér
//                }
//                else {
//                    for (AbstractItem picupitem : slots) {
//                        if (picupitem.getType() == item.getType()) { //benézük ha nem üres de a tipus ugyanaz
//                            if (picupitem.isStackelheto()) { //stakelheto e az item
//                                ItemType picupitemtype = picupitem.getType();
//                                int picupitemam = picupitem.getAmount();
//
//                                for (Map.Entry<ItemType, Integer> amvizsgal:
//                                        maxstack.entrySet()) {
//                                    if (amvizsgal.getKey().equals(picupitemtype)){
//                                        if (amvizsgal.getValue() <= picupitemam){ //kisebb e az érték mint a maxstack
//                                            slots.add(item);
//                                        }
//                                        else{ //nagyobb az ertek mint a maxstack
//                                            //vonjuk ki a és rakjuk másik slotra
//                                            int leftover = picupitemam - amvizsgal.getValue();
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            return true;
//        }


        //swapItems
//        boolean csere1 = false;
//        AbstractItem mentes1 = null;
//        boolean csere2 = false;
//        AbstractItem mentes2 = null;
//        int siker = 0;
//
//
//        for (int i = 0; i < slots.size(); i++) {
//            if (i == index1){
//                if (slots.get(i).getType() != null){
//                    mentes1 = slots.get(i);
//                    slots.remove(i);
//                    csere1 = true;
//                }
//            }
//            if (i == index2){
//                if (slots.get(i).getType() != null){
//                    mentes2 = slots.get(i);
//                    slots.remove(i);
//                    csere2 = true;
//                }
//            }
//        }
//        if (csere1 && csere2){
//            for (int i = 0; i < slots.size(); i++) {
//                if (i == index1){
//                    slots.add(mentes2);
//                    siker++;
//                }
//                if (i == index2){
//                    slots.add(mentes1);
//                    siker++;
//                }
//            }
//        }
//        if (siker == 2){
//            return true;
//        }
//        else{
//            return false;
//        }
    }

//    private static void getItem(int index) {
//        List<Integer> slots;
//        slots = new ArrayList<Integer>();
//        for (int i = 0; i < 10; i++){
//            if (i == 0){
//                slots.add(12);
//            }
//            else{
//                slots.add(1);
//            }
//        }
//
//
//        if (index < 0 || index >= slots.size()){
//            System.out.println("null");
//        }
//        else{
//            System.out.println(slots.get(index));
//        }
//    }
}
