package com.company.concurrent.deadlock.balance;

import javax.naming.InsufficientResourcesException;

// 解决死锁版本
class Account {
    private int money;

    public Account(int money) {
        this.money = money;
    }

    public void debit(int amount) {
        System.out.println("after debit " + amount + " " + this.money + " -> " + (this.money-amount));
        this.money -= amount;
    }

    public void credit(int amount) {
        System.out.println("after credit " + amount + " " + this.money + " -> " + (this.money+amount));
        this.money += amount;
    }

    public int getBalance() {
        return this.money;
    }
}

