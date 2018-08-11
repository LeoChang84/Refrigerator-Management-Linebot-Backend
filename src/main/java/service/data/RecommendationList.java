package service.data;

import service.data.RecommendationItem;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.lang.Boolean;

public class RecommendationList {

	@Id
	private String id;
    private List<RecommendationItem> recommendationItems; 

	public RecommendationList (List<RecommendationItem> recommendationItems) {
		this.recommendationItems = recommendationItems;
	}
    
    public List<RecommendationItem>  getRecommendationList() {
    	return recommendationItems;
    }
}