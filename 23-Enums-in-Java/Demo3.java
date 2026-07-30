public class Demo3 {
    public static void main(String[] args) {
        Direction d = Direction.SOUTH;

        System.out.println(d.getDegree());
    }
}

enum Direction {
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270);

    private int degree;

    private Direction(int degree) {
        this.degree = degree;
    }

    public int getDegree() {
        return this.degree;
    }
}

// final class Direction2 extends Enum<Direction2>{
//     public static final Direction2 NORTH = new Direction2(0);
//     public static final Direction2 SOUTH = new Direction2(180);
//     public static final Direction2 EAST = new Direction2(90);
//     public static final Direction2 WEST = new Direction2(270);

//     private int degree;

//     private Direction2(int degree) {
//         this.degree = degree;
//     }

//     public int getDegree() {
//         return this.degree;
//     }
// }