package ca.mcgill.ecse321.TreeBook.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.TreeBook.dto.BooleanDto;
import ca.mcgill.ecse321.TreeBook.dto.ForecastTreeDto;
import ca.mcgill.ecse321.TreeBook.dto.LocationDto;
import ca.mcgill.ecse321.TreeBook.dto.TreeDto;
import ca.mcgill.ecse321.TreeBook.dto.UserDto;
import ca.mcgill.ecse321.TreeBook.model.ForecastTree;
import ca.mcgill.ecse321.TreeBook.model.Location;
import ca.mcgill.ecse321.TreeBook.model.SustainabilityAttributes;
import ca.mcgill.ecse321.TreeBook.model.Tree;
import ca.mcgill.ecse321.TreeBook.model.Tree.KindOfLandUse;
import ca.mcgill.ecse321.TreeBook.model.Tree.Status;
import ca.mcgill.ecse321.TreeBook.model.User;
import ca.mcgill.ecse321.TreeBook.service.InvalidInputException;
import ca.mcgill.ecse321.TreeBook.service.TreeBookService;

@RestController
public class TreeBookRestController {
	@Autowired
	private TreeBookService service;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping("/")
	public String index() 
	{
		return "TreeBook application root. Web-based frontend is a TODO. Use the REST API to manage events and participants.\n";
	}

	// Conversion methods (not part of the API)
	private TreeDto convertToDto(Tree t) 
	{
		// In simple cases, the mapper service is convenient
		return modelMapper.map(t, TreeDto.class);
	}

	private ForecastTreeDto convertToDto(ForecastTree t) 
	{
		return modelMapper.map(t, ForecastTreeDto.class);
	}

	private UserDto convertToDto(User u) 
	{
		UserDto userDto = modelMapper.map(u, UserDto.class);
		return userDto;
	}

	private LocationDto convertToDto(Location L) 
	{
		LocationDto locationDto = modelMapper.map(L, LocationDto.class);
		return locationDto;
	}

	private User convertToDomainObject(UserDto uDto) 
	{
		// Mapping DTO to the domain object without using the mapper
		List<User> allUsers = service.getAllUsers();
		for (User user : allUsers) 
		{
			if (user.getName().equals(uDto.getName())) 
			{
				return user;
			}
		}
		return null;
	}

	//HTML links to access backend from frontend:

	@PostMapping(value = { "/users/{name}", "/users/{name}/" })
	public UserDto createUser(
			@PathVariable("name") String name,
			@RequestParam String phoneNumber,
			@RequestParam String password) throws InvalidInputException 
	{
		User user = service.createUser(phoneNumber, name,password);
		return convertToDto(user);
	}

	@PostMapping(value = { "/file/", "/file" })
	public void addByFile(
			@RequestParam String fileLocation,
			@RequestParam String username
			//			@RequestBody String data
			) throws InvalidInputException 
	{
		//		System.out.println(data);
		File file = new File(fileLocation);
		//File file = new File("/home/student/Downloads/" + filename);
		service.saveFromFile(file,service.findUserByUsername(username));
	}

	@PostMapping(value = { "/usersSuper/{name}", "/users/{name}/" })
	public UserDto createSuperUser(
			@PathVariable("name") String name,
			@RequestParam String phoneNumber,
			@RequestParam String password) throws InvalidInputException 
	{
		User user = service.createUser(phoneNumber, name,password,true);
		return convertToDto(user);
	}

	@PostMapping(value = { "/trees/newTree", "/trees/newTree/" })
	public TreeDto plantTree(
			@RequestParam String species,
			@RequestParam boolean toBeChopped,
			@RequestParam String stringStatus,
			@RequestParam float height,
			@RequestParam float diameterOfCanopy,
			@RequestParam String municipality,
			@RequestParam float diameterOfTrunk,
			@RequestParam String stringLandUse,
			@RequestParam Date whenPlanted,
			@RequestParam int reports,
			@RequestParam Date whenCutDown,
			@RequestParam Date lastSurvey,
			@RequestParam float longitude,
			@RequestParam float latitude,
			@RequestParam String username,
			@RequestParam float monetaryValue,
			@RequestParam float BiodiversityIndex

			) throws InvalidInputException 
	{

		Status status = Status.valueOf(stringStatus);
		KindOfLandUse landUse = KindOfLandUse.valueOf(stringLandUse);

		Location coordinates = new Location(latitude,longitude);
		SustainabilityAttributes th = new SustainabilityAttributes(0, 0);
		Tree tree = service.plantTree(species, toBeChopped,  status, height, 
				diameterOfCanopy, municipality, diameterOfTrunk,
				landUse, whenPlanted, reports,
				whenCutDown, lastSurvey, coordinates,
				service.findUserByUsername(username), th);
		return convertToDto(tree);
	}

