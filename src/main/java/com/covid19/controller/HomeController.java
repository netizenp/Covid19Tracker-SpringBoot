package com.covid19.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.covid19.models.DataEntity;
import com.covid19.service.CovidDataService;

@Controller
public class HomeController {
	
	@Autowired
	CovidDataService covidDataService;
	
	//@RequestMapping(method = RequestMethod.GET)
	@GetMapping("/")
	public String home(Model model) {
		List<DataEntity> allStats = covidDataService.getStats();
		int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getLastDayIncrease()).sum();
		//System.out.println(totalNewCases);
		model.addAttribute("stats",allStats);
		model.addAttribute("totalcases",totalCases);
		model.addAttribute("totalNewCases",totalNewCases);
		return "home";
	}
}