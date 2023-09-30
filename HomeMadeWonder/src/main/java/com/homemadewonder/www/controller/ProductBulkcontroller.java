package com.homemadewonder.www.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Subcategory;
import com.homemadewonder.www.service.ProductService;

@RestController
@RequestMapping("/api/bulk")
public class ProductBulkcontroller {

	Logger logger = LoggerFactory.getLogger(ProductBulkcontroller.class);

	@Autowired
	private ProductService productService;

	@PostMapping("/upload/{subId}")
	public ResponseEntity<String> uploadFile(@PathVariable Subcategory subId,
			@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Please select a file to upload.");
		}

		try {
			String originalFilename = file.getOriginalFilename();
			if (originalFilename != null && originalFilename.endsWith(".xlsx")) {
				return handleExcelUpload(subId, file);
			}
		} catch (Exception e) {
			logger.error("An error occurred while processing the file.", e);
			return ResponseEntity.status(500).body("An error occurred while processing the file.");
		}

		return ResponseEntity.badRequest().body("Unsupported file format. Please upload an XLSX file.");
	}

	public ResponseEntity<String> handleExcelUpload(Subcategory subId, MultipartFile file) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);

			Row headerRow = sheet.getRow(0);
			if (headerRow == null) {
				return ResponseEntity.badRequest().body("Excel file is empty or missing headers.");
			}

			int productNameColumnIndex = -1;
			int productCostColumnIndex = -1;
			int productOfferColumnIndex = -1;
			int productQuantityColumnIndex = -1;
			int productDescriptionColumnIndex = -1;
			int discountPercentColumnIndex = -1;
			int brandColumnIndex = -1;
			int colorColumnIndex = -1;
			int numRatingsColumnIndex = -1;
			int imageColumnIndex = -1;

			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				Cell cell = headerRow.getCell(i);
				if (cell != null) {
					String columnHeader = cell.getStringCellValue().toLowerCase();
					switch (columnHeader) {
					case "productname":
						productNameColumnIndex = i;
						break;
					case "productcost":
						productCostColumnIndex = i;
						break;
					case "productoffer":
						productOfferColumnIndex = i;
						break;
					case "productquantity":
						productQuantityColumnIndex = i;
						break;
					case "productdescription":
						productDescriptionColumnIndex = i;
						break;
					case "discount_percent":
						discountPercentColumnIndex = i;
						break;
					case "brand":
						brandColumnIndex = i;
						break;
					case "color":
						colorColumnIndex = i;
						break;
					case "num_ratings":
						numRatingsColumnIndex = i;
						break;

					case "image": // Set the image column index
						imageColumnIndex = i;
						break;
					}
				}
			}

			if (productNameColumnIndex == -1 || productCostColumnIndex == -1) {
				return ResponseEntity.badRequest()
						.body("Missing 'productName' or 'productCost' columns in the Excel file.");
			}

			List<Product> productList = new ArrayList<>();

			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row != null) {
					Cell productNameCell = row.getCell(productNameColumnIndex);
					Cell productCostCell = row.getCell(productCostColumnIndex);
					Cell productOfferCell = row.getCell(productOfferColumnIndex);
					Cell productQuantityCell = row.getCell(productQuantityColumnIndex);
					Cell productDescriptionCell = row.getCell(productDescriptionColumnIndex);
					Cell discountPercentCell = row.getCell(discountPercentColumnIndex);
					Cell brandCell = row.getCell(brandColumnIndex);
					Cell colorCell = row.getCell(colorColumnIndex);
					Cell numRatingsCell = row.getCell(numRatingsColumnIndex);
					Cell imageCell = row.getCell(imageColumnIndex); // Get the image cell

					if (productNameCell != null && productCostCell != null) {
						String productName = getStringCellValue(productNameCell);
						double productCost = getNumericCellValue(productCostCell);
						String productOffer = getStringCellValue(productOfferCell);
						long productQuantity = getLongCellValue(productQuantityCell);
						String productDescription = getStringCellValue(productDescriptionCell);
						String discountPercent = getStringCellValue(discountPercentCell);
						String brand = getStringCellValue(brandCell);
						String color = getStringCellValue(colorCell);
						int numRatings = getIntCellValue(numRatingsCell);

						byte[] imageData = getImageDataFromCell(imageCell);

						Product product = new Product();
						product.setProductName(productName);
						product.setProductCost(productCost);
						product.setProductOffer(productOffer);
						product.setpImage(imageData);
						product.setProductQuantity(productQuantity);
						product.setProductDescription(productDescription);
						product.setDiscountPercent(discountPercent);
						discountPercent = discountPercent.replace("%", "");

						try {

							double discount = Double.parseDouble(discountPercent);

							if (discount >= 0 && discount <= 100) {

								double discountedPrice = productCost * (1 - (discount / 100.0));
								product.setDiscountedPrice(discountedPrice);
							} else {

							}
						} catch (NumberFormatException e) {

						}

						product.setBrand(brand);
						product.setColor(color);
						product.setNumRatings(numRatings);
						Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mma");
						String formattedDate = dateFormat.format(currentTimestamp);
						product.setCreatedAt(formattedDate);
						product.setSubcategory(subId);

						productList.add(product);
					}
				}
			}

			productService.saveProducts(productList);

			return ResponseEntity
					.ok("Excel file uploaded and data saved successfully size of your products ." + productList.size());
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body("An error occurred while processing the Excel file: " + e.getMessage());
		}
	}

	private String getStringCellValue(Cell cell) {
		if (cell != null) {
			if (cell.getCellType() == CellType.STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == CellType.NUMERIC) {

				DataFormatter dataFormatter = new DataFormatter();
				return dataFormatter.formatCellValue(cell);
			} else {
				return cell.toString();
			}
		}
		return null;
	}

	private double getNumericCellValue(Cell cell) {
		if (cell != null && cell.getCellType() == CellType.NUMERIC) {
			return cell.getNumericCellValue();
		}
		return 0.0;
	}

	private long getLongCellValue(Cell cell) {
		if (cell != null && cell.getCellType() == CellType.NUMERIC) {
			return (long) cell.getNumericCellValue();
		}
		return 0L;
	}

	private int getIntCellValue(Cell cell) {
		if (cell != null && cell.getCellType() == CellType.NUMERIC) {
			return (int) cell.getNumericCellValue();
		}
		return 0;
	}

	public byte[] getImageDataFromCell(Cell imageCell) {
		byte[] imageData = null;

		if (imageCell != null) {
			if (imageCell.getCellType() == CellType.STRING) {
				// If the cell contains binary data (image), extract it
				imageData = imageCell.getRichStringCellValue().getString().getBytes(StandardCharsets.ISO_8859_1);
			} else if (imageCell.getCellType() == CellType.STRING) {
				// If the cell contains a string representing binary data, you may need to
				// decode it
				String base64ImageData = imageCell.getStringCellValue();
				imageData = Base64.getDecoder().decode(base64ImageData);
			}
		}

		return imageData;
	}

}