	@PostMapping(value = { "/trees/newTreeOnAndroid", "/trees/newTreeOnAndroid/" })
	public TreeDto plantTreeOnAndroid(
			@RequestParam String species,
			@RequestParam boolean toBeChopped,
			@RequestParam String stringStatus,
			@RequestParam float height,
			@RequestParam float diameterOfCanopy,
			@RequestParam String municipality,
			@RequestParam float diameterOfTrunk,
			@RequestParam String stringLandUse,
			@RequestParam Date whenPlanted,
			@RequestParam int reports,
			@RequestParam Date whenCutDown,
			@RequestParam Date lastSurvey,
			@RequestParam float longitude,
			@RequestParam float latitude,
			@RequestParam String username,
			@RequestParam String password,
			@RequestParam float monetaryValue,
			@RequestParam float BiodiversityIndex

			) throws InvalidInputException 
	{

		service.login(username, password);
		Status status = Status.valueOf(stringStatus);
		KindOfLandUse landUse = KindOfLandUse.valueOf(stringLandUse);
		Location coordinates = new Location(latitude,longitude);
		SustainabilityAttributes th = new SustainabilityAttributes(0, 0);
		Tree tree = service.plantTree(species, toBeChopped,  status, height, 
				diameterOfCanopy, municipality, diameterOfTrunk,
				landUse, whenPlanted, reports,
				whenCutDown, lastSurvey, coordinates,
				service.findUserByUsername(username), th);
		return convertToDto(tree);
	}
	@PostMapping(value = { "/forecast/{name}/plantTree", "/forecast/{name}/plantTree/" })
	public ForecastTreeDto plantForecastTree(
			@RequestParam String species,
			@RequestParam boolean toBeChopped,
			@RequestParam String stringStatus,
			@RequestParam float height,
			@RequestParam float diameterOfCanopy,
			@RequestParam String municipality,
			@RequestParam float diameterOfTrunk,
			@RequestParam String stringLandUse,
			@RequestParam Date whenPlanted,
			@RequestParam int reports,
			@RequestParam Date whenCutDown,
			@RequestParam Date lastSurvey,
			@RequestParam float longitude,
			@RequestParam float latitude,
			@PathVariable("name")  String username,
			@RequestParam float monetaryValue,
			@RequestParam float BiodiversityIndex

			) throws InvalidInputException 
	{

		Status status = Status.valueOf(stringStatus);
		KindOfLandUse landUse = KindOfLandUse.valueOf(stringLandUse);

		Location coordinates = new Location(latitude,longitude);
		SustainabilityAttributes th = new SustainabilityAttributes(0, 0);
		ForecastTree tree = service.plantForecastTree(species, toBeChopped,  status, height, 
				diameterOfCanopy, municipality, diameterOfTrunk,
				landUse, whenPlanted, reports,
				whenCutDown, lastSurvey, coordinates,
				service.findUserByUsername(username), th);
		return convertToDto(tree);
	}

