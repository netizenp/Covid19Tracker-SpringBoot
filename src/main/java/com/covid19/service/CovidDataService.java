package com.covid19.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.covid19.models.DataEntity;
@Service
public class CovidDataService {
	
	private static String VIRUS_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<DataEntity> stats = new ArrayList<>();
	
	public List<DataEntity> getStats() {
		return stats;
	}
	
	//execute this method after spring finish construct this class named CovidDataService
	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchData() throws IOException, InterruptedException {
		List<DataEntity> newstats = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA)).build();
		HttpResponse<String> httpResponse = client.send(request, /*responseBodyHandler*/HttpResponse.BodyHandlers.ofString());
		//System.out.println(httpResponse.body());
		//StringReader is a instance of reader which parse String
		StringReader csvReader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
		for (CSVRecord record : records) {
		    //String country = record.get("Country/Region");
		    //System.out.println(country);
		    DataEntity dataEntity = new DataEntity();
		    dataEntity.setCountry(record.get("Country/Region"));
		    dataEntity.setState(record.get("Province/State"));
		    int latestCases = Integer.parseInt((record.get(record.size() - 1)));
		    int lastDayCases = Integer.parseInt((record.get(record.size() - 2)));
		    dataEntity.setLatestTotalCases(latestCases);
		    dataEntity.setLastDayIncrease(latestCases - lastDayCases);
		    newstats.add(dataEntity);
		}
		this.stats = newstats;
	}
}