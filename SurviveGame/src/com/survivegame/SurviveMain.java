package com.survivegame;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SurviveMain {



    public static void main(String[] args) throws IOException {
        String path="src/com/survivegame/input.txt";
        String outputPath="src/com/survivegame/output.txt";
        HashMap<String,List<Integer>> enemyMap=new HashMap<>();
        List<String> heroList=new ArrayList<>();
        List<String>enemyList=new ArrayList<>();

        try  (Stream<String> stream= Files.lines(Paths.get(path))) {
            heroList = getHero(stream,"Hero");
        }catch (Exception e){

            e.printStackTrace();
        }
        for (String hero:heroList) {
            try (Stream<String> stream = Files.lines(Paths.get(path))) {

                List<Integer> list = getStringList(stream, hero);
                enemyMap.put(hero, list);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        try  (Stream<String> stream= Files.lines(Paths.get(path))) {
            enemyList = getEnemies(stream,"Enemy");
        }catch (Exception e){

            e.printStackTrace();
        }

        for (String enemy:enemyList) {
            try (Stream<String> stream = Files.lines(Paths.get(path))) {

                List<Integer> list = getStringList(stream, enemy);
                enemyMap.put(enemy, list);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }


            writeFile(outputPath,fight(sortPosition(createCharacters(enemyMap,path))));


    }
    private static   void writeFile(String path,List<String> result)
    {
        try
        {
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(new File(path)));
        result.forEach(n-> {
            try {
                bufferedWriter.write(n+"\n");
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
    private static List<String> fight(List<Character> characters)
    {
        List<String> result=new ArrayList<>();

        boolean checkHp=true;
        Hero hero= (Hero) characters.get(0);
        characters.remove(hero);
        result.add(hero.getType() + " started journey with "+hero.getHp()+ " HP!");
        List<Enemy> enemies=characters.stream().map(line-> (Enemy) line).toList();
        for (Enemy enemy:enemies)
        {
            while(enemy.getHp()>0)
                {
                    hero.setHp(hero.getHp()-enemy.getAttack());
                    enemy.setHp(enemy.getHp()- hero.getAttack());
                    if(hero.getHp()<0)
                    {

                        checkHp=false;
                        result.add(enemy.getName()+"defeated hero with "+enemy.getHp()+" remaining");
                        result.add("Hero is Dead!! Last seen at position "+enemy.getPosition()+" !!");
                        return result;
                    }

                }
                if(hero.getHp()>0)
                result.add("Hero defeated "+enemy.getName()+" with "+hero.getHp()+" HP remaining");
            }
        if(checkHp)
        {
            result.add("Hero Survived!");
        }
     return result;
    }

    private static List<Character> createCharacters(Map<String,List<Integer>> mapCharacter,String path) throws IOException {
        List<Character> charactersList=new ArrayList<>();
        Set<String> setKeys =  mapCharacter.keySet();
        for (String key:setKeys) {
            mapCharacter.get(key).size();
            if(key.equals("Hero"))
            {
                Hero hero=Hero.getINSTANCE(mapCharacter.get(key).get(0),mapCharacter.get(key).get(1),0,key,getResources(path));
                charactersList.add(hero);
            }
            else
            {
                int hp=mapCharacter.get(key).get(0);
                int attack=mapCharacter.get(key).get(1);
                for (int i = 2; i <mapCharacter.get(key).size() ; i++) {
                    Enemy enemy=new Enemy(hp,attack,mapCharacter.get(key).get(i),key);
                    charactersList.add(enemy);
                }

            }
        }
        return charactersList;
    }

    private static List<Character> sortPosition(List<Character> characters)
    {
        List sortedList = characters.stream().sorted(Comparator.comparingInt(Character::getPosition)).
                collect(Collectors.toList());

        return  sortedList;

    }
    private static List<String> getEnemies(Stream<String> stream, String enemies) {
        List<String> list;
        List<String> temp= stream.filter((line->line.contains(enemies))).collect(Collectors.toList());
        list= temp.stream().map(p -> {
            StringTokenizer tokenizer=new StringTokenizer(p," ");
            String tempString=tokenizer.nextToken();
            return    tempString;
        }).collect(Collectors.toList());
        return list;
    }

    private static int getResources(String path) throws IOException {

        BufferedReader bufferedReader=new BufferedReader(new FileReader(path));
        String input=bufferedReader.readLine();
        var tokens= input.split( " ");
        for (String str:tokens) {
         if(java.lang.Character.isDigit(str.charAt(0)))
         {
             return Integer.parseInt(str);
         }
        }
        return 0;
    }

    private static List<Integer> getStringList(Stream<String> stream, String enemies) {
        List<Integer> list=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        stream.forEach(line -> {
            String [] array= line.split(" ");
            Arrays.stream(array).forEach(l -> {
                if(  l.equals(enemies)){
                    list2.add(line);
                }
            });
        });
        list2.stream().forEach(line->{
            isEnemy(line,list);
        });
        return list;
    }
    static void isEnemy(String p , List<Integer> list) {
        StringTokenizer tokenizer = new StringTokenizer(p, " ");

        while (tokenizer.hasMoreTokens()) {
            String tempString = tokenizer.nextToken();


            if (java.lang.Character.isDigit(tempString.charAt(0))){

                list.add(Integer.parseInt(tempString));
            }

        }
    }
    private static List<String> getHero(Stream<String> stream, String hero) {
        List<String> list;
        List<String> temp= stream.filter((line->line.contains(hero))).collect(Collectors.toList());
        list= temp.stream().map(p -> {
            StringTokenizer tokenizer=new StringTokenizer(p," ");
            String tempString=tokenizer.nextToken();
            return    tempString;
        }).collect(Collectors.toList());
        return list;
    }

}


