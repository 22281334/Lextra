package comp5216.sydney.edu.au.lextra.Map;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.lextra.uos.UoS;

public class BuildingLocation {
    private GoogleMap myMap;
    List<LatLngName> latLngNames = new ArrayList<LatLngName>();
    public BuildingLocation(GoogleMap myMap) {
        this.myMap = myMap;
        addLocation();
    }

    public BuildingLocation(GoogleMap myMap, ArrayList<UoS> location) {

        this.myMap = myMap;
        addLocation();

    }

    public LatLng findLocation(String str) {
        for (LatLngName latLngName : latLngNames) {
            if (latLngName.getName().equals(str)) {
                return latLngName.getLatLng();
            }
        }
        return null;
    }

    public void setMarker(String str) {

        for (int i = 0; i < latLngNames.size(); i++) {
            if (latLngNames.get(i).getName().equals(str)) {
                MarkerOptions locationMarker = new MarkerOptions()
                        .position(latLngNames.get(i).getLatLng())
                        .title(latLngNames.get(i).getName());
                myMap.addMarker(locationMarker);
            }

        }
    }


    public void setMarker() {
        for (LatLngName location : latLngNames) {
            if (location != null) {
                MarkerOptions locationMarker = new MarkerOptions()
                        .position(location.getLatLng())
                        .title(location.getName());
                myMap.addMarker(locationMarker);
            }
        }
    }

