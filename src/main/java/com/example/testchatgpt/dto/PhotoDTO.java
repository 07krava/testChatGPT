package com.example.testchatgpt.dto;

import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {
    private Long id;
    private String fileName;
    private Housing housing;
    private byte[] data;

    public static PhotoDTO convertToDTO(Photo photoEntity) {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(photoEntity.getId());
        photoDTO.setFileName(photoEntity.getFileName());
        photoDTO.setHousing(photoEntity.getHousing());
        photoDTO.setData(photoEntity.getData());
        return photoDTO;
    }

    public static Photo convertToPhoto(PhotoDTO photoDTO){
        Photo photo = new Photo();
        photo.setId(photoDTO.getId());
        photo.setFileName(photoDTO.getFileName());
        photo.setHousing(photoDTO.getHousing());
        photo.setData(photoDTO.getData());
        return photo;
    }
    public PhotoDTO convertMultipartFileToPhotoDTO(MultipartFile file, Long id) {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(id);
        photoDTO.setFileName(file.getOriginalFilename());
        return photoDTO;
    }
}
