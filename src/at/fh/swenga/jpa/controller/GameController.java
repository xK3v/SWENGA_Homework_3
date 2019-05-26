package at.fh.swenga.jpa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.DeveloperRepository;
import at.fh.swenga.jpa.dao.GameRepository;
import at.fh.swenga.jpa.model.DeveloperModel;
import at.fh.swenga.jpa.model.GameModel;

@Controller
public class GameController {

	@Autowired
	GameRepository gameRepository;

	@Autowired
	DeveloperRepository developerRepository;

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<GameModel> games = gameRepository.findAll();
		model.addAttribute("games", games);
		model.addAttribute("count", games.size());
		return "index";
	}

	@RequestMapping(value = { "/getPage" })
	public String getPage(Pageable page, Model model) {
		Page<GameModel> gamesPage = gameRepository.findAll(page);
		model.addAttribute("gamesPage", gamesPage);
		model.addAttribute("games", gamesPage.getContent());
		model.addAttribute("count", gamesPage.getTotalElements());
		return "index";
	}

	@RequestMapping(value = { "/find" })
	public String find(Model model, @RequestParam String searchString, @RequestParam String searchType) {
		List<GameModel> games = null;
		int count = 0;

		switch (searchType) {
		case "query1":
			games = gameRepository.findAll();
			break;
		case "query2":
			games = gameRepository.findByName(searchString);
			break;
		case "query4":
			games = gameRepository.findByWhateverName(searchString);
			break;
		case "query5":
			games = gameRepository.doANameSearchWithLike("%" + searchString + "%");
			break;
		case "query6":
			count = gameRepository.countByName(searchString);
			break;
		case "query7":
			games = gameRepository.removeByName(searchString);
			break;
		case "query8":
			count = gameRepository.deleteByDeveloperName(searchString);
			break;
		case "query9":
			games = gameRepository.findByNameContainingOrDeveloperNameContainingAllIgnoreCase(searchString,
					searchString);
			break;
		case "query10":
			games = gameRepository.findByOrderByDeveloperNameAsc();
			// employees=employeeRepository.findAllByOrderByLastNameAsc();
			break;
		case "query11":
			games = gameRepository.findTop10ByOrderByName();
			break;

		case "query12":
			games = gameRepository.findByDeveloperNameOrderByNameAsc(searchString);
			break;
		case "query13":
			Calendar nowMinus40 = Calendar.getInstance();
			nowMinus40.add(Calendar.YEAR, -40);
			games = gameRepository.findByReleaseDateAfter(nowMinus40);
			break;
		case "query14":
			Calendar year1980 = Calendar.getInstance();
			year1980.set(1980, 0, 1);
			Calendar year1985 = Calendar.getInstance();
			year1985.set(1985, 11, 31);
			games = gameRepository.findByReleaseDateBetween(year1980, year1985);
			break;
		case "query15":
			games = gameRepository.findByDeveloperName(searchString);
			break;

		default:
			games = gameRepository.findAll();
		}

		model.addAttribute("games", games);

		if (games != null) {
			model.addAttribute("count", games.size());
		} else {
			model.addAttribute("count", count);
		}
		return "index";
	}

	@RequestMapping(value = { "/findById" })
	public String findById(@RequestParam("id") GameModel e, Model model) {
		if (e != null) {
			List<GameModel> games = new ArrayList<GameModel>();
			games.add(e);
			model.addAttribute("games", games);
		}
		return "index";
	}

	@RequestMapping("/fillGameList")
	@Transactional
	public String fillData(Model model) {

		DataFactory df = new DataFactory();

		DeveloperModel developer = null;

		for (int i = 0; i < 100; i++) {
			if (i % 10 == 0) {
				String developerName = df.getBusinessName();
				developer = developerRepository.findFirstByName(developerName);
				if (developer == null) {
					developer = new DeveloperModel(developerName);
				}
			}

			Calendar rd = Calendar.getInstance();
			rd.setTime(df.getBirthDate());

			GameModel gameModel = new GameModel(df.getFirstName(), rd);
			gameModel.setDeveloper(developer);
			gameRepository.save(gameModel);
		}

		return "forward:list";
	}

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		gameRepository.deleteById(id);

		return "forward:list";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
}