    private void addLocation() {

        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Annexe SR 444"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Annexe SR 440"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Annexe SR 346"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Annexe SR 342"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Annexe SR 344"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Annexe SR 340"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Seminar 020"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Seminar 022"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Seminar 102"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Seminar 105"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law Annexe"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Seminar 107"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Seminar 028"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Lecture Theatre 026"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Lecture Theatre 024"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Seminar 100 (Hicksons)"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Annexe SR 442"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Lecture Theatre 106"));
        latLngNames.add(new LatLngName(-33.8875005, 151.1885718, "New Law School Lecture Theatre 104"));
        latLngNames.add(new LatLngName(-33.8909177, 151.1873761, "Institute"));
        latLngNames.add(new LatLngName(-33.8909177, 151.1873761, "Institute Lecture Theatre 1"));
        latLngNames.add(new LatLngName(-33.8887084, 151.1898652, "Wilkinson"));
        latLngNames.add(new LatLngName(-33.8887084, 151.1898652, "Wilkinson 212 Computing Studio"));
        latLngNames.add(new LatLngName(-33.8874844, 151.1863076, "R C Mills"));
        latLngNames.add(new LatLngName(-33.8892052, 151.1917779, "Mechanical Engineering"));
        latLngNames.add(new LatLngName(-33.8881587, 151.1885357, "Eastern Avenue Complex"));
        latLngNames.add(new LatLngName(-33.8893878, 151.191372, "Electrical Engineering"));
        latLngNames.add(new LatLngName(-33.8916508, 151.1889624, "Codrington Building"));
        latLngNames.add(new LatLngName(-33.8862956, 151.185613, "Christopher Brennan"));
        latLngNames.add(new LatLngName(-33.8910416, 151.1875091, "Storie Dixson"));
        latLngNames.add(new LatLngName(-33.8735803, 151.2084093, "Castlereagh Lecture Room 1602-1603"));
        latLngNames.add(new LatLngName(-33.8874136, 151.1780497, "Performance Studies Audio Visual Room"));
        latLngNames.add(new LatLngName(-33.885456, 151.1842563, "Heydon Laurence Lecture Theatre 217 (DT Anderson)"));
        latLngNames.add(new LatLngName(-33.8885158, 151.1753153, "Faculty of Nursing (Mallett St) Building B"));
        latLngNames.add(new LatLngName(-33.8885158, 151.1753153, "Faculty of Nursing (Mallett St) Building C"));
        latLngNames.add(new LatLngName(-33.8866012, 151.1817846, "R M C Gunn"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Seminar Room 3180"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Seminar Room 3170"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Case Study Lecture Theatre 1170"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Seminar Room 3200"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Seminar Room 3270"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Lecture Theatre 1130"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Seminar Room 1140"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Seminar Room 1100"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Case Study Lecture Theatre 2080"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Case Study Lecture Theatre 2090"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Case Study Lecture Theatre 2140"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Lecture Theatre 1040"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Case Study Lecture Theatre 1070"));
        latLngNames.add(new LatLngName(-33.8919692, 151.1890535, "ABS Seminar Room 2110"));
        latLngNames.add(new LatLngName(-33.8881587, 151.1885357, "Carslaw Lecture Theatre 273"));
        latLngNames.add(new LatLngName(-33.8881587, 151.1885357, "Carslaw Lecture Theatre 373"));
        latLngNames.add(new LatLngName(-33.8881587, 151.1885357, "Carslaw"));
        latLngNames.add(new LatLngName(-33.8881587, 151.1885357, "Carslaw Lecture Theatre 173"));
        latLngNames.add(new LatLngName(-33.8881587, 151.1885357, "Carslaw Lecture Theatre 175"));
        latLngNames.add(new LatLngName(-33.8547027, 150.9823621, "Westmead Hospital"));
        latLngNames.add(new LatLngName(-34.0526114, 150.6591789, "Centre for Carbon"));
        latLngNames.add(new LatLngName(-33.8907131, 151.190714, "Chemical Engineering"));
        latLngNames.add(new LatLngName(-33.8865254, 151.176547, "Mackie"));
        latLngNames.add(new LatLngName(-34.0327354, 150.6545372, "Liz Kernohan Conference Centre"));
        latLngNames.add(new LatLngName(-33.6002308, 150.7377058, "Castlereagh Seminar Room 1613"));
        latLngNames.add(new LatLngName(-33.8852137, 151.1855512, "Edgeworth David Geology Building"));
        latLngNames.add(new LatLngName(-33.88734, 151.1813448, "Charles Perkins Centre"));
        latLngNames.add(new LatLngName(-33.8886967, 151.1881474, "Madsen"));
        latLngNames.add(new LatLngName(-33.8921156, 151.1886925, "Abercrombie Business School"));
        latLngNames.add(new LatLngName(-33.8869003, 151.185017, "Education Building"));
        latLngNames.add(new LatLngName(-33.887914, 151.185448, "in Physics Road Learning Hub"));
        latLngNames.add(new LatLngName(-33.8892288, 151.1832764, "Bosch 1A"));
        latLngNames.add(new LatLngName(-33.8854515, 151.1842563, "Heydon-Laurence"));
        latLngNames.add(new LatLngName(-33.8634952, 151.2122373, "Sydney Conservatorium of Music"));
        latLngNames.add(new LatLngName(-33.8715217, 151.206851, "133 Castlereagh Street"));
        latLngNames.add(new LatLngName(-33.8889356, 151.1920115, "Aeronautical Engineering"));
        latLngNames.add(new LatLngName(-33.880089, 151.182783, "Woolley Seminar Room N208"));
        latLngNames.add(new LatLngName(-33.8876107, 151.1871451, "Chemistry"));
        latLngNames.add(new LatLngName(-33.8859203, 151.1868848, "Quadrangle Building"));
        latLngNames.add(new LatLngName(-33.8961019, 151.1939423, "Biomedical Building"));
        latLngNames.add(new LatLngName(-33.8851423, 151.185129, "Holme Building"));
        latLngNames.add(new LatLngName(-33.8903871, 151.1899977, "Biochemistry"));
        latLngNames.add(new LatLngName(-34.0715405, 150.6802433, "Camden General Teaching Building"));
        latLngNames.add(new LatLngName(-33.8862501, 151.1816064, "Veterinary Science Conference Centre"));
        latLngNames.add(new LatLngName(-33.8947538, 151.1991496, "SSB Seminar Room 105"));
        latLngNames.add(new LatLngName(-33.8870707, 151.1845592, "Education Annexe"));
        latLngNames.add(new LatLngName(-33.8776358, 151.2119517, "Norman Gregg Lecture Theatre"));
        latLngNames.add(new LatLngName(-33.8902182, 151.1910273, "Peter Nicol Russell"));
        latLngNames.add(new LatLngName(-33.885698, 151.1855663, "Pharmacy"));
        latLngNames.add(new LatLngName(-33.8871667, 151.1870113, "Anderson Stuart"));
        latLngNames.add(new LatLngName(-33.8901635, 151.1912009, "Civil & Mining Engineering"));
        latLngNames.add(new LatLngName(-33.8885287, 151.1922501, "School of Information Technologies Building"));
        latLngNames.add(new LatLngName(-33.8904886, 151.1880783, "Merewether"));
        latLngNames.add(new LatLngName(-33.8860821, 151.1841023, "John Woolley"));
        latLngNames.add(new LatLngName(-33.8883745, 151.1912633, "Seymour Centre"));
        latLngNames.add(new LatLngName(-33.8883134, 151.1855295, "Sydney Nanoscience Hub"));
        latLngNames.add(new LatLngName(-33.8859237, 151.1834555, "Wallace Theatre"));
        latLngNames.add(new LatLngName(-33.8881042, 151.1863765, "Edward Ford"));
        latLngNames.add(new LatLngName(-33.8867091, 151.1840589, "Teachers College Lecture Theatre 306"));
        latLngNames.add(new LatLngName(-33.8899035, 151.1902635, "Old School Building"));
        latLngNames.add(new LatLngName(-33.8859083, 151.1819295, "J D Stewart"));
        latLngNames.add(new LatLngName(-33.8867091, 151.1840589, "Old Teachers College"));
        latLngNames.add(new LatLngName(-33.8851974, 151.1831724, "Social Sciences Building"));
        latLngNames.add(new LatLngName(-33.8880955, 151.1849353, "Physics"));
        latLngNames.add(new LatLngName(-33.8858436, 151.1848921, "Badham"));
        latLngNames.add(new LatLngName(-33.8858999, 151.1780125, "Medical Foundation Building"));
    }


}
