package work.file;

import java.io.FileInputStream;
import java.io.File;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.ss.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// New data to be added
		String[] carArray = { "BMW", "Mer", "Toy", "Yah", "MID" };
		String excelFilePath = "src/main/java/work/file/input.xlsx";
		Map<Integer, List<String>> existingData = readExcelFile(excelFilePath);
		updateExcelFile(excelFilePath, existingData, carArray);

		String jsonFilePath = "src/main/java/work/file/data.json"; // Replace with your JSON file path
		readJsonFile(jsonFilePath);
		writeJsonFile(jsonFilePath);

	}

	private static Map<Integer, List<String>> readExcelFile(String filePath) {
		Map<Integer, List<String>> data = new HashMap<>();

		try (FileInputStream file = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(file)) {
			Sheet sheet = workbook.getSheetAt(0);

			int rowIndex = 0;
			for (Row row : sheet) {
				data.put(rowIndex, new ArrayList<>());
				int columnIndex = 0;
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case STRING:
						data.get(rowIndex).add(cell.getStringCellValue());
						break;
					case NUMERIC:
						data.get(rowIndex).add(Double.toString(cell.getNumericCellValue()));
						break;
					default:
						data.get(rowIndex).add(" ");
					}
					columnIndex++;
				}
				rowIndex++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Print the data for verification
		for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
			System.out.println("Row " + entry.getKey() + ": " + entry.getValue());
		}

		return data;

	}

	private static void updateExcelFile(String filePath, Map<Integer, List<String>> existingData,
			String[] newDataArray) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Sheet1");

			// Copy existing data to the file excel
			for (Map.Entry<Integer, List<String>> entry : existingData.entrySet()) {
				Row row = sheet.createRow(entry.getKey());
				int columnIndex = 0;
				for (String cellValue : entry.getValue()) {
					Cell cell = row.createCell(columnIndex++);
					cell.setCellValue(cellValue);
				}
			}

			// Update range (F5:F9)
			int rowIndex = 4; // Row 5 index 4
			for (int i = 0; i < newDataArray.length; i++) {
				Row existingRow = sheet.getRow(rowIndex);
				if (existingRow == null) {
					existingRow = sheet.createRow(rowIndex);
				}
				Cell newCell = existingRow.createCell(5); // Column F index 5
				newCell.setCellValue(newDataArray[i]);
				rowIndex++;
			}

			// Write to the existing file
			try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
				workbook.write(outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readJsonFile(String filePath) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(new File(filePath));

			// Read file
			System.out.println("JSON Content:");
			System.out.println(rootNode.toPrettyString());

			// Get value with Name of Key
			JsonNode dataNode = rootNode.path("data"); // Get key data
			JsonNode value = dataNode.path("att"); // Get key of Data ( second key)
			System.out.println("Value of 'data': " + value);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeJsonFile(String filePath) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			// Create a sample JSON data (Map)
			 Map<String, Object> jsonData = new HashMap<>();
		        jsonData.put("key", "text");
		        jsonData.put("h1", "heading");

		        Map<String, Object> data = new HashMap<>();
		        data.put("name", "Dat");
		        data.put("age", "10");

		        // Att is an array, so we create a List
		        List<String> attList = new ArrayList<>();
		        attList.add("handsome");
		        attList.add("cute");
		        attList.add("rich");
		        data.put("att", attList);

		        jsonData.put("data", data);

			// Write JSON data to file
			objectMapper.writeValue(new File(filePath), jsonData);

			System.out.println("JSON data written to file successfully.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
