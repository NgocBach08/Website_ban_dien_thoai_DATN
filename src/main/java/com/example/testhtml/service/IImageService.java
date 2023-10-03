package com.example.testhtml.service;

import java.util.List;

public interface IImageService {

    String createImage(List<String> image, Long id);
    String editImage(List<String> image, Long id);
}
