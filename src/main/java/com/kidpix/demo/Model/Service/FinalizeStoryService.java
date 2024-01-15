package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.DTO.FinalizeStoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FinalizeStoryService {


    @Autowired
    private CategoryService categoryService ;

    @Value("${scribus.path}")
    private String scribusPath;

    @Value("${script.path}")
    private String scriptPath;

    @Value("${script.sla.path}")
    private String slaPath;

    @Value("${output.pdf.path}")
    private String outputPdfPath;
    public String createStorybook(FinalizeStoryDTO request) {

        String scribusThemPath = this.categoryService.findByName(request.getThemeName());
        try {
            StringJoiner storyJoiner = new StringJoiner("\" \"", "\"", "\"");
            request.getStoryList().forEach(storyJoiner::add);

            StringJoiner imageJoiner = new StringJoiner("\" \"", "\"", "\"");
            request.getImageUrls().forEach(imageJoiner::add);

            String command = scribusPath + " -g --python-script " + scriptPath +
                    " --kid_name \"" + request.getKidName() + "\"" +
                    " --listStory " + storyJoiner.toString() +
                    " --listImages " + imageJoiner.toString() +
                    " --script_path \"" + scribusThemPath + "\"" +
                    " --output_path \"" + outputPdfPath + "\"";
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "/c", command);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }

            return "URL to the PDF file"; // Modify this to return the actual URL
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }
}
