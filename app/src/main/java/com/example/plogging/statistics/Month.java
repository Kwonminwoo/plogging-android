package com.example.plogging.statistics;

import java.util.List;

public class Month {
    private int month;
    private List<Statistics> statisticsList;

    public Month(int month, List<Statistics> statisticsList) {
        this.month = month;
        this.statisticsList = statisticsList;
    }

    public List<Statistics> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<Statistics> statisticsList) {
        this.statisticsList = statisticsList;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
