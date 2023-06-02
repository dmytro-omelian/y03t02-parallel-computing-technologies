package com.assignment.assignment8.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class FileReaderService {
    private static final Logger logger = LogManager.getLogger(FileReaderService.class);

    public double[][] getValues(String filename) throws FileNotFoundException {
        String path = "./data/" + filename + ".txt";
        File file = new File(path);
        double[][] result;
        if (file.exists() && !file.isDirectory()) {
            result = readFile(file);
            logger.info("Read data from " + path + " successfully.");
            return result;
        } else {
            throw new FileNotFoundException("There is no " + filename + " file to read. Please create it or check filename.");
        }
    }

    private double[][] readFile(File file) throws FileNotFoundException {
        List<double[]> resultList = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] numbers = line.trim().split(" ");
            double[] row = new double[numbers.length];

            for (int j = 0; j < numbers.length; ++j) {
                row[j] = Double.parseDouble(numbers[j]);
            }

            resultList.add(row);
        }

        double[][] result = new double[resultList.size()][];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }


}
