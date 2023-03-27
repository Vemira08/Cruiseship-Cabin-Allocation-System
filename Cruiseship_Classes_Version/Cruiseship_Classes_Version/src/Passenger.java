public class Passenger {
    private String Pass_fName;
    private String Pass_lName;
    private int Pass_expenses;

    public Passenger(String Pass_fName,String Pass_lName,int Pass_expenses){
        this.Pass_fName = Pass_fName;
        this.Pass_lName = Pass_lName;
        this.Pass_expenses = Pass_expenses;
    }

    public void setPass_fName(String Pass_fName1){Pass_fName = Pass_fName1;}
    public String getPass_fName(){return Pass_fName;}

    public void setPass_lName(String Pass_lName1){Pass_lName = Pass_lName1;}
    public String getPass_lName(){return Pass_lName;}

    public void setPass_expenses(int Pass_expenses1){Pass_expenses = Pass_expenses1;}
    public int getPass_expenses(){return Pass_expenses;}
}
