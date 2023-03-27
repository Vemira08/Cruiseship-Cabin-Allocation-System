import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner cabins = new Scanner(System.in);
        String input;
        Cabin[] Ship = new Cabin[13];
        for (int x = 1; x < 13; x++) Ship[x] = new Cabin();
        initialise(Ship);
        CircularQueue queue= new CircularQueue(5);
        while (true){
            System.out.println("**************************************CRUISE SHIP**********************************************");
            System.out.println("A: Add customer to the cabin");
            System.out.println("V: View all cabins");
            System.out.println("E: Display Empty cabins");
            System.out.println("D: Delete customer from cabin");
            System.out.println("F: Find cabin from customer name");
            System.out.println("S: Store program data into file");
            System.out.println("L: Load program data from file");
            System.out.println("O: View passengers ordered alphabetically by name");
            System.out.println("T: Print expenses per customer/ total expenses");
            System.out.println("Choose an option to proceed:");

            input = cabins.next();
            if (input.equalsIgnoreCase("V")){
                viewALL(Ship);
            }
            else if (input.equalsIgnoreCase("A")){
                addCustomer(Ship,cabins,queue);
            }
            else if (input.equalsIgnoreCase("E")){
                Display_Empty_Cabins(Ship);
            }
            else if (input.equalsIgnoreCase("D")){
                Delete_Customer_From_Cabin(Ship,cabins,queue);
            }
            else if (input.equalsIgnoreCase("F")){
                FindRoomFromCustomerName(Ship,cabins);
            }
            else if (input.equalsIgnoreCase("S")){
                StoreProgramDataInToFile(Ship);
            }
            else if (input.equalsIgnoreCase("L")){
                LoadProgramDataFromFile(Ship);
            }
            else if (input.equalsIgnoreCase("O")){
                View_passengers_Ordered_alphabetically_by_name(Ship);
            }
            else if (input.equalsIgnoreCase("T")){
                expenses(Ship,cabins);
            }
            else if (input.equalsIgnoreCase("x")){
                break;
            }
            else {
                System.out.println("Incorrect input.");
            }
        }
    }

    private static void initialise(Cabin[] myCabin) {
        for (int x = 1; x < 13; x++ ){

            myCabin[x].setMainName("e");
            for (int y=0; y<3; y++){
                myCabin[x].getPassengers()[y] = new Passenger("e","e",0);
            }
        }
        System.out.println( "initialise ");
    }


    private static void waitingListInitialise(Cabin[] myCabin, CircularQueue queue){
        if (!queue.isEmpty()){
            for (int x = 1; x < 13; x++){
                if (myCabin[x].getMainName().equals("e")){
                    if (!queue.isEmpty()){
                        String[] tempCabin =  queue.dequeue();
                        int i = 0;
                        for (int y=0; y<3; y++){
                            System.out.println();
                            myCabin[x].getPassengers()[y].setPass_fName(tempCabin[i].split(":")[0]);
                            myCabin[x].getPassengers()[y].setPass_lName(tempCabin[i].split(":")[1]) ;
                            myCabin[x].getPassengers()[y].setPass_expenses(Integer.parseInt(tempCabin[i+3]));
                            myCabin[x].setMainName("");
                            i++;
                        }
                        System.out.println("Customers added from waiting list to cabin "+x);
                    }
                }
            }
        }
    }

    private static void viewALL(Cabin[] myCabin){
        for (int x = 1; x < 13; x++) {
            if(myCabin[x].getName().equals("e")){
                System.out.println("Cabin "+x+" is empty.");
            }
            else {
                System.out.println("Cabin " + x + " is occupied by:");
                for (int y=0; y<3; y++) {
                    if(myCabin[x].getPassengers()[y].getPass_fName().equals("e") && myCabin[x].getPassengers()[y].getPass_lName().equals("e")){
                        continue;
                    }
                    System.out.println("   "+myCabin[x].getPassengers()[y].getPass_fName() + " " + myCabin[x].getPassengers()[y].getPass_lName());
                }
                System.out.println();
            }
        }
    }

    private static void addCustomer(Cabin[] myCabin, Scanner scanner, CircularQueue queue){
        String[] names = new String[6];
        String temp,firstName,surName,select;
        int expenses,count =0;
        int cabinNum;
        while (true) {
            System.out.println("Enter cabin number (1-12) or 13 to stop:" );
            cabinNum = scanner.nextInt();
            if (cabinNum == 13){
                break;
            }
            else if(cabinNum>0 && cabinNum<13){
                if (myCabin[cabinNum].getMainName().equals("e")){
                    while (true) {
                        System.out.println("Enter first name:");
                        temp = scanner.next();
                        firstName = temp.substring(0, 1).toUpperCase() + temp.substring(1);

                        System.out.println("Enter surname: ");
                        temp = scanner.next();
                        surName = temp.substring(0, 1).toUpperCase() + temp.substring(1);

                        System.out.println("Enter expenses($): ");
                        expenses = scanner.nextInt();
                        if (myCabin[cabinNum].getPassengers()[count].getPass_fName().equals("e")&& myCabin[cabinNum].getPassengers()[count].getPass_lName().equals("e")){
                            myCabin[cabinNum].getPassengers()[count].setPass_fName(firstName);
                            myCabin[cabinNum].getPassengers()[count].setPass_lName(surName);
                            myCabin[cabinNum].getPassengers()[count].setPass_expenses(expenses);
                        }
                        myCabin[cabinNum].setMainName("");

                        System.out.println("Add another customer to the cabin?(Enter 'y' for yes or 'n' for no): ");
                        select = scanner.next();

                        if (select.equalsIgnoreCase("y")){
                            count++;
                            if (count==3){
                                System.out.println("Only 3 passengers can be added to a cabin.");
                                break;
                            }
                        }
                        else if (select.equalsIgnoreCase("n")){
                            count = 0;
                            break;
                        }
                    }
                }
                else {
                    if (queue.isFull()) break;
                    System.out.println("Adding to waiting list because the cabin is full.");
                    for (int i =0;i<3;i++){
                        System.out.println("Enter first name:");
                        temp = scanner.next();
                        firstName = temp.substring(0, 1).toUpperCase() + temp.substring(1);

                        System.out.println("Enter surname:");
                        temp = scanner.next();
                        surName = temp.substring(0, 1).toUpperCase() + temp.substring(1);

                        names[i] = firstName+":"+surName;

                        System.out.println("Enter expenses($): ");
                        expenses = scanner.nextInt();
                        names[i+3] = String.valueOf(expenses);
                    }
                    queue.enqueue(names);
                    waitingListInitialise(myCabin,queue);
                }
            }
            else {
                System.out.println("Incorrect input.");
            }
        }
    }

    private static void Display_Empty_Cabins(Cabin[] myCabin){
        for (int x = 1; x < 13; x++ ) {
            if (myCabin[x].getMainName().equals("e"))
                System.out.println("Cabin " + x + " is empty");
        }
    }

    private static void Delete_Customer_From_Cabin(Cabin[] myCabin, Scanner scanner, CircularQueue queue){
        String fName,sName;
        System.out.println("Enter customer's first name and the surname to delete.");
        System.out.println("Enter the first name:");
        fName = scanner.next();
        System.out.println("Enter the surname:");
        sName = scanner.next();
        int cabinNum = 0;
        int x=1;
        while (x<13){
            for (int y=0; y<3; y++) {
                if (fName.equalsIgnoreCase(myCabin[x].getPassengers()[y].getPass_fName())&& sName.equalsIgnoreCase(myCabin[x].getPassengers()[y].getPass_lName())) {
                    cabinNum=x;
                    System.out.println("Name was deleted successfully.");
                    break;
                }
            }
            x++;
        }
        if (cabinNum!=0) {
            for (Passenger passenger : myCabin[cabinNum].getPassengers()) {
                passenger.setPass_lName("e");
                passenger.setPass_fName("e");
                passenger.setPass_expenses(0);
                myCabin[cabinNum].setMainName("e");
            }
        }
        waitingListInitialise(myCabin,queue);
        if (cabinNum==0){
            System.out.println("Name was not found.");
        }
    }

    private static void FindRoomFromCustomerName(Cabin[] myCabin, Scanner scanner){
        String fName,sName;
        System.out.println("Enter customer's firstname and the surname to find the cabin.");
        System.out.println("Enter the firstname:");
        fName = scanner.next();
        System.out.println("Enter the surname:");
        sName = scanner.next();
        int x=1;
        while (x<13){
            for (int y=0; y<3; y++) {
                if (fName.equalsIgnoreCase(myCabin[x].getPassengers()[y].getPass_fName())&& sName.equalsIgnoreCase(myCabin[x].getPassengers()[y].getPass_lName())) {
                    System.out.println("The cabin customer " + fName +" "+sName+ " having is: cabin " + x);
                    return;
                }
            }
            x++;
        }
        if (x==13){
            System.out.println("Name was not found.");
        }
    }

    private static void StoreProgramDataInToFile(Cabin[] myCabin) throws IOException {
        FileWriter fileWriter = new FileWriter("File.txt");
        for (int x = 1; x < 13; x++ ){
            String writeFileCabin = "Cabin "+x+" : ";
            fileWriter.write(writeFileCabin+"\n");
            for (int y=0; y<3; y++){
                String writeFileCustomer = myCabin[x].getPassengers()[y].getPass_fName()+" "+myCabin[x].getPassengers()[y].getPass_fName()+" , Expenses- $"+myCabin[x].getPassengers()[y].getPass_expenses()+"\n";
                fileWriter.write(writeFileCustomer);
            }
        }
        fileWriter.close();
        System.out.println("Program data were stored in the file successfully.");
    }

    private static void LoadProgramDataFromFile(Cabin[] ship) throws FileNotFoundException {
        File file = new File("File.txt");
        Scanner scanner = new Scanner(file);
        int a = 1;
        for (;a<13;a++){
            String[] cabin = scanner.nextLine().split(" : ");
            if (cabin[1].replace(" ","").equals("0")){ship[a].setMainName("e");}
            else {ship[a].setMainName("");}
            System.out.println(Arrays.toString(cabin));

            for (int i=0;i<3;i++){
                String[] detail = scanner.nextLine().split(" , ");
                ship[a].getPassengers()[i].setPass_fName(detail[0].split(" ")[0]);
                ship[a].getPassengers()[i].setPass_lName(detail[0].split(" ")[1]);
                ship[a].getPassengers()[i].setPass_expenses(Integer.parseInt(detail[1].split("- ")[1].replace("$","")));
                System.out.println(Arrays.toString(detail));
            }
        }
    }

    private static void View_passengers_Ordered_alphabetically_by_name(Cabin[] myCabin){
        String[] fullName = new String[36];
        int m=0;
        for (int x = 1; x < 13; x++ ){
            for (int y=0; y<3; y++){
                fullName[m]= myCabin[x].getPassengers()[y].getPass_fName()+myCabin[x].getPassengers()[y].getPass_lName();
                m++;
            }
        }
        for (int i=0;i<m;i++){
            for (int j=0;j<m;j++) {
                if (fullName[j].compareTo(fullName[i]) > 0) {
                    String highIndex = fullName[i];
                    String smallIndex = fullName[j];
                    fullName[i] = smallIndex;
                    fullName[j] = highIndex;
                }
            }
        }
        System.out.println("Ordered view of passengers:");
        for (int j=0;j<m;j++){
            if (fullName[j].equals("ee")) {
                continue;
            }
            System.out.println(fullName[j]);
        }
        System.out.println("\n");
    }

    private static void expenses(Cabin[] myCabin,Scanner scanner){
        int option,totalExpenses = 0;
        System.out.println("1. Print expenses per customer.");
        System.out.println("2. Print total expenses.");
        System.out.println("Enter 1 or 2 to print expenses:");

        option = scanner.nextInt();
        if (option==1){
            System.out.println("Expenses per customer:");
            for (int x = 1; x < 13; x++ ) {
                System.out.println("Cabin "+x+" :");
                for (int y = 0; y < 3; y++) {
                    System.out.println(myCabin[x].getPassengers()[y].getPass_fName()+" "+myCabin[x].getPassengers()[y].getPass_lName()+"    -   $"+myCabin[x].getPassengers()[y].getPass_expenses());
                }
            }
        }
        else if(option==2){
            for (int x = 1; x < 13; x++ ) {
                System.out.println("Cabin "+x+" :");
                for (int y = 0; y < 3; y++) {
                    System.out.println(myCabin[x].getPassengers()[y].getPass_fName()+" "+myCabin[x].getPassengers()[y].getPass_lName()+"     -   $"+myCabin[x].getPassengers()[y].getPass_expenses());
                    totalExpenses = totalExpenses + myCabin[x].getPassengers()[y].getPass_expenses();
                }
            }
            System.out.println("               ___________");
            System.out.println("\nTotal expenses -   $"+totalExpenses);
        }
        else {
            System.out.println("Incorrect input.");
        }
    }
}
