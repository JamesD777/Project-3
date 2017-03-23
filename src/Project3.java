import java.io.File;
import java.util.Scanner;
import LinkedList.LinkedList;
import LinkedList.DoubleLinkNode;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author jcd4912
 */
public class Project3 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args)throws FileNotFoundException{
        LinkedList A1Available = new LinkedList();
        LinkedList A1Reserved = new LinkedList();
        //looks for first file and reads it
        try {
            File f = new File("A1.txt");
            Scanner A1 = new Scanner(f);
            int r = 0;
            String line;
            while(A1.hasNextLine()){
                line = A1.nextLine();
		for(int i = 0; i < line.length();i++){
                    if(line.charAt(i) == '#')
                        A1Available.addNode(r,i);
                    else
                        A1Reserved.addNode(r,i);
                }
                r++;
            }
            A1.close();
        } catch (FileNotFoundException e){System.out.println("File not found" + e);}
        
        LinkedList A2Available = new LinkedList();
        LinkedList A2Reserved = new LinkedList();

        try {
            File f2 = new File("A2.txt");
            Scanner A2 = new Scanner(f2);
            int row = 0;
            while(A2.hasNextLine()){
                String line = A2.nextLine();
		for(int i = 0; i < line.length();i++){
                    if(line.charAt(i) == '#')
                        A2Available.addNode(row,i);
                    else
                        A2Reserved.addNode(row,i);
                }
                row++;
            }
            A2.close();
        } catch (FileNotFoundException e){System.out.println("File not found" + e);}
        
        LinkedList A3Available = new LinkedList();
        LinkedList A3Reserved = new LinkedList();
        
        try {
            File f3 = new File("A3.txt");
            Scanner A3 = new Scanner(f3);
            int row = 0;
            while(A3.hasNextLine()){
                String line = A3.nextLine();
		for(int i = 0; i < line.length();i++){
                    if(line.charAt(i) == '#')
                        A3Available.addNode(row,i);
                    else
                        A3Reserved.addNode(row,i);
                }
                row++;
            }
            A3.close();
        } catch (FileNotFoundException e){System.out.println("File not found" + e);}
        
        Scanner input = new Scanner(System.in);
        
        LinkedList a = null;
        LinkedList r = null;
        boolean loop;
        Boolean loop2;
        
