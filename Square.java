import java.util.ArrayList;

public abstract class Square {
    private Integer id;
    private String name;
    private Integer cost;
    private boolean Buyable;
    public Square(Integer id, String name, Integer cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }
    public boolean isBuyable() {
        return Buyable;
    }
    public void setBuyable(boolean buyable) {
        Buyable = buyable;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Integer getCost() {
        return cost;
    }
    public abstract Integer calculateRent(Integer amount);
    public String toString() {
        return this.getName();
    }
}
class Land extends Square {
    public Land(Integer id, String name, Integer cost) {
        super(id, name, cost);
        this.setBuyable(true);
    }
    @Override
    public Integer calculateRent(Integer amount) {
        if (this.getCost() < 2001) {
            return (int) Math.round(this.getCost() * 40 / 100);
        } else if (this.getCost() < 3001) {
            return (int) Math.round(this.getCost() * 30 / 100);
        } else {
            return (int) Math.round(this.getCost() * 35 / 100);
        }
    }
}
class Company extends Square {
    public Company(Integer id, String name, Integer cost) {
        super(id, name, cost);
        this.setBuyable(true);
    }
    @Override
    public Integer calculateRent(Integer amount) {
        return this.getCost() * 4 * amount;
    }
}
class Railroad extends Square {

    public Railroad(Integer id, String name, Integer cost) {
        super(id, name, cost);
        this.setBuyable(true);
    }

