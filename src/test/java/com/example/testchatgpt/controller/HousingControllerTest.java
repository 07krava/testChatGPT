package com.example.testchatgpt.controller;

import com.example.testchatgpt.Service.HousingService;
import com.example.testchatgpt.dto.HousingDTO;
import com.example.testchatgpt.dto.PhotoDTO;
import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HousingControllerTest {

    @InjectMocks
    private HousingController housingController;

    @Mock
    private HousingService housingService;

//    @Test
//    void testCreateHousing() throws IOException {
//
//        HousingDTO housingDTO = new HousingDTO();
//        housingDTO.setTitle("Test Housing");
//        housingDTO.setDescription("Test Description");
//        housingDTO.setPrice(BigDecimal.valueOf(1000.0));
//        List<MultipartFile> files = new ArrayList<>();
//        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test File".getBytes());
//        files.add(mockFile);
//        when(housingService.createHousing(any(HousingDTO.class), any(MultipartFile[].class)))
//                .thenReturn(housingDTO);
//
//        ResponseEntity<HousingDTO> responseEntity = housingController.createHousing(housingDTO, files.toArray(new MultipartFile[0]));
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(housingDTO, responseEntity.getBody());
//    }

    @Test
    void testGetAllHousing() {

        List<HousingDTO> housingList = new ArrayList<>();
        HousingDTO housing1 = new HousingDTO();
        housing1.setTitle("Housing 1");
        housingList.add(housing1);
        HousingDTO housing2 = new HousingDTO();
        housing2.setTitle("Housing 2");
        housingList.add(housing2);
        when(housingService.getAllHousing()).thenReturn(housingList);

        List<HousingDTO> responseList = housingController.getAllHousing();

        assertEquals(housingList.size(), responseList.size());
        assertEquals(housing1.getTitle(), responseList.get(0).getTitle());
        assertEquals(housing2.getTitle(), responseList.get(1).getTitle());
    }


    @Test
    void testUpdateHousing() throws IOException {

        Long id = 1L;
        HousingDTO housingDTO = new HousingDTO();
        housingDTO.setId(id);
        MultipartFile[] files = new MultipartFile[0];
        HousingDTO updatedHousing = new HousingDTO();
        updatedHousing.setId(id);
        updatedHousing.setTitle("Updated Housing");
        when(housingService.updateHousing(id, housingDTO, files)).thenReturn(updatedHousing);

        HousingDTO response = housingController.updateHousing(id, housingDTO, files);

        assertEquals(updatedHousing.getId(), response.getId());
        assertEquals(updatedHousing.getTitle(), response.getTitle());
    }

    @Test
    public void testDeleteHousingById() {
        Long id = 1L;

        // mock the housing service to do nothing when deleteHousing method is called
        doNothing().when(housingService).deleteHousing(id);

        // call the deleteHousing method on the controller and check the response
        ResponseEntity<String> response = housingController.deleteHousingById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Housing " + id + " delete successfully!", response.getBody());

        // verify that the deleteHousing method was called on the service
        verify(housingService).deleteHousing(id);
    }

    @Test
    void testGetHousingById() {
        Long id = 1L;
        HousingDTO expectedHousing = new HousingDTO();
        expectedHousing.setId(id);
        when(housingService.getHousingById(id)).thenReturn(expectedHousing);

        HousingDTO actualHousing = housingController.getHousingById(id);

        assertThat(actualHousing).isNotNull();
        assertThat(actualHousing).isEqualTo(expectedHousing);
        assertEquals(expectedHousing, actualHousing);
    }

    @Test
    void testGetPhotoByHousingId() {
        // Создаем HousingDTO и PhotoDTO
        Housing housing = new Housing();
        housing.setId(1L);

        PhotoDTO photo1 = new PhotoDTO();
        photo1.setId(1L);
        photo1.setHousing(housing);

        PhotoDTO photo2 = new PhotoDTO();
        photo2.setId(2L);
        photo2.setHousing(housing);

        List<PhotoDTO> photos = Arrays.asList(photo1, photo2);

        // Указываем, что HousingService.getPhotosByHousingId() должен возвращать список фотографий
        when(housingService.getPhotosByHousingId(1L)).thenReturn(photos);

        // Выполняем метод контроллера и проверяем результат
        List<PhotoDTO> result = housingController.getPhotoByHousingId(1L);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1L, result.get(0).getHousing().getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(1L, result.get(1).getHousing().getId());
    }

    @Test
    void testGetPhotoById() {
        // создаем фиктивный объект HousingDTO и PhotoDTO
        HousingDTO housingDTO = new HousingDTO();
        housingDTO.setId(1L);
        List<PhotoDTO> photos = new ArrayList<>();
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(1L);
        photos.add(photoDTO);
        housingDTO.setPhotos(photos);

        // настроить возвращаемое значение сервиса
        when(housingService.getHousingById(1L)).thenReturn(housingDTO);

        // вызов контроллера
        ResponseEntity<PhotoDTO> response = housingController.getPhotoById(1L, 1L);

        // проверяем статус ответа
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // проверяем, что вернулся правильный объект PhotoDTO
        PhotoDTO actualPhotoDTO = response.getBody();
        assertEquals(photoDTO.getId(), actualPhotoDTO.getId());
    }

    @Test
    void testGetPhotoByIdNotFound() {
        // Указываем, что HousingService.getHousingById() должен возвращать null
        when(housingService.getHousingById(1L)).thenReturn(null);

        // Выполняем метод контроллера и проверяем, что NullPointerException было выброшено
        assertThrows(NullPointerException.class, () -> {
            housingController.getPhotoById(1L, null);
        });
    }

    @Test
    void testGetPhotoByIdNotFoundInHousing() {
        // Создаем HousingDTO без фотографий
        HousingDTO housing = new HousingDTO();
        housing.setId(1L);

        // Указываем, что HousingService.getHousingById() должен возвращать HousingDTO без фотографий
        when(housingService.getHousingById(1L)).thenReturn(housing);

        // Выполняем метод контроллера и проверяем, что NullPointerException было выброшено
        assertThrows(NullPointerException.class, () -> {
            housingController.getPhotoById(1L, 2L);
        });
    }

    @Test
    void testDeletePhoto() {
        Long housingId = 1L;
        Long imageId = 2L;

        // Mock housingService.deleteImageByIdFromHousingId() method
        doNothing().when(housingService).deleteImageByIdFromHousingId(housingId, imageId);

        // Call the deletePhoto() method of HousingController
        ResponseEntity<Void> responseEntity = housingController.deletePhoto(housingId, imageId);

        // Verify that the deleteImageByIdFromHousingId() method of HousingService is called once
        verify(housingService, times(1)).deleteImageByIdFromHousingId(housingId, imageId);

        // Verify the response status code
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}