package util;

import java.util.Map;

public class RepositoryHelper {

    public static int getNextIdForMap(Map<Integer, ?> map) {
        int id = 1;
        while (map.containsKey(id)) {
            id++;
        }
        return id;
    }
}
