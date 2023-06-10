package ru.job4j.cash;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    synchronized public boolean add(Account account) {
       return accounts.putIfAbsent(account.id(), account) == null;
    }

    synchronized public boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    synchronized public boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    synchronized public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }
    public boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        synchronized (accounts) {
            Account fromAccount = getById(fromId).get();
            Account toAccount = getById(toId).get();
            if (fromAccount != null && toAccount != null
                    && fromAccount.amount() >= amount) {
                Account updatedFromAccount = new Account(fromAccount.id(), fromAccount.amount() - amount);
                Account updatedToAccount = new Account(toAccount.id(), toAccount.amount() + amount);
                accounts.put(fromId, updatedFromAccount);
                accounts.put(toId, updatedToAccount);
                result = true;
            }
        }
        return result;
    }
}
