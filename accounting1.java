import java.io.*;
import java.util.Scanner;
import java.util.function.ToIntBiFunction;
import java.util.*;
import java.lang.String;
import java.io.File;  // Import the File class
import java.io.IOException;
import java.io.FileWriter;   // Import the FileWriter class


public class accounting1{
    

    public static void main(String[] args)throws Exception{

        String path = "C:/Java/jdk-18.0.1.1/";
        try {
			File myObj = new File("fullReport.txt");
			if (myObj.createNewFile()) {
			  System.out.println("File created: " + myObj.getName());
			} else {
			  System.out.println("File already exists.");
			}
		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }

		  try {
			FileWriter myWriter = new FileWriter("fullReport.txt");
            
            Console console = System.console();
            String name = console.readLine("Enter 0 for no report, Enter 1 for grand total across all companies, Enter 2 for totals of each company: ");
            
            Boolean validinput = false;
            while(validinput == false){
                if((Integer.parseInt(name) <0)  || (Integer.parseInt(name)>2)){
                    Console hi = System.console();
                    name = hi.readLine("Input is not valid. Please enter 0, 1, or 2: ");
                }else{
                    validinput =true;
                }
            }
            String masterQuote = "";
            int level = Integer.parseInt(name);
            int masterCounter = 0;
            double[] master = new double[] {0,0,0,0,0,0,0,0,0};
            Boolean[] error = new Boolean[] {false,false,false,false,false,false,false,false,false};
            String[] filelist = new String[]{"ttupnl2021","sandokanpnl2021","astrontxpnl2021","astrontelnjpnl2021","astronconsultingpnl2021"};
            double[][] totals = new double[filelist.length][master.length];
            for(int loop = 0;loop<filelist.length;loop++){
                int saveNumber = 0;
            String file = filelist[loop];
            int counter = 0;
            Scanner sc = new Scanner(new File(path+file+".csv"));
            sc.useDelimiter(",");
            while(sc.hasNext()){
                //System.out.println(sc.next());
                sc.next();
                counter++;        
            }
            sc.close();
            //System.out.println(counter);
            masterCounter = counter;
            int newCounter = 0;
            String[] info = new String[counter];
            String[] message = new String[counter];
            
            Scanner array = new Scanner(new File(path+file+ ".csv"));
            array.useDelimiter(",");
            while(array.hasNext()){
                info[newCounter] = array.next();
                newCounter++;
            }
            array.close();
            String quote = info[0].substring(0,1);
            masterQuote = quote;
            char charQuote = quote.charAt(0);
            //System.out.println(info[2]);
    
    
    
            double sum = 0;
            double checkIncome = 0;
            for(int i=0; i<counter;i++){ 
                if(info[i].indexOf("(")>0){
                    if(info[i].indexOf(")") >0){
                        
                    }else{
                        
                    }
                }
                int count = 0;
            int COUNT = 0;
            int[] index = new int[4];
            message[i] = info[i];
            if(message[i].indexOf("Total Income") > 0){
                if(info[i-1].indexOf("Total")<0){
                    message[i] = message[i].replaceAll("Total Income","");
                    message[i] = message[i].replaceAll(quote,"");
                    double last = Double.parseDouble(message[i]);
                    //System.out.println(last);
                    sum = sum + last; 
                    saveNumber = i;
                    i = counter;
                }else{
                    
                    saveNumber = i;
                    i=counter;
                }
                Boolean hit = false;
                String getIncome = info[saveNumber+1];
                char[] getIncomeArray = getIncome.toCharArray();
                for(int nikhil = 0;nikhil<getIncomeArray.length;nikhil++){
                    if(getIncomeArray[nikhil] == '"'){
                        hit = true;
                        getIncomeArray[nikhil] = ' ';
    
                    }
                    if(hit == true){
                        getIncomeArray[nikhil] = ' ';
                    }
                    
                }
                getIncome = String.valueOf(getIncomeArray);
                checkIncome = Double.parseDouble(getIncome);
                
            }else{int length = message[i].length();
                for(int j = 0; j<length; j++){
                    if(message[i].substring(j, j+1).equals(quote)){
                        count++;
                        index[COUNT] = j;
                        //System.out.println(index[COUNT]);
                        COUNT++;
                    }
                }
                if(count == 2 && info[i-1].indexOf("Total")<0){
                    message[i] = message[i].replaceAll(message[i].substring(index[0],index[1]+1),"");
                    if(message[i].substring(0,1).equals(" ")){
                        message[i] = message[i].replaceAll(message[i].substring(0,1),"");
                    }
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] lastlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        lastlist[k] = ' ';
                        if(lastlist[k+1] == charQuote ){
                            lastlist[k+1] = ' ';
                        }
                    }
                    String finalString = String.valueOf(lastlist);
                    //System.out.println(finalString);
                    Double num = Double.parseDouble(finalString);
                    
                    sum = sum+num;
                    
                }else{
                    message[i] = "0";
                }
                
            }
            }
            
            sum = Math.round(sum*100.0)/100.0;
            Boolean incomeError = false;
            if(checkIncome == sum){
                if(level>=2){
                    //System.out.println("Total income: " + sum);
    
                }
            }else{
                if(level>=2){
                    System.out.println("Total Income error--   pnl data: " + checkIncome+"   software data: "+sum);
                    myWriter.write("Total Income error--   pnl data: " + checkIncome+"   software data: "+sum+"\n");
    
                }
                incomeError = true;
            }
            totals[loop][0] = sum;
            master[0] = master[0] + sum;
            if(error[0]==false && incomeError==true){
                error[0] = true;
            }
    
            
            
            
            double checkCogs = 0;
            int savecogsnumber = 0;
            double cogssum = 0;
            Boolean onquote = false;
    
            for(int save = saveNumber;save<counter;save++){
                if(info[save].indexOf("Cost of Goods Sold")<0){
                    saveNumber++;
                }else{
                    saveNumber++;
                    save = counter;
                }
            }
            for(int i=saveNumber; i<counter;i++){ 
                int count = 0;
                int COUNT = 0;
            int[] cogsindex = new int[4];
            message[i] = info[i];
            if(message[i].indexOf("Total COGS") > 0){
                if(info[i-1].indexOf("Total")<0){
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] cogsfirstlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        cogsfirstlist[k] = ' ';
                        if(cogsfirstlist[k+1] == charQuote ){
                            cogsfirstlist[k+1] = ' ';
                        }
                    }
                    String cogsfirstString = String.valueOf(cogsfirstlist);
                    //System.out.println(cogsfirstString);
                    Double cogsfirst = Double.parseDouble(cogsfirstString);
                    cogssum = cogssum + cogsfirst; 
                    savecogsnumber = i;
                    i = counter;
                }else{
                    savecogsnumber = i;
                    i=counter;
                }
                Boolean hit = false;
                String getCogs = info[savecogsnumber+1];
                char[] getCogsArray = getCogs.toCharArray();
                for(int nikhil = 0;nikhil<getCogsArray.length;nikhil++){
                    if(getCogsArray[nikhil] == '"'){
                        hit = true;
                        getCogsArray[nikhil] = ' ';
    
                    }
                    if(hit == true){
                        getCogsArray[nikhil] = ' ';
                    }
                    
                }
                hit = false;
                getCogs = String.valueOf(getCogsArray);
                checkCogs = Double.parseDouble(getCogs);
                
                
            }else{int length = message[i].length();
                for(int j = 0; j<length; j++){
                    if(message[i].substring(j, j+1).equals(quote)){
                        count++;
                        cogsindex[COUNT] = j;
                        //System.out.println(index[COUNT]);
                        COUNT++;
                    }
                }
    
                if(count == 2 && info[i-1].indexOf("Total Premium")>0){
                    message[i] = message[i].replaceAll(message[i].substring(cogsindex[0],cogsindex[1]+1),"");
                    if(message[i].substring(0,1).equals(" ")){
                        message[i] = message[i].replaceAll(message[i].substring(0,1),"");
                    }
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] cogslastlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        cogslastlist[k] = ' ';
                        if(cogslastlist[k+1] == charQuote ){
                            cogslastlist[k+1] = ' ';
                        }
                    }
                    String cogsfinalString = String.valueOf(cogslastlist);
                    //System.out.println(cogsfinalString);
                    Double num = Double.parseDouble(cogsfinalString);
                    
