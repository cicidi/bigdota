package com.cicidi.bigdota.domain.dota.ruleEngine;

/**
 * @author cicidi on 4/2/19
 */
public class BestHeroCombination {

    private String combination;
    private int total;

    public BestHeroCombination() {
    }

    public BestHeroCombination(String combination, int total) {
        this.combination = combination;
        this.total = total;
    }

    public String getCombination() {

        return combination;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
