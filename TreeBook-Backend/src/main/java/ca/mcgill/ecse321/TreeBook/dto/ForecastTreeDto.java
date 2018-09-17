package ca.mcgill.ecse321.TreeBook.dto;

import java.sql.Date;

import ca.mcgill.ecse321.TreeBook.model.ForecastSustainabilityAttributes;
import ca.mcgill.ecse321.TreeBook.model.Location;
import ca.mcgill.ecse321.TreeBook.model.SustainabilityAttributes;
import ca.mcgill.ecse321.TreeBook.model.Tree.KindOfLandUse;
import ca.mcgill.ecse321.TreeBook.model.Tree.Status;


public class ForecastTreeDto {
	  private int id;
	  private String species;
	  private boolean toBeChopped;
	  private Status status;
	  private float height;
	  private float diameterOfCanopy;
	  private String municipality;
	  private float diameterOfTrunk;
	  private KindOfLandUse landUse;
	  private Date whenPlanted;
	  private int reports;
	  private Date whenCutDown;
	  private Date lastSurvey;
	  private Location coordinates;
	  private ForecastSustainabilityAttributes treeSustainability;


	public ForecastTreeDto() {
	}

	public ForecastTreeDto(String species, boolean toBeChopped, Status status, float height, float diameterOfCanopy, String municipality, float diameterOfTrunk, KindOfLandUse landUse, Date whenPlanted, int reports, Date whenCutDown, Date lastSurvey, Location coordinates, ForecastSustainabilityAttributes treeSustainability,int id)
	  {
	    this.species = species;
	    this.toBeChopped = toBeChopped;
	    this.status = status;
	    this.height =height;
	    this.diameterOfCanopy = diameterOfCanopy;
	    this.municipality = municipality;
	    this.diameterOfTrunk = diameterOfTrunk;
	    this.landUse = landUse;
	    this.whenPlanted = whenPlanted;
	    this.reports = reports;
	    this.whenCutDown = whenCutDown;
	    this.lastSurvey = lastSurvey;
	    this.coordinates = coordinates ;
	    this.treeSustainability = treeSustainability;
	    this.id=id;
	  }

	 public String getSpecies()
	  {
	    return species;
	  }

	  public boolean isToBeChopped()
	  {
	    return toBeChopped;
	  }

	  public Status getStatus()
	  {
	    return status;
	  }

	  public float getHeight()
	  {
	    return height;
	  }

	  public float getDiameterOfCanopy()
	  {
	    return diameterOfCanopy;
	  }

	  public String getMunicipality()
	  {
	    return municipality;
	  }

	  public float getdiameterOfTrunk()
	  {
	    return diameterOfTrunk;
	  }

	  public KindOfLandUse getLandUse()
	  {
	    return landUse;
	  }

	  public Date getWhenPlanted()
	  {
	    return whenPlanted;
	  }

	  public int getReports()
	  {
	    return reports;
	  }

	  public Date getWhenCutDown()
	  {
	    return whenCutDown;
	  }

	  public Date getLastSurvey()
	  {
	    return lastSurvey;
	  }


	  public Location getCoordinates()
	  {
	    return coordinates;
	  }

	  public ForecastSustainabilityAttributes getTreeSustainability()
	  {
	    return treeSustainability;
	  }
	  public int getId() {
		  return id;
	  }
	
}
