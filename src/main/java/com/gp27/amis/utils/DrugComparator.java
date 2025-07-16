package com.gp27.amis.utils;

import com.gp27.amis.models.Drug;

public class DrugComparator implements java.util.Comparator<Drug> {
    @Override
    public int compare(Drug d1, Drug d2) {
        return Integer.compare(d1.getStockLevel(), d2.getStockLevel());
    }
}
