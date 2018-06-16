package com.itnetsoft.services;


import com.itnetsoft.dto.ImageEditDTO;
import com.itnetsoft.dto.ImageInfoDTO;
import com.itnetsoft.dto.ImageManagerDTO;
import com.itnetsoft.dto.SettingsDTO;
import com.itnetsoft.exceptions.SettingsServiceException;
import com.itnetsoft.jsonDTO.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISettingsService {
    public Response<List<SettingsDTO>> getSettingsList() throws SettingsServiceException;
    public Response<List<SettingsDTO>> saveSettingsList(List<SettingsDTO> itemsList) throws SettingsServiceException;
    public Response<SettingsDTO> deleteSetting(int id) throws SettingsServiceException;
    public Response<SettingsDTO> addSettingItem(SettingsDTO item) throws SettingsServiceException;
}
