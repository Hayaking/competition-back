package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.service.PicService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author haya
 */
@Log4j2
@RestController
public class PicController {
    @Autowired
    private PicService picService;

    /**
     * 上传图片 返回id
     * @param file
     * @return
     */
    @RequestMapping(value = "/pic", method = RequestMethod.POST)
    public Object uploadFiles(MultipartFile file) {
        log.warn( "上传图片了" );
        log.warn( file );
        int picId = picService.savePic( file );
        return MessageFactory.message( picId );
    }

}
