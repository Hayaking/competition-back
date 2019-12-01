package cadc.controller;

import cadc.service.DownLoadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author haya
 */
@Log4j2
@RestController
public class DownLoadController {
    @Autowired
    private DownLoadService downLoadService;

    /**
     * 下载竞赛申请表
     *
     * @param competitionId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download/{competitionId}", method = RequestMethod.POST)
    public ResponseEntity<byte[]>  getCompetitionWord( @PathVariable int competitionId) throws IOException {
        ResponseEntity<byte[]> response;
        try (
                FileInputStream fis = downLoadService.getCompetitionApplyWord(  competitionId );
                FileChannel channel = fis.getChannel()
        ) {
            ByteBuffer body = ByteBuffer.allocate( fis.available() );
            channel.read( body );
            HttpHeaders headers = new HttpHeaders();
            headers.add( "Access-Control-Expose-Headers", "Content-Disposition" );
            headers.add( "Content-Disposition", "attachment;filename=" + competitionId + ".docx" );
            response = new ResponseEntity<>( body.array(), headers, HttpStatus.OK );
        }
        return response;
    }

    /**
     * 下载预算表
     * @param competitionId
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/download/budget/{competitionId}")
    public ResponseEntity<byte[]> getBudgetWord(@PathVariable int competitionId) throws IOException {
        ResponseEntity<byte[]> response;
        try (
                FileInputStream fis = downLoadService.getBudgetApplyWord( competitionId );
                FileChannel channel = fis.getChannel()
        ) {
            ByteBuffer body = ByteBuffer.allocate( fis.available() );
            channel.read( body );
            HttpHeaders headers = new HttpHeaders();
            headers.add( "Access-Control-Expose-Headers", "Content-Disposition" );
            headers.add( "Content-Disposition", "attachment;filename=" + competitionId + ".docx" );
            response = new ResponseEntity<>( body.array(), headers, HttpStatus.OK );
        }
        return response;
    }

    /**
     * 下载报名列表
     * @param competitionId
     * @param progressId
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/download/{competitionId}/{progressId}/enter/list")
    public ResponseEntity<byte[]> getEnterListExcel(@PathVariable int competitionId, @PathVariable int progressId) throws IOException {
        ResponseEntity<byte[]> response;

        try (
                FileInputStream fis = downLoadService.getEnterListExcel( competitionId, progressId );
                FileChannel channel = fis.getChannel()
        ) {
            ByteBuffer body = ByteBuffer.allocate( fis.available() );
            channel.read( body );
            HttpHeaders headers = new HttpHeaders();
            headers.add( "Access-Control-Expose-Headers", "Content-Disposition" );
            headers.add( "Content-Disposition", "attachment;filename=" + progressId + ".xlsx" );
            response = new ResponseEntity<>( body.array(), headers, HttpStatus.OK );
        }
        return response;
    }

    /**
     * 下载比赛结果
     * @param progressId
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/download/result/{progressId}")
    public ResponseEntity<byte[]> getResult( @PathVariable int progressId) throws IOException {
        ResponseEntity<byte[]> response;
        try (
                FileInputStream fis = downLoadService.getProgressResult(progressId);
                FileChannel channel = fis.getChannel()
        ) {
            ByteBuffer body = ByteBuffer.allocate( fis.available() );
            channel.read( body );
            HttpHeaders headers = new HttpHeaders();
            headers.add( "Access-Control-Expose-Headers", "Content-Disposition" );
            headers.add( "Content-Disposition", "attachment;filename=" + progressId + ".docx" );
            response = new ResponseEntity<>( body.array(), headers, HttpStatus.OK );
        }
        return response;
    }
}
