package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.service.CompetitionService;
import cadc.service.JoinService;
import cadc.util.ExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

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

    /**
     * 下载竞赛申请表
     *
     * @param response
     * @param competitionId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download/{competitionId}", method = RequestMethod.POST)
    public Object getCompetitionWord(HttpServletResponse response, @PathVariable int competitionId) throws IOException {
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

    @PostMapping(value = "/download/budget/{competitionId}")
    public ResponseEntity<byte[]> getBudgetWord(@PathVariable int competitionId) throws IOException {
        ResponseEntity<byte[]> response;
        try (
                FileInputStream fis = competitionService.getBudgetWord( competitionId );
                FileChannel channel = fis.getChannel()
        ) {
            ByteBuffer body = ByteBuffer.allocate( fis.available() );
            channel.read( body );
            HttpHeaders headers = new HttpHeaders();
            headers.add( CONTENT_DISPOSITION, "attachment;filename=" + competitionId + ".doc" );
            headers.add( ACCESS_CONTROL_ALLOW_HEADERS, CONTENT_DISPOSITION );
            response = new ResponseEntity<>( body.array(), headers, HttpStatus.OK );
        }
        return response;
    }

    /**
     * @param competitionId
     * @param progressId
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/download/{competitionId}/{progressId}/enter/list")
    public ResponseEntity<byte[]> getEnterListExcel(@PathVariable int competitionId, @PathVariable int progressId) throws IOException {
        ResponseEntity<byte[]> response;
        String fileName = joinService.generateEnterListExcel( competitionId, progressId );
        File file = ExcelUtils.getFile( fileName );
        if (file.exists()) {
            try (
                    FileInputStream fis = new FileInputStream( file );
                    FileChannel channel = fis.getChannel()
            ) {
                ByteBuffer body = ByteBuffer.allocate( fis.available() );
                channel.read( body );
                HttpHeaders headers = new HttpHeaders();
                headers.add( CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".xlsx" );
                headers.add( ACCESS_CONTROL_ALLOW_HEADERS, CONTENT_DISPOSITION );
                response = new ResponseEntity<>( body.array(), headers, HttpStatus.OK );
            }
        } else {
            response = new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
        return response;
    }
}
