package com.r7.core.integral.controller;

import com.r7.core.integral.constant.JsonResult;
import com.r7.core.integral.service.FileAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zs
 */

@RestController
@RequestMapping("fileAction")
public class FileActionController {
    @Autowired
    private FileAction fileAction;

    public JsonResult uploadFile(String filePath, Integer fileType) {
//        if ()
//        fileAction.uploadFile("C:/Users/zs/Desktop/1.png", )
        return null;
    }
}
