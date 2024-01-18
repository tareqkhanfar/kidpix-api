package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.DTO.FinalizeStoryDTO;
import com.kidpix.demo.Model.DTO.ImageGeneratorDTO;
import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ImageGeneratorService {



    // animeGAN
    @Value("${animegan.python.env.path}")
    private String animeGanPythonEnvPath;

    @Value("${animegan.script.path}")
    private String animeGanScriptPath;

    @Value("${animegan.checkpoint.dir}")
    private String animeGanCheckpointDir;

    @Value("${animegan.output.dir}")
    private String animeGanOutputDir;




    //faceSwap
    @Value("${faceswap.python.env.path}")
    private String faceSwapPythonEnvPath;

    @Value("${faceswap.script.path}")
    private String faceSwapScriptPath;

    @Value("${faceswap.output.dir}")
    private String faceSwapOutputDir;

    @Autowired
    private FinalizeStoryService storyService ;

    @Autowired
    private CategoryService categoryService ;

    @Autowired
    private BookService bookService ;

    @Autowired
    private EmailService emailService ;



    public Map<String , String> generateImage(ImageGeneratorDTO imageGeneratorDTO) {

        String kidName = imageGeneratorDTO.getKidName();
        String imageInputPath = imageGeneratorDTO.getImageInputPath();
        String themeName = imageGeneratorDTO.getThemeName();

      //String outputDir = runFaceSwapScript(imageInputPath, themeName, kidName);
     String outputDir = "/var/www/html/assets/bundles/zoo/" ;

        File outputDir__ = new File(outputDir);

        // List all files in the directory
        Map<String , String > stringStringMap = new LinkedHashMap<>();
        File[] files = outputDir__.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().contains("Swap_0.png")){
                       stringStringMap.put("coverPageAfterSwap" , file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("The specified path does not denote a directory.");
        }
        System.out.println("[DEGUB.IMAGEGEN] outputDir : " + outputDir);



        if (imageGeneratorDTO.getWant_anime() == 1) {
            outputDir = runAnimeGanScript(outputDir, kidName);
        }
        FinalizeStoryDTO finalizeStoryDTO = new FinalizeStoryDTO() ;
        finalizeStoryDTO.setStoryList(imageGeneratorDTO.getStoryList());
        finalizeStoryDTO.setThemeName(imageGeneratorDTO.getThemeName());
        finalizeStoryDTO.setKidName(imageGeneratorDTO.getKidName());
        finalizeStoryDTO.setBookId(imageGeneratorDTO.getBookId());
        finalizeStoryDTO.setImageUrls(outputDir);

        String finalPath = storyService.createStorybook(finalizeStoryDTO) ;

        BookEntity bookEntity = this.bookService.findBookById(imageGeneratorDTO.getBookId());
        bookEntity.setBookPath(finalPath);
        this.bookService.createBook(bookEntity);

        System.out.println("[DEGUB.IMAGEGEN] fileNames : " + outputDir);
        UserEntity userEntity = this.bookService.findBookById(imageGeneratorDTO.getBookId()).getUser() ;
        this.emailService.sendBook(userEntity.getFirstName() +" " + userEntity.getLastName() ,userEntity.getEmail() , finalPath );


stringStringMap.put("finalPath" , finalPath) ;

        return stringStringMap ;

    }


    private String runFaceSwapScript(String imageInputPath, String themeName, String kidName) {
///////////////////////////////////////////////////////////////////////////////////////
        String themePath = this.categoryService.findThemePathByCatName(themeName) ;

/////////////////////////////////////////////////////////////////////////////////////////
        String outputFilename = "outputFaceSwap_" + System.currentTimeMillis() + "_" + kidName;

        System.out.println("[DEGUB.FACESWAP] input data : " + imageInputPath);

        File outDir = new File(faceSwapOutputDir + File.separator + outputFilename );

        if (!outDir.exists()) {
            if (outDir.mkdirs()){
                System.out.println("[DEGUB.FACESWAP] created folder successfully . ");
            }
            else {
                System.out.println("[DEGUB.FACESWAP] failed to create folder . ");
            }
        }

        executeFaceSwapScript(imageInputPath, themeName, faceSwapOutputDir + File.separator + outputFilename);

        System.out.println("[DEGUB.IMAGEGEN.FACESWAP] finished executing script ?? ");
         System.out.println("Output Dir From FaceSwap : " +faceSwapOutputDir + File.separator + outputFilename );
        return faceSwapOutputDir + File.separator + outputFilename;


    }

    private void executeFaceSwapScript(String imageInputPath, String themeName, String outputDir) {

        System.out.println("[DEGUB.FACESWAP] imageInputPath : " + imageInputPath);
        System.out.println("[DEGUB.FACESWAP] themeName : " + themeName);
        System.out.println("[DEGUB.FACESWAP] outputDir : " + outputDir);

        String pythonEnvPath = Paths.get(this.faceSwapPythonEnvPath).toAbsolutePath().toString();
        String scriptPath = Paths.get(this.faceSwapScriptPath).toAbsolutePath().toString();

        System.out.println("[DEGUB.FACESWAP] Python Script : " + pythonEnvPath);
        System.out.println("[DEGUB.FACESWAP] Script GAN : " + scriptPath);

        String[] command = {
                pythonEnvPath,
                scriptPath,
                "-s", imageInputPath,
                "-b", themeName,
                "-o", outputDir
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
         processBuilder.inheritIO();


        try {
            Process process = processBuilder.start();

            //to show the output of the script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {

            }

            int exitCode = process.waitFor();

            System.out.println("[DEGUB.FACESWAP] Exited with code " + exitCode);




        } catch (IOException | InterruptedException e) {
            System.out.println("[DEGUB.FACESWAP] error executing script : " + e.getMessage());
        }

    }

    private String runAnimeGanScript(String dirImagesInput, String kidName) {

        String outputFilename = "outputAnime_" + System.currentTimeMillis() + "_" + kidName;

        System.out.println("input data : " + dirImagesInput);

        File outDir = new File(animeGanOutputDir + File.separator + outputFilename + File.separator);

        if (!outDir.exists()) {
            outDir.mkdirs();
            System.out.println("created folder successfully . ");
        }

        executeAnimeGANScript(dirImagesInput, animeGanOutputDir + File.separator + outputFilename + File.separator);

        System.out.println("test 2 ");


      System.out.println("OutputDir  from Anime GAN : " +animeGanOutputDir + File.separator +outputFilename );

        return animeGanOutputDir + File.separator +outputFilename;

    }




    private void executeAnimeGANScript(String testDir, String outputDir) {

        System.out.println("test data : " + testDir);
        String pythonEnvPath = Paths.get(this.animeGanPythonEnvPath).toAbsolutePath().toString();
        String scriptPath = Paths.get(this.animeGanScriptPath).toAbsolutePath().toString();
        String checkpointDir = Paths.get(this.animeGanCheckpointDir).toAbsolutePath().toString();


        System.out.println("Python Script : " + pythonEnvPath);
        System.out.println("Script GAN : " + scriptPath);
        System.out.println("checkPoint : " + checkpointDir);
        String[] command = {
                pythonEnvPath,
                scriptPath,
                "--checkpoint_dir", checkpointDir,
                "--test_dir", testDir,
                "--output_dir", outputDir
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();

        try {
            Process process = processBuilder.start();


            // Reading the output of the script
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exited with code " + exitCode);
            System.out.println("TEST 1 ");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }




}
