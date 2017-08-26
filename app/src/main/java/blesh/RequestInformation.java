package blesh;

/**
 * Created by SKYLIFE on 24/06/2017.
 */

public class RequestInformation {

    private double latitude;
    private double longitude;
    private String amount;
    private String bleshKey;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setBleshKey(String bleshKey) {
        this.bleshKey = bleshKey;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBleshKey() {
        return bleshKey;
    }

    public String getAmount() {
        return amount;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


}
