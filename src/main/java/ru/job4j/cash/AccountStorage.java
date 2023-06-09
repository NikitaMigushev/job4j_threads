package ru.job4j.cash;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    synchronized public boolean add(Account account) {
        boolean result = false;
        if (accounts.get(account.id()) == null) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    synchronized public boolean update(Account account) {
        boolean result = false;
        Optional searchAccount = getById(account.id());
        if (searchAccount.isPresent()) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    synchronized public boolean delete(int id) {
        boolean result = false;
        Optional searchAccount = getById(id);
        if (searchAccount.isPresent()) {
            accounts.remove(id);
            result = true;
        }
        return result;
    }

    synchronized public Optional<Account> getById(int id) {
        Optional result = Optional.empty();
        Account account = accounts.get(id);
        if (account != null) {
            result = Optional.of(account);
        }
        return result;
    }
    public boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        synchronized (accounts) {
            Account fromAccount = accounts.get(fromId);
            Account toAccount = accounts.get(toId);
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
