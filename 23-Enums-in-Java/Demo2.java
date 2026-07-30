public class Demo2 {
    public static void main(String[] args) {
        Direction east = Direction.EAST;

        System.out.println(east);
    }
}

// all are markers, objects of the Direction class
enum Direction {
NORTH,
SOUTH,
EAST,
WEST
}

// final class Direction2 extends Enum<Direction2>{
//     public static final Direction2 NORTH = new Direction2();
//     public static final Direction2 SOUTH = new Direction2();
//     public static final Direction2 EAST = new Direction2();
//     public static final Direction2 WEST = new Direction2();
// }