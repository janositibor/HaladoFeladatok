package locations;

public class LocationParser {
    public Location parse(String text){
        String[] fieldsArray;
        fieldsArray=text.split(",");
        return new Location(fieldsArray[0],Double.parseDouble(fieldsArray[1]),Double.parseDouble(fieldsArray[2]));
    }
}
