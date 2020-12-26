package org.wendex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class StorageList {
    private static final long STORAGE_TIMEOUT = 60000;

    private ArrayList<Storage> list = new ArrayList<>();
    private HashMap<byte[], Storage> map = new HashMap<>();
    private Random random = new Random();

    public StorageList() {}

    public void notifyUpdate(byte[] frame, int beginInterval, int endInterval) {
        if (map.containsKey(frame)) {
            Storage storage = map.get(frame);
            storage.setBeginInterval(beginInterval);
            storage.setEndInterval(endInterval);
            storage.updateNotificationTime();
        } else {
            Storage storage = new Storage(beginInterval, endInterval, frame);
            map.put(frame, storage);
            list.add(storage);
        }
    }

    public Storage findByIndex(int index) {
        int firstIndex = random.nextInt(list.size());
        long curTime = System.currentTimeMillis();
        for (int i = firstIndex; i < list.size(); i++) {
            Storage storage = list.get(i);
            long delta = curTime - storage.getNotificationTime();
            if (delta > STORAGE_TIMEOUT) {
                map.remove(storage.getRouteFrame());
                list.set(i, list.get(list.size() - 1));
                list.remove(list.size() - 1);
                i--;
                continue;
            }
            if (index >= storage.getBeginInterval() && index <= storage.getEndInterval())
                return storage;
        }
        for (int i = 0; i < firstIndex; i++) {
            Storage storage = list.get(i);
            long delta = curTime - storage.getNotificationTime();
            if (delta > STORAGE_TIMEOUT) {
                map.remove(storage.getRouteFrame());
                list.set(i, list.get(list.size() - 1));
                list.remove(list.size() - 1);
                i--;
                continue;
            }
            if (index >= storage.getBeginInterval() && index <= storage.getEndInterval())
                return storage;
        }
    }
}
