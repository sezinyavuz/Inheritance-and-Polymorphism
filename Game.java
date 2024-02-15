import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static ArrayList < Square > squares = new ArrayList < Square > ();
    private static PropertyJsonReader reader1 = new PropertyJsonReader();
    private static ListJsonReader reader2 = new ListJsonReader();
    public static Banker banker = new Banker();
    public static Player player1 = new Player("1");
    public static Player player2 = new Player("2");
    public static void Start(String arg) {
        try (Scanner scan = new Scanner(new BufferedReader(new FileReader(arg)))) {
            FileWriter writer = new FileWriter("output.txt", true);
            outer: while (scan.hasNext()) {
                if (scan.next().toLowerCase().equals("player")) {
                    String[] inpt = scan.next().split(";");
                    Integer player = Integer.parseInt(inpt[0]);
                    Integer dice = Integer.parseInt(inpt[1]);
                    switch (player) {
                        case 1:
                            if (player1.getWaitLaps() == 0) {
                                player1.AddLocation(dice);
                                if (player1.getLocation() == 3 || player1.getLocation() == 18 || player1.getLocation() == 34) {
                                    String output = CommunityChest.DrawCommunityChest(player1, player2);
                                    if (output.equals("Game over")) {
                                        writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                            "\t" + player2.getMoney() + "\t" + "Player 1 goes bankrupt\n");
                                        break outer;
                                    } else {
                                        writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                            "\t" + player2.getMoney() + "\tPlayer 1 draw " + output + "\n");
                                    }
                                } else if (player1.getLocation() == 8 || player1.getLocation() == 23 || player1.getLocation() == 37) {
                                    String output = Chance.DrawChance(player1);
                                    if (output.equals("Game over")) {
                                        writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                            "\t" + player2.getMoney() + "\t" + "Player 1 goes bankrupt\n");
                                        break outer;
                                    } else {
                                        writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                            "\t" + player2.getMoney() + "\tPlayer1 draw " + output + "\n");
                                    }
                                } else {
                                    for (Square square: squares) {
                                        if (square.getId() == player1.getLocation()) {
                                            if (player1.getLocation() == 1 || player1.getLocation() == 5 || player1.getLocation() == 11 ||
                                                player1.getLocation() == 21 || player1.getLocation() == 31 || player1.getLocation() == 39) {
                                                SpecialPlace spclPlace = (SpecialPlace) square;
                                                spclPlace.specialTreat(player1);
                                                writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                                    "\t" + player2.getMoney() + "\t" + "Player1 " + spclPlace + "\n");
                                            } else {
                                                if (square.isBuyable()) {
                                                    if (player1.getMoney() >= square.getCost()) {
                                                        player1.AddOwnedLands(square);
                                                        square.setBuyable(false);
                                                        player1.SubMoney(square.getCost());
                                                        banker.AddMoney(square.getCost());
                                                        writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                                            "\t" + player2.getMoney() + "\t" + "Player1 bought " + square + "\n");
                                                    } else {
                                                        writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                                            "\t" + player2.getMoney() + "\t" + "Player 1 goes bankrupt\n");
                                                        break outer;
                                                    }
                                                } else {
                                                    if (player1.getMoney() >= square.calculateRent(dice)) {
                                                        if (player2.getOwnedLands().contains(square)) {
                                                            player1.SubMoney(square.calculateRent(dice));
                                                            player2.AddMoney(square.calculateRent(dice));
                                                            writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                                                "\t" + player2.getMoney() + "\t" + "Player 1 rent for " + square + "\n");
                                                        }
                                                        else{
                                                            writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                                                "\t" + player2.getMoney() + "\t" + "Player 1 has " + square + "\n");
                                                        }
                                                    } else {
                                                        writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                                            "\t" + player2.getMoney() + "\t" + "Player 1 goes bankrupt\n");
                                                        break outer;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                player1.SubWaitLaps();
                                writer.write("Player1\t" + dice + "\t" + player1.getLocation() + "\t" + player1.getMoney() +
                                    "\t" + player2.getMoney() + "\t" + "Player 1 in jail (count=" + (3 - player1.getWaitLaps()) + ")\n");
                            }
                            break;
                        case 2:
                            if (player2.getWaitLaps() == 0) {
                                player2.AddLocation(dice);
                                if (player2.getLocation() == 3 || player2.getLocation() == 18 || player2.getLocation() == 34) {
                                    String output = CommunityChest.DrawCommunityChest(player2, player1);
                                    if (output.equals("Game over")) {
                                        writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                            "\t" + player2.getMoney() + "\t" + "Player 2 goes bankrupt\n");
                                        break outer;
                                    } else {
                                        writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                            "\t" + player2.getMoney() + "\tPlayer2 draw " + output + "\n");
                                    }
                                } else if (player2.getLocation() == 8 || player2.getLocation() == 23 || player2.getLocation() == 37) {
                                    String output = Chance.DrawChance(player2);
                                    if (output.equals("Game over")) {
                                        writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                            "\t" + player2.getMoney() + "\t" + "Player 2 goes bankrupt\n");
                                        break outer;
                                    } else {
                                        writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                            "\t" + player2.getMoney() + "\tPlayer2 draw " + output + "\n");
                                    }
                                } else {
                                    for (Square square: squares) {
                                        if (square.getId() == player2.getLocation()) {
                                            if (player2.getLocation() == 1 || player2.getLocation() == 5 || player2.getLocation() == 11 ||
                                                player2.getLocation() == 21 || player2.getLocation() == 31 || player2.getLocation() == 39) {
                                                SpecialPlace spclPlace = (SpecialPlace) square;
                                                spclPlace.specialTreat(player2);
                                                writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                                    "\t" + player2.getMoney() + "\t" + "Player2 " + spclPlace + "\n");
                                            } else {
                                                if (square.isBuyable()) {
                                                    if (player2.getMoney() >= square.getCost()) {
                                                        player2.AddOwnedLands(square);
                                                        square.setBuyable(false);
                                                        player2.SubMoney(square.getCost());
                                                        banker.AddMoney(square.getCost());
                                                        writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                                            "\t" + player2.getMoney() + "\t" + "Player2 bought " + square + "\n");
                                                    } else {
                                                        writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                                            "\t" + player2.getMoney() + "\t" + "Player 2 goes bankrupt\n");
                                                        break outer;
                                                    }
                                                } else {
                                                    if (player2.getMoney() >= square.calculateRent(dice)) {
                                                        if (player1.getOwnedLands().contains(square)) {
                                                            player2.SubMoney(square.calculateRent(dice));
                                                            player1.AddMoney(square.calculateRent(dice));
                                                            writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player2.getMoney() +
                                                                "\t" + player2.getMoney() + "\t" + "Player 2 rent for " + square + "\n");
                                                        }
                                                        else{
                                                            writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player2.getMoney() +
                                                                "\t" + player2.getMoney() + "\t" + "Player 2 has " + square + "\n");
                                                        }
                                                    } else {
                                                        writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                                            "\t" + player2.getMoney() + "\t" + "Player 2 goes bankrupt\n");
                                                        break outer;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                player2.SubWaitLaps();
                                writer.write("Player2\t" + dice + "\t" + player2.getLocation() + "\t" + player1.getMoney() +
                                    "\t" + player2.getMoney() + "\t" + "Player 2 in jail (count=" + (3 - player2.getWaitLaps()) + ")\n");
                            }
                            break;
                        default:
                            break;
                    }
                    if(player1.getMoney() == 0 || player2.getMoney() == 0){
                        break outer;
            }
                } else {
                    writer.write("-----------------------------------------------------------------------------------------------------------\n");
                    writer.write("Player1\t" + player1.getMoney() + " have: ");
                    for (Square square: player1.getOwnedLands()) {
                        writer.write(square + " ");
                    }
                    writer.write("\nPlayer 2" + "\t" + player2.getMoney() + " have: ");
                    for (Square square: player2.getOwnedLands()) {
                        writer.write(square + " ");
                    }
                    writer.write("\nBanker\t" + banker.getMoney() + "\n");
                    if (player1.getMoney() > player2.getMoney()) {
                        writer.write("Winner Player 1\n");
                    } else if (player1.getMoney() < player2.getMoney()) {
                        writer.write("Winner\tPlayer 2\n");
                    } else {
                        writer.write("Draw\n");
                    }
                    writer.write("-----------------------------------------------------------------------------------------------------------\n");
                }
            }
            writer.write("-----------------------------------------------------------------------------------------------------------\n");
            writer.write("Player1\t" + player1.getMoney() + " have: ");
            for (Square square: player1.getOwnedLands()) {
                writer.write(square + " ");
            }
            writer.write("\nPlayer 2" + "\t" + player2.getMoney() + " have: ");
            for (Square square: player2.getOwnedLands()) {
                writer.write(square + " ");
            }
            writer.write("\nBanker\t" + banker.getMoney() + "\n");
            if (player1.getMoney() > player2.getMoney()) {
                writer.write("Winner Player 1\n");
            } else if (player1.getMoney() < player2.getMoney()) {
                writer.write("Winner\tPlayer 2\n");
            } else {
                writer.write("Draw\n");
            }
            writer.write("-----------------------------------------------------------------------------------------------------------\n");
            writer.close();
        } catch (IOException e) {

        }

    }
}