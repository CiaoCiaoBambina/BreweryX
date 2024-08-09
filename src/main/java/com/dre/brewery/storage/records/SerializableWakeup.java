package com.dre.brewery.storage.records;

import com.dre.brewery.Wakeup;
import com.dre.brewery.storage.DataManager;
import com.dre.brewery.utility.BUtil;
import org.bukkit.Location;

import java.util.List;

/**
 * Represents a wakeup that can be serialized.
 * @param id The UUID of the wakeup
 * @param serializedLocation The Location of the wakeup
 */
public record SerializableWakeup(String id, String serializedLocation) implements SerializableThing {
    public SerializableWakeup(Wakeup wakeup) {
        this(wakeup.getId().toString(), DataManager.serializeLocation(wakeup.getLoc(), true));
    }

    public Wakeup toWakeup() {
        Location loc = DataManager.deserializeLocation(serializedLocation, true);
        if (loc == null) {
            return null;
        }
        return new Wakeup(loc, BUtil.uuidFromString(id));
    }

    @Override
    public String getId() {
        return id;
    }

    public static List<SerializableWakeup> fromWakeups(List<Wakeup> wakeups) {
        if (wakeups == null) {
            return List.of();
        }
        return wakeups.stream().map(SerializableWakeup::new).toList();
    }

    public static List<Wakeup> toWakeups(List<SerializableWakeup> wakeups) {
        if (wakeups == null || wakeups.isEmpty()) {
            return List.of();
        }
        return wakeups.stream().map(SerializableWakeup::toWakeup).toList();
    }

    @Override
    public String toString() {
        return "SerializableWakeup{" +
                "id='" + id + '\'' +
                ", serializedLocation='" + serializedLocation + '\'' +
                '}';
    }
}