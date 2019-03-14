
package filevalidationproject;
import java.util.regex.*; 
import java.io.*;
import java.util.Arrays;

public class FileValidationProject implements Validator {

    @Override 
    public boolean isAlpha(String textToCheck){
        String regex = "^(?=.*[a-z])(?=.*[A-Z]).+$"; // checks if the row contains upper and lower case
        return checkText(regex, textToCheck);
    } 
    
    @Override 
    public boolean isDigit(String textToCheck){
        String regex = "(.)*(\\d)(.)*";              //checks if the row contains numbers 0-9
        return checkText(regex, textToCheck);
    } 
    
    @Override 
    public boolean isASCII(String textToCheck){
        String regex = ".*";                          //checks if the row contains ASCII code
        return checkText(regex, textToCheck);
    }
    
    
    public boolean checkText(String pattern, String matcher){    
        Pattern p = Pattern.compile(pattern); 
        Matcher m = p.matcher(matcher); 
        if (m.find()) 
            return true; 
        else 
            return false; 
    }
    
    
    public void validation(String arrayToValidate[]) throws IOException{   
        FileWriter fw = new FileWriter("ValidationResults.txt"); 
        BufferedWriter bw = new BufferedWriter(fw);
        
        for (int i = 0; i < arrayToValidate.length; i++) {
            
            if(isAlpha(arrayToValidate[i]) && isDigit(arrayToValidate[i]) && isASCII(arrayToValidate[i])){
                
                try{
                    bw.write(arrayToValidate[i] + "VALID"); 
                    bw.newLine();
                } catch(IOException ioe){
                    System.out.println("Error while writing to the file: " + ioe);
                }
            } else {
                
                try{
                    bw.write(arrayToValidate[i] + "INVALID"); 
                    bw.newLine();
                } catch(IOException ioe) {
                    System.out.println("Error while writing to the file: " + ioe);
                }
            }
        } 
        bw.close(); 
        System.out.println("Validated data Successfully added to file: ValidationResults.txt" );
    }


    public static void main(String[] args) throws IOException {
        
        //Reading the file FileToRead.txt 
        FileReader fr = null; 
        BufferedReader br = null; 
        LineNumberReader lineReader = null; 
        int count = 0; 
        String dataArray[] = null;   //data from file will be asign to this array
        
        try{
            fr = new FileReader("FileToRead.txt"); 
            lineReader = new LineNumberReader(fr); 
            while(lineReader.readLine() != null){
                count = lineReader.getLineNumber();  //Counting lines in file for array length
            }
            
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if(lineReader != null){
                lineReader.close();
            }
        } 
        System.out.println("Number of lines in file is: " + count); 
        
        
        //Setting the lines from file as dataArray values 
        try{
            fr = new FileReader("FileToRead.txt"); 
            br = new BufferedReader(fr); 
            dataArray = new String[count]; 
            
            do{
                for (int i = 0; i < dataArray.length; i++) {
                    dataArray[i] = br.readLine();
                }
            } while(br.readLine() != null);
            
        } catch(IOException ioe){
            ioe.printStackTrace();
        } finally {
            if(br != null) {
                br.close();
            }
        } 
        System.out.println(Arrays.toString(dataArray)); 
        
        FileValidationProject obj1 = new FileValidationProject(); 
        obj1.validation(dataArray);    //calling the non-static validation method to check if row is valid or invalid and writing it on new file 
        
        
        States stateArray[] = new States[dataArray.length];   //Making the State obaject array
        for (int i = 0; i < dataArray.length; i++) { 
            stateArray[i] = new States();
            stateArray[i].setName(dataArray[i]);                      //setting values
            stateArray[i].setCity(dataArray[i]); 
            stateArray[i].setPopulation(dataArray[i]);
        } 
        
        //Algorithm for 3rd TASK
        
        int duplicateCount,k; // duplicateCount presents how many times does the state name occur in array, Variable k will be used to get data of the 3rd state if exist 
        boolean duplicate,triplicate; // if state name occurs 2 times duplicate = true, if state name occurs 3 times triplicate is true but duplicate becomes false 
        String twoCityPop,threeCityPop; 
        String dupName = "";
        String triName = "";      
        System.out.println("------------------------------------------------------------------------------------------"); 
        
        for (int i = 0; i < stateArray.length; i++) {
            duplicateCount = 1; 
            k = 0; 
            duplicate = false; 
            triplicate = false; 
            twoCityPop = ""; 
            threeCityPop = ""; 
            
            if(i == stateArray.length - 1) { //when the last element of stateArray is to be checkd, the program breaks, because there is nothing to compare it to
                break;
            }
            
            for (int j = i + 1; j < stateArray.length; j++) {  //with this for in for loop, every elements will be compered with every single one element of stateArrray
                if(stateArray[i].getName().equals(stateArray[j].getName())) {  //cchecks if two states have same name
                    duplicateCount++; 
                    
                    if(duplicateCount == 2){
                        duplicate = true; 
                        twoCityPop = stateArray[j].getCity() + "(" + stateArray[j].getPopulation() + ")"; 
                        dupName = stateArray[i].getName();
                    } else if(duplicateCount == 3){
                        duplicate = false; 
                        triplicate = true; 
                        k = j; 
                        threeCityPop = stateArray[k].getCity()+ "(" + stateArray[k].getPopulation() + ")";
                        triName = stateArray[i].getName();
                    }
                }
            }
            if(triplicate){
                
                System.out.printf("{%s: %s(%s), %s , %s}\n",stateArray[i].getName(),stateArray[i].getCity(),stateArray[i].getPopulation(),twoCityPop,threeCityPop);
                System.out.println("------------------------------------------------------------------------------------------");
                
            } else if(duplicate){
                
                if(stateArray[i].getName().equals(triName)){ 
                    continue;
                }
                System.out.printf("{%s: %s(%s), %s}\n",stateArray[i].getName(),stateArray[i].getCity(),stateArray[i].getPopulation(),twoCityPop);  
                System.out.println("------------------------------------------------------------------------------------------");
                
            } else {
                
                if(stateArray[i].getName().equals(dupName) || stateArray[i].getName().equals(triName)) {
                    continue;
                } 
                System.out.printf("{%s: %s(%s)}\n",stateArray[i].getName(),stateArray[i].getCity(),stateArray[i].getPopulation()); 
                System.out.println("------------------------------------------------------------------------------------------");
            }
        }
        
    }

  
    
}
