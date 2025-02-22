public class Pair <T>{
    private T x;
    private T y;

    public Pair(T X, T Y) {
        this.x = X;
        this.y = Y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public void setX(T x) {
        this.x = x;
    }

    public void setY(T y) {
        this.y = y;
    }
}
