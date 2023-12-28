package com.spring.finaldemo.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;


import com.opencsv.exceptions.CsvException;
import com.spring.finaldemo.repository.ProductRepository;
import com.spring.finaldemo.entity.Category;
import com.spring.finaldemo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{productId}")
    public Optional<Product> getProductById(@PathVariable Long productId) {
        return productRepository.findById(productId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> getAllProductsWithSortAndPagination(
            @RequestParam("name") String search,
            @RequestParam(name = "sort", defaultValue = "productId") String sort,
            @RequestParam(name = "order", defaultValue = "asc") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        // Sorting
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort sortObj = Sort.by(direction, sort);

        // Pagination
        Pageable pageable = PageRequest.of(page, size, sortObj);

        // Query
        Page<Product> productPage = productRepository.findByProductNameContainingIgnoreCase(search, pageable);

        return ResponseEntity.ok(productPage.getContent());
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<List<Product>> searchProductsByCategoryWithSortAndPagination(
            @RequestParam("categoryName") String categoryName,
            @RequestParam(name = "sort", defaultValue = "productId") String sort,
            @RequestParam(name = "order", defaultValue = "asc") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        // Sorting
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort sortObj = Sort.by(direction, sort);

        // Pagination
        Pageable pageable = PageRequest.of(page, size, sortObj);

        // Query
        Page<Product> productPage = productRepository.findByCategory_CategoryNameContainingIgnoreCase(categoryName, pageable);

        return ResponseEntity.ok(productPage.getContent());
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productRepository.save(product);
        return new ResponseEntity<>("Update product successful.", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> createProductWithCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("File CSV empty.", HttpStatus.BAD_REQUEST);
        }

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            // Read file csv
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> rows = csvReader.readAll();

            // Import product from csv
            for (String[] row : rows) {
                String[] columns = row[0].split(";");

                if (columns.length >= 3) {
                    String productName = columns[0];
                    double price = Double.parseDouble(columns[1]);
                    long cateID = Long.parseLong(columns[2]);

                    Category category = new Category();

                    Product newProduct = new Product(productName, price, category);
                    category.setCategoryId(cateID);

                    productRepository.save(newProduct);
                } else {
                    return new ResponseEntity<>("Import product from file successful.", HttpStatus.OK);
                }
            }

            return new ResponseEntity<>("Import product from file successful.", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something error when handle file CSV.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportProductsToCSV() {
        try {
            List<Product> productList = productRepository.findAll();

            // Created StringWriter to write data
            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);

            // Header of file CSV
            csvWriter.writeNext(new String[]{"Product Name", "Price", "Category"});

            // Write each product to file
            for (Product product : productList) {
                String[] data = new String[]{
                        product.getProductName(),
                        String.valueOf(product.getPrice()),
                        product.getCategory().getCategoryName()
                };
                csvWriter.writeNext(data);
            }

            // CSV Close and return with UTF-8
            csvWriter.close();
            byte[] csvBytes = stringWriter.toString().getBytes(StandardCharsets.UTF_8);

            // Setting header for response HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "products.csv");

            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        if (productRepository.existsById(productId)) {
            updatedProduct.setProductId(productId);
            productRepository.save(updatedProduct);
            return new ResponseEntity<>("Update product successful.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something error ...", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productRepository.deleteById(productId);
        return new ResponseEntity<>("Delete product successful.", HttpStatus.OK);
    }
}
