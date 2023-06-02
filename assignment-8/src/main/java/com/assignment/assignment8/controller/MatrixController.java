package com.assignment.assignment8.controller;

import com.assignment.assignment8.entity.MatrixMultiplicationResponse;
import com.assignment.assignment8.service.FileReaderService;
import com.assignment.assignment8.service.FileWriterService;
import com.assignment.assignment8.service.MatrixService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/matrix")
@CrossOrigin(origins = "*")
public class MatrixController {
    private static final Logger logger = LogManager.getLogger(MatrixController.class);

    private final MatrixService matrixService;
    private final FileWriterService writerService;
    private final FileReaderService readerService;

    @Autowired
    public MatrixController(MatrixService matrixService, FileWriterService writerService, FileReaderService readerService) {
        this.matrixService = matrixService;
        this.writerService = writerService;
        this.readerService = readerService;
    }

    @GetMapping("/multiply-server")
    public ResponseEntity<?> multiplyOnServer(
            @RequestParam String matrixAFilename,
            @RequestParam String matrixBFilename) {

        try {
            double[][] matrixA = readerService.getValues(matrixAFilename);
            double[][] matrixB = readerService.getValues(matrixBFilename);

            double[][] result = matrixService.multiply(matrixA, matrixB);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MatrixMultiplicationResponse(result));
        } catch (FileNotFoundException e) {
            logger.error("file was not found, see: " + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found.");
        }
    }

    @PostMapping("/multiply-client")
    public ResponseEntity<?> multiplyFromFiles(
            @RequestParam MultipartFile matrixA,
            @RequestParam MultipartFile matrixB) {
        try {
            double[][] result = matrixService.multiply(matrixA, matrixB);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MatrixMultiplicationResponse(result));
        } catch (FileNotFoundException e) {
            logger.error("file was not found, see: " + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found.");
        } catch (IOException e) {
            logger.error("error occurred, see: " + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something went wrong while reading files.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMatrix(
            @RequestParam String filename,
            @RequestParam int rows,
            @RequestParam int columns) {
        double[][] matrix = matrixService.generate(rows, columns);
        try {
            writerService.save(filename, matrix, true);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Matrix was created and saved to \"" + filename + "\" file");
        } catch (IOException e) {
            logger.error("Couldn't save matrix to file, see: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Matrix was not saved. Please try again.");
        }
    }

}
