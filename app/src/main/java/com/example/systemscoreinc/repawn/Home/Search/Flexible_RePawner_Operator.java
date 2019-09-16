package com.example.systemscoreinc.repawn.Home.Search;

import com.example.systemscoreinc.repawn.Home.RePawners.RePawnerList;

import java.util.Comparator;

public class Flexible_RePawner_Operator  implements Comparator<RePawnerList> {
    public enum Order {Name, Age, Country}

    private Order sortingBy;

    @Override
    public int compare(RePawnerList r1, RePawnerList r2) {
        switch(sortingBy) {
            case Name: return r1.getRname().compareTo(r2.getRname());
         //   case Age: return person1.age.compareTo(person2.age);
        //    case Country: return person1.country.compareTo(person2.country);
        }
        throw new RuntimeException("Practically unreachable code, can't be thrown");
    }

    public void setSortingBy(Order sortBy) {
        this.sortingBy = sortingBy;
    }
}