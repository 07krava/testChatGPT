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
        location.setZipCode(housing.getLocation().getZipCode());

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

    public static Housing convertToEntity(HousingDTO housingDTO) {
        Housing housing = new Housing();
        housing.setId(housingDTO.getId());
        housing.setTitle(housingDTO.getTitle());
        housing.setAmountPeople(housingDTO.getAmountPeople());
        housing.setPrice(housingDTO.getPrice());
        housing.setDescription(housingDTO.getDescription());
        housing.setActive(housingDTO.isActive());

        Location location = new Location();
        location.setCountry(housingDTO.getLocation().getCountry());
        location.setRegion(housingDTO.getLocation().getRegion());
        location.setCity(housingDTO.getLocation().getCity());
        location.setStreet(housingDTO.getLocation().getStreet());
        location.setHouseNumber(housingDTO.getLocation().getHouseNumber());
        location.setApartmentNumber(housingDTO.getLocation().getApartmentNumber());
        location.setZipCode(housingDTO.getLocation().getZipCode());

        housing.setLocation(location);

        List<Photo> photoEntities = new ArrayList<>();
        for (PhotoDTO photoDTO : housingDTO.getPhotos()) {
            Photo photo = new Photo();
            photo.setId(photoDTO.getId());
            photo.setFileName(photoDTO.getFileName());
            photo.setData(photoDTO.getData());
            photo.setHousing(housing);
            photoEntities.add(photo);
        }
        housing.setPhotos(photoEntities);

        return housing;
    }
}
