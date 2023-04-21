package com.example.testchatgpt.Service;

import com.example.testchatgpt.dto.HousingDTO;
import com.example.testchatgpt.dto.PhotoDTO;
import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface HousingService {

   List<Housing> findByCity(String city);

   HousingDTO createHousing(HousingDTO housingDTO, MultipartFile[] files) throws IOException;

   HousingDTO updateHousing(Long id, HousingDTO housingDTO, MultipartFile[] files) throws IOException;

   List<PhotoDTO> getPhotosByHousingId(Long housingId);

   Photo getImageById(Long housingId, Long photoId);

   List<HousingDTO> getAllHousing();

   HousingDTO getHousingById(Long id);

   void deleteHousing(Long id);

   void deleteImageByIdFromHousingId(Long housingId, Long imageId);

   List<Housing> getBookedHousing(Date startDate, Date endDate);

   List<Housing> getAvailableHousings(Date startDate, Date endDate);
}
