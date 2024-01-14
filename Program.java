import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Scanner;

public class Program {
	static String datafile;
	static String resultsfile;
	
	public static String readFromFile(String filename) {
		try {
			FileReader reader = new FileReader(filename);
			StringBuffer content = new StringBuffer();
			int c;
			while ((c = reader.read()) != -1) {
				content.append((char)c);
			}
			reader.close();
			return content.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static void writeToFile(String filename, String content) {
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(content);
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int calculateMedian(ArrayList<Integer> seq) {
		seq.sort(null);
		int n = seq.size();
		if (n % 2 == 1) {
			return seq.get(n/2);
		}
		else {
			return (seq.get(n/2-1) + seq.get(n/2)) / 2;
		}
	}
	
	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
	        System.out.print("Введите путь к файлу с данными: ");
	        datafile = in.nextLine();
	        System.out.print("Введите путь, куда надо записать результаты: ");
	        resultsfile = in.nextLine();
	        in.close();
			
			Gson gson = new Gson();
			JsonObject parser = gson.fromJson(readFromFile(datafile), JsonObject.class);
			JsonArray array = parser.get("tickets").getAsJsonArray();
			Carriers carriers = new Carriers();
			ArrayList<Integer> prices = new ArrayList<Integer>();
			int priceSum = 0;
			
			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = (JsonObject)array.get(i);
				if (obj.get("origin").getAsString().equals("VVO") && obj.get("destination").getAsString().equals("TLV")) {
					carriers.addNewCarrier(obj.get("carrier").getAsString());
					Carrier carrier = carriers.getByName(obj.get("carrier").getAsString());
					carrier.setSchedule(obj.get("departure_date").getAsString(), obj.get("departure_time").getAsString(), obj.get("arrival_date").getAsString(), obj.get("arrival_time").getAsString());
					priceSum += obj.get("price").getAsInt();
					prices.add(obj.get("price").getAsInt());
				}
				
			}
			
			double averagePrice = (double)priceSum / prices.size();
			int median = calculateMedian(prices);
			StringBuffer content = new StringBuffer();
			content.append(carriers.printInfo());
			content.append("\nРазница между средней ценой и медианой: " + (averagePrice-median));
			writeToFile(resultsfile, content.toString());
			System.out.println("Completed successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
