package Klm1.KLMLineMaintenanceServer.models.helper;

import Klm1.KLMLineMaintenanceServer.models.Equipment;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class EquipmentComparator implements Comparator<Equipment> {
    private Map<String, Integer> importance;

    public EquipmentComparator() {
        this.importance = new HashMap<>();

        importance.put("Nitrogen cart", 1);
        importance.put("Tires and brakes cart", 2);
        importance.put("Tires cart", 3);
    }

    @Override
    public int compare(Equipment o1, Equipment o2) {
        String group1 = o1.getType().getGroup();
        String group2 = o2.getType().getGroup();

        if (group1.equals(group2)) {
            return compareSubgroup(o1.getType().getSubgroup(), o2.getType().getSubgroup());
        }

        if (importance.containsKey(group1) && importance.containsKey(group2)) {
            return importance.get(group1) - importance.get(group2);
        } else if (importance.containsKey(group1)) {
            return -1;
        } else if (importance.containsKey(group2)) {
            return 1;
        } else {
            return group1.compareTo(group2);
        }
    }

    private int compareSubgroup(String subgroup1, String subgroup2) {
        return subgroup1.compareTo(subgroup2);
    }
}
