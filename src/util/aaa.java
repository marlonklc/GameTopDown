package util;

import java.util.Scanner;

public class aaa
{
    public static String[] board = new String[]{"1","2","3","4","5","6","7","8","9"};
    public static Scanner input;
    public static String playerSymbol;
    public static boolean gameOver = false;
    public static boolean playersTurn = false;
    public static boolean tie = false;

    public static void main(String args[])
    {

        //Introduction
        intro();

        //Player to pick their symbol (x or o)
        input = new Scanner(System.in);
        String choice = "";
        while(!choice.equalsIgnoreCase("X") && !choice.equalsIgnoreCase("O"))
        {
            System.out.print("Choose your symbol (X or O): ");
            choice = input.next();
            if(!choice.equalsIgnoreCase("X") && !choice.equalsIgnoreCase("O"))
            {
                System.out.println("Invalid input, please try again!");
            }
        }

        //X goes first whether it is the player or computer
        if(choice.equalsIgnoreCase("x"))
        {
            playersTurn = true;
        }
        playerSymbol = choice.toUpperCase();
        //if player turn
        while(gameOver == false)
        {
            if(playersTurn)
            {
                PTurn();
                if(over()) {break;}
            }
            else
            {
                CTurn();
                if(over()) {break;}
            }
            //check for win
        }
        displayBoard();
        if(!tie)
        {
            if(!playersTurn)
            {
                System.out.println("You are the winner!");
            }
            else
            {
                System.out.println("You lost!");
            }
        }
        else
        {
            System.out.println("The match is a tie!");
        }
        //repeat

        input.close();
    }

    public static boolean over()
    {
        int[] checks = new int[] {0,1,2,
                3,4,5,
                6,7,8,
                0,3,6,
                1,4,7,
                2,5,8,
                0,4,8,
                6,4,2};
        for(int i = 0; i < 24; i += 3)
        {
            if(board[checks[i]].equals(board[checks[i+1]]) && board[checks[i + 1]].equals(board[checks[i + 2]]))
            {
                return true;
            }
        }

        int flags = 0;
        for(int i = 0; i < 9; i++)
        {
            if(board[i].equalsIgnoreCase("X") || board[i].equalsIgnoreCase("O"))
            {
                flags++;
            }
        }
        if(flags == 9)
        {
            tie = true;
            return true;
        }

        return false;
    }

    public static void PTurn()
    {
        displayBoard();
        boolean goodInput = false;

        while(!goodInput)
        {
            System.out.print("Please choose a location on the board (1-9): ");
            String numberInput = input.next();
            int number = 0;
            try
            {
                number = Integer.parseInt(numberInput);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Non-number entered!");
                continue;
            }

            if(number < 1 || number > 9)
            {
                System.out.println("Number not between 1 and 9!");
                continue;
            }

            if(board[number - 1].equalsIgnoreCase("X") || board[number -1].equalsIgnoreCase("O"))
            {
                System.out.println("Spot already taken!");
                continue;
            }
            board[number - 1] = playerSymbol;
            goodInput = true;
        }
        playersTurn = false;
    }

    public static void CTurn()
    {
        boolean validLocation = false;

        int[] checks = new int[] {0,1,2,
                3,4,5,
                6,7,8,
                0,3,6,
                1,4,7,
                2,5,8,
                0,4,8,
                6,4,2};

        for(int i = 0; i < 24; i += 3)
        {
            int flags = 0;
            for(int j = 0; j < 3; j++)
            {
                if(board[checks[i + j]].equals(playerSymbol))
                {
                    flags++;
                }
            }
            if(flags == 2)
            {
                for(int j = 0; j < 3; j++)
                {
                    if(!board[checks[i + j]].equals(playerSymbol) && !board[checks[i + j]].equals(playerSymbol.equalsIgnoreCase("X") ? "O" : "X"))
                    {
                        board[checks[i+j]] = playerSymbol.equalsIgnoreCase("X") ? "O" : "X";
                        validLocation = true;
                        break;
                    }
                }
                break;
            }
        }

        while(!validLocation)
        {
            int location = (int)(Math.random() * 9);
            if(!board[location ].equalsIgnoreCase("X") && !board[location ].equalsIgnoreCase("O"))
            {
                board[location] = playerSymbol.equalsIgnoreCase("X") ? "O" : "X";
                validLocation = true;
            }
        }
        playersTurn = true;
    }

    public static void intro()
    {
        System.out.println("****Welcome to Tic-Tac-Toe!****");
        System.out.println("*********By Majoolwip**********");
    }

    public static void displayBoard()
    {
        System.out.println(board[0] + "|" + board[1] + "|" + board[2]);
        System.out.println("------");
        System.out.println(board[3] + "|" + board[4] + "|" + board[5]);
        System.out.println("------");
        System.out.println(board[6] + "|" + board[7] + "|" + board[8]);
    }
}