	@PostMapping(value = { "/trees/confirmTree", "/trees/confirmTree/" })
	public TreeDto confirmTree(
			@RequestParam String species,
			@RequestParam boolean toBeChopped,
			@RequestParam String stringStatus,
			@RequestParam float height,
			@RequestParam float diameterOfCanopy,
			@RequestParam String municipality,
			@RequestParam float diameterOfTrunk,
			@RequestParam String stringLandUse,
			@RequestParam Date whenPlanted,
			@RequestParam int reports,
			@RequestParam Date whenCutDown,
			@RequestParam Date lastSurvey,
			@RequestParam float longitude,
			@RequestParam float latitude,
			@RequestParam String username

			) throws InvalidInputException 
	{
		Status status = Status.valueOf(stringStatus);
		KindOfLandUse landUse = KindOfLandUse.valueOf(stringLandUse);

		Location coordinates = new Location(latitude,longitude);
		SustainabilityAttributes th = new SustainabilityAttributes(0, 0);
		Tree tree = service.confirmTree(species, toBeChopped,  status, height, 
				diameterOfCanopy, municipality, diameterOfTrunk,
				landUse, whenPlanted, reports,
				whenCutDown, lastSurvey, coordinates,
				service.findUserByUsername(username), th);
		return convertToDto(tree);
	}


	@PostMapping(value = { "/forecast/{name}/cutDown", "/forecast/{name}/cutDown/" })
	public void cutDownForecastTree(@RequestParam int id, @PathVariable("name") String username) throws InvalidInputException
	{
		service.cutDownForecastTree(service.findForecastTreeByID(id,service.findUserByUsername(username)), service.findUserByUsername(username));
	}

	@PostMapping(value = { "/trees/rejectTree", "/trees/rejectTree/" })
	public void rejectTree(@RequestParam float longitude,
			@RequestParam float latitude,
			@RequestParam String username) throws InvalidInputException 
	{
		Location L =  new Location(latitude,longitude);
		service.rejectTree(L, service.findUserByUsername(username));

	}
	@PostMapping(value = { "/trees/requestTree", "/trees/requestTree/" })
	public Location requestTree(@RequestParam float latitude,
			@RequestParam float longitude,
			@RequestParam String username) throws InvalidInputException 
	{
		Location R = service.requestTree(latitude, longitude, service.findUserByUsername(username));
		return R;
	}

	@PostMapping(value = { "/trees/requestTreeOnAndroid", "/trees/requestTreeOnAndroid/" })
	public Location requestTreeOnAndroid(@RequestParam float latitude,
			@RequestParam float longitude,
			@RequestParam String username,
			@RequestParam String password) throws InvalidInputException 
	{

		service.login(username, password);
		Location R = service.requestTree(latitude, longitude, service.findUserByUsername(username));
		return R;

	}

	@GetMapping(value = { "/trees/", "/trees" })
	public List<TreeDto> getAllTrees()
	{
		List<TreeDto> TreeDtoList = new ArrayList<TreeDto>();
		for(int i = 0;i<service.getAllTrees().size();i++)
			TreeDtoList.add(convertToDto(service.getAllTrees().get(i)));

		return TreeDtoList;
	}

	@GetMapping(value = { "/requestedTrees/", "/requestedTrees" })
	public List<LocationDto> getAllRequestedTrees()
	{
		List<LocationDto> TreeDtoList = new ArrayList<LocationDto>();
		for(int i = 0;i<service.getAllRequestedTrees().size();i++)
			TreeDtoList.add(convertToDto(service.getAllRequestedTrees().get(i)));

		return TreeDtoList;
	}

	@GetMapping(value = { "/users/", "/users" })
	public List<UserDto> getAllUsers()
	{
		List<UserDto> UserDtoList = new ArrayList<UserDto>();
		for(int i = 0;i<service.getAllUsers().size();i++)
			UserDtoList.add(convertToDto(service.getAllUsers().get(i)));

		return UserDtoList;
	}

	@PostMapping(value = { "/login/", "/login"})
	public boolean login(@RequestParam String name, @RequestParam String password) throws InvalidInputException 
	{
		return service.login(name, password);
	}

	@PostMapping(value = "/loginOnAndroid/")
	public BooleanDto loginOnAndroid(@RequestParam String name, @RequestParam String password) throws InvalidInputException 
	{

		BooleanDto response = new BooleanDto(String.valueOf(service.login(name, password)));
		return response;
	}

	@PostMapping(value = { "/trees/cutDown", "/trees/cutDown" })
	public TreeDto cutDownTree(@RequestParam int id, @RequestParam String username) throws InvalidInputException
	{
		service.cutDownTree(service.findTreeByID(id), service.findUserByUsername(username));
		return convertToDto(service.findTreeByID(id));

	}

