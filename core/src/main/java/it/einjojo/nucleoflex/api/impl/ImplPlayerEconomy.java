package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.player.PlayerEconomy;
import it.einjojo.nucleoflex.economy.PlayerEconomyObserver;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread-safe implementation of the PlayerEconomy interface
 */
public class ImplPlayerEconomy implements PlayerEconomy {
    private final UUID player;
    private final PlayerEconomyObserver observer;
    private final AtomicLong balance;

    private transient long lastDelta = 0;


    public ImplPlayerEconomy(UUID player, long balance, PlayerEconomyObserver observer) {
        this.player = player;
        this.observer = observer;
        this.balance = new AtomicLong(balance);
    }

    @Override
    public UUID playerUuid() {
        return player;
    }

    @Override
    public long balance() {
        return balance.get();
    }

    @Override
    public void setBalance(long amount) {
        lastDelta = amount - balance.get();
        setBalanceWithoutPublish(amount);
        observer.publishChange(this);
    }

    public void setBalanceWithoutPublish(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot set a negative amount");
        }
        balance.set(amount);
    }

    @Override
    public void addBalance(long amount) {
        lastDelta = amount;
        addBalanceWithoutPublish(amount);
        observer.publishChange(this);
    }

    public void addBalanceWithoutPublish(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount");
        }
        balance.addAndGet(amount);
    }

    @Override
    public void removeBalance(String player, long amount) {
        lastDelta = -amount;
        removeBalanceWithoutPublish(amount);
        observer.publishChange(this);
    }

    public void removeBalanceWithoutPublish(long amount) {
        if (balance.get() < amount) {
            throw new IllegalArgumentException("Cannot remove more than the balance");
        }
        balance.addAndGet(-amount);
    }

    public long lastDelta() {
        return lastDelta;
    }
}
