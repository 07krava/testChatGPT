package com.example.testchatgpt.Service.impl;

import com.example.testchatgpt.Service.PhotoService;
import com.example.testchatgpt.dto.PhotoDTO;
import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.Photo;
import com.example.testchatgpt.repository.PhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    private PhotoRepository photoRepository;
    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public PhotoDTO savePhoto(MultipartFile file, Housing housingEntity) throws IOException {
        Photo photoEntity = new Photo();
        photoEntity.setFileName(file.getOriginalFilename());
        photoEntity.setHousing(housingEntity);
        photoEntity.setData(file.getBytes());

        Photo savedPhotoEntity = photoRepository.save(photoEntity);

        return convertToDTO(savedPhotoEntity);
    }

    public List<PhotoDTO> savePhotos(MultipartFile[] files, Housing housingEntity) throws IOException {
        List<PhotoDTO> photoDTOS = new ArrayList<>();

        for (MultipartFile file : files) {
            photoDTOS.add(savePhoto(file, housingEntity));
        }

        return photoDTOS;
    }

    public PhotoDTO getPhotoById(Long id) {
        Photo photoEntity = photoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Photo not found with id " + id));

        return convertToDTO(photoEntity);
    }

    public void deletePhotoById(Long id) {
        photoRepository.deleteById(id);
    }

    public void deletePhotosByHousingId(Long housingId) {
        photoRepository.deleteByHousingId(housingId);
    }

    private PhotoDTO convertToDTO(Photo photoEntity) {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(photoEntity.getId());
        photoDTO.setFileName(photoEntity.getFileName());
        photoDTO.setData(photoEntity.getData());
        //photoDTO.setHousingId(photoEntity.getHousing().getId());

        return photoDTO;
    }
}
