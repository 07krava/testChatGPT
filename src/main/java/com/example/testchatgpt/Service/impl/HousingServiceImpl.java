package com.example.testchatgpt.Service.impl;

import com.example.testchatgpt.Service.HousingService;
import com.example.testchatgpt.Service.PhotoService;
import com.example.testchatgpt.dto.HousingDTO;
import com.example.testchatgpt.dto.LocationDTO;
import com.example.testchatgpt.dto.PhotoDTO;
import com.example.testchatgpt.model.Booking;
import com.example.testchatgpt.model.Housing;
import com.example.testchatgpt.model.Location;
import com.example.testchatgpt.model.Photo;
import com.example.testchatgpt.repository.BookingRepository;
import com.example.testchatgpt.repository.HousingRepository;
import com.example.testchatgpt.repository.PhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.testchatgpt.dto.HousingDTO.convertToDTO;
import static com.example.testchatgpt.dto.HousingDTO.convertToEntity;
import static com.example.testchatgpt.dto.PhotoDTO.convertToPhoto;

@Slf4j
@Service
public class HousingServiceImpl implements HousingService {

    private final HousingRepository housingRepository;
    private final PhotoRepository photoRepository;
    private final PhotoService photoService;
    private final BookingRepository bookingRepository;

    @Autowired
    public HousingServiceImpl(HousingRepository housingRepository, BookingRepository bookingRepository, PhotoRepository photoRepository, PhotoService photoService) {
        this.housingRepository = housingRepository;
        this.photoRepository = photoRepository;
        this.photoService = photoService;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Housing> findByCity(String city) {
        List<Housing> housings = housingRepository.findAll();
        List<Housing> result = new ArrayList<>();
        for (Housing housing : housings) {
            Location location = housing.getLocation();
            if (location != null && location.getCity().equals(city)) {
                result.add(housing);
            }
        }
        return result;
    }

    //worked
    @Override
    public HousingDTO createHousing(HousingDTO housingDTO, MultipartFile[] files) throws IOException {

        Housing housing = new Housing();
        housing.setDescription(housingDTO.getDescription());
        housing.setTitle(housingDTO.getTitle());
        housing.setAmountPeople(housingDTO.getAmountPeople());
        housing.setPrice(housingDTO.getPrice());
        housing.setActive(housingDTO.isActive());

        housingRepository.save(housing);

        // Создание и сохранение объекта Location
        Location location = new Location();
        location.setCountry(housingDTO.getLocation().getCountry());
        location.setRegion(housingDTO.getLocation().getRegion());
        location.setCity(housingDTO.getLocation().getCity());
        location.setStreet(housingDTO.getLocation().getStreet());
        location.setHouseNumber(housingDTO.getLocation().getHouseNumber());
        location.setApartmentNumber(housingDTO.getLocation().getApartmentNumber());
        location.setZipCode(housingDTO.getLocation().getZipCode());
        location.setHousing(housing);
        housing.setLocation(location);

        List<Photo> photoEntities = new ArrayList<>();
        for (MultipartFile file : files) {
            PhotoDTO photoDTO = new PhotoDTO();
            photoDTO.setFileName(file.getOriginalFilename());
            photoDTO.setData(file.getBytes());
            photoDTO.setHousing(housing);
            photoEntities.add(convertToPhoto(photoDTO));
        }
        housing.setPhotos(photoEntities);

        housingRepository.save(housing);

        return convertToDTO(housing);
    }

    // worked
    @Override
    public HousingDTO updateHousing(Long housingId, HousingDTO housingDTO, MultipartFile[] files) throws IOException {
        Housing housingEntity = housingRepository.findById(housingId)
                .orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + housingId));

        // Update fields of HousingEntity based on HousingDTO
        housingEntity.setTitle(housingDTO.getTitle());
        housingEntity.setDescription(housingDTO.getDescription());
        housingEntity.setAmountPeople(housingDTO.getAmountPeople());
        housingEntity.setPrice(housingDTO.getPrice());
        housingEntity.setActive(housingDTO.isActive());

        Location location = housingEntity.getLocation();
        if (location != null) {
            location.setCountry(housingDTO.getLocation().getCountry());
            location.setRegion(housingDTO.getLocation().getRegion());
            location.setCity(housingDTO.getLocation().getCity());
            location.setStreet(housingDTO.getLocation().getStreet());
            location.setHouseNumber(housingDTO.getLocation().getHouseNumber());
            location.setApartmentNumber(housingDTO.getLocation().getApartmentNumber());
            location.setZipCode(housingDTO.getLocation().getZipCode());
        } else {
            location = new Location();
            location.setCountry(housingDTO.getLocation().getCountry());
            location.setRegion(housingDTO.getLocation().getRegion());
            location.setCity(housingDTO.getLocation().getCity());
            location.setStreet(housingDTO.getLocation().getStreet());
            location.setHouseNumber(housingDTO.getLocation().getHouseNumber());
            location.setApartmentNumber(housingDTO.getLocation().getApartmentNumber());
            location.setZipCode(housingDTO.getLocation().getZipCode());
            location.setHousing(convertToEntity(housingDTO));
            housingDTO.setLocation(location);
        }

