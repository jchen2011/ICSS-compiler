package nl.han.ica.datastructures;

import java.util.HashMap;

public class SymbolTable<K, V> {
    private IHANLinkedList<HashMap<K, V>> scope;

    public SymbolTable() {
        this.scope = new HANLinkedList<>();
        this.scope.addFirst(new HashMap<>());
    }

    public void newScope() {
        this.scope.insert(scope.getSize(), new HashMap<>());
    }

    public void removeScope() {
        if (scope.getSize() > 1) {
            this.scope.delete(scope.getSize() - 1);
        }
    }

    public void assignSymbol(K key, V value) {
        if (scope.getSize() == 0) {
            newScope();
        }
        HashMap<K, V> currentScope = scope.get(scope.getSize() - 1);
        currentScope.put(key, value);
    }

    public V getValue(K key) {
        for (int i = scope.getSize() - 1; i >= 0; i--) {
            HashMap<K, V> scopeLayer = scope.get(i);
            if (scopeLayer.containsKey(key)) {
                return scopeLayer.get(key);
            }
        }
        return null;
    }
}
