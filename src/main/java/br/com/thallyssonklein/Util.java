package br.com.thallyssonklein;

import br.com.thallyssonklein.entity.Log;

import java.util.*;
import java.util.stream.Collectors;

public class Util {
    public static Map<String, Integer> countFrequencies(List<String> list){
        Map<String, Integer> oc = new HashMap<String, Integer>();

        for(String s : list){
            Integer i = oc.get(s);
            oc.put(s, (i == null) ? 1 : i + 1);
        }
        return oc;
    }

    public static int countFrequenciesByMinute(List<Log> list){
        Map<Integer, Integer> oc = new HashMap<>();
        for(Log l : list){
            Calendar cal = Calendar.getInstance();
            cal.setTime(l.getTimes());
            Integer i = oc.get(cal.get(Calendar.MINUTE));
            oc.put(cal.get(Calendar.MINUTE), (i == null) ? 1 : i + 1);
        }
        return maxEntry(oc);
    }

    public static Map<String, Integer> sortMap(Map<String, Integer> map){
        return map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static int maxEntry(Map<Integer, Integer> map){
        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }
}
