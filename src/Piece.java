public abstract class Piece {
    private String name;
    private String position;
    private String color;

    public String getName(){return name;}
    public String getPosition(){return position;}
    public String getColor(){return color;}

    public void setName(String n){
        name = n;
    }
    public void setPosition(String p){
        position = p;
    }
    public void setColor(String c){
        color = c;
    }

    public abstract String move();
}

void main() {
}
