package cadc.service.impl;

import cadc.entity.Pic;
import cadc.mapper.PicMapper;
import cadc.service.PicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author haya
 */
@Service
public class PicServiceImpl extends ServiceImpl<PicMapper, Pic> implements PicService {
    @Resource
    private PicMapper picMapper;

    @Override
    public int savePic(MultipartFile file) {
        Writer writer = null;
        String name = file.getOriginalFilename();
        try {
            String outPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/images/"+name;
            File outFile = new File( outPath );
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
            }
            writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( outFile ), StandardCharsets.UTF_8 ), 10240 );
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //设置一个Pics
        Pic pic = new Pic();
        pic.setPath( "static/images/" + name );
        pic.setCreateTime( new Date() );
        boolean flag = pic.insert();
        return flag ? pic.getId() : -1;
    }

}
