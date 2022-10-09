package locations;

import java.util.Objects;

public class Location {
    private String name;
    private double latitude;
    private double longitude;

    public Location(String name, double latitude, double longitude) {
        if(latitude<-90 || latitude>90){
            throw new IllegalArgumentException("The latitude must be between -90 and 90 degree!");
        }
        if(longitude<-180 || longitude>180){
            throw new IllegalArgumentException("The longitude must be between -180 and 180 degree!");
        }
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double distanceFrom(Location location){
        int earthRadiusInMeter=6371000;
        double latDistance = distanceInRad (location.getLatitude()-getLatitude());
        double lonDistance = Math.toRadians(location.getLongitude()-getLongitude());

        double temp = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(getLatitude())) * Math.cos(Math.toRadians(location.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double differenceInRadian = 2 * Math.atan2(Math.sqrt(temp), Math.sqrt(1 - temp));

        double lateralDistanceInMeter = earthRadiusInMeter * differenceInRadian; // convert to meters

        return lateralDistanceInMeter/1000;
    }
    private double distanceInRad(double differenceInDegree){
        return  Math.toRadians(differenceInDegree);
    }

    public boolean isOnEquator(){
        return latitude == 0;
    }

    public boolean isOnPrimeMeridian(){
        return longitude == 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.latitude, latitude) == 0 && Double.compare(location.longitude, longitude) == 0 && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }
}
