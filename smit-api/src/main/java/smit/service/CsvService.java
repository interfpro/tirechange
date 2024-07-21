package smit.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import smit.model.Workshop;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    public List<Workshop> getWorkshops() {
        return readCsvFile(getResourceFilePath("workshops.csv"));
    }

    public void editWorkshop(Workshop workshop) {
        String filePath = getResourceFilePath("workshops.csv");
        List<Workshop> workshops = readCsvFile(filePath);
        updateRow(workshops, Integer.parseInt(workshop.getId()), workshop);
        writeCsvFile(filePath, workshops);
    }

    public List<Workshop> readCsvFile(String filePath) {
        List<Workshop> workshops = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            List<String[]> items = reader.readAll();
            for (String[] item : items) {
                workshops.add(new Workshop(item[0], item[1], item[2], item[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workshops;
    }

    public void updateRow(List<Workshop> workshops, int rowIndex, Workshop newWorkshop) {
        if (rowIndex >= 0 && rowIndex < workshops.size()) {
            workshops.set(rowIndex, newWorkshop);
        } else {
            System.out.println("Row index out of bounds");
        }
    }

    public void writeCsvFile(String filePath, List<Workshop> workshops) {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (Workshop workshop : workshops) {
                String[] item = { workshop.getId(), workshop.getName(), workshop.getAddress(), workshop.getTypes() };
                writer.writeNext(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  String getResourceFilePath(String fileName) {
        ClassLoader classLoader = CsvService.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found! " + fileName);
        } else {
            try {
                Path path = Paths.get(resource.toURI());
                return path.toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
