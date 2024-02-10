package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.DTO.FinalizeStoryDTO;
import com.kidpix.demo.Model.Entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FinalizeStoryService {


    @Autowired
    private CategoryService categoryService ;

    @Autowired
    private BookService bookService ;

    @Value("${prefix.scribus.path}")
    private String prefixScribusPath ;

    @Value("${scribus.path}")
    private String scribusPath;

    @Value("${script.path}")
    private String scriptPath;






    @Value("${output.pdf.path}")
    private String outputPdfPath;
    public String createStorybook(FinalizeStoryDTO request) {

     String outputPDF = outputPdfPath + System.currentTimeMillis() +"__"+request.getKidName()+"__"+request.getThemeName()+File.separator;
        File file = new File(outputPDF) ;
        if (!file.exists()){
            file.mkdirs();
            System.out.println("Created Folder Sucessfully : " + outputPDF);
        }
        outputPDF += request.getThemeName()+"__"+request.getKidName()+".pdf";

        BookEntity bookEntity = this.bookService.findBookById(request.getBookId()) ;

        String scribusThemPath = this.categoryService.findByName(request.getThemeName());
        System.out.println("Scribus Path Theme : " + scribusThemPath);
        try {




            StringJoiner storyJoiner = new StringJoiner("[[ ]]", "[[", "]]");
            request.getStoryList().forEach(storyJoiner::add);



            String commandd = scribusPath + " -g --python-script " + scriptPath +
                    " --kid_name \"" + request.getKidName() + "\"" +
                    " --listStory " + storyJoiner.toString() +
                    " --listImages " + request.getImageUrls().toString() +
                    " --script_path \"" + scribusThemPath + "\"" +
                    " --output_path \"" + outputPDF + "\"";
   if (request.getLang()==null || request.getLang().isEmpty()) {
       request.setLang("en");
   }
            String[] command2 = {
                    prefixScribusPath ,
                    scribusPath,
                    "-g",
                    "--python-script",
                    scriptPath,
                    "--theme_name",
                    request.getThemeName(),
                    "--kid_name",
                    request.getKidName(),
                    "--lang",request.getLang(),
                    "--listStory",
                    storyJoiner.toString(),
                    "--listImages",
                    request.getImageUrls().toString(),
                    "--script_path",
                    scribusThemPath,
                    "--output_path",
                    outputPDF
            };


            String temp = "" ;
            for (String s : command2 ) {
                temp+=s ;
            }
            System.out.println("Commadn  : "+temp);
            ProcessBuilder builder = new ProcessBuilder(command2);
            builder.inheritIO();
            Map<String, String> env = builder.environment();
            env.put("FONTCONFIG_PATH", "/usr/local/share/fonts/");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontFamilies = ge.getAvailableFontFamilyNames();

            for (String fontName : fontFamilies) {
                System.out.println("FONST NAME : "+fontName);
            }
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }

            return outputPDF; // Modify this to return the actual URL
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }
}
