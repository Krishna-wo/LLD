public class Main {
    public static void main(String[] args) {
        CustomHashMap<String,Integer> map = new CustomHashMap<>();
        map.put("Krishan",5);
        map.put("Rahul",7);
        map.put("Daniel",3);
        map.put("Katya",4);
        map.put("Jeremy",2);
        map.put("Jeremy",1);
        System.out.println(map.get("Katya"));
        System.out.println(map.get("Daniel"));
        CustomHashMap<Integer,String> map2 = new CustomHashMap<>();
        map2.put(1,"Lily");
        map2.put(2,"Jeremy");
        map2.put(3,"Jenifer");
        System.out.println(map2.get(1));
        System.out.println(map2.get(3));

    }
}
