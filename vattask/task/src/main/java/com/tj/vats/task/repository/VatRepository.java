package com.tj.vats.task.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.tj.vats.task.model.CountryVats;
import com.tj.vats.task.model.Rate;
import com.tj.vats.task.model.VatResponse;

@Repository
public class VatRepository {
	
	/**
	 * Fetch VAT data from the net.
	 * @return list of CountryVats objects.
	 */
	public List<CountryVats> getSourceData(Date date) {
		RestTemplate restTemplate = new RestTemplate();
		VatResponse vats = restTemplate.getForObject("http://jsonvat.com/", VatResponse.class);
		return convertToDao(vats.getRates(), date);
	}
	
	/**
	 * Converts from Rate object to CountryVats object for easier data manipulation.
	 * @param rates List of Rate object to convert.
	 * @param date Data for which to fetch VAT rates.
	 * @return List of CountryVats objects.
	 */
	private List<CountryVats> convertToDao(List<Rate> rates, Date date){
		List<CountryVats> countries = new ArrayList<>(rates.size());

		rates.forEach(rate -> {
			CountryVats c = new CountryVats();
			c.setCode(rate.getCode());
			c.setContryCode(rate.getCountryCode());
			c.setName(rate.getName());
			c.setRates(rate.getRatesByDate(date));
			countries.add(c);
		});
		
		return countries;
	}

}
