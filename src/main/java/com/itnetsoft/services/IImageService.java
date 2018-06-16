package com.itnetsoft.services;


import com.itnetsoft.dto.ImageEditDTO;
import com.itnetsoft.dto.ImageInfoDTO;
import com.itnetsoft.dto.ImageManagerDTO;
import com.itnetsoft.exceptions.ImageServiceException;
import com.itnetsoft.jsonDTO.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    public Response<List<ImageManagerDTO>> getManagerImageList() throws ImageServiceException;
    public Response<?> deleteImageItem(int id) throws ImageServiceException;
    public Response<ImageManagerDTO> saveImageItem(ImageManagerDTO item) throws ImageServiceException;
    public Response<List<ImageManagerDTO>> uploadImageFiles(List<MultipartFile> files) throws ImageServiceException;
    public Response<ImageInfoDTO> processImageDimensions(ImageEditDTO image) throws ImageServiceException;
    public Response<ImageInfoDTO> getImageItem(int id) throws ImageServiceException;





}
