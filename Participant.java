import java.util.ArrayList;

public abstract class Participant {
    private String name;
    private Integer money;
    private ArrayList < Square > ownedLands = new ArrayList < Square > ();
    private Integer waitLaps = 0;   
    public Integer getWaitLaps() {
        return waitLaps;
    }
    public void AddWaitLaps(Integer waitLaps) {
        this.waitLaps += waitLaps;
    }
    public void SubWaitLaps() {
        this.waitLaps--;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getMoney() {
        return money;
    }
    public void SubMoney(Integer money) {
        this.money -= money;
    }
    public void AddMoney(Integer money) {
        this.money += money;
    }
    public void SetMoney(Integer money){
        this.money = money;
    }
    public ArrayList < Square > getOwnedLands() {
        return ownedLands;
    }
    public void AddOwnedLands(Square ownedLands) {
        this.ownedLands.add(ownedLands);
    }
    public void SubOwnedLands(Square ownedLands) {
        this.ownedLands.remove(ownedLands);
    }
    public void setOwnedLands(ArrayList<Square> squares){
        this.ownedLands = squares;
    }
    public String toString(){
        return "player "+ this.name;
    }
}
class Player extends Participant {
    private Integer location;
    
    public Player(String name) {
        this.setName(name);
        this.SetMoney(15000);
        this.location = 1;
    }
    public Integer getLocation() {
        return location;
    }
    public void SubLocation(Integer location) {
        if(this.location - location <= 0){
            this.location -= location;
            this.location += 40;
        }
        else{
            this.location -= location;
        }       
    }
    public void AddLocation(Integer location) {
        if(this.location + location > 40){
            this.location += location;
            this.location -= 40;
            this.AddMoney(200);
            Game.banker.SubMoney(200);
        }
        else{
            this.location += location;
        }
    }
    public void setLocation(Integer location) {
        this.location = location;
    }
}
class Banker extends Participant{
    public Banker() {
        this.setName("Banker");
        this.SetMoney(100000);      
    }    
}