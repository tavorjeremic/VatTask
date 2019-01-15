package com.tj.vats.task.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.vats.task.model.CountryVats;
import com.tj.vats.task.model.Rates;
import com.tj.vats.task.repository.VatRepository;

@Service
public class VatsService {
	
	@Autowired
	private VatRepository vatRepo;
	
	/**
	 * Get all countries with rates valid on specific date sorted by standard rate ascending.
	 * @param date Date for which to fetch rates.
	 * @return all countries with rates valid on specific date.
	 */
	public List<CountryVats> getRatesByStandard(Date date){
		return getRatesByStandard(null, true, date);
	}

	/**
	 * Get specific number of countries with rates valid on specific date sorted by standard rate.
	 * @param limit How many countries to fetch (if null it will default to all countries).
	 * @param asc true for ascending or false for descending sorting.
	 * @param date Date for which to fetch rates.
	 * @return specific number of countries with rates valid on specific date sorted by standard rate.
	 */
	public List<CountryVats> getRatesByStandard(Integer limit, boolean asc, Date date){
		
		List<CountryVats> countries = vatRepo.getSourceData(date);
		
		if(limit == null || limit > countries.size()) {
			limit = countries.size();
		} else if(limit < 0 ) {
			limit = 0;
		}

		Collections.sort(countries, new Comparator<CountryVats>() {
			@Override
			public int compare(CountryVats o1, CountryVats o2) {
				Rates r1 = o1 == null?null:o1.getRates();
				Rates r2 = o2 == null?null:o2.getRates();
				if((o1 == null || r1 == null) && (o2==null || r2 == null)) {
					return 0;
				}else if(o1 == null || r1 == null) {
					return 1;
				} else if(o2 == null || r2 == null) {
					return -1;
				}
				return (int) (o1.getRates().getStandard() - o2.getRates().getStandard());
			}
		});
		if(!asc) {
			Collections.reverse(countries);
		}
		return countries.subList(0, limit);
	}

}
