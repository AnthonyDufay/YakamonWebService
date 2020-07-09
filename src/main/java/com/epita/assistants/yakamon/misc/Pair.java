package com.epita.assistants.yakamon.misc;

public class Pair {
    public String id;
    public Integer nb;

    public Pair(String id, Integer nb) {
        this.id = id;
        this.nb = nb;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Pair && ((Pair) obj).id.equals(id) && ((Pair) obj).nb.equals(nb);
    }
}
