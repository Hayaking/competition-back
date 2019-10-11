package cadc.service;

import cadc.entity.Pic;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author haya
 */
public interface PicService extends IService<Pic> {
    int savePic(MultipartFile pic);
}
