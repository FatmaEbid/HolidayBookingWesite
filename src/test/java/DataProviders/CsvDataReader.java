package DataProviders;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CsvDataReader {
	public static List<String[]> getTestData(String filePath) {
		List<String[]> data = new ArrayList<>();
		try (FileReader reader = new FileReader(filePath)) {
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
			for (CSVRecord record : records) {
				String[] row = {
						record.get("title"),
						record.get("fullName"),
						record.get("day"),
						record.get("month"),
						record.get("year"),
						record.get("phoneNum"),
						record.get("email1"),
						record.get("email2"),
						record.get("addressLine1"),
						record.get("addressLine2"),
						record.get("addressLine3"),
						record.get("postCode"),
						record.get("assertionFlag")
				};
				data.add(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