                    cogssum = cogssum+num;
                    
                }else{
                    if(count == 2 && info[i-1].indexOf("Total")<0){
                        message[i] = message[i].replaceAll(message[i].substring(cogsindex[0],cogsindex[1]+1),"");
                        if(message[i].substring(0,1).equals(" ")){
                            message[i] = message[i].replaceAll(message[i].substring(0,1),"");
                        }
                        int first = message[i].indexOf(quote);
                        int last = message[i].lastIndexOf(quote);
                        char[] cogslastlist = message[i].toCharArray();
                        for(int k = first;k<last;k++){
                            cogslastlist[k] = ' ';
                            if(cogslastlist[k+1] == charQuote ){
                                cogslastlist[k+1] = ' ';
                            }
                        }
                        String cogsfinalString = String.valueOf(cogslastlist);
                        //System.out.println(cogsfinalString);
                        Double num = Double.parseDouble(cogsfinalString);
                        
                        cogssum = cogssum+num;
                        
                    }else{
                        message[i] = "0";
                    }
                }
                
            }
            }
            cogssum = Math.round(cogssum*100.0)/100.0;
            Boolean COGSerror = false;
            if(cogssum == checkCogs){
                if(level>1){
                    //System.out.println("Total COGS: " + cogssum);
                }
                
            }else{
                if(level>1){
                    System.out.println("Total COGS error--   pnl data: " + checkCogs+"   software data: "+cogssum);
                    myWriter.write("Total COGS error--   pnl data: " + checkCogs+"   software data: "+cogssum+"\n");
                }
                
                COGSerror = true;
            }
            totals[loop][1] = cogssum;
            master[1] = master[1] + cogssum;
            if(error[1]==false && COGSerror==true){
                error[1] = true;
            }
    
            Boolean grossError = false;
            if(COGSerror ==true || incomeError == true){
                grossError = true;
            }
            double gross = sum-cogssum;
            gross = Math.round(gross*100.0)/100.0;
            if(grossError == false){
                if(level>1){
                    //System.out.println("Total Gross Profit: "+ gross);
    
                }
    
            }else{
                if(level>1){
                    if(COGSerror == true && incomeError ==false){
                        System.out.println("Gross Profit: COGS total is wrong, check data sheet");
                        myWriter.write("Gross Profit: COGS total is wrong, check data sheet"+"\n");
                    }
                    if(COGSerror == false && incomeError ==true){
                        System.out.println("Gross Profit: Income total is wrong, check data sheet");
                        myWriter.write("Gross Profit: Income total is wrong, check data sheet"+"\n");
                    }
                    if(COGSerror == true && incomeError ==true){
                        System.out.println("Gross Profit: COGS total & Income total is wrong, check data sheet");
                        myWriter.write("Gross Profit: COGS total & Income total is wrong, check data sheet"+"\n");
                    }
                }
                
            }
            totals[loop][2] = gross;
            master[2] = master[2] + gross;
            if(error[2]==false && grossError==true){
                error[2] = true;
            }
            
    
    
    
    
            Boolean cogsExist = true;
            if(cogssum == 0){
                cogsExist = false;
            }
            for(int l = savecogsnumber;l<counter;l++){
                if(info[l].indexOf("Expense")<0){
                    savecogsnumber++;
                }else{
                    
                    savecogsnumber++;
                    l = counter;
                }
            }
    
            int saveexpnum = 0;
            double expsum = 0;
            double checkEXP = 0;
            for(int i=savecogsnumber; i<counter;i++){
                
             
                int count = 0;
                int COUNT = 0;
            int[] expindex = new int[4];
            message[i] = info[i];
            if(message[i].indexOf("Total Expense") > 0){
                Boolean hit = false;
                char[] love = info[i+1].toCharArray();
                for(int live = 0;live<love.length;live++){
                    if(love[live] == '"'){
                        hit = true;
                        love[live] = ' ';
    
                    }
                    if(hit == true){
                        love[live] = ' ';
                    }
                }
                String lol = String.valueOf(love);
                checkEXP = Double.parseDouble(lol);
                checkEXP = Math.round(checkEXP*100.0)/100.0;
                
    
                if(info[i-1].indexOf("Total")<0){
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] cogsfirstlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        cogsfirstlist[k] = ' ';
                        if(cogsfirstlist[k+1] == charQuote ){
                            cogsfirstlist[k+1] = ' ';
                        }
                    }
                    String cogsfirstString = String.valueOf(cogsfirstlist);
                    //System.out.println(cogsfirstString);
                    Double cogsfirst = Double.parseDouble(cogsfirstString);
                    expsum = expsum + cogsfirst; 
                    saveexpnum = i;
                    i = counter;
                }else{
                    saveexpnum = i;
                    i=counter;
                }
                
            }else{
                int length = message[i].length();
                for(int j = 0; j<length; j++){
                    if(message[i].substring(j, j+1).equals(quote)){
                        count++;
                        expindex[COUNT] = j;
                        //System.out.println(index[COUNT]);
                        COUNT++;
                    }
                }
                if(count == 2 && info[i-1].indexOf("Total Premium")>0){
                    if(info[i].indexOf("(")>0){
                        if(info[i].indexOf(")") >0){
                            message[i] = message[i].replaceAll(message[i].substring(expindex[0],expindex[1]+1),"");
                    if(message[i].substring(0,1).equals(" ")){
                        message[i] = message[i].replaceAll(message[i].substring(0,1),"");
                    }
                        }else{
                            info[i]=quote+"hello"+quote;
                            
                        }
                    }
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] cogslastlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        cogslastlist[k] = ' ';
                        if(cogslastlist[k+1] == charQuote ){
                            cogslastlist[k+1] = ' ';
                        }
                    }
                    String cogsfinalString = String.valueOf(cogslastlist);
                    //System.out.println(cogsfinalString);
                    Double num = Double.parseDouble(cogsfinalString);
                    
                    expsum = expsum+num;
                }else{
                    if(count == 2 && info[i-1].indexOf("Total")<0){
                        if(info[i].indexOf("(")>0){
                            if(info[i].indexOf(")") >0){
                                message[i] = message[i].replaceAll(message[i].substring(expindex[0],expindex[1]+1),"");
                        if(message[i].substring(0,1).equals(" ")){
                            message[i] = message[i].replaceAll(message[i].substring(0,1),"");
                        }
                            }else{
                                info[i]=quote+"hello"+quote;
                                
                            }
                        }
                        int first = message[i].indexOf(quote);
                        int last = message[i].lastIndexOf(quote);
                        char[] cogslastlist = message[i].toCharArray();
                        for(int k = first;k<last;k++){
                            cogslastlist[k] = ' ';
                            if(cogslastlist[k+1] == charQuote ){
                                cogslastlist[k+1] = ' ';
                            }
                        }
                        String cogsfinalString = String.valueOf(cogslastlist);
                        //System.out.println(cogsfinalString);
                        Double num = Double.parseDouble(cogsfinalString);
                        
                        expsum = expsum+num;
                        
                    }else{
                        message[i] = "0";
                    }
                }
                
                
            }
            }
            Boolean expError = false;
            if(cogsExist == false){
                
                expsum = expsum-sum-sum;
                expsum = Math.round(expsum*100.0)/100.0;
                if(checkEXP == expsum){
                    if(level>1){
                        //System.out.println("Total Expense: "+expsum);
                    }
                }else{
                    if(level>1){
                        System.out.println("Expense Error--   pnl data: "+checkEXP+ "  software data: "+expsum);
                        myWriter.write("Expense Error--   pnl data: "+checkEXP+ "  software data: "+expsum+"\n");
                    }
                    expError=true;
                }
                
            }else{
                expsum = Math.round(expsum*100.0)/100.0;
                if(checkEXP == expsum){
                    if(level>1){
                        //System.out.println("Total Expense: "+expsum);
                    }
                }else{
                    if(level>1){
                        System.out.println("Expense Error--   pnl data: "+checkEXP+ "  software data: "+expsum);
                        myWriter.write("Expense Error--   pnl data: "+checkEXP+ "  software data: "+expsum+"\n");
                    }
                    expError=true;
    
                }
            }
            Boolean noiError = false;
    
            totals[loop][3] = expsum;
            master[3] = master[3] + expsum;
            if(error[3]==false && expError==true){
                error[3] = true;
            }
            double noi = gross -expsum;
            noi = Math.round(noi*100.0)/100.0;
            if(grossError ==true || expError == true){
                noiError= true;
            }else{
                noiError = false;
            }
    
            totals[loop][4] = noi;
            master[4] = master[4] + noi;
            if(noiError == false){
                if(level>1){
                    //System.out.println("Net Ordinary Income: " +noi);
                }
            }else{
                if(level>1){
                    if(grossError == true && expError ==false){
                        System.out.println("Net Ordinary Income: Gross Profit is wrong, check data sheet");
                        myWriter.write("Net Ordinary Income: Gross Profit is wrong, check data sheet"+"\n");
                    }
                    if(grossError == false && expError ==true){
                        System.out.println("Net Ordinary Income: Expense total is wrong, check data sheet");
                        myWriter.write("Net Ordinary Income: Expense total is wrong, check data sheet"+"\n");
                    }
                    if(grossError == true && expError ==true){
                        System.out.println("Net Ordinary Income: Gross Profit & Expense total is wrong, check data sheet");
                        myWriter.write("Net Ordinary Income: Gross Profit & Expense total is wrong, check data sheet"+"\n");
                    }
                }
                
            }
            if(error[4]==false && noiError==true){
                error[4] = true;
            }
    
    
            for(int al = saveexpnum;al<counter;al++){
                if(info[al].indexOf("Other Income")<0){
                    saveexpnum++;
                }else{
                    saveexpnum++;
                    al = counter;
                }
            }
    
    
            int saveincome = 0;
            double otherincome = 0;
            double checkTOI = 0;
            for(int i=saveexpnum; i<counter;i++){
                
             
                int count = 0;
                int COUNT = 0;
            int[] otherinindex = new int[4];
            message[i] = info[i];
            if(message[i].indexOf("Total Other Income") > 0){
                Boolean hit = false;
                char[] love = info[i+1].toCharArray();
                for(int live = 0;live<love.length;live++){
                    if(love[live] == '"'){
                        hit = true;
                        love[live] = ' ';
    
                    }
                    if(hit == true){
                        love[live] = ' ';
                    }
                }
                String lol = String.valueOf(love);
                checkTOI = Double.parseDouble(lol);
                checkTOI = Math.round(checkTOI*100.0)/100.0;
    
                if(info[i-1].indexOf("Total")<0){
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] cogsfirstlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        cogsfirstlist[k] = ' ';
                        if(cogsfirstlist[k+1] == charQuote ){
                            cogsfirstlist[k+1] = ' ';
                        }
                    }
                    String cogsfirstString = String.valueOf(cogsfirstlist);
                    //System.out.println(cogsfirstString);
                    Double cogsfirst = Double.parseDouble(cogsfirstString);
                    otherincome = otherincome + cogsfirst; 
                    saveincome = i;
                    i = counter;
                }else{
                    saveincome = i;
                    i=counter;
                }
                
            }else{int length = message[i].length();
                for(int j = 0; j<length; j++){
                    if(message[i].substring(j, j+1).equals(quote)){
                        count++;
                        otherinindex[COUNT] = j;
                        //System.out.println(index[COUNT]);
                        COUNT++;
                    }
                }
                if(count == 2 && info[i-1].indexOf("Total")<0){
                    if(info[i].indexOf("(")>0){
                        if(info[i].indexOf(")") >0){
                            message[i] = message[i].replaceAll(message[i].substring(otherinindex[0],otherinindex[1]+1),"");
                    if(message[i].substring(0,1).equals(" ")){
                        message[i] = message[i].replaceAll(message[i].substring(0,1),"");
                    }
                        }else{
                            info[i]=quote+"hello"+quote;
                            
                        }
                    }
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] cogslastlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        cogslastlist[k] = ' ';
                        if(cogslastlist[k+1] == charQuote ){
                            cogslastlist[k+1] = ' ';
                        }
                    }
                    String cogsfinalString = String.valueOf(cogslastlist);
                    //System.out.println(cogsfinalString);
                    Double num = Double.parseDouble(cogsfinalString);
                    
                    otherincome = otherincome+num;
                    
                }else{
                    message[i] = "0";
                }
                
            }
            }
            Boolean TOIerror = false;
            otherincome = Math.round(otherincome*100.0)/100.0;
            if(otherincome == checkTOI){
                if(level>1){
                    //System.out.println("Total Other Income: " + otherincome);
    
                }
            }else{
                if(level>1){
                    System.out.println("TOI error--   pnl data: "+checkTOI+"    software data: "+otherincome);
                    myWriter.write("TOI error--   pnl data: "+checkTOI+"    software data: "+otherincome+"\n");
    
                }
                TOIerror = true;
            }
            totals[loop][5] = otherincome;
            master[5] = master[5] + otherincome;
            if(error[5]==false && TOIerror==true){
                error[5] = true;
            }
    
        
            
    
    
    
    
    
            for(int hope = saveincome;hope<counter;hope++){
                if(info[hope].indexOf("Other Expense")<0){
                    saveincome++;
                }else{
                    saveincome++;
                    hope = counter;
                }
            }
    
    
            int saveexpense = 0;
            double otherexpense = 0;
            double checkTOE = 0;
            for(int i=saveincome; i<counter;i++){
                
             
                int count = 0;
                int COUNT = 0;
            int[] otherinindex = new int[4];
            message[i] = info[i];
            if(message[i].indexOf("Total Other Expense") > 0){
                Boolean hit = false;
                char[] love = info[i+1].toCharArray();
                for(int live = 0;live<love.length;live++){
                    if(love[live] == '"'){
                        hit = true;
                        love[live] = ' ';
    
                    }
                    if(hit == true){
                        love[live] = ' ';
                    }
                }
                String lol = String.valueOf(love);
                checkTOE = Double.parseDouble(lol);
                checkTOE = Math.round(checkTOE*100.0)/100.0;
    
                if(info[i-1].indexOf("Total")<0){
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] cogsfirstlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        cogsfirstlist[k] = ' ';
                        if(cogsfirstlist[k+1] == charQuote ){
                            cogsfirstlist[k+1] = ' ';
                        }
                    }
                    String cogsfirstString = String.valueOf(cogsfirstlist);
                    //System.out.println(cogsfirstString);
                    Double cogsfirst = Double.parseDouble(cogsfirstString);
                    otherexpense = otherexpense + cogsfirst; 
                    saveexpense = i;
                    i = counter;
                }else{
                    saveexpense = i;
                    i=counter;
                }
                
            }else{int length = message[i].length();
                for(int j = 0; j<length; j++){
                    if(message[i].substring(j, j+1).equals(quote)){
                        count++;
                        otherinindex[COUNT] = j;
                        //System.out.println(index[COUNT]);
                        COUNT++;
                    }
                }
                if(count == 2 && info[i-1].indexOf("Total")<0){
                    if(info[i].indexOf("(")>0){
                        if(info[i].indexOf(")") >0){
                            message[i] = message[i].replaceAll(message[i].substring(otherinindex[0],otherinindex[1]+1),"");
                    if(message[i].substring(0,1).equals(" ")){
                        message[i] = message[i].replaceAll(message[i].substring(0,1),"");
                    }
                        }else{
                            info[i]=quote+"hello"+quote;
                            
                        }
                    }
                    int first = message[i].indexOf(quote);
                    int last = message[i].lastIndexOf(quote);
                    char[] cogslastlist = message[i].toCharArray();
                    for(int k = first;k<last;k++){
                        cogslastlist[k] = ' ';
                        if(cogslastlist[k+1] == charQuote ){
                            cogslastlist[k+1] = ' ';
                        }
                    }
                    String cogsfinalString = String.valueOf(cogslastlist);
                    //System.out.println(cogsfinalString);
                    Double num = Double.parseDouble(cogsfinalString);
                    
                    otherexpense = otherexpense+num;
                    
                }else{
                    message[i] = "0";
                }
                
            }
            }
            otherexpense = Math.round(otherexpense*100.0)/100.0;
            Boolean TOEerror = false;
            if(checkTOE == otherexpense){
                if(level>1){
                    //System.out.println("Total Other Expense: " + otherexpense);
    
                }
            }else{
                if(level>1){
                    System.out.println("TOE error--    pnl data: "+checkTOE+"    software data: "+otherexpense);
                    myWriter.write("TOE error--    pnl data: "+checkTOE+"    software data: "+otherexpense+"\n");
                }
                TOEerror = true;
            }
            Boolean NOIerror = false;
            if(TOEerror == true || TOIerror ==true){
                NOIerror = true;
            }
            totals[loop][6] = otherexpense;
            master[6] = master[6] + otherexpense;
            if(error[6]==false && TOEerror==true){
                error[6] = true;
            }
            double NOI = otherincome-otherexpense;
            NOI = Math.round(NOI*100.0)/100.0;
            if(NOIerror == false){
                if(level>1){
                    //System.out.println("Net Other Income: "+NOI);
                }
            }else{
                if(level>1){
                    if(TOEerror ==true && TOIerror == false){
                        System.out.println("Net Other Income: Total Other Expense is wrong, check data sheet");
                        myWriter.write("Net Other Income: Total Other Expense is wrong, check data sheet"+"\n");
                    }
                    if(TOEerror ==false && TOIerror == true){
                        System.out.println("Net Other Income: Total Other Income is wrong, check data sheet");
                        myWriter.write("Net Other Income: Total Other Income is wrong, check data sheet"+"\n");
                    }
                    if(TOEerror ==true && TOIerror == true){
                        System.out.println("Net Other Income: Total Other Expense/Total Other Income is wrong, check data sheet");
                        myWriter.write("Net Other Income: Total Other Expense/Total Other Income is wrong, check data sheet"+"\n");
                    }
                }
                
            }
            totals[loop][7] = NOI;
            master[7] = master[7] + NOI;
            if(error[7]==false && NOIerror==true){
                error[7] = true;
            }
            double NetIncome = noi+NOI;
            Boolean NIerror = false;
            if(noiError == true || NOIerror == true){
                NIerror = true;
            }
            NetIncome = Math.round(NetIncome*100.0)/100.0;
            if(NIerror == false){
                if(level>1){
                    //System.out.println("Net Income: "+NetIncome);
                }
            }else{
                if(level>1){
                    if(noiError ==true && NOIerror == false){
                        System.out.println("Net Income: Net Ordinary Income is wrong, check data sheet");
                        myWriter.write("Net Income: Net Ordinary Income is wrong, check data sheet"+"\n");
                    }
                    if(noiError ==false && NOIerror == true){
                        System.out.println("Net Income: Net Other Income is wrong, check data sheet");
                        myWriter.write("Net Income: Net Other Income is wrong, check data sheet"+"\n");
                    }
                    if(noiError ==true && NOIerror == true){
                        System.out.println("Net Income: Net Other/Ordinary Income is wrong, check data sheet");
                        myWriter.write("Net Income: Net Other/Ordinary Income is wrong, check data sheet"+"\n");
                    }
                }
                
            }
            totals[loop][8] = NetIncome;
            master[8] = master[8] + NetIncome;
            if(error[8]==false && NIerror==true){
                error[8] = true;
            }
            }
    
    
            for(int round = 0;round<master.length;round++){
                master[round] = Math.round(master[round]*100.0)/100.0;
            }
    
            
    
            String[][][] nest3totals = new String[filelist.length][9][masterCounter];
            String[][][] nest4totals = new String[filelist.length][9][masterCounter];
            for(int loop = 0;loop<filelist.length;loop++){
                int counter = 0;
                String file = filelist[loop];
            Scanner sc = new Scanner(new File(path+file+".csv"));
            sc.useDelimiter("\n");
            while(sc.hasNext()){
                //System.out.println(sc.next());
                sc.next();      
                counter++; 
            }
            sc.close();
            
            String[] data = new String[counter];
            counter = 0;
            Scanner SC = new Scanner(new File(path+file+".csv"));
            SC.useDelimiter("\n");
            while(SC.hasNext()){
                data[counter] = SC.next();
                //System.out.println(data[counter]);      
                counter++;
            }
            SC.close();
            //System.out.println(data.length);
    
            int indexCount = 0;
            int indexSplitter = 0;
            String[] realData = new String[data.length * 2];
            for(int i = 0;i<data.length;i++){
                char[] datachar = data[i].toCharArray();
                for(int j = 0;j<datachar.length;j++){
                    if(datachar[j] == ','){
                        indexSplitter = j;
                    }
                }
                String temp = String.valueOf(datachar);
                realData[indexCount] = temp.substring(0,indexSplitter);
                //System.out.println(realData[indexCount]);
                indexCount++;
                realData[indexCount] = temp.substring(indexSplitter+1,temp.length());
                //System.out.println(realData[indexCount]);
                indexCount++;
            }
            String nullString = realData[3];
            for(int i = 0;i<realData.length;i++){
                if(realData[i].equals(nullString)){
                    realData[i] = "null";
                }
                //System.out.println(realData[i]);
            }
            
            Boolean incomeLoop = false;
            int nullcounter= 0;
            int numberCount = 0;
            
            for(int i = 0;i<realData.length;i++){
                if(realData[i].indexOf("Income")>0){
                    incomeLoop=true;
                }
                if(incomeLoop == true && realData[i]=="null"){
                    nullcounter = nullcounter+1;
                }
                if(nullcounter ==2 && realData[i+1] != "null"){
                    //System.out.println(realData[i]);
                    numberCount++;
                }
                if(nullcounter>2 && realData[i].indexOf("Total")>0){
                    nullcounter = nullcounter-1;
                    if(nullcounter==2){
                        //System.out.println(realData[i-1] +  realData[i]);
                        numberCount++;
                        
                    }
                }
                if(realData[i].indexOf("Total Income")>0){
                    i = realData.length;
                }
            }
            String[] incomeNest3 = new String[numberCount];
            numberCount = 0;
            incomeLoop = false;
            nullcounter =0;
            for(int i = 0;i<realData.length;i++){
                if(realData[i].indexOf("Income")>0){
                    incomeLoop=true;
                }
                if(incomeLoop == true && realData[i]=="null"){
                    nullcounter = nullcounter+1;
                }
                if(nullcounter ==2 && realData[i+1] != "null"){
                    incomeNest3[numberCount] = realData[i];
                    //System.out.println(incomeNest3[numberCount]);
                    numberCount++;
                }
                if(nullcounter>2 && realData[i].indexOf("Total")>0){
                    nullcounter = nullcounter-1;
                    if(nullcounter==2){
                        incomeNest3[numberCount] = realData[i-1] + realData[i];
                        //System.out.println(incomeNest3[numberCount]);
                        numberCount++;
                        
                    }
                }
                if(realData[i].indexOf("Total Income")>0){
                    i = realData.length;
                }
            }
            for(int i = 0;i<(incomeNest3.length-2);i++){
                nest3totals[loop][0][i] = incomeNest3[i+1];
                //System.out.println(nest3totals[loop][i]);
            }
            


            Boolean cogsloop = false;
            nullcounter= 0;
            numberCount = 0;
            
            for(int i = 0;i<realData.length;i++){
                if(realData[i].indexOf("Total Income")>0){
                    cogsloop=true;
                }
                if(cogsloop == true && realData[i]=="null"){
                    nullcounter = nullcounter+1;
                }
                if(nullcounter ==2 && realData[i+1] != "null"){
                    //System.out.println(realData[i]);
                    numberCount++;
                }
                if(nullcounter>2 && realData[i].indexOf("Total ")>0){
                    nullcounter = nullcounter-1;
                    if(nullcounter==2){
                        //System.out.println(realData[i-1] +  realData[i]);
                        numberCount++;
                        
                    }
                }
                if(realData[i].indexOf("Total COGS ")>0){
                    i = realData.length;
                }
            }
            String[] cogsNest3 = new String[numberCount];
            numberCount = 0;
            cogsloop = false;
            nullcounter =0;
            for(int i = 0;i<realData.length;i++){
                if(realData[i].indexOf("Total Income")>0){
                    cogsloop=true;
                }
                if(cogsloop == true && realData[i]=="null"){
                    nullcounter = nullcounter+1;
                }
                if(nullcounter ==2 && realData[i+1] != "null"){
                    cogsNest3[numberCount] = realData[i];
                    //System.out.println(incomeNest3[numberCount]);
                    numberCount++;
                }
                if(nullcounter>2 && realData[i].indexOf("Total ")>0){
                    nullcounter = nullcounter-1;
                    if(nullcounter==2){
                        cogsNest3[numberCount] = realData[i-1] + realData[i];
                        //System.out.println(incomeNest3[numberCount]);
                        numberCount++;
                        
                    }
                }
                if(realData[i].indexOf("Total COGS")>0){
                    i = realData.length;
                }
            }
            for(int i = 0;i<(cogsNest3.length-2);i++){
                nest3totals[loop][1][i] = cogsNest3[i+1];
                //System.out.println(nest3totals[loop][i]);
            }
            
            
            
    
            //System.out.println("\n");
            }
            if(level == 0){
                System.out.println("No report printed" +"\n");
                myWriter.write("No report printed" +"\n");
            }
            if(level >= 1){
                if(error[0] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Total Income: "+master[0]);
                    myWriter.write("Total Income: "+master[0]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][0]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][0]+"\n");
                            if(level>2){
                                for(int j = 0;j<masterCounter;j+=2){
                                    
                                    if(nest3totals[i][0][j]==null || nest3totals[i][0][j+1]==null){
                                        j = masterCounter;
                                    }else{
                                        
                                        System.out.println("      "+nest3totals[i][0][j]+"-- "+nest3totals[i][0][j+1]+"      ");
                                        myWriter.write("      "+nest3totals[i][0][j]+"-- "+nest3totals[i][0][j+1]+"      "+"\n");
                                    }
                                }
                            }
                        }
                        
                    }
        
                }
                if(error[1] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Total COGS: "+master[1]);
                    myWriter.write("Total COGS: "+master[1]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][1]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][1]+"\n");
                            if(level>2){
                                for(int j = 0;j<masterCounter;j+=2){
                                            
                                    if(nest3totals[i][1][j]==null || nest3totals[i][1][j+1]==null){
                                        j = masterCounter;
                                    }else{
                                        
                                        System.out.println("      "+nest3totals[i][1][j]+"-- "+nest3totals[i][1][j+1]+"      ");
                                        myWriter.write("      "+nest3totals[i][1][j]+"-- "+nest3totals[i][1][j+1]+"      "+"\n");
                                    }
                                }
                            }
                    }
                    
                    }
        
                }
                if(error[2] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Gross Profit: "+master[2]);
                    myWriter.write("Gross Profit: "+master[2]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][2]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][2]+"\n");
                        
                    }
                    if(level>2){
                            
                    }
                    }
        
                }
                if(error[3] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Total Expense: "+master[3]);
                    myWriter.write("Total Expense: "+master[3]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][3]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][3]+"\n");
                        
                    }
                    if(level>2){
                            
                    }
                    }
        
                }
                if(error[4] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Net Ordinary Income: "+master[4]);
                    myWriter.write("Net Ordinary Income: "+master[4]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][4]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][4]+"\n");
                    }
                    if(level>2){
                            
                    }
                    }
        
                }
                if(error[5] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Total Other Income: "+master[5]);
                    myWriter.write("Total Other Income: "+master[5]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][5]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][5]+"\n");
                    }
                    if(level>2){
                            
                    }
                    }
        
                }
                if(error[6] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Total Other Expense: "+master[6]);
                    myWriter.write("Total Other Expense: "+master[6]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][6]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][6]+"\n");
                        
                    }
                    if(level>2){
                            
                    }
                    }
        
                }
                if(error[7] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Net Other Income: "+master[7]);
                    myWriter.write("Net Other Income: "+master[7]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][7]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][7]+"\n");
                        
                    }
                    if(level>2){
                            
                    }
                    }
        
                }
                if(error[8] == true){
                    System.out.println("error--   refer to report to see where");
                    myWriter.write("error--   refer to report to see where"+"\n");
                }else{
                    System.out.println("Net Income: "+master[8]);
                    myWriter.write("Net Income: "+master[8]+"\n");
                    if(level>1){
                        for(int i = 0;i<filelist.length;i++){
                            System.out.println("   "+filelist[i] + ": "+totals[i][8]);
                            myWriter.write("   "+filelist[i] + ": "+totals[i][8]+"\n");
                        
                    }
                    if(level>2){
                            
                    }
                    }
                }
            }
            System.out.println("\n");
            myWriter.write("\n");
    
    
            
            Console hii = System.console();
        name = hii.readLine("How many levels would you like to see on the report? Enter 1 for Income report. Enter 2 for Net Ordinary/Net Other Income. Enter 3 for Subtotals. Enter 4 or 5 for a more in depth report: ");
        
        Boolean noreport = false;
        if(Integer.parseInt(name) == 0){
            System.out.println("No report printed");
            myWriter.write("No report printed"+"\n");
            noreport = true;
        }
            validinput = false;
            while(validinput == false){
                if((Integer.parseInt(name) <0)  || (Integer.parseInt(name)>5)){
                    Console hi = System.console();
                    name = hi.readLine("Input is not valid. Please enter 0, 1, 2, 3, 4, or 5: ");
                }else{
                    validinput =true;
                }
            }
        int lvl =Integer.parseInt(name) -1;
        int grandCounter = 0;
        int counter = 0;
            for(int rotations = 0;rotations<filelist.length;rotations++){
                
                //System.out.println("\n");
                counter = 0;
                String file = filelist[rotations];
                Scanner sc = new Scanner(new File(path+file+".csv"));
                sc.useDelimiter("\n");
                while(sc.hasNext()){
                    //System.out.println(sc.next());
                    sc.next();      
                    counter++; 
                }
                sc.close();
                
        
                String[] data = new String[counter];
                counter = 0;
                Scanner SC = new Scanner(new File(path+file+".csv"));
                SC.useDelimiter("\n");
                while(SC.hasNext()){
                    data[counter] = SC.next();
                    //System.out.println(data[counter]);      
                    counter++;
                }
                SC.close();
                //System.out.println(data.length);
        
                int indexCount = 0;
                int indexSplitter = 0;
                String[] realData = new String[data.length * 2];
                for(int i = 0;i<data.length;i++){
                    char[] datachar = data[i].toCharArray();
                    for(int j = 0;j<datachar.length;j++){
                        if(datachar[j] == ','){
                            indexSplitter = j;
                        }
                    }
                    String temp = String.valueOf(datachar);
                    realData[indexCount] = temp.substring(0,indexSplitter);
                    //System.out.println(realData[indexCount]);
                    indexCount++;
                    realData[indexCount] = temp.substring(indexSplitter+1,temp.length());
                    //System.out.println(realData[indexCount]);
                    indexCount++;
                }
                String nullString = realData[3];
                for(int i = 0;i<realData.length;i++){
                    if(realData[i].equals(nullString)){
                        realData[i] = null;
                    }
                    //System.out.println(realData[i]);
                }
                realData[0] = null;
                realData[1] = null;
                int start = 0;
                for(int i = 0;i<realData.length;i++){
                    if(realData[i] == null){
                        start++;
                    }else{
                        i = realData.length;
                    }
                }
                //System.out.println(start);
                String[] Data = new String[realData.length-start];
                for(int i = 0; i<Data.length;i++){
                    Data[i] = realData[start];
                    start++;
                    //System.out.println(Data[i]);
                }
                int nullcounter = 0;
                String[][] dataArray = new String[Data.length][3];
                for(int i = 0;i<Data.length;i++){
                    dataArray[i][2] = Integer.toString(i);
                    //System.out.println(dataArray[i][2]);
                }
                for(int i = 0; i<Data.length;i++){
                    if(Data[i] == null){
                        nullcounter++;
                    }else{
                        dataArray[i][0] = Data[i];
                        //System.out.println(dataArray[i][0]);
                        dataArray[i][1] = Integer.toString(nullcounter);
                        //System.out.println(dataArray[i][1]);
                        if(Data[i].indexOf("Total ")>0 || Data[i].indexOf("Net ")>0){
                            nullcounter--;
                            
                        }
                        
                    }
                }
                int COGSPLACE = 0;
                for(int i = 0;i<Data.length;i++){
                    if(dataArray[i][0] ==null){
        
                    }else{
                        if(dataArray[i][0].indexOf("Cost of Goods Sold")>0){
                            COGSPLACE = i;
                            i = Data.length;
                        }
                    }
                    
                }
                //System.out.println(COGSPLACE);
                for(int i=0;i<Data.length;i++){
                    int num = 0;
                    if(dataArray[i][0] == null){
                        dataArray[i][1] = dataArray[i-1][1];
                    }else{
                        num = Integer.parseInt(dataArray[i][1]);
                        if(dataArray[i][0].indexOf("Total ")>0 || dataArray[i][0].indexOf("Net ")>0){
                            num = num-1;
                            dataArray[i][1] = Integer.toString(num);
                        }
                    }
                    //System.out.println(dataArray[i][0]);
                    //System.out.println(dataArray[i][1]);
                }
        
                for(int i = 0; i<Data.length;i++){
                    if(Integer.parseInt(dataArray[i][1]) == 0){
                        //System.out.println(dataArray[i][0]);
                    }
                }
                
                int greatest = 0;
                for(int i =0;i<Data.length;i++){
                    int num = Integer.parseInt(dataArray[i][1]);
                    if (num>greatest){
                        greatest = num;
                        //System.out.println(greatest);
                    }
                }
                int[] greatestArray = new int[greatest+1];
                for(int i = 0;i<greatestArray.length;i++){
                    greatestArray[i] = 0;
                }
                for(int i = 0;i<Data.length;i++){
                    for(int j = 0;j<greatestArray.length;j++){
                        if(Integer.parseInt(dataArray[i][1]) == j){
                            greatestArray[j] = greatestArray[j]+1;
                        }
                    }
                }
                int largest = 0;
                for(int i = 0;i<greatestArray.length;i++){
                    if(greatestArray[i]> largest){
                        largest = greatestArray[i];
                    }
                }
                //System.out.println(largest);
        
                String[][] square = new String[greatestArray.length][largest];
                int inc = 0;
                for(int i = 0; i<Data.length;i++){
                    if(Integer.parseInt(dataArray[i][1]) == 0){
                        square[0][inc] = dataArray[i][0];
                        //System.out.println(square[0][inc]);
                        inc++;
                    }
                }
                inc = 0;
                for(int i = 0; i<Data.length;i++){
                    if(Integer.parseInt(dataArray[i][1]) == 1){
                        square[1][inc] = dataArray[i][0];
                        //System.out.println(square[1][inc]);
                        inc++;
                    }
                }
        
                inc = 0;
                for(int i = 0; i<Data.length;i++){
                    if(Integer.parseInt(dataArray[i][1]) == 2){
                        square[2][inc] = dataArray[i][0];
                        //System.out.println(square[2][inc]);
                        inc++;
                    }
                }
        
                String[][] display = new String[Data.length][3];
                int loopnum = 0;
                int placehold= 0;
                if(loopnum<=lvl){
                    int place = 0;
                    for(int i = 0;i<Data.length;i++){
                        if(Integer.parseInt(dataArray[i][1]) == -1){
                            display[place][0] = dataArray[i][0];
                            display[place][1] = dataArray[i][1];
                            display[place][2] = dataArray[i][2];
                            place++;
                            
                        }
                        
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(display[i][0] == null){
                            if(display[i+1][0] == null){
                                placehold = i;
                                i = Data.length;
                            }else{
                                display[i-1][0] =null;
        
                            }
                        }
                    }
                }
                loopnum = 1;
                if(loopnum<=lvl){
                    int place = placehold;
                    for(int i = 0;i<Data.length;i++){
                        if(Integer.parseInt(dataArray[i][1]) == 0){
                            display[place][0] = dataArray[i][0];
                            display[place][1] = dataArray[i][1];
                            display[place][2] = dataArray[i][2];
                            place++;
                            
                        }
                        
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(display[i][0] == null){
                            if(display[i+1][0] == null){
                                placehold = i+1;
                                i = Data.length;
                            }else{
                                display[i-1][0] =null;
        
                            }
                        }
                    }
                }
                loopnum = 2;
                if(loopnum<=lvl){
                    int place = placehold;
                    for(int i = 0;i<Data.length;i++){
                        if(Integer.parseInt(dataArray[i][1]) == 1){
                            display[place][0] = dataArray[i][0];
                            display[place][1] = dataArray[i][1];
                            display[place][2] = dataArray[i][2];
                            place++;
                            
                        }
                        
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(display[i][0] == null){
                            if(display[i+1][0] == null){
                                placehold = i+1;
                                i = Data.length;
                            }else{
                                display[i-1][0] =null;
        
                            }
                        }
                    }
        
                }
                loopnum = 3;
                String[][] incomeloop = new String[Data.length][3];
                String[][] newincomeloop = new String[Data.length][3];
                if(loopnum<=lvl){
                    for(int i = 0;i<Data.length;i++){
                        if(Integer.parseInt(dataArray[i][1])==2){
                            incomeloop[i][0] = dataArray[i][0];
                            incomeloop[i][1] = dataArray[i][1];
                            incomeloop[i][2] = dataArray[i][2];
                            //System.out.println(incomeloop[i][0]);
                        }
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(incomeloop[i][0] == null){
                            i++;
                        }else{
                            newincomeloop[i][0] = incomeloop[i][0];
                            newincomeloop[i][1] = incomeloop[i][1];
                            newincomeloop[i][2] = incomeloop[i][2];
                            //System.out.println(newincomeloop[i][0]);
                        }
                    }
                }
        
                loopnum = 4;
                String[][] lastloop = new String[Data.length][3];
                String[][] newlastloop = new String[Data.length][3];
                if(loopnum<=lvl){
                    for(int i = 0;i<COGSPLACE;i++){
                        if(Integer.parseInt(dataArray[i][1])==3){
                            lastloop[i][0] = dataArray[i][0];
                            lastloop[i][1] = dataArray[i][1];
                            lastloop[i][2] = dataArray[i][2];
                            //System.out.println(incomeloop[i][0]);
                        }
                        
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(lastloop[i][0] == null){
                            i++;
                        }else{
                            newlastloop[i][0] = lastloop[i][0];
                            newlastloop[i][1] = lastloop[i][1];
                            newlastloop[i][2] = lastloop[i][2];
                            //System.out.println(newincomeloop[i][0]);
                        }
                    }
                }
        
                for(int i = 0;i<Data.length;i++){
                    //System.out.println(display[i][0]);
                }
                String[][] realdisplay = new String[Data.length][2];
                int increment = 0;
                for(int i = 0; i<Data.length; i++){
                    for(int j = 0;j<Data.length;j++){
                        if(display[j][2] == null){
        
                        }else{
                            if(Integer.parseInt(display[j][2]) == increment){
                                realdisplay[i][0] = display[j][0];
                                realdisplay[i][1] = display[j][1];
                            }
                            
                        }
                        if(newincomeloop[j][2] == null){
        
                        }else{
                            if(Integer.parseInt(newincomeloop[j][2]) == increment){
                                realdisplay[i][0] = newincomeloop[j][0];
                                realdisplay[i][1] = newincomeloop[j][1];
                            }
                            
                        }
                        if(newlastloop[j][2] == null){
        
                        }else{
                            if(Integer.parseInt(newlastloop[j][2]) == increment){
                                realdisplay[i][0] = newlastloop[j][0];
                                realdisplay[i][1] = newlastloop[j][1];
                            }
                            
                        }
                        
                    }
                    
                    increment++;
                    
                }
                //System.out.println(filelist[rotations] + ": ");
                for(int i=0;i<Data.length;i++){
                    if(realdisplay[i][0]==null){
        
                    }else{
                        if(Integer.parseInt(realdisplay[i][1]) == -1){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                //System.out.println(realdisplay[i][0]);
        
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                        if(Integer.parseInt(realdisplay[i][1]) == 0){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                //System.out.println("     "+realdisplay[i][0]);
        
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                        if(Integer.parseInt(realdisplay[i][1])==1){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                //System.out.println("          "+realdisplay[i][0]);
        
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                        if(Integer.parseInt(realdisplay[i][1])==2){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                //System.out.println("               "+realdisplay[i][0]);
        
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                        if(Integer.parseInt(realdisplay[i][1])==3){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                //System.out.println("                    "+realdisplay[i][0]);
        
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                    }
                }
                //System.out.println("\n");
                if(grandCounter < counter){
                    grandCounter = counter;
                }
                //System.out.println(counter);
            }
            //System.out.println(grandCounter);
    
            String[][] print = new String[filelist.length][grandCounter+1];
    
            for(int rotations = 0;rotations<filelist.length;rotations++){
                
                //System.out.println("\n");
                counter = 0;
                String file = filelist[rotations];
                Scanner sc = new Scanner(new File(path+file+".csv"));
                sc.useDelimiter("\n");
                while(sc.hasNext()){
                    //System.out.println(sc.next());
                    sc.next();      
                    counter++; 
                }
                sc.close();
                
        
                String[] data = new String[counter];
                counter = 0;
                Scanner SC = new Scanner(new File(path+file+".csv"));
                SC.useDelimiter("\n");
                while(SC.hasNext()){
                    data[counter] = SC.next();
                    //System.out.println(data[counter]);      
                    counter++;
                }
                SC.close();
                //System.out.println(data.length);
        
                int indexCount = 0;
                int indexSplitter = 0;
                String[] realData = new String[data.length * 2];
                for(int i = 0;i<data.length;i++){
                    char[] datachar = data[i].toCharArray();
                    for(int j = 0;j<datachar.length;j++){
                        if(datachar[j] == ','){
                            indexSplitter = j;
                        }
                    }
                    String temp = String.valueOf(datachar);
                    realData[indexCount] = temp.substring(0,indexSplitter);
                    //System.out.println(realData[indexCount]);
                    indexCount++;
                    realData[indexCount] = temp.substring(indexSplitter+1,temp.length());
                    //System.out.println(realData[indexCount]);
                    indexCount++;
                }
                String nullString = realData[3];
                for(int i = 0;i<realData.length;i++){
                    if(realData[i].equals(nullString)){
                        realData[i] = null;
                    }
                    //System.out.println(realData[i]);
                }
                realData[0] = null;
                realData[1] = null;
                int start = 0;
                for(int i = 0;i<realData.length;i++){
                    if(realData[i] == null){
                        start++;
                    }else{
                        i = realData.length;
                    }
                }
                //System.out.println(start);
                String[] Data = new String[realData.length-start];
                for(int i = 0; i<Data.length;i++){
                    Data[i] = realData[start];
                    start++;
                    //System.out.println(Data[i]);
                }
                int nullcounter = 0;
                String[][] dataArray = new String[Data.length][3];
                for(int i = 0;i<Data.length;i++){
                    dataArray[i][2] = Integer.toString(i);
                    //System.out.println(dataArray[i][2]);
                }
                for(int i = 0; i<Data.length;i++){
                    if(Data[i] == null){
                        nullcounter++;
                    }else{
                        dataArray[i][0] = Data[i];
                        //System.out.println(dataArray[i][0]);
                        dataArray[i][1] = Integer.toString(nullcounter);
                        //System.out.println(dataArray[i][1]);
                        if(Data[i].indexOf("Total ")>0 || Data[i].indexOf("Net ")>0){
                            nullcounter--;
                            
                        }
                        
                    }
                }
                int COGSPLACE = 0;
                for(int i = 0;i<Data.length;i++){
                    if(dataArray[i][0] ==null){
        
                    }else{
                        if(dataArray[i][0].indexOf("Cost of Goods Sold")>0){
                            COGSPLACE = i;
                            i = Data.length;
                        }
                    }
                    
                }
                //System.out.println(COGSPLACE);
                for(int i=0;i<Data.length;i++){
                    int num = 0;
                    if(dataArray[i][0] == null){
                        dataArray[i][1] = dataArray[i-1][1];
                    }else{
                        num = Integer.parseInt(dataArray[i][1]);
                        if(dataArray[i][0].indexOf("Total ")>0 || dataArray[i][0].indexOf("Net ")>0){
                            num = num-1;
                            dataArray[i][1] = Integer.toString(num);
                        }
                    }
                    //System.out.println(dataArray[i][0]);
                    //System.out.println(dataArray[i][1]);
                }
        
                for(int i = 0; i<Data.length;i++){
                    if(Integer.parseInt(dataArray[i][1]) == 0){
                        //System.out.println(dataArray[i][0]);
                    }
                }
                
                int greatest = 0;
                for(int i =0;i<Data.length;i++){
                    int num = Integer.parseInt(dataArray[i][1]);
                    if (num>greatest){
                        greatest = num;
                        //System.out.println(greatest);
                    }
                }
                int[] greatestArray = new int[greatest+1];
                for(int i = 0;i<greatestArray.length;i++){
                    greatestArray[i] = 0;
                }
                for(int i = 0;i<Data.length;i++){
                    for(int j = 0;j<greatestArray.length;j++){
                        if(Integer.parseInt(dataArray[i][1]) == j){
                            greatestArray[j] = greatestArray[j]+1;
                        }
                    }
                }
                int largest = 0;
                for(int i = 0;i<greatestArray.length;i++){
                    if(greatestArray[i]> largest){
                        largest = greatestArray[i];
                    }
                }
                //System.out.println(largest);
        
                String[][] square = new String[greatestArray.length][largest];
                int inc = 0;
                for(int i = 0; i<Data.length;i++){
                    if(Integer.parseInt(dataArray[i][1]) == 0){
                        square[0][inc] = dataArray[i][0];
                        //System.out.println(square[0][inc]);
                        inc++;
                    }
                }
                inc = 0;
                for(int i = 0; i<Data.length;i++){
                    if(Integer.parseInt(dataArray[i][1]) == 1){
                        square[1][inc] = dataArray[i][0];
                        //System.out.println(square[1][inc]);
                        inc++;
                    }
                }
        
                inc = 0;
                for(int i = 0; i<Data.length;i++){
                    if(Integer.parseInt(dataArray[i][1]) == 2){
                        square[2][inc] = dataArray[i][0];
                        //System.out.println(square[2][inc]);
                        inc++;
                    }
                }
        
                String[][] display = new String[Data.length][3];
                int loopnum = 0;
                int placehold= 0;
                if(loopnum<=lvl){
                    int place = 0;
                    for(int i = 0;i<Data.length;i++){
                        if(Integer.parseInt(dataArray[i][1]) == -1){
                            display[place][0] = dataArray[i][0];
                            display[place][1] = dataArray[i][1];
                            display[place][2] = dataArray[i][2];
                            place++;
                            
                        }
                        
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(display[i][0] == null){
                            if(display[i+1][0] == null){
                                placehold = i;
                                i = Data.length;
                            }else{
                                display[i-1][0] =null;
        
                            }
                        }
                    }
                }
                loopnum = 1;
                if(loopnum<=lvl){
                    int place = placehold;
                    for(int i = 0;i<Data.length;i++){
                        if(Integer.parseInt(dataArray[i][1]) == 0){
                            display[place][0] = dataArray[i][0];
                            display[place][1] = dataArray[i][1];
                            display[place][2] = dataArray[i][2];
                            place++;
                            
                        }
                        
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(display[i][0] == null){
                            if(display[i+1][0] == null){
                                placehold = i+1;
                                i = Data.length;
                            }else{
                                display[i-1][0] =null;
        
                            }
                        }
                    }
                }
                loopnum = 2;
                if(loopnum<=lvl){
                    int place = placehold;
                    for(int i = 0;i<Data.length;i++){
                        if(Integer.parseInt(dataArray[i][1]) == 1){
                            display[place][0] = dataArray[i][0];
                            display[place][1] = dataArray[i][1];
                            display[place][2] = dataArray[i][2];
                            place++;
                            
                        }
                        
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(display[i][0] == null){
                            if(display[i+1][0] == null){
                                placehold = i+1;
                                i = Data.length;
                            }else{
                                display[i-1][0] =null;
        
                            }
                        }
                    }
        
                }
                loopnum = 3;
                String[][] incomeloop = new String[Data.length][3];
                String[][] newincomeloop = new String[Data.length][3];
                if(loopnum<=lvl){
                    for(int i = 0;i<Data.length;i++){
                        if(Integer.parseInt(dataArray[i][1])==2){
                            incomeloop[i][0] = dataArray[i][0];
                            incomeloop[i][1] = dataArray[i][1];
                            incomeloop[i][2] = dataArray[i][2];
                            //System.out.println(incomeloop[i][0]);
                        }
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(incomeloop[i][0] == null){
                            i++;
                        }else{
                            newincomeloop[i][0] = incomeloop[i][0];
                            newincomeloop[i][1] = incomeloop[i][1];
                            newincomeloop[i][2] = incomeloop[i][2];
                            //System.out.println(newincomeloop[i][0]);
                        }
                    }
                }
        
                loopnum = 4;
                String[][] lastloop = new String[Data.length][3];
                String[][] newlastloop = new String[Data.length][3];
                int x = Integer.parseInt(name);
                if(loopnum<=lvl){
                    for(int i = 0;i<COGSPLACE;i++){
                        if(Integer.parseInt(dataArray[i][1])==3){
                            lastloop[i][0] = dataArray[i][0];
                            lastloop[i][1] = dataArray[i][1];
                            lastloop[i][2] = dataArray[i][2];
                            //System.out.println(incomeloop[i][0]);
                        }
                        
                    }
                    for(int i = 0;i<Data.length;i++){
                        if(lastloop[i][0] == null){
                            i++;
                        }else{
                            newlastloop[i][0] = lastloop[i][0];
                            newlastloop[i][1] = lastloop[i][1];
                            newlastloop[i][2] = lastloop[i][2];
                            //System.out.println(newincomeloop[i][0]);
                        }
                    }
                }
        
                for(int i = 0;i<Data.length;i++){
                    //System.out.println(display[i][0]);
                }
                String[][] realdisplay = new String[Data.length][2];
                int increment = 0;
                for(int i = 0; i<Data.length; i++){
                    for(int j = 0;j<Data.length;j++){
                        if(display[j][2] == null){
        
                        }else{
                            if(Integer.parseInt(display[j][2]) == increment){
                                realdisplay[i][0] = display[j][0];
                                realdisplay[i][1] = display[j][1];
                            }
                            
                        }
                        if(newincomeloop[j][2] == null){
        
                        }else{
                            if(Integer.parseInt(newincomeloop[j][2]) == increment){
                                realdisplay[i][0] = newincomeloop[j][0];
                                realdisplay[i][1] = newincomeloop[j][1];
                            }
                            
                        }
                        if(newlastloop[j][2] == null){
        
                        }else{
                            if(Integer.parseInt(newlastloop[j][2]) == increment){
                                realdisplay[i][0] = newlastloop[j][0];
                                realdisplay[i][1] = newlastloop[j][1];
                            }
                            
                        }
                        
                    }
                    
                    increment++;
                    
                }
                int lmao = 0;
                //System.out.println(filelist[rotations] + ": ");
                if(noreport == true){

                }else{
                    print[rotations][lmao] = filelist[rotations] + ": ";
                System.out.println(print[rotations][lmao]);
                myWriter.write(print[rotations][lmao]+"\n");
                lmao++;
                }
                
                for(int i=0;i<Data.length;i++){
                    if(realdisplay[i][0]==null){
        
                    }else{
                        if(Integer.parseInt(realdisplay[i][1]) == -1){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                //System.out.println(realdisplay[i][0]);
                                    if(Integer.parseInt(name)<4){
                                        if(lmao % 2 == 0){
                                            print[rotations][lmao] = realdisplay[i][0];
                                            System.out.println(print[rotations][lmao]);
                                            myWriter.write(print[rotations][lmao]+"\n");
                                            lmao++;
                                        }else{
                                            print[rotations][lmao] = realdisplay[i][0];
                                            System.out.println(print[rotations][lmao]);
                                            myWriter.write(print[rotations][lmao]+"--  ");
                                            lmao++;
                                        }
                                    }else{
                                        print[rotations][lmao] = realdisplay[i][0];
                                            System.out.println(print[rotations][lmao]);
                                            myWriter.write(print[rotations][lmao]+"\n");
                                            lmao++;
                                    }
                                    
                                
                                
                                
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                        if(Integer.parseInt(realdisplay[i][1]) == 0){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                //System.out.println("     "+realdisplay[i][0]);
                                if(x<4){
                                    if(lmao % 2 == 0){
                                        print[rotations][lmao] = realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"\n");
                                        lmao++;
                                    }else{
                                        print[rotations][lmao] ="     "+realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"--  ");
                                        lmao++;
                                    }
                                }else{
                                    print[rotations][lmao] = "     "+realdisplay[i][0];
                                    System.out.println(print[rotations][lmao]);
                                    myWriter.write(print[rotations][lmao]+"\n");
                                    lmao++;
                                }
                                
        
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                        if(Integer.parseInt(realdisplay[i][1])==1){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                //System.out.println("          "+realdisplay[i][0]);
                                if(x<4){
                                    if(lmao % 2 == 0){
                                        print[rotations][lmao] = realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"\n");
                                        lmao++;
                                    }else{
                                        print[rotations][lmao] = "          "+ realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"--  ");
                                        lmao++;
                                    }
                                }else{
                                    print[rotations][lmao] = "          "+realdisplay[i][0];
                                    System.out.println(print[rotations][lmao]);
                                    myWriter.write(print[rotations][lmao]+"\n");
                                    lmao++;
                                }
                                
                                
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                        if(Integer.parseInt(realdisplay[i][1])==2){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                if(x<4){
                                    if(lmao % 2 == 0){
                                        print[rotations][lmao] = realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"\n");
                                        lmao++;
                                    }else{
                                        print[rotations][lmao] = "               "+ realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"--  ");
                                        lmao++;
                                    }
                                }else{
                                    print[rotations][lmao] = "               "+realdisplay[i][0];
                                    System.out.println(print[rotations][lmao]);
                                    myWriter.write(print[rotations][lmao]+"\n");
                                    lmao++;
                                }
                                
                                
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                        if(Integer.parseInt(realdisplay[i][1])==3){
                            if(realdisplay[i][0].indexOf("Total")>0){
        
                            }else{
                                if(x<4){
                                    if(lmao % 2 ==0){
                                        print[rotations][lmao] = realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"\n");
                                        lmao++;
                                    }else{
                                        print[rotations][lmao] = "                    "+ realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"--  ");
                                        lmao++;
                                    }
                                }else{
                                    print[rotations][lmao] = "                    "+realdisplay[i][0];
                                        System.out.println(print[rotations][lmao]);
                                        myWriter.write(print[rotations][lmao]+"\n");
                                        lmao++;
                                }
                                
                                
                                
                            }
                            //System.out.println(realdisplay[i][0].indexOf("Total"));
                            //System.out.println(display[i][2]);
                        }
                    }
                }
                if(Integer.parseInt(name)==0){

                }else{
                    System.out.println("\n");
                    myWriter.write("\n");
                }
                
    
            }
            
            int greatestStringLength = 0;
            for(int i = 0;i<filelist.length;i++){
                for(int j = 0;j<grandCounter+1;j++){
                    if(print[i][j] == null){
    
                    }else{
                        int stringLength = print[i][j].length();
                        if(stringLength > greatestStringLength){
                            greatestStringLength = stringLength;
                        }
                    }
                    
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            



		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
        
        

    }
    
}
