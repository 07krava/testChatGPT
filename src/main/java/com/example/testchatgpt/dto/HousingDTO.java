package com.example.testchatgpt.dto;

import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.Location;
import com.example.testchatgpt.model.Photo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class HousingDTO {
    private Long id;
    private BigDecimal price;
    private String description;
    private Location location;
    private String title;
    private boolean isActive;
    private Integer amountPeople;
    private List<PhotoDTO> photos;

    public static HousingDTO convertToDTO(Housing housing){
     HousingDTO housingDTO = new HousingDTO();
        housingDTO.setId(housing.getId());
        housingDTO.setTitle(housing.getTitle());
        housingDTO.setAmountPeople(housing.getAmountPeople());
        housingDTO.setLocation(housing.getLocation());
        housingDTO.setPrice(housing.getPrice());
        housingDTO.setDescription(housing.getDescription());
        housingDTO.setActive(housing.isActive());

        LocationDTO location = new LocationDTO();
        location.setCountry(housing.getLocation().getCountry());
        location.setRegion(housing.getLocation().getRegion());
        location.setCity(housing.getLocation().getCity());
        location.setStreet(housing.getLocation().getStreet());
        location.setHouseNumber(housing.getLocation().getHouseNumber());
        location.setApartmentNumber(housing.getLocation().getApartmentNumber());

        List<PhotoDTO> photoDTOList = new ArrayList<>();
        for (Photo photo : housing.getPhotos()) {
            PhotoDTO photoDTO = new PhotoDTO();
            photoDTO.setId(photo.getId());
            photoDTO.setFileName(photo.getFileName());
            photoDTO.setData(photo.getData());
            photoDTOList.add(photoDTO);
        }

        housingDTO.setPhotos(photoDTOList);
        return housingDTO;
    }
}
