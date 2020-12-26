package org.wendex;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageList {
    private ArrayList<Storage> list = new ArrayList<>();
    private HashMap<byte[], Storage> map = new HashMap<>();

    public StorageList() {}

    public void notifyUpdate(byte[] frame, int beginInterval, int endInterval) {
        if (map.containsKey(frame)) {
            Storage storage = map.get(frame);
            storage.setBeginInterval(beginInterval);
            storage.setEndInterval(endInterval);
        }
    }

    public Storage getByFrame(byte[] frame) {

    }
}