	@PostMapping(value = { "/trees/cutDownOnAndroid/", "/trees/cutDownOnAndroid" })
	public TreeDto cutDownTreeOnAndroid(@RequestParam int id,
			@RequestParam String username,
			@RequestParam String password
			) throws InvalidInputException
	{
		service.login(username, password);
		service.cutDownTree(service.findTreeByID(id), service.findUserByUsername(username));
		return convertToDto(service.findTreeByID(id));
	}

	@DeleteMapping(value = {"/trees/removeAll","/trees/removeAll/"})
	public void removeAllTrees() 
	{
		service.removeAllTrees();
	}

	@DeleteMapping(value = {"/trees/remove","/trees/remove/"})
	public void removeTree(@RequestParam int id,@RequestParam String username) throws InvalidInputException
	{
		service.removeTree(service.findTreeByID(id),service.findUserByUsername(username));
	}

	@GetMapping(value = {"/forecast/{name}","/forecast/{name}/"})
	public List<ForecastTreeDto> initializeForecastTrees(@PathVariable("name") String name) throws InvalidInputException
	{
		List<ForecastTree> TList = service.initializeForecastTrees(service.findUserByUsername(name));
		List<ForecastTreeDto> TDtoList = new ArrayList();
		for(int i = 0;i<TList.size();i++)
			TDtoList.add(convertToDto(TList.get(i)));
		return TDtoList;
	}

	@GetMapping(value = {"/getForecast/{name}","/getForecast/{name}/"})
	public List<ForecastTreeDto> getForecastTreesForUser(@PathVariable("name") String name) 
	{
		List<ForecastTree> TList = service.getForecastTreesForUser(service.findUserByUsername(name));
		List<ForecastTreeDto> TDtoList = new ArrayList();
		for(int i = 0;i<TList.size();i++)
			TDtoList.add(convertToDto(TList.get(i)));
		return TDtoList;
	}

	@GetMapping(value = { "/trees/filtered", "/trees/filtered/" })
	public List <TreeDto> filterTrees(
			@RequestParam String species,
			@RequestParam boolean searchingForChopped,
			@RequestParam boolean toBeChopped,
			@RequestParam String stringStatus,
			@RequestParam float minHeight,
			@RequestParam float maxHeight,
			@RequestParam float minDiameterOfCanopy,
			@RequestParam float maxDiameterOfCanopy,
			@RequestParam String municipality,
			@RequestParam String stringLandUse
			) throws InvalidInputException 
	{
		Status status;
		KindOfLandUse landUse;
		//Default all null string values to null.
		if(species.equals("null")) species = null;

		if(stringStatus.equals("null")) status = null;
		else status = Status.valueOf(stringStatus);

		if(municipality.equals("null")) municipality = null;

		if(stringLandUse.equals("null")) landUse = null;
		else landUse = KindOfLandUse.valueOf(stringLandUse);

		//Sort the list of trees by a specific trait.
		List<Tree> treeList = service.filterTrees(species, searchingForChopped, toBeChopped, status,municipality, minHeight, maxHeight, minDiameterOfCanopy, maxDiameterOfCanopy, landUse);
		int size = treeList.size();

		//Convert the sorted tree list to dto.
		List<TreeDto> treeDtoList = new ArrayList();
		for(int i = 0; i<size;i++) 
		{
			treeDtoList.add(convertToDto(treeList.get(i)));
		}
		return treeDtoList;
	}

