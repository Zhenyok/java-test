package com.itnetsoft.utils;


import com.itnetsoft.dto.FileUploadDTO;
import com.itnetsoft.dto.ImageEditDTO;
import com.itnetsoft.dto.ImageInfoDTO;
import com.sun.deploy.ui.ImageLoader;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FileUtils {

    public static boolean makeDirsAndUpload(List<FileUploadDTO> files, String uploadDir) {
        if (files == null || files.size() == 0 || StringUtils.isBlank(uploadDir)) {
            return false;
        }
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            return false;
        }
        Date currDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        String imageDir = dateFormat.format(currDate);
        String[] uploadDirDateFolders = imageDir.split("/", 2);
        if (uploadDirDateFolders.length < 2 || StringUtils.isBlank(uploadDirDateFolders[0])
                || StringUtils.isBlank(uploadDirDateFolders[1])) {
            return false;
        }
        String uploadDirString = uploadDir + uploadDirDateFolders[0] + File.separator + uploadDirDateFolders[1];
        File completeDir = new File(uploadDirString);
        if (!completeDir.exists()) {
            if (!completeDir.mkdirs()) {
                return false;
            }
        }
        String imageUrl = uploadDirDateFolders[0] + "/" + uploadDirDateFolders[1] + "/";
        uploadFiles(files, uploadDirString, imageUrl);
        return true;
    }

    private static void uploadFiles(List<FileUploadDTO> files, String uploadDir, String imageUrl) {
        if (files == null || files.size() == 0 || StringUtils.isBlank(uploadDir) || !new File(uploadDir).exists()) {
            return;
        }
        String fileDirPath = null, imageDirPath = null, fileName = null, fileExt = null;
        for (FileUploadDTO file : files) {
            try {
                if (!"IMAGE".equals(file.getContentType()) || StringUtils.isBlank(file.getFileExtension())
                        || StringUtils.isBlank(file.getTitle()) || file.getFile() == null
                        || file.getFile().isEmpty()) {
                    continue;
                }
                int i = 0;
                fileName = file.getTitle().replaceAll("\\.", "");
                String checkName = fileName;
                fileExt = file.getFileExtension();
                while (fileExists(uploadDir, checkName, fileExt)) {
                    ++i;
                    checkName = fileName + "_" + i;
                }
                imageDirPath = imageUrl + checkName + "." + file.getFileExtension();
                fileDirPath = uploadDir + File.separator + checkName + "." + file.getFileExtension();
                file.setUrl(imageDirPath);
                file.getFile().transferTo(new File(fileDirPath));
                File savedFile = new File(fileDirPath);
                if (!savedFile.isFile()) {
                    file.setUrl("");
                } else {
                    fileThumbMake(file, savedFile, uploadDir + File.separator + checkName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void fileThumbMake(FileUploadDTO file, File savedImgPath, String filePath) throws IOException {
        BufferedImage image = ImageIO.read(savedImgPath);
        int width = 0, height = 0;
        width = image.getWidth();
        height = image.getHeight();

        String thumb200x200 = filePath + "_200x200." + file.getFileExtension();
        if (width > 200 || height > 200) {
            File resizedFile = new File(thumb200x200);
            org.apache.commons.io.FileUtils.copyFile(savedImgPath, resizedFile);
            BufferedImage imageThumb = ImageIO.read(resizedFile);
            imageThumb = Scalr.resize(imageThumb, 200);
            ImageIO.write(imageThumb, file.getFileExtension(), resizedFile);
        }
    }

    private static boolean fileThumbMake(BufferedImage image, String fileType, File reassignedThumb) {
        if (image == null || StringUtils.isBlank(fileType) || reassignedThumb == null) {
            return false;
        }
        if (image.getHeight() > 200 || image.getWidth() > 200) {
            try {
                image = Scalr.resize(image, 200);
                return ImageIO.write(image, fileType, reassignedThumb);
            } catch(IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }



    private static boolean fileExists(String uploadDir, String fileName, String fileExt) {
        File file = new File(uploadDir + File.separator + fileName + "." + fileExt);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static boolean imageDelete(String url, String uploadDir) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(uploadDir)) {
            return false;
        }
        String[] thumb_url = url.split("\\.");
        if (thumb_url.length < 2) {
            return false;
        }
        File originalImage = new File(uploadDir + "/" + url);
        File thumb = new File(uploadDir + "/" + thumb_url[0] + "_200x200." + thumb_url[1]);
        if (originalImage.exists() && originalImage.isFile()) {
            if (!originalImage.delete()) {
                return false;
            }
            if (thumb.exists() && thumb.isFile()) {
                if (!thumb.delete()) {
                    return false;
                }
            }
            return true;
        } else if (thumb.exists() && thumb.isFile()) {
            if (thumb.delete()) {
                return true;
            }
        }
        return false;
    }

    public static boolean setImageDimensions(ImageInfoDTO image, String uploadDir) {
        if (image == null || StringUtils.isBlank(image.getUrl()) || StringUtils.isBlank(uploadDir)) {
            return false;
        }
        try {
            BufferedImage imageFile = ImageIO.read(new File(uploadDir + image.getUrl()));
            if (imageFile == null) {
                return false;
            }
            image.setHeight(imageFile.getHeight());
            image.setWidth(imageFile.getWidth());
            if (image.getHeight() == 0 || image.getWidth() == 0) {
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changeImageSize(ImageEditDTO imageDimensions, ImageInfoDTO item, String uploadDir) {
        if (imageDimensions == null || item == null || StringUtils.isBlank(uploadDir) || StringUtils.isBlank(item.getUrl())
                || imageDimensions.getWidth() <= 0 || imageDimensions.getHeight() <= 0
                || StringUtils.isBlank(imageDimensions.getEditType())) {
            return false;
        }
        File file = new File(uploadDir + item.getUrl());

        String fileType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
        if (fileType == null) {
            return false;
        }
        fileType = fileType.toLowerCase().replace("image/", "");
        if (StringUtils.isBlank(fileType)) {
            return false;
        }
        if (imageDimensions.getEditType().toLowerCase().equals("crop")) {
            if (!cropImage(file, fileType,imageDimensions, item, uploadDir)) {
                return false;
            }

            return true;
        } else if (imageDimensions.getEditType().toLowerCase().equals("scale")) {
            if (!scaleImage(file, fileType, imageDimensions, item, uploadDir)) {
                return false;
            }
            return true;
        }

        return false;

    }

    private static boolean scaleImage(File file, String fileType,
    ImageEditDTO imageDimensions, ImageInfoDTO item, String uploadDir){
        if (StringUtils.isBlank(fileType) || file == null || imageDimensions == null
                || item == null || StringUtils.isBlank(uploadDir)) {
            return false;
        }
        try {
            BufferedImage image = ImageIO.read(file);
            if (imageDimensions.getWidth() > image.getWidth() || imageDimensions.getHeight() > image.getHeight()
                    || StringUtils.isBlank(fileType)) {
                return false;
            }
            image = Scalr.resize(image, imageDimensions.getWidth(), imageDimensions.getHeight());
            ImageIO.write(image, fileType, file);
            item.setWidth(image.getWidth());
            item.setHeight(image.getHeight());
            String[] thumb_url = item.getUrl().split("\\.");
            if (image.getWidth() <= 200 && image.getHeight() <= 200) {
                if (thumb_url.length == 2) {
                    if (!deleteThumb(new File(uploadDir + "/" + thumb_url[0] + "_200x200." + thumb_url[1]))) {
                        return false;
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean cropImage(File file, String fileType,
                                     ImageEditDTO imageDimensions, ImageInfoDTO item, String uploadDir){
        if (StringUtils.isBlank(fileType) || file == null || imageDimensions == null
                || item == null || StringUtils.isBlank(uploadDir)) {
            return false;
        }
        try {
            BufferedImage image = ImageIO.read(file);
            if (imageDimensions.getWidth() > image.getWidth() || imageDimensions.getHeight() > image.getHeight()
                    || StringUtils.isBlank(fileType)) {
                return false;
            }
            image = Scalr.crop(image, imageDimensions.getxCoord(), imageDimensions.getyCoord(),
                    imageDimensions.getWidth(), imageDimensions.getHeight());
            if (!ImageIO.write(image, fileType, file)) {
                return false;
            }
            item.setWidth(image.getWidth());
            item.setHeight(image.getHeight());
            String[] thumb_url = item.getUrl().split("\\.");
            if (thumb_url.length == 2) {
                if (deleteThumb(new File(uploadDir + "/" + thumb_url[0] + "_200x200." + thumb_url[1]))) {
                    File reassignedThumb = new File(uploadDir + "/" + thumb_url[0] + "_200x200." + thumb_url[1]);
                    fileThumbMake(image, fileType, reassignedThumb);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteThumb(File thumb) {
        if (thumb == null) {
            return false;
        }

        if (thumb.exists() && thumb.isFile()) {
            if (thumb.delete()) {
                return true;
            }
            return false;
        }
        return true;
    }
}