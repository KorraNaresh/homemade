package com.homemadewonder.www.serviceimpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Deal;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.excpetion.ProductNotFoundException;
import com.homemadewonder.www.repository.DealRepositiory;
import com.homemadewonder.www.repository.ProductRepository;
import com.homemadewonder.www.request.DealRequestDTO;
import com.homemadewonder.www.service.DealService;

import jakarta.transaction.Transactional;

@Service
public class DealServiceimpl implements DealService {

	@Autowired
	private DealRepositiory dealReposititory;

	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional
	public void createDeal(DealRequestDTO dealRequestDTO) {

		Deal deal = new Deal();

		Product product = productRepository.findById(dealRequestDTO.getProductId())
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));

		String discountPercent = dealRequestDTO.getNewDiscountPercent();

		try {
			discountPercent = discountPercent.replace("%", "");
			double discount = Double.parseDouble(discountPercent);


			if (discount >= 0 && discount <= 100) {

				double discountedPrice = dealRequestDTO.getNewProductCost() * (1 - (discount / 100.0));
				product.setDiscountedPrice(discountedPrice);
			} else {

				throw new IllegalArgumentException("Invalid discount percentage. It must be between 0 and 100.");
			}
		} catch (NumberFormatException e) {

			throw new IllegalArgumentException("Invalid discount percentage format. It should be a valid number.");
		}

		product.setProductCost(dealRequestDTO.getNewProductCost());
		product.setDiscountPercent(discountPercent);

		productRepository.save(product);

		deal.setProduct(product);
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mma");
		String formattedDate = dateFormat.format(currentTimestamp);

		deal.setCreatedate(formattedDate);
		deal.setOfferEndDate(dealRequestDTO.getOfferEndDate());

		LocalDate current = LocalDate.now();
		LocalDate offerEndDate = dealRequestDTO.getOfferEndDate();
		if (current.isBefore(offerEndDate)) {
			deal.setOfferAvailability("New Offer");
		} else {
			deal.setOfferAvailability("Offer Over");
		}

		dealReposititory.save(deal);
	}

	@Scheduled(cron = "0 0 */6 * * *")
	public void updateOfferAvailability() {
		List<Deal> deals = dealReposititory.findAll();

		for (Deal deal : deals) {
			LocalDate current = LocalDate.now();
			LocalDate offerEndDate = deal.getOfferEndDate();

			if (current.isBefore(offerEndDate)) {
				deal.setOfferAvailability("New Offer");
			} else {
				deal.setOfferAvailability("Offer Over");
			}

			dealReposititory.save(deal);
		}
	}

	@Override
	public List<Product> getProductsByDealCreatedate(String createdate) {
		List<Deal> deals = dealReposititory.findAllByCreatedate(createdate);

		List<Product> products = deals.stream().map(Deal::getProduct).collect(Collectors.toList());

		return products;
	}

	@Override
	public List<Deal> getalldeals() {

		return dealReposititory.findAll();
	}

	@Override
	public long countall() {

		return dealReposititory.count();
	}
	
	
	@Override
	public List<Deal> getAllDealsByOfferAvailability(String offerAvailability) {
		return dealReposititory.findByOfferAvailability(offerAvailability);
	}
	
	

}
