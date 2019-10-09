package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.service.CompetitionService;
import cadc.service.JoinService;
import cadc.util.ExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author haya
 */
@Log4j2
@RestController
public class DownLoadController {
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private JoinService joinService;

    @RequestMapping(value = "/download/{competitionId}", method = RequestMethod.POST)
    public Object xxx(HttpServletResponse response, @PathVariable int competitionId) throws IOException {
        FileInputStream in = competitionService.getWord( competitionId );
        response.setHeader( "Access-Control-Expose-Headers", "Content-Disposition" );
        response.setHeader( "Content-Disposition", "attachment;filename=" + competitionId + ".doc" );
        response.setContentType( "application/octet-stream;charset=UTF-8" );
        // 形成输出流
        BufferedOutputStream out = new BufferedOutputStream( response.getOutputStream() );

        byte[] bys = new byte[1024];
        int len = 0;
        while ((len = in.read( bys )) != -1) {
            out.write( bys, 0, len );
        }
        in.close();
        out.close();
        return MessageFactory.message( true );
    }

    @RequestMapping(value = "/download/{competitionId}/enter/list", method = RequestMethod.POST)
    public Object getEnterListExcel(HttpServletResponse response, @PathVariable int competitionId) throws IOException {
        String fileName = joinService.generateEnterListExcel( competitionId );
        InputStream in = ExcelUtils.getExcel( fileName );
        response.setHeader( "Access-Control-Expose-Headers", "Content-Disposition" );
        response.setHeader( "Content-Disposition", "attachment;filename=" + competitionId + ".xlsx" );
        response.setContentType( "application/octet-stream;charset=UTF-8" );
        // 形成输出流
        BufferedOutputStream out = new BufferedOutputStream( response.getOutputStream() );

        byte[] bys = new byte[1024];
        int len = 0;
        while ((len = in.read( bys )) != -1) {
            out.write( bys, 0, len );
        }
        in.close();
        out.close();
        return MessageFactory.message( true );
    }
}
