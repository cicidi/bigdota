package com.cicidi.bigdota.service.dota;

import com.cicidi.bigdota.domain.dota.Hero;
import com.cicidi.utilities.JSONUtil;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class HeroDetailMapService {
    static List<Hero> heroList;
    static Map<Integer, Hero> map;
    static String current;

    static {
        try {
            loadHeroData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    public static void convert(String key) {
    public static String convert(String key) throws IOException {
//        String key = "+35+38+50+71+94 vs +11+14+76+77+88,1";

        key = key.replaceAll("[()]", "");
        current = key;
        int index_vs = key.indexOf("vs");
        String[] team0 = key.substring(1, index_vs).split("\\+");
        String[] team1 = key.substring(index_vs + 4, key.indexOf(",")).split("\\+");
        if (team0.length > 5 && team1.length > 5) {
            System.out.println("check");
        }
        return convertToString(idToName(team0)) + " vs " + convertToString(idToName(team1));

    }

    public static Map loadHeroData() throws IOException {
        String path = "hero.json";
        ClassLoader classLoader = new HeroDetailMapService().getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        String jsonStr = new String(Files.readAllBytes(Paths.get(file.getPath())));
        heroList = JSONUtil.getObjectMapper().readValue(jsonStr, new TypeReference<Collection<Hero>>() {
        });
        map = new HashMap();
        for (Hero hero : heroList) {
            map.put(hero.getId(), hero);
        }
        return map;
    }


    public static List<String> idToName(String[] ids) {
        List<String> list = new ArrayList<>();
        for (String id : ids) {
            if (id.trim().length() > 0) {
                Hero hero = map.get(Integer.valueOf(id.trim()));
                list.add(hero.getName());
            }
        }
        return list;
    }

    public static String convertToString(List<String> list) {
        return list.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
    }

}