    @Override
    public Integer calculateRent(Integer amount) {
        return 25 * amount;
    }

}
class SpecialPlace extends Square {
    public SpecialPlace(Integer id, String name) {
        super(id, name, 0);
        this.setBuyable(false);
    }
    @Override
    public Integer calculateRent(Integer amount) {
        return 0;
    }
    public void specialTreat(Player player) {
        switch (this.getId()) {
            case 1:
                break;
            case 5:
                player.SubMoney(100);
                Game.banker.AddMoney(100);
                break;
            case 11:
                player.AddWaitLaps(3);
                break;
            case 21:
                player.AddWaitLaps(1);
                break;
            case 31:
                player.setLocation(11);
                player.AddWaitLaps(3);
                break;
            case 39:
                player.SubMoney(100);
                Game.banker.AddMoney(100);
                break;
            default:
                break;
        }
    }
}
class Chance {
    public static ArrayList < String > Chancelist = new ArrayList < String > ();
    private static Integer count = 0;
    public static String DrawChance(Player player) {
        switch (count) {
            case 0:
                player.setLocation(1);
                player.AddMoney(200);
                count++;
                Game.banker.SubMoney(200);
                return Chancelist.get(0);
            case 1:
                count++;
                if(player.getLocation() > 27){
                    player.AddMoney(200);
                }
                player.setLocation(27);
                for (Square square: Game.squares) {
                    if (square.getId() == 27 && player.getMoney() >= square.getCost() && square.isBuyable()) {
                        player.AddOwnedLands(square);
                        player.SubMoney(square.getCost());
                        Game.banker.AddMoney(square.getCost());
                        square.setBuyable(false);
                        if(player.getName().equals("1")){
                            return Chancelist.get(1) + " " + player +" bought " + square;
                        }
                        else{
                            return Chancelist.get(1) + " " + player +" bought " + square;
                        }
                    }
                    else if(square.getId() == 27 && player.getMoney() < square.getCost() && square.isBuyable()){
                        return "Game over";
                    }
                    else if(square.getId() == 27 && player.getMoney() >= square.getCost() && !(square.isBuyable())){
                        if(player.getName().equals("1")){
                            if(Game.player1.getOwnedLands().contains(square)){
                                return Chancelist.get(1) + " " + player + " has Leicester Square";
                            }
                            else{
                                Game.player1.SubMoney(square.calculateRent(1));
                                Game.player2.AddMoney(square.calculateRent(1));
                                return Chancelist.get(1) + " " + player + " paid rent for Leicester Square";
                            }
                            
                        }
                        else{
                            if(Game.player2.getOwnedLands().contains(square)){
                                return Chancelist.get(1) + " " + player + " has Leicester Square";
                            }
                            else{
                                Game.player2.SubMoney(square.calculateRent(1));
                                Game.player1.AddMoney(square.calculateRent(1));
                                return Chancelist.get(1) + " " + player + " paid rent for Leicester Square";
                            }
                        }
                    }
                }
                return Chancelist.get(1);
            case 2:
                count++;
                player.SubLocation(3);
                for (Square square: Game.squares) {
                    if (square.getId() == player.getLocation() && player.getMoney() >= square.getCost() && square.isBuyable()) {
                        player.AddOwnedLands(square);
                        player.SubMoney(square.getCost());
                        Game.banker.AddMoney(square.getCost());
                        square.setBuyable(false);
                        if(player.getName().equals("1")){
                            return Chancelist.get(2) + " " + player +" bought " + square;
                        }
                        else{
                            return Chancelist.get(2) + " " + player +" bought " + square;
                        }
                    }
                    else if(square.getId() == player.getLocation() && player.getMoney() < square.getCost() && square.isBuyable()){
                        return "Game over";
                    }
                    else if(square.getId() == player.getLocation() && player.getMoney() >= square.getCost() && !(square.isBuyable())){
                        if(player.getName().equals("1")){
                            if(Game.player1.getOwnedLands().contains(square)){
                                return Chancelist.get(2) + " " + player + " has " + square;
                            }
                            else{
                                Game.player1.SubMoney(square.calculateRent(1));
                                Game.player2.AddMoney(square.calculateRent(1));
                                return Chancelist.get(2) + " " + player + " paid rent for " + square;
                            }
                            
                        }
                        else{
                            if(Game.player2.getOwnedLands().contains(square)){
                                return Chancelist.get(2) + " " + player + " has " + square;
                            }
                            else{
                                Game.player2.SubMoney(square.calculateRent(1));
                                Game.player1.AddMoney(square.calculateRent(1));
                                return Chancelist.get(2) + " " + player + " paid rent for " + square;
                            }
                        }
                    }
                }
                return Chancelist.get(2);
            case 3:
                player.SubMoney(15);
                if(player.getMoney() < 0){
                    return "Game over";
                }
                Game.banker.AddMoney(15);
                count++;
                return Chancelist.get(3);
            case 4:
                player.AddMoney(150);
                Game.banker.SubMoney(150);
                count++;
                return Chancelist.get(4);
            case 5:
                player.AddMoney(100);
                Game.banker.SubMoney(100);
                count++;
                return Chancelist.get(5);
            default:
                count = 0;
                return Chance.DrawChance(player);
        }
    }
}
class CommunityChest {
    public static ArrayList < String > CommunityChests = new ArrayList < String > ();
    public static Integer count = 0;
    public static String DrawCommunityChest(Player player1, Player player2) {
        switch (count) {
            case 0:
                player1.setLocation(1);
                player1.AddMoney(200);
                Game.banker.SubMoney(200);
                count++;
                return CommunityChests.get(0);
            case 1:
                player1.AddMoney(75);
                Game.banker.SubMoney(75);
                count++;
                return CommunityChests.get(1);
            case 2:
                player1.SubMoney(50);
                if(player1.getMoney() < 0){
                    return "Game over";
                }
                Game.banker.AddMoney(50);
                count++;
                return CommunityChests.get(2);
            case 3:
                player1.AddMoney(10);
                player2.SubMoney(10);
                if(player2.getMoney() < 0){
                    return "Game over";
                }
                count++;
                return CommunityChests.get(3);
            case 4:
                player1.AddMoney(50);
                player2.SubMoney(50);
                if(player2.getMoney() < 0){
                    return "Game over";
                }
                count++;
                return CommunityChests.get(4);
            case 5:
                player1.AddMoney(20);
                Game.banker.SubMoney(20);
                count++;
                return CommunityChests.get(5);
            case 6:
                player1.AddMoney(100);
                Game.banker.SubMoney(100);
                count++;
                return CommunityChests.get(6);
            case 7:
                player1.SubMoney(100);
                if(player1.getMoney() < 0){
                    return "Game over";
                }
                Game.banker.AddMoney(100);
                count++;
                return CommunityChests.get(7);
            case 8:
                player1.SubMoney(50);
                if(player1.getMoney() < 0){
                    return "Game over";
                }
                Game.banker.AddMoney(50);
                count++;
                return CommunityChests.get(8);
            case 9:
                player1.AddMoney(100);
                Game.banker.SubMoney(100);
                count++;
                return CommunityChests.get(9);
            case 10:
                player1.AddMoney(50);
                Game.banker.SubMoney(50);
                count++;
                return CommunityChests.get(10);
            default:
                count = 0;
                return DrawCommunityChest(player1, player2);
        }
    }
}