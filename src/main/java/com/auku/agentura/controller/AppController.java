package com.auku.agentura.controller;

import com.auku.agentura.entity.*;
import com.auku.agentura.service.AgencyService;
import com.auku.agentura.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppController {

	@Autowired
	private AgencyService agencyService;

	private Logger logger = LoggerFactory.getLogger(AppController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/owners", method = RequestMethod.GET)
	public String showOwners(Model model) {
		var list = agencyService.getOwners();
		model.addAttribute("owners", list);
		return "list-owners";
	}

	@RequestMapping(value = "/houses", method = RequestMethod.GET)
	public String showHouses(Model model) {
		var list = agencyService.getHouses();
		model.addAttribute("houses", list);
		return "list-houses";
	}

	@RequestMapping(value = "/searchOwners", method = RequestMethod.GET)
	public String search(@RequestParam("ownersSearchInput") String searchItem, Model model) {
		List<Owner> searchResult = agencyService.searchOwners(searchItem);
		model.addAttribute("owners", searchResult);
		return "list-owners";
	}

	@RequestMapping(value = "/showAddOwnerForm", method = RequestMethod.GET)
	public String showAddOwnerForm(Model model) {
		Owner owner = new Owner();
		model.addAttribute("owner", owner);

		return "add-owner-form";
	}

	@RequestMapping(value = "/saveOwner", method = RequestMethod.POST)
	public String saveOwner(@ModelAttribute("owner") Owner owner, Model model) {
		ResponseWrapper response = agencyService.saveOwner(owner);
		if(!response.isSuccess()){
			model.addAttribute("error", response);
			logger.error("Could not create new owner: " + response.getDetailedErrorMessage());
		}
		logger.info("Created owner " + owner);

		return "redirect:/owners";
	}

	@RequestMapping(value = "/showFormUpdate", method = RequestMethod.GET)
	public String updateOwner(@RequestParam("id") int id, Model model) {
		Owner owner = agencyService.getOwner(id);
		model.addAttribute("owner", owner);

		return "update-owner-form";
	}

	@RequestMapping(value = "/updateOwner", method = RequestMethod.POST)
	public String updateOwner(@ModelAttribute("owner") Owner owner, @RequestParam("id") int id) {
		agencyService.update(owner, id);

		return "redirect:/owners";
	}

	@RequestMapping(value = "/deleteOwner", method = RequestMethod.GET)
	public String deleteOwner(@ModelAttribute("ownerId") int id) {
		agencyService.deleteOwner(id);

		return "redirect:/owners";
	}

	@RequestMapping(value = "/houseData", method = RequestMethod.GET)
	public String getHouseData(Model model) {
		List<HouseDataEntity> list = agencyService.getHouseData();
		model.addAttribute("houseData", list);

		return "house-main-data";
	}

	@RequestMapping(value = "/searchHouseInfo", method = RequestMethod.GET)
	public String searchHouseInfo(@RequestParam("ownersSearchInput") String searchItem, Model model) {
		List<HouseDataEntity> searchResult = agencyService.searchHouseInfo(searchItem);
		model.addAttribute("houseData", searchResult);
		return "house-main-data";
	}
	
	@RequestMapping(value = "/searchHouses", method = RequestMethod.GET)
	public String searchHouses(@RequestParam("input") String searchItem, Model model) {
		List<House> result = agencyService.searchHouses(searchItem);
		model.addAttribute("houses", result);
		
		return "list-houses";
	}

	@RequestMapping(value = "/ownersSuggestions", method = RequestMethod.GET)
	public @ResponseBody
	List<String> ownersSuggestions(@RequestParam("term") String query) {
		var owners = agencyService.getOwners();
		List<String> foundNames = new ArrayList<>();
		for (Owner owner : owners) {
			if (owner.getName().toLowerCase().startsWith(query.toLowerCase())){
				foundNames.add(owner.getName());
			}
			if (owner.getSurname().toLowerCase().startsWith(query.toLowerCase())){
				foundNames.add(owner.getSurname());
			}
			if (owner.getAddress().toLowerCase().startsWith(query.toLowerCase())){
				foundNames.add(owner.getAddress());
			}
		}

		return foundNames;
	}

	@RequestMapping(value = "/houseDataSuggestions", method = RequestMethod.GET)
	public @ResponseBody
	List<String> houseDataSuggestions(@RequestParam("term") String query) {
		var houses = agencyService.getHouseData();
		List<String> foundData = new ArrayList<>();
		for (var item : houses) {
			if (item.getAddress() != null && item.getAddress().toLowerCase().startsWith(query.toLowerCase())) {
				foundData.add(item.getAddress());
			}
			if (item.getAgentSurname() != null && item.getAgentSurname().toLowerCase().startsWith(query.toLowerCase())) {
				foundData.add(item.getAgentSurname());
			}
			if (item.getOwnerName() != null && item.getOwnerName().toLowerCase().startsWith(query.toLowerCase())) {
				foundData.add(item.getOwnerName());
			}
			if (item.getOwnerSurname() != null && item.getOwnerSurname().toLowerCase().startsWith(query.toLowerCase())) {
				foundData.add(item.getOwnerSurname());
			}
		}
		foundData = foundData.stream().distinct().collect(Collectors.toList());
		return foundData;
	}
	
	@RequestMapping(value = "/housesSuggestions", method = RequestMethod.GET)
	public @ResponseBody
	List<String> housesSuggestions(@RequestParam("term") String query) {
		var houses = agencyService.getHouses();
		List<String> searchResult = new ArrayList<>();
		for (var house : houses) {
			if (house.getAddress().toLowerCase().startsWith(query.toLowerCase())) {
				searchResult.add(house.getAddress());
			}
		}
		
		return searchResult;
	}

	@RequestMapping(value = "/showBuyHouse", method = RequestMethod.GET)
	public String showBuyHouse(@RequestParam("id") String id, Model model) {
		House house = agencyService.getHouse(id);
		model.addAttribute("house", house);
		List<Owner> suggestedOwners = agencyService.getAvailableOwners();
		model.addAttribute("suggestedOwners", suggestedOwners);

		return "show-buy-house";
	}

	@RequestMapping(value = "/sellHouse", method = RequestMethod.POST)
	public String sellHouseToOwner(@RequestParam("chosenOwner") int id, @ModelAttribute("house") House house) {
		agencyService.sellHouse(house, id);

		return "redirect:/houses";
	}

	@RequestMapping(value = "/showSellHouse", method = RequestMethod.GET)
	public String showSellHouse(@RequestParam("id") String id, Model model) {
		House house = agencyService.getHouse(id);
		model.addAttribute("house", house);
		List<Agent> agents = agencyService.getAgents();
		model.addAttribute("agents", agents);
		String owner = agencyService.getHouseOwner(house);
		model.addAttribute("owner", owner);
		return "show-sell-house";
	}

	@RequestMapping(value = "/sellOwnersHouse", method = RequestMethod.POST)
	public String sellOwnersHouse(@RequestParam("chosenAgent") int id, @ModelAttribute("house") House house) {
		agencyService.sellOwnersHouse(house, id);

		return "redirect:/houses";
	}

	@RequestMapping(value = "/showAddHouseForm", method = RequestMethod.GET)
	public String showAddHouseForm(Model model) {
		House house = new House();
		model.addAttribute("house", house);

		return "add-house-form";
	}

	@RequestMapping(value = "/saveHouse", method = RequestMethod.POST)
	public String saveHouse(@ModelAttribute("house") House house, Model model) {
		agencyService.saveHouse(house);

		return "redirect:/houses";
	}

	@RequestMapping(value = "/deletelHouse", method = RequestMethod.GET)
	public String deletelHouse(@ModelAttribute("id") String address) {
		agencyService.deleteHouse(address);

		return "redirect:/houses";
	}
}
