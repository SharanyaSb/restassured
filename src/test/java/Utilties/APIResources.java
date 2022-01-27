package Utilties;

public enum APIResources {

	
	AddplaceAPI("/maps/api/place/add/json"),
	GetplaceAPI("/maps/api/place/get/json"),
	DeleteplaceAPI("/maps/api/place/delete/json");
			
	private String resource;
	
	APIResources(String resource){
		
		this.resource= resource;
		
	}
	
	public String getResource() {
		return resource;
	}
}
