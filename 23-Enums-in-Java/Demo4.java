public class Demo4 {
    public static void main(String[] args) {
        // Direction2 eastDirection = new Direction2() {
        // @Override
        // public void move() {
        // System.out.println("Move Right (x+1)");
        // }
        // };
        // Direction2 northDirection = new Direction2() {
        // @Override
        // public void move() {
        // System.out.println("Move Forward (y+1)");
        // }
        // };
        // northDirection.move();
        // eastDirection.move();

        Direction d = Direction.SOUTH;
        d.move();
    }
}

enum Direction {
    NORTH {
        @Override
        public void move() {
            System.out.println("Move Forward (y+1)");
        }
    },
    SOUTH{
        @Override
        public void move() {
            System.out.println("Move Backward (y-1)");
        }
    },
    EAST{
        @Override
        public void move() {
            System.out.println("Move Right (x+1)");
        }
    },
    WEST{
        @Override
        public void move() {
            System.out.println("Move Forward (x-1)");
        }
    };

    abstract public void move();
}

// abstract class Direction2 extends Enum<Direction2>{
// abstract public void move();
// }