	@GetMapping(value = { "/trees/sortBy/", "/trees/sortBy" })
	public List<TreeDto> sortBy(@RequestParam String comparator,
			@RequestParam boolean reverse
			)
	{
		List<TreeDto> TreeDtoList = new ArrayList<TreeDto>();
		if(comparator.equals("species"))
		{
			for(int i = 0;i<service.getAllTrees().size();i++)
				TreeDtoList.add(convertToDto(service.sortBySpecies(reverse).get(i)));
		}
		else if(comparator.equals("toBeChopped"))
		{
			for(int i = 0;i<service.getAllTrees().size();i++) 
				TreeDtoList.add(convertToDto(service.sortByToBeChopped(reverse).get(i)));
		}
		else if(comparator.equals("status"))
		{
			for(int i = 0;i<service.getAllTrees().size();i++)
				TreeDtoList.add(convertToDto(service.sortByStatus(reverse).get(i)));
		}
		else if(comparator.equals("height"))
		{
			for(int i = 0;i<service.getAllTrees().size();i++)
				TreeDtoList.add(convertToDto(service.sortByHeight(reverse).get(i)));
		}
		else if(comparator.equals("diameterOfCanopy"))
		{
			for(int i = 0;i<service.getAllTrees().size();i++)
				TreeDtoList.add(convertToDto(service.sortByDiameterOfCanopy(reverse).get(i)));
		}
		else if(comparator.equals("municipality"))
		{
			for(int i = 0;i<service.getAllTrees().size();i++)
				TreeDtoList.add(convertToDto(service.sortByMunicipality(reverse).get(i)));
		}
		else if(comparator.equals("landUse"))
		{
			for(int i = 0;i<service.getAllTrees().size();i++)
				TreeDtoList.add(convertToDto(service.sortByLandUse(reverse).get(i)));
		}
		return TreeDtoList;
	}

	@PostMapping(value = { "/trees/editTree", "/trees/editTree/" })
	public TreeDto editTree(
			@RequestParam int id,
			@RequestParam String species,
			@RequestParam boolean toBeChopped,
			@RequestParam String stringStatus,
			@RequestParam float height,
			@RequestParam float diameterOfCanopy,
			@RequestParam String municipality,
			@RequestParam float diameterOfTrunk,
			@RequestParam String stringLandUse,
			@RequestParam Date whenPlanted,
			@RequestParam int reports,
			@RequestParam Date whenCutDown,
			@RequestParam Date lastSurvey,
			@RequestParam float longitude,
			@RequestParam float latitude,
			@RequestParam String username,
			@RequestParam float monetaryValue,
			@RequestParam float BiodiversityIndex ) throws InvalidInputException 
	{
		Status status = Status.valueOf(stringStatus);
		KindOfLandUse landUse = KindOfLandUse.valueOf(stringLandUse);
		Location coordinates = new Location(latitude,longitude);
		SustainabilityAttributes th = new SustainabilityAttributes(0, 0);
		Tree tree = service.editTree(service.findTreeByID(id),species, toBeChopped,  status, height, 
				diameterOfCanopy, municipality, diameterOfTrunk,
				landUse, whenPlanted, reports,
				whenCutDown, lastSurvey, coordinates,
				service.findUserByUsername(username), th);
		return convertToDto(tree);
	}

	@PostMapping(value = { "/trees/editTreeOnAndroid", "/trees/editTreeOnAndroid/" })
	public TreeDto editTreeOnAndroid(
			@RequestParam int id,
			@RequestParam String species,
			@RequestParam boolean toBeChopped,
			@RequestParam String stringStatus,
			@RequestParam float height,
			@RequestParam float diameterOfCanopy,
			@RequestParam String municipality,
			@RequestParam float diameterOfTrunk,
			@RequestParam String stringLandUse,
			@RequestParam Date whenPlanted,
			@RequestParam int reports,
			@RequestParam Date whenCutDown,
			@RequestParam Date lastSurvey,
			@RequestParam float longitude,
			@RequestParam float latitude,
			@RequestParam String username,
			@RequestParam String password,
			@RequestParam float monetaryValue,
			@RequestParam float BiodiversityIndex ) throws InvalidInputException 
	{

		service.login(username, password);
		Status status = Status.valueOf(stringStatus);
		KindOfLandUse landUse = KindOfLandUse.valueOf(stringLandUse);
		Location coordinates = new Location(latitude,longitude);
		SustainabilityAttributes th = new SustainabilityAttributes(0, 0);
		Tree tree = service.editTree(service.findTreeByID(id),species, toBeChopped,  status, height, 
				diameterOfCanopy, municipality, diameterOfTrunk,
				landUse, whenPlanted, reports,
				whenCutDown, lastSurvey, coordinates,
				service.findUserByUsername(username), th);
		return convertToDto(tree);
	}
}