        loop = true;
        while(loop){
            PrintWriter p = null;
            System.out.println("\n1) Reserve Seats \n" + "2) View Auditorium \n" + "3) Exit");
            switch(input.nextInt()){
                case 1:
                    loop2 = true;
                    while(loop2){
                        System.out.println("Which auditorium would you like to reserve in?");
                            switch(input.nextInt()){
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
                    int row = 0;
                    int seat = 0;
                    int n = 0;

                    loop2 = true;
                    while(loop2){
                        System.out.println("Which row do you want?");
                        row = input.nextInt() - 1;
                        if(row >= 0 && (row <= r.getTail().getRow() || row <= a.getTail().getRow()))
                            loop2 = false;
                    }
                    loop2 = true;
                    while(loop2){
                        System.out.println("Which seat do you want?");
                        seat = input.nextInt() - 1;
                        if(seat >= 0 &&( seat < r.getTail().getSeat() || seat <= a.getTail().getSeat()))
                            loop2 = false;
                    }
                    loop2 = true;
                    while(loop2){
                        System.out.println("How many seats do you want to reserve?");
                        n = input.nextInt();
                        if(n >= 0)
                            loop2 = false;
                    }

                    if(checkA(row,seat,n,a)){
                        reserve(row,seat,n,r,a);
                    }
                    else{
                        int[] best = findBest(n, r, a);
                        int bR = best[0];
                        int bS = best[1];
                        if(bR == 9999999 || bS == 9999999){
                            System.out.println("Seats cannot be found");
                            break;
                        }
                        System.out.println("Would you like row: " + (bR + 1) + " seat: " + (bS + 1) + " ?");
                        char y = input.next().charAt(0);
                        if(y == 'Y' || y == 'y'){
                            reserve(bR,bS,n,r,a);
                            System.out.println("Seats reserved");
                        }
                        else
                            System.out.println("Seats NOT reserved");
                    }
                    r.print(p, a, r);
                    p.close();

                    break;

                case 2:
                    loop2 = true;
                    while(loop2){
                        System.out.println("Which auditorium would you like to see?");
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
                    display(a, r);
                    break;
                case 3:
                    loop = false;
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }
        }
        //Print 4 lines displaying columns of the names, seats reserved, seats open, and total ticket sales for each auditorium and the total overall.
        System.out.print("\nAuditorium:  Reserved Open  Sales");
        int size1 = size(A1Reserved, A1Available);
        int size2 = size(A2Reserved, A2Available);
        int size3 = size(A3Reserved, A2Available);
        int r1 = 0;
        DoubleLinkNode cur = A1Reserved.getHead();
        while(cur != null){
            r1++;
            cur = cur.getNext();
        }
        int r2 = 0;
        cur = A2Reserved.getHead();
        while(cur != null){
            r2++;
            cur = cur.getNext();
        }
        int r3 = 0;
        cur = A3Reserved.getHead();
        while(cur != null){
            r3++;
            cur = cur.getNext();
        }
        System.out.print("\nAuditorium 1: " + r1 + "       " + (size1 - r1) + "    $" + (r1 * 7) + "   ");
        System.out.print("\nAuditorium 2: " + r2 + "       " + (size2 - r2) + "    $" + (r2 * 7) + "   ");
        System.out.print("\nAuditorium 3: " + r3 + "       " + (size3 - r3) + "    $" + (r3 * 7) + "   ");

        System.out.print("\nTotal:        " + (r1+r2+r3) + "      " + ((size3 - r3)+(size2 - r2)+(size1 - r1))+ "   $" + (r1+r2+r3)*7 + "   ");

    }
    public static int size(LinkedList a, LinkedList b){
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
    
    public static void display(LinkedList a, LinkedList r){
        System.out.print("  ");
        int s;
        if(a.getTail().getSeat() > r.getTail().getSeat())
            s = a.getTail().getSeat()+1;
        else
            s = r.getTail().getSeat()+1;

        for(int i=1; i<=s; i++)
                System.out.print(i % 10 + " ");
        System.out.print('\n' + "1 ");
        DoubleLinkNode cura = a.getHead();
        DoubleLinkNode curr = r.getHead();
        
        int row = 0;
        int count = 0;
        while(cura != null && curr != null){
            if(cura.getSeat() == count && cura.getRow() == row){
                System.out.print("# ");
                cura = cura.getNext();
            }
            else{
                System.out.print(". ");
                curr = curr.getNext();
            }
            count++;
            if(count == s){
                count -= s;
                row++;
                System.out.print("\n" + (row+1) + " ");}
        }
        while(cura != null){
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
        while(curr != null){
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
        if(cura == null && curr != null)
            System.out.print('.' + "\n\n");
        if(cura != null && curr == null)
            System.out.print('#' +"\n\n");
    }

    
    public static int[] findBest(int n, LinkedList r, LinkedList a){
        int mSeat;
        int mRow;
        if(a.getTail().getSeat() > r.getTail().getSeat())
            mSeat = a.getTail().getSeat() / 2;
        else
            mSeat = r.getTail().getSeat() / 2;
        if(a.getTail().getRow() > r.getTail().getRow())
            mRow = a.getTail().getRow() / 2;
        else
            mRow = r.getTail().getRow() / 2;
        
        int bR = 9999999;
        int bS = 9999999;
        DoubleLinkNode cur = a.getHead();
        while(cur.getNext() != null){
            if((Math.sqrt(Math.pow(Math.abs((double)bR - (double)mRow),2) + Math.pow(Math.abs((double)bS - (double)mSeat),2))) > (Math.sqrt(Math.pow(Math.abs((double)cur.getRow() - (double)mRow),2) + Math.pow(Math.abs((double)cur.getSeat() - (double)mSeat),2)))){
                if(checkA(cur.getRow(),cur.getSeat(),n,a)){
                    bR = cur.getRow();
                    bS = cur.getSeat();
                }
            }
            else if((Math.sqrt(Math.pow(Math.abs((double)bR - (double)mRow),2) + Math.pow(Math.abs((double)bS - (double)mSeat),2))) == (Math.sqrt(Math.pow(Math.abs((double)cur.getRow() - (double)mRow),2) + Math.pow(Math.abs((double)cur.getSeat() - (double)mSeat),2)))){
                if(cur.getRow() == mRow)    
                    if(checkA(cur.getRow(),cur.getSeat(),n,a)){
                        bR = cur.getRow();
                        bS = cur.getSeat();
                    }
            }
            cur = cur.getNext();
        }
        return new int[] {bR, bS};
    }
    
    public static void reserve(int r, int s, int n, LinkedList R, LinkedList A){
        for(int i = 0; i < n; i++){
            R.addNode(r, s + i);
            A.deleteNode(r, s + i);
        }
    }
    
    public static boolean checkA(int r, int s, int n, LinkedList A){
        DoubleLinkNode cur = A.getHead();
        while(cur.getNext() != null){
            if(cur.getRow() == r && cur.getSeat() == s){
                for(int i = 0;i < n;i++){
                    if(cur == null || cur.getRow() != r || cur.getSeat() != s + i)
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
