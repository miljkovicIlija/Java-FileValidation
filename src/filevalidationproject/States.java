
package filevalidationproject;

import java.util.regex.*;

public class States {
    String name; 
    String city; 
    String population;

    public String getName() {
        return name;
    }

    public void setName(String name) { 
        String stateName = attributeFinder("^(.*?);",name);  
        this.name = stateName.toUpperCase();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) { 
        String stateCity = attributeFinder(";(.*?)=",city);
        this.city = stateCity;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) { 
        String statePopulation = attributeFinder("=(.*?);", population);
        this.population = statePopulation;
    }
    
    public String attributeFinder(String pattern, String rowToCheck){  //depending of the pattern it returns a String that will be either name/city/population of the State object
        String valueFound = "";
        Pattern p = Pattern.compile(pattern); 
        Matcher m = p.matcher(rowToCheck);  
        if(m.find()){
            valueFound = m.group(1);
        }
        return valueFound;
    }
}