        // Update photos of HousingEntity based on files
        if (files != null && files.length > 0) {
            List<Photo> photoEntities = new ArrayList<>();
            for (MultipartFile file : files) {

                List<Photo> photoList = housingEntity.getPhotos();
                for (Photo photoDTO1 : photoList) {
                    photoDTO1.setId(photoDTO1.getId());
                    photoDTO1.setFileName(file.getOriginalFilename());
                    photoDTO1.setData(file.getBytes());
                    photoEntities.add(photoDTO1);
                }
            }
            housingEntity.setPhotos(photoEntities);
        }
        Housing savedHousing = housingRepository.save(housingEntity);

        return convertToDTO(savedHousing);
    }

    // worked
    @Override
    public List<PhotoDTO> getPhotosByHousingId(Long housingId) {
        List<PhotoDTO> photoDTOList = new ArrayList<>();
        Optional<Housing> housingOptional = housingRepository.findById(housingId);
        if (housingOptional.isPresent()) {
            Housing housing = housingOptional.get();
            List<Photo> photos = housing.getPhotos();
            if (photos != null) {
                photoDTOList = photos.stream()
                        .map(photo -> PhotoDTO.builder()
                                .id(photo.getId())
                                .fileName(photo.getFileName())
                                .data(photo.getData())
                                .build())
                        .collect(Collectors.toList());
            }
        }
        return photoDTOList;
    }

    //worked
    @Override
    public Photo getImageById(Long housingId, Long imageId) {
        log.info("Start method getPhotoByIdFromHousingId");
        Housing housing = housingRepository.findById(housingId).orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + housingId));
        Photo photo = null;
        if (housing.getPhotos() != null) {
            for (Photo p : housing.getPhotos()) {
                if (p.getId().equals(imageId) && p.getId() != null) {
                    photo = p;
                    log.info("We found our image by id " + photo);
                    break;
                }
            }
        } else {
            throw new EntityNotFoundException(" Image this id not found ");
        }
        return photo;
    }

    //worked
    @Override
    public List<HousingDTO> getAllHousing() {
        List<Housing> housingEntities = housingRepository.findAll();
        List<HousingDTO> housingDTOS = new ArrayList<>();

        for (Housing housingEntity : housingEntities) {
            housingDTOS.add(convertToDTO(housingEntity));
        }

        return housingDTOS;
    }

    //worked
    @Override
    public void deleteHousing(Long id) {
        Optional<Housing> housingEntityOptional = housingRepository.findById(id);

        if (housingEntityOptional.isPresent()) {
            Housing housingEntity = housingEntityOptional.get();
            housingRepository.delete(housingEntity);
            System.out.println("Housing delete successfully");
        } else {
            throw new NullPointerException("Housing not found with id: " + id);
        }
    }

    //worked
    @Override
    public void deleteImageByIdFromHousingId(Long housingId, Long imageId) {
        Housing housing = housingRepository.findById(housingId).orElseThrow(NullPointerException::new);
        Photo photo = housing.getPhotos().stream()
                .filter(p -> p.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Image not found with id " + imageId));
        housing.getPhotos().remove(photo);
        photoRepository.deleteById(imageId);
    }

    //worked
    @Override
    public HousingDTO getHousingById(Long id) {
        Optional<Housing> housingEntityOptional = housingRepository.findById(id);
        if (housingEntityOptional.isPresent()) {
            Housing housingEntity = housingEntityOptional.get();
            return convertToDTO(housingEntity);
        } else {
            throw new NullPointerException("Housing not found with id: " + id);
        }
    }
    @Override
    public List<Housing> getAvailableHousings(Date startDate, Date endDate) {
        List<Housing> allHousing = housingRepository.findAll();
        Set<Housing> bookedHousing = new HashSet<>();
        for (Housing housing : allHousing) {
            for (Booking booking : housing.getBookings()) {
                if (isOverlapping(booking.getStartDate(), booking.getEndDate(), startDate, endDate)) {
                    bookedHousing.add(housing);
                    break;
                }
            }
        }
        allHousing.removeAll(bookedHousing);
        return allHousing;
    }

    private boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && start2.before(end1);
    }

    @Override
    public List<Housing> getBookedHousing(Date startDate, Date endDate) {
        List<Housing> allHousing = housingRepository.findAll();
        Set<Housing> bookedHousing = new HashSet<>();
        for (Housing housing : allHousing) {
            for (Booking booking : housing.getBookings()) {
                if (isOverlapping(booking.getStartDate(), booking.getEndDate(), startDate, endDate)) {
                    bookedHousing.add(housing);
                    break;
                }
            }
        }
        allHousing.removeAll(bookedHousing);
        return new ArrayList<>(bookedHousing);
    }
}
