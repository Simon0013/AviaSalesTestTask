import java.time.Duration;
import java.time.LocalDateTime;

public class Carrier {
	public String name;
	private long minTravelTimeInSec;
	
	public Carrier(String name_) {
		name = name_;
		minTravelTimeInSec = -1;
	}
	
	private LocalDateTime getDateTime(String dateStr, String timeStr) {
		String[] arr = dateStr.split("\\.");
		int day = Integer.parseInt(arr[0]);
		int month = Integer.parseInt(arr[1]);
		int year = 2000 + Integer.parseInt(arr[2]);
		
		arr = timeStr.split(":");
		int hour = Integer.parseInt(arr[0]);
		int minutes = Integer.parseInt(arr[1]);
		int seconds = 0;
		if (arr.length == 3)
			seconds = Integer.parseInt(arr[2]);
		
		return LocalDateTime.of(year, month, day, hour, minutes, seconds);
	}
	
	public void setSchedule(String dateStrDepart, String timeStrDepart, String dateStrArrival, String timeStrArrival) {
		LocalDateTime departure = getDateTime(dateStrDepart, timeStrDepart);
		LocalDateTime arrival = getDateTime(dateStrArrival, timeStrArrival);
		
		long travelTimeInSec = Duration.between(departure, arrival).toSeconds();
		if ((travelTimeInSec < minTravelTimeInSec) || (minTravelTimeInSec == -1)) {
			minTravelTimeInSec = travelTimeInSec;
		}
	}
	public long getMinTravelTime() {
		return minTravelTimeInSec;
	}
}
