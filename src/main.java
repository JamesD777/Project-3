//James Dunlap   Jcd160230
//Recursive Function Call: 201
//Recursive Function Definition: LinkedList Class
//Recursive Helper: Line 77 
//Recursive function: 86
import java.io.File;
import java.util.Scanner;
import LinkList.LinkedList;
import LinkList.DoubleLinkNode;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args)throws FileNotFoundException{
        LinkedList A1Available = new LinkedList();
        LinkedList A1Reserved = new LinkedList();
        try {
            File f = new File("A1.txt");//Look for file 1
            Scanner A1 = new Scanner(f);
            int r = 0;
            String line;
            while(A1.hasNextLine()){
                line = A1.nextLine();//get file line by line
		for(int i = 0; i < line.length();i++){//loop through line
                    if(line.charAt(i) == '#')//add node to the respective linked list
                        A1Available.addNode(r,i);
                    else
                        A1Reserved.addNode(r,i);
                }
                r++;
            }
            A1.close();
        } catch (FileNotFoundException e){System.out.println("File not found" + e);}//catch error if file not found
        
        LinkedList A2Available = new LinkedList();
        LinkedList A2Reserved = new LinkedList();
        try {
            File f2 = new File("A2.txt");//Check for file 2
            Scanner A2 = new Scanner(f2);
            int row = 0;
            while(A2.hasNextLine()){
                String line = A2.nextLine();//Get file line by line
		for(int i = 0; i < line.length();i++){//loop through line
                    if(line.charAt(i) == '#')//add node to each respective linked list
                        A2Available.addNode(row,i);
                    else
                        A2Reserved.addNode(row,i);
                }
                row++;
            }
            A2.close();
        } catch (FileNotFoundException e){System.out.println("File not found" + e);}//catch error if file is missing
        
        LinkedList A3Available = new LinkedList();
        LinkedList A3Reserved = new LinkedList();
        try {
            File f3 = new File("A3.txt");
            Scanner A3 = new Scanner(f3);//check for file 3
            int row = 0;
            while(A3.hasNextLine()){
                String line = A3.nextLine();//get file line by line
		for(int i = 0; i < line.length();i++){//loop through each line
                    if(line.charAt(i) == '#')//add each node to it's respective linked list
                        A3Available.addNode(row,i);
                    else
                        A3Reserved.addNode(row,i);
                }
                row++;
            }
            A3.close();
        } catch (FileNotFoundException e){System.out.println("File not found" + e);}//catch error if file not found
        
        Scanner input = new Scanner(System.in);
        
        LinkedList a = null;
        LinkedList r = null;
        boolean loop;
        boolean loop2;
        
        loop = true;
        while(loop){//main menu, loop till user wants to exit
            PrintWriter p = null;
            System.out.println("\n1) Reserve Seats \n" + "2) View Auditorium \n" + "3) Exit");
            if(input.hasNextInt()){
                switch(input.nextInt()){
                    case 1://if user wants to reserve
                        loop2 = true;
                        while(loop2){
                            System.out.println("Which auditorium would you like to reserve in?");
                            if(!input.hasNextInt()){
                                input.next();
                                continue;
                            }
                                switch(input.nextInt()){//set auditorium to the null lists for easy use along with the printwriter
                                    case 1:
                                        a = A1Available;
                                        r = A1Reserved;
                                        loop2 = false;
                                        p = new PrintWriter("A1.txt");
                                        break;
                                    case 2:
                                        a = A2Available;
                                        r = A2Reserved;
                                        loop2 = false;
                                        p = new PrintWriter("A2.txt");
                                        break;
                                    case 3:
                                        a = A3Available;
                                        r = A3Reserved;
                                        loop2 = false;
                                        p = new PrintWriter("A3.txt");
                                        break;
                                    default:
                                        System.out.println("Invalid input");

                                }
                        }
                        display(a,r);
                        int row = 0;
                        int seat = 0;
                        int n = 0;

                        loop2 = true;
                        while(loop2){
                            System.out.println("\nWhich row do you want?");
                            if(!input.hasNextInt()){
                                input.next();
                                continue;
                            }
                            row = input.nextInt() - 1;
                            if(r.getTail() == null){
                                if(row >= 0 && row <= a.getTail().getRow())
                                    loop2 = false;
                            }
                            else if(row >= 0 && (row <= r.getTail().getRow() || row <= a.getTail().getRow()))//loop till row is valid
                                loop2 = false;
                        }
                        loop2 = true;
                        while(loop2){
                            System.out.println("Which seat do you want?");
                            if(!input.hasNextInt()){
                                input.next();
                                continue;
                            }
                            seat = input.nextInt() - 1;
                            if(r.getTail() == null){
                                if(seat >= 0 && seat <= a.getTail().getSeat())
                                    loop2 = false;
                            }
                            else if(seat >= 0 &&( seat <= r.getTail().getSeat() || seat <= a.getTail().getSeat()))//loop till seat is valid
                                loop2 = false;
                        }
                        loop2 = true;
                        while(loop2){
                            System.out.println("How many seats do you want to reserve?");
                            if(!input.hasNextInt()){
                                input.next();
                                continue;
                            }
                            n = input.nextInt();
                            if(n >= 0)//loop till seat amount is 0 or greater
                                loop2 = false;
                        }
                        if(a.getTail() == null){
                            System.out.println("No Seats Available");
                            loop = false;
                            break;
                        }
                        else if(checkA(row,seat,n,a)){//check if seats asked for are available
                            reserve(row,seat,n,r,a);//reserve asked for seats
                            System.out.println("\nSeats Reserved!\n");
                        }
                        else{//if seats are full, find best open ones
                            int[] best = findBest(n, r, a);//get best seat option
                            int bR = best[0];
                            int bS = best[1];
                            if(bR == 9999999 || bS == 9999999){//if no options are available
                                System.out.println("Seats cannot be found");
                                break;
                            }
                            loop2 = true;
                            while(loop2){
                                System.out.println("Would you like row: " + (bR + 1) + " seat: " + (bS + 1) + " ?  Y/N?");//loop till valid answer
                                char y = input.next().charAt(0);
                                if(y == 'Y' || y == 'y'){//if they want the seat, reserve
                                    reserve(bR,bS,n,r,a);
                                    System.out.println("Seats reserved");
                                    loop2 = false;
                                }
                                else if(y == 'N' || y == 'n'){//if they dont want the seat, dont reserve
                                    System.out.println("Seats not reserved");
                                    loop2 = false;
                                }
                            }
                        }
                        r.print(p, a, r);//recursive print and update the files
                        p.close();//close printwriter
                        break;

                    case 2://if user wants to display the auditorium
                        loop2 = true;
                        while(loop2){
                            System.out.println("Which auditorium would you like to see?");
                            if(!input.hasNextInt()){
                                input.next();
                                continue;
                            }
                                switch(input.nextInt()){
                                    case 1:
                                        a = A1Available;
                                        r = A1Reserved;
                                        loop2 = false;
                                        break;
                                    case 2:
                                        a = A2Available;
                                        r = A2Reserved;
                                        loop2 = false;
                                        break;
                                    case 3:
                                        a = A3Available;
                                        r = A3Reserved;
                                        loop2 = false;
                                        break;
                                    default:
                                        System.out.println("Invalid input");

                                }
                        }
                        display(a, r);//display the auditorium
                        break;
                    case 3:
                        loop = false;
                        break;
                    default:
                        System.out.println("Incorrect input");
                        break;
                }
            }
            else{
                input.next();
            }
        }
        //Print 4 lines displaying columns of the names, seats reserved, seats open, and total ticket sales for each auditorium and the total overall.
        System.out.print("\nAuditorium:  Reserved Open  Sales");
        int size1 = size(A1Reserved, A1Available);
        int size2 = size(A2Reserved, A2Available);
        int size3 = size(A3Reserved, A3Available);
        int r1 = 0;
        DoubleLinkNode cur = A1Reserved.getHead();
        while(cur != null){//get the reserved 1 count 
            r1++;
            cur = cur.getNext();
        }
        int r2 = 0;
        cur = A2Reserved.getHead();
        while(cur != null){//get the reserved 2 count 
            r2++;
            cur = cur.getNext();
        }
        int r3 = 0;
        cur = A3Reserved.getHead();
        while(cur != null){//get the reserved 3 count 
            r3++;
            cur = cur.getNext();
        }
        System.out.print("\nAuditorium 1: " + r1 + "       " + (size1 - r1) + "    $" + (r1 * 7) + "   ");
        System.out.print("\nAuditorium 2: " + r2 + "       " + (size2 - r2) + "    $" + (r2 * 7) + "   ");
        System.out.print("\nAuditorium 3: " + r3 + "       " + (size3 - r3) + "    $" + (r3 * 7) + "   ");

        System.out.print("\nTotal:        " + (r1+r2+r3) + "      " + ((size3 - r3)+(size2 - r2)+(size1 - r1)) + "   $" + (r1+r2+r3)*7 + "   ");

    }
    public static int size(LinkedList a, LinkedList b){//calculate the size of the auditorium
        int row, seat;
        
        if(a.getTail().getSeat() > b.getTail().getSeat())
            seat = a.getTail().getSeat();
        else
            seat = b.getTail().getSeat();
        if(a.getTail().getRow() > b.getTail().getRow())
            row = a.getTail().getRow();
        else
            row = b.getTail().getRow();
        return (row+1) * (seat+1);
    }
    
    public static void display(LinkedList a, LinkedList r){//display the auditorium to the user in console
        System.out.print("  ");
        int s;
        if(r.getTail() == null){//if it was a full file of available, only use the available tail
            s = a.getTail().getSeat()+1;
        }
        else if(a.getTail().getSeat() > r.getTail().getSeat())
            s = a.getTail().getSeat()+1;
        else
            s = r.getTail().getSeat()+1;

        for(int i=1; i<=s; i++)//print out top numbers
                System.out.print(i % 10 + " ");
        System.out.print('\n' + "1 ");
        DoubleLinkNode cura = a.getHead();
        DoubleLinkNode curr = r.getHead();
        
        int row = 0;
        int count = 0;
        while(cura != null && curr != null){//while both currs arent at the tail, loop through the two linked lists and print the data
            if(cura.getSeat() == count && cura.getRow() == row){
                System.out.print("# ");
                cura = cura.getNext();
            }
            else{
                System.out.print(". ");
                curr = curr.getNext();
            }
            count++;//increment count each time a . or # is printed
            if(count == s){//at the end of each line, print a new line and the row number
                count -= s;
                row++;
                System.out.print("\n" + (row+1) + " ");}
        }
        while(cura != null){//deals when there is a row of available at the end
            if(count == s){
                count -= s;
                row++;
                System.out.print("\n" + (row+1) + " ");}
            if(cura.getSeat() == count && cura.getRow() == row){
                System.out.print("# ");
                cura = cura.getNext();
            }
            count++;
        }
        while(curr != null){//deals when there is a row of reserved at the end
            if(count == s){
                count -= s;
                row++;
                System.out.print("\n" + (row+1) + " ");}
            if(curr.getSeat() == count && curr.getRow() == row){
                System.out.print(". ");
                curr = curr.getNext();
            }
            count++;
        }
        if(cura == null && curr != null)//gets the last one
            System.out.print('.' + "\n\n");
        if(cura != null && curr == null)
            System.out.print('#' +"\n\n");
    }

    
    public static int[] findBest(int n, LinkedList r, LinkedList a){//calculates the best seat available and returns the row and seat in an array
        int mSeat;
        int mRow;//calculate middle row and middle seat
        if(a.getTail().getSeat() > r.getTail().getSeat())
            mSeat = a.getTail().getSeat() / 2;
        else
            mSeat = r.getTail().getSeat() / 2;
        if(a.getTail().getRow() > r.getTail().getRow())
            mRow = a.getTail().getRow() / 2;
        else
            mRow = r.getTail().getRow() / 2;
        
        int bR = 9999999;//default best seat values for if there are no seats in the auditorium to fit the criteria
        int bS = 9999999;
        DoubleLinkNode cur = a.getHead();
        while(cur.getNext() != null){//loop through auditorium and find best row and seat using distance formula
            if((Math.sqrt(Math.pow(Math.abs((double)bR - (double)mRow),2) + Math.pow(Math.abs((double)bS - (double)mSeat),2))) > (Math.sqrt(Math.pow(Math.abs((double)cur.getRow() - (double)mRow),2) + Math.pow(Math.abs((double)cur.getSeat() - (double)mSeat),2)))){
                if(checkA(cur.getRow(),cur.getSeat(),n,a)){
                    bR = cur.getRow();
                    bS = cur.getSeat();
                }
            }
            else if((Math.sqrt(Math.pow(Math.abs((double)bR - (double)mRow),2) + Math.pow(Math.abs((double)bS - (double)mSeat),2))) == (Math.sqrt(Math.pow(Math.abs((double)cur.getRow() - (double)mRow),2) + Math.pow(Math.abs((double)cur.getSeat() - (double)mSeat),2)))){
                if(cur.getRow() == mRow){    //if the distance is equal, find which is on a row closer to the middle
                    if(checkA(cur.getRow(),cur.getSeat(),n,a)){
                        bR = cur.getRow();
                        bS = cur.getSeat();
                    }
                }
                else if(Math.abs(mRow - cur.getRow()) < Math.abs(mRow - bR)){
                    if(checkA(cur.getRow(),cur.getSeat(),n,a)){
                        bR = cur.getRow();
                        bS = cur.getSeat();
                    }
                }
            }
            cur = cur.getNext();
        }
        return new int[] {bR, bS};
    }
    
    public static void reserve(int r, int s, int n, LinkedList R, LinkedList A){//reserve seats using addnode and deletenode
        for(int i = 0; i < n; i++){
            R.addNode(r, s + i);
            A.deleteNode(r, s + i);
        }
    }
    
    public static boolean checkA(int r, int s, int n, LinkedList A){//check if seats are available to reserve
        DoubleLinkNode cur = A.getHead();
        
        while(cur != null){//loop through available linked list
            if(cur.getRow() == r && cur.getSeat() == s){//if at the matching seat and row
                for(int i = 0;i < n;i++){
                    if(cur == null || cur.getRow() != r || cur.getSeat() != s + i)//check the correct number of seats after to verify availability
                        return false;
                    cur = cur.getNext();
                }
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }
}
