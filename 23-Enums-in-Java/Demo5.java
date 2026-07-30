public class Demo5 {
    public static void main(String[] args) {
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            System.out.println(direction.name());
            // System.out.println(direction.toString());
            // System.out.println(direction);
        }

        Direction d = Direction.valueOf("EAST");

        System.out.println(d);
        System.out.println(d.ordinal()); // 2
    }
}

enum Direction {
NORTH,
SOUTH,
EAST,
WEST;
}

// final class Direction extends Enum<Direction>{
//     public static final Direction NORTH = new Direction();
//     public static final Direction SOUTH = new Direction();
//     public static final Direction EAST = new Direction();
//     public static final Direction WEST = new Direction();

//     // added by compiler internally
//     private static final Direction[] $VALUES = { NORTH, SOUTH, EAST, WEST };
    
//     public static Direction[] values() {
//         return $VALUES.clone();
//     }

//     public static Direction valueOf(String name){
//         super.valueOf(Direction.class(),name);
//     }
// }