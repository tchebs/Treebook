package ca.mcgill.ecse321.TreeBook.dto;

import java.util.List;

import ca.mcgill.ecse321.TreeBook.model.Trophy;

public class UserDto {

	private String phoneNumber;
	private String name;
	private String password;
	private int treesPlanted;
	private int treesCutDown;
	private int treesRequested;
	private List<Trophy> trophies;
	private boolean isSuper;

	public UserDto() {
	}

	public UserDto(String phoneNumber, String name,boolean isSuper, String password, int treesPlanted, int treesCutDown, int treesRequested, List<Trophy> trophies) {

		this.phoneNumber = phoneNumber;
		this.name = name;
		this.password = password;
		this.treesPlanted = treesPlanted;
		this.treesCutDown = treesCutDown;
		this.treesRequested = treesRequested;
		this.trophies = trophies;
		this.isSuper = isSuper;
		
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean getIsSuper() {
		return isSuper;
	}
	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
	
	public int getTreesPlanted() {
		return treesPlanted;
	}
	public int getTreesCutDown() {
		return treesCutDown;
	}
	public int getTreesRequested() {
		return treesRequested;
	}
	public List<Trophy> getTrophies() {
		return trophies;
	}
	

}