package cadc.util;

import freemarker.template.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author haya
 */
public class WordUtils {
    public static String generateWord(String templatePath, String outPath,  Map<String,String> map) {

        try{
            Configuration configuration = new Configuration( new Version( "2.3.0" ) );
            configuration.setDefaultEncoding( "utf-8" );
            configuration.setDirectoryForTemplateLoading( new File( templatePath ) );
            Template template = configuration.getTemplate( "word.ftl" );
            File outFile = new File( outPath );
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
            }
            Writer writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( outFile ), StandardCharsets.UTF_8 ), 10240 );
            template.process( map, writer );
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return outPath;
    }

}
