/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.CommonController
 */
package tech.songjian.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.songjian.reggie.common.R;

import javax.servlet.Filter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * CommonController
 * @description 文件上传与下载
 * @author SongJian
 * @date 2022/12/6 14:33
 * @version
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;
    /**
     * @title upload
     * @author SongJian
     * @param: file
     * @updateTime 2022/12/6 15:28
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 文件上传功能
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info("文件:{}", file.toString());

        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用 UUID 重新生成文件名，防止文件名称重复造成文件覆盖
        String filename = UUID.randomUUID().toString() + suffix;

        // 创建目录
        File dir = new File(basePath);
        if (!dir.exists()) {
            // 不存在要创建
            dir.mkdir();
        }
        try {
            // 将临时文件进行转存到指定位置
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        // 输入流，通过输入流读取文件内容
        FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

        // 输出流，通过输出流返回给浏览器，在浏览器中显示图片
        ServletOutputStream outputStream = response.getOutputStream();

        // 说明是图片文件
        response.setContentType("image/jepg");
        try {
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            fileInputStream.close();
            outputStream.close();
        }
    }
}
 
