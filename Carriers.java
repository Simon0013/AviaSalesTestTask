import java.util.ArrayList;

public class Carriers {
	private ArrayList<Carrier> carriers = new ArrayList<Carrier>();
	
	public void addNewCarrier(String name) {
		boolean flag = false;
		for(Carrier item: carriers) {
			if (item.name.equals(name)) {
				flag = true;
				break;
			}
		}
		if (!flag) carriers.add(new Carrier(name));
	}
	public Carrier getByName(String name) {
		for(Carrier item: carriers) {
			if (item.name.equals(name)) {
				return item;
			}
		}
		return null;
	}
	public String printInfo() {
		StringBuffer content = new StringBuffer();
		content.append("Минимальное время полёта для каждого авиаперевозчика:\n");
		for(Carrier item: carriers) {
			content.append(item.name + ": ");
			long hour = item.getMinTravelTime() / 3600;
			long min = (item.getMinTravelTime() - hour * 3600) / 60;
			long sec = item.getMinTravelTime() - hour * 3600 - min * 60;
			content.append(hour + " ч " + min + " мин " + sec + " сек\n");
		}
		return content.toString();
	}
}
